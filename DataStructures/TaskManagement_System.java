class Task
{
    private int taskId;
    private String taskName;
    private String taskStatus;

    public Task(int taskId, String taskName, String taskStatus)
    {
        this.taskId = taskId;
        this.taskName = taskName;
        this.taskStatus = taskStatus;
    }

    public int getTaskId()
    {
        return taskId;
    }

    @Override
    public String toString()
    {
        return "Task ID: " + taskId +
               " | Name: " + taskName +
               " | Status: " + taskStatus;
    }
}

class TaskNode
{
    Task task;
    TaskNode nextNode;

    public TaskNode(Task task)
    {
        this.task = task;
        this.nextNode = null;
    }
}

class TaskLinkedList
{
    private TaskNode headNode;

    public void addTask(Task task)
    {
        TaskNode newNode = new TaskNode(task);

        if (headNode == null)
        {
            headNode = newNode;
        }
        else
        {
            TaskNode currentNode = headNode;

            while (currentNode.nextNode != null)
            {
                currentNode = currentNode.nextNode;
            }

            currentNode.nextNode = newNode;
        }

        System.out.println("Task added successfully: "
                           + task.getTaskId());
    }

    public Task searchTask(int taskId)
    {
        TaskNode currentNode = headNode;

        while (currentNode != null)
        {
            if (currentNode.task.getTaskId() == taskId)
            {
                return currentNode.task;
            }

            currentNode = currentNode.nextNode;
        }

        return null;
    }

    public void displayTasks()
    {
        System.out.println("\n--- CURRENT TASK LIST ---");

        if (headNode == null)
        {
            System.out.println("No tasks available.");
            return;
        }

        TaskNode currentNode = headNode;

        while (currentNode != null)
        {
            System.out.println(currentNode.task);
            currentNode = currentNode.nextNode;
        }

        System.out.println("-------------------------");
    }

    public void deleteTask(int taskId)
    {
        if (headNode == null)
        {
            System.out.println("Task list is empty.");
            return;
        }

        if (headNode.task.getTaskId() == taskId)
        {
            headNode = headNode.nextNode;
            System.out.println("Task ID " + taskId
                               + " deleted successfully.");
            return;
        }

        TaskNode currentNode = headNode;
        TaskNode previousNode = null;

        while (currentNode != null
               && currentNode.task.getTaskId() != taskId)
        {
            previousNode = currentNode;
            currentNode = currentNode.nextNode;
        }

        if (currentNode == null)
        {
            System.out.println("Task ID "
                               + taskId
                               + " not found.");
            return;
        }

        previousNode.nextNode = currentNode.nextNode;

        System.out.println("Task ID "
                           + taskId
                           + " deleted successfully.");
    }
}

public class Main
{
    public static void main(String[] args)
    {
        TaskLinkedList taskLinkedList =
                new TaskLinkedList();

        taskLinkedList.addTask(
                new Task(101,
                         "Database Migration",
                         "Pending"));

        taskLinkedList.addTask(
                new Task(102,
                         "API Implementation",
                         "In Progress"));

        taskLinkedList.addTask(
                new Task(103,
                         "Unit Testing",
                         "Pending"));

        taskLinkedList.displayTasks();

        Task foundTask =
                taskLinkedList.searchTask(102);

        if (foundTask != null)
        {
            System.out.println(
                    "\nTask Found -> " + foundTask);
        }
        else
        {
            System.out.println(
                    "\nTask not found.");
        }

        System.out.println();

        taskLinkedList.deleteTask(102);

        taskLinkedList.displayTasks();
    }
}