import java.util.*;

public class StudentGrades {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Double> grades = new ArrayList<>();
        
        System.out.println("Enter student grades (type -1 to stop):");
        while (true) {
            try {
                double grade = scanner.nextDouble();
                if (grade == -1) break;
                if (grade < 0 || grade > 100) {
                    System.out.println("Invalid grade. Enter a value between 0 and 100.");
                    continue;
                }
                grades.add(grade);
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a numeric value.");
                scanner.next(); // Clear the invalid input
            }
        }
        
        if (grades.isEmpty()) {
            System.out.println("No grades entered.");
        } else {
            double total = 0;
            double highest = grades.get(0);
            double lowest = grades.get(0);
            
            for (double grade : grades) {
                total += grade;
                if (grade > highest) highest = grade;
                if (grade < lowest) lowest = grade;
            }
            
            double average = total / grades.size();
            
            System.out.println("\nSummary of Grades:");
            System.out.println("---------------------");
            System.out.println("Total Students: " + grades.size());
            System.out.println("Average Score: " + average);
            System.out.println("Highest Score: " + highest);
            System.out.println("Lowest Score: " + lowest);
            System.out.println("Grades Entered: " + grades);
        }
        
        scanner.close();
    }
}

