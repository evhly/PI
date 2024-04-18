import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class Schedule {

    private ArrayList<Course> courses; // the courses contained in a Schedule
    private String title; // the title given to the Schedule by the user

    private PrintWriter logFile;

    private boolean loggingEnabled;

    private Deque<ScheduleChange> changeStack;

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
        this.changeStack = new ArrayDeque<>();
        createLogFile();
    }

    /**
     * Constructor that creates a blank schedule
     */
    public Schedule() {
        this.courses = new ArrayList<Course>();
        this.title = "Untitled";
        this.changeStack = new ArrayDeque<>();
        createLogFile();
    }

    /**
     * Creates an empty Schedule that has a name
     * @param title a name for this schedule
     */
    public Schedule(String title) {
        this.courses = new ArrayList<>();
        this.title = title;
        this.changeStack = new ArrayDeque<>();
        createLogFile();
    }

    private void createLogFile(){
        try {
            logFile = new PrintWriter(title + "Log.txt");
            loggingEnabled = true;
        } catch (FileNotFoundException e) {
            System.out.println("log file unable to be created.");
            loggingEnabled = false;
        }
    }


    /**
     * Removes course from schedule
     * @param courseToDelete Course to remove from schedule
     */
    public void deleteCourse(Course courseToDelete){
        courses.remove(courseToDelete);
        changeStack.push(new ScheduleChange("DELETE", courseToDelete));
        //logging
        if (loggingEnabled){
            logFile.println("DELETE: "+courseToDelete);
            logFile.flush();
            //logFile.close();
        }
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
        changeStack.push(new ScheduleChange("ADD", courseToAdd));
        //logging
        if (loggingEnabled){
            logFile.println("ADD: "+courseToAdd);
            logFile.flush();
        }
        return true;
    }

    /**
     * Adds the most recently deleted class or deletes the most recently added class
     * NOTE: Does NOT put this change onto the changeStack
     * @return true if the undo is successful, false if it fails
     */
    public boolean undo(){
        if (!changeStack.isEmpty()) {
            ScheduleChange lastChange = changeStack.pop();
            if (lastChange.action.equals("ADD")) {
                courses.remove(lastChange.course);
                //logging
                if (loggingEnabled){
                    logFile.println("DELETE: "+lastChange.course);
                    logFile.flush();
                    //logFile.close();
                }
                return true;
            } else {
                for(int i = 0 ; i < courses.size(); i++){
                    if(lastChange.course.hasConflict(courses.get(i))){
                        return false;
                    }
                }
                courses.add(lastChange.course);
                //logging
                if (loggingEnabled){
                    logFile.println("ADD: "+lastChange.course);
                    logFile.flush();
                }
                return true;
            }
        }
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

    private class ScheduleChange {
        private final String action;
        private final Course course;

        private ScheduleChange(String action, Course course) {
            this.action = action;
            this.course = course;
        }
    }

}
