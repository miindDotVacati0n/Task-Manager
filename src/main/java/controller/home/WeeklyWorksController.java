package controller.home;

import com.github.saacsos.FXRouter;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import model.works.WeeklyWorks;
import services.*;

import java.io.IOException;
import java.util.ArrayList;

public class WeeklyWorksController {

    @FXML
    TextField weeklyName ;
    @FXML
    ChoiceBox<Integer> hourStart, minStart, hourFin, minFin;
    @FXML
    ChoiceBox<String> priorityWL, statusWL, categoryWL;
    @FXML
    CheckBox sunday, monday, tuesday, wednesday, thursday, friday, saturday;
    @FXML
    Button submit, update;
    @FXML
    Label warningLabel;
    @FXML
    TableView<WeeklyWorks> weeklyTableView;

    private WeeklyWorks weekly ;
    private DataList dataList ;
    private DataList dataListCat;
    private DataSource dataSourceCat;
    private DataSource workDataSource ;
    private ObservableList<WeeklyWorks> weeklyWorksObservableList ;
    private WeeklyWorks selectedWeekly ;

    @FXML
    public void initialize() {
        Platform.runLater((Runnable)new Runnable() {
            @Override
            public void run() {
                workDataSource = new WeeklyWorksFileDataSource("data", "weeklyWorks.csv");
                dataList = workDataSource.getData();

                dataSourceCat = new CategoryFileDataSource("data", "category.csv");
                dataListCat = dataSourceCat.getData();
                for (int i = 0; i < dataListCat.getCategoryArrayList().size(); i++) {
                    categoryWL.getItems().add(dataListCat.getCategoryArrayList().get(i).getCategoryName());
                }
                categoryWL.getItems().add("No category");

                for (WeeklyWorks w : dataList.getWeeklyWorksArrayList()) {
                    System.out.println(w.toString());
                }

                priorityWL.getItems().addAll("Most priority", "Medium priority", "Less priority");
                statusWL.getItems().addAll("Just Start","Process","Success");

                for (int i = 0 ; i < 24 ; i++){
                    hourStart.getItems().add(i);
                }
                for (int i = 0 ; i < 60 ; i++){
                    minStart.getItems().add(i);
                }

                for (int i = 0 ; i < 24 ; i++){
                    hourFin.getItems().add(i);
                }
                for (int i = 0 ; i < 60 ; i++){
                    minFin.getItems().add(i);
                }
                showWeeklyData();


                weeklyTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        showSelectedWeekly(newValue);
                    }
                });
//                showSelectWeekly();
            }
        });
    }

    private void showWeeklyData() {
        weeklyTableView.getColumns().clear();
        weeklyWorksObservableList = FXCollections.observableArrayList(dataList.getWeeklyWorksArrayList());
        weeklyTableView.setItems(weeklyWorksObservableList);
        ArrayList<StringConfiguration> configs = new ArrayList<>();
        configs.add(new StringConfiguration("title:Work name", "field:name"));
        configs.add(new StringConfiguration("title:Day", "field:date"));
        configs.add(new StringConfiguration("title:Time Start", "field:startDate"));
        configs.add(new StringConfiguration("title:Time Finish", "field:lastDate"));
        configs.add(new StringConfiguration("title:Priority", "field:priority"));
        configs.add(new StringConfiguration("title:Status", "field:status"));
        configs.add(new StringConfiguration("title:Category", "field:category"));

        for (StringConfiguration conf: configs) {
            TableColumn col = new TableColumn(conf.get("title"));
            col.setCellValueFactory(new PropertyValueFactory<>(conf.get("field")));
            weeklyTableView.getColumns().add(col);
            if (conf.get("title").equals("Priority")){
                weeklyTableView.getSortOrder().addAll(col);
                col.setSortType(TableColumn.SortType.DESCENDING);
            }
        }


    }


    @FXML public void handleSubmitAction(ActionEvent event) throws IOException {

        StringBuilder checkDate = new StringBuilder();
        ArrayList<String> day = new ArrayList<>();
        if (sunday.isSelected()){day.add("Sunday");}
        if (monday.isSelected()){day.add("Monday");}
        if (tuesday.isSelected()){day.add("Tuesday");}
        if (wednesday.isSelected()){day.add("Wednesday");}
        if (thursday.isSelected()){day.add("Thursday");}
        if (friday.isSelected()){day.add("Friday");}
        if (saturday.isSelected()){day.add("Saturday");}
        int j = 0 ;
        for (String i : day ){
            checkDate.append(i);
            j++;
            if (j != day.size()){
                checkDate.append("/");
            }
        }

        if (!sunday.isSelected() && !monday.isSelected() && !tuesday.isSelected() && !wednesday.isSelected() && !thursday.isSelected() && !friday.isSelected() && !saturday.isSelected()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error!!!");
            alert.setContentText("Please complete the day!!!");
            alert.show();
        }

        else if (weeklyName.getText() != null &&
                hourFin.getValue() != null && minFin.getValue() != null && priorityWL.getValue() != null && statusWL.getValue() != null && dataList.checkNameWeekly(weeklyName.getText()) && categoryWL.getValue() != null) {

            weekly = new WeeklyWorks(weeklyName.getText(),
                    checkDate.toString(),
                    hourStart.getValue() + ":" + minStart.getValue(),
                    hourFin.getValue() + ":" + minFin.getValue(),
                    priorityWL.getValue(),
                    statusWL.getValue(), categoryWL.getValue());


            if (hourStart.getValue() == hourFin.getValue()) {
                if (minStart.getValue() < minFin.getValue()) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
                    alert.setTitle("Please check your data!!!");
                    alert.showAndWait();
                    if (alert.getResult().getText().equals("Yes")) {
                        System.out.println(weekly.toString());
                        dataList.addGenWork(weekly);

                        workDataSource.setData(dataList);
                        System.out.println(weekly.toString());
                        dataList.addWeekWork(weekly); // แก้หน้า DataList

                        workDataSource.setData(dataList);
                        warningLabel.setText(null);
                        showWeeklyData();

                    }
                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText("Error!!!");
                    alert.setContentText("Please complete the correct time!!!");
                    alert.show();
                }
            }else if (hourStart.getValue() < hourFin.getValue()){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
                alert.setTitle("Please check your data!!!");
                alert.showAndWait();
                if (alert.getResult().getText().equals("Yes")) {
                    System.out.println(weekly.toString());
                    dataList.addGenWork(weekly);

                    workDataSource.setData(dataList);
                    System.out.println(weekly.toString());
                    dataList.addWeekWork(weekly);

                    workDataSource.setData(dataList);
                    warningLabel.setText(null);
                    showWeeklyData();
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
                weeklyName.getText() == null &&
                priorityWL.getValue() == null &&
                statusWL.getValue() == null &&
                categoryWL.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error!!!");
            alert.setContentText("Please complete all information!!!");
            alert.show();
        }

        else if (!(dataList.checkNameWeekly(weeklyName.getText()))){
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

    }

    private void showSelectedWeekly(WeeklyWorks work) {
        selectedWeekly = work;
        sunday.setSelected(false);
        monday.setSelected(false);
        tuesday.setSelected(false);
        wednesday.setSelected(false);
        thursday.setSelected(false);
        friday.setSelected(false);
        saturday.setSelected(false);
        String[] date = selectedWeekly.getDate().split("/");
        for (String w: date) {
            if (w.equals("Sunday")){
                sunday.setSelected(true);
            }
        }
        for (String w: date) {
            if (w.equals("Monday")){
                monday.setSelected(true);
            }
        }
        for (String w: date) {
            if (w.equals("Tuesday")) {
                tuesday.setSelected(true);
            }
        }
        for (String w: date) {
            if (w.equals("Wednesday")) {
                wednesday.setSelected(true);
            }
        }
        for (String w: date) {
            if (w.equals("Thursday")){
                thursday.setSelected(true);
            }
        }
        for (String w: date){
            if (w.equals("Friday")){
                friday.setSelected(true);
            }
        }
        for (String w: date) {
            if (w.equals("Saturday")){
                saturday.setSelected(true);
            }
        }

        if (dataList.checkNameWeekly(weeklyName.getText())){
            submit.setDisable(false);
        }
        else {
            submit.setDisable(true);
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setHeaderText("Error!!!");
//            alert.setContentText("Please enter new name!!!");
//            alert.show();
        }

        if (selectedWeekly.getStatus().equals("Success")){
            weeklyName.setText(selectedWeekly.getName());
            priorityWL.setValue(selectedWeekly.getPriority());
            statusWL.setValue(selectedWeekly.getStatus());
            categoryWL.setValue(selectedWeekly.getCategory());
            update.setDisable(true);
        }

        else {
            weeklyName.setText(selectedWeekly.getName());
            priorityWL.setValue(selectedWeekly.getPriority());
            statusWL.setValue(selectedWeekly.getStatus());
            categoryWL.setValue(selectedWeekly.getCategory());
//            sunday.setSelected(false);
//            monday.setSelected(false);
//            tuesday.setSelected(false);
//            wednesday.setSelected(false);
//            thursday.setSelected(false);
//            friday.setSelected(false);
//            saturday.setSelected(false);
            submit.setDisable(false);
        }
    }

    private void clearSelectedWeekly() {
        selectedWeekly = null;
        weeklyName.setText(null);
        hourStart.setValue(null);
        minStart.setValue(null);
        hourFin.setValue(null);
        minFin.setValue(null);
        priorityWL.setValue(null);
        statusWL.setValue(null);
        sunday.setSelected(false);
        monday.setSelected(false);
        tuesday.setSelected(false);
        wednesday.setSelected(false);
        thursday.setSelected(false);
        friday.setSelected(false);
        saturday.setSelected(false);
        submit.setDisable(true);
    }

    @FXML
    public void handleUpdateButton(ActionEvent event) {

        StringBuilder date = new StringBuilder();
        ArrayList<String> day = new ArrayList<>();
        if (sunday.isSelected()){day.add("Sunday");}
        if (monday.isSelected()){day.add("Monday");}
        if (tuesday.isSelected()){day.add("Tuesday");}
        if (wednesday.isSelected()){day.add("Wednesday");}
        if (thursday.isSelected()){day.add("Thursday");}
        if (friday.isSelected()){day.add("Friday");}
        if (saturday.isSelected()){day.add("Saturday");}
        int j = 0 ;
        for (String i : day ){
            date.append(i);
            j++;
            if (j == day.size()){
            }
            else{
                date.append("/");
            }
        }
        selectedWeekly.setDate(date.toString());


        if (weeklyName.getText() != null && hourStart.getValue() != null && minStart.getValue() != null &&
                hourFin.getValue() != null && minFin.getValue() != null && priorityWL.getValue() != null && statusWL.getValue() != null) {

            if (hourStart.getValue() == hourFin.getValue()) {
                if (minStart.getValue() < minFin.getValue()) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
                    alert.setTitle("Please check your data!!!");
                    alert.showAndWait();
                    if (alert.getResult().getText().equals("Yes")) {
//                        System.out.println(weekly.toString());
//                        dataList.addGenWork(weekly);

//                        workDataSource.setData(dataList);
                        System.out.println(weekly.toString());
//                        dataList.addWeekWork(weekly); // แก้หน้า DataList

                        workDataSource.setData(dataList);
//                        warningLabel.setText(null);
                        showWeeklyData();

                    }
                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText("Error!!!");
                    alert.setContentText("Please complete the correct time!!!");
                    alert.show();
                }
            }else if (hourStart.getValue() < hourFin.getValue()){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
                alert.setTitle("Please check your data!!!");
                alert.showAndWait();
                if (alert.getResult().getText().equals("Yes")) {
//                    System.out.println(weekly.toString());
//                    dataList.addGenWork(weekly);

//                    workDataSource.setData(dataList);
//                    System.out.println(weekly.toString());
//                    dataList.addWeekWork(weekly);

                    workDataSource.setData(dataList);
//                    warningLabel.setText(null);
                    showWeeklyData();
                }
            }
            selectedWeekly.setStartDate(hourStart.getValue() + ":" + minStart.getValue());
            selectedWeekly.setLastDate(hourFin.getValue() + ":" + minFin.getValue());




            selectedWeekly.setName(weeklyName.getText());

            selectedWeekly.setPriority(priorityWL.getValue());
            selectedWeekly.setStatus(statusWL.getValue());
            clearSelectedWeekly();
            weeklyTableView.refresh();
            weeklyTableView.getSelectionModel().clearSelection();
            workDataSource.setData(dataList);
            warningLabel.setText(null);
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error!!!");
            alert.setContentText("Please complete all information!!!");
            alert.show();
        }
//
    }

    @FXML
    public void handleBackButton(ActionEvent actionEvent) {
        try {
            FXRouter.goTo("homepage");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า homepage ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }
}
