package controller;

import bo.BoFactory;
import bo.custom.CustomerBo;
import bo.custom.ItemBo;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import dao.util.BoType;
import db.DBConnection;
import dto.CustomerDto;
import dto.ItemDto;
import dto.tm.CustomerTm;
import dto.tm.ItemTm;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.function.Predicate;

public class ItemFormController {

    public JFXTreeTableView<ItemTm> tableItem;
    @FXML
    private BorderPane borderPane;

    @FXML
    private TreeTableColumn colCode;

    @FXML
    private TreeTableColumn colDes;

    @FXML
    private TreeTableColumn colOption;

    @FXML
    private TreeTableColumn colQty;

    @FXML
    private TreeTableColumn colUnitPrice;

    @FXML
    private JFXTextField txtCode;

    @FXML
    private JFXTextField txtDes;

    @FXML
    private JFXTextField txtQty;

    @FXML
    private JFXTextField txtSearch;

    @FXML
    private JFXTextField txtUnitPrice;

    private ItemBo itemBo = BoFactory.getInstance().getBo(BoType.ITEM);
    ObservableList<ItemTm> tmList = FXCollections.observableArrayList();

    public void initialize() throws SQLException, ClassNotFoundException {
        colCode.setCellValueFactory(new TreeItemPropertyValueFactory<>("code"));
        colDes.setCellValueFactory(new TreeItemPropertyValueFactory<>("desc"));
        colUnitPrice.setCellValueFactory(new TreeItemPropertyValueFactory<>("unitPrice"));
        colQty.setCellValueFactory(new TreeItemPropertyValueFactory<>("qty"));
        colOption.setCellValueFactory(new TreeItemPropertyValueFactory<>("btn"));
        loadItemTable();
        searchItem();
        tableItem.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            setData(newValue.getValue());
        });
    }

    private void setData(ItemTm newValue) {
        if (newValue != null) {
            txtCode.setEditable(false);
            txtCode.setText(newValue.getCode());
            txtDes.setText(newValue.getDesc());
            txtUnitPrice.setText(String.valueOf(newValue.getUnitPrice()));
            txtQty.setText(String.valueOf(newValue.getQty()));
        }
    }

    private void searchItem() {
        txtSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String newValue) {
                tableItem.setPredicate(new Predicate<TreeItem<ItemTm>>() {
                    @Override
                    public boolean test(TreeItem<ItemTm> treeItem) {
                        return treeItem.getValue().getCode().contains(newValue) ||
                                treeItem.getValue().getDesc().contains(newValue);
                    }
                });
            }
        });
    }

    private void loadItemTable() throws SQLException, ClassNotFoundException {
        tmList.clear();
        List<ItemDto> dtoList = itemBo.allItems();
        for (ItemDto dto : dtoList) {
            JFXButton btn = new JFXButton("Delete");
            ItemTm c = new ItemTm(
                    dto.getCode(),
                    dto.getDesc(),
                    dto.getUnitPrice(),
                    dto.getQty(),
                    btn
            );

            btn.setOnAction(actionEvent -> {
                try {
                    deleteItem(c.getCode());
                } catch (SQLException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            });

            tmList.add(c);
        }
        TreeItem<ItemTm> treeObject = new RecursiveTreeItem<>(tmList, RecursiveTreeObject::getChildren);
        tableItem.setRoot(treeObject);
        tableItem.setShowRoot(false);
    }

    private void deleteItem(String code) throws SQLException, ClassNotFoundException {
        boolean v = itemBo.deleteItem(code);
        if (v) {
            new Alert(Alert.AlertType.INFORMATION, "Item Deleted!").show();
            loadItemTable();
        } else {
            new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
        }
    }

    public void backButtonOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) borderPane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/Dashboard.fxml"))));
    }

    public void updateButtonOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        boolean isUpdated = false;
        try {



            isUpdated = itemBo.updateItem(new ItemDto(
                    txtCode.getText(),
                    txtDes.getText(),
                    Double.parseDouble(txtUnitPrice.getText()),
                    Integer.parseInt(txtQty.getText())

            ));
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        if (isUpdated) {
            new Alert(Alert.AlertType.INFORMATION, "Item Updated!").show();
            loadItemTable();
            clearFields();
        } else {
            new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
        }
    }

    private void clearFields() {
        tableItem.refresh();
        txtCode.clear();
        txtDes.clear();
        txtUnitPrice.clear();
        txtQty.clear();
    }

    public void savebuttonOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        ItemDto c = new ItemDto(
                txtCode.getText(),
                txtDes.getText(),
                Double.parseDouble(txtUnitPrice.getText()),
                Integer.parseInt(txtQty.getText())
        );
        boolean isSaved = itemBo.saveItem(c);
        if (isSaved) {
            new Alert(Alert.AlertType.INFORMATION, "Item Saved!").show();
            loadItemTable();
            clearFields();
        } else {
            new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
        }
    }

    public void reportButtonOnAction(ActionEvent actionEvent) {
        try {
            JasperDesign design = JRXmlLoader.load("src/main/resources/reports/Item_Reports.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(design);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, DBConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException | ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
