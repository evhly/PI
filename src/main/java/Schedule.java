import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Schedule {

    private ArrayList<Course> courses; // the courses contained in a Schedule
    private String title; // the title given to the Schedule by the user


    public ArrayList<Course> getCourses(){
        return courses;
    }


    /**
     * Constructor
     * //TODO: convert to real copy constructor
     * @param courses Courses to add to the schedule
     * @param title The name of the schedule
     */
    public Schedule(ArrayList<Course> courses, String title) {
        this.courses = courses;
        this.title = title;
    }

    /**
     * Constructor that creates a blank schedule
     */
    public Schedule() {
        this.courses = new ArrayList<Course>();
        this.title = "Untitled";
    }

    /**
     * Creates an empty Schedule that has a name
     * @param title a name for this schedule
     */
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
        for(int i = 0 ; i < courses.size(); i++){
            if(courseToAdd.hasConflict(courses.get(i))){
                return false;
            }
        }
        courses.add(courseToAdd);
        return true;
    }
    public Schedule undo(){
        return null; //TODO: implement
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

    /**
     * Converts a Schedule and its Courses into a String that can be saved in a CSV file and reloaded later
     * @return A String containing all necessary data to reconstruct this schedule
     */
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

    /**
     * Loads a specific student schedule
     * @param scheduleString Line of student's schedule csv, containing title and courses
     * @param db Course Database to read course data
     * @return Specific schedule of a student
     */
    public static Schedule loadSchedule(String scheduleString, CourseDatabase db) {
        String[] parts = scheduleString.split(",");
        Schedule schedule = new Schedule(parts[0].trim());

        //Adds a course to the schedule for every course in the schedule.csv
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
