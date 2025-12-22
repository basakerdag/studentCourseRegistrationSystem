package com.uni.registration.projectServices;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import com.uni.registration.projectModels.Instructor;

public class InstructorManager {
    private final String filePath = "src/data/instructors.csv";
    public void addInstructor(Instructor instructor) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filePath, true))) {
            String csvLine = String.format("%s,%s,%s,%s,%s",
                instructor.getInstructorName(),
                instructor.getInstructorSurname(),
                instructor.getInstructorID(),
                instructor.getInstructorDepartment(),
                instructor.getPassword()
            );
            pw.println(csvLine);
            System.out.println("Instructor successfully saved to CSV.");
        } catch (IOException e) {
            System.err.println("Error saving instructor: " + e.getMessage());
        }
    }

}
