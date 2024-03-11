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
    public void login(String attemptEmail, String attemptPassword){
    }

    public Boolean checkValid(String password) {
        return password.equals(this.password);
    }

}
