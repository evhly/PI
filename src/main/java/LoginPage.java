import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class LoginPage extends Page {
    private Credentials credentials;

    JTextField emailField;
    JLabel emailFieldLabel;
    JTextField passwordField;
    JLabel passwordFieldLabel;
    JButton loginBtn;
    JButton newAccountBtn;
    CredentialDB credDb = CredentialDB.getInstance();

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

        loginBtn = new JButton("LOGIN");

        loginBtn.addActionListener((event) -> {
            String userEmail = emailField.getText();
            String userPassword = passwordField.getText();
            credentials = credDb.loginSuccessful(userEmail, userPassword);
            System.out.println(credentials.getFirstName());
            if(credentials != null){
                try {
                    ((ChooseSchedulePage)app.getPage("choose-schedule-page")).loadStudentSchedules(credentials);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                app.switchPages("choose-schedule-page");
            }else{
                //Error message
                app.switchPages("login-page");
            }
        });

        add(loginBtn, gbc);

        newAccountBtn = new JButton("Create New Account");
        newAccountBtn.addActionListener((event ->{
            app.switchPages("new-account-page");
        }));

        add(newAccountBtn);

    }

    public void clearTextBoxes(){
        emailField.setText("");
        passwordField.setText("");
    }

}
