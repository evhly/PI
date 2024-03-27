import java.util.ArrayList;

public class Search {

    private ArrayList<Course> results;
    private String query;
    private CourseDatabase DB;
//    private Professor selectedProfessor;
//    private String startTime;
//    private String endTime;
//    private String selectedDepartment;
    private ArrayList<Filter> filters;

    public Search(CourseDatabase DB){
        this.DB = DB;
        results = new ArrayList<>();
    }
    public ArrayList<Course> modifyQuery(String query){
        this.query = query.toLowerCase();
        return search();
    }

    public void addFilter(Filter filter) {
        filters.add(filter);
    }

    public ArrayList<Course> getResults() {
        return results;
    }

    public ArrayList<Course> search(){
        results = new ArrayList<>();
        for(Course course : DB.getCourses()){
            for (Filter filter : filters) {
                if (filter.getType() == Filter.type.DEPARTMENT) {
                    if (!course.getDepartment().equals(filter.getDepartment())) {
                        continue;
                    }
                }
                if (filter.getType() == Filter.type.PROFESSOR) {
                    if (!course.getProfessor().equals(filter.getProfessor())) {
                        continue;
                    }
                }
                if (filter.getType() == Filter.type.TIMES) { //TODO: finish
                    // for each day of week
                    // if not at least one of the days has times that match the filter's
                    // continue;
                }
            }
            String[] arr = {course.getName().toLowerCase(), course.getCode().toLowerCase()}; // name, code
            for(int j = 0; j < arr.length; j++){ // for the name first, then the code
                String nameOrCode = arr[j];
                int index = nameOrCode.indexOf(query);
                if(index != -1){ // index would be -1 if query is not in the course name or code
                    if(index == 0 || nameOrCode.charAt(index-1) == ' ') { // either the name or code matches
                                                                          // fully or is the start of a word
                                                                          // in the name or code
                        results.add(course);
                        j = 2;
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
                arr[i] = course.getName();
                i++;
            }
            return arr;
        }

//    public ArrayList<Professor> getSelectedProfessors(){
//        return null;
//    }
//    public ArrayList<String> getSelectedTimes(){
//        return null;
//    }
//    public ArrayList<String> getSelectedDepartments(){
//        return null;
//    }
//    public ArrayList<Course> addProfessor(Professor professorToAdd){
//        return null;
//    }
//    public ArrayList<Course> addTimes(String start, String end){
//        return null;
//    }
//    public ArrayList<Course> addDepartment(String deptToAdd){
//        return null;
//    }



}
