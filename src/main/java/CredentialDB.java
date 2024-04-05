import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class CredentialDB {

    //Different options of status for creating new account
    public static final int SUCCESS = 0;
    public static final int PASSWORDS_DONT_MATCH = 1;
    public static final int EMAIL_ALREADY_USED = 2;
    public static final int FIELD_BLANK = 3;

    private static ArrayList<Credentials> allCredentials;

    /**
     * Creates new credentials.csv file if it doesn't exist and reads
     * all current credentials in credentials.csv
     */
    private CredentialDB() {
        File credentialsFile = new File("credentials.csv");
        if(!credentialsFile.exists()) {
            try {
                credentialsFile.createNewFile();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        allCredentials = Credentials.loadAllCredentials(credentialsFile);
    }

    /**
     * Creates new account if email is unique and if password
     * and confirm password fields match
     * @param userCredentials Credentials user input on new account page
     * @param confirmPw Password from confirm password field on new account page
     * @return Success if new account was created and an error if new account
     * could not be created
     */
    public int newAccount(Credentials userCredentials, String confirmPw) {
        if(!isEmailUnique(userCredentials, confirmPw)){
            return EMAIL_ALREADY_USED;
        }else if(!userCredentials.getPassword().equals(confirmPw)){
            return PASSWORDS_DONT_MATCH;
        }else if(!isFieldBlank(userCredentials)){
            return FIELD_BLANK;
        }
        allCredentials.add(userCredentials);
        Credentials.saveAllCredentials(allCredentials, new File("credentials.csv"));
        return SUCCESS;
    }

    /**
     * Reads credentials.csv to see if email already exists
     * @param userCredentials Credentials to compare input email
     *                        and all email in credentials.csv
     * @param confirmPw Password from confirmPassword field on new account page
     * @return True if email is unique, false if email is already in credentials.csv
     */
    private Boolean isEmailUnique(Credentials userCredentials, String confirmPw){
        for(Credentials credentials : allCredentials){
            if(credentials.getEmail().equals(userCredentials.getEmail())){
                return false;
            }
        }
        return true;
    }

    private Boolean isFieldBlank(Credentials userCredentials){
        return !userCredentials.getFirstName().isBlank() &&
                !userCredentials.getLastName().isBlank() &&
                !userCredentials.getEmail().isBlank() &&
                !userCredentials.getPassword().isBlank();
    }


    /**
     * Checks if email and username match a student credentials in credentials.csv
     * @param email Email input from login page
     * @param password Password input from login page
     * @return Student if login was successful, null if login was not successful
     */
    public Student loginSuccessful(String email, String password){
        for(Credentials credential : allCredentials){
            if(credential.login(email, password)){
                return new Student(credential);
            }
        }
        return null;
    }

    /**
     * Mimics auto-incrementing ids for each new created student
     * @return Id for next student that is created
     */
    public int findNextId(){
        int max = 0;
        for(Credentials credential : allCredentials){
            if (credential.getId() > max){
                max = credential.getId();
            }
        }
        return max + 1;
    }


    //Makes singleton
    private static CredentialDB instance;
    public static CredentialDB getInstance() {
        if(instance == null) {
            instance = new CredentialDB();
        }
        return instance;
    }

}
