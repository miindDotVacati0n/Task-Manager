package controller.home;

import com.github.saacsos.FXRouter;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.works.ForwardWorks;
import model.works.Project;
import services.*;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ForwardWorksController {
    @FXML
    TextField forwardName, leaderWork;
    @FXML
    ChoiceBox<String> priorityFW, statusFW, categoryFW;
    @FXML
    ChoiceBox<Integer> hourStart, minStart;
    @FXML
    Button submit, update;
    @FXML
    Label warningLabel;
    @FXML
    TableView<ForwardWorks> forwardTableView;

    @FXML
    DatePicker datePickerFW ;

    private ForwardWorks forward ;
    private DataList dataList ;
    private DataList dataListCat;
    private DataSource dataSourceCat;
    private DataSource workDataSource ;
    private ObservableList<ForwardWorks> forwardWorksObservableList ;
    private ForwardWorks selectedForward ;

    @FXML
    public void initialize() {
        Platform.runLater((Runnable)new Runnable() {


            @Override
            public void run() {
                workDataSource = new ForwardWorksFileDataSource("data", "forwardWorks.csv");
                dataList = workDataSource.getData();

                dataSourceCat = new CategoryFileDataSource("data", "category.csv");
                dataListCat = dataSourceCat.getData();
                for (int i = 0; i < dataListCat.getCategoryArrayList().size(); i++) {
                    categoryFW.getItems().add(dataListCat.getCategoryArrayList().get(i).getCategoryName());
                }
                categoryFW.getItems().add("No category");

                for (ForwardWorks f : dataList.getForwardWorksArrayList()) {
                    System.out.println(f.toString());
                }

                priorityFW.getItems().addAll("Most priority", "Medium priority", "Less priority");
                statusFW.getItems().addAll("Just Start","Process","Success");

                for (int i = 0 ; i < 24 ; i++){
                    hourStart.getItems().add(i);
                }
                for (int i = 0 ; i < 60 ; i++){
                    minStart.getItems().add(i);
                }


                showForwardData();


                forwardTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        showSelectedForward(newValue);
                    }
                });


            }
        });
    }

    private void showForwardData() {
        forwardTableView.getColumns().clear();
        forwardWorksObservableList = FXCollections.observableArrayList(dataList.getForwardWorksArrayList());
        forwardTableView.setItems(forwardWorksObservableList);

        ArrayList<StringConfiguration> configs = new ArrayList<>();
        configs.add(new StringConfiguration("title:Work name", "field:name"));
        configs.add(new StringConfiguration("title:Forward leader", "field:forwardLeader"));
        configs.add(new StringConfiguration("title:Day Assign", "field:madeDate"));
        configs.add(new StringConfiguration("title:Time Assign", "field:startDate"));
        configs.add(new StringConfiguration("title:Priority", "field:priority"));
        configs.add(new StringConfiguration("title:Status", "field:status"));
        configs.add(new StringConfiguration("title:Category", "field:category"));

        for (StringConfiguration conf: configs) {
            TableColumn col = new TableColumn(conf.get("title"));
            col.setCellValueFactory(new PropertyValueFactory<>(conf.get("field")));
            forwardTableView.getColumns().add(col);
            if (conf.get("title").equals("Priority")){
                forwardTableView.getSortOrder().addAll(col);
                col.setSortType(TableColumn.SortType.DESCENDING);
            }
        }
    }

    @FXML public void handleSubmitAction(ActionEvent event) throws IOException {

        if (forwardName.getText().isEmpty() && leaderWork.getText().isEmpty() &&
                hourStart.getValue() == null && minStart == null &&
                datePickerFW.getValue() == null &&
                priorityFW.getValue() == null &&
                statusFW.getValue() == null &&
                categoryFW.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error!!!");
            alert.setContentText("Please complete all information!!!");
            alert.show();

        }

        else if (!(dataList.checkNameForward(forwardName.getText()))){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error!!!");
            alert.setContentText("Please enter new name!!!");
            alert.show();
        }

        else if (forwardName.getText() != null && leaderWork.getText() != null && datePickerFW.getValue() != null &&
                hourStart.getValue() != null && minStart.getValue() != null &&
                priorityFW.getValue() != null && statusFW.getValue() != null &&
                dataList.checkNameForward(forwardName.getText()) && categoryFW.getValue() != null) {
            forward = new ForwardWorks(forwardName.getText(), leaderWork.getText(), datePickerFW.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    hourStart.getValue() + ":" + minStart.getValue(),
                    priorityFW.getValue(), statusFW.getValue(), categoryFW.getValue());

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
            alert.setTitle("Please check your data!!!");
            alert.showAndWait();
            if (alert.getResult().getText().equals("Yes")) {
                System.out.println(forward.toString());
                dataList.addForWork(forward); // แก้หน้า DataList

                workDataSource.setData(dataList);
                showForwardData();
            }
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error!!!");
            alert.setContentText("Please complete all information!!!");
            alert.show();
        }
    }

    private void showSelectedForward(ForwardWorks forwardWorks) {
        selectedForward = forwardWorks;
        if (dataList.checkNameForward(forwardName.getText())){
            submit.setDisable(false);
        }
        else {
            submit.setDisable(true);
        }

        if (selectedForward.getStatus().equals("Success")) {
            forwardName.setText(selectedForward.getName());
            leaderWork.setText(selectedForward.getForwardLeader());
            priorityFW.setValue(selectedForward.getPriority());
            statusFW.setValue(selectedForward.getStatus());
            categoryFW.setValue(selectedForward.getCategory());
            update.setDisable(true);
        }
        else {
            forwardName.setText(selectedForward.getName());
//            leaderWork.setText(selectedForward.getForwardLeader());
            priorityFW.setValue(selectedForward.getPriority());
            statusFW.setValue(selectedForward.getStatus());
            categoryFW.setValue(selectedForward.getCategory());
            update.setDisable(false);
        }

    }

    private void clearSelectedForward() {
        selectedForward = null;
        forwardName.setText(null);
        leaderWork.setText(null);
        priorityFW.setValue(null);
        statusFW.setValue(null);
        datePickerFW.setValue(null);
        submit.setDisable(true);
    }

    @FXML
    public void handleUpdateButton(ActionEvent event) {

        if (forwardName.getText() != null && leaderWork.getText() != null &&
                datePickerFW.getValue() != null &&
                priorityFW.getValue() != null &&
                statusFW.getValue() != null) {
            selectedForward.setName(forwardName.getText());
            selectedForward.conForName(leaderWork.getText());
            selectedForward.setPriority(priorityFW.getValue());
            selectedForward.setStatus(statusFW.getValue());
            clearSelectedForward();
            forwardTableView.refresh();
            forwardTableView.getSelectionModel().clearSelection();
            workDataSource.setData(dataList);
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
            System.err.println("ไปที่หน้า homepage ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }
}
