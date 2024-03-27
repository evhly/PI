import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class CredentialDB {

    public static final int SUCCESS = 0;
    public static final int PASSWORDS_DONT_MATCH = 1;
    public static final int EMAIL_ALREADY_USED = 2;

    private static ArrayList<Credentials> allCredentials;

    private CredentialDB() {
        allCredentials = Credentials.loadAllCredentials(new File("credentials.csv"));
    }

    public int newAccount(Credentials userCredentials, String confirmPw) throws FileNotFoundException {
        if(!isEmailUnique(userCredentials, confirmPw)){
            return EMAIL_ALREADY_USED;
        }else if(!userCredentials.getPassword().equals(confirmPw)){
            return PASSWORDS_DONT_MATCH;
        }
        allCredentials.add(userCredentials);
        Credentials.saveAllCredentials(allCredentials, new File("credentials.csv"));
        return SUCCESS;
    }

    private Boolean isEmailUnique(Credentials userCredentials, String confirmPw){
        for(Credentials credentials : allCredentials){
            if(credentials.getEmail().equals(userCredentials.getEmail())){
                return false;
            }
        }
        return true;
    }

    public Student loginSuccessful(String email, String password){
        for(Credentials credential : allCredentials){
            if(credential.login(email, password)){
                return credential.getAssociatedStudent();
            }
        }
        return null;
    }

    public int findNextId(){
        int max = 0;
        for(Credentials credential : allCredentials){
            if (credential.getId() > max){
                max = credential.getId();
            }
        }
        return max + 1;
    }


    private static CredentialDB instance;
    public static CredentialDB getInstance() {
        if(instance == null) {
            instance = new CredentialDB();
        }
        return instance;
    }

}
