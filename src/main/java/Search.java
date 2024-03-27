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
            String[] arr = {course.getName().toLowerCase(), course.getCode().toLowerCase()};
            for(int j = 0; j < arr.length; j++){
                String q = arr[j];
                int i = q.indexOf(query);
                if(i != -1){
                    if(i == 0 || q.charAt(i-1) == ' ') {
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
