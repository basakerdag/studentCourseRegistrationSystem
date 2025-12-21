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

        while (true) { // While artık main'in içinde
            System.out.println("\n---Student Course Registration System---");
            System.out.println("1. Student Portal");
            System.out.println("2. Instructor Portal");
            System.out.println("3. Exit");
            System.out.print("Please select an option: ");

            int choice = input.nextInt();
            input.nextLine();

            if (choice == 3) {
                System.out.println("Exiting system... Goodbye!");
                break; 
            }

            switch (choice) { 
                case 1:
                   //student
                    break;
                case 2:
                   //instructor
                    break;
                default:
                    System.out.println("Invalid selection.");
            }
        } 
    } 
} 