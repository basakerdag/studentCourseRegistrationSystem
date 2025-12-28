package com.uni.registration.projectModels;
import com.uni.registration.projectModels.Courses.*;
import com.uni.registration.projectModels.Students.*;
import com.uni.registration.projectServices.CourseCatalog;
import com.uni.registration.projectServices.StudentManager;

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

    private boolean isScheduleConflicting(Student student,Course newCourse){
        for(Course registeredCourse:student.getRegisteredCourses()){
            if(registeredCourse.getCourseDay().equalsIgnoreCase(newCourse.getCourseDay())){
                boolean overlaps=newCourse.getCourseStartHour().isBefore(registeredCourse.getCourseEndHour()) &&
                registeredCourse.getCourseStartHour().isBefore(newCourse.getCourseEndHour());
                if(overlaps){
                    return true;
                }
            }
        }
        return false;
    }





    public void registerCourse(Student student,Course course){
        if(course.getCourseEnrolledCount()>=course.getCourseCapacity()){
            System.out.println("Warning!"+course.getCourseName()+"is full.");
            return;
        }
        if(isScheduleConflicting(student, course)){
            System.out.println("Warning: Schedule conflict! You already have another course on "+ course.getCourseDay() +" beetween "+
            course.getCourseStartHour()+" and "+course.getCourseEndHour()+".");
            return;
        }
        
        
            student.getRegisteredCourses().add(course);
            course.incrementEnrolledCourse();
            this.student=student;
            this.course=course;
            stCrsSaveToCsv();
            System.out.println("Registration successful.");
        
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

    public static void loadEnrollments(StudentManager studentManager,CourseCatalog courseCatalog){
    java.io.File file = new java.io.File(enrollmentCsv);
    if (!file.exists()) return;

    try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(enrollmentCsv))) {
        String line;
        while ((line = br.readLine()) != null) {
            String[] data = line.split(",");
            if (data.length < 2) continue;
            int studentID = Integer.parseInt(data[0]);
            String courseCode = data[1];

            Student student = studentManager.findStudentByID(studentID);
            Course course = courseCatalog.findCourseByCode(courseCode);
            if (student != null && course != null) {
                if (!student.getRegisteredCourses().contains(course)) {
                    student.getRegisteredCourses().add(course);
                    course.incrementEnrolledCourse();
                }
            }
        }
        System.out.println("Enrollment data successfully recovered from CSV.");
    } catch (java.io.IOException e) {
        System.err.println("Error while recovering enrollments: " + e.getMessage());
    }
    }

    public static void dropCourse(Student student,Course course){
        if(student.getRegisteredCourses().remove(course)){
            course.decrementEnrolledCourse();
            stCrsRemoveFromCsv(student.getStudentID(),course.getCourseCode());
            System.out.println("Success:"+ course.getCourseCode()+"has been dropoped.");
        }else{
            System.out.println("Error: Your are not registered this course.");
        }
    }

    public static void removeAllEnrollmentsForCourse(String courseCode) {
    List<String> remainingEnrollments = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(enrollmentCsv))) {
        String line;
        while ((line = br.readLine()) != null) {
            if(line.trim().isEmpty()) continue;
            String[] data = line.split(",");
            String cCode = data[1];

            if (!(cCode.equalsIgnoreCase(courseCode))) {
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
