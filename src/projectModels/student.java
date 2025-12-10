package projectModels;
import java.util.ArrayList;


public class Student{
    private String name;
    private String studentID;
    private String department;
    private int year;
    private ArrayList<Course> enrolledCourses;

//constructor
    public Student(String name,String studentID,String department,int year){
        this.name=name;
        this.studentID=studentID;
        this.department=department;
        this.year=year;
        this.enrolledCourses=new ArrayList<>();
    }
//getter setter
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name=name;
    }

    public String getStudentID(){
        return studentID;
    }
    public void setStudentID(String studentID){
        this.studentID=studentID;
    }

    public String getDepartment(){
        return department;
    }
    public void setDepartment(String department){
        this.department=department;
    }

    public int getYear(){
        return year;
    }
    public void setYear(int year){
        this.year=year;
    }

    public ArrayList<Course> getEnrolledCourses(){
        return enrolledCourses;
    }

    public boolean isEnrolled(Course course){
        return enrolledCourses.contains(course);
    }


}