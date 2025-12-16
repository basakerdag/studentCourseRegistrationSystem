package projectModels;

public class Instructor{
    private String instructorName;
    private String instructorID;
    private String instructorDepartment;

    public Instructor(String instructorName,String instructorID,String instructorDepartment){
        this.instructorName=instructorName;
        this.instructorID=instructorID;
        this.instructorDepartment=instructorDepartment;
    }

    public String getInstructorName(){
        return instructorName;
    }
    public void setInstructorName(String instructorName){
        this.instructorName=instructorName;
    }

    public String getInstructorID(){
        return instructorID;
    }
    public void setInstructorID(String instructorID){
        this.instructorID=instructorID;
    }

    public String getInstructorDepartment(){
        return instructorDepartment;
    }
    public void setInstructorDepartment(String instructorDepartment){
        this.instructorDepartment=instructorDepartment;
    }

}