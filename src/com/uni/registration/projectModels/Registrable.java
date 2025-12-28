package com.uni.registration.projectModels;

import com.uni.registration.projectModels.Courses.Course;

public interface Registrable{
    void registerCourse(Course course);
    void unregisterCourse(Course course);
    void viewRegisteredCourses();
}