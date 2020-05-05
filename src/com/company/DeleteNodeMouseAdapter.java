package com.company;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DeleteNodeMouseAdapter extends MouseAdapter implements Command {

	@Override
	public void mousePressed(MouseEvent e) {

		MainController.setDeleteNodesArcsCounter(0);
		
		if (MainController.getActivateDelete() == 1) {

			
			
			for (int i = 0; i < MainController.getView().getNodes().size(); i++) {

				int x = MainController.getView().getNodes().get(i).getX() + 12;

				int y = MainController.getView().getNodes().get(i).getY() + 12;

				int radius = MainController.getView().getNodes().get(i).getDiameter() / 2;

				if (Math.pow(x - e.getX(), 2) + Math.pow(y - e.getY(), 2) <= Math.pow(radius, 2)) {

					int j = 0;
					
					while (j < MainController.getView().getArcs().size()) {

						if (MainController.getView().getNodes().get(i).getNumber() == MainController.getView().getArcs().get(j)
								.getEndNode()) {

							MainController.getDeleteNodesArcs().add(MainController.getView().getArcs().get(j));
							
							MainController.getView().getArcs().remove(j);
							
							MainController.setDeleteNodesArcsCounter(MainController.getDeleteNodesArcsCounter()+1);
							
							MainController.setArcNumber(MainController.getArcNumber() - 1);

							MainController.getView().getCenterPanel().repaint();

						} else {

							j++;
						}

					}

					int k = 0;
					while (k < MainController.getView().getArcs().size()) {

						if (MainController.getView().getNodes().get(i).getNumber() == MainController.getView().getArcs().get(k)
								.getInitNode()) {

							MainController.getDeleteNodesArcs().add(MainController.getView().getArcs().get(k));
							
							MainController.getView().getArcs().remove(k);
							
							MainController.setDeleteNodesArcsCounter(MainController.getDeleteNodesArcsCounter()+1);
							
							MainController.setArcNumber(MainController.getArcNumber() - 1);

							MainController.getView().getCenterPanel().repaint();

						} else {

							k++;
						}

					}
					
					MainController.getDeleteNodes().add(MainController.getView().getNodes().get(i));
					
					MainController.getView().getNodes().remove(i);

					MainController.setNodeNumber(MainController.getNodeNumber() - 1);
					
					MainController.getView().getCenterPanel().repaint();

					MainController.getStack().doCommand(new DeleteNodeMouseAdapter());

				}
			}
			MainController.getCounterList().add(MainController.getDeleteNodesArcsCounter());
		}
		
	}

	@Override
	public void execute() {
		
		Node node = MainController.getDeleteNodesRedo().get(MainController.getDeleteNodesRedo().size() - 1);
		
		MainController.getDeleteNodes().add(node);
		
		MainController.getView().getNodes().remove(node);
		
		MainController.getDeleteNodesRedo().remove(node);
		
		System.out.println("Delete Node - Delete Redo!");

	}

	@Override
	public void undo() {
		
		int count = MainController.getCounterList().get(0);
	
		System.out.println(MainController.getCounterList());

		Node node = MainController.getDeleteNodes().get(MainController.getDeleteNodes().size() - 1);
		
		MainController.getDeleteNodesRedo().add(node);
		
		MainController.getView().getNodes().add(node.getNumber(), node);
		
		MainController.getDeleteNodes().remove(node);

		for(int i = 1; i < count + 1; i++) {
			
			Arc arc = MainController.getDeleteNodesArcs().get( MainController.getDeleteNodesArcs().size() - i);
			
			MainController.getView().getArcs().add(arc);
			
			
		}
		
		for(int i = 0; i < count; i++) {
			
			MainController.getDeleteNodesArcs().remove(i);
			
			
		}
		
		
		System.out.println("Add Node - Delete Undo!");

	}

}
