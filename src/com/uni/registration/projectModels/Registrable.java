package com.uni.registration.projectModels;
import com.uni.registration.projectModels.Courses.Course;

/**
 * Interface defining the essential registration operations for students.
 * Provides a contract for managing course enrollments and schedule validations.
 */

public interface Registrable{

    /**
     * Registers the entity for a specific course.
     * @param course The course to be added.
     */
    void registerCourse(Course course);

    /**
     * Removes the entity from a specific course registration.
     * @param course The course to be removed.
     */
    void unregisterCourse(Course course);

    /**
     * Displays all currently registered courses.
     */
    void viewRegisteredCourses();

    /**
     * Checks if a given course overlaps with the existing schedule.
     * @param course The course to check for conflicts.
     * @return true if there is a conflict, false otherwise.
     */
    boolean hasScheduleConflict(Course course);

    /**
     * Verifies if the entity is already registered for a course code.
     * @param courseCode The unique code of the course.
     * @return true if registered, false otherwise.
     */
    boolean isRegistered(String courseCode);
}