package JavaHowToProgram.Arithmetics;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.NumberFormat;

public class BankInterest {
	public static void main(String args[]){
		BigDecimal principle = BigDecimal.valueOf(1000);
		BigDecimal rate = BigDecimal.valueOf(0.05);
		MathContext mc = new MathContext(3);
		BigDecimal amount;
		
		System.out.printf("%n%5s%20s", "Year","Amount");
		for(int i=1;i<=10;i++){
			amount = principle.multiply(rate.add(BigDecimal.ONE).pow(i));
			amount.setScale(1, BigDecimal.ROUND_HALF_EVEN);
//			System.out.printf("%n%5s%20s", i,NumberFormat.getCurrencyInstance().format(amount));
			System.out.printf("%n%5s%20s", i,amount.setScale(3, RoundingMode.HALF_EVEN));
		}
		
	}
}
