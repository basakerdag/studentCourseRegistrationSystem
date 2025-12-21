package com.uni.registration.projectModels;

public class Instructor{
    private String instructorName;
    private String instructorSurname;
    private String instructorID;
    private String instructorDepartment;
    private String password;

    public Instructor(String instructorName,String instructorSurname,String instructorID,String instructorDepartment,String password){
        this.instructorName=instructorName;
        this.instructorSurname=instructorSurname;
        this.instructorID=instructorID;
        this.instructorDepartment=instructorDepartment;
        this.password=password;
    }

    public String getInstructorName(){
        return instructorName;
    }
    public void setInstructorName(String instructorName){
        this.instructorName=instructorName;
    }

    public String getInstructorSurname(){
        return instructorSurname;
    }
    public void setInstructorSurname(String instructorSurname){
        this.instructorSurname=instructorSurname;
    }

    public String getInstructorID(){
        return instructorID;
    }
    public void setInstructorID(String instructorID){
        this.instructorID=instructorID;
    }

    public String getInstructorDepartment(){
        return instructorDepartment;
    }
    public void setInstructorDepartment(String instructorDepartment){
        this.instructorDepartment=instructorDepartment;
    }

    public String getPassword(){
        return password;
    }
    public void setPassword(String password){
        this.password=password;
    }

    @Override
    public String toString() {
    return "Instructor: " + instructorName + " (ID: " + instructorID + ")";
     }

}