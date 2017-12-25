package JavaHowToProgram.ControlStructures;

public class ExceptionFlow {
	public static void main(String[] args){
		try{
			method1();
			System.out.println("This line won't be executed!");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private static void method1() throws Exception{
		try{
			throw new Exception("Exception in method1 try block");
		}catch(Exception e){
			throw new Exception("Exception in method1 catch block",e);
		}
		finally{
			System.out.println("Finally block executed!");
		}
	}
}
