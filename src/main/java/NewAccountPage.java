import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class NewAccountPage extends Page {

    private Credentials credentials;



    JTextField firstNameField;
    JLabel firstNameFieldLabel;
    JTextField lastNameField;
    JLabel lastNameFieldLabel;
    JTextField emailField;
    JLabel emailFieldLabel;
    JTextField passwordField;
    JLabel passwordFieldLabel;
    JTextField confirmPasswordField;
    JLabel confirmPasswordFieldLabel;
    JLabel majorComboBoxLabel;
    JLabel accountStatusLabel;
    JComboBox<String> majorComboBox;
    JButton submitBtn;

    public NewAccountPage(App app) {
        super();

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

        passwordField = new JTextField(30);
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

        confirmPasswordField = new JTextField(30);
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
            //TODO make newID aut0increment

            app.switchPages("login-page");
            Credentials studentCredentials = new Credentials(
                firstName, lastName, 1, major,
                password, email
            );
            CredentialDB credDb = CredentialDB.getInstance();


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
    }

}


