package adapters;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.company.MainController;
import com.company.MainModel;
import com.company.MainView;

public class NodeInfoMouseAdapter extends MouseAdapter {
	
	private MainModel model;
	private MainView view;
	private MainController controller;
	
	public NodeInfoMouseAdapter(MainModel m, MainView v, MainController c) {
		
		this.model = m;
		this.view = v;
		this.controller = c;
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
