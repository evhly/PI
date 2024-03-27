import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Main {
    private Enum currentPage;
    private App frame;
    private Search search;
    private Student student;
    private Schedule currSchedule;

    public static void main(String[] args) throws FileNotFoundException {
        App app = App.getInstance();
        app.switchPages("login-page");
    }
}
