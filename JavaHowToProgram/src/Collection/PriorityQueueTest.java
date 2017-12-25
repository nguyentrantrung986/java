package Collection;

import java.util.PriorityQueue;

public class PriorityQueueTest {
	public static void main(String[] args){
		PriorityQueue<Double> pq = new PriorityQueue<>();
		pq.offer(5.4);
		pq.offer(2.3);
		pq.offer(9.9);
		pq.offer(8.1);
		
		System.out.println(pq.toString());
		
		while(!pq.isEmpty()){
			System.out.printf("Removing %.1f%n ", pq.poll());
			System.out.println(pq.toString());
		}
	}
}
