import java.util.ArrayList;
import java.io.*;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class Student {

    private ArrayList<Schedule> schedules;

    public Credentials getInformation() {
        return information;
    }

    private Credentials information;

    public void changeProfile(Credentials newCreds){
        information.setName(newCreds.getName());
        information.setId(newCreds.getId());
        information.setMajor(newCreds.getMajor());
        information.setEmail(newCreds.getEmail());
        information.setPassword(newCreds.getPassword());
    }

    public Student(Credentials creds){
        schedules = new ArrayList<Schedule>();
        information = creds;
    }

    public static Credentials readFile(File credentialDataFile) {
        try {
            Scanner credentialFileScanner = new Scanner(credentialDataFile);

            // reads file data
            String name = credentialFileScanner.nextLine();
            int id = parseInt(credentialFileScanner.nextLine());
            String major = credentialFileScanner.nextLine();
            String password = credentialFileScanner.nextLine();
            String email = credentialFileScanner.nextLine();

            Credentials returnCreds = new Credentials(name, id, major, password, email);
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

}
