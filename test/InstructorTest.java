package test;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.uni.registration.projectModels.Instructor;
import com.uni.registration.projectModels.Courses.*;
import com.uni.registration.projectServices.InstructorManager;

public class InstructorTest {
    private Instructor testInstructor;
    private InstructorManager instructormanager;

    @BeforeEach
    void setUp() {
        instructormanager = new InstructorManager();
        testInstructor = new Instructor("Yasemin", "Yener", 890, "Mechanical Engineering", "password56");
    }

    @Test
    void testInstructorLoginLogic() {
        instructormanager.addInstructor(testInstructor); 

        Instructor successfulLogin = instructormanager.login(890, "password56");
        Instructor failedLogin = instructormanager.login(890, "wrongPassword123");

        assertAll("Login System Checks",
            () -> assertNotNull(successfulLogin, "Login should succeed with correct credentials."),
            () -> assertNull(failedLogin, "Login should fail with an incorrect password.")
        );
    }

    @Test
    void testChangePassword() {
        testInstructor.setPassword("new4569");
        assertEquals("new4569", testInstructor.getPassword(), "Error: Password could not be updated.");
    }

    @Test
    void testInstructorCourseAssignment() {
        Course course = new ElectiveCourse("Embedded Systems", "EEE065", 5, testInstructor, 30, 0, "Tuesday",
            LocalTime.of(13, 0), LocalTime.of(15, 0));

        assertAll("Course Instructor Assignment",
            () -> assertEquals(890, course.getInstructor().getInstructorID()),
            () -> assertEquals("Yasemin", course.getInstructor().getInstructorName()),
            () -> assertEquals("Yener", course.getInstructor().getInstructorSurname())
        );
    }

    @Test
    void testFindInstructorByID() {
        instructormanager.addInstructor(testInstructor); 

        Instructor found = instructormanager.findInstructorByID(890);
        Instructor notFound = instructormanager.findInstructorByID(999); 

        assertAll("Find Instructor Logic",
            () -> assertNotNull(found, "Should find the instructor with existing ID 890."),
            () -> assertEquals("Yasemin", found.getInstructorName(), "The found instructor's name should match."),
            () -> assertNull(notFound, "Should return null for an ID that does not exist.")
        );
    }

    @Test
    void testDuplicateIdPrevention() {
        Instructor firstInstructor = new Instructor("Ahmet", "Yılmaz", 501, "Computer Engineering", "passAhmet123");
        instructormanager.addInstructor(firstInstructor);

        Instructor secondInstructor = new Instructor("Ayşe", "Kaya", 501, "Electrical Engineering", "passAyse456");
        instructormanager.addInstructor(secondInstructor);
        
        Instructor result = instructormanager.findInstructorByID(501);

        assertAll("Manager Duplicate ID Validation",
            () -> assertNotNull(result, "Instructor with ID 501 must exist."),
            () -> assertEquals("Ahmet", result.getInstructorName(), "The instructor in the system should still be 'Ahmet'."),
            () -> assertNotEquals("Ayşe", result.getInstructorName(), "The second instructor 'Ayşe' should not have been added.")
        );
    }
}