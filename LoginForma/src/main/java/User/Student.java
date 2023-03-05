package User;
import Ispit.PolozeniIspit;
import Ispit.Predmet;
import Ispit.PrijavljaniIspit;

import java.util.ArrayList;
import java.util.List;


public class Student extends User {
    
    private String ime;
    private String prezime;
    private int brojIndeksa;
    private int godinaUpisa;
    private static int brojStudenata=0;
    public double prosek=0.0;
    public int semestar;
    public String smer;
    
    public static int trenutnaKalendarskaGodina=2023;
    public List<PolozeniIspit> polozeniIspiti;
    public List<PrijavljaniIspit> prijavljeniIspiti;
    
    
    
    public Student(int ID, String username, String password,String ime, String prezime,int semestar,String smer) {
        super(ID, username, password);
        this.ime = ime;
        this.prezime = prezime;
        brojStudenata++;
        brojIndeksa=brojStudenata;
        polozeniIspiti=new ArrayList<PolozeniIspit>();
        prijavljeniIspiti=new ArrayList<PrijavljaniIspit>();
        this.semestar=semestar;
        this.smer=smer;
    }

    public Student(int ID, String ime, String prezime, int brojIndeksa, int godinaUpisa) {
        super(ID);
        this.ime = ime;
        this.prezime = prezime;
        this.brojIndeksa = brojIndeksa;
        this.godinaUpisa = godinaUpisa;
    }

    public Student(int ID,String ime, String prezime,int brojIndeksa,int godinaUpisa,int semestar,String smer) {
        super(ID);
        this.ime = ime;
        this.prezime = prezime;
        this.brojIndeksa=brojIndeksa;
        this.godinaUpisa=godinaUpisa;
        polozeniIspiti=new ArrayList<PolozeniIspit>();
        prijavljeniIspiti=new ArrayList<PrijavljaniIspit>();
        this.semestar=semestar;
        this.smer=smer;
    }

    public Student(int ID, String ime, String prezime, int brojIndeksa) {
        super(ID);
        this.ime = ime;
        this.prezime = prezime;
        this.brojIndeksa = brojIndeksa;
        this.godinaUpisa = trenutnaKalendarskaGodina;
    }
    
    

    public int getID() {
        return super.getID();
    }

    public String getIme() {
        return ime;
    }

    public int getGodinaUpisa() {
        return godinaUpisa;
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
    
    public void setPassword(String password) {
        super.setPassword(password);
    }
    public static void setBrojStudenata(int brojStudenata) {
        Student.brojStudenata = brojStudenata;
    }

    public int getBrojIndeksa() {
        return brojIndeksa;
    }

    public int getSemestar() {
        return semestar;
    }

    public String getSmer() {
        return smer;
    }
    
    
    /////
    
    public void PrijavaIspita(Predmet p){
        for(PrijavljaniIspit ispit : prijavljeniIspiti) {
            if(ispit.getPredmet()==p){
                return;
            }
        }     

       // prijavljeniIspiti.add(p);to doo pravljenje prijave ispita
    }
        
    
    public void OtkazivanjePijave(PrijavljaniIspit p){
        prijavljeniIspiti.remove(p);
    }
    
    
    public void CiscenjePrijava(){
        prijavljeniIspiti.clear();
    }
    
    
    public void CiscenjePolozenihIspita(){
        polozeniIspiti.clear();
    }
    
    
    @Override    
    public String toString() {    
        return super.getID()+" "+this.ime+" "+this.prezime+" "+this.brojIndeksa+"/"+this.godinaUpisa;    
    }
    
}
