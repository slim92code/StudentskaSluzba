package User;
import Ispit.Predmet;
import java.util.ArrayList;
import java.util.List;

public class Professor extends User{
    
    private String ime;
    private String prezime;
    public int brojCasovaNedeljno; //TO DO;
    
    public List<Predmet> Predmeti;
    public List<Student> ListaStudenataMentor;
    

    public Professor(int ID,String username,String password,String ime,String prezime ) {
        super(ID, username, password);
        this.ime=ime;
        this.prezime=prezime;  
        Predmeti=new ArrayList<Predmet>();
        ListaStudenataMentor=new ArrayList<Student>();
    }
    
    public Professor(int ID,String ime,String prezime ) {
        super(ID);
        this.ime=ime;
        this.prezime=prezime;  
        Predmeti=new ArrayList<Predmet>();
        ListaStudenataMentor=new ArrayList<Student>();
    }
    
    public int getID() {
        return super.getID();
    }

    public String getIme() {
        return ime;
    }

    public String getPassword() {
        return super.getPassword();
    }

    public String getPrezime() {
        return prezime;
    }
    
    public String getUsername() {
        return super.getUsername();
    }

    public int getBrojCasovaNedeljno() {
        return brojCasovaNedeljno;
    }
    
    
    
    public void setPassword(String password) {
        super.setPassword(password);
    }
    
    public void DodavanjePredmeta(Predmet p){
        for (Predmet predmet : Predmeti) {
            if(predmet==p){
                return;
            }
        }
        Predmeti.add(p);
    }
        
    public void BrisanjePredmeta(Predmet p){
        Predmeti.remove(p);
    }
    
    public void DodavanjeMentora(Student s){
        for(Student student : ListaStudenataMentor){
            if(student==s){
                return;
            }
        }
        ListaStudenataMentor.add(s);
    }
    
    public void ZavrsenaMentorstvo(Student s){
        ListaStudenataMentor.remove(s);
    }

    
    /////
    
    @Override 
    public String toString() {    
        return super.getID()+" "+" "+this.ime+" "+this.prezime;
    }
    
}
