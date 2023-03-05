
package LoginForma.demo;

import Ispit.PolozeniIspit;
import Ispit.Predmet;
import Ispit.PrijavljaniIspit;
import User.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DbConnection {
    Connection conn=null;
    Statement stmt = null;
    String sql;
    
    
    public DbConnection() {
        try {
            conn=DriverManager.getConnection("jdbc:mariadb://localhost/loginforma",
                                            "root",
                                            "");
            stmt = conn.createStatement();
            
            System.out.println("Uspesno sam se povezao"); 
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Nisam se povezao sa bazom!!!");    
        }
        
                       
    }
    
    /*
            sql="select * from korisnik";
        ResultSet rs = stmt.executeQuery(sql);
        while(rs.next()) {
            try {
                int id = rs.getInt("ID");
                String ime = rs.getString("Username");
                String prezime = rs.getString("Password");
                
                System.out.println(id+" "+ime+" "+prezime);
 
            } 
            catch (Exception e) {
                System.out.println("nisam uspeo");
            }
        }
    select * "
  + "from korisnik "
  + "where Username = '"+username+"'";
    
    
    za username = Aleksa123 sam napravio ovaj string:
    
    select*
    from korisnik
    where Username = 'Aleksa123'
    
    sql="insert into korisnik(Username,Password)"
           +"values('"+u.getUsername()+"','"+u.getPassword()+"')";
        //System.out.println(sql); 
        
    
    */
    
    public void addSubject(Predmet p) {
        sql="insert into subject(Name,ID_Professor) "
           +"values('"+p.getImePredmeta()+"',"+p.getProfessor().getID()+")";
       
        try {
            stmt.executeQuery(sql);
            
        } catch (Exception e) {
            System.out.println("Nije dodat predmet "+sql);
        }
    }    

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////   

    public void addPassedExam (PolozeniIspit p) {

        sql="insert into polozeniispiti(ID_PrijaveIspita,Ocena) "
           +"values("+p.getID()+","+p.getOcena()+")";

        try {
            stmt.executeQuery(sql);
        } catch (Exception e) {
            System.out.println("Nije dodat POLOZEN ISPIT "+sql);
        }
    }    

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////       
        
 
    
     public int probaLogin(String username,String password) {
        ResultSet rs;
        sql="select * "
          + "from korisnik "
          + "where Username = '"+username+"'";
        try {
            rs = stmt.executeQuery(sql);
            if(rs.next()==false){
                return -1;
            }
            int ID=rs.getInt("ID");
            String pass=rs.getString("Password");
            //stirngovi se ne ispituju sa == nego sa equals
            //equals vraca true ili false u slucaju da li su isti
            if(pass.equals(password)){
                //User u1=new User(ID, user, pass);
                return ID;
            }
            else{
                
                return -2;
            }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Nisam uspeo da izvrsim SQL upit");
        }
        
        return -3;
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////    

    public User getUser(int ID){
        ResultSet rs;

        sql="select * "
           +"from Student "
           +"where ID="+ID;

        try {
            rs=stmt.executeQuery(sql);
            String ime;
            String prezime;
            
            if(rs.next()!=false){
                ime=rs.getString("FirstName");
                prezime=rs.getString("LastName");
                int brojIndeksa=rs.getInt("BrojIndeksa");
                int godinaUpisa=rs.getInt("GodinaUpisa");
                int semestar=rs.getInt("Semestar");
                String smer=rs.getString("Smer");
               
                return new Student(ID,ime,prezime,brojIndeksa,godinaUpisa,semestar,smer);
            }
            sql="select * "
               +"from Professor "
               +"where ID="+ID;

            rs=stmt.executeQuery(sql);
            if(rs.next()!=false){
                ime=rs.getString("FirstName");
                prezime=rs.getString("LastName");

                return new Professor(ID,ime,prezime);
            }

            sql="select * "
               +"from administrator "
               +"where ID="+ID;

            rs=stmt.executeQuery(sql);
            if(rs.next()!=false){
                ime=rs.getString("FirstName");
                prezime=rs.getString("LastName");
                int ID_Administratora=rs.getInt("ID_Administratora");
                return new Admin(ID,ime,prezime,ID_Administratora);
            }


        } catch (Exception e) {
            System.out.println("Ne mogu da dodjem do korisnika");
        }



        return null;
    }
     
/*
    u slucaju da je username=kaca123 a password=Kaca1389
    napravi string koji izgleda ovako:
     
    INSERT into korisnik(Username,Password)
    VALUES('Kaca123','Kaca1389')
*/
     
    public void probaRegistracija(User u){
        ResultSet rs;
        sql="select * "
          + "from korisnik "
          + "where Username = '"+u.getUsername()+"'";
        try {
            rs = stmt.executeQuery(sql); 
            if(rs.next()==true) {
                System.out.println("NE MOZE: Dupli username");
                return;
            }
        } catch (Exception e) {
            System.out.println("Nisam uspeo da izvrsim SQL upit 2");
        }

        
        
        sql="insert into korisnik(Username,Password)"
           +"values('"+u.getUsername()+"','"+u.getPassword()+"')";
        //System.out.println(sql); 
        
        try {
            stmt.executeQuery(sql);
            
        
            
            sql="select * "
          + "from korisnik "
          + "where Username = '"+u.getUsername()+"' and Password='"+u.getPassword()+"'";
            
            rs = stmt.executeQuery(sql); 
            
            if(rs.next()==true) {
                
                u.setID(rs.getInt("ID"));
                
                if(u instanceof Student){
                    Student s=(Student)u;
                    sql="insert into Student(ID,FirstName,LastName,BrojIndeksa,GodinaUpisa ,Semestar,Smer)"
                   +"values(" + s.getID() + ",'"+s.getIme()+"','"+s.getPrezime()+"',"+s.getBrojIndeksa()+","+s.getGodinaUpisa()+","+s.getSemestar()+",'"+s.getSmer()+"')";
                }
                else if(u instanceof Professor){
                    Professor p=(Professor)u;
                    sql="insert into Professor(ID,FirstName,LastName)"
                   +"values(" + p.getID() + ",'"+p.getIme()+"','"+p.getPrezime()+"')";
                }
                else{
                    Admin a=(Admin)u;
                    sql="insert into Administrator(ID,FirstName,LastName,ID_Administratora)"
                   +"values(" + a.getID() + ",'"+a.getIme()+"','"+a.getPrezime()+"',"+a.getID_Administratora()+")";
                }
                System.out.println(sql);
                stmt.executeQuery(sql);
            }
        } catch (Exception e) {
            System.out.println("Nisam uspeo da ubacim Korisnika u bazu");
        }
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////    

    public int getNumberOfStudents(int godinaUpisa){

        ResultSet rs;
        sql="select max(ID) as ID "
           +"from student "
           +"where GodinaUpisa="+godinaUpisa;
        try {
           rs = stmt.executeQuery(sql); 
           if(rs.next()!= false){
                return rs.getInt("ID");
           }
        }
        catch(Exception e) {
            System.out.println("Nisam uspeo da izvrsim SQL upit "+ "select * "
                                                                 + "from student ");
        }
        return 0;
    }
    
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public List<User> getAllUsers(){

        ResultSet rs;
        sql="select * "
          + "from korisnik ";

        try {

            ArrayList<User> users=new ArrayList<>();
            ResultSet pomRS;
            String ime;
            String prezime;
            rs = stmt.executeQuery(sql); 
            while(rs.next()!= false) {
                int id = rs.getInt("ID");
                
                sql="select * "
                  + "from student "
                  + "where ID="+id;
                
                pomRS=stmt.executeQuery(sql); 
                
                if(pomRS.next()!= false){
                    ime=pomRS.getString("FirstName");
                    prezime=pomRS.getString("LastName");
                    int brIndexa=pomRS.getInt("BrojIndeksa");
                    int godUpisa=pomRS.getInt("GodinaUpisa");
                    users.add(new Student(id, ime, prezime, brIndexa, godUpisa));
                }
                sql="select * "
                  + "from professor "
                  + "where ID="+id;
                
                pomRS=stmt.executeQuery(sql);
                
                if(pomRS.next()!= false){
                    ime=pomRS.getString("FirstName");
                    prezime=pomRS.getString("LastName");
                    users.add(new Professor(id, ime, prezime));
                }
                
                sql="select * "
                  + "from administrator "
                  + "where ID="+id;
                
                pomRS=stmt.executeQuery(sql);
                
                if(pomRS.next()!= false){
                    ime=pomRS.getString("FirstName");
                    prezime=pomRS.getString("LastName");
                    int ID_Administratora=pomRS.getInt("ID_Administratora");
                    users.add(new Admin(id, ime, prezime,ID_Administratora));
                }
            }
            return users;
            
        } catch (Exception e) {
            
            System.out.println("Nisam uspeo da izvrsim SQL upit "+ "select * "
                                                                 + "from korisnik ");
        }
        
        return null;
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////    
    
    public List<Professor> getAllProfessors(){
        ResultSet rs;
        
        List<Professor> listProfessors=new ArrayList<Professor>();
        
        sql="select * "
          + "from professor";
        
        try {
            rs = stmt.executeQuery(sql); 
            
            while(rs.next()!= false) {
                int id = rs.getInt("ID");
                String FirstName = rs.getString("FirstName");
                String LastName = rs.getString("LastName");
                    
                listProfessors.add(new Professor(id,FirstName,LastName));
            }
            
        } catch (Exception e) {
            System.out.println("Nisam uspeo da izvrsim SQL upit "+ "select * "
                                                                 + "from professor ");
        }
        
        
        return listProfessors;
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////    

    public List<Predmet> getAllSubjects(){
        ResultSet rs;
        List<Predmet> subjets=new ArrayList<>();
        
        sql="select s.*,p.FirstName,p.LastName "
          + "from subject s left join professor p "
          + "on s.ID_Professor=p.ID ";
        
        try {
            rs=stmt.executeQuery(sql);
             
            while(rs.next()!= false) {
                int ID = rs.getInt("ID");
                String imePredmeta = rs.getString("Name");
                int ID_Professor = rs.getInt("ID_Professor");
                String imeProfesora=rs.getString("FirstName");
                String prezimeProfesora=rs.getString("LastName");
                   
                subjets.add(new Predmet(ID, imePredmeta, new Professor(ID_Professor, imeProfesora, prezimeProfesora)));
            }
        }catch(Exception e) {
            System.out.println("Nisam pokupio predmete iz baze");
        }
        return subjets;
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////    

    public List <PolozeniIspit> getAllPassedExams() {
        ResultSet rs;
        List <PolozeniIspit> passedExams=new ArrayList<>();

        sql="Select * "
           +"from polozeniispiti ";

        try{
            rs=stmt.executeQuery(sql);

            while ( rs.next() != false) {

                int ID = rs.getInt("ID");
                int ID_Prijave=rs.getInt("ID_PrijaveIspita");
                int Ocena = rs.getInt("Ocena");
                PrijavljaniIspit pi= getPrijavljaniIspitByID(ID_Prijave);

                passedExams.add(new PolozeniIspit(ID,pi, Ocena));
            }
            
        }catch(Exception e) {
            System.out.println("Nisam pokupio polozene ispite iz baze");
        }
    
        return passedExams;
    }  

///////////////////////////////////////////

    public List <PrijavljaniIspit> getAllRegisteredExams() {
        ResultSet rs;
        List <PrijavljaniIspit> registeredExams=new ArrayList<>();

        sql= "select * "
            +"from prijavljeniispiti "
            +"where ID not in( "
                        +"select ID_PrijaveIspita "
                        +"from polozeniispiti "
                        +")";

        try{
            rs=stmt.executeQuery(sql);

            while ( rs.next() != false ) {
                int ID = rs.getInt("ID");
                int IDPredmeta = rs.getInt("ID_Predmeta");
                int IDStudenta = rs.getInt("ID_Studenta");
                

                registeredExams.add(new PrijavljaniIspit(ID, getPredmetByID(IDPredmeta) , getStudentByID(IDStudenta) ));
            }
        }    
            catch(Exception e) {
                System.out.println("Nisam izvadio Listu Prijavljenih Ispita iz baze");
            }

        return registeredExams;
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////    

    public Professor getProfessor(int ID){
        ResultSet rs;
        Professor professor=null;
        
        sql="select * "
          + "from professor "
          + "where ID="+ID;
        
        try {
            rs=stmt.executeQuery(sql);
            rs.next();

            String FirstName = rs.getString("FirstName");
            String LastName = rs.getString("LastName");
            
            professor=new Professor(ID, FirstName, LastName);
            
        } catch (Exception e) {
            System.out.println("Nema profesora sa tim ID-jem");
        }
        

        return professor;
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////    

    public Student getStudentByID(int ID) {

        ResultSet rs;

        Student student = null;

        sql="select * "
           +"from student "
           +"where ID= "+ID;
        try{
            rs = stmt.executeQuery(sql);
            rs.next();

            String FirstName = rs.getString("FirstName");
            String LastName = rs.getString("LastName");
            int BrojIndeksa = rs.getInt("BrojIndeksa");
            int GodinaUpisa = rs.getInt("GodinaUpisa");

            student=new Student(ID, FirstName, LastName, BrojIndeksa, GodinaUpisa);
        }
        catch(Exception e) {
            System.out.println("Nisam izvadio Studente iz baze");
        }
        return student;
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public Predmet getPredmetByID(int ID){
        ResultSet rs;
        Predmet predmet=null;
        sql="select * "
           +"from subject "
           +"where ID= "+ID;
        try{
            rs=stmt.executeQuery(sql);
            rs.next();

            String Name = rs.getString("Name");
            int ID_Professor = rs.getInt("ID_Professor");

            predmet = new Predmet(ID, Name, getProfessorByID(ID_Professor));
        }
        catch(Exception e){
            System.out.println("Nisam izvadio Predmet iz baze");
        }
        return predmet;    
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////    
    
    public Professor getProfessorByID(int ID){
        ResultSet rs;
        Professor professor=null;
        sql="select * "
           +"from professor "
           +"where ID= "+ID;
        try {
            rs=stmt.executeQuery(sql);
            rs.next();
            
            String FirstName = rs.getString("FirstName");
            String LastName = rs.getString("LastName");
            
            professor=new Professor(ID,FirstName, LastName);
        }
        catch(Exception e){
            System.out.println("Nisam izvadio Profesora iz baze");
        }
        return professor;
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////    

    public PrijavljaniIspit getPrijavljaniIspitByID(int ID) {
        ResultSet rs=null;
        PrijavljaniIspit prijavljaniIspit = null;

        sql="SELECT * "
           +"FROM `prijavljeniispiti` "
           +"WHERE ID="+ID;
        try{
            rs =stmt.executeQuery(sql);
            rs.next();

            int ID_Predmeta = rs.getInt("ID_Predmeta");
            int ID_Studenta = rs.getInt("ID_Studenta");

            prijavljaniIspit=new PrijavljaniIspit(ID,getPredmetByID(ID_Predmeta),getStudentByID(ID_Studenta));
                
            } catch (Exception e) {
                System.out.println("Nisam izvadio Prijavljen Ispit iz baze");
            }

            return prijavljaniIspit;
        }

    public void addRegisteredExam(int ID_Predmeta,int ID_Studenta){
            sql="insert into prijavljeniispiti ( ID_Predmeta , ID_Studenta ) "
            +"values("+ID_Predmeta+","+ID_Studenta+")";
        
        try {
            stmt.executeQuery(sql);
            
        } catch (Exception e) {
            System.out.println("Nije dodat prijavljeni ispit "+sql);
        }
            
    }
        
}
