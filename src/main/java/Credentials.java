public class Credentials {

    private String name;
    private int id;
    private String major;
    private String password;
    private String email;
    public Credentials(
            String newName,
            int newId,
            String newMajor,
            String newPassword,
            String newEmail
    ){}
    public void login(String attemptEmail, String attemptPassword){}
    public Boolean checkValid(String password){
        return false;
    }

}
