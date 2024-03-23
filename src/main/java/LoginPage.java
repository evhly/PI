import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

public class LoginPage extends Page {
    private Credentials credentials;

    JTextField emailField;
    JLabel emailFieldLabel;
    JTextField passwordField;
    JLabel passwordFieldLabel;
    JButton loginBtn;
    JButton newAccountBtn;

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
            ArrayList<Credentials> credentials1 = Credentials.loadAllCredentials(new File("credentials.csv"));
            for(Credentials credential : credentials1){
                if(credential.login(userEmail, userPassword)){
                    //TODO: Actually load student's saved schedules!
                    ((ChooseSchedulePage)app.getPage("choose-schedule-page")).loadStudentSchedules(
                        new Student(credential)
                    );
                    app.switchPages("choose-schedule-page");
                }
            }
        });

        add(loginBtn, gbc);

        newAccountBtn = new JButton("Create New Account");
        newAccountBtn.addActionListener((event ->{
            app.switchPages("new-account-page");
        }));

        add(newAccountBtn);

    }
}
