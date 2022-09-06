package model.works;

public class WeeklyWorks extends GeneralWorks{
    private String date;

    public WeeklyWorks(String name, String date, String startDate, String lastDate, String priority, String status, String category){
        super(name, date, startDate, lastDate, priority, status, category);
        this.date = date ;
    }

    public void conDate(String name){
        if (!(date.contains(name))){
            setDate(getDate() + "/" + date);
        }
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


}
