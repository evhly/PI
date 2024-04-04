import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class NewAccountPage extends Page {

    public void draw() {
        App app = App.getInstance();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5, 5, 5);
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        setLayout(new GridBagLayout());

        JLabel firstNameFieldLabel = new JLabel("First Name");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        add(firstNameFieldLabel, gbc);

        JTextField firstNameField = new JTextField(15);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        add(firstNameField, gbc);


        JLabel lastNameFieldLabel = new JLabel("Last Name");
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        add(lastNameFieldLabel, gbc);

        JTextField lastNameField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        add(lastNameField, gbc);

        JLabel emailFieldLabel = new JLabel("Email Address");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        add(emailFieldLabel, gbc);

        JTextField emailField = new JTextField(30);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        add(emailField, gbc);

        JLabel passwordFieldLabel = new JLabel("Password");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        add(passwordFieldLabel, gbc);

        JPasswordField passwordField = new JPasswordField(30);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        add(passwordField, gbc);

        JLabel confirmPasswordFieldLabel = new JLabel("Confirm Password");
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        add(confirmPasswordFieldLabel, gbc);

        JPasswordField confirmPasswordField = new JPasswordField(30);
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        add(confirmPasswordField, gbc);

        JLabel majorComboBoxLabel = new JLabel("Major");
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

        JComboBox<String >majorComboBox = new JComboBox<>(choices);
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        add(majorComboBox, gbc);

        JButton submitBtn = new JButton("Submit");
        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;

        JLabel accountStatusLabel = new JLabel();
        submitBtn.addActionListener((event) -> {
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String email = emailField.getText();
            String password = passwordField.getText();
            String confirmPassword = confirmPasswordField.getText();
            String major = (String) majorComboBox.getSelectedItem();

            CredentialDB credDb = CredentialDB.getInstance();
            if(firstName.contains(",") || lastName.contains(",") || password.contains(",") || email.contains(",")){
                accountStatusLabel.setText("Inputs cannot contain commas");
                return;
            }

            Credentials studentCredentials = new Credentials(
                firstName, lastName, credDb.findNextId(), major,
                password, email
            );

            int result = credDb.newAccount(studentCredentials, confirmPassword);

            switch(result) {
                case CredentialDB.PASSWORDS_DONT_MATCH -> {
                    accountStatusLabel.setText("Passwords don't match");
                }
                case CredentialDB.EMAIL_ALREADY_USED -> {
                    accountStatusLabel.setText("Account exists with that email. Try logging in");
                }
                case CredentialDB.SUCCESS -> {
                    //accountStatusLabel.setText("Account created! Please log in");
                    Student student = new Student(studentCredentials);
                    student.save();
                    app.switchPages("login-page");
                }
            }

        });
        add(accountStatusLabel);
        add(submitBtn, gbc);

        gbc.gridx = 0;
        gbc.gridy = 11;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        JButton backToLoginBtn = new JButton("Back to Log In Screen");
        backToLoginBtn.addActionListener((event) -> {
            app.switchPages("login-page");
        });
        add(backToLoginBtn, gbc);
    }

}


