package adapters;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.company.MainController;
import com.company.MainModel;
import com.company.MainView;
import com.company.Node;

import undoredoStack.Command;


public class NodeMouseAdapter extends MouseAdapter implements Command{
	
	private Node nodeRedo = null;
	private MainModel model;
	private MainView view;
	private MainController controller;
	
	public NodeMouseAdapter(MainModel m, MainView v, MainController c) {
		
		this.model = m;
		this.view = v;
		this.controller = c;
		
	}

	/**
	 * Create a node on the centrepane when the mouse is pressed
	 */
	@Override
	public void mousePressed(MouseEvent e) {

		if (controller.getActivateNode() == 1) {

			if (e.getButton() == MouseEvent.BUTTON1) {
				/*
				 * Create a node and add it to List<Node>
				 */
				
				if (e.getX() < 0 || e.getY() < 0) {
					
					throw new IllegalStateException("Can't create a Node");
					
				}
				
				Node node = new Node(e.getX(), e.getY(), 24, Color.white, "node " + controller.getNodeNumber(), controller.getNodeNumber(), false,
						false);
				model.getNodes().add(node);
				view.getCenterPanel().repaint();
				controller.setNodeNumber(controller.getNodeNumber() + 1);
				
				/*
				 * Add adding node command into stack for undo and redo action
				 */
				controller.getStack().doCommand(new NodeMouseAdapter(model, view, controller));

			}
		}
	}

	/**
	 * Redo action of adding node
	 */
	@Override
	public void execute() {
		model.getNodes().add(nodeRedo);

		// Reset the node numbers and names
		for (int i = 0; i < model.getNodes().size(); i++) {
			Node node = model.getNodes().get(i);
			node.setNumber(i);
			if (node.isAttacker() || node.isTarget()) {
				node.setName(node.isAttacker() ? "Attacker" : "Target");
			} else {
				node.setName("node " + i);
			}
		}

		// Reset the arc numbers
		for (int i = 0; i < model.getArcs().size(); i++) {
			model.getArcs().get(i).setNumber(i);
		}

		controller.setNodeNumber(controller.getNodeNumber() + 1);
		System.out.println("Add Node - Redo!");
		view.getCenterPanel().repaint(); // Repaint the panel to reflect the changes
	}

	/**
	 * Undo action of adding node
	 */
	@Override
	public void undo() {
		int undoNodeIndex = model.getNodes().size() - 1;
		nodeRedo = model.getNodes().get(undoNodeIndex);
		model.getNodes().remove(undoNodeIndex);

		// Reset the node numbers and names
		for (int i = 0; i < model.getNodes().size(); i++) {
			Node node = model.getNodes().get(i);
			node.setNumber(i);
			if (node.isAttacker() || node.isTarget()) {
				node.setName(node.isAttacker() ? "Attacker" : "Target");
			} else {
				node.setName("node " + i);
			}
		}

		// Reset the arc numbers
		for (int i = 0; i < model.getArcs().size(); i++) {
			model.getArcs().get(i).setNumber(i);
		}

		controller.setNodeNumber(controller.getNodeNumber() - 1);
		System.out.println("Remove Node - Undo!");
		view.getCenterPanel().repaint(); // Repaint the panel to reflect the changes
	}

}