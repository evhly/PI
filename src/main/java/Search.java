import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Search {

    private ArrayList<Course> results;
    private String query;
    private CourseDatabase DB;
    private ArrayList<Filter> filters;

    App app = App.getInstance();

    public Search(CourseDatabase DB){
        this.DB = DB;
        this.filters = new ArrayList<>();
        results = new ArrayList<>();
    }

    /**
     *
     * @param query search query used to determine search results
     * @return search results returned when searching with the query
     */
    public ArrayList<Course> modifyQuery(String query){
        this.query = query.toLowerCase();
        return search();
    }

    public void addFilter(Filter filter) {
        filters.add(filter);
    }

    public void clearFilters() {
        this.filters = new ArrayList<>();
    }

    public ArrayList<Course> getResults() {
        return results;
    }

    /**
     * Searches the CourseDatabase for Courses that contain all query words within their course code, name, and meetings
     * Filters can be used to narrow the search to Courses that match department or Professor or meeting time
     * @return A list of Courses that match the query and filters
     */
    public ArrayList<Course> search(){
        results = new ArrayList<>();

        //System.out.println(filters.size());
        for(Course course : DB.getCourses()){ // checks every Course in the DB
            boolean filterMismatch = false; // this variable holds whether the filters rule a Course out
            for (Filter filter : filters) { // checks every filter

                if (filter.getType() == Filter.type.DEPARTMENT) {
                    if (!course.getDepartment().equals(filter.getDepartment())) { // checks for strict match
                        filterMismatch = true;
                    }
                }
                if ((filter.getType() == Filter.type.PROFESSOR) && !filterMismatch) {
                    if (!course.getProfessor().equals(filter.getProfessor())) { // checks for strict match
                        filterMismatch = true;
                    }
                }
                // if at least one day has a matching start and end time, then the course will be considered
                if ((filter.getType() == Filter.type.TIMES) && !filterMismatch) {
                    boolean hasMatchingTimes = false;

                    System.out.println(course.getMeetingTimes() + " == " +filter.getTimes()+"?"); //TODO: remove? (debugging?)


                    DateTimeFormatter ampmFormatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
                    for (DayOfWeek day : DayOfWeek.values()) { // for each day of the week
                        if (course.getMeetingTimes().containsKey(day)) { // if the course meets during that day
                            LocalTime filterStart = LocalTime.parse(filter.getTimes().get(0), ampmFormatter);
                            System.out.println("filter start" + filterStart);
                            LocalTime filterEnd = LocalTime.parse(filter.getTimes().get(1), ampmFormatter);
                            System.out.println("filter end" + filterEnd);
                            LocalTime courseStart = course.getMeetingTimes().get(day).get(0);
                            System.out.println("course start" + courseStart);
                            LocalTime courseEnd = course.getMeetingTimes().get(day).get(1);
                            System.out.println("course end" + courseEnd);
                            if ((courseStart.isAfter(filterStart) && courseEnd.isBefore(filterEnd))
                            || (courseStart.equals(filterStart))
                            || (courseEnd.equals(filterEnd))) {
                                hasMatchingTimes = true;
                            }
                        }
                    }
                    if (!hasMatchingTimes) {
                        filterMismatch = true;
                    }
                }
            }
            if (!filterMismatch) { // if the filters match, continue to check
                String[] queryList = query.split(" "); // list of each individual word in the query
                boolean match = true; // keeps track of whether a query matches the current Course
                ArrayList<String> arr = new ArrayList<>(); // contains each aspect of the Course that the query is checked against
                arr.add(course.getName().toLowerCase());
                arr.add(course.getCode().toLowerCase());
                // add days and start times for each course meeting to arr
                for(Map.Entry<DayOfWeek, ArrayList<LocalTime>> c : course.getMeetingTimes().entrySet()){
                    arr.add(c.getValue().get(0).toString());
                    arr.add(c.getKey().toString().toLowerCase());
                }
                int i = 0;
                while(i < queryList.length && match) { // check each word in the query, and stop if a word in any query is not found in any aspect of the Course
                    match = false;
                    for (int j = 0; j < arr.size(); j++) {
                        String fieldToCheck = arr.get(j);
                        int index = fieldToCheck.indexOf(queryList[i]);
                        if (index != -1) { // index would be -1 if query is not in fieldToCheck
                            if (index == 0 || fieldToCheck.charAt(index - 1) == ' ') { // the beginning of the name, code, or a meeting descriptor matches the current query word
                                j = arr.size();
                                match = true;
                            }
                        }
                    }
                    i++;
                }
                if(match){
                    results.add(course); // if each query word is found in an aspect of course, add course to search results
                }
            }
        }
        return results;
    }

    /**
     * Used for debugging
     * @return String representation of the current value of results
     */
    public String[] resultsStrs() {
        String[] arr = new String[results.size()];
        int i = 0;
        for (Course course : results) {
            arr[i] = course.getCode(); // unique identifier
            i++;
        }
        return arr;
    }

    public ArrayList<Course> searchBarSearch(String query, String department, Professor professor){
        ArrayList<Course> searchResults = new ArrayList<>();
        modifyQuery(query);
        Filter departmentFilterSelected = new Filter(Filter.type.DEPARTMENT, department);
        Filter facultyFilterSelected = new Filter(Filter.type.PROFESSOR, (Professor) professor);

        if(!Objects.equals(department, "")) {
            addFilter(departmentFilterSelected);
        }
        if(!Objects.equals(professor, new Professor("", ""))) {
            addFilter(facultyFilterSelected);
        }
        searchResults.addAll(search());
        return searchResults;
    }

    public String getBestMatch(String word){
        char[] wordArr = word.toLowerCase().toCharArray();
        HashSet<String> wordSet = setOfWords();
        for (Iterator<String> it = wordSet.iterator(); it.hasNext(); ) {
            String next = it.next().toLowerCase();
            if(Math.abs(word.length() - next.length()) <= 2){
                if(diffAlgorithm(wordArr, next.toCharArray()) <= 3){
                    return next;
                }
            }
        }
        return null;
    }

    public HashSet<String> setOfWords(){
        HashSet<String> wordSet = new HashSet<>();
        for(Course course : DB.getCourses()){
            String[] words = course.getName().split(" ");
            for(String word : words){
                word = word.toLowerCase();
                wordSet.add(word);
            }
        }
        return wordSet;
    }

    /**
     * Java implementation of the Myers Diff algorithm, done by Allison Harnly in COMP422 Fall 2023
     *
     * @param A one of two sequences to find the edit difference between
     * @param B one of two sequences to find the edit difference between
     * @return the minimum number of edits needed to transform A into B
     * A single edit is either one deletion or one insertion of a character in a sequence
     */
    public int diffAlgorithm(char[] A, char[] B){
        int N = A.length;
        int M = B.length;
        int DMax = M + N;
        int[][] VArr = new int[DMax + 1][];
        int shiftV = DMax;

        int[] V = new int[2 * DMax + 1]; // At the end of the line 44 loop, V[i] always holds the furthest reaching D-path on diagonal i-VShift, if such a path exists
        V[shiftV + 1] = 0;

        int x = 0; // x value of the furthest point on the path currently being traced
        int y = 0; // y value of the furthest point on the path currently being traced

        // calculate the furthest reaching D-path for each possible diagonal and store these values in VArr[D]
        for (int D = 0; D <= DMax; D++) {

            // for each k, find the furthest reaching D-path on diagonal k
            for (int k = -D; k <= D; k += 2) {
                // determine if the furthest D-path on diagonal k will be an extension of the furthest (D-1)-path on diagonal k+1 or diagonal k-1
                if (k == -D || (k != D && V[shiftV + k - 1] < V[shiftV + k + 1])) {
                    x = V[shiftV + k + 1]; // if diagonal k+1, the current path extends one unit vertically downward from V[shiftV + k + 1]
                } else {
                    x = V[shiftV + k - 1] + 1; // if diagonal k-1, the current path extends one unit horizontally rightward from V[shiftV + k - 1]
                }

                y = x - k; // calculate the value of y at the point at the end of the current path

                // take the longest snake extending from (x,y)
                while (x < N && y < M && A[x] == B[y]) {
                    x++;
                    y++;
                }

                // add x coordinate to V array to keep record of the furthest reaching D-path on diagonal k
                V[shiftV + k] = x;

                // return the edit distance if the point (N,M) has been reached, as at this point the path corresponding to the LCS has been fully traced
                if (x >= N && y >= M) {
                    return D;
                }
            }
            // Add the V array for value D to VArr
            VArr[D] = V.clone();
        }
        // this next line should never run
        return -1;
    }
}
