import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
	
	private static final int STARTING_SIZE = 8; // Initial Array Size of 8.
	
	private Item[] d; // Deque (Double ended queue) list.
	private int elements;
	
    // Construct an empty randomized queue
    public RandomizedQueue() {
    	d = (Item[]) new Object[STARTING_SIZE];
    	elements = 0;
    }

    // Is the randomised queue empty?
    public boolean isEmpty() {
    	return elements == 0;
    }

    // Return the number of items on the randomised queue
    public int size() {
    	return elements;
    }

    // Add the item
    public void enqueue(Item item) {
    	if (item == null) throw new IllegalArgumentException("Item can't be null");
    	
    	if (elements == d.length) resize(d.length * 2);
    	
    	d[elements++] = item;
    }

    // Remove and return a random item
    public Item dequeue() {
    	if (elements == 0) throw new java.util.NoSuchElementException("Queue empty");
    	
    	int index = StdRandom.uniform(elements);
    	
    	Item item = d[index];
    	
    	d[index] = d[elements - 1];
    	d[elements - 1] = null;
    	elements--;
    	
    	if ((elements > 0) && (elements == d.length / 4)) resize(d.length / 2); 
    	
    	return item;
    }

    // Return a random item (but do not remove it)
    public Item sample() {
    	if (elements == 0) throw new java.util.NoSuchElementException("Queue empty");
    	
    	int index = StdRandom.uniform(elements);

    	return d[index];
    }
    
    private void resize(int size) {
    	Item[] copy = (Item[]) new Object[size];

    	for (int i = 0; i < elements; i++) {
    		copy[i] = d[i];
    	}
    	d = copy;
    }

    // Return an independent iterator over items in random order
    public Iterator<Item> iterator() {
    	return new ArrayIterator();
    }
    
    private class ArrayIterator implements Iterator<Item> {
    	private Item[] randomArray;
    	private int iteration = 0;
    	
        public ArrayIterator() {
        	randomArray = (Item[]) new Object[elements];
        	
        	for (int i = 0; i < elements; i++) {
        		randomArray[i] = d[i]; // copy across elements from array
        		
        		// Knuth shuffle to get uniformly random permutation
        		int index = StdRandom.uniform(i + 1);
        		Item swap = randomArray[index];		
        		randomArray[index] = randomArray[i];
        		randomArray[i] = swap;
        	}       	
        }
        
        public boolean hasNext() {
        	return iteration < elements;                               
        }
        
        public Item next() {
            if (!hasNext()) throw new java.util.NoSuchElementException("No more items in itterator");
            
            return randomArray[iteration++];
        }
        
        public void remove() { 
        	throw new UnsupportedOperationException("Remove not supported by itterator");  
        }
        
    }
    

    // Unit testing (required)
    public static void main(String[] args) {
 
    	RandomizedQueue<Integer> rQueue = new RandomizedQueue<Integer>();
    	
    	System.out.println("Empty: " + rQueue.isEmpty());
    	for (int i = 1; i <= 10; i++) {
    		rQueue.enqueue(i);
    	}
    	System.out.println("Empty: " + rQueue.isEmpty());
    	System.out.println("Queue Length: " + rQueue.size());
    	System.out.println("Random Dequeue:" + rQueue.dequeue());
    	System.out.println("Queue Length: " + rQueue.size());
    	System.out.println("Random Sample: " + rQueue.sample());
    	
    	for (int i : rQueue) {
    		System.out.print(i + " ");
    	}
    }
}
