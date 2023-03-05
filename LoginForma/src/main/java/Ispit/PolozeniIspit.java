package Ispit;


public class PolozeniIspit extends PrijavljaniIspit {
    int ID;
    private int ocena;

    public PolozeniIspit(int ID,PrijavljaniIspit pi, int ocena) {
        super(pi.ID,pi.predmet,pi.student);
        this.ID=ID;
        this.ocena = ocena;
    }

    public PolozeniIspit(int ID,int ocena) {
        super(ID);
        this.ocena = ocena;
    }
    
    public int getOcena() {
        return ocena;
    }
}
