import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;

public class Frame extends JFrame {

    Frame(){
        PageManager pm = new PageManager();
        pm.switchPages("login-page");
        LoginPage loginPage = new LoginPage(pm);
        this.add(loginPage);
        this.setTitle("Scheduling App");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

}
