package adapters;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

import com.company.Arc;
import com.company.MainController;
import com.company.MainModel;
import com.company.MainView;
import com.company.Node;

import undoredoStack.Command;


public class ArcMouseAdapter extends MouseAdapter implements Command{
	
	private int initNode;
	private int endNode;
	private int x1, y1, x2, y2;	
	private Node startNode;
	private Arc tempArc;
	
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
		if (controller.getActivateArc() == 1 && !model.getNodes().isEmpty()) {
			// Find the nearest node to the mouse click position
			startNode = findNearestNode(e.getPoint());
			if (startNode != null) {
				// Set the starting coordinates of the arc to the center of the startNode
				int x1 = startNode.getX() + startNode.getDiameter() / 2;
				int y1 = startNode.getY() + startNode.getDiameter() / 2;
				// Initialize the temporary arc
				tempArc = new Arc(x1, y1, x1, y1, Color.BLACK, startNode.getNumber(), -1, controller.getArcNumber(), 0, 0, 0, 0, 0);
			}
		}
	}
	
	/**
	 * While the user is dragging the mouse, the arc is following user's mouse pointer
	 */
	@Override
	public void mouseDragged(MouseEvent e) {
		if (controller.getActivateArc() == 1 && !model.getNodes().isEmpty() && tempArc != null) {
			// Update the ending coordinates of the temporary arc to the current mouse position
			tempArc.setX2(e.getX());
			tempArc.setY2(e.getY());
			view.getCenterPanel().repaint();
		}
	}
	
	/**
	 * When the user's mouse click is released, new arc is created
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		if (controller.getActivateArc() == 1 && !model.getNodes().isEmpty() && tempArc != null) {
			// Find the nearest node to the mouse release position
			Node endNode = findNearestNode(e.getPoint());
			if (endNode != null && endNode != startNode) {
				// Check if an arc already exists between the start and end nodes
				if (!isArcExistsBetweenNodes(startNode, endNode)) {
					// Set the ending coordinates of the arc to the center of the endNode
					int x2 = endNode.getX() + endNode.getDiameter() / 2;
					int y2 = endNode.getY() + endNode.getDiameter() / 2;
					tempArc.setX2(x2);
					tempArc.setY2(y2);
					tempArc.setEndNode(endNode.getNumber());
					// Add the arc to the model
					model.getArcs().add(tempArc);
					controller.setArcNumber(controller.getArcNumber() + 1);
					// Add the arc creation command to the stack for undo/redo
					controller.getStack().doCommand(new ArcMouseAdapter(model, view, controller));
				} else {
					// If an arc already exists, remove the temporary arc
					tempArc = null;
				}
			} else {
				// If no valid end node is found, remove the temporary arc
				tempArc = null;
			}
			startNode = null;
			view.getCenterPanel().repaint();
		}
	}

	private boolean isArcExistsBetweenNodes(Node startNode, Node endNode) {
		for (Arc arc : model.getArcs()) {
			if ((arc.getInitNode() == startNode.getNumber() && arc.getEndNode() == endNode.getNumber())
					|| (arc.getInitNode() == endNode.getNumber() && arc.getEndNode() == startNode.getNumber())) {
				return true;
			}
		}
		return false;
	}

	private Node findNearestNode(java.awt.Point point) {
		double minDistance = Double.MAX_VALUE;
		Node nearestNode = null;
	
		for (Node node : model.getNodes()) {
			double distance = calculateDistance(point, node);
			if (distance < minDistance) {
				minDistance = distance;
				nearestNode = node;
			}
		}
	
		// Use a larger selection threshold
		if (minDistance <= Node.SELECTION_THRESHOLD * 2) {
			return nearestNode;
		} else {
			return null;
		}
	}
	
	private double calculateDistance(java.awt.Point point, Node node) {
		int x = node.getX() + node.getDiameter() / 2;
		int y = node.getY() + node.getDiameter() / 2;
		return Math.sqrt(Math.pow(point.x - x, 2) + Math.pow(point.y - y, 2));
	}
	/**
	 * Redo action of adding arc.
	 */
	@Override
	public void execute() {

		model.getArcs().add(ArcRedo);
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
		System.out.println("Delete Arc! - Redo");
	}

}

