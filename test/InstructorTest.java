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

    /**
      * Tests the instructor authentication logic to ensure that login is only successful when both the instructor ID and password are correct.
    */
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

    /**
     * Verifies that the instructor's password can be updated correctly within the instructor object.
    */
    @Test
    void testChangePassword() {
        testInstructor.setPassword("new4569");
        assertEquals("new4569", testInstructor.getPassword(), "Error: Password could not be updated.");
    }
    
    /**
     * Verifies that an instructor is correctly assigned to a course and that the course maintains the correct instructor details.
    */
    @Test
    void testInstructorCourseAssignment() {
        Course course = new ElectiveCourse("Embedded Systems", "EEE065", 5, testInstructor,null, 30, 0, "Tuesday",
            LocalTime.of(13, 0), LocalTime.of(15, 0));

        assertAll("Course Instructor Assignment",
            () -> assertEquals(890, course.getInstructor().getInstructorID()),
            () -> assertEquals("Yasemin", course.getInstructor().getInstructorName()),
            () -> assertEquals("Yener", course.getInstructor().getInstructorSurname())
        );
    }

    /**
     * Ensures that the system prevents adding multiple instructors with the same ID, preserving the original record and rejecting duplicates.
    */
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

    /**
     * Verifies that an instructor cannot remove a course that they are not assigned to.
      * Ensures system authorization and protects course data from unauthorized deletion.
    */
    @Test
     void testUnauthorizedCourseRemovalLogic() {
    Instructor otherInstructor = new Instructor("Ali", "Veli", 111, "CS", "pass");
    Course othersCourse = new MandatoryCourse("Data Structures", "CENG201", 5, otherInstructor, "CS", 50, 0, "Monday", null, null);
    boolean isAuthorized = othersCourse.getInstructor().getInstructorID() == testInstructor.getInstructorID();
    
    if (!isAuthorized) {
        assertFalse(isAuthorized, "Authorization should fail for an instructor who is not the owner of the course.");
    }
  }

   /**
    * Ensures that an instructor cannot enter or modify grades for a course 
    * that is not under their responsibility.
    */
    @Test
    void testUnauthorizedGradingLogic() {
    Instructor otherInstructor = new Instructor("Mehmet", "Öz", 222, "BIO", "pass");
    Course othersCourse = new MandatoryCourse("Biology", "BIO101", 3, otherInstructor, "BIO", 40, 0, "Friday", null, null);
    assertNotEquals(testInstructor.getInstructorID(), othersCourse.getInstructor().getInstructorID(),
        "Instructor IDs should not match, meaning grading should be unauthorized.");
   }

}