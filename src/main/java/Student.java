import java.util.ArrayList;
import java.io.*;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class Student {

    private ArrayList<Schedule> schedules; // all schedules saved by the Student

    public Credentials getInformation() {
        return information;
    }

    private Credentials information;

    /**
     * Creates a new Student if one does not exist with given credentials, otherwise it reloads the data of an existing student
     * @param creds
     */
    public Student(Credentials creds) {
        information = creds;

        if (getSaveFile().exists()) {
            schedules = Schedule.loadSchedules(
                getSaveFile(),
                App.getInstance().getCourseDatabase()
            );
        } else {
            schedules = new ArrayList<>();
        }
    }

    /**
     * constructor for console
     * @param creds
     * @param db
     */
    public Student(Credentials creds, CourseDatabase db) {
        information = creds;

        if (getSaveFile().exists()) {
            schedules = Schedule.loadSchedules(getSaveFile(),db);
        } else {
            schedules = new ArrayList<>();
        }
    }

    public void changeProfile(Credentials newCreds){
        information.setFirstName(newCreds.getFirstName());
        information.setLastName(newCreds.getLastName());
        information.setId(newCreds.getId());
        information.setMajor(newCreds.getMajor());
        information.setEmail(newCreds.getEmail());
        information.setPassword(newCreds.getPassword());
    }

    /**
     * Returns all schedules associated with the student's account
     * @return an ArrayList of all schedules associated with the student's account
     */
    public ArrayList<Schedule> getSchedules() {
        return schedules;
    }

    /**
     * Adds a schedule to the student's schedules list
     * @param schedule the schedule to add
     */
    public void addSchedule(Schedule schedule) {
        schedules.add(schedule);
        save();
    }

    /**
     * Deletes a schedule to the student's schedules list
     * @param schedule the schedule to delete
     */
    public void deleteSchedule(Schedule schedule) {
        schedules.remove(schedule);
        save();
    }

    /**
     * Saves all schedules of a specific student
     */
    public void save() {
        Schedule.saveSchedules(getSaveFile(), schedules);
    }

    /**
     * Creates a new file for a new student  which holds all of the student's schedules
     * @return New file for that specific student
     */
    private File getSaveFile() {
        return new File(information.getId()+"_savedSchedules.csv");
    }

}
