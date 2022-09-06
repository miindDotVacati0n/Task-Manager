package controller.home;

import com.github.saacsos.FXRouter;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.works.*;
import services.*;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class CategoryController {

    @FXML
    TextField name;
    @FXML
    TableView catTableView;
    @FXML
    Button submit;
    @FXML
    TextArea catFind;

    private DataList dataListCat ;
    private DataSource workDataSourceCat ;
    private ObservableList<Category> categoryObservableList ;
    private DataList dataListGenF ;
    private DataSource workDataSourceGenF ;
    private DataList dataListForF ;
    private DataSource workDataSourceForF ;
    private DataList dataListWeekF ;
    private DataSource workDataSourceWeekF ;
    private DataList dataListProF ;
    private DataSource workDataSourceProF ;

    public void initialize() {
        Platform.runLater((Runnable) new Runnable() {
            @Override
            public void run() {
                workDataSourceGenF = new GeneralWorkFileDataSource("data", "generalWorks.csv");
                workDataSourceWeekF = new WeeklyWorksFileDataSource("data", "weeklyWorks.csv");
                workDataSourceForF = new ForwardWorksFileDataSource("data", "forwardWorks.csv");
                workDataSourceProF = new ProjectWorkFileDataSource("data", "projectWorks.csv");
                workDataSourceCat = new CategoryFileDataSource("data", "category.csv");

                dataListCat = workDataSourceCat.getData();
                dataListGenF = workDataSourceGenF.getData();
                dataListWeekF = workDataSourceWeekF.getData();
                dataListForF = workDataSourceForF.getData();
                dataListProF = workDataSourceProF.getData();
                showCategoryData();

                catFind.setEditable(false);
            }
        });
        catTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null){
                showSelectedCat((Category) newValue);
            }
        });

    }
    private void showSelectedCat(Category category){
        catFind.setText(null);
        for (GeneralWorks g:dataListGenF.genFindCat(category.getCategoryName())) {
            catFind.appendText(g.toString()+"\n"); ;
        }
        if (dataListGenF.genFindCat(category.getCategoryName()).size() == 0 || dataListGenF.genFindCat(category.getCategoryName()).size() == 1) {
            catFind.appendText("General have " + dataListGenF.genFindCat(category.getCategoryName()).size() + " work." + "\n" + "\n");
        }
        else {
            catFind.appendText("General have " + dataListGenF.genFindCat(category.getCategoryName()).size() + " works." + "\n" + "\n");
        }

        //--------------------------------------------------------------------------------------------------//

        for (ForwardWorks f:dataListForF.forFindCat(category.getCategoryName())) {
            catFind.appendText(f.toString()+"\n"); ;
        }
        if (dataListForF.forFindCat(category.getCategoryName()).size() == 0 || dataListForF.forFindCat(category.getCategoryName()).size() == 1) {
            catFind.appendText("Forward have " + dataListForF.forFindCat(category.getCategoryName()).size() + " work." + "\n" + "\n");
        }
        else {
            catFind.appendText("Forward have " + dataListForF.forFindCat(category.getCategoryName()).size() + " works." + "\n" + "\n");
        }


        //--------------------------------------------------------------------------------------------------//

        for (WeeklyWorks f:dataListWeekF.weekFindCat(category.getCategoryName())) {
            catFind.appendText(f.toString()+"\n"); ;
        }
        if (dataListWeekF.weekFindCat(category.getCategoryName()).size() == 0 || dataListWeekF.weekFindCat(category.getCategoryName()).size() == 1) {
            catFind.appendText("Weekly have " + dataListWeekF.weekFindCat(category.getCategoryName()).size() + " work." + "\n" + "\n");
        }
        else {
            catFind.appendText("Weekly have " + dataListWeekF.weekFindCat(category.getCategoryName()).size() + " works." + "\n" + "\n");
        }

        //--------------------------------------------------------------------------------------------------//

        for (Project p:dataListProF.proFindCat(category.getCategoryName())) {
            catFind.appendText(p.toString()+"\n"); ;
        }
        if (dataListProF.proFindCat(category.getCategoryName()).size() == 0 || dataListProF.proFindCat(category.getCategoryName()).size() == 1) {
            catFind.appendText("Project have " + dataListProF.proFindCat(category.getCategoryName()).size() + " work." + "\n" + "\n");
        }
        else {
            catFind.appendText("Project have " + dataListProF.proFindCat(category.getCategoryName()).size() + " works." + "\n" + "\n");
        }
    }

    private void showCategoryData() {
        catTableView.getColumns().clear();
        categoryObservableList = FXCollections.observableArrayList(dataListCat.getCategoryArrayList());
        catTableView.setItems(categoryObservableList);

        ArrayList<StringConfiguration> configs = new ArrayList<>();
        configs.add(new StringConfiguration("title:Category", "field:categoryName"));
        

        for (StringConfiguration conf: configs) {
            TableColumn col = new TableColumn(conf.get("title"));
            col.prefWidthProperty().bind(catTableView.widthProperty().multiply(1.0));
            col.setCellValueFactory(new PropertyValueFactory<>(conf.get("field")));
            catTableView.getColumns().add(col);

        }
    }

    @FXML public void handleSubmitAction(ActionEvent event) throws IOException {
        if (name.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error!!!");
            alert.setContentText("Please complete the correct category!!!");
            alert.show();
            submit.setDisable(true);

        }
        if (!(dataListCat.checkNameCat(name.getText()))){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error!!!");
            alert.setContentText("Please complete the new category!!!");
            alert.show();

        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
            alert.setTitle("Please check your data!!!");
            alert.showAndWait();
            if (alert.getResult().getText().equals("Yes")) {
                dataListCat.addCatWork(new Category(name.getText()));
                workDataSourceCat.setData(dataListCat);
                catTableView.getColumns().clear();
                showCategoryData();
            }
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
