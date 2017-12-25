package Polymorphism;

public class Invoice implements Payable{
	private final String partNumber;
	private final String partDescription;
	private double unitPrice;
	private int quantity;
	
	public double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(double unitPrice) {
		if(unitPrice <= 0)
			throw new IllegalArgumentException("Unit Price must be greater than 0");
		
		this.unitPrice = unitPrice;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		if(quantity <= 0)
			throw new IllegalArgumentException("Quantity must be greater than 0");
		
		this.quantity = quantity;
	}

	public String getPartNumber() {
		return partNumber;
	}

	public String getPartDescription() {
		return partDescription;
	}

	public Invoice(String partNumber, String partDescription, double unitPrice, int quantity){
		this.partNumber = partNumber;
		this.partDescription = partDescription;
		
		if(unitPrice <= 0)
			throw new IllegalArgumentException("Unit Price must be greater than 0");
		
		if(quantity <= 0)
			throw new IllegalArgumentException("Quantity must be greater than 0");
		
		this.unitPrice = unitPrice; 
		this.quantity = quantity;
	}

	@Override
	public double getPaymentAmount() {
		// TODO Auto-generated method stub
		return getUnitPrice() * getQuantity();
	}
	
	@Override
	public String toString(){
		return String.format("%n%s%n%s: %s%n%s: %d%n%s: %s", "Invoice","Part Number",getPartNumber(),
				"Quantity",getQuantity(),"Price per item",getUnitPrice());
	}
}
