package com.uni.registration.projectServices;

import com.uni.registration.projectModels.*;
import com.uni.registration.projectModels.Courses.Course;
import com.uni.registration.projectModels.Courses.ElectiveCourse;
import com.uni.registration.projectModels.Courses.MandatoryCourse;
import com.uni.registration.projectModels.Students.Student;

import java.io.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class that manages the collection of all available courses.
 * Handles loading from and saving to the courses CSV database.
 */
public class CourseCatalog {
    private List<Course> allCourses;
    private final String coursesPath = "src/data/courses.csv";
    InstructorManager instructorManager;

    public CourseCatalog(InstructorManager instructorManager) {
        this.allCourses = new ArrayList<>();
        this.instructorManager=instructorManager;
        loadCoursesFromCSV();
    }

    /**
     * Reads course data from CSV and populates the catalog list.
     * Differentiates between Mandatory and Elective course types during instantiation.
     */
    private void loadCoursesFromCSV() {
        File file = new File(coursesPath);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(coursesPath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

            String[] data = line.split(",");
            if (data.length < 13) continue;

            String courseName = data[0];
            String courseCode = data[1];
            int courseCredit = Integer.parseInt(data[2]);
            int instructorID = Integer.parseInt(data[3]);
            String instructorName=data[4];
            String  instructorSurname=data[5];
            String courseDepartment=data[6].trim();
            String courseType = data[7].trim();
            int courseCapacity=Integer.parseInt(data[8].trim());
            int courseEnrolledCount=Integer.parseInt(data[9].trim());
            String courseDay=data[10];
            LocalTime courseStartHour=LocalTime.parse(data[11].trim());
            LocalTime courseEndHour=LocalTime.parse(data[12].trim());
            Instructor inst = instructorManager.findInstructorByID(instructorID);
            Course course;
            if (courseType.equalsIgnoreCase("Mandatory")) {
                course = new MandatoryCourse(courseName, courseCode, courseCredit, inst,courseDepartment,courseCapacity,courseEnrolledCount,courseDay,courseStartHour,courseEndHour);
            } else {
                course = new ElectiveCourse(courseName, courseCode, courseCredit, inst,courseDepartment,courseCapacity,courseEnrolledCount,courseDay,courseStartHour,courseEndHour);
            }
            allCourses.add(course);
        }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }
    
    /**
     * Adds a new course to the catalog if the course code is unique.
     * Automatically triggers a CSV sync upon successful addition.
     * @param course The course object to add.
     */
     public void addCourse(Course course) {
    if (findCourseByCode(course.getCourseCode()) != null) {
        System.out.println("Error: A course with this code " + course.getCourseCode() + " already exists.");
    } else {
        allCourses.add(course);
        saveCoursesToCsv();
    }
  } 

    /**
     * Overwrites the courses CSV file with the current state of the course list.
     */
    public void saveCoursesToCsv() {
    try (PrintWriter pw = new PrintWriter(new FileWriter(coursesPath, false))) {
        for (Course c : allCourses) {
            Instructor inst = c.getInstructor();
            String csvLine = String.format("%s,%s,%d,%d,%s,%s,%s,%s,%d,%d,%s,%s,%s",
                c.getCourseName(),
                c.getCourseCode(),
                c.getCourseCredit(),
                inst.getInstructorID(),
                inst.getInstructorName(),       
                inst.getInstructorSurname(), 
                c.getCourseDepartment(),      
                c.getCourseType(),
                c.getCourseCapacity(),
                c.getCourseEnrolledCount(),
                c.getCourseDay(),
                c.getCourseStartHour().toString(),
                c.getCourseEndHour().toString()
            );
            pw.println(csvLine);
        }
        System.out.println("Courses database successfully synced.");
    } catch (IOException e) {
        System.err.println("Error: Could not save courses - " + e.getMessage());
    }
  }

   /**
     * Displays the complete course catalog without any filtering.
     * This administrative view shows every course's type (Mandatory/Elective) 
     * and its target department. Used primarily by instructors to see 
     * the global state of the catalog.
    */
    public void displayCourses() {
    if (allCourses.isEmpty()) {
        System.out.println("No courses available in the catalog.");
        return;
    }
    System.out.println("--- General Course Catalog ---");
    for (Course c : allCourses) {
        String type = (c instanceof MandatoryCourse) ? "[MANDATORY]" : "[ELECTIVE]";
        System.out.println(type + " Code: " + c.getCourseCode() + " | Name: " + c.getCourseName() + " | Dept: " + c.getCourseDepartment());
     }
  }

   /**
     * Lists available courses filtered by the student's department.
     * Shows mandatory courses only if they belong to the student's department,
     * while elective courses remain visible to everyone.
     * * @param student The student for whom the course list is customized.
     */
   public void displayCourses(Student student) {
    if (allCourses.isEmpty()) {
        System.out.println("No courses available in the catalog.");
        return;
    }
    System.out.println("--- Available Courses for " + student.getName() + " ---");
    
    for (Course c : allCourses) {
        if (c instanceof MandatoryCourse) {
            String courseDept = c.getCourseDepartment().toUpperCase();
            String studentDept = student.getDepartment().toUpperCase();

            if (!studentDept.contains(courseDept)) {
                continue;
            }
        }
      
        String typeLabel = (c instanceof MandatoryCourse) ? "[MANDATORY]" : "[ELECTIVE]";
        System.out.println(typeLabel + " Code: " + c.getCourseCode() + " | Name: " + c.getCourseName() + " | Credit: " + c.getCourseCredit());
    }
}
   
   public List<Course> getAllCourses() {
    return allCourses;
   }

    /**
     * Searches for a course in the catalog using its unique course code.
     * @param code The code to search for (case-insensitive).
     * @return The found {@link Course} object, or null if no match exists.
     */
    public Course findCourseByCode(String code) {
        for (Course c : allCourses) {
            if (c.getCourseCode().equalsIgnoreCase(code)) {
                return c;
            }
        }
        return null;
    }
    
    /**
     * Removes a course from the catalog and updates the CSV file.
     * @param course The course to be removed.
     */
    public void removeCourseFromCatalog(Course course){
        if(allCourses.remove(course)){
            saveCoursesToCsv();
            System.out.println("Course " + course.getCourseCode() + " removed from catalog.");
        
        }
    }
}