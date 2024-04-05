import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.HashMap;
import java.util.Set;

public class App extends JFrame {

    //App state variables
    private HashMap<String, Page> pages = new HashMap<>();
    private Page currentPage;
    private Student loggedInStudent;
    private CourseDatabase courseDatabase;
    private Schedule currSchedule;
    private CourseReader courseReader;


    private App(){
        pages.put("login-page", new LoginPage());
        pages.put("new-account-page", new NewAccountPage());
        pages.put("choose-schedule-page", new ChooseSchedulePage());
        pages.put("schedule-page", new SchedulePage());
        courseReader = CourseReader.getAllCourseDatabases();
        setCourseDatabase(courseReader.getCourseDatabase("F20"));
        this.setTitle("Scheduling App");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    /**
     * Switches between pages
     * @param pageName Name of page which maps to a Page class
     */
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

    public CourseReader getCourseReader(){
        return courseReader;
    }

    //Makes App singleton
    private static App instance;
    public static App getInstance() {
        if(instance == null) {
            instance = new App();
        }
        return instance;
    }

}
