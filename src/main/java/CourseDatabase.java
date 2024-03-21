import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.NoSuchElementException;

public class CourseDatabase {

    private HashSet<Course> courses;
    public CourseDatabase(File in){}

    public HashSet<Course> getCourses() {
        return courses;
    }

    /**
     * Gets the data for a course from the database given just that course's code (primary key).
     * @param code Course code
     * @return Fully populated course object, or null if the course does not exist in the database
     */
    public Course getCourseData(String code) {
        for (Course course : courses) {
            if (course.getCode().equals(code)) {
                return course;
            }
        }
        throw new NoSuchElementException("Course with code " + code + " does not exist in database.");
    }
}
