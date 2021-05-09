package view;

import DOA.DBCountry;
import DOA.DBCustomer;
import DOA.DBDivision;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Country;
import model.Customer;
import model.Division;
import utility.Alerts;
import utility.ComboBoxInterface;

/**
 * FXML Controller class
 *
 * @author AdamS
 */
public class CustomerController implements Initializable {

    private boolean editing = false;
    private Customer thisCustomer;
    private int phoneLength;
    @FXML
    private Label headerLabel;

    //TextBoxes
    @FXML
    private TextField idBox;
    @FXML
    private TextField nameBox;
    @FXML
    private TextField phoneBox;
    @FXML
    private TextField streetBox;
    @FXML
    private TextField aptBox;
    @FXML
    private TextField postalBox;

    //ComboBoxes
    @FXML
    private ComboBox countryBox;
    @FXML
    private ComboBox districtBox;

    //Required Field Labels
    @FXML
    private Label nameStar;
    @FXML
    private Label phoneStar;
    @FXML
    private Label streetStar;
    @FXML
    private Label countryStar;
    @FXML
    private Label districtStar;
    @FXML
    private Label postalStar;
    @FXML
    private Label reqFieldStar;
    @FXML
    private Label reqFieldLabel;

    private final ComboBoxInterface combos = (ComboBox comboBox) -> {
        if (!comboBox.getSelectionModel().isEmpty()) {
            return true;
        } else {
            return false;
        }
    };

    /**
     * Populates the country combo box with all countries.
     */
    public void populateCountries() {
        ObservableList<Country> allCountries = DBCountry.getAllCountries();
        ObservableList<String> countryNames = FXCollections.observableArrayList();
        countryNames.add("United States");
        countryNames.add("Canada");
        for (Country country : allCountries) {
            if (!country.getName().equals("United States") && !country.getName().equals("Canada")) {
                countryNames.add(country.getName());
            }
        }
        countryBox.setItems(countryNames);
    }

    /**
     * Populates the district box with all districts in the selected country.
     * Runs when a country is selected. Also assigns the country to the current
     * customer object.
     */
    public void populateDistricts() {
        if (combos.comboPopulated(countryBox)) {
            ObservableList<String> divisionNames = FXCollections.observableArrayList();
            String countryName = (String) countryBox.getSelectionModel().getSelectedItem();
            Country country = DBCountry.getCountry(countryName);
            thisCustomer.setCountry(countryName);
            ObservableList<Division> allDivisions = DBDivision.getAllDivisions(country.getId());

            for (Division division : allDivisions) {

                divisionNames.add(division.getName());

            }
            districtBox.setItems(divisionNames);
        }
    }

    public void save() {
        if (requiredFieldsFilled()) {
            String name = nameBox.getText();
            String address;
            if (!aptBox.getText().isEmpty()) {
                address = streetBox.getText() + ", " + aptBox.getText();
            } else {
                address = streetBox.getText();
            }
            String phone = phoneBox.getText();
            int divisionID = 0;
            if (combos.comboPopulated(districtBox)) {
                String divisionName = districtBox.getSelectionModel().getSelectedItem().toString();
                divisionID = DBDivision.getDivision(divisionName).getDivisionID();
            }
            String postal = postalBox.getText();
            thisCustomer.setName(name);
            thisCustomer.setAddress(address);
            thisCustomer.setPhone(phone);
            thisCustomer.setDivisionID(divisionID);
            thisCustomer.setPostalCode(postal);
            if (editing) {
                boolean saved = DBCustomer.update(thisCustomer);
            } else {
                boolean saved = DBCustomer.save(thisCustomer);
            }
            Stage stage = (Stage) postalBox.getScene().getWindow();
            stage.close();

        } else {
            Alerts.displayInputAlert("Please enter all required fields");
        }
    }

    /**
     * Checks that all required input fields are filled. If all fields are not
     * filled, sets red stars visible and displays an alert.
     *
     * @return Boolean - True if all fields are filled
     */
    public boolean requiredFieldsFilled() {
        boolean allEntered = true;
        if (nameBox.getText().isEmpty()) {
            allEntered = false;
        }
        if (phoneBox.getText().isEmpty()) {
            allEntered = false;
        }
        if (streetBox.getText().isEmpty()) {
            allEntered = false;
        }
        if (postalBox.getText().isEmpty()) {
            allEntered = false;
        }
        if (!combos.comboPopulated(countryBox)) {
            allEntered = false;
        }

        if (!allEntered) {
            reqFieldStar.setVisible(true);
            reqFieldLabel.setVisible(true);
            nameStar.setVisible(true);
            phoneStar.setVisible(true);
            streetStar.setVisible(true);
            postalStar.setVisible(true);
            countryStar.setVisible(true);

        }
        return allEntered;
    }

    /**
     * Adds dashes to phone number as it is entered and makes it so the field
     * only accepts numbers.
     */
    public void phoneNumberFormat() {
        phoneLength++;
        if (phoneBox.getText().length() != 4 && phoneBox.getText().length() != 8 && !phoneBox.getText().isEmpty()) {
            if (!Character.isDigit(phoneBox.getText().charAt(phoneBox.getText().length() - 1))) {

                String s = phoneBox.getText().substring(0, phoneBox.getText().length() - 1);
                phoneBox.setText(s);
            }
        }

        if (phoneBox.getText().length() == 2 && phoneLength > 2) {
            phoneLength = 2;
        }
        if (phoneBox.getText().length() == 3 && phoneBox.getText().length() >= phoneLength) {
            String s = phoneBox.getText() + "-";
            phoneBox.setText(s);
        }
        if (phoneBox.getText().length() == 6 && phoneLength > 6) {
            phoneLength = 2;
        }

        if (phoneBox.getText().length() == 7 && phoneBox.getText().length() >= phoneLength) {
            String s = phoneBox.getText() + "-";
            phoneBox.setText(s);
        }
        if (phoneBox.getText().length() > 12) {
            String s = phoneBox.getText().substring(0, 12);
            phoneBox.setText(s);
        }
    }

    public void editCustomer(Customer customer) {
        editing = true;
        headerLabel.setText("Edit Customer");
        thisCustomer = customer;
        idBox.setText(Integer.toString(thisCustomer.getCustID()));
        nameBox.setText(thisCustomer.getName());
        phoneBox.setText(thisCustomer.getPhone());
        String address = thisCustomer.getAddress();
        if (address.contains(",")) {
            String street = address.substring(0, address.indexOf(","));
            String apt = address.substring(address.indexOf(",") + 2);
            streetBox.setText(street);
            aptBox.setText(apt);
        } else {
            streetBox.setText(address);
        }
        countryBox.getSelectionModel().select(thisCustomer.getCountry());
        populateDistricts();
        if (!districtBox.getItems().isEmpty()) {
            String distName = DBDivision.getDivision(thisCustomer.getDivisionID()).getName();
            districtBox.getSelectionModel().select(distName);
        }
        postalBox.setText(thisCustomer.getPostalCode());

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        populateCountries();
        if (!editing) {
            int customerID = (DBCustomer.getAllCustomers().get(DBCustomer.getAllCustomers().size() - 1).getCustID() + 1);
            thisCustomer = new Customer(customerID);
            idBox.setText(Integer.toString(customerID));
        }

        phoneBox.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                    String newValue) {

                phoneNumberFormat();
            }

        });
    }

}
