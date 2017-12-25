package Polymorphism;

public class PayableInterfaceTest {
	public static void main(String[] args){
		Payable[] payables = new Payable[4];
		payables[0] = new Invoice("HDD1","Hard disk 01",80,3);
		payables[1] = new Invoice("SSD1","Solid State Drive 01",90,2);
		payables[2] = new SalariedEmployee("Tuan","Vu","11111",450);
		payables[3] = new SalariedEmployee("Trang", "Tran", "22222", 600);
		
		System.out.println("Process payable objects polymorphically: ");
		for(Payable p:payables){
			System.out.println(p.toString());
			System.out.printf("%s: $%,.2f%n", "Payment due:",p.getPaymentAmount());
		}
	}
}
