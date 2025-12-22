package com.uni.registration;
import java.util.ArrayList;
import java.util.Scanner;
import com.uni.registration.projectServices.CourseCatalog;
import com.uni.registration.projectServices.InstructorManager;
import com.uni.registration.projectServices.StudentManager;
import com.uni.registration.projectModels.Courses.*;
import com.uni.registration.projectModels.Students.*;
import com.uni.registration.projectModels.Instructor;
import com.uni.registration.projectModels.Instructor.*;
import com.uni.registration.projectServices.StudentManager;
import com.uni.registration.projectServices.InstructorManager;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        CourseCatalog catalog = new CourseCatalog();
        StudentManager studentmanager=new StudentManager();
        InstructorManager instructorManager=new InstructorManager();
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
                        int password=input.nextInt();
                        input.nextLine();
                        //loggedInStudent=new Student("Name","Surname","");
                        //daha sonra if else yapabilirim buraya
                        System.out.println("---Student Dashboard---");
                        System.out.println("1.Browse Available Courses");
                        System.out.println("2.Enroll in a course");
                        System.out.println("3.View My Registered Courses");
                        System.out.println("4. Display Tuition Fee");     
                        System.out.println("5. Logout");
                        int dashboardChoice=input.nextInt();
                        input.nextLine();
                        if(dashboardChoice==5){break;}
                        switch(dashboardChoice){
                            case 1:
                                catalog.displayCourses();
                                break;
                            case 2:
                                System.out.println("Enter the course code you want to register for: ");
                                String registerCourse=input.nextLine();
                                Course course=catalog.findCourseByCode(registerCourse);
                                loggedInStudent.getRegisteredCourses().add(course);
                                break;
                            case 3:
                                System.out.println("Your registered course : ");
                                ArrayList<Course> registered=loggedInStudent.getRegisteredCourses();
                                if(registered.isEmpty()){
                                    System.out.println("You haven't registered for any courses yet.");
                                }else{
                                  for(Course c:registered){
                                    System.out.println("- " + c.getCourseCode() + ": " + c.getCourseName());
                                  }
                                }
                                break;
                            case 4:
                                System.out.println("Display Tuition Fee");  
                                double fee=loggedInStudent.calculateTuition();
                                System.out.println("Student Type: " + loggedInStudent.getStudentType());
                                System.out.println("Number of Registered Courses: " + loggedInStudent.getRegisteredCourses().size());
                                System.out.println("Total Amount to Pay: " + fee + " TL");
                                break;
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
                            studentmanager.addStudent(newStudent);
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
                   if(instructorChoice==3){ break;}
                   switch(instructorChoice){
                    case 1:
                        System.out.println("---Instructor Login Page---");
                        System.out.print("Enter ID: ");
                        String id = input.nextLine();
                        System.out.print("Enter Password: ");
                        String password = input.nextLine();
                        loggedInInstructor = new Instructor("Name", "Surname", id,"Department","Password");

                        System.out.println("---Instructor Login Page");
                        System.out.println("1.View all courses");
                        System.out.println("2.Add new course");
                        System.out.println("3.Logout");
                        System.out.println("Please select an option.");
                        int instructorOp=input.nextInt();
                        input.nextLine();
                        if (instructorOp==3){break;}
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
                                Course newCourse=null;
                                if (addCourseOption==1){
                                      newCourse=new MandatoryCourse(courseName, courseCode, courseCredit, loggedInInstructor);
                                } else if(addCourseOption==2){
                                     newCourse=new ElectiveCourse(courseName, courseCode, courseCredit, loggedInInstructor);
                                }
                                if(newCourse!=null){
                                    catalog.addCourse(newCourse);
                                }
                        }
                        break;
                    case 2:

                        System.out.println("---Instructor Register Page---");
                        System.out.println("Enter your name: ");
                        String instructorName=input.nextLine();
                        System.out.println("Enter your surname: ");
                        String instructorSurname=input.nextLine();
                        System.out.println("Enter your ID: ");
                        String instructorID=input.nextLine();
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
                default:
                    System.out.println("Invalid selection.");
            }
        } 
    } 
} 