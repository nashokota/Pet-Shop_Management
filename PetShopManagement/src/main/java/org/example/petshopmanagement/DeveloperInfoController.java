package org.example.petshopmanagement;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DeveloperInfoController {

    @FXML
    private Label name;

    @FXML
    private Label email;

    @FXML
    private Label mobile;

    @FXML
    private Label school;

    @FXML
    private Label college;

    @FXML
    private Label universilty;

    @FXML
    private Label address;

    @FXML
    private Button close;

    @FXML
    private void initialize() {
        fetchDeveloperInfo();
    }

    private void fetchDeveloperInfo() {
        String apiUrl = "https://api.myjson.online/v1/records/e8dfca89-1d74-4070-87e6-ca0eaf2dccd0";

        try {
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");

            if (connection.getResponseCode() == 200) {
                InputStreamReader reader = new InputStreamReader(connection.getInputStream());
                Gson gson = new Gson();
                JsonObject responseJson = gson.fromJson(reader, JsonObject.class);
                reader.close();

                JsonObject data = responseJson.getAsJsonObject("data");

                // Set label values
                name.setText(data.get("Name").getAsString());
                email.setText(data.get("Email").getAsString());
                mobile.setText(data.get("Mobile").getAsString());
                school.setText(data.get("School").getAsString());
                college.setText(data.get("College").getAsString());
                universilty.setText(data.get("University").getAsString());
                address.setText(data.get("Address").getAsString());

            } else {
                System.err.println("Failed to fetch data. HTTP Code: " + connection.getResponseCode());
            }

            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void closeWindow(ActionEvent event) {
        // Add logic to close the current stage if necessary
        close.getScene().getWindow().hide();
    }
}
