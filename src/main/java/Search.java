import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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

    /**
     *
     * @return map containing the frequency of each word that appears in any course name in the DB
     * key: word in the course name
     * value: how many times it appears across all course names
     */
    public HashMap<String, Integer> getWordFreqMap(){
        // key: word
        // value: how many times it appears in course names
        HashMap<String, Integer> wordFreq = new HashMap<String, Integer>();

        for(Course course : DB.getCourses()){
            String[] words = course.getName().split(" ");
            for(String word : words){
                word = word.toLowerCase();
                wordFreq.put(word, wordFreq.getOrDefault(word, 0) + 1);
            }
        }
        return wordFreq;
    }

    /**
     *
     * @param query word to suggest a completed word for
     * @return a suggested word if one is found, else null
     */
    public String suggestWord(String query){
        HashMap<String, Integer> map = getWordFreqMap();
        ArrayList<String> matches = new ArrayList<>();
        for(String s : map.keySet()){
            if(s.indexOf(query) == 0){
                matches.add(s);
//                System.out.println("match: " + s);
            }
        }
        System.out.println(matches.size());
        if(!matches.isEmpty() && matches.size() < 10){ // TODO: fine tune this threshold
            String match = matches.get(0);
            int count = map.get(match);
            for(int i = 1; i < matches.size(); i++){
                if(map.get(matches.get(i)) > count){
                    match = matches.get(i);
                    count = map.get(match);
                }
            }
            return match;
        }
        return null;
    }
}
