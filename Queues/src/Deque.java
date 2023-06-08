import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
	
	private int elements;
	private Node firstElement;
	private Node lastElement;
	
	private class Node {
        private Item item;
        private Node next;
        private Node prev;
    }
	
    // construct an empty deque
	public Deque() {
    	elements = 0;
    	firstElement = null;
    	lastElement = null;
    }

    // is the deque empty?
    public boolean isEmpty() {
    	return elements == 0;
    }

    // return the number of items on the deque
    public int size() {
    	return elements;
    }

    // add the item to the front
    public void addFirst(Item item) {
    	if (item == null) throw new IllegalArgumentException("Item can't be null");
    	
    	Node oldfirst = firstElement;
    	
    	firstElement = new Node();
    	firstElement.item = item;
    	firstElement.prev = null;
    	
    	
    	// if deque was empty before adding item
        if (isEmpty()) {
        	firstElement.next = null;
        	lastElement = firstElement;
        } else {  
        	oldfirst.prev = firstElement;
        	firstElement.next = oldfirst;
        }
        elements++;
    }

    // add the item to the back
    public void addLast(Item item) {
    	if (item == null) throw new IllegalArgumentException("Item can't be null");
    	
    	Node oldlast = lastElement;
    	
    	lastElement = new Node();
    	lastElement.item = item;
    	lastElement.next = null;
    	
    	// if deque was empty before adding item
        if (isEmpty()) {
        	lastElement.prev = null;
        	firstElement = lastElement;
        } else {  
        	lastElement.prev = oldlast;
        	oldlast.next = lastElement;
        }
        elements++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
    	if (isEmpty()) throw new java.util.NoSuchElementException("Can't remove item from empty deque");
    	
    	Item item = firstElement.item;
    	
    	firstElement = firstElement.next;
    	elements--;
    	
        if (isEmpty()) {
        	lastElement = null;
        } else {
        	firstElement.prev = null;
        }
        
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
    	if (isEmpty()) throw new java.util.NoSuchElementException("Can't remove item from empty deque");
    	
    	Item item = lastElement.item;
    	
    	lastElement = lastElement.prev;
    	elements--;
    	
        if (isEmpty()) {
        	firstElement = null;
        } else {
        	lastElement.next = null;
        }
        
        return item;
    }
    
    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
    	return new DequeIterator();
    }
    
    private class DequeIterator implements Iterator<Item> {
        private Node currentNode = firstElement;
        
        public boolean hasNext() { 
        	return (currentNode != null);                               
        }
        
        public Item next() {
            if (!hasNext()) throw new java.util.NoSuchElementException("No more items in itterator");
            
            Item item = currentNode.item;
            currentNode = currentNode.next;
            
            return item;
        }
        
        public void remove() { 
        	throw new UnsupportedOperationException("Remove not supported by itterator");  
        }
        
    }
    
    // Unit testing (required)
    public static void main(String[] args) {
    	Deque<Integer> deque = new Deque<Integer>();
    	
    	System.out.println("Empty: " + deque.isEmpty());
    	System.out.println("\nAdding items\n");
    	deque.addFirst(6);
    	deque.addFirst(4);
    	deque.addFirst(2);
    	deque.addFirst(0);
    	deque.addLast(8);
    	deque.addLast(10);
    	deque.addLast(12);
    	System.out.println("Empty: " + deque.isEmpty());
    	System.out.println("Size: " + deque.size());
    	
    	for (int i : deque) {
    		System.out.print(i + " ");
    	}
    	
    	System.out.println("\n\nPop Last Item: " + deque.removeLast());
    	System.out.println("Pop Last Item: " + deque.removeLast());
    	System.out.println("Pop First Item: " + deque.removeFirst());
    	System.out.println("Pop First Item: " + deque.removeFirst());
    	
    	System.out.println("\nSize: " + deque.size());
    	
    	for (int i : deque) {
    		System.out.print(i + " ");
    	}
    	
    	System.out.println("\n\nPop First Item: " + deque.removeFirst());
    	System.out.println("Pop First Item: " + deque.removeFirst());
    	System.out.println("Pop First Item: " + deque.removeFirst());
    	System.out.println("\nSize: " + deque.size());
    }
}
