import java.util.ArrayList;
import java.io.*;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class Student {

    private ArrayList<Schedule> schedules;

    public Credentials getInformation() {
        return information;
    }

    private Credentials information;

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

    public void save() {
        Schedule.saveSchedules(getSaveFile(), schedules);
    }

    private File getSaveFile() {
        return new File(information.getId()+"_savedSchedules.csv");
    }

}
