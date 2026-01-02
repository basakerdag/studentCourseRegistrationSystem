package com.uni.registration.projectServices;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import com.uni.registration.projectModels.Registration;
import com.uni.registration.projectModels.Courses.Course;
import com.uni.registration.projectModels.Students.*;


public class StudentManager {
    private List<Student> students;
    private final String studentsFile = "src/data/students.csv";

    public StudentManager() {
        this.students = new ArrayList<>();
        loadFromStudentCsv();
    }
   
    public void loadFromStudentCsv(){
     try(BufferedReader br=new BufferedReader(new FileReader(studentsFile))){
        String line;
        while((line=br.readLine())!=null){
            String[] data=line.split(",");
            if(data.length < 7) continue;
            String studentName=data[0];
            String studentSurname=data[1];
            int studentID=Integer.parseInt(data[2]);
            String studentDepartment=data[3];
            int studentYear=Integer.parseInt(data[4]);
            String studentPassword=data[5];
            String studentType=data[6];
            
            Student newStudent=null;
            if(studentType.equals("Graduate Student")){
                newStudent=new GraduateStudent(studentName,studentSurname,studentID,studentDepartment,studentYear,studentPassword);
            }else if(studentType.equals("Undergraduate Student")){
                newStudent=new UndergraduateStudent(studentName, studentSurname, studentID, studentDepartment, studentYear, studentPassword);
            }
            
            if(newStudent!=null){
                students.add(newStudent);
            }
        }
    }catch(IOException e) {
        System.out.println("No existing student data found or file is empty.");
    }
   
} 
   public void saveAllStudentsToCsv() {
    try (PrintWriter pw = new PrintWriter(new FileWriter(studentsFile, false))) {
        for (Student s : students) {
            String csvLine = String.format("%s,%s,%d,%s,%d,%s,%s",
                s.getName(),
                s.getSurname(),
                s.getStudentID(),
                s.getDepartment(),
                s.getYear(),
                s.getPassword(),  
                s.getStudentType() 
            );
            pw.println(csvLine);
        }
    } catch (IOException e) {
        System.err.println(" Error: Could not update students file: " + e.getMessage());
    }
} 
    public void addStudent(Student student) {
        if (findStudentByID(student.getStudentID()) != null) {
        System.out.println("Error: Student with ID " + student.getStudentID() + " already exists! Registration cancelled.");
        return; 
    }
        students.add(student);
        try(PrintWriter pw=new PrintWriter(new FileWriter(studentsFile,true))){
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

    public Student login(int studentID,String studentPassword){
        for(Student s: students){
            if(s.getStudentID()==studentID && s.getPassword().equals(studentPassword)){
                return s;
            }
        }
        return null;
    }

        public Student findStudentByID(int ID) {
        for (Student s : students) {
            if (s.getStudentID()==ID) {
                return s;
            }
        }
        return null;
    }

   public void removeCourseFromAllStudents(String courseCode) {
    for (Student s : students) {        
        List<Course> registered = s.getRegisteredCourses();
        Course courseToRemove = null;
        for (Course c : registered) {
            if (c.getCourseCode().equalsIgnoreCase(courseCode)) {
                courseToRemove = c; 
                break;
            }
        }
        if (courseToRemove != null) {
            registered.remove(courseToRemove);
        }
    }

    Registration.removeAllEnrollmentsForCourse(courseCode);
}


}


