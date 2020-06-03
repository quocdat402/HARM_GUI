package undoredoStack;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * Push or pop every executed action 
 */
class QueueStack<T> {

    private List<T> dataCollection;

    QueueStack() {
        dataCollection = new LinkedList<>();
    }

    /*
     * Stack executed action
     */
    void push(T item) {
        dataCollection.add(0, item);
    }

    /*
     * When performing undo, that action will be popped
     */
    Optional<T> pop() {
        if(dataCollection.size() > 0)
            return Optional.of(dataCollection.remove(dataCollection.size() - 1));
        else
            return Optional.empty();
    }

    void clear() {
        dataCollection.clear();
    }

}