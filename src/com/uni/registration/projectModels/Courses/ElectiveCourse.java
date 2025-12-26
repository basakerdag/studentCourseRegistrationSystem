package com.uni.registration.projectModels.Courses;
import com.uni.registration.projectModels.Instructor;

 public class ElectiveCourse extends Course{
    public ElectiveCourse(String courseName,String courseCode,int courseCredit,Instructor instructor,int courseCapacity,int courseEnrolledCount){
        super (courseName,courseCode,courseCredit,instructor,courseCapacity,courseEnrolledCount);
    }
    @Override
    public String getCourseType(){
        return "Elective";
    }
}