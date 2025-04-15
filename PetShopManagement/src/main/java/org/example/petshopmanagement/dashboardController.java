package org.example.petshopmanagement;


import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javafx.event.ActionEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

public class dashboardController implements Initializable {
    @FXML
    private AnchorPane main_form;

    @FXML
    private Button addPets_add;

    @FXML
    private TextField addPets_price;

    @FXML
    private TextField addPets_age;

    @FXML
    private TextField addPets_breed;

    @FXML
    private Button addPets_btn;

    @FXML
    private Button addPets_clear;

    @FXML
    private TableColumn<petData, String> addPets_col_age;

    @FXML
    private TableColumn<petData, String> addPets_col_price;

    @FXML
    private TableColumn<petData, String> addPets_col_breed;

    @FXML
    private TableColumn<petData, String> addPets_col_petId;

    @FXML
    private TableColumn<petData, String> addPets_col_sex;

    @FXML
    private Button addPets_delete;

    @FXML
    private AnchorPane addPets_form;

    @FXML
    private TextField addPets_petId;

    @FXML
    private TextField addPets_search;

    @FXML
    private ComboBox<?> addPets_sex;

    @FXML
    private TableView<petData> addPets_tableView;

    @FXML
    private Button addPets_update;

    @FXML
    private Button close;

    @FXML
    private Label home_availablePets;

    @FXML
    private Button home_btn;

    @FXML
    private AreaChart<?, ?> home_chart;

    @FXML
    private Label home_totalCustomers;

    @FXML
    private Label home_totalIncome;

    @FXML
    private Button logout;

    @FXML
    private Button minimize;

    @FXML
    private TableView<customerData> purchase_TableView;

    @FXML
    private Button purchase_add;

    @FXML
    private TextField purchase_amount;

    @FXML
    private Label purchase_balance;

    @FXML
    private ComboBox<?> purchase_breed;

    @FXML
    private Button purchase_btn;

    @FXML
    private TableColumn<?, ?> purchase_col_age;

    @FXML
    private TableColumn<customerData,String> purchase_col_breed;

    @FXML
    private TableColumn<customerData,String> purchase_col_petid;

    @FXML
    private TableColumn<customerData,String> purchase_col_price;

    @FXML
    private TableColumn<customerData,String> purchase_col_quantity;

    @FXML
    private TableColumn<customerData,String> purchase_col_sex;

    @FXML
    private AnchorPane purchase_form;

    @FXML
    private Button purchase_pay;

    @FXML
    private ComboBox<?> purchase_petID;

    @FXML
    private Spinner<Integer> purchase_quantity;

    @FXML
    private Label purchase_total;

    @FXML
    private Label username;

    @FXML
    private AnchorPane home_form;

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private Statement statement;

    public void homeDisplayAP(){
        String sql = "SELECT COUNT(id) FROM pet";
        connect = Database.connectDB();
        int countAP = 0;
        try{
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while(result.next()){
                countAP = result.getInt("COUNT(id)");
            }
            home_availablePets.setText(String.valueOf(countAP));
        }catch(Exception e){e.printStackTrace();}
    }

