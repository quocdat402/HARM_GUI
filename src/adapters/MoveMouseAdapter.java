package adapters;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.company.MainController;
import com.company.MainModel;
import com.company.MainView;

import undoredoStack.Command;



public class MoveMouseAdapter extends MouseAdapter implements Command {
	
	private int moveNodeX;
	private int moveNodeY;
	private int nodeIndex;
	private List<Integer> moveUndoInteger;
	
	private MainModel model;
	private MainView view;
	private MainController controller;
	
	public MoveMouseAdapter(MainModel m, MainView v, MainController c) {
		
		this.model = m;
		this.view = v;
		this.controller = c;
		
	}
	
	@Override
	public void mousePressed(MouseEvent e) {

		if (controller.getActivateMove() == 1) {

			controller.setMoveUndoArcCounter(0);

			for (int i = 0; i < model.getNodes().size(); i++) {

				int x = model.getNodes().get(i).getX() + 12;

				int y = model.getNodes().get(i).getY() + 12;

				int radius = model.getNodes().get(i).getDiameter() / 2;

				/*
				 * Check that user's mouse pointer is on any one of the Node the centrepane
				 */
				if (Math.pow(x - e.getX(), 2) + Math.pow(y - e.getY(), 2) <= Math.pow(radius, 2)) {

					/*
					 * Add temporary variables for undo action
					 */
					moveUndoInteger = new ArrayList<>();
					moveNodeX = model.getNodes().get(i).getX();
					moveNodeY = model.getNodes().get(i).getY();
					nodeIndex = i;
					moveUndoInteger.add(nodeIndex);
					moveUndoInteger.add(moveNodeX);
					moveUndoInteger.add(moveNodeY);
					controller.getMoveUndoMap().put(controller.getMoveCounter(), moveUndoInteger);
					
					controller.setMoveCounter(controller.getMoveCounter()+1);

				}
			}

			view.getCenterPanel().repaint();

		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
		

		if (controller.getActivateMove() == 1 && !model.getNodes().isEmpty()) {

			/*
			 * Set a new point for node while the mouse is dragging
			 */
			model.getNodes().get(nodeIndex).setX(e.getX());
			model.getNodes().get(nodeIndex).setY(e.getY());
			/*
			 * Set a new point for Arc connected to the node
			 */
			for (int i = 0; i < model.getArcs().size(); i++) {

				if (nodeIndex == model.getArcs().get(i).getInitNode()) {

					if (model.getArcs().get(i).getX2() > model.getArcs().get(i).getX1()) {

						model.getArcs().get(i).setX1(e.getX() + 24);

					} else if (model.getArcs().get(i).getX2() < model.getArcs().get(i).getX1()) {

						model.getArcs().get(i).setX1(e.getX());

					} else {

						model.getArcs().get(i).setX1(e.getX() + 12);

					}
					model.getArcs().get(i).setY1(e.getY() + 12);
				}
			}
			
			/*
			 * Set a new point for Arc connected to the node
			 */
			for (int i = 0; i < model.getArcs().size(); i++) {

				if (nodeIndex == model.getArcs().get(i).getEndNode()) {

					if (model.getArcs().get(i).getX2() > model.getArcs().get(i).getX1()) {

						model.getArcs().get(i).setX2(e.getX());

					} else if (model.getArcs().get(i).getX2() < model.getArcs().get(i).getX1()) {

						model.getArcs().get(i).setX2(e.getX() + 24);

					} else {

						model.getArcs().get(i).setX2(e.getX() + 12);

					}
					model.getArcs().get(i).setY2(e.getY() + 12);
				}
			}

			view.getCenterPanel().repaint();

			/*
			 * Add moving node and arcs command into stack for undo and redo action
			 */
			controller.getStack().doCommand(new MoveMouseAdapter(model, view, controller));

		}

	}

	@Override
	public void mouseDragged(MouseEvent e) {

		if (controller.getActivateMove() == 1 && !model.getNodes().isEmpty()) {
			
			/*
			 * Set a new point for node while the mouse is dragging
			 */
			model.getNodes().get(nodeIndex).setX(e.getX());
			model.getNodes().get(nodeIndex).setY(e.getY());

			/*
			 * Set a new point for Arc connected to the node while the mouse is dragging
			 */
			for (int i = 0; i < model.getArcs().size(); i++) {

				if (nodeIndex == model.getArcs().get(i).getInitNode()) {

					if (model.getArcs().get(i).getX2() > model.getArcs().get(i).getX1()) {

						model.getArcs().get(i).setX1(e.getX() + 24);

					} else if (model.getArcs().get(i).getX2() < model.getArcs().get(i).getX1()) {

						model.getArcs().get(i).setX1(e.getX());

					} else {

						model.getArcs().get(i).setX1(e.getX() - 12);

					}
					
					model.getArcs().get(i).setY1(e.getY() + 12);
					
				}

			}

			/*
			 * Set a new point for Arc connected to the node while the mouse is dragging
			 */
			for (int i = 0; i < model.getArcs().size(); i++) {

				if (nodeIndex == model.getArcs().get(i).getEndNode()) {

					if (model.getArcs().get(i).getX2() > model.getArcs().get(i).getX1()) {

						model.getArcs().get(i).setX2(e.getX());

					} else if (model.getArcs().get(i).getX2() < model.getArcs().get(i).getX1()) {

						model.getArcs().get(i).setX2(e.getX() + 24);

					} else {

						model.getArcs().get(i).setX2(e.getX() + 12);

					}
					
					model.getArcs().get(i).setY2(e.getY() + 12);

				}
			}
			
			view.getCenterPanel().repaint();
		}
	}

	/**
	 * Redo action of moving node.
	 */
	@Override
	public void execute() {
		// TODO Auto-generated method stub

		int nodeNumber;
		int xLocation;
		int yLocation;
		
		/*
		 * Set up the variables to move the node to the previous position.
		 */
		nodeNumber = controller.getMoveRedoMap().get(controller.getMoveRedoCounter() - 1).get(0);
		xLocation = controller.getMoveRedoMap().get(controller.getMoveRedoCounter() - 1).get(1);
		yLocation = controller.getMoveRedoMap().get(controller.getMoveRedoCounter() - 1).get(2);

		
		/*
		 * Move the node to the previous position
		 */
		for (int i = 0; i < model.getNodes().size(); i++) {

			List<Integer> undoList = new ArrayList<>();

			if (model.getNodes().get(i).getNumber() == nodeNumber) {

				undoList.add(model.getNodes().get(i).getNumber());
				undoList.add(model.getNodes().get(i).getX());
				undoList.add(model.getNodes().get(i).getY());

				model.getNodes().get(i).setX(xLocation);
				model.getNodes().get(i).setY(yLocation);

				controller.getMoveUndoMap().put(controller.getMoveRedoCounter(), undoList);

				controller.getMoveRedoMap().remove(controller.getMoveRedoCounter());

				controller.setMoveCounter(controller.getMoveCounter()+1);
				controller.setMoveRedoCounter(controller.getMoveRedoCounter()-1);

			}

		}
		
		/*
		 * Move any connected arcs with the node to new position
		 */
		for (int i = 0; i < model.getArcs().size(); i++) {

			model.getArcs().get(i).setX1(model.getNodes().get(model.getArcs().get(i).getInitNode()).getX() + 12);
			model.getArcs().get(i).setY1(model.getNodes().get(model.getArcs().get(i).getInitNode()).getY() + 12);
			model.getArcs().get(i).setX2(model.getNodes().get(model.getArcs().get(i).getEndNode()).getX() + 12);
			model.getArcs().get(i).setY2(model.getNodes().get(model.getArcs().get(i).getEndNode()).getY() + 12);
			
			if (model.getArcs().get(i).getX2() > model.getArcs().get(i).getX1()) {

				model.getArcs().get(i).setX1(model.getNodes().get(model.getArcs().get(i).getInitNode()).getX() + 24);

			} else if (model.getArcs().get(i).getX2() < model.getArcs().get(i).getX1()) {

				model.getArcs().get(i).setX1(model.getNodes().get(model.getArcs().get(i).getInitNode()).getX());

			} else {

				model.getArcs().get(i).setX1(model.getNodes().get(model.getArcs().get(i).getInitNode()).getX() - 12);

			}
			
			if (model.getArcs().get(i).getX2() > model.getArcs().get(i).getX1()) {

				model.getArcs().get(i).setX2(model.getNodes().get(model.getArcs().get(i).getEndNode()).getX());

			} else if (model.getArcs().get(i).getX2() < model.getArcs().get(i).getX1()) {

				model.getArcs().get(i).setX2(model.getNodes().get(model.getArcs().get(i).getEndNode()).getX() + 24);

			} else {

				model.getArcs().get(i).setX2(model.getNodes().get(model.getArcs().get(i).getEndNode()).getX() + 12);

			}
			

		}

		System.out.println("Move - Redo!");

	}

	/**
	 * Undo action of moving node.
	 */
	@Override
	public void undo() {

		int nodeNumber;
		int xLocation;
		int yLocation;
		
		/*
		 * Set up the variables to move the node to the previous position.
		 */
		nodeNumber = controller.getMoveUndoMap().get(controller.getMoveCounter() - 1).get(0);
		xLocation = controller.getMoveUndoMap().get(controller.getMoveCounter() - 1).get(1);
		yLocation = controller.getMoveUndoMap().get(controller.getMoveCounter() - 1).get(2);

		/*
		 * Move the node to the previous position
		 */
		for (int i = 0; i < model.getNodes().size(); i++) {

			List<Integer> redoList = new ArrayList<>();

			if (model.getNodes().get(i).getNumber() == nodeNumber) {

				redoList.add(model.getNodes().get(i).getNumber());
				redoList.add(model.getNodes().get(i).getX());
				redoList.add(model.getNodes().get(i).getY());

				model.getNodes().get(i).setX(xLocation);
				model.getNodes().get(i).setY(yLocation);

				controller.getMoveRedoMap().put(controller.getMoveRedoCounter(), redoList);

				controller.getMoveUndoMap().remove(controller.getMoveCounter());
				controller.setMoveRedoCounter(controller.getMoveRedoCounter()+1);
				controller.setMoveCounter(controller.getMoveCounter()-1);

			}

		}

		/*
		 * Move any connected arcs with the node to new position
		 */
		for (int i = 0; i < model.getArcs().size(); i++) {

			model.getArcs().get(i).setX1(model.getNodes().get(model.getArcs().get(i).getInitNode()).getX() + 12);
			model.getArcs().get(i).setY1(model.getNodes().get(model.getArcs().get(i).getInitNode()).getY() + 12);
			model.getArcs().get(i).setX2(model.getNodes().get(model.getArcs().get(i).getEndNode()).getX() + 12);
			model.getArcs().get(i).setY2(model.getNodes().get(model.getArcs().get(i).getEndNode()).getY() + 12);
			
			if (model.getArcs().get(i).getX2() > model.getArcs().get(i).getX1()) {

				model.getArcs().get(i).setX1(model.getNodes().get(model.getArcs().get(i).getInitNode()).getX() + 24);

			} else if (model.getArcs().get(i).getX2() < model.getArcs().get(i).getX1()) {

				model.getArcs().get(i).setX1(model.getNodes().get(model.getArcs().get(i).getInitNode()).getX());

			} else {

				model.getArcs().get(i).setX1(model.getNodes().get(model.getArcs().get(i).getInitNode()).getX() - 12);

			}
			
			if (model.getArcs().get(i).getX2() > model.getArcs().get(i).getX1()) {

				model.getArcs().get(i).setX2(model.getNodes().get(model.getArcs().get(i).getEndNode()).getX());

			} else if (model.getArcs().get(i).getX2() < model.getArcs().get(i).getX1()) {

				model.getArcs().get(i).setX2(model.getNodes().get(model.getArcs().get(i).getEndNode()).getX() + 24);

			} else {

				model.getArcs().get(i).setX2(model.getNodes().get(model.getArcs().get(i).getEndNode()).getX() + 12);

			}
			

		}
		
		System.out.println("Move - Undo!");

	}

}
