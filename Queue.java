public class Queue {
    private int capacity, front, rear, currentSize;
    private int[] arr;

    public Queue(){
        capacity = 100;
        this.arr = new int[100];
        currentSize = 0;
        front = 0;
        rear = 0;
    }

    public void enqueue(int number) {
        if(capacity != rear) {
            arr[rear] = number;
            rear++;
        }
    }

    public int dequeue() {
        int result = 0;
        if (!isEmpty()) {
            result = arr[0];
            rear--;
            for (int i = 0; i < rear; i++)
                arr[i] = arr[i+1];
            arr[rear] = 0;
        }
        return result;
    }

    public void print() {
        for (int number : arr) {
            System.out.print(number + " ");
        }
    }

    public int peek(){
        return arr[front];
    }

    public int front() {
        return arr[front];
    }

    public int size() {
        return rear;
    }

    public boolean isEmpty() {
        return front == rear;
    }

    public boolean isFull() {
        return currentSize == capacity;
    }
}
