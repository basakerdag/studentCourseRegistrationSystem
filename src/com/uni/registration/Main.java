package com.uni.registration;
import java.time.LocalTime;
import java.util.Scanner;
import com.uni.registration.projectServices.CourseCatalog;
import com.uni.registration.projectServices.InstructorManager;
import com.uni.registration.projectServices.StudentManager;
import com.uni.registration.projectModels.Courses.*;
import com.uni.registration.projectModels.Students.*;
import com.uni.registration.projectModels.Instructor;
import com.uni.registration.projectModels.Registration;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        InstructorManager instructorManager=new InstructorManager();
        CourseCatalog catalog = new CourseCatalog(instructorManager);
        StudentManager studentManager=new StudentManager();
        Registration.loadEnrollments(studentManager,catalog);
        Instructor loggedInInstructor = null; 
        Student loggedInStudent = null; 

        while (true) { 
            System.out.println("\n---Student Course Registration System---");
            System.out.println("1. Student Portal");
            System.out.println("2. Instructor Portal");
            System.out.println("3. Exit");
            System.out.println("Please select an option: ");

            int choice = input.nextInt();
            input.nextLine();

            if (choice == 3) {
                System.out.println("Existing system... Goodbye!");
                break; 
            }

            switch (choice) { 
                case 1:
                   System.out.println("---Student Portal---");
                   System.out.println("1.Login");
                   System.out.println("2.Register");
                   System.out.println("3.Back");
                   System.out.println("Please select an option: ");
                   int studentChoice = input.nextInt();
                   input.nextLine();
                   if (studentChoice==3){break;}
                   switch(studentChoice){
                    case 1:
                        System.out.println("---Student Login Page---");
                        System.out.println("Enter your student ID: ");
                        int studentID=input.nextInt();
                        input.nextLine();
                        System.out.println("Enter your password: ");
                        String password=input.nextLine();                    
                        loggedInStudent = studentManager.login(studentID, password);
                        if (loggedInStudent != null) {
                            System.out.println("Welcome  " + loggedInStudent.getName());
                        boolean testUser = true;
                        while(testUser){
                             System.out.println("---Student Dashboard---");
                        System.out.println("1.Browse Available Courses");
                        System.out.println("2.Enroll in a course");
                        System.out.println("3.View My Registered Courses");
                        System.out.println("4. Display Tuition Fee"); 
                        System.out.println("5. Change Password ");  
                        System.out.println("6. Drop course ");  
                        System.out.println("7. Logout");
                        int dashboardChoice=input.nextInt();
                        input.nextLine();
                        switch(dashboardChoice){
                            case 1:
                                catalog.displayCourses();
                                break;
                            case 2:
                                System.out.println("---Enroll in a course---");
                                System.out.println("Enter the course code you want to register for: ");
                                String registerCourse=input.nextLine();
                                Course selectedCourse=catalog.findCourseByCode(registerCourse);
                                if(selectedCourse==null){
                                    System.out.println("Error: Course not found in the catalog.");
                                }else{
                                    Registration registrationService=new Registration(loggedInStudent, selectedCourse);
                                    registrationService.registerCourse(loggedInStudent, selectedCourse);
                                    catalog.saveCoursesToCsv();
                                }
                                break;
                            case 3:
                                loggedInStudent.viewRegisteredCourses();
                                break;
                            case 4:
                                System.out.println("Display Tuition Fee");  
                                double fee=loggedInStudent.calculateTuition();
                                System.out.println("Student Type: " + loggedInStudent.getStudentType());
                                System.out.println("Number of Registered Courses: " + loggedInStudent.getRegisteredCourses().size());
                                System.out.println("Total Amount to Pay: " + fee + " TL");
                                break;
                            case 5:
                                System.out.println("Enter your current password: ");
                                String currentStudentPassword=input.nextLine();
                                System.out.println("Enter your new password: ");
                                String newStudentPassword=input.nextLine();
                                boolean isStudentChanged=loggedInStudent.changeStudentPassword(currentStudentPassword,newStudentPassword);
                                if(isStudentChanged){
                                    studentManager.saveAllStudentsToCsv();
                                    System.out.println("Your account security has been updated.");
                                }else{
                                    System.out.println("Password change failed. Please try again.");
                                }
                                break;
                            case 6:
                                System.out.println("---Your Registered Courses---");
                                if(loggedInStudent.getRegisteredCourses().isEmpty()){
                                    System.out.println("You have no registered courses.");
                                }else{
                                    for(Course c:loggedInStudent.getRegisteredCourses()){
                                        System.out.println("--["+c.getCourseCode()+"]--"+c.getCourseName());
                                    }
                                }
                                System.out.println("Which course do you want to drop?");
                                String courseDrop=input.nextLine();
                                Course courseToDrop=catalog.findCourseByCode(courseDrop);
                                if(courseToDrop!=null){
                                    Registration.dropCourse(loggedInStudent, courseToDrop);
                                }else{
                                    System.out.println("Course not found in your list");
                                }
                                 break;
                            case 7:
                                testUser=false;
                                break;                          
                          }
 
                          }
                        }else{
                            System.out.println("System cannot find the user.");
                        }
                       
                        break;
                    case 2:
                        System.out.println("---Student Register Page---");
                        System.out.println("Please give your information for register: ");
                        System.out.println("Enter your name: ");
                        String studentName=input.nextLine();
                        System.out.println("Enter your surname: ");
                        String studentSurname=input.nextLine();
                        System.out.println("Enter your student ID: ");
                        int studentID2=input.nextInt();
                        input.nextLine();
                        System.out.println("Enter your department: ");
                        String studentDepartment=input.nextLine();
                        System.out.println("Enter your class: ");
                        int studentYear=input.nextInt();
                        input.nextLine();
                        System.out.println("Enter your password: ");
                        String password2=input.nextLine();
                        System.out.println("Are you a graduate student or an undergraduate student? (1-Graduate Student , 2-Undergraduate Student)");
                        int newStudentChoice=input.nextInt();
                        input.nextLine();
                        Student newStudent=null;
                        if(newStudentChoice==1){
                            newStudent=new GraduateStudent(studentName, studentSurname, studentID2, studentDepartment, studentYear, password2);
                        }else if(newStudentChoice==2){
                            newStudent=new UndergraduateStudent(studentName, studentSurname, studentID2, studentDepartment, studentYear, password2);
                        }

                        if(newStudent!=null){
                            studentManager.addStudent(newStudent);
                        }
                        break;
                    default:
                        System.out.println("Invalid selection.");
                   }
            
                    break;
                case 2:
                   System.out.println("---Instructor Portal---");
                   System.out.println("1.Login");
                   System.out.println("2.Register");
                   System.out.println("3.Back");
                   System.out.println("Please select an option: ");
                   int instructorChoice=input.nextInt();
                   input.nextLine();
                   switch(instructorChoice){
                    case 1:
                        System.out.println("---Instructor Login Page---");
                        System.out.print("Enter ID: ");
                        int id = input.nextInt();
                        input.nextLine();
                        System.out.print("Enter Password: ");
                        String password = input.nextLine();
                        loggedInInstructor = instructorManager.login(id, password);
                        if(loggedInInstructor!=null){
                        System.out.println("Welcome "+ loggedInInstructor.getInstructorName());
                        Boolean testInstructor=true;
                        while(testInstructor){
                            System.out.println("---Instructor Login Page");
                        System.out.println("1. View all courses");
                        System.out.println("2. Add new course");
                        System.out.println("3. Change Password");
                        System.out.println("4. Drop courses ");
                        System.out.println("5. Logout");
                        System.out.println("Please select an option.");
                        int instructorOp=input.nextInt();
                        input.nextLine();
                        switch(instructorOp){
                            case 1:
                                System.out.println("");
                                catalog.displayCourses();
                                break;
                            case 2:
                                System.out.println("Please provide information about the course you want to add.");
                                System.out.println("Enter course name: ");
                                String courseName=input.nextLine();
                                System.out.println("Enter course code: ");
                                String courseCode=input.nextLine();
                                System.out.println("Enter course credit: ");
                                int courseCredit=input.nextInt();
                                input.nextLine();
                                System.out.println("What type of course do you want to add (1-Mandatory , 2-Elective)?");
                                int addCourseOption=input.nextInt();
                                input.nextLine();
                                System.out.println("Enter course capacity: ");
                                int courseCapacity=input.nextInt();
                                input.nextLine();
                                int initialEnrolledCount = 0;
                                System.out.println("Enter course day: ");
                                String courseDay=input.nextLine();
                                System.out.println("Enter course start time: ");
                                LocalTime courseStartHour=LocalTime.parse(input.nextLine());
                                System.out.println("Enter course end time: ");
                                LocalTime courseEndHour=LocalTime.parse(input.nextLine());
                                Course newCourse=null;
                                if (addCourseOption==1){
                                      newCourse=new MandatoryCourse(courseName, courseCode, courseCredit, loggedInInstructor,courseCapacity,initialEnrolledCount,courseDay,courseStartHour,courseEndHour);
                                } else if(addCourseOption==2){
                                     newCourse=new ElectiveCourse(courseName, courseCode, courseCredit, loggedInInstructor,courseCapacity,initialEnrolledCount,courseDay,courseStartHour,courseEndHour);
                                }
                                if(newCourse!=null){
                                    catalog.addCourse(newCourse);
                                }
                                break;
                            case 3:
                                System.out.println("Enter your current password: ");
                                String currentInstructorPassword=input.nextLine();
                                System.out.println("Enter your new password: ");
                                String newInstructorPassword=input.nextLine();
                                boolean isInstructorChanged=loggedInInstructor.changeInstructorPassword(currentInstructorPassword,newInstructorPassword);
                                if(isInstructorChanged){
                                    instructorManager.saveAllInstructorsToCsv();
                                    System.out.println("Your account security has been updated.");
                                }else{
                                    System.out.println("Password change failed. Please try again.");
                                }
                                break;
                            case 4: 
                                System.out.println("\n--- Your Active Courses ---");
                                boolean hasCourse = false;    
                                for (Course c : catalog.getAllCourses()) {
                                  if (c.getInstructor().getInstructorID() == loggedInInstructor.getInstructorID()) {
                                     System.out.println("- [" + c.getCourseCode() + "] " + c.getCourseName());
                                     hasCourse = true;
                                         }
                                }
                                if (!hasCourse) {
                                   System.out.println("You don't have any courses to delete.");
                                } else {
                                   System.out.print("\nEnter the Course Code to delete: ");
                                   String code = input.nextLine();
                                   Course courseToDelete = catalog.findCourseByCode(code);
                                  if (courseToDelete != null && courseToDelete.getInstructor().getInstructorID() == loggedInInstructor.getInstructorID()) { 
                                      catalog.removeCourseFromCatalog(courseToDelete);
                                      studentManager.removeCourseFromAllStudents(code);
                                      System.out.println("The course and all its enrollments have been cleared.");
                                 } else {
                                      System.out.println("Error: Course not found or you are not authorized to delete it.");
                                    }
                                }
                                break;                             
                            case 5:
                                testInstructor=false;
                          }
                         }
                        
                        }else{
                            System.out.println("System cannot find the user.");
                        }
                        break;
                    case 2:

                        System.out.println("---Instructor Register Page---");
                        System.out.println("Enter your name: ");
                        String instructorName=input.nextLine();
                        System.out.println("Enter your surname: ");
                        String instructorSurname=input.nextLine();
                        System.out.println("Enter your ID: ");
                        int instructorID=input.nextInt();
                        input.nextLine();
                        System.out.println("Enter your department: ");
                        String instructorDepartment=input.nextLine();
                        System.out.println("Enter your password: ");
                        String instructorPassword=input.nextLine();
                        Instructor newInstructor=new Instructor(instructorName, instructorSurname, instructorID, instructorDepartment, instructorPassword);
                        if(newInstructor!=null){
                            instructorManager.addInstructor(newInstructor);
                        }
                        break;
                    default:
                        System.out.println("Invalid selection.");
                   }
                    break;
                    case 3:
                        break;
                default:
                    System.out.println("Invalid selection.");
            }
        } 
    } 
} 