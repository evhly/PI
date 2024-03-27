import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class NewAccountPage extends Page {

    private Credentials credentials;

    JTextField firstNameField;
    JLabel firstNameFieldLabel;
    JTextField lastNameField;
    JLabel lastNameFieldLabel;
    JTextField emailField;
    JLabel emailFieldLabel;
    JPasswordField passwordField;
    JLabel passwordFieldLabel;
    JPasswordField confirmPasswordField;
    JLabel confirmPasswordFieldLabel;
    JLabel majorComboBoxLabel;
    JLabel accountStatusLabel;
    JComboBox<String> majorComboBox;
    JButton submitBtn;
    JButton backToLoginBtn;

    public void draw() {
        App app = App.getInstance();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5, 5, 5);
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        setLayout(new GridBagLayout());

        firstNameFieldLabel = new JLabel("First Name");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        add(firstNameFieldLabel, gbc);

        firstNameField = new JTextField(15);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        add(firstNameField, gbc);


        lastNameFieldLabel = new JLabel("Last Name");
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        add(lastNameFieldLabel, gbc);

        lastNameField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        add(lastNameField, gbc);

        emailFieldLabel = new JLabel("Email Address");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        add(emailFieldLabel, gbc);

        emailField = new JTextField(30);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        add(emailField, gbc);

        passwordFieldLabel = new JLabel("Password");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        add(passwordFieldLabel, gbc);

        passwordField = new JPasswordField(30);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        add(passwordField, gbc);

        confirmPasswordFieldLabel = new JLabel("Confirm Password");
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        add(confirmPasswordFieldLabel, gbc);

        confirmPasswordField = new JPasswordField(30);
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        add(confirmPasswordField, gbc);

        majorComboBoxLabel = new JLabel("Major");
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        add(majorComboBoxLabel, gbc);

        String[] choices = {
                "English",
                "Business",
                "Computer Science",
                "Mechanical Engineer",
                "Math",
                "Biology"
        };

        majorComboBox = new JComboBox<String>(choices);
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        add(majorComboBox, gbc);

        submitBtn = new JButton("Submit");
        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;

        submitBtn.addActionListener((event) -> {
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String email = emailField.getText();
            String password = passwordField.getText();
            String confirmPassword = confirmPasswordField.getText();
            String major = (String) majorComboBox.getSelectedItem();


            app.switchPages("login-page");
            CredentialDB credDb = CredentialDB.getInstance();
            Credentials studentCredentials = new Credentials(
                    firstName, lastName, credDb.findNextId(), major,
                    password, email
            );

            Student student = new Student(studentCredentials);
            try {
                Schedule.saveSchedules(student);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


            int result = 0;
            try {
                result = credDb.newAccount(studentCredentials, confirmPassword);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            switch(result) {
                case CredentialDB.PASSWORDS_DONT_MATCH -> {
                    accountStatusLabel.setText("Passwords don't match");
                    app.switchPages("new-account-page");
                }
                case CredentialDB.EMAIL_ALREADY_USED -> {
                    accountStatusLabel.setText("Account exists with that email. Try logging in");
                    app.switchPages("new-account-page");
                }
                case CredentialDB.SUCCESS -> {
                    //accountStatusLabel.setText("Account created! Please log in");
                    app.switchPages("login-page");
                    firstNameField.setText("");
                    lastNameField.setText("");
                    emailField.setText("");
                    passwordField.setText("");
                    confirmPasswordField.setText("");
                }
            }

        });
        accountStatusLabel = new JLabel();
        add(accountStatusLabel);

        add(submitBtn, gbc);



        gbc.gridx = 0;
        gbc.gridy = 11;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        backToLoginBtn = new JButton("Back to Log In Screen");
        add(backToLoginBtn, gbc);
        backToLoginBtn.addActionListener((event) -> {
            app.switchPages("login-page");
        });
    }

}


