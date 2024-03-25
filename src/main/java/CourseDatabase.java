import java.io.File;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.NoSuchElementException;

public class CourseDatabase {

    private HashSet<Course> courses;
    public CourseDatabase(String path){
        File in = new File(path);
    }

    public CourseDatabase() {
        courses = new HashSet<>();
    }

    public void addCourse(Course course) {

        // checks if a course already exists with this course code
        try { //TODO: it may be better to use if/else since it is faster than try/catch in Java
            Course other = getCourseData(course.getCode());

            // there is a different course with this same course code (likely 4-credit MATH course)
            // merge the courses' meetingTimes
            if (!course.hasConflict(other)) {
                for (DayOfWeek day : DayOfWeek.values()) { // for each day of the week
                    if (course.getMeetingTimes().containsKey(day)) { // if course has startTime/endTime for that day

                        // trying to get a deep copy
                        ArrayList<LocalTime> times = new ArrayList<>();
                        times.add(course.getMeetingTimes().get(day).get(0));
                        times.add(course.getMeetingTimes().get(day).get(1));

                        other.getMeetingTimes().put(day, times); // put that entry in the meetingTimes HashMap of other
                        return; // merger complete--no need to add course to the database
                    }
                }

            } else {
                // likely duplicate course encountered
                throw new RuntimeException("Likely duplicate course detected");
            }

        } catch (NoSuchElementException nse) {
            // course with same code is not in database, proceed as normal
        }

        courses.add(course); // no duplicates or 4-credit math courses
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
