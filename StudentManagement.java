import java.io.*;
import java.util.*;

// Student Class
class Student implements Serializable {
    private String name;
    private int rollNo;
    private double marks;

    public Student(String name, int rollNo, double marks) {
        this.name = name;
        this.rollNo = rollNo;
        this.marks = marks;
    }

    public int getRollNo() {
        return rollNo;
    }

    public void setMarks(double marks) {
        this.marks = marks;
    }

    public String toString() {
        return "Roll No: " + rollNo + ", Name: " + name + ", Marks: " + marks;
    }
}

// Main System Class
public class StudentManagement {
    private static ArrayList<Student> studentList = new ArrayList<>();
    private static final String FILE_NAME = "students.dat";

    // Save students to file
    private static void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(studentList);
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }

    // Load students from file
    private static void loadFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            studentList = (ArrayList<Student>) ois.readObject();
        } catch (FileNotFoundException e) {
            // Ignore if file not found (first run)
        } catch (Exception e) {
            System.out.println("Error loading file: " + e.getMessage());
        }
    }

    // Add Student
    private static void addStudent(Scanner sc) {
        System.out.print("Enter Roll No: ");
        int roll = sc.nextInt();
        sc.nextLine(); // consume newline
        System.out.print("Enter Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Marks: ");
        double marks = sc.nextDouble();

        studentList.add(new Student(name, roll, marks));
        saveToFile();
        System.out.println("✅ Student Added Successfully!");
    }

    // Update Student
    private static void updateStudent(Scanner sc) {
        System.out.print("Enter Roll No to Update: ");
        int roll = sc.nextInt();
        for (Student s : studentList) {
            if (s.getRollNo() == roll) {
                System.out.print("Enter New Marks: ");
                double marks = sc.nextDouble();
                s.setMarks(marks);
                saveToFile();
                System.out.println(" Student Updated!");
                return;
            }
        }
        System.out.println(" Student Not Found!");
    }

    // Delete Student
    private static void deleteStudent(Scanner sc) {
        System.out.print("Enter Roll No to Delete: ");
        int roll = sc.nextInt();
        studentList.removeIf(s -> s.getRollNo() == roll);
        saveToFile();
        System.out.println("Student Deleted!");
    }

    // Search Student
    private static void searchStudent(Scanner sc) {
        System.out.print("Enter Roll No to Search: ");
        int roll = sc.nextInt();
        for (Student s : studentList) {
            if (s.getRollNo() == roll) {
                System.out.println(s);
                return;
            }
        }
        System.out.println("Student Not Found!");
    }

    // Display All Students
    private static void displayAllStudents() {
        if (studentList.isEmpty()) {
            System.out.println("No Students Available!");
        } else {
            for (Student s : studentList) {
                System.out.println(s);
            }
        }
    }

    // Main Menu
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        loadFromFile();
        while (true) {
            System.out.println("\n===== Student Management System =====");
            System.out.println("1. Add Student");
            System.out.println("2. Update Student");
            System.out.println("3. Delete Student");
            System.out.println("4. Search Student");
            System.out.println("5. Display All Students");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    addStudent(sc);
                    break;
                case 2:
                    updateStudent(sc);
                    break;
                case 3:
                    deleteStudent(sc);
                    break;
                case 4:
                    searchStudent(sc);
                    break;
                case 5:
                    displayAllStudents();
                    break;
                case 6:
                    System.out.println(" Exiting... Data Saved!");
                    saveToFile();
                    return;
                default:
                    System.out.println(" Invalid Choice!");
            }
        }
    }
}
