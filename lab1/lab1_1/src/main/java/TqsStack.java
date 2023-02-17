import java.util.ArrayList;
import java.util.NoSuchElementException;

public class TqsStack<T> {

    private ArrayList<T> stack;

    public TqsStack(){
        stack = new ArrayList<T>();
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
        stack.add(object);
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


