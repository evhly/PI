import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class CredentialDB {

    public static final int SUCCESS = 0;
    public static final int PASSWORDS_DONT_MATCH = 1;
    public static final int EMAIL_ALREADY_USED = 2;

    private ArrayList<Credentials> allCredentials;

    private CredentialDB() {
        allCredentials = Credentials.loadAllCredentials(new File("credentials.csv"));
    }

    public int newAccount(Credentials userCredentials, String confirmPw) throws FileNotFoundException {

        if(!checkEmail(userCredentials, confirmPw)){
            return EMAIL_ALREADY_USED;
        }else if(!checkPassword(userCredentials, confirmPw)){
            return PASSWORDS_DONT_MATCH;
        }
        allCredentials.add(userCredentials);
        Credentials.saveAllCredentials(allCredentials, new File("credentials.csv"));
        return SUCCESS;
    }

    public Boolean checkEmail(Credentials userCredentials, String confirmPw){
        for(Credentials credentials : allCredentials){
            if(credentials.getEmail().equals(userCredentials.getEmail())){
                return false;
            }
        }
        return true;
    }

    public Boolean checkPassword(Credentials userCredentials, String confirmPw){
        return userCredentials.getPassword().equals(confirmPw);
    }


    private static CredentialDB instance;
    public static CredentialDB getInstance() {
        if(instance == null) {
            instance = new CredentialDB();
        }
        return instance;
    }

}
