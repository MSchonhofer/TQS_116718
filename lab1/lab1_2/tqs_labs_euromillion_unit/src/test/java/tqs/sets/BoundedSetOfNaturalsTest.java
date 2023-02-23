/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tqs.sets;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import tqs.sets.BoundedSetOfNaturals;

import javax.annotation.processing.SupportedAnnotationTypes;

/**
 * @author ico0
 */
class BoundedSetOfNaturalsTest {
    private BoundedSetOfNaturals setA;
    private BoundedSetOfNaturals setB;
    private BoundedSetOfNaturals setC;


    @BeforeEach
    public void setUp() {
        setA = new BoundedSetOfNaturals(2);
        setB = BoundedSetOfNaturals.fromArray(new int[]{10, 20, 30, 40, 50, 60});
        setC = BoundedSetOfNaturals.fromArray(new int[]{50, 60});
    }

    @AfterEach
    public void tearDown() {
        setA = setB = setC = null;
    }

    @Test
    public void testAddElement() {
        setA.add(99);
        assertTrue(setA.contains(99), "add: added element not found in set.");
        assertEquals(1, setA.size());

    }

    @Test
    public void testAddFromBadArray() {
        int[] elems = new int[]{10, -20, -30};

        // must fail with exception
        assertThrows(IllegalArgumentException.class, () -> setA.add(elems));
    }

    @Test
    public void testAddDuplicateArray() {
        setA.add(10);
        assertThrows(IllegalArgumentException.class, () -> setA.add(10));
    }

    @Test
    public void testAddElementToFullSet(){
        assertThrows(IllegalArgumentException.class, () -> setB.add(12));
    }

    @Test
    void testAddDuplicateValue() {
        setA.add(20);
        assertThrows(IllegalArgumentException.class, () -> setA.add(20));
    }

    @Test
    public void testAddNonNaturalNumbers(){
        int[] elems = new int[]{-1, -3, -2137};
        assertThrows(IllegalArgumentException.class, ()-> setA.add(elems));
        assertThrows(IllegalArgumentException.class, ()-> setA.add(-2137));
    }

    @Test
    public void testSize(){
        assertEquals(setB.size(), 6);
    }

    @Test
    void testIntersects() {
        assertTrue(setB.intersects(setC));
        assertFalse(setA.intersects(setC));
        assertFalse(setA.intersects(setB));
        }

    @Test
    void testHashCode() {
        BoundedSetOfNaturals set1 = BoundedSetOfNaturals.fromArray(new int[]{10, 20, 30, 40, 50, 60});
        BoundedSetOfNaturals set2 = BoundedSetOfNaturals.fromArray(new int[]{10, 20, 30, 40, 50, 60});

        assertTrue(set1.equals(set2));
        assertEquals(set1.hashCode(), set2.hashCode());

        //assertNotEquals(setB.hashCode(), setC.hashCode());
    }

    @Test
    void testSetContains() {
        assertFalse(setB.contains(80));
        assertTrue(setB.contains(50));
        assertFalse(setC.contains(2));
        assertTrue(setC.contains(50));
    }

    @Test
    void testEquals() {
        setB = setA;
        assertTrue(setB.equals(setA));
        assertFalse(setA.equals(setC));
    }

    @Test
    void testEqualsWhenNull() {
        assertFalse(setA.equals(null));
    }

    @Test
    void testEqualsWhenDifferentClass() {
        int number = 0;
        assertFalse(setA.equals(number));
    }
}