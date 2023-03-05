package Ispit;

import User.Professor;

public class Predmet {
    private int ID;
    private String imePredmeta;
    private Professor professor;

    public Predmet(int ID,String imePredmeta,Professor professor) {
        this.ID=ID;
        this.imePredmeta=imePredmeta;
        this.professor=professor;
    }

    public int getID() {
        return ID;
    }

    public String getImePredmeta() {
        return imePredmeta;
    }

    public Professor getProfessor() {
        return professor;
    }
    
}

