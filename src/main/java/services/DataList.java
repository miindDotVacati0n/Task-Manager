package services;

import model.works.*;


import java.util.ArrayList;

public class DataList {
    private ArrayList<GeneralWorks> generalWorkArrayList;
    private ArrayList<Project> projectArrayList;
    private ArrayList<ForwardWorks> forwardWorksArrayList;
    private ArrayList<WeeklyWorks> weeklyWorksArrayList;
    private ArrayList<Category> categoryArrayList;


    public DataList(){
        this.generalWorkArrayList = new ArrayList<>();
        this.projectArrayList = new ArrayList<>();
        this.forwardWorksArrayList = new ArrayList<>();
        this.weeklyWorksArrayList = new ArrayList<>();
        this.categoryArrayList = new ArrayList<>();
    }



    public void addGenWork(GeneralWorks generalWork) {
        this.generalWorkArrayList.add(generalWork);
    }
    public void addProWork(Project project) { this.projectArrayList.add(project); }
    public void addForWork(ForwardWorks forwardWorks) { this.forwardWorksArrayList.add(forwardWorks); }
    public void addWeekWork(WeeklyWorks weeklyWorks) { this.weeklyWorksArrayList.add(weeklyWorks); }
    public void addCatWork(Category category) { this.categoryArrayList.add(category); }


    public ArrayList<GeneralWorks> getGeneralWorkArrayList() { return generalWorkArrayList; }
    public ArrayList<Project> getProjectArrayList() { return projectArrayList; }
    public ArrayList<ForwardWorks> getForwardWorksArrayList() { return forwardWorksArrayList; }
    public ArrayList<WeeklyWorks> getWeeklyWorksArrayList() { return weeklyWorksArrayList; }
    public ArrayList<Category> getCategoryArrayList() { return categoryArrayList;}


    public boolean checkNameGeneral(String name){
        for (GeneralWorks g : getGeneralWorkArrayList()){
            if (g.getName().equals(name)){ return false; }
        }
        return true;
    }

    public boolean checkNameProject(String name){
        for (Project p : getProjectArrayList()){
            if (p.getName().equals(name)){ return false; }
        }
        return true;
    }

    public boolean checkNameWeekly(String name){
        for (WeeklyWorks w : getWeeklyWorksArrayList()){
            if (w.getName().equals(name)){ return false; }
        }
        return true;
    }

    public boolean checkNameForward(String name){
        for (ForwardWorks f : getForwardWorksArrayList()){
            if (f.getName().equals(name)){ return false; }
        }
        return true;
    }

    public boolean checkNameCat(String name){
        for (Category c : getCategoryArrayList()){
            if (c.getCategoryName().equals(name)){ return false; }
        }
        return true;
    }

    //--------------------------------------------------------------------------------//

    public ArrayList<GeneralWorks> genFindCat(String category){
        ArrayList<GeneralWorks> genFind = new ArrayList<>();
        for (GeneralWorks g: generalWorkArrayList) {
            if(g.getCategory().equals(category)){
                genFind.add(g) ;
            }
        }
        return genFind ;
    }

    public ArrayList<ForwardWorks> forFindCat(String category){
        ArrayList<ForwardWorks> forFind = new ArrayList<>();
        for (ForwardWorks f : forwardWorksArrayList) {
            if (f.getCategory().equals(category)){
                forFind.add(f);
            }
        }
        return forFind;
    }

    public ArrayList<WeeklyWorks> weekFindCat(String category){
        ArrayList<WeeklyWorks> weekFind = new ArrayList<>();
        for (WeeklyWorks w : weeklyWorksArrayList) {
            if (w.getCategory().equals(category)){
                weekFind.add(w);
            }
        }
        return weekFind;
    }

    public ArrayList<Project> proFindCat(String category){
        ArrayList<Project> proFind = new ArrayList<>();
        for (Project p : projectArrayList) {
            if (p.getCategory().equals(category)){
                proFind.add(p);
            }
        }
        return proFind;
    }

}