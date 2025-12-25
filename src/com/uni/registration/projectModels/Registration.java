package com.uni.registration.projectModels;
import com.uni.registration.projectModels.Courses.*;
import com.uni.registration.projectModels.Students.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Registration {
    private Student student;
    private Course course;
    private LocalDate registrationDate;

    public static final String enrollmentCsv = "src/data/enrollments.csv";


    public Registration(Student student,Course course){
        this.student=student;
        this.course=course;
        this.registrationDate=LocalDate.now();
        stCrsSaveToCsv();
    }

    public Student getStudent(){
        return student;
    }
    public void setStudent(Student student){
        this.student=student;
    }

    public Course getCourse(){
        return course;
    }
    public void setCourse(Course course){
        this.course=course;
    }

    public LocalDate getRegistrationDate(){
        return registrationDate;
    }
    public void setDate(LocalDate registrationDate){
        this.registrationDate=registrationDate;
    }

    public void stCrsSaveToCsv(){
        try (PrintWriter pw = new PrintWriter(new FileWriter(enrollmentCsv, true))) {
            pw.println(student.getStudentID() + "," + course.getCourseCode() + "," + registrationDate);
        } catch (IOException e) {
            System.err.println("Error writing to enrollments: " + e.getMessage());
        }
    }

    public static void stCrsRemoveFromCsv(int studentID,String courseCode){
    List<String> remainingEnrollments = new ArrayList<>();

    try (BufferedReader br = new BufferedReader(new FileReader(enrollmentCsv))) {
        String line;
        while ((line = br.readLine()) != null) {
            String[] data = line.split(",");
            int sID = Integer.parseInt(data[0]);
            String cCode = data[1];

            if (!(sID == studentID && cCode.equalsIgnoreCase(courseCode))) {
                remainingEnrollments.add(line);
            }
        }
    } catch (IOException e) {
        System.err.println("Error reading enrollments for deletion: " + e.getMessage());
    }
    try (PrintWriter pw = new PrintWriter(new FileWriter(enrollmentCsv, false))) {
        for (String record : remainingEnrollments) {
            pw.println(record);
        }
    } catch (IOException e) {
        System.err.println("Error updating enrollments file: " + e.getMessage());
    }      
    }

}
