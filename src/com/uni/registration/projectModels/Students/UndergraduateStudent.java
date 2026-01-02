package com.uni.registration.projectModels.Students;
import com.uni.registration.projectModels.Courses.*;

/**
   *UndergraduateStudent class representing a bachelor level student.
   *Tuition is calculated based on the total credits of registered courses.
 */

public class UndergraduateStudent extends Student{
    private static final double pricePerCredit = 300.0;
    public UndergraduateStudent(String name,String surname,int studentID,String department,int year,String password){
        super(name,surname,studentID,department,year,password);
    }
    @Override
    public String getStudentType(){
        return "Undergraduate Student";
    }
    /*
      Calculates tuition for undergraduates: Total credits multiplied by credit price.
      * @return The total tuition amount based on credit load.
     */
    @Override
    public double calculateTuition(){
        int totalCredits=0;
        for(Course c : registeredCourse){
            totalCredits+=c.getCourseCredit();
        }
        return totalCredits*pricePerCredit;
    }
}