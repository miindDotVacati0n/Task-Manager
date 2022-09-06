package controller.home;

import com.github.saacsos.FXRouter;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
//import javafx.scene.control.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.works.Category;
import model.works.GeneralWorks;
import services.*;
//import services.*;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class GeneralWorksController {

    @FXML
    private TextField Name ;
    @FXML
    private ChoiceBox<Integer> hourStart, minStart, hourFin, minFin;
    @FXML
    private ChoiceBox<String> priorityCB, statusCB, categoryCB;
    @FXML
    private Button submit, update;
    @FXML
    private Label warningLabel;
    @FXML
    private TableView<GeneralWorks> generalTableView;

    @FXML
    private DatePicker datePicker ;


    private GeneralWorks generalWork;
    private DataList dataList;
    private DataList dataListCat;
    private DataSource dataSourceCat;
    private DataSource workDataSource;
    private ObservableList<GeneralWorks> generalWorksObservableList ;
    private GeneralWorks selectedGeneralWorks;

    @FXML
    public void initialize() {
        Platform.runLater((Runnable)new Runnable() {


            @Override
            public void run() {
                workDataSource = new GeneralWorkFileDataSource("data", "generalWorks.csv");
                dataList = workDataSource.getData();

                dataSourceCat = new CategoryFileDataSource("data", "category.csv");
                dataListCat = dataSourceCat.getData();
                for (int i = 0; i < dataListCat.getCategoryArrayList().size(); i++) {
                    categoryCB.getItems().add(dataListCat.getCategoryArrayList().get(i).getCategoryName());
                }
                categoryCB.getItems().add("No category");
//                for (GeneralWorks g : dataList.getGeneralWorkArrayList()) {
//                    System.out.println(g.toString());
//                }

                priorityCB.getItems().addAll("Most priority", "Medium priority", "Less priority");
                statusCB.getItems().addAll("Just Start","Process","Success");

                for (int i = 0 ; i < 24 ; i++){
                    hourStart.getItems().add((i));
                }
                for (int i = 0 ; i < 60 ; i++){
                    minStart.getItems().add((i));
                }

                for (int i = 0 ; i < 24 ; i++){
                    hourFin.getItems().add((i));
                }
                for (int i = 0 ; i < 60 ; i++){
                    minFin.getItems().add((i));
                }
                showGeneralWorksData();


                generalTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        showSelectedGeneralWorks(newValue);
                    }
                });

            }
        });
    }

    private void showGeneralWorksData() {
        generalTableView.getColumns().clear();
        generalWorksObservableList = FXCollections.observableArrayList(dataList.getGeneralWorkArrayList());
        generalTableView.setItems(generalWorksObservableList);

        ArrayList<StringConfiguration> configs = new ArrayList<>();
        configs.add(new StringConfiguration("title:Work name", "field:name"));
        configs.add(new StringConfiguration("title:Do Date", "field:madeDate"));
        configs.add(new StringConfiguration("title:Time Start", "field:startDate"));
        configs.add(new StringConfiguration("title:Time Finish", "field:lastDate"));
        configs.add(new StringConfiguration("title:Priority", "field:priority"));
        configs.add(new StringConfiguration("title:Status", "field:status"));
        configs.add(new StringConfiguration("title:Category", "field:category"));

        for (StringConfiguration conf: configs) {
            TableColumn col = new TableColumn(conf.get("title"));
            col.setCellValueFactory(new PropertyValueFactory<>(conf.get("field")));
            generalTableView.getColumns().add(col);
            if (conf.get("title").equals("Priority")){
                generalTableView.getSortOrder().addAll(col);
                col.setSortType(TableColumn.SortType.DESCENDING);
            }
        }


    }

    @FXML public void handleSubmitAction(ActionEvent event) throws IOException {

        if (hourStart.getValue() != null && minStart.getValue() != null && hourFin.getValue() != null && minFin.getValue() != null &&
                Name.getText() != null && datePicker.getValue() != null && priorityCB.getValue() != null && statusCB.getValue() != null && dataList.checkNameGeneral(Name.getText()) && categoryCB.getValue() != null) {
            generalWork = new GeneralWorks(Name.getText(), datePicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    hourStart.getValue() + ":" + minStart.getValue(),
                    hourFin.getValue() + ":" + minFin.getValue(),
                    priorityCB.getValue(), statusCB.getValue(), categoryCB.getValue());

            if (hourStart.getValue() == hourFin.getValue()){
                if (minStart.getValue() < minFin.getValue()){
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
                    alert.setTitle("Please check your data!!!");
                    alert.showAndWait();
                    if (alert.getResult().getText().equals("Yes")) {
                        System.out.println(generalWork.toString());
                        dataList.addGenWork(generalWork);

                        workDataSource.setData(dataList);
//                        warningLabel.setText(null);
                        showGeneralWorksData();
                    }
                }
                else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText("Error!!!");
                    alert.setContentText("Please complete the correct time!!!");
                    alert.show();
                }
            }
            else if (hourStart.getValue() < hourFin.getValue()){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
                alert.setTitle("Please check your data!!!");
                alert.showAndWait();
                if (alert.getResult().getText().equals("Yes")) {
                    System.out.println(generalWork.toString());
                    dataList.addGenWork(generalWork);

                    workDataSource.setData(dataList);
//                    warningLabel.setText(null);
                    showGeneralWorksData();
                }
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Error!!!");
                alert.setContentText("Please complete the correct time!!!");
                alert.show();
            }
        }

        else if (hourStart.getValue() == null && minStart.getValue() == null && hourFin.getValue() == null && minFin.getValue() == null &&
                Name.getText() == null && datePicker.getValue() == null &&
                priorityCB.getValue() == null &&
                statusCB.getValue() == null &&
                categoryCB.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error!!!");
            alert.setContentText("Please complete all information!!!");
            alert.show();
        }

        else if (!(dataList.checkNameGeneral(Name.getText()))){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error!!!");
            alert.setContentText("Please enter new name!!!");
            alert.show();
        }

        else{

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error!!!");
            alert.setContentText("Please complete all information!!!");
            alert.show();
        }

