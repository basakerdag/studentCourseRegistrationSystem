package com.uni.registration.projectServices;

import com.uni.registration.projectModels.*;
import com.uni.registration.projectModels.Courses.Course;
import com.uni.registration.projectModels.Courses.ElectiveCourse;
import com.uni.registration.projectModels.Courses.MandatoryCourse;
import com.uni.registration.projectServices.InstructorManager;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class CourseCatalog {
    private List<Course> allCourses;
    private final String coursesPath = "src/data/courses.csv";
    InstructorManager instructorManager;

    public CourseCatalog(InstructorManager instructorManager) {
        this.allCourses = new ArrayList<>();
        this.instructorManager=instructorManager;
        loadCoursesFromCSV();
    }

    private void loadCoursesFromCSV() {
        File file = new File(coursesPath);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(coursesPath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

            String[] data = line.split(",");
            if (data.length < 7) continue;

            String courseName = data[0];
            String courseCode = data[1];
            int courseCredit = Integer.parseInt(data[2]);
            int instructorID = Integer.parseInt(data[3]);
            String courseType = data[4];
            int courseCapacity=Integer.parseInt(data[5]);
            int courseEnrolledCount=Integer.parseInt(data[6]);
            String courseDay=data[7];
            LocalTime courseStartHour=LocalTime.parse(data[8]);
            LocalTime courseEndHour=LocalTime.parse(data[9]);
            Instructor inst = instructorManager.findInstructorByID(instructorID);
            Course course;
            if (courseType.equalsIgnoreCase("Mandatory")) {
                course = new MandatoryCourse(courseName, courseCode, courseCredit, inst,courseCapacity,courseEnrolledCount,courseDay,courseStartHour,courseEndHour);
            } else {
                course = new ElectiveCourse(courseName, courseCode, courseCredit, inst,courseCapacity,courseEnrolledCount,courseDay,courseStartHour,courseEndHour);
            }
            allCourses.add(course);
        }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    public void addCourse(Course course) {
        if(findCourseByCode(course.getCourseCode())!=null){
            System.out.println("Error:A course with this code"+ course.getCourseCode()+"already exists.");
        }else{
                    allCourses.add(course);
        
        try (PrintWriter pw = new PrintWriter(new FileWriter(coursesPath, true))) {
            String csvLine = String.format("%s,%s,%d,%s,%s,%d,%d",
                course.getCourseName(),
                course.getCourseCode(),
                course.getCourseCredit(),
                course.getInstructor().getInstructorID(),
                course.getCourseType(),
                course.getCourseCapacity(),
                course.getCourseEnrolledCount()
            );
            pw.println(csvLine);
        } catch (IOException e) {
            System.err.println("Error saving to file: " + e.getMessage());
        }

        }
    }

    public void saveCoursesToCsv(){
        try (PrintWriter pw = new PrintWriter(new FileWriter(coursesPath, false))) {
        for (Course c : allCourses) {
            String csvLine = String.format("%s,%s,%d,%d,%s,%d,%d,%s,%s,%s",
                c.getCourseName(),
                c.getCourseCode(),
                c.getCourseCredit(),
                c.getInstructor().getInstructorID(),
                (c instanceof MandatoryCourse ? "Mandatory" : "Elective"),
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
        System.err.println("Error: Could not update courses file: " + e.getMessage());
    }
    }

    public void displayCourses() {
    if (allCourses.isEmpty()) {
        System.out.println("No courses available in the catalog.");
        return;
    }
    System.out.println("--- Available Courses ---");
    for (Course c : allCourses) {
        System.out.println("Code: " + c.getCourseCode() + " | Name: " + c.getCourseName() + " | Credit: " + c.getCourseCredit());
    }
}

    public List<Course> getAllCourses() {
        return allCourses;
    }

    public Course findCourseByCode(String code) {
        for (Course c : allCourses) {
            if (c.getCourseCode().equalsIgnoreCase(code)) {
                return c;
            }
        }
        return null;
    }

    public void removeCourseFromCatalog(Course course){
        if(allCourses.remove(course)){
            saveCoursesToCsv();
            System.out.println("Course " + course.getCourseCode() + " removed from catalog.");
        
        }
    }
}