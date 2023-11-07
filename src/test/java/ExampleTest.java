package test.java;


import org.junit.jupiter.api.Test;

import main.RecipeLogic.Recipe;
import main.RecipeLogic.RecipeManager;

import static org.junit.jupiter.api.Assertions.assertEquals;


import org.junit.jupiter.api.BeforeEach;


public class ExampleTest {
    private RecipeManager manager;
    private Recipe first;
    
    @BeforeEach
    void setUp() {
        first = new Recipe("sandwich", "put stuff between two slices of bread");
        manager = new RecipeManager();
    }
    
    @Test
    void testAdd() {
        manager.addRecipe(first);
        assertEquals(1, manager.getList().size());
        assertEquals("sandwich", manager.getList().get(0).getTitle());
    }
    
    /*@Test
    void testDequeue() {
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        assertEquals(1, queue.dequeue());
        assertEquals(2, queue.dequeue());
        assertEquals(1, queue.size());
    }
    
    @Test
    void testPeek() {
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        assertEquals(1, queue.peek());
        assertEquals(3, queue.size());
    }
    
    @Test
    void testIsEmpty() {
        assertTrue(queue.isEmpty());
        queue.enqueue(1);
        assertFalse(queue.isEmpty());
    }
    
    @Test
    void testIsFull() {
        assertFalse(queue.isFull());
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        queue.enqueue(4);
        queue.enqueue(5);
        assertTrue(queue.isFull());
    }
    
    @Test
    void testEnqueueWhenFull() {
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        queue.enqueue(4);
        queue.enqueue(5);
        assertThrows(IllegalStateException.class, () -> queue.enqueue(6));
    }
    
    @Test
    void testDequeueWhenEmpty() {
        assertThrows(IllegalStateException.class, () -> queue.dequeue());
    } */
}