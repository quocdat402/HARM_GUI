package adapters;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

import com.company.Arc;
import com.company.MainController;
import com.company.MainModel;
import com.company.MainView;

import undoredoStack.Command;


public class ArcMouseAdapter extends MouseAdapter implements Command{
	
	private int initNode;
	private int endNode;
	private int x1, y1, x2, y2;	
	private int currentArcNumber = 0;
	
	//Temporary Arcs for redo and undo
	Arc ArcRedo = null;
	Arc arcTemp = null;
	
	private MainModel model;
	private MainView view;
	private MainController controller;
	
	public ArcMouseAdapter(MainModel m, MainView v, MainController c) {
		
		this.model = m;
		this.view = v;
		this.controller = c;
		
	}
	
	/**
	 * Detect that user's mouse click is on the node
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		
		/*
		 * Check that mouse left button is clicked
		 */
		if(SwingUtilities.isLeftMouseButton(e)) {
		
		if (controller.getActivateArc() == 1 && !model.getNodes().isEmpty()) {
				
				for (int i = 0; i < model.getNodes().size(); i++) {
					
					
					int x = model.getNodes().get(i).getX() + 12;
					int y = model.getNodes().get(i).getY() + 12;
					int radius = model.getNodes().get(i).getDiameter() / 2;
					
					/*
					 * Check that user's mouse pointer is on any one of the Node the centrepane
					 */
					if (Math.pow(x - e.getX(), 2) + Math.pow(y - e.getY(), 2) <= Math.pow(radius, 2)) {

						x1 = x;
						y1 = y;
						view.getCenterPanel().repaint();
						initNode = i;
						
						/*
						 * Create a temporary arc for undo and redo function
						 */
						arcTemp = new Arc(x1, y1, x1, y1, Color.black, initNode, endNode, controller.getArcNumber(),0, 0, 0, 0, 0);
						model.getArcs().add(arcTemp);
					}
				}
			}
		
		}
		
	}
	
	/**
	 * While the user is dragging the mouse, the arc is following user's moouse pointer
	 */
	@Override
	public void mouseDragged(MouseEvent e)  {
		
		/*
		 * Check that mouse left button is clicked
		 */
		if(SwingUtilities.isLeftMouseButton(e)) {
		
		if (controller.getActivateArc() == 1 && !model.getNodes().isEmpty() && arcTemp != null) {
			
				arcTemp.setX2(e.getX());
				arcTemp.setY2(e.getY());					
				view.getCenterPanel().repaint();				
		}
		
		
		}
	}
	
	/**
	 * When the user's mouse click is released, new arc is created
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		/*
		 * Check that mouse left button is clicked
		 */
		if(SwingUtilities.isLeftMouseButton(e)) {
		if (controller.getActivateArc() == 1 && !model.getNodes().isEmpty()) {
				for (int i = 0; i < model.getNodes().size(); i++) {
					int x = model.getNodes().get(i).getX() + 12;
					int y = model.getNodes().get(i).getY() + 12;
					int radius = model.getNodes().get(i).getDiameter() / 2;
					/*
					 * Check that user's mouse pointer is on any one of the Node the centrepane
					 */
					if (Math.pow(x - e.getX(), 2) + Math.pow(y - e.getY(), 2) <= Math.pow(radius, 2)) {
						x2 = x;						
						if (x2 < x1) {
							x2 = x + 12;
							x1 = x1 - 12;
						} else if (x2 > x1) {
							x2 = x - 12;
							x1 = x1 + 12;
						} else {
							x2 = x;
						}						
						y2 = y;
						endNode = i;						
						model.getArcs().remove(arcTemp);
						/*
						 * no arc is created on same node
						 */
						if(initNode == endNode) {	
							throw new UnsupportedOperationException();	
						} else {			
							/*
							 * Create a new arc
							 */
							Arc arc = new Arc(x1, y1, x2, y2, Color.black, initNode, endNode, controller.getArcNumber(), 0, 0, 0, 0, 0);
							model.getArcs().add(arc);
							currentArcNumber++;
							controller.setArcNumber(controller.getArcNumber() + 1);
							view.getCenterPanel().repaint();
							/*
							 * Add adding arc command into stack for undo and redo action
							 */
							controller.getStack().doCommand(new ArcMouseAdapter(model, view, controller));
						}						
					} else {
						model.getArcs().remove(arcTemp);
						view.getCenterPanel().repaint();
					}
				}
		}
		}
	}

	/**
	 * Redo action of adding arc.
	 */
	@Override
	public void execute() {
		model.getArcs().add(ArcRedo);
		currentArcNumber++; // Update the currentArcNumber
		System.out.println("Add Arc! - Undo");
	}

	/**
	 * Undo action of adding arc.
	 */
	@Override
	public void undo() {
		int undoArcIndex = model.getArcs().size() - 1;
		ArcRedo = model.getArcs().get(undoArcIndex);
		model.getArcs().remove(undoArcIndex);
		currentArcNumber--; // Update the currentArcNumber
		System.out.println("Delete Arc! - Redo");
	}
}