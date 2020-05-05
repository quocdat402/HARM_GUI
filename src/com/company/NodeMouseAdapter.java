package com.company;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class NodeMouseAdapter extends MouseAdapter implements Command{
	
	Node nodeRedo = null;

	@Override
	public void mousePressed(MouseEvent e) {

		if (MainController.getActivateNode() == 1) {
			
//			System.out.println(e.getSource());
//			System.out.println(e.getWhen());
//			System.out.println(e.getID());
//			System.out.println(e.getModifiers());
//			System.out.println(e.getClickCount());
//			System.out.println(e.getX());
//			System.out.println(e.getY());
			
			if (e.getButton() == MouseEvent.BUTTON1) {
				Node node = new Node(e.getX(), e.getY(), 24, Color.white, "node " + MainController.getNodeNumber(), MainController.getNodeNumber(), false,
						false);
				
				MainController.getView().getNodes().add(node);
				MainController.getView().getCenterPanel().repaint();
				MainController.setNodeNumber(MainController.getNodeNumber() + 1);
				

				MainController.getStack().doCommand(new NodeMouseAdapter());

			}
		}
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		MainController.getView().getNodes().add(nodeRedo);
		System.out.println("Add Node - Redo!");
	}

	@Override
	public void undo() {
		// TODO Auto-generated method stub

		int undoNodeIndex = MainController.getView().getNodes().size() - 1;
		nodeRedo = MainController.getView().getNodes().get(undoNodeIndex);
		MainController.getView().getNodes().remove(undoNodeIndex);
		System.out.println("Remove Node - Undo!");
	}

}
