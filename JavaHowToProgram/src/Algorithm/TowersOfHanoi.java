package Algorithm;

public class TowersOfHanoi {
	private static int stackPointer =-1; //0 is the first non-recursive call
	
	public static void moveDisks(int discCount, int srcPeg, int destPeg, int tmpPeg) {
		stackPointer++;
		
		align();
		System.out.printf("Task: move %d %s from peg %d to peg %d %s",discCount,
				discCount==1?"disc":"discs",srcPeg,destPeg,discCount==1?"":", using peg "+tmpPeg+" as temp");
		//base case, move one disc
		if (discCount == 1) {			
			align();
			System.out.printf("%d --> %d", srcPeg, destPeg);
			stackPointer--;
			return;
		}
		
		//move n-1 discs from source peg to temp peg, using dest peg as a temp
		moveDisks(discCount-1, srcPeg, tmpPeg, destPeg);
		
		//move the last, biggest disc from source peg to dest peg
		stackPointer++;
		align();
		System.out.printf("Move the last of %d discs from disc %d to disc %d", discCount,srcPeg,destPeg);
		align();
		System.out.printf("%d --> %d", srcPeg, destPeg);
		stackPointer--;
		
		//move n-1 discs from temp peg to dest pegs, using source peg as a temp
		moveDisks(discCount-1, tmpPeg, destPeg, srcPeg);
		
		stackPointer--;
	}
	
	//printing space to indent the inner steps
	private static void align(){
		StringBuilder sb = new StringBuilder();
		
		for(int i=0;i<stackPointer;i++){
			sb.append("  ");
		}
		System.out.printf("%n%s",sb.toString());
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int srcPeg =1;
		int destPeg =3;
		int tmpPeg = 2;
		int discCount =4;
		
		moveDisks(discCount, srcPeg, destPeg, tmpPeg);		
	}

}
