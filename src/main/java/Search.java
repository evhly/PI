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
                results.add(course);
            }
        }
        results = searchWithQuery(query, results);
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

    private ArrayList<Course> searchWithQuery(String q, ArrayList<Course> courses){
        TreeMap<Double, ArrayList<Course>> resultsMap = new TreeMap<>(Collections.reverseOrder());

        for(Course course : courses){
            double score = 0.0;
            String[] nameWords = course.getName().toLowerCase().split(" ");
            String[] queryWords = query.split(" ");
            for(int i = 0; i < queryWords.length; i++){
                String queryWord = queryWords[i];
                double addToScore = 0.0;
                for(int j = 0; j < nameWords.length; j++){
                    String nameWord = nameWords[j];
                    int idx = nameWord.indexOf(queryWord);
                    if(queryWord.equals(nameWord)){ // query matches current word from course name
                        if(j == 0){ // matches first word in course name
                            addToScore = 1;
                        } else {
                            addToScore = 0.9;
                        }
                        j = nameWords.length; // stop checking words from course name
                    }
                    else if (idx != -1) { // if query appears in current course name word
                        if (idx == 0) { // if it appears at the beginning of course name word
                            if (j == 0) { // if it appears in the first word of the course name
                                addToScore = 0.8;
                            } else if(addToScore < 0.7){ // if it appears in a word of the course name other than the first
                                addToScore = 0.7;
                            }
                        } else if (queryWord.length() > 1 && addToScore < 0.4) { // query is inside of a word
                            addToScore = 0.4;
                        }
                    }
                }
                if(addToScore == 0.0){
                    score -= 0.4;
                } else{
                    score += addToScore;
                }
            }
            score = score / queryWords.length;
            if(score > 0) {
                if (resultsMap.containsKey(score)) {
                    resultsMap.get(score).add(course);
                } else {
                    ArrayList<Course> toAdd = new ArrayList<>();
                    toAdd.add(course);
                    resultsMap.put(score, toAdd);
                }
            }
        }

        ArrayList<Course> orderedResults = new ArrayList<>();
        for(ArrayList<Course> cArr : resultsMap.values()){
            orderedResults.addAll(cArr);
        }
        return orderedResults;
    }
}
