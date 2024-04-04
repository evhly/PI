import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.HashMap;
import java.util.Set;

public class App extends JFrame {

    private HashMap<String, Page> pages = new HashMap<>();
    private Page currentPage;
    private Student loggedInStudent;
    private CourseDatabase courseDatabase;
    private Schedule currSchedule;


    private App(){
        pages.put("login-page", new LoginPage());
        pages.put("new-account-page", new NewAccountPage());
        pages.put("choose-schedule-page", new ChooseSchedulePage());
        pages.put("schedule-page", new SchedulePage());
        setCourseDatabase(CourseReader.getAllCourseDatabases().getCourseDatabase("F20"));
        this.setTitle("Scheduling App");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    public void switchPages(String pageName){
        if(currentPage != null) {
            remove(currentPage);
        }
        currentPage = pages.get(pageName);
        currentPage.redraw();
        add(currentPage);
        pack();
        repaint();
    }

    public Student getLoggedInStudent() {
        return loggedInStudent;
    }
    public void setLoggedInStudent(Student student){
        this.loggedInStudent = student;
    }

    public CourseDatabase getCourseDatabase() {
        return courseDatabase;
    }
    public void setCourseDatabase(CourseDatabase courseDatabase){
        this.courseDatabase = courseDatabase;
    }
    public Schedule getCurrSchedule(){
        return currSchedule;
    }
    public void setCurrSchedule(Schedule currSchedule){
        this.currSchedule = currSchedule;
    }

    private static App instance;
    public static App getInstance() {
        if(instance == null) {
            instance = new App();
        }
        return instance;
    }

}
