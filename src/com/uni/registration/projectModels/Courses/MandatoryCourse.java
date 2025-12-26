package com.uni.registration.projectModels.Courses;
import com.uni.registration.projectModels.Instructor;
import java.time.LocalTime;

 public class MandatoryCourse extends Course{
    public MandatoryCourse(String courseName,String courseCode,int courseCredit,Instructor instructor,int courseCapacity,int courseEnrolledCount,
        String courseDay,LocalTime courseStartHour,LocalTime courseEndHour)
    {
        super (courseName,courseCode,courseCredit,instructor,courseCapacity,courseEnrolledCount,courseDay,courseStartHour,courseEndHour);
    }
    @Override
    public String getCourseType(){
        return "Mandatory";
    }
}