//        if (hourStart.getValue() != null && minStart.getValue() != null &&
//                Name.getText() != null && datePicker.getValue() != null &&
//                priorityCB.getValue() != null && statusCB.getValue() != null&& dataList.checkNameGeneral(Name.getText()) && categoryCB.getValue() != null) {
//            generalWork = new GeneralWorks(Name.getText(), datePicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
//                    hourStart.getValue() + ":" + minStart.getValue(),
//                    hourFin.getValue() + ":" + minFin.getValue(),
//                    priorityCB.getValue(), statusCB.getValue(), categoryCB.getValue());
//
//            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
//            alert.setTitle("Please check your data!!!");
//            alert.showAndWait();
//            if (alert.getResult().getText().equals("Yes")) {
//
//                System.out.println(generalWork.toString()); // Showing S
//                dataList.addGenWork(generalWork);
//                workDataSource.setData(dataList);
//                hourFin.setValue(0);
//                minFin.setValue(0);
//                showGeneralWorksData();
//            }
//        }
//        else{
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setHeaderText("Error!!!");
//            alert.setContentText("Please complete all information!!!");
//            alert.show();
//        }

    }

    private void showSelectedGeneralWorks(GeneralWorks generalWork) {
        selectedGeneralWorks = generalWork;
        if (dataList.checkNameGeneral(Name.getText())){
            submit.setDisable(false);
        }
        else {
            submit.setDisable(true);
        }
        if (selectedGeneralWorks.getStatus().equals("Success")){
            Name.setText(selectedGeneralWorks.getName());
            priorityCB.setValue(selectedGeneralWorks.getPriority());
            statusCB.setValue(selectedGeneralWorks.getStatus());
            categoryCB.setValue(selectedGeneralWorks.getCategory());
            update.setDisable(true);
        }
        else {
            Name.setText(selectedGeneralWorks.getName());
            priorityCB.setValue(selectedGeneralWorks.getPriority());
            statusCB.setValue(selectedGeneralWorks.getStatus());
            categoryCB.setValue(selectedGeneralWorks.getCategory());

            update.setDisable(false);
        }
    }

    private void clearSelectedGeneral() {
        selectedGeneralWorks = null;
        Name.setText(null);
        hourStart.setValue(null);
        minStart.setValue(null);
        hourFin.setValue(null);
        minFin.setValue(null);
//        priorityCB.setValue(null);
//        statusCB.setValue(null);
        datePicker.setValue(null);
        submit.setDisable(true);
    }

    @FXML
    public void handleUpdateButton(ActionEvent event) {

        if (hourStart.getValue() != null && minStart.getValue() != null && hourFin.getValue() != null && minFin.getValue() != null &&
        Name.getText() != null && datePicker.getValue() != null && priorityCB.getValue() != null && statusCB.getValue() != null ) {
            selectedGeneralWorks.setName(Name.getText());

            if (hourStart.getValue() == hourFin.getValue()){
                if (minStart.getValue() < minFin.getValue()){
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
                    alert.setTitle("Please check your data!!!");
                    alert.showAndWait();
                    if (alert.getResult().getText().equals("Yes")) {
                        selectedGeneralWorks.setStartDate(hourStart.getValue() + ":" + minStart.getValue());
                        selectedGeneralWorks.setLastDate(hourFin.getValue() + ":" + minFin.getValue());
                    }
                }
                else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText("Error!!!");
                    alert.setContentText("Please complete the correct time!!!");
                    alert.show();
                }
            }
            else if (hourStart.getValue() < hourFin.getValue()){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
                alert.setTitle("Please check your data!!!");
                alert.showAndWait();
                if (alert.getResult().getText().equals("Yes")) {
                    selectedGeneralWorks.setStartDate(hourStart.getValue() + ":" + minStart.getValue());
                    selectedGeneralWorks.setLastDate(hourFin.getValue() + ":" + minFin.getValue());
                }
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Error!!!");
                alert.setContentText("Please complete the correct time!!!");
                alert.show();
            }
//            selectedGeneralWorks.setStartDate(hourStart.getValue() + ":" + minStart.getValue());
//            selectedGeneralWorks.setLastDate(hourFin.getValue() + ":" + minFin.getValue());


            selectedGeneralWorks.setPriority(priorityCB.getValue());
            selectedGeneralWorks.setStatus(statusCB.getValue());
            clearSelectedGeneral();
            generalTableView.refresh();
            generalTableView.getSelectionModel().clearSelection();
            workDataSource.setData(dataList);
//            warningLabel.setText(null);
        }

        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error!!!");
            alert.setContentText("Please complete all information!!!");
            alert.show();
        }
    }

    @FXML
    public void handleBackButton(ActionEvent actionEvent) {
        try {
            FXRouter.goTo("homepage");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า home ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

}

