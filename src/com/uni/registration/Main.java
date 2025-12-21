package com.uni.registration;
import java.util.Scanner;
import com.uni.registration.projectServices.CourseCatalog;
import com.uni.registration.projectModels.Courses.*;
import com.uni.registration.projectModels.Students.*;
import com.uni.registration.projectModels.Instructor;
import com.uni.registration.projectModels.Courses.ElectiveCourse;
import com.uni.registration.projectModels.Courses.MandatoryCourse;
import com.uni.registration.projectModels.Instructor.*;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        CourseCatalog catalog = new CourseCatalog();
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
                    //add something
                        break;
                    case 2:
                        System.out.println("---Student Register Page---");
                        //add something
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
                                System.out.println("What type of course do you want to add (1-Mandatory , 2-Elective)?");
                                int addCourseOption=input.nextInt();
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