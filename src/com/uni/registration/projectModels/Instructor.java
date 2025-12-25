package com.uni.registration.projectModels;

public class Instructor{
    private String instructorName;
    private String instructorSurname;
    private int instructorID;
    private String instructorDepartment;
    private String password;

    public Instructor(String instructorName,String instructorSurname,int instructorID,String instructorDepartment,String password){
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

    public int getInstructorID(){
        return instructorID;
    }
    public void setInstructorID(int instructorID){
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