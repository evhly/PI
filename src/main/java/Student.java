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
        information.setFirstName(newCreds.getFirstName());
        information.setLastName(newCreds.getLastName());
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

    /**
     * Prints schedules to a file in a format (csv) that can be read back into the program
     * @throws IOException File cannot be created or written to
     */
    public void saveSchedules() throws IOException {
        PrintWriter fout = new PrintWriter(information.getId() + "_savedSchedules.csv");
        StringBuilder sb = new StringBuilder();
        for (Schedule schedule : schedules) {
            sb.append(schedule.toSave());
            sb.append("\n");
        }
        fout.print(sb);
        fout.flush();
        fout.close();
    }



}
