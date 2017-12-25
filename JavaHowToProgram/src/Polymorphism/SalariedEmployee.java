package Polymorphism;

public class SalariedEmployee extends Employee{
	private double weeklySalary;
	
	public double getWeeklySalary() {
		return weeklySalary;
	}

	public void setWeeklySalary(double weeklySalary) {
		this.weeklySalary = weeklySalary;
	}

	public SalariedEmployee(String firstName, String lastName, String socialSecurityNumber, double weeklySalary){
		super(firstName,lastName,socialSecurityNumber);
		this.weeklySalary = weeklySalary;
	}
	
	@Override
	public double getPaymentAmount() {
		// TODO Auto-generated method stub
		return getWeeklySalary();
	}	
	
	@Override
	public String toString(){
		return String.format("%nSalaried Employee: %s%n%s: %,.2f", super.toString(),"Weekly Salary: ",getWeeklySalary());
	}
}
