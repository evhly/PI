import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

        String[] choices = {
                "English",
                "Business",
                "Computer Science",
                "Mechanical Engineer",
                "Math",
                "Biology"
        };

        JComboBox<String> cb = new JComboBox<String>(choices);
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        add(cb, gbc);

        submitBtn = new JButton("Submit");
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;

        submitBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                app.switchPages("login-page");
            }
        } );

        add(submitBtn, gbc);
    }

}


