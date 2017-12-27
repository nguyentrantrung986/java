import java.util.Iterator;

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
		first = newFirst;
		
		if(isEmpty())
			last=newFirst;
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
		
		last = newLast;
		n++;
	}
	
	// remove and return the item from the front
	public Item removeFirst(){
		if(isEmpty())
			throw new java.util.NoSuchElementException();
		
		Item item = first.item;
		first = first.next;
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
		last.next = null;
		n--;
		return item;
	}
	
	@Override
	public Iterator<Item> iterator() {
		// TODO Auto-generated method stub
		return null;
	}
	
	private class Node{
		Item item;
		Node next;
		Node prev;
	}
}
