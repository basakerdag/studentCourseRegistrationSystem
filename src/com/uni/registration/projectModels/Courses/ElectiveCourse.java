package com.uni.registration.projectModels.Courses;
import java.time.LocalTime;

import com.uni.registration.projectModels.Instructor;

 public class ElectiveCourse extends Course{
    public ElectiveCourse(String courseName,String courseCode,int courseCredit,Instructor instructor,int courseCapacity,int courseEnrolledCount,
        String courseDay,LocalTime courseStartHour,LocalTime courseEndHour)
    {
        super (courseName,courseCode,courseCredit,instructor,courseCapacity,courseEnrolledCount,courseDay,courseStartHour,courseEndHour);
    }
    @Override
    public String getCourseType(){
        return "Elective";
    }
}