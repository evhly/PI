import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class Credentials {

    private String firstName; // Given name of the associated Student
    private String lastName;  // Family name of the associated Student
    private int id;           // Grove City College ID number of the associated Student
    private String major;     // Primary major of the associated Student //TODO: handle multiple majors
    private String password;  // Don't look too closely at this field
    private String email;     // Email address of the associated Student (GCC email)

    /**
     * Constructor
     * @param newFirst
     * @param newLast
     * @param newId
     * @param newMajor
     * @param newPassword
     * @param newEmail
     */
    public Credentials(
            String newFirst,
            String newLast,
            int newId,
            String newMajor,
            String newPassword,
            String newEmail
    ){
        firstName =newFirst;
        lastName = newLast;
        id = newId;
        major = newMajor;
        password = newPassword;
        email = newEmail;
    }

    /**
     * Determines whether an email address and password are correct for a user trying to log in
     * @param attemptEmail The email address to check
     * @param attemptPassword The password (plain text) to check
     * @return Whether the email address and password are correct
     */
    public Boolean login(String attemptEmail, String attemptPassword){
         return attemptEmail.equals(email) && checkValid(attemptPassword);
    }

    public static Credentials fromCSV(String credential){
        String[] cols = credential.split(",");
        Credentials credentials = new Credentials(
                cols[0].trim(),
                cols[1].trim(),
                parseInt(cols[2].trim()),
                cols[3].trim(),
                cols[4].trim(),
                cols[5].trim());
        return credentials;
    }
    public static ArrayList<Credentials> loadAllCredentials(File credentialDataFile) {
        try {
            ArrayList<Credentials> allCredentials = new ArrayList<>();

            Scanner credentialFileScanner = new Scanner(credentialDataFile);
            credentialFileScanner.useDelimiter("\n");
            while(credentialFileScanner.hasNext()){
                String credentials = credentialFileScanner.nextLine();
                allCredentials.add(fromCSV(credentials));
            }
            return allCredentials;
        } catch (IOException e) {
            System.out.println("No credential data file found.");
            return null;
        }
    }

    public String toCSV(){
        return(String.format("%s,%s,%d,%s,%s,%s",firstName, lastName, id, major, password, email));
    }

    public static void saveAllCredentials(ArrayList<Credentials> allCredentials, File file) {
        try {
            PrintWriter pw = new PrintWriter(file);
            StringBuilder sb = new StringBuilder();
            for (Credentials credentials : allCredentials) {
                sb.append(credentials.toCSV());
                sb.append("\n");
            }
            if (sb.length() > 0) {
                sb.deleteCharAt(sb.length() - 1);
            }
            pw.print(sb);
            pw.flush();
            pw.close();
        } catch(Exception e ) {
            e.printStackTrace();
        }
    }

    /**
     * Checks whether the given password is the same as the associated Student's password
     * @param password The password to check
     * @return Whether the password matches this.password
     */
    public Boolean checkValid(String password) {
        return password.equals(this.password);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getId() {
        return id;
    }

    public String getMajor() {
        return major;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setFirstName(String first) {
        this.firstName = first;
    }

    public void setLastName(String last) {
        this.lastName = last;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
