package controller;
import bo.BoFactory;
import bo.custom.CustomerBo;
import com.jfoenix.controls.JFXTextField;
import dao.util.BoType;
import db.DBConnection;
import dto.CustomerDto;
import dto.tm.CustomerTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.function.Predicate;

public class CustomerFormController {

    private AnchorPane pane;
    public JFXTextField txtAddress;
    public JFXTextField txtSearch;
    @FXML
    private TableColumn colAddress;

    @FXML
    private TableColumn colId;

    @FXML
    private TableColumn colName;

    @FXML
    private TableColumn colOption;

    @FXML
    private TableColumn colSalary;

    @FXML
    private TableView<CustomerTm> tblCustomer;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtSalary;

    @FXML
    private JFXTextField txtid;
    private CustomerBo customerBo = BoFactory.getInstance().getBo(BoType.CUSTOMER);
    final ObservableList<CustomerTm> tmList = FXCollections.observableArrayList();
    public void initialize(){
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("btn"));
        loadCustomerTable();
        searchCustomer();

        tblCustomer.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            setData(newValue);

        });
    }

    private void searchCustomer() {
        FilteredList<CustomerTm> filterData= new FilteredList<>(tmList,e->true);
        txtSearch.setOnKeyReleased(e->{
            txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
                filterData.setPredicate((Predicate<? super CustomerTm >) cust->{
                    if (newValue==null){
                        return true;
                    }
                    String toLowerCaseFilter = newValue.toLowerCase();
                    if(cust.getId().toLowerCase().contains(toLowerCaseFilter)){
                        return true;
                    } else if (cust.getName().toLowerCase().contains(toLowerCaseFilter)){
                        return true;
                    }

                return false;
                });
            });

            final SortedList<CustomerTm> customers = new SortedList<>(filterData);
            customers.comparatorProperty().bind(tblCustomer.comparatorProperty());
            tblCustomer.setItems(customers);
        });

    }

    private void setData(CustomerTm newValue) {
        if (newValue != null) {
            txtid.setEditable(false);
            txtid.setText(newValue.getId());
            txtName.setText(newValue.getName());
            txtAddress.setText(newValue.getAddress());
            txtSalary.setText(String.valueOf(newValue.getSalary()));
        }
    }

    private void loadCustomerTable() {
        ObservableList<CustomerTm> tmList = FXCollections.observableArrayList();

        try {
            List<CustomerDto> dtoList = customerBo.allCustomers();

            for (CustomerDto dto:dtoList) {
                Button btn = new Button("Delete");

                CustomerTm c = new CustomerTm(
                        dto.getId(),
                        dto.getName(),
                        dto.getAddress(),
                        dto.getSalary(),
                        btn
                );

                btn.setOnAction(actionEvent -> {
                    deleteCustomer(c.getId());
                });

                tmList.add(c);
            }

            tblCustomer.setItems(tmList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteCustomer(String id){

        boolean isDeleted = false;
        try {
            isDeleted = customerBo.deleteCustomer(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        if (isDeleted) {
            new Alert(Alert.AlertType.INFORMATION, "Customer Deleted!").show();
            loadCustomerTable();
            tblCustomer.refresh();
        } else {
            new Alert(Alert.AlertType.ERROR, "Somrthing went wronge!").show();
        }
    }

    @FXML
    void reloadButtonOnAction(ActionEvent event) throws ClassNotFoundException {
        loadCustomerTable();
        tblCustomer.refresh();
        clearFields();

    }

    @FXML
    void saveButtonOnAction(ActionEvent event){

        CustomerDto c = new CustomerDto(txtid.getText(),
                txtName.getText(),
                txtAddress.getText(),
                Double.parseDouble(txtSalary.getText())
        );
        boolean isSaved = false;
        try {
            isSaved = customerBo.saveCustomer(c);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        if(isSaved){
            new Alert(Alert.AlertType.INFORMATION,"Customer Saved").show();
            clearFields();
            loadCustomerTable();
        }else {
            new Alert(Alert.AlertType.ERROR,"Duplicate Entry").show();
        }
    }

    private void clearFields() {
        tblCustomer.refresh();
        txtSalary.clear();
        txtAddress.clear();
        txtName.clear();
        txtid.clear();
        txtid.setEditable(true);
    }

    @FXML
    void updateButtonOnAction(ActionEvent event) {
        CustomerDto c = new CustomerDto(txtid.getText(),
                txtName.getText(),
                txtAddress.getText(),
                Double.parseDouble(txtSalary.getText())
        );
        String sql = "UPDATE customer SET name='"+c.getName()+"', address='"+c.getAddress()+"', salary="+c.getSalary()+" WHERE id='"+c.getId()+"'";

        try {
            Statement stm = DBConnection.getInstance().getConnection().createStatement();
            int result = stm.executeUpdate(sql);
            if (result>0){
                new Alert(Alert.AlertType.INFORMATION,"Customer "+c.getId()+" Updated!").show();
                loadCustomerTable();
                clearFields();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void backButtonOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) tblCustomer.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/Dashboard.fxml"))));
    }

    public void reportButtonOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, JRException {
        try {
            JasperDesign design = JRXmlLoader.load("src/main/resources/reports/Customer_Reports.jrxml");
            //
            //
            JasperReport jasperReport = JasperCompileManager.compileReport(design);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, DBConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint,false);
        } catch (JRException | ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
