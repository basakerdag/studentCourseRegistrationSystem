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

/**
 * Service class for managing the student database.
 * Handles student registration, CSV persistence, authentication, and systemic course removal.
 */
public class StudentManager {
    private List<Student> students;
    private final String studentsFile = "src/data/students.csv";

    public StudentManager() {
        this.students = new ArrayList<>();
        loadFromStudentCsv();
    }

    /**
     * Loads student records from CSV, instantiating specific {@link GraduateStudent} 
     * or {@link UndergraduateStudent} objects based on the stored type.
     */
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
  
   /**
    * Overwrites the student CSV file with the current state of the student list.
   */
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
  
    /**
     * Adds a new student to the system and appends their data to the CSV file.
     * Prevents duplicate registrations based on Student ID.
     * @param student The {@link Student} object to be added.
     */
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

    /**
     * Authenticates a student using their ID and password.
     * @param studentID Unique student identification number.
     * @param studentPassword Password for the account.
     * @return The authenticated {@link Student} object, or null if credentials fail.
     */
    public Student login(int studentID,String studentPassword){
        for(Student s: students){
            if(s.getStudentID()==studentID && s.getPassword().equals(studentPassword)){
                return s;
            }
        }
        return null;
    }
    

    /**
     * Searches for a student in the local database by their ID.
     * @param ID The ID to search for.
     * @return The found {@link Student} or null.
    */
    public Student findStudentByID(int ID) {
        for (Student s : students) {
            if (s.getStudentID()==ID) {
                return s;
            }
        }
        return null;
    }

    /**
     * Removes a specific course from every student's registered course list.
     * Also triggers the removal of these enrollments from the persistent CSV storage.
     * @param courseCode The unique code of the course to be removed.
    */
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


