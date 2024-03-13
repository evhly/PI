import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;

public class Frame extends JFrame {

    Frame(){
        LoginPage panel = new LoginPage();
        this.add(panel);
        this.setTitle("Scheduling App");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

}
