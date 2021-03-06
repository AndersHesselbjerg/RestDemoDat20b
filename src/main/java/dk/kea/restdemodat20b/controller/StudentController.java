package dk.kea.restdemodat20b.controller;

import dk.kea.restdemodat20b.model.Student;
import dk.kea.restdemodat20b.repository.StudentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequestMapping("/students")
@RestController
public class StudentController {


    private StudentRepository studentRepository;


    //constructor injection i stedet for field injection (autowired)
    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;

    }
    @GetMapping("/students")
    //HTTP GET(/students)
    public ResponseEntity<List<Student>> findAll(){
        List<Student> students = new ArrayList<>();
        studentRepository.findAll().forEach(students::add);
        return ResponseEntity.status(HttpStatus.OK).body(students);

    }
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Student>> findById(@PathVariable Long id){

        //hent student fra repository
        Optional<Student> optionalStudent = studentRepository.findById(id);
        //er student fundet?
        if (optionalStudent.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(optionalStudent);
        }
        else{

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(optionalStudent);
        }


    }

    //HTTP Post (/students)) - create
    @CrossOrigin(origins = "*", exposedHeaders = "Location")
    @PostMapping(value = "" )
    public ResponseEntity<Student> create(@RequestBody Student student) {
        //opret ny student i JPA
        Student newStudent = studentRepository.save(student);

        //location header: /students/{id}
        //"location", "/students/" + newStudent.getId()
        //HttpStatus.CREATED 201

        return RequestEntity.status(HttpStatus.CREATED).header("Location", "/students/" + newStudent.getId()).body(newStudent);


    }





}

