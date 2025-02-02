/**
 * Implement an application that support undo/redo functionality. Use a linked list to maintain a sequence of states.\
 * Each state change is stored as a node in the list, allowing for easy navigation
 * 1<>2<>3<>4<>5
 */

public class UndoRedoManager<T> {
    private class Node {
        private T state;
        private Node prev;
        private Node next;

        private Node(T state) {
            this.state = state;
        }
    }

    private Node currentState;
//    private Node head;

    // Undo operation: Move to the previous state
    public T undo() {
        if (currentState != null && currentState.prev != null) {
            currentState = currentState.prev;
            return currentState.state;
        }
        return null;  // No previous state, cannot undo
    }

    // Redo operation: Move to the next state
    public T redo() {
        if (currentState != null && currentState.next != null) {
            currentState = currentState.next;
            return currentState.state;
        }
        return null;  // No next state, cannot redo
    }

    // Add a new state: Create a new node, set it as the current state, and discard redo history
    public void addState(T newState) {
        if (currentState != null) {
            Node newNode = new Node(newState);
            newNode.prev = currentState;
            currentState.next = newNode;
            currentState = newNode;
            currentState.next = null;
        } else {
            currentState = new Node(newState);
        }
    }

    public static void main(String[] args) {
        UndoRedoManager<String> manager = new UndoRedoManager<>();

        // Test cases to add states and perform undo/redo
        manager.addState("State 1");
        manager.addState("State 2");
        manager.addState("State 3");

        System.out.println("Undo: " + manager.undo());  // State 2
        System.out.println("Undo: " + manager.undo());  // State 1
        System.out.println("Redo: " + manager.redo());  // State 2

        System.out.println("");
        System.out.println("Add new state");
        manager.addState("State 4");  // Adding a new state after redo

        // After adding a new state, redo history is cleared
        System.out.println("Redo after adding a new state: " + manager.redo());  // State null
    }
}
