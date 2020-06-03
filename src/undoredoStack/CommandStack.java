package undoredoStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Executing actions and performing undo/redo
 */
public class CommandStack {
	 private List<Command> commands = Collections.emptyList();
	    private int nextPointer = 0;

	    public void doCommand(Command command) {
	        List<Command> newList = new ArrayList<>(nextPointer + 1);

	        for(int k = 0; k < nextPointer; k++) {
	            newList.add(commands.get(k));
	        }

	        newList.add(command);

	        commands = newList;
	        nextPointer++;

	    }

	    public boolean canUndo() {
	        return nextPointer > 0;
	    }

	    //Perform undo function
	    public void undo() {
	        if(canUndo()) {
	            nextPointer--;
	            Command commandToUndo = commands.get(nextPointer);
	            //Do undo comman
	            commandToUndo.undo();
	         } else {
	             throw new IllegalStateException("Cannot undo");
	         }
	    }

	    public boolean canRedo() {
	        return nextPointer < commands.size();
	    }

	    //Perform redo function
	    public void redo() {
	        if(canRedo()) {
	        	Command commandToDo = commands.get(nextPointer);
	            nextPointer++;
	            //Do redo command
	            commandToDo.execute();
	        } else {
	            throw new IllegalStateException("Cannot redo");
	        }
	    }
}
