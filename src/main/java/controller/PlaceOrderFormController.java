package controller;

import bo.custom.CustomerBo;
import bo.custom.ItemBo;
import bo.custom.OrderBo;
import bo.custom.OrderDetailsBo;
import bo.custom.impl.CustomerBoImpl;
import bo.custom.impl.ItemBoImpl;
import bo.custom.impl.OrderBoImpl;
import bo.custom.impl.OrderDetailsBoImpl;
import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import dao.custom.*;
import db.DBConnection;
import dto.*;
import dto.tm.OrderTm;
import dao.custom.impl.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PlaceOrderFormController {
    public JFXComboBox cmbItemCode;
    public JFXComboBox cmbCustId;
    public JFXTextField txtDesc;
    public JFXTextField txtUnitPrice;
    public JFXTextField txtQty;
    public JFXTextField txtName;
    public Label lblOrderID;
    public Label lbltotal;
    public TableView <OrderTm>orderTable;
    public JFXTreeTableView OrderTable;
    public TreeTableColumn colCode;
    public TreeTableColumn colDesc;
    public TreeTableColumn colQty;
    public TreeTableColumn colOption;
    public JFXTreeTableView tblOrder;
    public TreeTableColumn colAmount;
    private List<CustomerDto> customers;
    private List<ItemDto> items;
    private double tot = 0;
    private OrderDao orderModel = new OrderDaoImpl();
    private ItemDao itemModel = new ItemDaoImpl();
    private ObservableList<OrderTm> tmList = FXCollections.observableArrayList();
    private List<PlaceOrderDto> check = new ArrayList<>();
    private PlaceOrderDao placeOrderModels = new PlaceOrderDaoImpl();
    private OrderDetailsDao orderDetailsModel = new OrderDetailsDaoImpl();
    private CustomerBo customerBo = new CustomerBoImpl();
    private OrderBo orderBo = new OrderBoImpl();
    private OrderDetailsBo orderDetailsBo = new OrderDetailsBoImpl();

    @FXML
BorderPane pane;

    public void initialize() throws SQLException, ClassNotFoundException {
        colCode.setCellValueFactory(new TreeItemPropertyValueFactory<>("code"));
        colDesc.setCellValueFactory(new TreeItemPropertyValueFactory<>("desc"));
        colQty.setCellValueFactory(new TreeItemPropertyValueFactory<>("qty"));
        colAmount.setCellValueFactory(new TreeItemPropertyValueFactory<>("amount"));
        colOption.setCellValueFactory(new TreeItemPropertyValueFactory<>("btn"));
        generateId();
        loadCustomerIds();
        loadItemCodes();
        checkValidQty();
        System.out.println(lblOrderID.getText());


        cmbCustId.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, id) -> {
            for (CustomerDto dto:customers) {
                if (dto.getId().equals(id)){
                    txtName.setText(dto.getName());
                }
            }
        });

        cmbItemCode.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, code) -> {
            for (ItemDto dto:items) {
                if (dto.getCode().equals(code)){
                    txtDesc.setText(dto.getDesc());
                    txtUnitPrice.setText(String.format("%.2f",dto.getUnitPrice()));
                }
            }
        });
    }

    private void checkValidQty() throws SQLException, ClassNotFoundException {
        //ObservableList<ItemTm>tmList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM item";

        Statement stm = DBConnection.getInstance().getConnection().createStatement();
        ResultSet result = stm.executeQuery(sql);
        while (result.next()){
            PlaceOrderDto tm = new PlaceOrderDto(
                    result.getString(1),
                    result.getInt(4)
            );


            check.add(tm);
        }
    }

    private void loadItemCodes() {
        ItemBo itemBo = new ItemBoImpl();
        try {
            items = itemBo.allItems();
            ObservableList list = FXCollections.observableArrayList();
            for (ItemDto dto:items) {
                list.add(dto.getCode());
            }
            cmbItemCode.setItems(list);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

        private void loadCustomerIds() {
           try {
                customers = customerBo.allCustomers();
                ObservableList list = FXCollections.observableArrayList();
                for (CustomerDto dto:customers) {
                    list.add(dto.getId());
                }
                cmbCustId.setItems(list);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }


    private void generateId() {
        try {
            OrderDto dto = orderModel.lastOrder();
            if (dto!=null){
                String id = dto.getOrderId();
                int num = Integer.parseInt(id.split("[D]")[1]);
                num++;
                lblOrderID.setText(String.format("D%03d",num));
            }else{
                lblOrderID.setText("D001");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void backButtonOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/Dashboard.fxml"))));
    }

    public void addToCartButtonOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        String x = (String) cmbItemCode.getValue();
        int y = placeOrderModels.checkValidQty(x);
        boolean isValid = false;
        if(Integer.parseInt(txtQty.getText())<=y){
            isValid = true;
        }

        if (isValid) {
            try {
                double amount = itemModel.getItem(cmbItemCode.getValue().toString()).getUnitPrice() * Integer.parseInt(txtQty.getText());
                JFXButton btn = new JFXButton("Delete");

                OrderTm tm = new OrderTm(
                        cmbItemCode.getValue().toString(),
                        txtDesc.getText(),
                        Integer.parseInt(txtQty.getText()),
                        amount,
                        btn
                );

                btn.setOnAction(actionEvent1 -> {
                    tmList.remove(tm);
                    tot -= tm.getAmount();
                    orderTable.refresh();
                    lbltotal.setText(String.format("%.2f", tot));
                });

                boolean isExist = false;

                for (OrderTm order : tmList) {
                    if (order.getCode().equals(tm.getCode())) {
                        order.setQty(order.getQty() + tm.getQty());
                        order.setAmount(order.getAmount() + tm.getAmount());
                        isExist = true;
                        tot += tm.getAmount();
                    }
                }

                if (!isExist) {
                    tmList.add(tm);
                    tot += tm.getAmount();
                }

                TreeItem<OrderTm> treeObject = new RecursiveTreeItem<OrderTm>(tmList, RecursiveTreeObject::getChildren);
                tblOrder.setRoot(treeObject);
                tblOrder.setShowRoot(false);

                lbltotal.setText(String.format("%.2f", tot));
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }else {
            new Alert(Alert.AlertType.ERROR,"Something went wrong!").show();
        }

    }

    public void PlaceOrderButtonOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        List<OrderDetailsDto> list = new ArrayList<>();
        orderDetailsModel.updateQty(list);
        for (OrderTm tm:tmList) {
            list.add(new OrderDetailsDto(
                    lblOrderID.getText(),
                    tm.getCode(),
                    tm.getQty(),
                    tm.getAmount()/tm.getQty()
            ));

        }
        boolean isSaved = false;
        try {
            isSaved = orderBo.saveOrder(new OrderDto(
                    lblOrderID.getText(),
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY-MM-dd")),
                    cmbCustId.getValue().toString(),
                    list
            ));
           boolean qtyUpdate = orderDetailsBo.updateQty(list);
           if(isSaved){
               boolean isSavedD = orderDetailsBo.saveOrderDetails(list);
               System.out.println("Is Order Save ? "+isSaved);
               if (isSaved && isSavedD){
                   new Alert(Alert.AlertType.INFORMATION,"Order Saved!").show();
               }else{
                   new Alert(Alert.AlertType.ERROR,"Something went wrong!").show();
               }
           }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
