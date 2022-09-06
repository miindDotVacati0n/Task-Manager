package controller.home;

import com.github.saacsos.FXRouter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import services.PDFFileDataSource;

import java.io.IOException;


public class ManualController {
    PDFFileDataSource pdfFileDataSource;

    @FXML
    private void handlePDFButton() throws IOException{
        pdfFileDataSource = new PDFFileDataSource("pdfFile", "6210451195.pdf");
        pdfFileDataSource.PDFFile();
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
