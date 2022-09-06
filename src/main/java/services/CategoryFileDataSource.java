package services;

import model.works.Category;
import model.works.ForwardWorks;

import java.io.*;

public class CategoryFileDataSource implements DataSource{

    private String fileDirectoryName;
    private String fileName;
    private DataList dataList;

    public CategoryFileDataSource(String fileDirectoryName, String fileName) {
        this.fileDirectoryName = fileDirectoryName;
        this.fileName = fileName;
        checkFileIsExisted();
    }

    private void checkFileIsExisted() {
        File file = new File(fileDirectoryName);
        if (!file.exists()) {
            file.mkdirs();
        }
        String filePath = fileDirectoryName + File.separator + fileName;
        file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.err.println("Cannot create " + filePath);
            }
        }
    }

    public void readData() throws IOException {
        String filePath = fileDirectoryName + File.separator + fileName;
        File file = new File(filePath);
        FileReader fileReader = new FileReader(file);
        BufferedReader reader = new BufferedReader(fileReader);
        String line = "";
        while ((line = reader.readLine()) != null) {
            String[] data = line.split(",");
            if (data[0].equals("Category")){
                model.works.Category category = new model.works.Category(data[1].trim());
                dataList.addCatWork(category);
            }

        }
        reader.close();
    }

    @Override
    public DataList getData() {
        try {
            dataList = new DataList();
            readData();
        } catch (FileNotFoundException e) {
            System.err.println(this.fileName + " not found");
        } catch (IOException e) {
            System.err.println("IOException from reading " + this.fileName);
        }
        return dataList;
    }

    @Override
    public void setData(DataList list) {
        String filePath = fileDirectoryName + File.separator + fileName;
        File file = new File(filePath);
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file);
            BufferedWriter writer = new BufferedWriter(fileWriter);
            for (Category work : list.getCategoryArrayList()) {
                String line = "Category" + ","
                        + work.getCategoryName() ;

                writer.append(line);
                writer.newLine();

            }

            writer.close();
        } catch (IOException e) {
            System.err.println("Cannot write " + filePath);
        }
    }
}
