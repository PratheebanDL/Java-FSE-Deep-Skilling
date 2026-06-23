class Employee
{
    private int employeeId;
    private String employeeName;
    private String employeePosition;
    private double employeeSalary;

    public Employee(int employeeId,
                    String employeeName,
                    String employeePosition,
                    double employeeSalary)
    {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.employeePosition = employeePosition;
        this.employeeSalary = employeeSalary;
    }

    public int getEmployeeId()
    {
        return employeeId;
    }

    public String getEmployeeName()
    {
        return employeeName;
    }

    public String getEmployeePosition()
    {
        return employeePosition;
    }

    public double getEmployeeSalary()
    {
        return employeeSalary;
    }

    @Override
    public String toString()
    {
        return "Employee ID: " + employeeId
                + " | Name: " + employeeName
                + " | Position: " + employeePosition
                + " | Salary: " + employeeSalary;
    }
}

class EmployeeManager
{
    private Employee[] employeeList;
    private int employeeCount;

    public EmployeeManager(int capacity)
    {
        employeeList = new Employee[capacity];
        employeeCount = 0;
    }

    public void addEmployee(Employee employee)
    {
        if (employeeCount >= employeeList.length)
        {
            System.out.println(
                    "Employee cannot be added");
            return;
        }

        employeeList[employeeCount] = employee;
        employeeCount++;

        System.out.println(
                "Employee added : "
                + employee.getEmployeeId());
    }

    public Employee searchEmployee(int employeeId)
    {
        for (int index = 0;
             index < employeeCount;
             index++)
        {
            if (employeeList[index]
                    .getEmployeeId() == employeeId)
            {
                return employeeList[index];
            }
        }

        return null;
    }

    public void displayEmployees()
    {
        if (employeeCount == 0)
        {
            System.out.println(
                    "No employee records found");
            return;
        }

        for (int index = 0;
             index < employeeCount;
             index++)
        {
            System.out.println(
                    employeeList[index]);
        }
    }

    public void deleteEmployee(int employeeId)
    {
        int employeeIndex = -1;

        for (int index = 0;
             index < employeeCount;
             index++)
        {
            if (employeeList[index]
                    .getEmployeeId() == employeeId)
            {
                employeeIndex = index;
                break;
            }
        }

        if (employeeIndex == -1)
        {
            System.out.println(
                    "Employee not found : "
                    + employeeId);
            return;
        }

        for (int index = employeeIndex;
             index < employeeCount - 1;
             index++)
        {
            employeeList[index] =
                    employeeList[index + 1];
        }

        employeeList[employeeCount - 1] = null;
        employeeCount--;

        System.out.println(
                "Employee deleted successfully");
    }
}

public class Main
{
    public static void main(String[] args)
    {
        EmployeeManager employeeManager =
                new EmployeeManager(5);

        employeeManager.addEmployee(
                new Employee(
                        1,
                        "Mokesh",
                        "Senior Manager",
                        100000.00));

        employeeManager.addEmployee(
                new Employee(
                        2,
                        "Max",
                        "Assistant Manager",
                        80000.00));

        employeeManager.displayEmployees();

        Employee foundEmployee =
                employeeManager.searchEmployee(2);

        if (foundEmployee == null)
        {
            System.out.println(
                    "Employee not found");
        }
        else
        {
            System.out.println(
                    "Employee Found : "
                    + foundEmployee);
        }

        employeeManager.deleteEmployee(2);

        employeeManager.displayEmployees();
    }
}