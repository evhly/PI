import java.util.ArrayList;

public class Search {

    private ArrayList<Course> results;
    private String query;
    private CourseDatabase DB;
    private Professor selectedProfessor;
    private String startTime;
    private String endTime;
    private String selectedDepartment;
    public Search(CourseDatabase DB){
        this.DB = DB;
    }
    public ArrayList<Course> modifyQuery(String query){
        this.query = query;
        return search();
    }
    public ArrayList<Course> search(){
        results = new ArrayList<>();
        for(Course course : DB.getCourses()){
            if(course.getName().contains(query) || course.getCode().contains(query)){
                results.add(course);
            }
        }
        return results;
    }
    public ArrayList<Professor> getSelectedProfessors(){
        return null;
    }
    public ArrayList<String> getSelectedTimes(){
        return null;
    }
    public ArrayList<String> getSelectedDepartments(){
        return null;
    }
    public ArrayList<Course> addProfessor(Professor professorToAdd){
        return null;
    }
    public ArrayList<Course> addTimes(String start, String end){
        return null;
    }
    public ArrayList<Course> addDepartment(String deptToAdd){
        return null;
    }



}
