import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class CourseDatabase {

    private HashSet<Course> courses;
    public CourseDatabase(String path){
        File in = new File(path);
    }

    /**
     * loads in the database from a CSV file
     * @param fileName name of CSV file
     * @return data is an ArrayList of courses
     * @throws FileNotFoundException
     */
    public static ArrayList<Course> loadDB(String fileName) throws FileNotFoundException {
        ArrayList<Course> data = new ArrayList<>();
        File f = new File(fileName);
        Scanner fs = new Scanner(f);
        //get rid of header
        fs.nextLine();

        while (fs.hasNext()){
            // Scanner ls = new Scanner(fs.nextLine());
            //Course c = new Course(fs.nextLine());
            //data.add(c);
        }
        fs.close();
        return data;
    }

    public CourseDatabase() {
        courses = new HashSet<>();
    }

    public void addCourse(Course course) {
        courses.add(course);
    }

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
