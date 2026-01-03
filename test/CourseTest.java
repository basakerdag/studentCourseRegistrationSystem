package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.uni.registration.projectModels.Courses.*;
import java.time.LocalTime;

/**
 * Unit tests for the Course class logic.
 * Focuses on enrollment management, capacity validation, and type consistency.
 */
public class CourseTest {
    private Course testCourse;

    @BeforeEach
    void setUp() {
        testCourse = new MandatoryCourse("Software Validation", "SENG302", 6, null, "SENG", 2, 0, "Friday", LocalTime.of(14, 0), LocalTime.of(17, 0));
    }

    /**
     * Verifies that the enrolled student count increments and decrements correctly.
     * Ensures that the internal counter reflects the actual number of registrations.
     */
    @Test
    void testEnrollmentLogic() {
        testCourse.incrementEnrolledCourse();
        assertEquals(1, testCourse.getCourseEnrolledCount(), "Enrolled count should be 1 after one increment.");

        testCourse.decrementEnrolledCourse();
        assertEquals(0, testCourse.getCourseEnrolledCount(), "Enrolled count should return to 0 after decrement.");
    }

    /**
     * Tests the boundary case where the course enrollment reaches its maximum capacity.
     * Confirms that the system correctly identifies the course as full.
     */
    @Test
    void testCourseFullLogic() {
        testCourse.incrementEnrolledCourse();
        testCourse.incrementEnrolledCourse();

        boolean isFull = testCourse.getCourseEnrolledCount() >= testCourse.getCourseCapacity();
        assertTrue(isFull, "Course should be reported as full when enrolled count equals capacity.");
    }

    /**
     * Ensures that the course type (Inheritance property) is correctly identified.
     * Validates that a MandatoryCourse object returns "Mandatory" as its type.
     */
    @Test
    void testCourseTypeConsistency() {
        assertEquals("Mandatory", testCourse.getCourseType(), "The course type should correctly identify as Mandatory.");
    }
}