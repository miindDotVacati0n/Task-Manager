package model.works;

public class Project extends GeneralWorks {
    private String projectLeader ;

    public Project(String name, String projectLeader, String startDate, String lastDate, String priority, String status, String category) {
        super(name, projectLeader, startDate, lastDate, priority, status, category);
//        this.name = name;
        this.projectLeader = projectLeader;
//        this.startDate = startDate;
//        this.lastDate = lastDate;
//        this.priority = priority;
//        this.status = status;
//        this.category = category;
    }

    public String getProjectLeader() {
        return projectLeader;
    }

    public void setProjectLeader(String projectLeader) {
        this.projectLeader = projectLeader;
    }



}
