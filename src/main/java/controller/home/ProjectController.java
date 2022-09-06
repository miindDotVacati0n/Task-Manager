package controller.home;

import com.github.saacsos.FXRouter;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
//import model.works.GeneralWorks;
import jdk.nashorn.internal.runtime.regexp.joni.Warnings;
import model.works.GeneralWorks;
import model.works.Project;
import services.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ProjectController {
    @FXML
    TextField projectName, projectLeader ;
    @FXML
    ChoiceBox<String> priorityPJ, statusPJ, categoryPJ;
    @FXML
    Button submit, update;
    @FXML
    Label warningLabel;
    @FXML
    TableView<Project> projectTableView;

    @FXML
    DatePicker datePickerDo, datePickerFin ;

    LocalDate localDateDo, localDateFin;

    private Project project;
    private DataList dataList;
    private DataList dataListCat;
    private DataSource dataSourceCat;
    private DataSource workDataSource;
    private ObservableList<Project> projectObservableList ;
    private Project selectedProject;

    @FXML
    public void initialize() {
        Platform.runLater((Runnable)new Runnable() {


            @Override
            public void run() {
                workDataSource = new ProjectWorkFileDataSource("data", "projectWorks.csv");
                dataList = workDataSource.getData();

                dataSourceCat = new CategoryFileDataSource("data", "category.csv");
                dataListCat = dataSourceCat.getData();
                for (int i = 0; i < dataListCat.getCategoryArrayList().size(); i++) {
                    categoryPJ.getItems().add(dataListCat.getCategoryArrayList().get(i).getCategoryName());
                }
                categoryPJ.getItems().add("No category");

                for (Project p : dataList.getProjectArrayList()) {
                    System.out.println(p.toString());
                }

                priorityPJ.getItems().addAll("Most priority", "Medium priority", "Less priority");
                statusPJ.getItems().addAll("Just Start","Process","Success");

                showProjectData();

                datePickerDo.setOnAction(event -> {
                    if (datePickerDo.getValue() == null){
                    }
                    else {
                        localDateDo = datePickerDo.getValue() ;
                    }
                });

                datePickerFin.setOnAction(event -> {
                    if (datePickerDo.getValue() == null) {
                    }
                    else {
                        localDateFin = datePickerFin.getValue() ;
                        if (localDateDo.isAfter(localDateFin)){
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setHeaderText("Error!!!");
                            alert.setContentText("Please complete the correct time!!!");
                            alert.show();
                            submit.setDisable(true);
                            update.setDisable(true);
                        }
                        else {
                            submit.setDisable(false);
                        }
                    }
                });

                projectTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        showSelectedProject(newValue);
                    }
                });


            }
        });
    }

    private void showProjectData() {
        projectTableView.getColumns().clear();
        projectObservableList = FXCollections.observableArrayList(dataList.getProjectArrayList());
        projectTableView.setItems(projectObservableList);

        ArrayList<StringConfiguration> configs = new ArrayList<>();
        configs.add(new StringConfiguration("title:Project name", "field:name"));
        configs.add(new StringConfiguration("title:Project leader", "field:projectLeader"));
        configs.add(new StringConfiguration("title:Day do", "field:startDate"));
        configs.add(new StringConfiguration("title:Day finish", "field:lastDate"));
        configs.add(new StringConfiguration("title:Priority", "field:priority"));
        configs.add(new StringConfiguration("title:Status", "field:status"));
        configs.add(new StringConfiguration("title:Category", "field:category"));

        for (StringConfiguration conf: configs) {
            TableColumn col = new TableColumn(conf.get("title"));
            col.setCellValueFactory(new PropertyValueFactory<>(conf.get("field")));
            projectTableView.getColumns().add(col);
            if (conf.get("title").equals("Priority")){
                projectTableView.getSortOrder().addAll(col);
                col.setSortType(TableColumn.SortType.DESCENDING);
            }
        }
    }

    @FXML public void handleSubmitAction(ActionEvent event) throws IOException {

        if (projectName.getText().isEmpty() && projectLeader.getText().isEmpty() &&
                datePickerDo.getValue() == null &&
                priorityPJ.getValue() == null &&
                statusPJ.getValue() == null  &&
                categoryPJ.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error!!!");
            alert.setContentText("Please complete all information!!!");
            alert.show();
        }

        else if (!(dataList.checkNameProject(projectName.getText()))){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error!!!");
            alert.setContentText("Please enter new name!!!");
            alert.show();
        }

        else if (projectName.getText() != null && projectLeader.getText() != null && datePickerDo.getValue() != null &&
                priorityPJ.getValue() != null && statusPJ.getValue() != null &&
                dataList.checkNameProject(projectName.getText()) &&
                categoryPJ.getValue() != null) {
            project = new Project(projectName.getText(),
                    projectLeader.getText(),
                    datePickerDo.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    datePickerFin.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    priorityPJ.getValue(),
                    statusPJ.getValue(), categoryPJ.getValue());

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
            alert.setTitle("Please check your data!!!");
            alert.showAndWait();
            if (alert.getResult().getText().equals("Yes")){
                System.out.println(project.toString());
                dataList.addProWork(project); // แก้หน้า DataList

                workDataSource.setData(dataList);
//                warningLabel.setText(null);
                showProjectData();
            }


        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error!!!");
            alert.setContentText("Please complete all information!!!");
            alert.show();
        }

    }

    private void showSelectedProject(Project projectWork) {
        selectedProject = projectWork;
        if (dataList.checkNameGeneral(projectName.getText())){
            submit.setDisable(false);
        }
        else {
            submit.setDisable(true);
//            warningLabel.setText("Please enter new name!!!");
        }

        if (selectedProject.getStatus().equals("Success")) {
            projectName.setText(selectedProject.getName());
            projectLeader.setText(selectedProject.getProjectLeader());
            priorityPJ.setValue(selectedProject.getPriority());
            statusPJ.setValue(selectedProject.getStatus());
            categoryPJ.setValue(selectedProject.getCategory());
            update.setDisable(true);
        }
        else {
            projectName.setText(selectedProject.getName());
            projectLeader.setText(selectedProject.getProjectLeader());
            priorityPJ.setValue(selectedProject.getPriority());
            statusPJ.setValue(selectedProject.getStatus());
            categoryPJ.setValue(selectedProject.getCategory());
            update.setDisable(false);
        }
    }

    private void clearSelectedProject() {
        selectedProject = null;
        projectName.setText(null);
        projectLeader.setText(null);
        priorityPJ.setValue(null);
        statusPJ.setValue(null);
        datePickerDo.setValue(null);
        datePickerFin.setValue(null);
        submit.setDisable(true);
    }

    @FXML
    public void handleUpdateButton(ActionEvent event) {

        if (projectName.getText() != null && projectLeader.getText() != null &&
                datePickerDo.getValue() != null &&
                datePickerFin.getValue() != null &&
                priorityPJ.getValue() != null &&
                statusPJ.getValue() != null) {
            selectedProject.setName(projectName.getText());
            selectedProject.setProjectLeader(projectLeader.getText());

            selectedProject.setPriority(priorityPJ.getValue());
            selectedProject.setStatus(statusPJ.getValue());
            clearSelectedProject();
            projectTableView.refresh();
            projectTableView.getSelectionModel().clearSelection();
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
            System.err.println("ไปที่หน้า home ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }
}
