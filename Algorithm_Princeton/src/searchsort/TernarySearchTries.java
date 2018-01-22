package searchsort;

import edu.princeton.cs.algs4.Queue;

public class TernarySearchTries<Value> {
	private Node root;

	public void put(String key, Value val) {
		root = put(root, key, val, 0);
	}

	private Node put(Node x, String key, Value val, int d) {
		char c = key.charAt(d);
		
		if (x == null) {
			x = new Node();
			x.c = c;
		}
		
		if (c < x.c)
			x.left = put(x.left, key, val,d);
		else if (c > x.c)
			x.right = put(x.right, key, val, d);
		else if (d < key.length()-1)
			x.mid = put(x.mid,key,val,d+1);
		else
			x.val = val;
			
		return x;
	}
	
	public Value get(String key){
		return get(root,key,0);
	}
	
	private Value get(Node x, String key, int d){
		if(x==null) return null;
		char c = key.charAt(d);
		Value val;
		if(c < x.c)
			val = get(x.left, key, d);
		else if(c > x.c)
			val = get(x.right,key, d);
		else if (d == key.length() -1)
			return x.val;
		else
			val = get(x.mid,key,d+1);
		return val;
	}
	
	public Iterable<String> keys(){
		Queue<String> q = new Queue<>();
		collect(root,"",q);
		return q;
	}
	
	private void collect(Node x, String prefix, Queue<String> q){
		if(x == null) return;
		if(x.val != null) 
			q.enqueue(prefix+x.c);
		collect(x.left,prefix,q);
		collect(x.mid,prefix+x.c,q);
		collect(x.right,prefix,q);
	}
	private class Node {
		private char c;
		private Value val;
		private Node left, mid, right;
	}
	
	public static void main(String[] args){
		TernarySearchTries<Integer> tst = new TernarySearchTries<>();
		String[] a = {"she","sells","shells","by","the","sea","shore"};
		for(int i =0; i<a.length;i++)
			tst.put(a[i], i);
		for(String s:tst.keys()){
			System.out.println("Get value for key \""+s+"\": "+tst.get(s));
		}
	}
}
