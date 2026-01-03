package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalTime;
import com.uni.registration.projectModels.Courses.Course;
import com.uni.registration.projectModels.Courses.ElectiveCourse;
import com.uni.registration.projectModels.Courses.MandatoryCourse;
import com.uni.registration.projectModels.Students.*;
import com.uni.registration.projectServices.StudentManager;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class StudentTest {
    private Student testStudent;
    private StudentManager studentManager;

    @BeforeEach
    void setUp() {
        testStudent = new UndergraduateStudent("Deniz", "Aydin", 101, "Computer Engineering", 
        2, "studentPass123");
        studentManager = new StudentManager();
    }

    @Test
    void testAddStudentToCsvPersistence() throws IOException {
        studentManager.addStudent(testStudent);

        List<String> lines = Files.readAllLines(Paths.get("src/data/students.csv"));
        String lastLine = lines.get(lines.size() - 1);
        String[] columns = lastLine.split(",");
        assertAll("Undergraduate Student CSV Verification",
            () -> assertEquals("Deniz", columns[0], "First name should match."),
            () -> assertEquals("Aydin", columns[1], "Surname should match."),
            () -> assertEquals("101", columns[2], "Student ID should match."),
            () -> assertEquals("studentPass123", columns[5], "Password should be in the correct column.")
        );
    }

    @Test
    void testDuplicateIdPrevention() {
    Student firstStudent = new UndergraduateStudent("Ali", "Kaya", 56, "Computer Engineering", 1, "pass123");
    studentManager.addStudent(firstStudent); 

    Student secondStudent = new UndergraduateStudent("Veli", "Can", 56, "EE", 2, "pass456");
    studentManager.addStudent(secondStudent); 
    Student result = studentManager.findStudentByID(56);
    
    assertAll("Manager Duplicate ID Validation",
        () -> assertNotNull(result, "Student with ID 56 must exist."),
        () -> assertEquals("Ali", result.getName(), "The student in the system should still be 'Ali'."),
        () -> assertNotEquals("Veli", result.getName(), "The second student 'Veli' should not have been added.")
       );
    }

   @Test
   void testChangeStudentPasswordLogic() {
    String currentPass = "studentPass123";
    String newPass = "newSecurePass456";
    String wrongPass = "wrong123";

    assertAll("Change Password Logic Validations",
        () -> assertTrue(testStudent.changeStudentPassword(currentPass, newPass), 
            "Password should change successfully with correct credentials."),
        () -> assertFalse(testStudent.changeStudentPassword(wrongPass, "somePass"), 
            "Should return false if the current password is incorrect."),
        () -> assertFalse(testStudent.changeStudentPassword(newPass, newPass), 
            "Should return false if the new password is the same as the current one.")
      );
   }

   @Test
   void testStudentLoginLogic() {
    studentManager.addStudent(testStudent); 
    Student successfulLogin = studentManager.login(101, "studentPass123");
    Student wrongPasswordLogin = studentManager.login(101, "wrongPass456");
    Student nonExistingStudentLogin = studentManager.login(999, "anyPass");

    assertAll("Student Login Logic Validations",
        () -> assertNotNull(successfulLogin, 
            "Login should return the student object when ID and password are correct."),
                () -> assertNull(wrongPasswordLogin, 
            "Login should return null if the password does not match."),
                () -> assertNull(nonExistingStudentLogin, 
            "Login should return null if the student ID is not found in the system.")
      );
    }

    @Test
    void testGpaCalculationWithMissingFinal() {
    String courseCode = "CENG101";  
    Course mockCourse = new MandatoryCourse("Java", courseCode, 5, null,null, 30, 0, "Monday", null, null);

    testStudent.registerCourse(mockCourse); 
    
    testStudent.addGrade(courseCode, 80.0, null);
    assertEquals(0.0, testStudent.calculateGPA(), "GPA should be 0.0 for courses with a missing final exam grade.");
}

   @Test
    void testGradePersistenceInMemory() {
        String courseCode = "MATH101";        
        Course mathCourse = new MandatoryCourse("Math", courseCode, 5, null,null, 30, 0, "Monday", null, null);
        testStudent.registerCourse(mathCourse); 
        
        testStudent.addGrade(courseCode, 75.0, null);
        
        Double[] grades = testStudent.getGrades().get(courseCode);
        
        assertAll("Grade Data Integrity Validations",
            () -> assertNotNull(grades, "Grade array should not be null."),
            () -> assertEquals(75.0, grades[0], "Midterm grade must be recorded correctly."),
            () -> assertNull(grades[1], "Final grade should remain null if not entered.")
        );;
    }

    @Test
    void testRegistrationValidationBeforeGrading() {
        String unregisteredCourse = "HIST101";
        
        testStudent.addGrade(unregisteredCourse, 50.0, 50.0);
        
        assertNull(testStudent.getGrades().get(unregisteredCourse), 
            "Grades should not be assigned to a course the student is not registered for.");
    }

}