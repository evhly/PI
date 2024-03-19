import java.io.File;
import java.util.ArrayList;

public class CourseDatabase {

    private ArrayList<Course> courses;
    public CourseDatabase(File in){}

    public ArrayList<Course> getCourses() {
        return courses;
    }
}
