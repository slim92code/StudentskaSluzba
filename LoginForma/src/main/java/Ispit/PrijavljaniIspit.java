package Ispit;

import User.Student;


public class PrijavljaniIspit {
    int ID;
    Predmet predmet;
    Student student;
    
    public PrijavljaniIspit(int ID,Predmet predmet,Student student){
        this.ID=ID;
        this.predmet=predmet;
        this.student=student;
    }

    public PrijavljaniIspit(int ID){
        this.ID=ID;
    }

    public int getID() {
        return ID;
    }
    public Predmet getPredmet() {
        return predmet;
    }
    public Student getStudent() {
        return student;
    }
    

    
}
