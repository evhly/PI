import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Search {

    private ArrayList<Course> results;
    private String query;
    private CourseDatabase DB;
    private ArrayList<Filter> filters;

    public Search(CourseDatabase DB){
        this.DB = DB;
        this.filters = new ArrayList<>();
        results = new ArrayList<>();
    }
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

    public ArrayList<Course> search(){
        results = new ArrayList<>();
        for(Course course : DB.getCourses()){
            boolean filterMismatch = false;
            for (Filter filter : filters) {
                // none of these checks will run if the filter is disabled
                if ((filter.getType() == Filter.type.DEPARTMENT) && filter.isEnabled()) {
                    if (!course.getDepartment().equals(filter.getDepartment())) {
                        filterMismatch = true;
                    }
                }
                if ((filter.getType() == Filter.type.PROFESSOR) && !filterMismatch && filter.isEnabled()) {
                    if (!course.getProfessor().equals(filter.getProfessor())) {
                        filterMismatch = true;
                    }
                }
                // if at least one day has a matching start and end time, then the course will be considered
                if ((filter.getType() == Filter.type.TIMES) && !filterMismatch && filter.isEnabled()) {
                    boolean hasMatchingTimes = false;
                    DateTimeFormatter ampmFormatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
                    for (DayOfWeek day : DayOfWeek.values()) { // for each day of the week
                        if (course.getMeetingTimes().containsKey(day)) {
                            LocalTime filterStart = LocalTime.parse(filter.getTimes().get(0), ampmFormatter);
                            LocalTime filterEnd = LocalTime.parse(filter.getTimes().get(1), ampmFormatter);
                            LocalTime courseStart = course.getMeetingTimes().get(day).get(0);
                            LocalTime courseEnd = course.getMeetingTimes().get(day).get(1);
                            if (filterStart.equals(courseStart) && filterEnd.equals(courseEnd)) {
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
                String[] arr = {course.getName().toLowerCase(), course.getCode().toLowerCase()}; // name, code
                for (int j = 0; j < arr.length; j++) { // for the name first, then the code
                    String nameOrCode = arr[j];
                    int index = nameOrCode.indexOf(query);
                    if (index != -1) { // index would be -1 if query is not in the course name or code
                        if (index == 0 || nameOrCode.charAt(index - 1) == ' ') { // either the name or code matches
                            // fully or is the start of a word
                            // in the name or code
                            results.add(course);
                            j = 2;
                        }
                    }
                }
            }
        }
        return results;
    }

        public String[] resultsStrs() {
            String[] arr = new String[results.size()];
            int i = 0;
            for (Course course : results) {
                arr[i] = course.getCode(); // unique identifier
                i++;
            }
            return arr;
        }
}
