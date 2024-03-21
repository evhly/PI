import java.io.File;
import java.util.ArrayList;

public class CourseDatabase {

    private ArrayList<Course> courses;

    public CourseDatabase(){
        courses = new ArrayList<>();
    }
    public void addCourse(Course c){
        courses.add(c);
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }
}
