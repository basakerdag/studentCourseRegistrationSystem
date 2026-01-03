package com.uni.registration.projectModels.Students;
import java.util.ArrayList;
import com.uni.registration.projectModels.Registrable;
import com.uni.registration.projectModels.Registration;
import com.uni.registration.projectModels.Courses.Course;
import java.util.HashMap;
import java.util.Map;

/**
 * Abstract base class representing a student in the university system.
 * This class implements the {@link Registrable} interface and defines core student
 * attributes and behaviors, serving as a template for specific student levels.
 */


public abstract class Student implements Registrable{
    private String name;
    private String surname;
    private int studentID;
    private String department;
    private int year;
    private String password;
    protected ArrayList<Course> registeredCourse;
    protected Map<String, Double[]> grades;


    public Student(String name,String surname,int studentID,String department,int year,String password){
        this.name=name;
        this.surname=surname;
        this.studentID=studentID;
        this.department=department;
        this.year=year;
        this.password=password;
        this.registeredCourse=new ArrayList<>();
        this.grades = new HashMap<>();
    }

    /**
     * Returns the specific type of the student (Undergraduate or Graduate).
     * @return A string representing the student category.
     */
    public abstract String getStudentType();
    
    /**
     * Registers the student for a new course if they are not already enrolled.
     * Triggers a new {@link Registration} record upon success.
     * @param course The course to be added to the student's schedule.
     */
    @Override
    public void registerCourse(Course course){
        if(!registeredCourse.contains(course)){
            registeredCourse.add(course);
            System.out.println(course.getCourseName()+"has been successfully added.");
            new Registration(this, course);
        }else{
            System.out.println("Error: The course with code "+ course.getCourseCode()+ " is already in your list.");
        }
    }

    /**
     * Removes a course from the student's registered list and updates the database record.
     * @param course The course to be removed.
     */
    @Override
    public void unregisterCourse(Course course){
        if(registeredCourse.contains(course)){
            registeredCourse.remove(course);
            Registration.stCrsRemoveFromCsv(this.getStudentID(),course.getCourseCode());
            System.out.println(course.getCourseName()+"registration has been removed.");
        }else{
            System.out.println("Error: This course is not in your list.");
        }
    }
    
    /**
     * Displays a formatted list of all courses the student is currently registered for.
     * If the student has no registrations, it prints a notification message.
     * Each course is listed with its unique code, name, and credit value.
     */
    @Override
    public void viewRegisteredCourses() {
    System.out.println("\n--- Registered Courses for " + this.getName() + " ---");   
    if (this.getRegisteredCourses().isEmpty()) {
        System.out.println("You are not registered for any courses yet.");
    } else {    
        for (Course c : this.getRegisteredCourses()) {
            System.out.println("- [" + c.getCourseCode() + "] " + c.getCourseName() +  " (" + c.getCourseCredit() + " Credits)");
        }
    }
    System.out.println("------------------------------------------");
   }
   
   /**
     * Checks if the student has any time overlap between an existing course and a new course.
     * @param course The new course to check against the current schedule.
     * @return true if a conflict is detected, false otherwise.
     */
   @Override
   public boolean hasScheduleConflict(Course course){
    Registration checker=new Registration(this,course);
    return checker.isScheduleConflicting(this,course);
   }
   
   /**
     * Checks if the student is registered for a course by its code (case-insensitive).
     * @param courseCode Unique code of the course.
     * @return true if enrolled, false otherwise.
     */
   @Override
   public boolean isRegistered(String courseCode) {
    for (Course c : this.getRegisteredCourses()) {
        if (c.getCourseCode().equalsIgnoreCase(courseCode)) {
            return true;
        }
     }
     return false;
    }

    /**
     * Abstract method to calculate tuition fees based on student type and credit load.
     * @return The total tuition amount.
     */
    public abstract double calculateTuition();

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name=name;
    }

    public String getSurname(){
        return surname;
    }
    public void setSurname(String surname){
        this.surname=surname;
    }
    

    public int getStudentID(){
        return studentID;
    }
    public void setStudentID(int studentID){
        this.studentID=studentID;
    }

    public String getDepartment(){
        return department;
    }
    public void setDepartment(String department){
        this.department=department;
    }

    public int getYear(){
        return year;
    }
    public void setYear(int year){
        this.year=year;
    }

    public String getPassword(){
        return password;
    }
    public void setPassword(String password){
        this.password=password;
    }

    public ArrayList<Course> getRegisteredCourses(){
        return registeredCourse;
    }

    public boolean isEnrolled(Course course){
        return registeredCourse.contains(course);
    }

    public Map<String, Double[]> getGrades() {
    return grades;
    } 

    /**
     * Updates the student's password after validating the current one.
     * @param currentStudentPassword The existing password. 
     * @param newSStudentPassword The new password to set.
     * @return true if changed successfully, false otherwise.
     */
    public boolean changeStudentPassword(String currentStudentPassword,String newSStudentPassword){
        if(!this.password.equals(currentStudentPassword)){
            System.out.println("Current password is incorrect.");
            return false;
        }
        if(currentStudentPassword.equals(newSStudentPassword)){
           System.out.println("New password cannot be equal current password.");
           return false;
        }

        this.password=newSStudentPassword;
        System.out.println("Password changed succesfully.");
        return true;
    }
    
    /**
     * Assigns or updates grades for a course if the student is registered.
     * Supports partial updates; provided non-null grades overwrite existing ones.
     * @param courseCode The unique course identifier.
     * @param midterm Midterm score or null to keep unchanged.
     * @param finalExam Final score or null to keep unchanged.
    */ 
   public void addGrade(String courseCode, Double midterm, Double finalExam) {
    if (isRegistered(courseCode)) {
        Double[] existing = grades.getOrDefault(courseCode, new Double[2]);
        if (midterm != null) existing[0] = midterm;
        if (finalExam != null) existing[1] = finalExam;

        grades.put(courseCode, existing);
    } else {
        System.out.println("Error: Student is not registered for " + courseCode);
    }
  }
    /**
    * Calculates the weighted GPA based on completed courses.
    * Only includes courses where both midterm (40%) and final (60%) grades are entered.
    * @return The calculated GPA (0.0 to 4.0), or 0.0 if no courses are completed.
    */
    public double calculateGPA() {
    double totalPoints = 0;
    int totalCredits = 0;

    for (Course course : registeredCourse) {
        Double[] notes = grades.get(course.getCourseCode());
        if (notes != null && notes.length >= 2) {
            if (notes[0] != null && notes[1] != null) {
                double average = (notes[0] * 0.4) + (notes[1] * 0.6);
                double gradePoint = (average / 100) * 4.0;
                
                totalPoints += gradePoint * course.getCourseCredit();
                totalCredits += course.getCourseCredit();
            }
        }
    }
    if (totalCredits == 0) {
        return 0.0;
    }

    return totalPoints / totalCredits;
}

}
 

