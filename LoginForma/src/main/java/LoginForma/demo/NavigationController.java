package LoginForma.demo;

import Ispit.PolozeniIspit;
import Ispit.Predmet;
import Ispit.PrijavljaniIspit;
import User.Admin;
import User.Professor;
import User.Student;
import User.User;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
public class NavigationController {
    public static DbConnection baza=null;
    public static User user=null;
    private static int permission = 0;
    
    public static void connection(){
        baza=new DbConnection();
        Student.setBrojStudenata(baza.getNumberOfStudents(Student.trenutnaKalendarskaGodina));
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////    

    public static void  editPermission(){
        if(user instanceof Student){
            permission=1;
            return;
        }
        else if(user instanceof Professor){
            permission=2;
            return;
        }
        else if(user instanceof Admin){
            permission=3;
            return;
        }
        else
            permission=0;
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////    
    
    @RequestMapping("/")
    public String welcome(Model model){
        if (baza==null) connection();

        if(permission==0)
           return "index"; 
        else{
            model.addAttribute("permission",permission);
        }
        return "indexx";
        
    }
    
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////    
    
    @RequestMapping("/addSubjects")
    public String addSubject(Model model){
        if(user instanceof Admin){

            if(baza==null) connection();
            
            List<Professor> listProfessors = baza.getAllProfessors();

            
            model.addAttribute("permission",permission);
            model.addAttribute("listProfessors",listProfessors);
            
            return "addSubjects";
        }
        return "index";
    }
    
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////    
    
    @RequestMapping("/subjects")
    public String getAllSubjets(Model model){
        if(user instanceof Student){
            if(baza==null) connection();
            
            List<Predmet> listsubjects= baza.getAllSubjects();

            model.addAttribute("permission",permission);
            model.addAttribute("listSubjects",listsubjects);
            
            return "subjects";
        }
        return "index";
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////    
    
    @RequestMapping("/users")
    public String getAllUsers(Model model){
        if(user instanceof Admin){
            if(baza == null) connection();
            
            System.out.println("Uso sam");
            List<User> listUsers= baza.getAllUsers();
            
            model.addAttribute("permission",permission);
            model.addAttribute("listUsers",listUsers);
            
            return "users";
        }
        return "index";
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @RequestMapping("/registeredExams")
    public String gettAllRegisteredExams(Model model) {
        if(user instanceof Professor){
            if(baza==null) connection();

            List<PrijavljaniIspit> listaPrijavljanihIspita = baza.getAllRegisteredExams();

            model.addAttribute("permission",permission);
            model.addAttribute("listaPrijavljanihIspita", listaPrijavljanihIspita);

            return "registeredExams";
        }
        return "index";
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////    

    @RequestMapping("/gradeExam")
    public String gradeExam(@RequestParam(value = "ID")int id,Model model){
        if(user instanceof Professor){
            if(baza==null) connection();

            PrijavljaniIspit ispit=baza.getPrijavljaniIspitByID(id);

            model.addAttribute("permission",permission);
            model.addAttribute("gradeExam", ispit);

            return "gradeExam";
        }
        return "index";
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @RequestMapping("/passedExams")
    public String getAllPassedExams(Model model) {
        if(user instanceof Student){
            if (baza==null) connection();

            List<PolozeniIspit> listaPolozenihIspita = baza.getAllPassedExams();

            model.addAttribute("permission",permission);
            model.addAttribute("listaPolozenihIspita", listaPolozenihIspita);

            return "passedExams";
        }
        return "index";
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    @RequestMapping("/login")
    public String LogIn(){
        if (baza==null) connection();
        return "login";
    }
    
    @RequestMapping("/register")
    public String Register(Model model){
        model.addAttribute("permission",permission);
        if(user instanceof Admin){
            if (baza==null) connection();
            return "register";
        }
        return "index";
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @RequestMapping("/logout")
    public String logout(Model model){
        
        user=null;
        permission=0;

        model.addAttribute("permission",permission);
        return "index";
    }
  

}
