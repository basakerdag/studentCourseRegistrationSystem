package com.uni.registration.projectServices;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import com.uni.registration.projectModels.Students.*;


public class StudentManager {
    private List<Student> students;
    private final String filePath = "src/data/students.csv";

    public StudentManager() {
        this.students = new ArrayList<>();
    }

    public void addStudent(Student student) {
        students.add(student);
        try(PrintWriter pw=new PrintWriter(new FileWriter(filePath,true))){
            String csvLine=String.format("%s,%s,%d,%s,%d,%s,%s",
                student.getName(),
                student.getSurname(),
                student.getStudentID(),
                student.getDepartment(),
                student.getYear(),
                student.getPassword(),
                student.getStudentType()
            );
            pw.println(csvLine);
        }catch(IOException e){
            System.err.println("Error saving to file: " + e.getMessage());
        }
        System.out.println("Student saved to system.");
    }

}