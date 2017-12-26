
public class FixedCapacityGenericStack<Item> {
	private Item[] s;
	private int N = 0;
	
	public FixedCapacityGenericStack(int capacity){
		/*since the values of the Object array elements are all null, they can be cast to any other
		reference type. Formally, null is a singleton member of the null type, 
		which is defined to be the subtype of every other Java type*/
		
		s = (Item[])new Object[capacity];
	}
	
	public boolean isEmpty(){
		return N==0;
	}
	
	public void push(Item item){
		s[N++] = item;
	}
	
	public Item pop(){
		return s[--N];
	}
	
	public static void main(String[] args){
		Object[] obj = new Object[3];
		Object obj2 = new Object();
		obj[0] = new Integer(3);
		Integer i = (Integer) obj[1];
		System.out.println(""+i+" "+obj[0]+" "+obj[2]);
		System.out.println(""+obj2);
	}
}
