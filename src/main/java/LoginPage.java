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


    public LoginPage(PageManager pm){

        super.draw();

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

        submitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pm.switchPages("new-account-page");
            }
        });

        add(panel, gbc);
    }
}
