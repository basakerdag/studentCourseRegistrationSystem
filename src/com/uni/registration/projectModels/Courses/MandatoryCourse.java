package com.uni.registration.projectModels.Courses;
import com.uni.registration.projectModels.Instructor;
import java.time.LocalTime;

/**
 * Represents a mandatory course in the registration system.
 * Mandatory courses are required subjects that students must complete as part of their curriculum.
 * This class extends the abstract {@link Course} class.
 */

 public class MandatoryCourse extends Course{
    public MandatoryCourse(String courseName,String courseCode,int courseCredit,Instructor instructor,int courseCapacity,int courseEnrolledCount,
        String courseDay,LocalTime courseStartHour,LocalTime courseEndHour)
    {
        super (courseName,courseCode,courseCredit,instructor,courseCapacity,courseEnrolledCount,courseDay,courseStartHour,courseEndHour);
    }

    /**
     * Returns the specific category of this course.
     * Implementation of the abstract method defined in {@link Course}.
     * * @return A string literal indicating the course type: "Mandatory".
     */
    @Override
    public String getCourseType(){
        return "Mandatory";
    }
}
