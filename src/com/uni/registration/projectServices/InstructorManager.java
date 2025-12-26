package com.uni.registration.projectServices;

import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import com.uni.registration.projectModels.Instructor;

public class InstructorManager {
    private List<Instructor> instructors;
    private final String instructorFile = "src/data/instructors.csv";

    public InstructorManager() {
        this.instructors = new ArrayList<>();
        loadFromInstructorCsv();
    }

    public void loadFromInstructorCsv() {
        try (BufferedReader br = new BufferedReader(new FileReader(instructorFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length < 5) continue;
                
                String instructorName = data[0];
                String instructorSurname = data[1];
                int instructorID = Integer.parseInt(data[2]);
                String instructorDepartment = data[3];
                String instructorPassword = data[4];

                Instructor newInstructor = new Instructor(instructorName, instructorSurname, instructorID, instructorDepartment, instructorPassword);
                instructors.add(newInstructor);
            }
        } catch (IOException e) {
            System.out.println("No existing instructor data found or file is empty.");
        }
    } 

    public void saveAllInstructorsToCsv(){
        try(PrintWriter pw=new PrintWriter(new FileWriter(instructorFile,false))){
            for(Instructor inst:instructors){
                String csvLine= String.format("%s,%s,%s,%s,%s",
                inst.getInstructorName(),
                inst.getInstructorSurname(),
                inst.getInstructorID(),
                inst.getInstructorDepartment(),
                inst.getPassword()
                );
                pw.println(csvLine);
            }
        }catch(IOException e){
            System.err.println(" Error: Could not update instructors file: " + e.getMessage());
        }
    }

    public void addInstructor(Instructor instructor) {
        instructors.add(instructor); 
        try (PrintWriter pw = new PrintWriter(new FileWriter(instructorFile, true))) {
            String csvLine = String.format("%s,%s,%s,%s,%s",
                instructor.getInstructorName(),
                instructor.getInstructorSurname(),
                instructor.getInstructorID(),
                instructor.getInstructorDepartment(),
                instructor.getPassword()
            );
            pw.println(csvLine);
            System.out.println("Instructor successfully saved to CSV.");
        } catch (IOException e) {
            System.err.println("Error saving instructor: " + e.getMessage());
        }
    } 

    public Instructor findInstructorByID(int ID){
        for(Instructor inst:instructors){
            if(inst.getInstructorID() == ID){
                return inst;
            }
        }
        return null;
    }

    public Instructor login(int instructorID,String password){
        for(Instructor inst:instructors){
            if(inst.getInstructorID()==instructorID && inst.getPassword().equals(password)){
                return inst;
            }
        }
        return null;
    }

} 