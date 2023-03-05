package User;
public class Admin extends User {
             
    private String ime;
    private String prezime;
    private static int idAdmin=0;
    private int ID_Administratora;
    

    public Admin(int ID, String username, String password, String ime, String prezime) {
        super(ID, username, password);
        this.ime = ime;
        this.prezime = prezime;
        idAdmin++;
        ID_Administratora=idAdmin;
    }
    
    public Admin(int ID, String ime, String prezime,int ID_Administratora) {
        super(ID);
        this.ime = ime;
        this.prezime = prezime;
        this.ID_Administratora=ID_Administratora;
    }
    

    @Override
    public int getID() {
        return super.getID();
    }

    public String getIme() {
        return ime;
    }

    @Override
    public String getPassword() {
        return super.getPassword();
    }

    public String getPrezime() {
        return prezime;
    }

    public int getID_Administratora() {
        return ID_Administratora;
    }
    
    
    
    public void setPassword(String password) {
        super.setPassword(password);
    }


    @Override
    public String getUsername() {
        return super.getUsername();
    }
    
    
    @Override    
    public String toString() {    
        return super.getID()+" "+this.ime+" "+this.prezime+" "+ID_Administratora; 
    }
    
}
