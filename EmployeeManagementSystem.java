import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

class Employee {
    private static int nextEmpId = 1;
    private int empId;
    private String name;
    private LocalDate dateOfJoining;
    private String phoneNumber;
    private String aadhaarNumber;

    public Employee(String name, LocalDate dateOfJoining, String phoneNumber, String aadhaarNumber) {
        this.empId = nextEmpId++;
        this.name = name;
        this.dateOfJoining = dateOfJoining;
        this.phoneNumber = phoneNumber;
        this.aadhaarNumber = aadhaarNumber;
    }

    public int getEmpId() {
        return empId;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDateOfJoining() {
        return dateOfJoining;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAadhaarNumber() {
        return aadhaarNumber;
    }
}

class FTE extends Employee {
    private double monthlySalary;

    public FTE(String name, LocalDate dateOfJoining, String phoneNumber, String aadhaarNumber, double monthlySalary) {
        super(name, dateOfJoining, phoneNumber, aadhaarNumber);
        this.monthlySalary = monthlySalary;
    }

    public double getMonthlySalary() {
        return monthlySalary;
    }
}

class PTE extends Employee {
    private double hourlyPayment;

    public PTE(String name, LocalDate dateOfJoining, String phoneNumber, String aadhaarNumber, double hourlyPayment) {
        super(name, dateOfJoining, phoneNumber, aadhaarNumber);
        this.hourlyPayment = hourlyPayment;
    }

    public double getHourlyPayment() {
        return hourlyPayment;
    }
}

class EmployeeManagementSystem {
    private List<Employee> employees = new ArrayList<>();

    public void addFTE(String name, LocalDate dateOfJoining, String phoneNumber, String aadhaarNumber,
            double monthlySalary) {
        FTE fte = new FTE(name, dateOfJoining, phoneNumber, aadhaarNumber, monthlySalary);
        validateAndAddEmployee(fte);
    }

    public void addPTE(String name, LocalDate dateOfJoining, String phoneNumber, String aadhaarNumber,
            double hourlyPayment) {
        PTE pte = new PTE(name, dateOfJoining, phoneNumber, aadhaarNumber, hourlyPayment);
        validateAndAddEmployee(pte);
    }

    public void deleteEmployee(int empId) {
        employees.removeIf(e -> e.getEmpId() == empId);
    }

    public Employee searchEmployee(String aadhaarNumber) {
        return employees.stream()
                .filter(e -> e.getAadhaarNumber().equals(aadhaarNumber))
                .findFirst()
                .orElse(null);
    }

    public void displayAllEmployees() {
        employees.forEach(this::displayEmployeeDetails);
    }

    public void displayAllEmployeesSortedByDateOfJoining() {
        employees.stream()
                .sorted(Comparator.comparing(Employee::getDateOfJoining))
                .forEach(this::displayEmployeeDetails);
    }

    private void validateAndAddEmployee(Employee employee) {
        validateDateOfJoining(employee.getDateOfJoining());
        validatePhoneNumber(employee.getPhoneNumber());
        validateAadhaarNumber(employee.getAadhaarNumber());

        // Ensure Aadhaar number is unique
        if (employees.stream().anyMatch(e -> e.getAadhaarNumber().equals(employee.getAadhaarNumber()))) {
            throw new RuntimeException("Aadhaar number must be unique");
        }

        employees.add(employee);
    }

    private void validateDateOfJoining(LocalDate dateOfJoining) {
        // Custom validation logic for dateOfJoining
        // You can add more validation if needed
        if (dateOfJoining.isAfter(LocalDate.now())) {
            throw new RuntimeException("Date of joining cannot be in the future");
        }
    }

    private void validatePhoneNumber(String phoneNumber) {
        // Custom validation logic for phoneNumber
        // You can add more validation if needed
        if (!phoneNumber.matches("[0-9]+")
                || !(phoneNumber.length() == 10 || phoneNumber.length() == 12 || phoneNumber.length() == 13)) {
            throw new RuntimeException("Invalid phone number");
        }
    }

    private void validateAadhaarNumber(String aadhaarNumber) {
        // Custom validation logic for aadhaarNumber
        // You can add more validation if needed
        if (!aadhaarNumber.matches("[0-9]+") || aadhaarNumber.length() != 12) {
            throw new RuntimeException("Invalid Aadhaar number");
        }
    }

    private void displayEmployeeDetails(Employee employee) {
        System.out.println("Employee ID: " + employee.getEmpId());
        System.out.println("Name: " + employee.getName());
        System.out.println("Date of Joining: " + employee.getDateOfJoining());
        System.out.println("Phone Number: " + employee.getPhoneNumber());
        System.out.println("Aadhaar Number: " + employee.getAadhaarNumber());

        if (employee instanceof FTE) {
            System.out.println("Type: Full Time Employee");
            System.out.println("Monthly Salary: " + ((FTE) employee).getMonthlySalary());
        } else if (employee instanceof PTE) {
            System.out.println("Type: Part Time Employee");
            System.out.println("Hourly Payment: " + ((PTE) employee).getHourlyPayment());
        }

        System.out.println("-----------------------");
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            EmployeeManagementSystem employeeManagementSystem = new EmployeeManagementSystem();

            while (true) {
                System.out.println("Application Menu:");
                System.out.println("a. Add full time employee");
                System.out.println("b. Add part time employee");
                System.out.println("c. Delete an employee by Emp Id");
                System.out.println("d. Search employee details by Aadhaar number");
                System.out.println("e. Display all employee details");
                System.out.println("f. Display all employee details sorted by date of joining");
                System.out.println("g. Exit");

                System.out.print("Enter your choice: ");
                String choice = scanner.nextLine();

                switch (choice.toLowerCase()) {
                    // Inside the main method
                    case "a":
                        System.out.print("Enter name: ");
                        String nameA = scanner.nextLine();
                        System.out.print("Enter date of joining (format: dd-MMM-yyyy): ");
                        DateTimeFormatter formatterA = DateTimeFormatter.ofPattern("dd-MMM-yyyy", Locale.ENGLISH);
                        LocalDate dojA = LocalDate.parse(scanner.nextLine(), formatterA);
                        System.out.print("Enter phone number: ");
                        String phoneNumberA = scanner.nextLine();
                        System.out.print("Enter Aadhaar number: ");
                        String aadhaarNumberA = scanner.nextLine();
                        System.out.print("Enter monthly salary: ");
                        double monthlySalaryA = Double.parseDouble(scanner.nextLine());
                        employeeManagementSystem.addFTE(nameA, dojA, phoneNumberA, aadhaarNumberA, monthlySalaryA);
                        break;

                    case "b":
                        System.out.print("Enter name: ");
                        String nameB = scanner.nextLine();
                        System.out.print("Enter date of joining (format: dd-MMM-yyyy): ");
                        LocalDate dojB = LocalDate.parse(scanner.nextLine());
                        System.out.print("Enter phone number: ");
                        String phoneNumberB = scanner.nextLine();
                        System.out.print("Enter Aadhaar number: ");
                        String aadhaarNumberB = scanner.nextLine();
                        System.out.print("Enter hourly payment: ");
                        double hourlyPaymentB = Double.parseDouble(scanner.nextLine());
                        employeeManagementSystem.addPTE(nameB, dojB, phoneNumberB, aadhaarNumberB, hourlyPaymentB);
                        break;

                    case "c":
                        System.out.print("Enter Emp Id to delete: ");
                        int empIdC = Integer.parseInt(scanner.nextLine());
                        employeeManagementSystem.deleteEmployee(empIdC);
                        System.out.println("Employee deleted successfully");
                        break;

                    case "d":
                        System.out.print("Enter Aadhaar number to search: ");
                        String aadhaarNumberD = scanner.nextLine();
                        Employee searchedEmployee = employeeManagementSystem.searchEmployee(aadhaarNumberD);
                        if (searchedEmployee != null) {
                            System.out.println("Employee found:");
                            employeeManagementSystem.displayEmployeeDetails(searchedEmployee);
                        } else {
                            System.out.println("Employee not found");
                        }
                        break;

                    case "e":
                        employeeManagementSystem.displayAllEmployees();
                        break;

                    case "f":
                        employeeManagementSystem.displayAllEmployeesSortedByDateOfJoining();
                        break;

                    case "g":
                        System.out.println("Exiting application. Goodbye!");
                        System.exit(0);

                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
