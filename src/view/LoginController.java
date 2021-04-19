package view;

import DOA.DBCountry;
import DOA.DBUser;
import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Country;
import model.User;
import utility.Session;
import utility.WindowInterface;
import utility.WindowUtility;

/**
 * FXML Controller class
 *
 * @author Adam Sindermann
 */
public class LoginController implements Initializable {

    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField usernameField;
    @FXML
    private Button submit;
    @FXML
    private Label timezoneLabel;
    @FXML
    private Label headerLabel;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label passwordLabel;
    @FXML
    private Label incorrectPassword;
    
    //File Locations
    private final String dashboard = "/view/Dashboard.fxml";
    
    private final WindowInterface loader = location -> {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(location));
        return loader;
    };
    
    


    public void login() throws IOException {
        String userName = usernameField.getText();
        String password = passwordField.getText();

        User user = DBUser.getUser(userName);
        if (user.getUserName().isEmpty()) {
            invalid();
        } else if (!user.getPassword().equals(password)) {
            invalid();
        } else {
            Session.setCurrentUser(user);
            WindowUtility.newWindow(loader.getLoader(dashboard), "Dashboard");
            Stage stage = (Stage) submit.getScene().getWindow();
            stage.close(); 
        }

    }

    public void invalid() {
        if (Locale.getDefault().getLanguage().toString().equals(("fr"))) {
            ResourceBundle frenchRB = ResourceBundle.getBundle("utility/Nat", Locale.getDefault());
            incorrectPassword.setText("*" + frenchRB.getString("Incorrect"));
        } else {
            incorrectPassword.setText("*Incorrect Username or Password");
        }

    }


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        String timezone = Session.getZoneID().toString();

        timezoneLabel.setText("Timezone: " + timezone);
        if (Locale.getDefault().getLanguage().toString().equals("fr")) {
            ResourceBundle frenchRB = ResourceBundle.getBundle("utility/Nat", Locale.getDefault());
            submit.setText(frenchRB.getString("SignIn"));
            usernameLabel.setText(frenchRB.getString("Username") + ":");
            passwordLabel.setText(frenchRB.getString("Password") + ":");
            headerLabel.setText(frenchRB.getString("SignIn"));
            timezoneLabel.setText(frenchRB.getString("Timezone") + ": " + timezone);

        }

    }

}
