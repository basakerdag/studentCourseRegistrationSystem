package com.uni.registration;
import java.util.Scanner;
import com.uni.registration.projectServices.CourseCatalog;
import com.uni.registration.projectServices.CourseCatalog.*;
import com.uni.registration.projectModels.Students.*;
import com.uni.registration.projectModels.Instructor.*;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        CourseCatalog catalog = new CourseCatalog();

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
                   if (studentChoice==3){
                    break;
                   }
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
                   if(instructorChoice==3){
                    break;
                   }
                   switch(instructorChoice){
                    case 1:
                        System.out.println("---Instructor Login Page");
                        //add something
                        break;
                    case 2:
                        System.out.println("---Instructor Register Page---");
                        //add something
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