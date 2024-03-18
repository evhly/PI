import java.util.Objects;

public class Credentials {

    private String firstName;
    private String lastName;
    private int id;
    private String major;
    private String password;
    private String email;

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

    //TODO: set up with user interface
    public String login(String attemptEmail, String attemptPassword){
        if (attemptEmail.equals(email) && checkValid(attemptPassword)) {
            return "Good";
        } else {
            return "Sus";
        }
    }

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

    public void setName(String name) {
        this.name = name;
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
