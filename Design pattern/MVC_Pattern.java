class StudentModel
{
    private String studentId;
    private String studentName;

    public String getStudentId()
    {
        return studentId;
    }

    public void setStudentId(String studentId)
    {
        this.studentId = studentId;
    }

    public String getStudentName()
    {
        return studentName;
    }

    public void setStudentName(String studentName)
    {
        this.studentName = studentName;
    }
}

class StudentView
{
    public void displayStudent(String studentName, String studentId)
    {
        System.out.println("===== STUDENT DETAILS =====");
        System.out.println("Student ID   : " + studentId);
        System.out.println("Student Name : " + studentName);
        System.out.println("===========================");
    }
}

class StudentController
{
    private StudentModel studentModel;
    private StudentView studentView;

    public StudentController(StudentModel studentModel,
                             StudentView studentView)
    {
        this.studentModel = studentModel;
        this.studentView = studentView;
    }

    public void setStudentName(String studentName)
    {
        studentModel.setStudentName(studentName);
    }

    public String getStudentName()
    {
        return studentModel.getStudentName();
    }

    public void setStudentId(String studentId)
    {
        studentModel.setStudentId(studentId);
    }

    public String getStudentId()
    {
        return studentModel.getStudentId();
    }

    public void updateView()
    {
        studentView.displayStudent(
            studentModel.getStudentName(),
            studentModel.getStudentId()
        );
    }
}

public class Main
{
    public static void main(String[] args)
    {
        StudentModel studentModel = new StudentModel();

        studentModel.setStudentName("John Doe");
        studentModel.setStudentId("STU-98765");

        StudentView studentView = new StudentView();

        StudentController studentController =
            new StudentController(studentModel, studentView);

        studentController.updateView();

        studentController.setStudentName("Jane Smith");

        studentController.updateView();
    }
}