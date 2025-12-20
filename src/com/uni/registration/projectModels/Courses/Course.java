package com.uni.registration.projectModels.Courses;

import com.uni.registration.projectModels.Instructor;

public abstract class Course{
    private String courseName;
    private String courseCode;
    protected int courseCredit;
    private Instructor instructor;

    public Course (String courseName,String courseCode,int courseCredit,Instructor instructor){
        this.courseName=courseName;
        this.courseCode=courseCode;
        this.courseCredit=courseCredit;
        this.instructor=instructor;
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

