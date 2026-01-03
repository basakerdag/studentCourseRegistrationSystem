package test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import com.uni.registration.projectModels.Students.*;
import com.uni.registration.projectModels.Courses.*;
import com.uni.registration.projectModels.Registration;
import java.time.LocalTime;

public class RegistrationTest {
    private Student testStudent;
    private Course capacityCourse;
    private Registration registrationSystem;
    private Course mathCourse;

    @BeforeEach
    void setUp() {
    testStudent = new UndergraduateStudent("Deniz", "Aydin", 101, "CE", 2, "pass123");
    capacityCourse = new MandatoryCourse("Software Validation", "SENG302", 6, null, "CE", 1, 0, "Friday", LocalTime.of(14, 0), LocalTime.of(17, 0));
    
    registrationSystem = new Registration(testStudent, capacityCourse);
    
    mathCourse = new MandatoryCourse("Calculus I", "MATH101", 5, null, "MATH", 50, 0, "Monday", LocalTime.of(9, 0), LocalTime.of(11, 0));
   }

    /**
     * Verifies that a course cannot exceed its maximum capacity.
     * Ensures that subsequent registration attempts are rejected once the limit is reached.
    */
    @Test
    void testCapacityFull() {

        registrationSystem.registerCourse(testStudent, capacityCourse);
        Student secondStudent = new UndergraduateStudent("Ali", "Yilmaz", 102, "CE", 2, "pw456");
 
        registrationSystem.registerCourse(secondStudent, capacityCourse);
        assertFalse(secondStudent.getRegisteredCourses().contains(capacityCourse), 
            "A second student should not be able to register when capacity is 1 and already full.");
    }
    /**
     * Verifies the successful link between a student and a course upon registration.
     * Ensures the course is added to the student's list and the course's enrollment count increases.
    */
    @Test
    void testRegistrationLink() {
        registrationSystem.registerCourse(testStudent, capacityCourse);

        assertAll("Registration Success Checks",
            () -> assertTrue(testStudent.getRegisteredCourses().contains(capacityCourse), 
                "The student should have the course in their registered list."),
            () -> assertEquals(1, capacityCourse.getCourseEnrolledCount(), 
                "The course's enrolled count should have incremented.")
        );
    }
    
    /**
     * Validates that the system prevents registration for courses with overlapping schedules.
     * Ensures a student cannot be enrolled in two courses at the same time on the same day.
    */
    @Test
    void testScheduleConflict() {
    Course firstCourse = new MandatoryCourse("Math 101", "MAT101", 5, null, "CE", 50, 0, "Monday", LocalTime.of(9, 0), LocalTime.of(11, 0));
    registrationSystem.registerCourse(testStudent, firstCourse);

    Course conflictingCourse = new MandatoryCourse("Physics 101", "PHYS101", 5, null, "CE", 50, 0, "Monday", LocalTime.of(10, 30), LocalTime.of(12, 30)); 
    registrationSystem.registerCourse(testStudent, conflictingCourse);

    assertAll("Schedule Conflict Validations",
        () -> assertEquals(1, testStudent.getRegisteredCourses().size(), "Student should only have 1 course due to schedule conflict."),
        () -> assertFalse(testStudent.getRegisteredCourses().contains(conflictingCourse), "The conflicting course should not be in the student's registered list.")
    );
}
   
   /**
   * Verifies that dropping a course correctly updates both the student's registered course list and the course's enrollment count.
   */
   @Test
   void testDropCourse() {
    registrationSystem.registerCourse(testStudent, capacityCourse);
    int countAfterRegister = capacityCourse.getCourseEnrolledCount(); 

    Registration.dropCourse(testStudent, capacityCourse);

    assertAll("Drop Course Validations",
        () -> assertFalse(testStudent.getRegisteredCourses().contains(capacityCourse), 
            "Course should be removed from student list."),
        () -> assertEquals(countAfterRegister - 1, capacityCourse.getCourseEnrolledCount(), 
            "Course enrollment count should decrease by 1.")
      );
   }
   
   /**
    * Verifies that when a course is removed, all associated student enrollment records are permanently deleted from the physical CSV database.
   */
  @Test
  void testRemoveAllEnrollmentsFromCsv() throws java.io.IOException {
    registrationSystem.registerCourse(testStudent, mathCourse); 
    String courseCodeToDelete = mathCourse.getCourseCode();
    
    Registration.removeAllEnrollmentsForCourse(courseCodeToDelete);
    java.nio.file.Path path = java.nio.file.Paths.get(Registration.enrollmentCsv);

    if (java.nio.file.Files.exists(path)) {
        java.util.List<String> lines = java.nio.file.Files.readAllLines(path);
        
        for (String line : lines) {
            assertFalse(line.contains(courseCodeToDelete), 
                "The CSV should not contain any records for the deleted course: " + courseCodeToDelete);
        }
    } else {
        assertTrue(true); 
    }
   }
}