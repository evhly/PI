import javax.swing.*;
import java.awt.*;

public abstract class Page extends DynamicComponent {

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    public Page(){
        setMinimumSize(screenSize);
        setMaximumSize(screenSize);
        setPreferredSize(screenSize);
        setBackground(Color.white);
        setFocusable(true);

    }

}
