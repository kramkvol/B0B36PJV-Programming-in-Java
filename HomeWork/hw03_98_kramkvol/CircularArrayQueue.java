package cz.cvut.fel.pjv;

/**
 * Implementation of the {@link Queue} backed by fixed size array.
 */
public class CircularArrayQueue implements Queue {

    /**
     * Creates the queue with capacity set to the value of 5.
     */
    private final String[] elements;
    private int front; //  index in beginning
    private int rear; // index in end
    private int size; // now
    private final int capacity; // max
    public CircularArrayQueue() {
        this.capacity = 5;
        this.size = 0;
        this.elements = new String[this.size];
        this.front = 0;
        this.rear = -1;
    }

    /**
     * Creates the queue with given {@code capacity}. The capacity represents maximal number of elements that the
     * queue is able to store.
     * @param capacity of the queue
     */
    public CircularArrayQueue(int capacity) {
        this.size = 0;
        this.capacity = capacity;
        this.elements = new String[capacity];
        this.front = 0;
        this.rear = -1;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean isFull() {
        return size == capacity;
    }

    @Override
    public boolean enqueue(String obj) {
        if (obj == null || isFull()) {
            return false;
        }
        rear = (rear + 1) % capacity;
        elements[rear] = obj;
        size++;
        return true;
    }

    @Override
    public String dequeue() {
        if (isEmpty()) {
            return null;
        }
        String value = elements[front];
        front = (front + 1) % capacity;
        size--;
        return value;
    }

    @Override
    public void printAllElements() {
        if (isEmpty()) {
            System.out.println("Queue is empty.");
            return;
        }
        int current = front;
        for (int i = 0; i < size; i++) {
            System.out.println(elements[current]);
            current = (current + 1) % capacity;
        }
    }
}
