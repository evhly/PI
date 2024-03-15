import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPage extends Page {
    private Credentials credentials;

    JTextField emailField;
    JLabel emailFieldLabel;
    JTextField passwordField;
    JLabel passwordFieldLabel;
    JButton submitBtn;

    public LoginPage(App app) {
        super();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5, 5, 5);
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        setLayout(new GridBagLayout());

        emailField = new JTextField(30);
        emailFieldLabel = new JLabel("Email Address");

        add(emailFieldLabel, gbc);
        add(emailField, gbc);

        passwordField = new JTextField(30);
        passwordFieldLabel = new JLabel("Password");

        add(passwordFieldLabel, gbc);
        add(passwordField, gbc);

        submitBtn = new JButton("Submit");

        submitBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                app.switchPages("new-account-page");
            }
        } );

        add(submitBtn, gbc);
    }

}
