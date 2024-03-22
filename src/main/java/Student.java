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

    public void changeProfile(Credentials newCreds){
        information.setFirstName(newCreds.getFirstName());
        information.setLastName(newCreds.getLastName());
        information.setId(newCreds.getId());
        information.setMajor(newCreds.getMajor());
        information.setEmail(newCreds.getEmail());
        information.setPassword(newCreds.getPassword());
    }

    public static Credentials readFile(File credentialDataFile) {
        try {
            Scanner credentialFileScanner = new Scanner(credentialDataFile);

            // reads file data
            String first = credentialFileScanner.nextLine();
            String last = credentialFileScanner.nextLine();
            int id = parseInt(credentialFileScanner.nextLine());
            String major = credentialFileScanner.nextLine();
            String password = credentialFileScanner.nextLine();
            String email = credentialFileScanner.nextLine();

            Credentials returnCreds = new Credentials(first, last, id, major, password, email);
            credentialFileScanner.close();
            return returnCreds;
        } catch (IOException e) {
            System.out.println("No credential data file found.");
            return null;
        }
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
    }





}
