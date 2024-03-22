import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Schedule {

    private ArrayList<Course> courses;
    private String title;
    public Schedule(ArrayList<Course> courses, String title) {
        this.courses = courses;
        this.title = title;
    }

    public Schedule() {
        this.courses = new ArrayList<Course>();
        this.title = "Untitled";
    }

    public Schedule(String title) {
        this.courses = new ArrayList<>();
        this.title = title;
    }

    /**
     * Removes course from schedule
     * @param courseToDelete Course to remove from schedule
     */
    public void deleteCourse(Course courseToDelete){
        courses.remove(courseToDelete);
    }

    /**
     * Adds a course if it does not conflict with any other course in the current schedule
     * @param courseToAdd Course to try to add to schedule
     * @return false if there is a time conflict, true otherwise
     */
    public Boolean addCourse(Course courseToAdd){
        if(checkConflict(courseToAdd)){
            return false;
        }
        courses.add(courseToAdd);
        return true;
    }
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

    public String toSave() {
        StringBuilder sb = new StringBuilder();
        sb.append(title + ",");
        for (Course course : courses) {
            sb.append(course.getCode());
            sb.append(",");
        }
        return sb.toString();
    }

    /**
     * Prints schedules to a file in a format (csv) that can be read back into the program
     * @throws IOException File cannot be created or written to
     */
    public static void saveSchedules(Student s) throws IOException {
        PrintWriter fout = new PrintWriter(s.getInformation().getId() + "_savedSchedules.csv");
        StringBuilder sb = new StringBuilder();
        for (Schedule schedule : s.getSchedules()) {
            sb.append(schedule.toSave());
            sb.append("\n");
            sb.append(",");
        }
        fout.print(sb);
        fout.flush();
        fout.close();
    }

    /**
     * Loads schedules of a student from a file
     * @param db The CourseDatabase to draw course data from, since we only have the course code
     * @throws IOException If a save data file does not exist
     */
    public static void loadSchedules(CourseDatabase db, Student s) throws IOException {
        try {
            File file = new File(s.getInformation().getId() + "_savedSchedules.csv");
            Scanner fileScanner = new Scanner(file);
            fileScanner.useDelimiter(",");

            while (fileScanner.hasNext()) {
                Schedule schedule = new Schedule(fileScanner.next());
                do {
                    String code = fileScanner.next();
                    if (code.equals("\n")) {
                        break;
                    }
                    Course course;
                    try {
                        course = getCourseFromCode(code, db);
                        schedule.addCourse(course);
                    } catch (NoSuchElementException nse) {
                        System.out.println(nse.getMessage());
                    }
                } while (true);

                s.getSchedules().add(schedule);
            }

            fileScanner.close();

        } catch (NullPointerException e) {
            System.out.println("No saved schedules exist for " + s.getInformation().getLastName() + ", ID: " + s.getInformation().getId());
        }

    }

    public static Course getCourseFromCode(String code, CourseDatabase db) {;
        return db.getCourseData(code);
    }

}
