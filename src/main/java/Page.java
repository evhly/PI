import javax.swing.*;
import java.awt.*;

public class Page extends JPanel {

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    double width = screenSize.getWidth();
    double height = screenSize.getHeight();

    JPanel panel = new JPanel(new GridBagLayout());

    GridBagConstraints gbc = new GridBagConstraints();

    public void draw(){


        panel.setPreferredSize(new Dimension(screenSize));
        panel.setBackground(Color.white);
        panel.setFocusable(true);

        panel.setLayout(new GridBagLayout());


        gbc.insets = new Insets(5,5, 5, 5);

        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
    }

}
