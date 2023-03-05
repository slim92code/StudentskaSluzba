
package LoginForma.demo;

import Ispit.PolozeniIspit;
import Ispit.Predmet;
import Ispit.PrijavljaniIspit;
import User.Admin;
import User.Professor;
import User.Student;
import User.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
public class ServiceController {
    
    @PostMapping("/loginUser")
    public ResponseEntity LoginUser(@RequestBody String body){
        
        String username="";
        String password="";
        try {
            JSONObject json = new JSONObject(body);
            username = json.getString("username");
            password = json.getString("password");

        } catch (Exception e) {
            System.out.println("Problem sa ucitavanjem json-a, login");
        }
        
        if(NavigationController.baza==null)  NavigationController.connection();
        
        int ID=NavigationController.baza.probaLogin(username, password);
        
        if(ID>0){
            NavigationController.user=NavigationController.baza.getUser(ID);
            NavigationController.editPermission();
            return new ResponseEntity(HttpStatus.ACCEPTED);
        }
        else if(ID<1)
            return new ResponseEntity(HttpStatus.NON_AUTHORITATIVE_INFORMATION);
        else
            return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
        
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////    
    
    @PostMapping("/registerStudent")
    public void RegisterStudent(@RequestBody String body){
        
        String username ="";
        String password ="";
        String firstName ="";     
        String lastName =""; 
        int semestar=0; 
        String smer =""; 
        
        try {
            JSONObject json = new JSONObject(body);
            username = json.getString("username");
            password = json.getString("password");
            firstName = json.getString("firstName");
            lastName = json.getString("lastName");
            semestar = json.getInt("semestar");
            smer = json.getString("smer");

        } catch (Exception e) {
            System.out.println("Problem sa ucitavanjem json-a, registracija");
    }
         
        
        User u=new Student(0, username, password, firstName, lastName,semestar,smer);
        
        
        if(NavigationController.baza==null)  NavigationController.connection();
        
        NavigationController.baza.probaRegistracija(u);
        
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////    
    
    @GetMapping("/registerProfessor")
    public void RegisterProfessor( @RequestBody String body ) {

        String username ="";
        String password ="";
        String firstName ="";     
        String lastName =""; 
        
        try {
            JSONObject json = new JSONObject(body);
            username = json.getString("username");
            password = json.getString("password");
            firstName = json.getString("firstName");
            lastName = json.getString("lastName");

        } catch (Exception e) {
            System.out.println("Problem sa ucitavanjem json-a, registracija");
    }
        
        User u=new Professor(0, username, password, firstName, lastName);
        
        if(NavigationController.baza==null)  NavigationController.connection();
        
        NavigationController.baza.probaRegistracija(u);
        
    }

   
    
    @GetMapping("/registerAdministrator")
    public void RegisterAdministrator(@RequestBody String body){
        
        String username ="";
        String password ="";
        String firstName ="";     
        String lastName =""; 
        
        try {
            JSONObject json = new JSONObject(body);
            username = json.getString("username");
            password = json.getString("password");
            firstName = json.getString("firstName");
            lastName = json.getString("lastName");

        } catch (Exception e) {
            System.out.println("Problem sa ucitavanjem json-a, registracija");
    }
        
        User u=new Admin(0, username, password, firstName, lastName);
        
        if(NavigationController.baza==null)  NavigationController.connection();
        
        NavigationController.baza.probaRegistracija(u);
        
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////    
    
    @GetMapping("/addSubject")
    public void AddSubject(@RequestParam(value = "subjectName")String name,
                           @RequestParam(value = "ID_Professor")int ID_Professor){
                            
        if(NavigationController.baza==null)   NavigationController.connection();
        
        Professor prof= NavigationController.baza.getProfessor(ID_Professor);      
        
        Predmet p=new Predmet(0, name,prof);
        
        NavigationController.baza.addSubject(p);
        
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////    
 
    @GetMapping("/gradeTheExam")
    public void GradeTheExam(@RequestParam(value = "grade")int ocena,
                             @RequestParam(value = "exam")int ID){

        if(NavigationController.baza==null)   NavigationController.connection();
        
        PrijavljaniIspit prijavljaniIspit= NavigationController.baza.getPrijavljaniIspitByID(ID);

        PolozeniIspit polozeniIspit=new PolozeniIspit(0, prijavljaniIspit, ocena);

        NavigationController.baza.addPassedExam(polozeniIspit);

    }
    
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping("/registerExam")
    public void RegisterExam (@RequestParam(value = "idSubject")int id){
        
        if(NavigationController.baza==null)     NavigationController.connection();

        NavigationController.baza.addRegisteredExam( id, NavigationController.user.getID() );
}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////// 


}


/*
function loginDugme()
{
    
    Username=document.getElementById("username").value;
    Password=document.getElementById("password").value;
    
    const htttpRequest = new XMLHttpRequest();
    htttpRequest.open("POST", "http://localhost:8080/loginUser");
    htttpRequest.setRequestHeader("Content-Type", "application/json; charset=UTF-8");
    
    const body = JSON.stringify({
        username:Username,
        password:Password
      });
     htttpRequest.send(body);
}
*/