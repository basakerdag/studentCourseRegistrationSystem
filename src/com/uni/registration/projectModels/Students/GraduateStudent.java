/**
   *GraduateStudent class representing a master or doctoral level student.
   *Inherits from Student and implements tuition calculation based on course count.
 */

package com.uni.registration.projectModels.Students;

public class GraduateStudent extends Student{
    private static final double fixedTuition=5000.0;
    private static final double costPerCourse=100.0;
    public GraduateStudent(String name,String surname,int studentID,String department,int year,String password){
        super(name,surname,studentID,department,year,password);
    }
    @Override
    public String getStudentType(){
        return "Graduate Student";
    }  
    /*
      Calculates tuition for graduates: Fixed fee plus a fee for each course.
      @return The total tuition amount.
     */
    @Override  
    public double calculateTuition(){
        return fixedTuition+(registeredCourse.size() * costPerCourse );
    } 
}
