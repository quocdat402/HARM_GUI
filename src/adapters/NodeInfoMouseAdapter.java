package adapters;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;

import com.company.Arc;
import com.company.MainController;
import com.company.MainModel;
import com.company.MainView;
import com.company.Node;

public class NodeInfoMouseAdapter extends MouseAdapter {
	
	private MainModel model;
	private MainView view;
	private MainController controller;
	private JMenuItem nodeDelete;
	private JMenuItem arcDelete;
	
	public NodeInfoMouseAdapter(MainModel m, MainView v, MainController c) {
		
		this.model = m;
		this.view = v;
		this.controller = c;

        nodeDelete = new JMenuItem("Delete");
        nodeDelete.addActionListener(e -> deleteNode());
        view.getNodePopUp().add(nodeDelete);

		arcDelete = new JMenuItem("Delete");
        arcDelete.addActionListener(e -> deleteArc());
        view.getArcPopUp().add(arcDelete);
	}

	private void deleteNode() {
        // Code to delete the selected node
        int nodeIndex = controller.getNodePropertyInt();
        Node nodeToDelete = model.getNodes().get(nodeIndex);

        // Remove the node from the model
        model.getNodes().remove(nodeToDelete);

        // Remove any arcs connected to the deleted node
        model.getArcs().removeIf(arc -> arc.getInitNode() == nodeIndex || arc.getEndNode() == nodeIndex);

        // Update the view
        view.getCenterPanel().repaint();
    }

	private void deleteArc() {
        // Code to delete the selected arc
        int arcIndex = controller.getArcPropertyInt();
        Arc arcToDelete = model.getArcs().get(arcIndex);

        // Remove the arc from the model
        model.getArcs().remove(arcToDelete);

        // Update the view
        view.getCenterPanel().repaint();
    }
	
	/**
	 * Show the pop-up menu when the mouse click is pressed and released
	 */
	@Override
	public void mousePressed(MouseEvent e) {

		for (int i = 0; i <model.getNodes().size(); i++) {
			int x = model.getNodes().get(i).getX() + 12;
			int y = model.getNodes().get(i).getY() + 12;
			int radius = model.getNodes().get(i).getDiameter() / 2;

			/*
			 * Check that user's mouse pointer is on any one of the node
			 */
			if (Math.pow(x - e.getX(), 2) + Math.pow(y - e.getY(), 2) <= Math.pow(radius, 2)) {

				if (model.getNodes().get(i).isAttacker()) {
					view.getNodeAttacker().setText("Unset from the attacker");
				} else {
					view.getNodeAttacker().setText("Set as the attacker");					
				}				
				if (model.getNodes().get(i).isTarget()) {					
					view.getNodeTarget().setText("Unset from the target");					
				} else {					
					view.getNodeTarget().setText("Set as the target");					
				}			
				if (e.isPopupTrigger()) {									
					
					controller.getView().getNodePopUp().show(e.getComponent(), e.getX(), e.getY());
					
					controller.setNodePropertyInt(i);

				}
			}
		}
	}
	
	/**
	 * Show the pop-up menu when the mouse click is pressed and released
	 */
	@Override
	public void mouseReleased(MouseEvent e) {

		for (int i = 0; i < model.getNodes().size(); i++) {

			int x = model.getNodes().get(i).getX() + 12;

			int y = model.getNodes().get(i).getY() + 12;

			int radius = model.getNodes().get(i).getDiameter() / 2;

			/*
			 * Check that user's mouse pointer is on any one of the node
			 */
			if (Math.pow(x - e.getX(), 2) + Math.pow(y - e.getY(), 2) <= Math.pow(radius, 2)) {

				if (e.isPopupTrigger()) {

					controller.getView().getNodePopUp().show(e.getComponent(), e.getX(), e.getY());

					controller.setNodePropertyInt(i);
				}
			}
		}
	}
}
