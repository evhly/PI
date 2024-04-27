import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class Credentials {

    private String firstName; // Given name of the associated Student
    private String lastName;  // Family name of the associated Student
    private int id;           // Grove City College ID number of the associated Student
    private String major;     // Primary major of the associated Student //TODO: handle multiple majors
    private String passwordHash;  // Hash of user password, hashed using SHA-512
    private String email;     // Email address of the associated Student (GCC email)

    /**
     * Constructor
     * @param newFirst
     * @param newLast
     * @param newId
     * @param newMajor
     * @param newPassword
     * @param newEmail
     * @param passwordIsHashed true if passwordIsHashed is the hashed password,
     *                         false if passwordIsHashed is the raw input from the user
     */
    public Credentials(
            String newFirst,
            String newLast,
            int newId,
            String newMajor,
            String newPassword,
            String newEmail,
            Boolean passwordIsHashed
    ){
        firstName =newFirst;
        lastName = newLast;
        id = newId;
        major = newMajor;
        if(passwordIsHashed){
            passwordHash = newPassword;
        } else {
            passwordHash = hashRawPassword(newPassword);
        }
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

    /**
     * Converts one line of a csv into a credentials object
     * @param credential One line of the credential csv
     * @return Credential object created by the read in string
     */
    public static Credentials fromCSV(String credential){
        String[] cols = credential.split(",");
        Credentials credentials = new Credentials(
                cols[0].trim(),
                cols[1].trim(),
                parseInt(cols[2].trim()),
                cols[3].trim(),
                cols[4].trim(),
                cols[5].trim(),
                true);
        return credentials;
    }

    /**
     * Calls fromCSV to convert csv file into a list of Credential objects
     * @param credentialDataFile file that holds all student credentials being stored
     * @return list of all student credentials being stored
     */
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


    /**
     * Converts one credential object into a csv formatted string
     * @return One line of credentials.csv
     */
    public String toCSV(){
        return(String.format("%s,%s,%d,%s,%s,%s",firstName, lastName, id, major, passwordHash, email));
    }

    /**
     * Calls toCSV to convert list of credential object to a credentials CSV file
     * @param allCredentials List of all student credential objects
     * @param file file where csv formatted credential strings are written
     */
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
     * @param rawPassword The password (unhashed) to check
     * @return Whether the hash of rawPassword matches this.passwordHash
     */
    public Boolean checkValid(String rawPassword) {
        return hashRawPassword(rawPassword).equals(this.passwordHash);
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

    public String getPasswordHash() {
        return passwordHash;
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

    public void setPasswordHash(String rawPassword) {
        this.passwordHash = hashRawPassword(rawPassword);
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @param rawPassword plaintext of password to be hashed
     * @return rawPassword hashed using SHA-512
     */
    public String hashRawPassword(String rawPassword){
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(rawPassword.getBytes(StandardCharsets.UTF_8));
            byte[] rawHash = md.digest();
            String hash = byteArrToHex(rawHash);
            System.out.println("Hashing: ");
            System.out.println(hash);
            return hash;
        }catch(Exception e){
            System.out.println("hashRawPassword exception: " + e);
        }
        return null;
    }

    private String byteArrToHex(byte[] arr){
        StringBuilder str = new StringBuilder();
        for(byte b : arr){
            str.append(Integer.toHexString(0xFF & b));
        }
        return str.toString();
    }
}
