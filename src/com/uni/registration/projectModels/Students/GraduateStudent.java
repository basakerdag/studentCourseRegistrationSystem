package com.uni.registration.projectModels.Students;
import com.uni.registration.projectModels.Courses.*;


public class GraduateStudent extends Student{
    private static final double fixedTuition=5000.0;
    public GraduateStudent(String name,String surname,String studentID,String department,int year){
        super(name,surname,studentID,department,year);
    }
    @Override  
    public double calculateTuition(){
        return fixedTuition+(registeredCourse.size() * 100.0);
    } 
}