    public void homeDisplayTI(){
        String sql = "SELECT SUM(total) FROM customer_info";

        connect = Database.connectDB();
        double sumTI = 0;
        try{
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while(result.next()){
                sumTI = result.getDouble("SUM(total)");
                home_totalIncome.setText("$"+String.valueOf(sumTI));

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void homeDisplayTC(){
        String sql = "SELECT COUNT(id) FROM customer_info";
        connect = Database.connectDB();
        int countTC = 0;
        try{
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            while(result.next()){
                countTC = result.getInt("COUNT(id)");
            }
            home_totalCustomers.setText(String.valueOf(countTC));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void homeChart(){
        home_chart.getData().clear();
        String sql = "SELECT date, SUM(total) FROM customer_info GROUP BY date ORDER BY TIMESTAMP(date) ASC LIMIT 9";
        connect = Database.connectDB();

        try{
            XYChart.Series chart = new XYChart.Series();
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            while(result.next()){
                chart.getData().add(new XYChart.Data(result.getString(1),result.getInt(2)));
            }
            home_chart.getData().add(chart);
        }catch (Exception e){e.printStackTrace();}

    }

    public void addPetsAdd(){
        String sql = "INSERT INTO pet(pet_Id,breed,sex,age,price,date) VALUES(?,?,?,?,?,?)";

        connect = Database.connectDB();

        try {
            Alert alert;
            if(addPets_petId.getText().isEmpty()
            || addPets_breed.getText().isEmpty()
            || addPets_sex.getSelectionModel().getSelectedItem() == null
            || addPets_age.getText().isEmpty()
            || addPets_price.getText().isEmpty()){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please enter all the details above ");
                alert.showAndWait();
            }    else {

                String checkData = "SELECT pet_Id FROM pet WHERE pet_Id='"
                        + addPets_petId.getText() + "'";
                statement = connect.createStatement();
                result = statement.executeQuery(checkData);

                if(result.next()){
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Per ID: "+addPets_petId.getText()+" was already exist!");
                    alert.showAndWait();
                }else{
                    prepare = connect.prepareStatement(sql);
                    prepare.setString(1, addPets_petId.getText());
                    prepare.setString(2, addPets_breed.getText());
                    prepare.setString(3, (String) addPets_sex.getSelectionModel().getSelectedItem());
                    prepare.setString(4, addPets_age.getText());
                    prepare.setString(5, addPets_price.getText());

                    Date date = new Date();
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                    prepare.setString(6, sqlDate.toString());
                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Added !");
                    alert.showAndWait();

                    addPetsShowListData();
                    addPetsClear();
                }

            }
        } catch (Exception e) {e.printStackTrace();}
    }

    public void addPetsUpdate(){
        String sql = "UPDATE pet SET breed = '" + addPets_breed.getText()+"', sex = '"+addPets_sex.getSelectionModel().getSelectedItem()+"',age = '"+addPets_age.getText()+"', price = '"+addPets_price.getText()+"' WHERE pet_Id = '"+addPets_petId.getText()+"'";
        connect = Database.connectDB();
        try {
            Alert alert;
            if(addPets_petId.getText().isEmpty()
            || addPets_breed.getText().isEmpty()
            || addPets_age.getText().isEmpty()
            || addPets_sex.getSelectionModel().getSelectedItem() == null
            || addPets_price.getText().isEmpty()){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please enter all the details above ");
                alert.showAndWait();
            }else{
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to update Pet ID: "+addPets_petId.getText()+"?");
                Optional<ButtonType>option = alert.showAndWait();
                if(option.get() == ButtonType.OK){
                    statement = connect.createStatement();
                    statement.executeUpdate(sql);

                    //update
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Updated !");
                    alert.showAndWait();

                    addPetsShowListData();
                    addPetsClear();
                }
            }
        }catch (Exception e) {e.printStackTrace();}
    }

    public void addPetsDelete(){
        String sql = "DELETE FROM pet WHERE pet_Id = '"+addPets_petId.getText()+"'";

        connect = Database.connectDB();

        try {
            Alert alert;
            if(addPets_petId.getText().isEmpty()
                    || addPets_breed.getText().isEmpty()
                    || addPets_age.getText().isEmpty()
                    || addPets_sex.getSelectionModel().getSelectedItem() == null
                    || addPets_price.getText().isEmpty()){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please enter all the details above ");
                alert.showAndWait();
            }else{
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to Delete Pet ID: "+addPets_petId.getText()+"?");
                Optional<ButtonType>option = alert.showAndWait();
                if(option.get() == ButtonType.OK){
                    statement = connect.createStatement();
                    statement.executeUpdate(sql);

                    //update
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Deleted !");
                    alert.showAndWait();

                    addPetsShowListData();
                    addPetsClear();
                }
            }
        }catch (Exception e) {e.printStackTrace();}
    }

    public void addPetsClear(){
        addPets_petId.setText("");
        addPets_breed.setText("");
        addPets_age.setText("");
        addPets_sex.getSelectionModel().clearSelection();
        addPets_price.setText("");
    }

    private String[] sexList ={"Male","Female"};
    public void addPetsSexList(){
        List<String> lists = new ArrayList<>();

        for(String data: sexList){
            lists.add(data);
        }

        ObservableList listD = FXCollections.observableArrayList(lists);
        addPets_sex.setItems(listD);
    }

    public ObservableList<petData> addPetsListData(){
        ObservableList<petData> listData = FXCollections.observableArrayList();
        String sql = "select * from pet";
        connect = Database.connectDB();

        try{
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            petData petD;
            while(result.next()){
                petD = new petData(result.getInt("pet_Id"),result.getString("breed"),result.getString("sex"),result.getInt("age"), result.getDouble("price"));
                listData.add(petD);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }

    private ObservableList<petData>addPetsList;
    public void addPetsShowListData(){
        addPetsList = addPetsListData();

        addPets_col_petId.setCellValueFactory(new PropertyValueFactory<>("petId"));
        addPets_col_breed.setCellValueFactory(new PropertyValueFactory<>("breed"));
        addPets_col_sex.setCellValueFactory(new PropertyValueFactory<>("sex"));
        addPets_col_age.setCellValueFactory(new PropertyValueFactory<>("age"));
        addPets_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));

        addPets_tableView.setItems(addPetsList);
    }

    public void addPetsSearch(){
        FilteredList<petData> filter = new FilteredList<>(addPetsList, e -> true);

        addPets_search.textProperty().addListener((Observable,oldValue,newValue)->{
            filter.setPredicate(predicatePetData->{
                if(newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String searchKey = newValue.toLowerCase();
                if(predicatePetData.getPetId().toString().contains(searchKey)){
                    return true;
                }else if(predicatePetData.getBreed().toLowerCase().contains(searchKey)){
                    return true;
                }else if (predicatePetData.getSex().toLowerCase().contains(searchKey)){
                    return true;
                }else if (predicatePetData.getAge().toString().contains(searchKey)){
                    return true;
                }else if (predicatePetData.getPrice().toString().contains(searchKey)){
                    return true;
                }else{return false;}
            });
        });
        SortedList<petData> sortList = new SortedList<>(filter);
        sortList.comparatorProperty().bind(addPets_tableView.comparatorProperty());
        addPets_tableView.setItems(sortList);
    }

    public void addPetsSelect(){
        petData petD = addPets_tableView.getSelectionModel().getSelectedItem();
        int num = addPets_tableView.getSelectionModel().getSelectedIndex();

        if((num-1)< -1){return;}

        addPets_petId.setText(String.valueOf(petD.getPetId()));
        addPets_breed.setText(petD.getBreed());
        addPets_age.setText(String.valueOf(petD.getAge()));
        addPets_price.setText(String.valueOf(petD.getPrice()));
    }
    private double totalP = 0;
    public void purchaseAdd(){
        purchaseCustomerId();

        String sql = "INSERT INTO customer (customer_id,pet_Id,breed,sex,age,quantity,price,date)"+"VALUES(?,?,?,?,?,?,?,?)";
        connect = Database.connectDB();

        try {
            Alert alert;
            if (purchase_petID.getSelectionModel().getSelectedItem() == null
                    || purchase_breed.getSelectionModel().getSelectedItem() == null
                    || qty == 0) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Invalid :");
                alert.showAndWait();
            } else {
                String sexInfo = "";
                String ageInfo = "";
                Double price = 0.0;
                String checkData = "SELECT * FROM pet WHERE pet_Id = '"
                        + purchase_petID.getSelectionModel().getSelectedItem() + "'";

                statement = connect.createStatement();
                result = statement.executeQuery(checkData);

                if (result.next()) {
                    sexInfo = result.getString("sex");
                    ageInfo = result.getString("age");
                    price = result.getDouble("price");
                }

                prepare = connect.prepareStatement(sql);
                prepare.setString(1, String.valueOf(customerId));
                prepare.setString(2, String.valueOf(purchase_petID.getSelectionModel().getSelectedItem()));
                prepare.setString(3, String.valueOf(purchase_breed.getSelectionModel().getSelectedItem()));
                prepare.setString(4, sexInfo);
                prepare.setString(5, ageInfo);
                prepare.setString(6, String.valueOf(qty));
                totalP = qty * price;
                prepare.setString(7, String.valueOf(totalP));
                Date date = new Date();
                java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                prepare.setString(8, String.valueOf(sqlDate));

                prepare.executeUpdate();

                purchaseShowListData();
                purchaseDisplayTotal();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private double totalDisplay;
    public void purchaseDisplayTotal(){
        purchaseCustomerId();
        String sql = "SELECT * FROM customer WHERE customer_id = '"+customerId+"'";

        connect = Database.connectDB();
        try{
            assert connect != null;
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if(result.next()){
                totalDisplay = result.getDouble("price");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        purchase_total.setText("$"+totalDisplay);
    }

    private double balance;
    private double amount;
    public void purchaseAmount(){
        Alert alert;
        if(totalDisplay == 0|| purchase_amount.getText().isEmpty()){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Invalid :");
            alert.showAndWait();
        }else{
            amount = Double.parseDouble(purchase_amount.getText());
            if(amount < totalDisplay){
                purchase_amount.setText("");
                balance = 0;
            }else{
                balance = (amount-totalDisplay);

            }
        }
        purchase_balance.setText("$"+String.valueOf(balance));
    }

    public void purchasePay(){
        purchaseCustomerId();
        String sql = "INSERT INTO customer_info (customer_id,total,date)"+"VALUES(?,?,?)";
        connect = Database.connectDB();
        try{
            Alert alert;
            if(purchase_amount.getText().isEmpty() || purchase_TableView.getSelectionModel().isEmpty()){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please choose product First");
                alert.showAndWait();
            }else {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure ?");
                Optional<ButtonType> option = alert.showAndWait();

                if(option.get() == ButtonType.OK){
                    prepare = connect.prepareStatement(sql);
                    prepare.setString(1, String.valueOf(customerId));
                    prepare.setString(2, String.valueOf(totalDisplay));

                    Date date = new Date();
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                    prepare.setString(3, String.valueOf(sqlDate));
                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully purchased");
                    alert.showAndWait();

                    purchase_amount.setText("");
                    purchase_balance.setText("$0.0");
                }
            }
        } catch (Exception e) {e.printStackTrace();}

    }

    public void purchasePetId(){
        String sql = "SELECT * FROM pet";
        connect = Database.connectDB();
        try{
            ObservableList listData = FXCollections.observableArrayList();
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()){
                listData.add(result.getInt("pet_Id"));
            }
            purchase_petID.setItems(listData);

            purchaseBreed();
        } catch (Exception e) {e.printStackTrace();}
    }

    public void purchaseBreed(){
        String sql = "SELECT * FROM pet WHERE pet_Id = '"+purchase_petID.getSelectionModel().getSelectedItem()+"'";
        connect = Database.connectDB();
        try{
            ObservableList listData = FXCollections.observableArrayList();
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            while (result.next()){
                listData.add(result.getString("breed"));
            }
            purchase_breed.setItems(listData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int qty;
    private SpinnerValueFactory<Integer>spinner;
    public void purchaseSpinner(){
        spinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,10,0);
        purchase_quantity.setValueFactory(spinner);
    }

    public void purchaseQty(){
        qty = purchase_quantity.getValue();
    }

    public ObservableList<customerData>purchaseListData(){
        purchaseCustomerId();
        ObservableList<customerData> listData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM customer WHERE customer_id = '"+customerId+"'";
        connect = Database.connectDB();

        try{
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            customerData customerD;
            while(result.next()){
                customerD = new customerData(result.getInt("customer_id"), result.getInt("pet_Id"),result.getString("breed"),result.getString("sex"),result.getInt("age"),result.getInt("quantity"),result.getDouble("price"),result.getDate("date") );
                listData.add(customerD);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }

    private ObservableList<customerData> purchaseList;
    public void purchaseShowListData(){
        purchaseList = purchaseListData();

        purchase_col_petid.setCellValueFactory(new PropertyValueFactory<>("petId"));
        purchase_col_breed.setCellValueFactory(new PropertyValueFactory<>("breed"));
        purchase_col_sex.setCellValueFactory(new PropertyValueFactory<>("sex"));
        purchase_col_age.setCellValueFactory(new PropertyValueFactory<>("age"));
        purchase_col_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        purchase_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));

        purchase_TableView.setItems(purchaseList);

    }

    private int customerId;
    public void purchaseCustomerId(){
        String sql = "SELECT customer_id FROM customer";

        connect = Database.connectDB();
        int checkCID = 0;
        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            while (result.next()){
                customerId = result.getInt("customer_id");
            }

            String checkData = "SELECT * FROM customer_info";
            statement = connect.createStatement();
            result = statement.executeQuery(checkData);
            while (result.next()){
                checkCID = result.getInt("customer_id");
            }
            if(customerId == 0){
                customerId++;
            }else if(customerId == checkCID){
                customerId++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void switchForm(ActionEvent event) {
        if(event.getSource() == home_btn) {
            home_form.setVisible(true);
            addPets_form.setVisible(false);
            purchase_form.setVisible(false);

            home_btn.setStyle(" -fx-background-color: linear-gradient(to bottom right, #52ae8b, #9a2d3d)");
            addPets_btn.setStyle(" -fx-background-color: transparent");
            purchase_btn.setStyle("-fx-background-color: transparent");

            homeDisplayAP();
            homeDisplayTI();
            homeDisplayTC();
            homeChart();

        }else if(event.getSource() == addPets_btn) {
            home_form.setVisible(false);
            addPets_form.setVisible(true);
            purchase_form.setVisible(false);

            addPets_btn.setStyle(" -fx-background-color: linear-gradient(to bottom right, #52ae8b, #9a2d3d)");
            home_btn.setStyle(" -fx-background-color: transparent");
            purchase_btn.setStyle("-fx-background-color: transparent");

            addPetsShowListData();
            addPetsSexList();
            addPetsSearch();
        }else if(event.getSource() == purchase_btn) {
            home_form.setVisible(false);
            addPets_form.setVisible(false);
            purchase_form.setVisible(true);

            purchase_btn.setStyle(" -fx-background-color: linear-gradient(to bottom right, #52ae8b, #9a2d3d)");
            addPets_btn.setStyle(" -fx-background-color: transparent");
            home_btn.setStyle("-fx-background-color: transparent");

            purchasePetId();
            purchaseBreed();
            purchaseSpinner();
            purchaseShowListData();
            purchaseDisplayTotal();
        }
    }

    public void dev(ActionEvent event) throws IOException {
        Stage stage2 = new Stage();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/org/example/petshopmanagement/about.fxml")));
        Scene scene = new Scene(root);
        stage2.setScene(scene);
        stage2.show();
    }

    public void defaultNav(){
        home_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #52ae8b,#9a2d3d)");
    }

    public void displayUsername(){
        String user = getData.username;
        username.setText(user.substring(0,1).toUpperCase()+ user.substring(1));
    }

    private double x = 0 , y =0;
    public void logout(){
        try{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Logout");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to logout?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                logout.getScene().getWindow().hide();
                Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(root);

                root.setOnMousePressed((MouseEvent event)->{
                    x = event.getSceneX();
                    y = event.getSceneY();
                });
                root.setOnMouseDragged((MouseEvent event)->{
                    stage.setX(event.getScreenX() - x);
                    stage.setY(event.getScreenY() - y);

                    stage.setOpacity(.8);
                });

                root.setOnMouseReleased((MouseEvent event)->{
                    stage.setOpacity(1);
                });

                stage.initStyle(StageStyle.TRANSPARENT);

                stage.setScene(scene);
                stage.show();
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void close(){
        System.exit(0);
    }
    public void minimize(){
        Stage stage = (Stage) main_form.getScene().getWindow();
        stage.setIconified(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        displayUsername();
        defaultNav();
        homeDisplayAP();
        homeDisplayTI();
        homeDisplayTC();
        homeChart();

        addPetsShowListData();
        addPetsSexList();
        addPetsSearch();

        purchasePetId();
        purchaseBreed();
        purchaseSpinner();
        purchaseShowListData();
        purchaseDisplayTotal();
    }
}



