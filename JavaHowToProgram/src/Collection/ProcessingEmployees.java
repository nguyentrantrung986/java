package Collection;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ProcessingEmployees {
	public static void main(String[] args) {
		// initialize array of Employees
		Employee[] employees = { new Employee("Jason", "Red", 5000, "IT"), new Employee("Ashley", "Green", 7600, "IT"),
				new Employee("Matthew", "Indigo", 3587.5, "Sales"),
				new Employee("James", "Indigo", 4700.77, "Marketing"), new Employee("Luke", "Indigo", 6200, "IT"),
				new Employee("Jason", "Blue", 3200, "Sales"), new Employee("Wendy", "Brown", 4236.4, "Marketing") };

		// get List view of the Employees
		List<Employee> list = Arrays.asList(employees);
		
		//print employees within the salary range
		Predicate<Employee> salaryRange = (Employee e) -> {
			return (e.getSalary() >= 3000 && e.getSalary() <= 5000);
		};
		Stream<Employee> se = list.stream().filter(salaryRange);
		Function<Employee, Double> getSalary = e -> e.getSalary();

		System.out.println("First employee in the salary range $3000 to $5000: " + se.findFirst().get());

		System.out.println("All employees in the salary range $3000 to $5000: ");
		list.stream().filter(salaryRange).sorted(Comparator.comparing(getSalary)).forEach(e -> System.out.println(e));

		System.out.println();
		
		//grouping employees by department
		list.stream().collect(Collectors.groupingBy(Employee::getDepartment)).forEach(
				(String dep, List<Employee> le)->{
					System.out.println("Department "+dep);
					le.stream().forEach(e->System.out.println(e));
				});
		
		//counting employees per department
		list.stream().collect(Collectors.groupingBy(Employee::getDepartment, Collectors.counting())).forEach(
				(String dep, Long l)->{
					System.out.printf("%nDepartment %s has %d employees",dep, l);
				});
		
		//Summing and averaging employee salary
		System.out.printf("%nThe sum of salary: %.2f" ,list.stream().mapToDouble((Employee e)->e.getSalary()).sum());
		System.out.printf("%nThe average of salary: %.2f",list.stream().mapToDouble(Employee::getSalary).average().getAsDouble());
	}
}
