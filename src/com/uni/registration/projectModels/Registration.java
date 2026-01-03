package com.uni.registration.projectModels;
import com.uni.registration.projectModels.Courses.*;
import com.uni.registration.projectModels.Students.*;
import com.uni.registration.projectServices.CourseCatalog;
import com.uni.registration.projectServices.StudentManager;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages the course registration process, including schedule validations,capacity checks, and persistence of enrollment data in CSV files.
 */
public class Registration {
    private Student student;
    private Course course;
    private LocalDate registrationDate;
    
    /**
     * Path to the CSV file where enrollment records are stored.
     */
    public static final String enrollmentCsv = "src/data/enrollments.csv";


    public Registration(Student student,Course course){
        this.student=student;
        this.course=course;
        this.registrationDate=LocalDate.now();
    }

    public Student getStudent(){
        return student;
    }
    public Course getCourse(){
        return course;
    }
    public LocalDate getRegistrationDate(){
        return registrationDate;
    }
    
    /**
     * Checks for time overlaps between a new course and the student's existing schedule.
     * @param student The student to check.
     * @param newCourse The potential new course.
     * @return true if a time conflict exists, false otherwise.
     */
    public boolean isScheduleConflicting(Student student,Course newCourse){
        for(Course registeredCourse:student.getRegisteredCourses()){
            if(registeredCourse.getCourseDay().equalsIgnoreCase(newCourse.getCourseDay())){
                boolean overlaps=newCourse.getCourseStartHour().isBefore(registeredCourse.getCourseEndHour()) &&
                registeredCourse.getCourseStartHour().isBefore(newCourse.getCourseEndHour());
                if(overlaps){
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * Handles the full registration logic, verifying department eligibility,credit limits, capacity, conflicts, and duplicates.
     * Saves the successful registration to the CSV database.
     * @param student The student attempting to register.
     * @param course The course target for registration.
     */
    public void registerCourse(Student student, Course course) {
        boolean alreadyRegistered=false;
        for(Course registeredCourse:student.getRegisteredCourses()){
            if(registeredCourse.getCourseCode().equalsIgnoreCase(course.getCourseCode())){
                alreadyRegistered=true;
                break;
            }
        }
        if(alreadyRegistered){
            System.out.println("Warning: You are already registered for " + course.getCourseName() + ".");
            return;
        }

        if (course instanceof MandatoryCourse) {
        String courseDept = course.getCourseDepartment().toUpperCase();
        String studentDept = student.getDepartment().toUpperCase();

        if (!studentDept.contains(courseDept)) {
            System.out.println("Error: '" + course.getCourseName() + "' is a mandatory course for " + 
                               courseDept + " department. You are not eligible to register.");
            return;
        }
      }

        int currentTotalCredits = 0;
    for (Course c : student.getRegisteredCourses()) {
        currentTotalCredits += c.getCourseCredit();
    }
    if (currentTotalCredits + course.getCourseCredit() > 30) {
        System.out.println("Warning: Credit limit exceeded! Cannot exceed 30 credits. Current: " 
                            + currentTotalCredits + ", Course Credit: " + course.getCourseCredit());
        return;
    }


    if (course.getCourseEnrolledCount() >= course.getCourseCapacity()) {
        System.out.println("Warning! " + course.getCourseName() + " is full.");
        return;
    }

    if (isScheduleConflicting(student, course)) {
        System.out.println("Warning: Schedule conflict! You already have another course on " + 
            course.getCourseDay() + " between " + course.getCourseStartHour() + 
            " and " + course.getCourseEndHour() + ".");
        return;
    }
    
    student.getRegisteredCourses().add(course);
    course.incrementEnrolledCourse();
    this.student = student;
    this.course = course;
    stCrsSaveToCsv();
    System.out.println("Registration successful for " + course.getCourseName() + ".");
   } 

   /**
     * Saves a successful registration record (Student ID, Course Code, Date) to CSV.
     */
    public void stCrsSaveToCsv(){
        try (PrintWriter pw = new PrintWriter(new FileWriter(enrollmentCsv, true))) {
            pw.println(student.getStudentID() + "," + course.getCourseCode() + "," + registrationDate);
        } catch (IOException e) {
            System.err.println("Error writing to enrollments: " + e.getMessage());
        }
    }
    
    /**
     * Removes a specific enrollment record from the CSV file.
     * @param studentID ID of the student.
     * @param courseCode Code of the course to be removed.
     */
    public static void stCrsRemoveFromCsv(int studentID,String courseCode){
    List<String> remainingEnrollments = new ArrayList<>();

    try (BufferedReader br = new BufferedReader(new FileReader(enrollmentCsv))) {
        String line;
        while ((line = br.readLine()) != null) {
            String[] data = line.split(",");
            int sID = Integer.parseInt(data[0]);
            String cCode = data[1];

            if (!(sID == studentID && cCode.equalsIgnoreCase(courseCode))) {
                remainingEnrollments.add(line);
            }
        }
    } catch (IOException e) {
        System.err.println("Error reading enrollments for deletion: " + e.getMessage());
    }
    try (PrintWriter pw = new PrintWriter(new FileWriter(enrollmentCsv, false))) {
        for (String record : remainingEnrollments) {
            pw.println(record);
        }
    } catch (IOException e) {
        System.err.println("Error updating enrollments file: " + e.getMessage());
    }      
  }

    /**
     * Synchronizes the system state by loading all enrollment data from the CSV file.
     * @param studentManager Service to find student objects.
     * @param courseCatalog Service to find course objects.
     */
    public static void loadEnrollments(StudentManager studentManager,CourseCatalog courseCatalog){
    java.io.File file = new java.io.File(enrollmentCsv);
    if (!file.exists()) return;

    try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(enrollmentCsv))) {
        String line;
        while ((line = br.readLine()) != null) {
            String[] data = line.split(",");
            if (data.length < 2) continue;
            int studentID = Integer.parseInt(data[0]);
            String courseCode = data[1];

            Student student = studentManager.findStudentByID(studentID);
            Course course = courseCatalog.findCourseByCode(courseCode);
            if (student != null && course != null) {
                if (!student.getRegisteredCourses().contains(course)) {
                    student.getRegisteredCourses().add(course);
                    course.incrementEnrolledCourse();
                }
            }
        }
        System.out.println("Enrollment data successfully recovered from CSV.");
    } catch (java.io.IOException e) {
        System.err.println("Error while recovering enrollments: " + e.getMessage());
    }
    }
    
    /**
     * Processes a course withdrawal for a student and updates the persistent storage.
     * @param student The student dropping the course.
     * @param course The course to be dropped.
     */
    public static void dropCourse(Student student,Course course){
        if(student.getRegisteredCourses().remove(course)){
            course.decrementEnrolledCourse();
            stCrsRemoveFromCsv(student.getStudentID(),course.getCourseCode());
            System.out.println("Success:"+ course.getCourseCode()+"has been dropoped.");
        }else{
            System.out.println("Error: Your are not registered this course.");
        }
    }
    
    /**
     * Cleans up all enrollment records associated with a specific course code.
     * Used when a course is completely removed from the catalog.
     * @param courseCode The code of the course to purge.
     */
    public static void removeAllEnrollmentsForCourse(String courseCode) {
    List<String> remainingEnrollments = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(enrollmentCsv))) {
        String line;
        while ((line = br.readLine()) != null) {
            if(line.trim().isEmpty()) continue;
            String[] data = line.split(",");
            String cCode = data[1];

            if (!(cCode.equalsIgnoreCase(courseCode))) {
                remainingEnrollments.add(line);
            }
        }
    } catch (IOException e) {
        System.err.println("Error reading enrollments for deletion: " + e.getMessage());
    }
    try (PrintWriter pw = new PrintWriter(new FileWriter(enrollmentCsv, false))) {
        for (String record : remainingEnrollments) {
            pw.println(record);
        }
    } catch (IOException e) {
        System.err.println("Error updating enrollments file: " + e.getMessage());
    }      
    }

    

}
