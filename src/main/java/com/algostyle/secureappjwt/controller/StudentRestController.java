package com.algostyle.secureappjwt.controller;

import com.algostyle.secureappjwt.entity.Student;
import com.algostyle.secureappjwt.repository.StudentRepository;
import com.algostyle.secureappjwt.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("studentapi")
public class StudentRestController {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtService jwtService;


    @GetMapping("welcome")
    public String welcome(){
        return "welcome to algostyle channel";
    }


    @PostMapping("/register")
    public  String registerStudent(@RequestBody Student student){

        // encoder le mot de passe
        String encodedPassword=passwordEncoder.encode(student.getPwd());
        student.setPwd(encodedPassword);

        studentRepository.save(student);

        return "User registered";
    }


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Student student){
        UsernamePasswordAuthenticationToken token=new UsernamePasswordAuthenticationToken(student.getUsername(),student.getPwd());

        try{
            Authentication authenticate = authManager.authenticate(token);
            if(authenticate.isAuthenticated()){
                String jwtToken=jwtService.generateToken(student.getUsername());
                return new ResponseEntity<>("Login successful \ntoken = "+jwtToken, HttpStatus.OK);
            }
        }catch (Exception e){

        }
        return new ResponseEntity<String>("Invalid Credentials",HttpStatus.BAD_REQUEST);
    }
}
