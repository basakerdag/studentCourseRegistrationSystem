package com.uni.registration.projectModels.Courses;
import com.uni.registration.projectModels.Instructor;

 public class MandatoryCourse extends Course{
    public MandatoryCourse(String courseName,String courseCode,int courseCredit,Instructor instructor,int courseCapacity,int courseEnrolledCount){
        super (courseName,courseCode,courseCredit,instructor,courseCapacity,courseEnrolledCount);
    }
    @Override
    public String getCourseType(){
        return "Mandatory";
    }
}
