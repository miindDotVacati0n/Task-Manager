package services;

import model.works.Project;

import java.io.*;

public class ProjectWorkFileDataSource implements DataSource {
    private String fileDirectoryName;
    private String fileName;
    private DataList dataList;

    public ProjectWorkFileDataSource(String fileDirectoryName, String fileName) {
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
            if (data[0].equals("Project")){
                model.works.Project project = new model.works.Project(data[1].trim(), data[2].trim(), data[3].trim(), data[4].trim(), data[5].trim(), data[6].trim(), data[7].trim());
                dataList.addProWork(project);
            }
            else{}

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
            for (Project work : list.getProjectArrayList()) {
                String line = "Project" + ","
                        + work.getName() + ","
                        + work.getProjectLeader()+ ","
                        + work.getStartDate() + ","
                        + work.getLastDate() + ","
                        + work.getPriority() + ","
                        + work.getStatus() + ","
                        + work.getCategory();

                writer.append(line);
                writer.newLine();

            }

            writer.close();
        } catch (IOException e) {
            System.err.println("Cannot write " + filePath);
        }
    }
}
