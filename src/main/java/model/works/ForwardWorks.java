package model.works;

public class ForwardWorks extends GeneralWorks {

    private String forwardLeader ;

    public ForwardWorks(String name, String forwardLeader, String madeDate, String startDate, String priority, String status, String category) {
        super(name, forwardLeader, madeDate, startDate, priority, status, category);
        this.name = name;
        this.forwardLeader = forwardLeader;
        this.madeDate = madeDate;
        this.startDate = startDate;
        this.priority = priority;
        this.status = status;
        this.category = category;
    }

    public void conForName(String forwardLeader){
        setForwardLeader(getForwardLeader() + "->" + forwardLeader);
    }



    public String getForwardLeader() {
        return forwardLeader;
    }

    public void setForwardLeader(String forwardLeader) {
        this.forwardLeader = forwardLeader;
    }
}
