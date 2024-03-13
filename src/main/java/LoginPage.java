import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LoginPage extends Page {
    private Credentials credentials;
    public void draw(Graphics g){

    }

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    double width = screenSize.getWidth();
    double height = screenSize.getHeight();

    JTextField emailField;
    JLabel emailFieldLabel;
    JTextField passwordField;
    JLabel passwordFieldLabel;
    JButton submitBtn;


    public LoginPage(){

        JPanel panel = new JPanel(new GridBagLayout());

        panel.setPreferredSize(new Dimension(screenSize));
        panel.setBackground(Color.white);
        panel.setFocusable(true);

        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5, 5, 5);

        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        emailField = new JTextField(30);
        emailFieldLabel = new JLabel("Email Address");

        panel.add(emailFieldLabel, gbc);
        panel.add(emailField, gbc);

        passwordField = new JTextField(30);
        passwordFieldLabel = new JLabel("Password");

        panel.add(passwordFieldLabel, gbc);
        panel.add(passwordField, gbc);

        submitBtn = new JButton("Submit");
        panel.add(submitBtn, gbc);

        add(panel, gbc);
    }
}
