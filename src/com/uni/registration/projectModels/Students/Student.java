package com.uni.registration.projectModels.Students;
import java.util.ArrayList;

import com.uni.registration.projectModels.Registrable;
import com.uni.registration.projectModels.Registration;
import com.uni.registration.projectModels.Courses.Course;


public abstract class Student implements Registrable{
    private String name;
    private String surname;
    private int studentID;
    private String department;
    private int year;
    private String password;
    protected ArrayList<Course> registeredCourse;


    public Student(String name,String surname,int studentID,String department,int year,String password){
        this.name=name;
        this.surname=surname;
        this.studentID=studentID;
        this.department=department;
        this.year=year;
        this.password=password;
        this.registeredCourse=new ArrayList<>();
    }

    public abstract String getStudentType();


    @Override
    public void registerCourse(Course course){
        if(!registeredCourse.contains(course)){
            registeredCourse.add(course);
            System.out.println(course.getCourseName()+"has been successfully added.");
            new Registration(this, course);
        }else{
            System.out.println("Error: The course with code "+ course.getCourseCode()+ " is already in your list.");
        }
    }

    @Override
    public void unregisterCourse(Course course){
        if(registeredCourse.contains(course)){
            registeredCourse.remove(course);
            Registration.stCrsRemoveFromCsv(this.getStudentID(),course.getCourseCode());
            System.out.println(course.getCourseName()+"registration has been removed.");
        }else{
            System.out.println("Error: This course is not in your list.");
        }
    }

    public abstract double calculateTuition();

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name=name;
    }

    public String getSurname(){
        return surname;
    }
    public void setSurname(String surname){
        this.surname=surname;
    }
    

    public int getStudentID(){
        return studentID;
    }
    public void setStudentID(int studentID){
        this.studentID=studentID;
    }

    public String getDepartment(){
        return department;
    }
    public void setDepartment(String department){
        this.department=department;
    }

    public int getYear(){
        return year;
    }
    public void setYear(int year){
        this.year=year;
    }

    public String getPassword(){
        return password;
    }
    public void setPassword(String password){
        this.password=password;
    }

    public ArrayList<Course> getRegisteredCourses(){
        return registeredCourse;
    }

    public boolean isEnrolled(Course course){
        return registeredCourse.contains(course);
    }

    public boolean changeStudentPassword(String currentStudentPassword,String newSStudentPassword){
        if(!this.password.equals(currentStudentPassword)){
            System.out.println("Current password is incorrect.");
            return false;
        }
        if(newSStudentPassword.equals(currentStudentPassword)){
           System.out.println("New password cannot be equal current password.");
           return false;
        }

        this.password=newSStudentPassword;
        System.out.println("Password changed succesfully.");
        return true;
    }
 

}
  

