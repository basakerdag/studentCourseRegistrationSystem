package com.uni.registration.projectModels;

/**
 * Represents an instructor in the registration system.
 * Manages personal information, departmental affiliation, and security credentials.
 */

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

    /**
     * @return A formatted string with instructor name and ID.
     */
    @Override
    public String toString() {
    return "Instructor: " + instructorName + " (ID: " + instructorID + ")";
     }

     /**
     * Updates the instructor's password after verifying the current one.
     * @param currentInstructorPassword The current password to verify.
     * @param newInstructorPassword The new password to be set.
     * @return true if the password was successfully updated, false otherwise.
     */
    public boolean changeInstructorPassword(String currentInstructorPassword, String newInstructorPassword){
        if(!this.password.equals(currentInstructorPassword)){
            System.out.println("Current password is incorrect.");
            return false;
        }
        if(currentInstructorPassword.equals(newInstructorPassword)){
            System.out.println("New password cannot be equal current password.");
            return false;
        }

        this.password=newInstructorPassword;
        System.out.println("Password changed successfully.");
        return true;
    }

}