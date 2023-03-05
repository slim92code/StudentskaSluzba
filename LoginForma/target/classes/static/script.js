var divDodatno=document.getElementById("dodatno");
    
var textField1=document.createElement("input");
var textField2=document.createElement("input");
    
textField1.type="text";
textField1.placeholder="Set semestar";
textField1.id="semestar";
   
textField2.type="text";
textField2.placeholder="Set smer";
textField2.id="smer";

var flag=0;

function load(){
    console.log(permission);
    var ul=document.getElementById("navbar");
    var li1=document.createElement("li");
    var a1=document.createElement("a");
   
    
    if(permission==1){
        a1.href="/subjects";
        a1.textContent="Prijavi ispit";
        li1.appendChild(a1);
        ul.appendChild(li1);

        var li2=document.createElement("li");
        var a2=document.createElement("a");
        a2.href="/logout";
        a2.textContent="Log Out";
        li2.appendChild(a2);
        ul.appendChild(li2);
    }
    else if(permission==2){
        a1.href="/registeredExams";
        a1.textContent="Oceni ispit";
        li1.appendChild(a1);
        ul.appendChild(li1);

        var li2=document.createElement("li");
        var a2=document.createElement("a");
        a2.href="/logout";
        a2.textContent="Log Out";
        li2.appendChild(a2);
        ul.appendChild(li2);
    }
    else if(permission==3){
        a1.href="/addSubjects";
        a1.textContent="Dodaj ispit za prijavu"; 
        li1.appendChild(a1);
        ul.appendChild(li1);

        var li2=document.createElement("li");
        var a2=document.createElement("a");
        a2.href="/users";
        a2.textContent="Vidi sve korisnike"; 
        li2.appendChild(a2);
        ul.appendChild(li2);

        var li3=document.createElement("li");
        var a3=document.createElement("a");
        a3.href="/register";
        a3.textContent="Registuj korisnika";
        li3.appendChild(a3);
        ul.appendChild(li3);

        var li4=document.createElement("li");
        var a4=document.createElement("a");
        a4.href="/logout";
        a4.textContent="Log Out";
        li4.appendChild(a4);
        ul.appendChild(li4);
    }

    
}



function addFields(){  
    clearFields();
    if(flag==0){
        divDodatno.appendChild(textField1);
        divDodatno.appendChild(textField2);
        flag=1;
    }
}

function clearFields(){
    if(flag==1){
        divDodatno.removeChild(textField1);
        divDodatno.removeChild(textField2);
        flag=0;
    }
}

function registracija(){
    var permission=document.getElementById("permission").value;
    
    Username=document.getElementById("username").value;
    Password=document.getElementById("password").value;
    FirstName=document.getElementById("firstName").value;
    LastName=document.getElementById("lastName").value;
    
    if(permission==1){
        Semestar=+document.getElementById("semestar").value
        Smer=document.getElementById("smer").value;
        url="/registerStudent";
        const body = JSON.stringify({
            username:Username,
            password:Password,
            firstName:FirstName,
            lastName:LastName,
            semestar:Semestar,
            smer:Smer
        });
        
    }  
    else if(permission==2){
        url="/registerProfessor";
        const body = JSON.stringify({
            username:Username,
            password:Password,
            firstName:FirstName,
            lastName:LastName
        });
    }
    else{
        url="/registerAdministrator";
        const body = JSON.stringify({
            username:Username,
            password:Password,
            firstName:FirstName,
            lastName:LastName
        });
    }
            
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open("POST", url); // true for asynchronous 

    xmlHttp.onreadystatechange = function() { 
        if (xmlHttp.readyState === 4 && xmlHttp.status === 200)  console.log("Sve je kako treba");
    }
    
    xmlHttp.send(body);
    
    console.log(xmlHttp.readyState + " / " + xmlHttp.status);
    
}

function loginDugme(){
    
    console.log(localStorage);
    
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
    
    htttpRequest.onreadystatechange = function() { 
        document.getElementById("error").textContent="";
        if (htttpRequest.readyState === 4 && htttpRequest.status === 202){
             window.location="/";        
        }
        else if(htttpRequest.readyState === 4 && htttpRequest.status === 203){
            document.getElementById("error").textContent="You entered the wrong username or password."
        }    
    }
}


function addSubject(){
    url="/addSubject?subjectName="+document.getElementById("subjectName").value
                    +"&ID_Professor="+document.getElementById("professors").value;
            
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open("GET", url, false); // true for asynchronous 

    xmlHttp.onreadystatechange = function() { 
        
        console.log(xmlHttp.readyState + " / " + xmlHttp.status);
        
        if (xmlHttp.readyState === 4 && xmlHttp.status === 200)     console.log("Sve je kako treba _ DODAJ ISPIT");
    }
    
         
    xmlHttp.send();
}

function prijaviIspit(id){
    url="/registerExam?idSubject="+id;
            
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open("GET", url, false); // true for asynchronous 

    xmlHttp.onreadystatechange = function() { 
        
        console.log(xmlHttp.readyState + " / " + xmlHttp.status);
        
        if (xmlHttp.readyState === 4 && xmlHttp.status === 200)     console.log("Sve je kako treba _ DODAJ PRIJAVLJEN ISPIT");
    }
    
         
    xmlHttp.send();
}


function oceni(id){
    window.location="/gradeExam?ID="+id;
}

function oceniIspit(id){
    url="/gradeTheExam?grade="+document.getElementById("grade").value
                    +"&exam="+id;

    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open("GET", url, false); //true for asynchronous

    xmlHttp.onreadystatechange = function() {

        console.log(xmlHttp.readyState + " / " + xmlHttp.status);

        if ( xmlHttp.responseText === 4 && xmlHttp.status === 200)   console.log("Sve je kako treba _ OCENI ISPIT");        
    }
    

    xmlHttp.send();

}
