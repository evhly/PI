import java.io.File;
import java.util.ArrayList;

public class CourseDatabase {

    private ArrayList<Course> courses;
    public CourseDatabase(File in){}

    public ArrayList<Course> getCourses() {
        return courses;
    }

    /**
     * Gets the data for a course from the database given just that course's code (primary key).
     * @param code Course code
     * @return Fully populated course object
     */
    public Course getCourseData(String code) {
        return new Course(); // stub
    }
}
