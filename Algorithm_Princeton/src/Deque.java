import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item>  {
	private Node first, last;
	private int n; //size
	
	public Deque(){
		n=0;
	}
	
	public boolean isEmpty(){
		return n==0;
	}
	
	public int size(){
		return n;
	}
	
	// add the item to the front
	public void addFirst(Item item){
		if(item == null)
			throw new java.lang.IllegalArgumentException();
		
		Node newFirst = new Node();
		newFirst.item = item;
		newFirst.next = first;
		newFirst.prev = null;				
		
		if(isEmpty())
			last=newFirst;
		else
			first.prev = newFirst;
		
		first = newFirst;
		n++;
	}
	
	// add the item to the end
	public void addLast(Item item) {
		if(item == null)
			throw new java.lang.IllegalArgumentException();
		
		Node newLast = new Node();
		newLast.item = item;
		newLast.next = null;
		newLast.prev = last;
		
		if(!isEmpty())
			last.next=newLast;
		else
			first = newLast;
		
		last = newLast;
		
		n++;
	}
	
	// remove and return the item from the front
	public Item removeFirst(){
		if(isEmpty())
			throw new java.util.NoSuchElementException();
		
		Item item = first.item;
		first = first.next;
		if(first != null)
			first.prev = null;
		n--;
		return item;
	}
	
	// remove and return the item from the end
	public Item removeLast(){
		if(isEmpty())
			throw new java.util.NoSuchElementException();
				
		Item item = last.item;
		last = last.prev;
		if(last != null)
			last.next = null;
		else
			first = null; //the deque is now empty
		n--;
		return item;
	}
	
	@Override
	public Iterator<Item> iterator() {
		return new DequeIterator();
	}
	
	private class DequeIterator implements Iterator<Item>{
		private Node current = first;
		
		@Override
		public boolean hasNext() {
			return current != null;
		}

		@Override
		public Item next() {
			if(!hasNext())
				throw new NoSuchElementException();
			
			Item item = current.item;
			current = current.next;
			return item;
		}
		
		public void remove(){
			throw new UnsupportedOperationException();
		}
	}
	
	private class Node{
		Item item;
		Node next;
		Node prev;
	}
	
	//unit testing
	public static void main(String[] args){		
		Deque<Integer> d = new Deque<Integer>();
		
		StdOut.println("Deque Operations: add first:1; add last:2; "
				+ "remove First:3; remove Last:4; do nothing:other");
		int item;
		
		do{
			StdOut.print("Operation:");
			int op = StdIn.readInt();
			
			switch(op){
			case 1:
				StdOut.print("Item:");
				item = StdIn.readInt();
				d.addFirst(item);
				break;
			case 2:
				StdOut.print("Item:");
				item = StdIn.readInt();
				d.addLast(item);
				break;
			case 3: d.removeFirst();
			break;
			case 4: d.removeLast();
			break;
			}
			
//			Iterator<Integer> ite = d.iterator();
//			while(ite.hasNext()){
//				Integer i = ite.next();
//				StdOut.print(i+" ");
//			}
			for(Integer i:d){
				StdOut.print(i+" ");
			}
			StdOut.println();
		}while(true);
	}
}
