public class Stack {
    private final int arraySize;
    private final int[] arr;
    private int top;

    public Stack() {
        arraySize = 100;
        this.top = -1;
        this.arr = new int[100];
    }

    public void push(int element) {
        if (!isFull()) {
            arr[++top] = element;
        }
    }

    public int pop() {
        if (top >= 0) {
            int returnElement = arr[top];
            top--;
            return returnElement;
        }
        else {
            return -1;
        }
    }

    public int size() {
        return top+1;
    }

    public int peek(){
        return arr[top];
    }

    public boolean isEmpty() {
        return top < 0;
    }

    public boolean isFull() {
        return top == arraySize - 1;
    }
}
