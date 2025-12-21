package com.uni.registration.projectModels.Students;
import com.uni.registration.projectModels.Courses.*;

public class UndergraduateStudent extends Student{
    private static final double pricePerCredit = 300.0;
    public UndergraduateStudent(String name,String surname,String studentID,String department,int year,String password){
        super(name,surname,studentID,department,year,password);
    }
    @Override
    public double calculateTuition(){
        int totalCredits=0;
        for(Course c : registeredCourse){
            totalCredits+=c.getCourseCredit();
        }
        return totalCredits*pricePerCredit;
    }
}