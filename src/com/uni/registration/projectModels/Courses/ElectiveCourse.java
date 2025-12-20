package com.uni.registration.projectModels.Courses;
import com.uni.registration.projectModels.Instructor;

 public class ElectiveCourse extends Course{
    public ElectiveCourse(String courseName,String courseCode,int courseCredit,Instructor instructor){
        super (courseName,courseCode,courseCredit,instructor);
    }
    @Override
    public String getCourseType(){
        return "Elective";
    }
}