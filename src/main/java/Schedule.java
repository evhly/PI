import java.util.ArrayList;

public class Schedule {

    private ArrayList<Course> courses;
    private String title;
    public Schedule(){}
    public void deleteCourse(Course courseToDelete){}
    public void addCourse(Course courseToAdd){}
    public Schedule undo(){
        return null;
    }
    public Boolean checkConflict(Course sectionToCheck){
        return false;
    }
    public String showMoreInfo(Course sectionToCheck){
        return "";
    }
    public void rename(){}
    public String getTitle(){
        return title;
    }


}
