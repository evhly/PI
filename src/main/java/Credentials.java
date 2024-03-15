import java.util.Objects;

public class Credentials {

    private String name;
    private int id;
    private String major;
    private String password;
    private String email;

    /**
     * Constructor
     * @param newName
     * @param newId
     * @param newMajor
     * @param newPassword
     * @param newEmail
     */
    public Credentials(
            String newName,
            int newId,
            String newMajor,
            String newPassword,
            String newEmail
    ){
        name = newName;
        id = newId;
        major = newMajor;
        password = newPassword;
        email = newEmail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Credentials that = (Credentials) o;
        return id == that.id && Objects.equals(name, that.name) && Objects.equals(major, that.major) && Objects.equals(password, that.password) && Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id, major, password, email);
    }

    //TODO: set up with user interface
    public void login(String attemptEmail, String attemptPassword){
        if (attemptEmail.equals(email) && attemptPassword.equals(password)) {
            //TODO: LOGIN STUFF
        } else {
            //TODO: YELL AT THEM
        }
    }

    public Boolean checkValid(String password) {
        return password.equals(this.password);
    }

    public String getName() {
        return name;
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
