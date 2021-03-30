package view;

import DBAccess.DBCountry;
import java.net.URL;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.Country;

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
            

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        timezoneLabel.setText("Timezone: " + ZoneId.systemDefault().toString());
        if(Locale.getDefault().getLanguage().toString().equals("fr")){
            
        }
        

    }


}
