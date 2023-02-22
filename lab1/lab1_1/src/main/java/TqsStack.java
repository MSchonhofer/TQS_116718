import java.util.ArrayList;
import java.util.NoSuchElementException;

public class TqsStack<T> {

    private ArrayList<T> stack;
    private int boundedStack;

    public TqsStack(){
        this.stack = new ArrayList<>();
    }
    public TqsStack(int boundedStack){
        this.stack = new ArrayList<T>();
        this.boundedStack = boundedStack;
    }

    //return the number of items in the stack
    public int size(){
        return stack.size();
    }

    // check whether the stack is empty
    public boolean isEmpty(){
        return stack.size() == 0;
    }

    // add object on the top of the stack
    public void push(T object){
        if (boundedStack > 0) {
            if (stack.size() < boundedStack) {
                stack.add(object);
            } else {
                throw new IllegalStateException("Stack is full");
            }
        }else {
                stack.add(object);
            }
        }

    // remove object from the stack
    public T pop(){
        if (stack.size() == 0){
            throw new NoSuchElementException();
        }
            return stack.remove(stack.size()-1);
    }

    // return the item from the top without removing it
    public T peek(){
        if (stack.size() == 0){
            throw new NoSuchElementException();
        }
        return stack.get(stack.size()-1);
    }
}


