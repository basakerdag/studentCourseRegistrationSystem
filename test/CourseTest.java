package test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.uni.registration.projectModels.Courses.*;
import java.time.LocalTime;

public class CourseTest {
    private Course testCourse;

    @BeforeEach
    void setUp(){
        testCourse= new MandatoryCourse("Software Validation", "SENG302", 6, null, 2, 0, "Friday",
        LocalTime.of(14, 0), LocalTime.of(17, 0));
    }
    
    
    @Test
    void testEnrollmentLogic(){
        testCourse.incrementEnrolledCourse();
        assertEquals(1, testCourse.getCourseEnrolledCount(),"Enrolled count should be 1 after one increment.");

        testCourse.decrementEnrolledCourse();
        assertEquals(0,testCourse.getCourseEnrolledCount(),"Enrolled count should return to 0 after decrement.");
    }

    @Test
    void testCourseFullLogic(){
        testCourse.incrementEnrolledCourse();
        testCourse.incrementEnrolledCourse();

        boolean isFull= testCourse.getCourseEnrolledCount()>= testCourse.getCourseCapacity();
        assertTrue(isFull,"Course should be reported as full when enrolled count equals capacity.");
    }

    @Test
    void testCourseTypeConsistency(){
        assertEquals("Mandatory", testCourse.getCourseType(), "The course type should correctly identify as Mandatory.");
    }
    
}
