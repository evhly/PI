public class Main {

    public static void main(String[] args) {
        int verbosity = 0; // change this as needed
        App app = App.getInstance(verbosity);
        app.switchPages("login-page");
    }
}
