import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class TqsStackTest {

    private TqsStack<Integer> stack;
    private int size;

    @BeforeEach
    void setUp(){
        stack = new TqsStack<>();
    }

    // 1. A stack is empty on construction.
    @Test
    void isEmpty(){
        assertTrue(stack.isEmpty(), "Stack is not empty on construction.");
    }

    // 2. A stack has size 0 on construction.
    @Test
    void size(){
        assertEquals(0, stack.size(), "Stack size is not 0 on construction.");
        //assertTrue(stack.size() == 0, "Stack size is not 0 on construction");
    }

    // 3. After n pushes to an empty stack, n > 0, the stack is not empty and its size is n.
    @Test
    void push(){
        assertTrue(stack.isEmpty(), "Size of the stack is greater than n after n pushes.");
    }

    @Test
    public void testPushNElements() {
        final int n = 5;

        for (int i = 0; i < n; i++) {
            stack.push(i);
        }

        assertFalse(stack.isEmpty());
        assertEquals(n, stack.size());
    }
    // 4.  If one pushes x then pops, the value popped is x.
    @Test
    void pop(){
        int x = 100;
        stack.push(x);
        assertEquals(x, stack.pop(), "The value popped is different from the pushed one.");
    }

    // 5. If one pushes x then peeks, the value returned is x, but the size stays the same.
    @Test
    void peek(){
        int x = 100;
        stack.push(x);
        assertEquals(x, stack.peek(), "The value peeked is different from the one pushed");
    }

    // 6. Popping from an empty stack does throw a NoSuchElementException
    @Test
    void popEmptyStack(){
        assertTrue(stack.isEmpty());
        assertThrows(NoSuchElementException.class, () -> stack.pop());
    }

    // 7. Peeking into an empty stack does throw a NoSuchElementException
    @Test
    void peekEmptyStack(){
        assertTrue(stack.isEmpty());
        assertThrows(NoSuchElementException.class, () -> stack.peek());
    }

    // 8. For bounded stacks only, pushing onto a full stack does throw an IllegalStateException
    @Test
    void pushBoundedStack() {
    }

}