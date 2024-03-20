import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class App extends JFrame {

    private HashMap<String, Page> pages = new HashMap<>();
    private Page currentPage;

    public App(){
        pages.put("login-page", new LoginPage(this));
        pages.put("new-account-page", new NewAccountPage(this));
        pages.put("choose-schedule-page", new ChooseSchedulePage(this));
        pages.put("schedule-page", new SchedulePage(this));

        switchPages("choose-schedule-page");

        this.setTitle("Scheduling App");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
        this.pack();
        this.setLocationRelativeTo(null);
    }

    public void switchPages(String pageName){
        System.out.println(pageName);
        Page page = pages.get(pageName);
        if(currentPage != null) {
            this.remove(currentPage);
        }
        this.add(page);
        this.pack();
        this.repaint();
        this.currentPage = page;
        System.out.println("Done");
    }

}
