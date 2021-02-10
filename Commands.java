import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Commands {
    private Stack stack;
    private Queue queue;
    FileWriter stackOut = new FileWriter("stackOut.txt");
    FileWriter queueOut = new FileWriter("queueOut.txt");

    public Commands() throws IOException {
        stack = new Stack();
        queue = new Queue();
    }

    // This method gets command file as an argument in the main, reads the file line by line and controls every line
    // with switch case. Switch case controls that which operation should be used for that line.
    public void operations(String commandFile) throws IOException {
        Path filePath = Paths.get(commandFile);
        String[] lines = ReadFromFile.readFile(filePath.toAbsolutePath().toString());
        createStructures("stack.txt");
        createStructures("queue.txt");
        for (String line : lines) {
            String[] line1 = line.split(" ");
            switch (line1[1]) {
                case "removeGreater":
                    if (line1[0].equals("S")) {
                        stackOut.write("After removeGreater " + line1[2] + ":" + "\n");
                        removeGreater(Integer.parseInt(line1[2]), "stack");
                    }

                    else if (line1[0].equals("Q")) {
                        queueOut.write("After removeGreater " + line1[2] + ":" + "\n");
                        removeGreater(Integer.parseInt(line1[2]), "queue");
                    }
                    break;
                case "calculateDistance":
                    if (line1[0].equals("S")) {
                        stackOut.write("After calculateDistance:" + "\n");
                        calculateDistance("stack");
                    }

                    else if (line1[0].equals("Q")) {
                        queueOut.write("After calculateDistance:" + "\n");
                        calculateDistance("queue");
                    }
                    break;
                case "addOrRemove":
                    if (line1[0].equals("S")) {
                        stackOut.write("After addOrRemove " + line1[2] + ":" + "\n");
                        addOrRemove(Integer.parseInt(line1[2]), "stack");
                    }

                    else if (line1[0].equals("Q")) {
                        queueOut.write("After addOrRemove " + line1[2] + ":" + "\n");
                        addOrRemove(Integer.parseInt(line1[2]), "queue");
                    }
                    break;
                case "reverse":
                    if (line1[0].equals("S")) {
                        stackOut.write("After reverse " + line1[2] + ":" + "\n");
                        reverse(Integer.parseInt(line1[2]), "stack");
                    }

                    else if (line1[0].equals("Q")) {
                        queueOut.write("After reverse " + line1[2] + ":" + "\n");
                        reverse(Integer.parseInt(line1[2]), "queue");
                    }
                    break;
                case "sortElements":
                    if (line1[0].equals("S")) {
                        stackOut.write("After sortElements:" + "\n");
                        sort("stack");
                    }

                    else if (line1[0].equals("Q")) {
                        queueOut.write("After sortElements:" + "\n");
                        sort("queue");
                    }
                    break;
                case "distinctElements":
                    if (line1[0].equals("S")) {
                        stackOut.write("After distinctElements:" + "\n");
                        distinct("stack");
                    }

                    else if (line1[0].equals("Q")) {
                        queueOut.write("After distinctElements:" + "\n");
                        distinct("queue");
                    }
                    break;
            }
        }
        stackOut.close();
        queueOut.close();
        updateFiles("stack");
        updateFiles("queue");
    }

    //This method reads existing stack and queue files and creates stack and queue according to files.
    public void createStructures(String fileName) throws FileNotFoundException {
        File file = new File(fileName);
        Scanner s = new Scanner(file);
        while (s.hasNextLine()) {
            String data = s.nextLine();
            String[] splitData = data.split(" ");
            for (String number1 : splitData) {
                int n1 = Integer.parseInt(number1);
                if (fileName.equals("queue.txt")) {
                    queue.enqueue(n1);
                }
            }
            List<String> list = Arrays.asList(splitData);
            String[] original = new String[list.size()];
            Collections.reverse(list);
            list.toArray(original);
            for (String number : original) {
                int n = Integer.parseInt(number);
                if (fileName.equals("stack.txt")) {
                    stack.push(n);
                }
            }
        }
    }

    // This method gets type of data structure and a number as an argument, deletes numbers which are greater than
    // the input number and writes the output to txt files associated with their types.
    public void removeGreater(int number, String type) throws IOException {
        if (type.equals("stack")) {
            Stack tempStack = new Stack();
            while (!stack.isEmpty()) {
                if (number >= stack.peek()) {
                    int current = stack.pop();
                    tempStack.push(current);
                    stackOut.write(current + " ");
                }
                else {
                    stack.pop();
                }
            }
            while (!tempStack.isEmpty()) {
                stack.push(tempStack.pop());
            }
            stackOut.write("\n");
        }

        else if (type.equals("queue")) {
            Queue tempQueue = new Queue();
            while (!queue.isEmpty()) {
                if (number >= queue.front()) {
                    int current = queue.dequeue();
                    tempQueue.enqueue(current);
                    queueOut.write(current + " ");
                }
                else {
                    queue.dequeue();
                }
            }
            while (!tempQueue.isEmpty()) {
                queue.enqueue(tempQueue.dequeue());
            }
            queueOut.write("\n");
        }
    }

    // This method gets type as an argument, sorts data structures and
    // writes the output to txt files associated with their types.
    public void sort(String type) throws IOException {
        if(type.equals("stack")) {
            ArrayList<Integer> sortStack = new ArrayList<>();
            while (!stack.isEmpty()) {
                sortStack.add(stack.pop());
            }
            Collections.sort(sortStack);
            for (int number : sortStack) {
                stack.push(number);
                stackOut.write(number + " ");
            }
            stackOut.write("\n");
        }
        else if (type.equals("queue")) {
            ArrayList<Integer> sortQueue = new ArrayList<>();
            while (!queue.isEmpty()) {
                sortQueue.add(queue.dequeue());
            }
            Collections.sort(sortQueue);
            for (int number : sortQueue) {
                queue.enqueue(number);
                queueOut.write(number + " ");
            }
            queueOut.write("\n");
        }
    }

    // This method calculates sum of the distances of all elements to other elements
    // and writes the output to txt files associated with their types.
    public void calculateDistance(String type) throws IOException {
        if (type.equals("stack")) {
            ArrayList<Integer> numbers = new ArrayList<>();
            ArrayList<Integer> differences = new ArrayList<>();
            Stack tempStack = new Stack();
            while (!stack.isEmpty()) {
                int current = stack.pop();
                tempStack.push(current);
                numbers.add(current);
            }
            Integer[] array = numbers.toArray(new Integer[0]);
            for (int i = 0; i <= array.length-1; i++) {
                int in = i;
                for (int j = in++; j <= array.length-1; j++) {
                    differences.add(Math.abs(array[i] - array[j]));
                }
            }
            while (!tempStack.isEmpty()) {
                stack.push(tempStack.pop());
            }
            int sum = 0;
            for (int i : differences) {
                sum += i;
            }
            stackOut.write("Total distance=" + sum + "\n");
        }
        else if (type.equals("queue")) {
            ArrayList<Integer> numbers = new ArrayList<>();
            ArrayList<Integer> differences = new ArrayList<>();
            Queue tempQueue = new Queue();
            while (!queue.isEmpty()) {
                int current = queue.dequeue();
                tempQueue.enqueue(current);
                numbers.add(current);
            }
            Integer[] arrayQ = numbers.toArray(new Integer[0]);
            for (int i = 0; i <= arrayQ.length-1; i++) {
                int in = i;
                for (int j = in++; j <= arrayQ.length-1; j++) {
                    differences.add(Math.abs(arrayQ[i] - arrayQ[j]));
                }
            }
            while (!tempQueue.isEmpty()) {
                queue.enqueue(tempQueue.dequeue());
            }
            int sum = 0;
            for (int i : differences) {
                sum += i;
            }
            queueOut.write("Total distance=" + sum + "\n");
        }
    }

    // This method gets a value as an argument. If this value is negative, it deletes as many elements as that value's
    // absolute value. If this value is positive, it adds random numbers as many elements as
    // that value between 0 and 50 to stack or queue.
    public void addOrRemove(int value, String type) throws IOException {
        if (type.equals("stack")) {
            if (value < 0) {
                Queue tempQueue = new Queue();
                int v = Math.abs(value);
                for (int i = 0; i < v; i++) {
                    stack.pop();
                }
                while (!stack.isEmpty()) {
                    tempQueue.enqueue(stack.pop());
                }
                while (!tempQueue.isEmpty()) {
                    int current = tempQueue.dequeue();
                    stack.push(current);
                    stackOut.write(current + " ");
                }
            }
            else if (value > 0) {
                Stack tempStack = new Stack();
                Random random = new Random();
                for (int i = 0; i < value; i++) {
                    int randInt = random.nextInt(50);
                    stack.push(randInt);
                }
                while (!stack.isEmpty()) {
                    int current = stack.pop();
                    tempStack.push(current);
                    stackOut.write(current + " ");
                }
                while (!tempStack.isEmpty()) {
                    stack.push(tempStack.pop());
                }
            }
            stackOut.write("\n");
        }

        else if (type.equals("queue")) {
            if (value < 0) {
                Queue tempQueue = new Queue();
                int v = Math.abs(value);
                for (int i = 0; i < v; i++) {
                    queue.dequeue();
                }
                while (!queue.isEmpty()) {
                    tempQueue.enqueue(queue.dequeue());
                }
                while (!tempQueue.isEmpty()) {
                    int current = tempQueue.dequeue();
                    queue.enqueue(current);
                    queueOut.write(current + " ");
                }
            }
            else if (value > 0) {
                Queue tempQueue = new Queue();
                Random random = new Random();
                for (int i = 0; i < value; i++) {
                    int randInt = random.nextInt(50);
                    queue.enqueue(randInt);
                }
                while (!queue.isEmpty()) {
                    int current = queue.dequeue();
                    tempQueue.enqueue(current);
                    queueOut.write(current + " ");
                }
                while (!tempQueue.isEmpty()) {
                    queue.enqueue(tempQueue.dequeue());
                }
            }
            queueOut.write("\n");
        }
    }

    // This method gets value(i) as an argument and reverses the first i elements of stack or queue.
    public void reverse(int times, String type) throws IOException {
        if (type.equals("stack")) {
            Stack tempStack = new Stack();
            Queue tempQueue = new Queue();
            for (int i = 0; i < times; i++) {
                tempStack.push(stack.pop());
            }
            while (!stack.isEmpty()) {
                tempQueue.enqueue(stack.pop());
            }
            while (!tempStack.isEmpty()) {
                int current = tempStack.pop();
                stack.push(current);
                stackOut.write(current + " ");
            }
            while (!tempQueue.isEmpty()) {
                int current = tempQueue.dequeue();
                stack.push(current);
                stackOut.write(current + " ");
            }
            stackOut.write("\n");
        }

        else if (type.equals("queue")) {
            Queue tempQueue = new Queue();
            ArrayList<Integer> arrayList = new ArrayList<>();
            for (int i = 0; i < times; i++) {
                arrayList.add(queue.dequeue());
            }
            while (!queue.isEmpty()) {
                tempQueue.enqueue(queue.dequeue());
            }
            Collections.reverse(arrayList);
            for (int number : arrayList) {
                queue.enqueue(number);
                queueOut.write(number + " ");
            }
            while (!tempQueue.isEmpty()) {
                int current = tempQueue.dequeue();
                queue.enqueue(current);
                queueOut.write(current + " ");
            }
            queueOut.write("\n");
        }
    }

    // This method finds that how many unique numbers that stack or queue contains.
    public void distinct(String type) throws IOException {
        if(type.equals("stack")) {
            int distinctElement = 1;
            Stack tempStack = new Stack();
            Stack tempStack1 = new Stack();
            ArrayList<Integer> sortedStack = new ArrayList<>();
            while (!stack.isEmpty()) {
                int current = stack.pop();
                tempStack.push(current);
                sortedStack.add(current);
            }
            Collections.sort(sortedStack);
            Integer[] array = sortedStack.toArray(new Integer[0]);
            for (int i = 0; i < sortedStack.size(); i++) {
                int k = i + 1;
                if (k < sortedStack.size()) {
                    if (!array[i].equals(array[k])) {
                        distinctElement += 1;
                    }
                }
            }
            while (!tempStack.isEmpty()) {
                tempStack1.push(tempStack.pop());
            }
            while (!tempStack1.isEmpty()) {
                stack.push(tempStack1.pop());
            }
            stackOut.write("Total distinct element=" + distinctElement + "\n");
        }
        else if (type.equals("queue")) {
            int distinctElement = 1;
            Queue tempQueue = new Queue();
            ArrayList<Integer> sortedQueue = new ArrayList<>();
            while (!queue.isEmpty()) {
                int current = queue.dequeue();
                tempQueue.enqueue(current);
                sortedQueue.add(current);
            }
            Collections.sort(sortedQueue);
            Integer[] array = sortedQueue.toArray(new Integer[0]);
            for (int i = 0; i < sortedQueue.size(); i++) {
                int k = i + 1;
                if (k < sortedQueue.size()) {
                    if (!array[i].equals(array[k])) {
                        distinctElement += 1;
                    }
                }
            }
            while (!tempQueue.isEmpty()) {
                queue.enqueue(tempQueue.dequeue());
            }
            queueOut.write("Total distinct element=" + distinctElement + "\n");
        }
    }

    //This method updates stack.txt and queue.txt with their final versions.
    public void updateFiles(String fileName) throws IOException {
        FileWriter fileWriter = new FileWriter(fileName + ".txt");
        if (fileName.equals("stack")) {
            while (!stack.isEmpty()) {
                int current = stack.pop();
                fileWriter.write(Integer.toString(current) + " ");
            }
        }
        else if (fileName.equals("queue")) {
            while (!queue.isEmpty()) {
                int current = queue.dequeue();
                fileWriter.write(Integer.toString(current) + " ");
            }
        }
        fileWriter.close();
    }
}
