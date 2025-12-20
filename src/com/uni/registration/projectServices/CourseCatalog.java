package com.uni.registration.projectServices;

import com.uni.registration.projectModels.*;
import com.uni.registration.projectModels.Courses.Course;
import com.uni.registration.projectModels.Courses.ElectiveCourse;
import com.uni.registration.projectModels.Courses.MandatoryCourse;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CourseCatalog {
    private List<Course> allCourses;
    private final String FILE_PATH = "src/data/courses.csv";

    public CourseCatalog() {
        this.allCourses = new ArrayList<>();
        loadCoursesFromCSV();
    }

    private void loadCoursesFromCSV() {
        File file = new File(FILE_PATH);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] data = line.split(",");
                                
                Instructor inst = new Instructor(data[3], data[4], data[5]);
                int credit = Integer.parseInt(data[2]);
                
                Course course;
                if (data[6].equalsIgnoreCase("Mandatory")) {
                    course = new MandatoryCourse(data[1], data[0], credit, inst);
                } else {
                    course = new ElectiveCourse(data[1], data[0], credit, inst);
                }
                allCourses.add(course);
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    public void addCourse(Course course) {
        allCourses.add(course);
        
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_PATH, true))) {
            String csvLine = String.format("%s,%s,%d,%s,%s,%s,%s",
                course.getCourseCode(),
                course.getCourseName(),
                course.getCourseCredit(),
                course.getInstructor().getInstructorName(),
                course.getInstructor().getInstructorID(),
                course.getInstructor().getInstructorDepartment(),
                course.getCourseType()
            );
            pw.println(csvLine);
        } catch (IOException e) {
            System.err.println("Error saving to file: " + e.getMessage());
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
}