import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class TqsStackTest {

    private TqsStack<Integer> unboundedStack;
    private TqsStack<Integer> boundedStack;

    @BeforeEach
    void setUp() {
        unboundedStack = new TqsStack<>();
        boundedStack = new TqsStack<>(3);
    }
    @AfterEach
    void tearDown() {
    }

    // a
    @DisplayName("Stack is empty on construction")
    @Test
    void isEmpty() {
        assertTrue(unboundedStack.isEmpty());
    }

    // b
    @DisplayName("Stack has size 0 on construction.")
    @Test
    void size(){
        assertEquals(0, unboundedStack.size(), "Stack size is not 0 on construction.");
        //assertTrue(stack.size() == 0, "Stack size is not 0 on construction");
    }

    // c
    @DisplayName("After n pushes to an empty stack, n > 0, the stack is not empty and its size is n.")
    @Test
    void push() {
        unboundedStack.push(1);
        assertFalse(unboundedStack.isEmpty());
        assertEquals(1, unboundedStack.size());
        unboundedStack.push(2);
        assertEquals(2, unboundedStack.size());
    }

    // d
    @DisplayName("If one pushes x then pops, the value popped is x.")
    @Test
    void pop() {
        unboundedStack.push(1);
        assertEquals(1, unboundedStack.pop());
        assertTrue(unboundedStack.isEmpty());
        assertEquals(0, unboundedStack.size());
    }

    // e
    @DisplayName("If one pushes x then peeks, the value returned is x, but the size stays the same.")
    @Test
    void testPushPeek() {
        unboundedStack.push(2);
        assertEquals(2, unboundedStack.peek());
        assertFalse(unboundedStack.isEmpty());
        assertEquals(1, unboundedStack.size());
    }

    // f
    @DisplayName("If the size is n, then after n pops, the stack is empty and has a size 0.")
    @Test
    void testSizeAfterPops(){
        int x = 1;
        int y = 2;
        int z = 3;
        unboundedStack.push(x);
        unboundedStack.push(y);
        unboundedStack.push(z);

        unboundedStack.pop();
        unboundedStack.pop();
        unboundedStack.pop();


        assertAll(
                () -> assertEquals(0, unboundedStack.size(), "After popping all the items, the stack has not size 0"),
                () -> assertTrue(unboundedStack.isEmpty(), "After popping all the items, the stack is not empty"));
    }

    // g
    @DisplayName("Popping from an empty stack does throw a NoSuchElementException.")
    @Test
    void testPopEmpty() {
        assertTrue(unboundedStack.isEmpty());
        assertThrows(NoSuchElementException.class, () -> unboundedStack.pop());
    }

    // h
    @DisplayName(" Peeking into an empty stack does throw a NoSuchElementException.")
    @Test
    void testPeekEmpty() {
        assertTrue(unboundedStack.isEmpty());
        assertThrows(NoSuchElementException.class, () -> unboundedStack.peek());
    }

    // i
    @DisplayName("For bounded stacks only, pushing onto a full stack does throw an IllegalStateException.")
    @Test
    void testBoundedPushFull() {
        boundedStack.push(1);
        boundedStack.push(2);
        boundedStack.push(3);
        assertThrows(IllegalStateException.class, () -> boundedStack.push(5));
    }
}
