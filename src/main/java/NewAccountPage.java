import javax.swing.*;
import java.awt.*;

public class NewAccountPage extends Page {

    private Credentials credentials;
    public NewAccountPage(PageManager pm){
        super.draw();
        JLabel label = new JLabel("NEW ACCOUNT PAGE!");
        panel.add(label);
    }

}
