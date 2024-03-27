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
    JPasswordField passwordField;
    JLabel passwordFieldLabel;
    JButton loginBtn;
    JButton newAccountBtn;
    CredentialDB credDb = CredentialDB.getInstance();

    public void draw() {
        App app = App.getInstance();

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

        passwordField = new JPasswordField(30);
        passwordFieldLabel = new JLabel("Password");

        add(passwordFieldLabel, gbc);
        add(passwordField, gbc);

        loginBtn = new JButton("LOGIN");

        loginBtn.addActionListener((event) -> {
            String userEmail = emailField.getText();
            String userPassword = passwordField.getText();
            Student student = credDb.loginSuccessful(userEmail, userPassword);
            if(student != null){
                clearTextBoxes();
                app.setLoggedInStudent(student);
                app.switchPages("choose-schedule-page");
            }else{
                //Error message
            }
        });

        add(loginBtn, gbc);

        newAccountBtn = new JButton("Create New Account");
        newAccountBtn.addActionListener((event) ->{
            clearTextBoxes();
            app.switchPages("new-account-page");
        });

        add(newAccountBtn);
    }

    public void clearTextBoxes(){
        emailField.setText("");
        passwordField.setText("");
    }

}
