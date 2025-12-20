package com.uni.registration.projectModels.Students;
import java.util.ArrayList;

import com.uni.registration.projectModels.Registrable;
import com.uni.registration.projectModels.Courses.Course;


public abstract class Student implements Registrable{
    private String name;
    private String surname;
    private String studentID;
    private String department;
    private int year;
    protected ArrayList<Course> registeredCourse;


    public Student(String name,String surname,String studentID,String department,int year){
        this.name=name;
        this.surname=surname;
        this.studentID=studentID;
        this.department=department;
        this.year=year;
        this.registeredCourse=new ArrayList<>();
    }

    @Override
    public void registerCourse(Course course){
        if(!registeredCourse.contains(course)){
            registeredCourse.add(course);
            System.out.println(course.getCourseName()+"has been successfully added.");
        }else{
            System.out.println("Error: The course with code "+ course.getCourseCode()+ " is already in your list.");
        }
    }

    @Override
    public void unregisterCourse(Course course){
        if(registeredCourse.contains(course)){
            registeredCourse.remove(course);
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
    

    public String getStudentID(){
        return studentID;
    }
    public void setStudentID(String studentID){
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

    public ArrayList<Course> getRegisteredCourses(){
        return registeredCourse;
    }

    public boolean isEnrolled(Course course){
        return registeredCourse.contains(course);
    }


}


