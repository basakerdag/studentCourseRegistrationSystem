package com.uni.registration.projectModels.Courses;
import java.time.LocalTime;
import com.uni.registration.projectModels.Instructor;

/**
 * Abstract class representing a generic course in the registration system.
 * It serves as a base class for different types of courses such as Mandatory and Elective.
 */

public abstract class Course{
    private String courseName;
    private String courseCode;
    protected int courseCredit;
    private Instructor instructor;
    private String courseDepartment;
    private int courseCapacity;
    private int courseEnrolledCount;
    private String courseDay;
    private LocalTime courseStartHour;
    private LocalTime courseEndHour;

    public Course (String courseName,String courseCode,int courseCredit,Instructor instructor,String courseDepartment,int courseCapacity,int courseEnrolledCount,String courseDay,
        LocalTime courseStartHour,LocalTime courseEndHour){
        this.courseName=courseName;
        this.courseCode=courseCode;
        this.courseCredit=courseCredit;
        this.instructor=instructor;
        this.courseDepartment=courseDepartment;
        this.courseCapacity=courseCapacity; 
        this.courseEnrolledCount=0;
        this.courseDay=courseDay;
        this.courseStartHour=courseStartHour;
        this.courseEndHour=courseEndHour;
    }

    public abstract String getCourseType();

    public String getCourseName(){
        return courseName;
    }
    public void setCourseName(String courseName){
         this.courseName=courseName;
    }


    public String getCourseCode(){
        return courseCode;
    }
    public void setCourseCode(String courseCode){
        this.courseCode=courseCode;
    }

    public int getCourseCredit(){
        return courseCredit;
    }
    public void setCourseCredit(int courseCredit){
        this.courseCredit=courseCredit;
    }

    public Instructor getInstructor(){
        return instructor;
    }
    public void setInstructor(Instructor instructor){
        this.instructor=instructor;
    }

    public String getCourseDepartment(){
        return courseDepartment;
    }
    public void setCourseDepartment(String courseDepartment){
        this.courseDepartment=courseDepartment;
    }

    public int getCourseCapacity(){
        return courseCapacity;
    }

    /**
    * The new capacity cannot be less than the current number of enrolled students.
    * @param courseCapacity The new capacity to be set.
    */
    public void setCourseCapacity(int courseCapacity){
        if(courseCapacity>=this.courseEnrolledCount){
           this.courseCapacity=courseCapacity;
        }else{
            System.out.println("Course capacity cannot be less than the current number of enrolled students.");
        }
    }

    public int getCourseEnrolledCount(){
        return courseEnrolledCount;
    }

    /**
     * Increments the enrolled student count if the course has not reached its capacity.
    */
    public void incrementEnrolledCourse(){
        if (courseEnrolledCount<courseCapacity){
            courseEnrolledCount++;
        }
    }

    /**
     *Decrements the enrolled student count, ensuring it does not drop below zero.
    */
    public void decrementEnrolledCourse(){
        if(this.courseEnrolledCount>0){
            this.courseEnrolledCount--;
        }
    }
    
    public String getCourseDay(){
        return courseDay;
    }
    public void setCourseDay(String courseDay){
        this.courseDay=courseDay;
    }

    public LocalTime getCourseStartHour(){
        return courseStartHour;
    }
    public void setCourseStartHour(LocalTime courseStartHour){
        this.courseStartHour=courseStartHour;
    }

    public LocalTime getCourseEndHour(){
        return courseEndHour;
    }

    /**
    * Sets the course end hour and validates that it occurs after the start hour.
    * @param courseEndHour The ending time to be validated and set.
    */
    public void setCourseEndHour(LocalTime courseEndHour){
        if(this.courseStartHour!=null && courseEndHour.isBefore(this.courseEndHour)){
            System.out.println("Course end hour cannot be before course start hour");
        }else{
            this.courseEndHour=courseEndHour;
        }
    }

    /**
     * Compares this course with another object for equality based on the course code.
     * @param o The object to compare with.
     * @return true if the course codes are identical, false otherwise.
     */

     @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course)) return false;
        Course course = (Course) o;
        return courseCode != null && courseCode.equals(course.courseCode);
    }

    @Override
    public int hashCode() {
        return courseCode != null ? courseCode.hashCode() : 0;
    }

}

