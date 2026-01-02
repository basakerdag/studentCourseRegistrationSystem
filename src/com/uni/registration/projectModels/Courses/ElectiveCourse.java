package com.uni.registration.projectModels.Courses;
import java.time.LocalTime;

import com.uni.registration.projectModels.Instructor;

/**
 * Represents an elective course in the registration system.
 * Elective courses are specialized subjects that students can choose based on their interests.
 * This class extends the abstract {@link Course} class.
 */

 public class ElectiveCourse extends Course{
    public ElectiveCourse(String courseName,String courseCode,int courseCredit,Instructor instructor,int courseCapacity,int courseEnrolledCount,
        String courseDay,LocalTime courseStartHour,LocalTime courseEndHour)
    {
        super (courseName,courseCode,courseCredit,instructor,courseCapacity,courseEnrolledCount,courseDay,courseStartHour,courseEndHour);
    }

    /**
     * Identifies the specific type of this course.
     * This is a polymorphic implementation of the abstract method in {@link Course}.
     * * @return A string representing the course type: "Elective".
     */
    @Override
    public String getCourseType(){
        return "Elective";
    }
}