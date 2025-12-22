package com.uni.registration.projectModels.Students;
import com.uni.registration.projectModels.Courses.*;


public class GraduateStudent extends Student{
    private static final double fixedTuition=5000.0;
    public GraduateStudent(String name,String surname,int studentID,String department,int year,String password){
        super(name,surname,studentID,department,year,password);
    }
    @Override
    public String getStudentType(){
        return "Graduate Student";
    }
    
    @Override  
    public double calculateTuition(){
        return fixedTuition+(registeredCourse.size() * 100.0);
    } 
}
