package com.uni.registration.projectModels;
import com.uni.registration.projectModels.Courses.*;
import com.uni.registration.projectModels.Students.*;
import java.time.LocalDate;

public class Registration {
    private Student student;
    private Course course;
    private LocalDate registrationDate;

    public Registration(Student student,Course course){
        this.student=student;
        this.course=course;
        this.registrationDate=LocalDate.now();
    }

    public Student getStudent(){
        return student;
    }
    public void setStudent(Student student){
        this.student=student;
    }

    public Course getCourse(){
        return course;
    }
    public void setCourse(Course course){
        this.course=course;
    }

    public LocalDate getRegistrationDate(){
        return registrationDate;
    }
    public void setDate(LocalDate registrationDate){
        this.registrationDate=registrationDate;
    }

}
