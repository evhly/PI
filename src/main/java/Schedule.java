import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Schedule {

    private ArrayList<Course> courses;
    private String title;


    public ArrayList<Course> getCourses(){
        return courses;
    }


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

//    public ArrayList<Course> getCourses() {
//        return courses;
//    }

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
    public void rename(String title){
        this.title = title;
    }
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
        sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }

    /**
     * Prints schedules to a file in a format (csv) that can be read back into the program
     * @throws IOException File cannot be created or written to
     */
    public static void saveSchedules(File file, ArrayList<Schedule> schedules) {
        try {
            PrintWriter fout = new PrintWriter(file);
            StringBuilder sb = new StringBuilder();
            for (Schedule schedule : schedules) {
                sb.append(schedule.toSave());
                sb.append("\n");
            }
            if (schedules.size() > 0) {
                sb.deleteCharAt(sb.length() - 1);
            }
            fout.print(sb);
            fout.flush();
            fout.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Schedule loadSchedule(String scheduleString, CourseDatabase db) {
        String[] parts = scheduleString.split(",");
        Schedule schedule = new Schedule(parts[0].trim());

        for(int i=1; i<parts.length; i++) {
            schedule.addCourse(db.getCourseData(parts[i]));
        }

        return schedule;
    }

    /**
     * Loads schedules from a file
     * @param file The file to load the list of schedules from
     * @param db The CourseDatabase to draw course data from, since we only have the course code
     * @throws IOException If a save data file does not exist
     */
    public static ArrayList<Schedule> loadSchedules(File file, CourseDatabase db) {
        ArrayList<Schedule> schedules = new ArrayList<>();

        try {
            Scanner fileScanner = new Scanner(file);
            fileScanner.useDelimiter("\n");
            while (fileScanner.hasNext()) {
                schedules.add(Schedule.loadSchedule(fileScanner.next(), db));
            }
            fileScanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return schedules;
    }

    @Override
    public String toString(){
        String toR = "";
        for (int i = 0; i < courses.size(); i++) {
            toR += i + ": " +  courses.get(i).consoleString() + "\n";
        }
        return toR;
    }

}
