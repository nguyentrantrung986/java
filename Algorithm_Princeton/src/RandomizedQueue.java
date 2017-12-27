import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
	private int n;
	private Item[] q;

	public RandomizedQueue() {
		n=0;
		q = (Item[]) new Object[2];
	}

	public boolean isEmpty() {
		return n == 0;
	}

	public int size() {
		return n;
	}

	public void enqueue(Item item) {
		if(item ==null) throw new IllegalArgumentException();
		
		if(n==q.length) resize(2*q.length);
		q[n++]=item;
	}
	
	//swap the last item to the randomIndex item
	public Item dequeue(){
	    if (isEmpty()) throw new NoSuchElementException("Queue is empty");
	    int randomIndex = StdRandom.uniform(n);
	    
	    Item tmp = q[randomIndex];
	    q[randomIndex]=q[--n];
	    q[n] = null;
	    
	    if(n>0&&n<q.length/4) resize(q.length/2);
	    
	    return tmp;
	}
	
	public Item sample(){
	    if (isEmpty()) throw new NoSuchElementException("Queue is empty");
	    int randomIndex = StdRandom.uniform(n);
	    
	    return q[randomIndex];
	}

	@Override
	public Iterator<Item> iterator() {
		return new RQueueIterator();
	}
	
	private class RQueueIterator implements Iterator<Item>{
		private int current;
		
		public RQueueIterator() {
			current =0;
			
			//Fisher Yates Shuffle takes O(n) time
			for(int i=n-1;i>1;i--){
				int randomIndex = StdRandom.uniform(i+1);
				Item tmp = q[randomIndex];
				q[randomIndex] = q[i];
				q[i] = tmp;
			}
		}
		@Override
		public boolean hasNext() {
			return current <=n-1;
		}

		@Override
		public Item next() {
			if(!hasNext()) throw new NoSuchElementException ();
				
			return q[current++];
		}
		
		public void remove(){
			throw new UnsupportedOperationException();
		}	
	}

	private void resize(int capacity) {
		Item[] copy = (Item[]) new Object[capacity];
		for (int i = 0; i < n; i++)
			copy[i] = q[i];
		q = copy;
	}
	
	//unit testing
	public static void main(String[] args){	
		RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
		
		StdOut.println("RQueue Operations: enqueue:1; dequeue:2; "
				+ "iterate-print:3; do nothing:other");
		int item;
		
		do{
			StdOut.print("Operation:");
			int op = StdIn.readInt();
			
			switch(op){
			case 1:
				StdOut.print("Item:");
				item = StdIn.readInt();
				rq.enqueue(item);
				break;
			case 2:
				rq.dequeue();
				break;
			case 3:
				break;
			}
			
			StdOut.println();
			for(int i:rq){
				StdOut.print(""+i+" ");
			}
		}while(true);
	}
}
