package User;
public abstract class User {
    
    private int ID;
    private String username=null;
    private String password=null;

    public User(int ID) {
        this.ID = ID;
    }

    public User(int ID, String username, String password) {
        this.ID = ID;
        this.username = username;
        this.password = password;
    }
    
////////////////////////


    public int getID() {
        return ID;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    protected void setPassword(String password) {
        this.password = password;
    }
    
    
    @Override
    public String toString() {
        return ID+" "+username+" "+password;
        
    }
   
}
