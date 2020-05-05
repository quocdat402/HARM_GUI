package com.company;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class NodeInfoMouseAdapter extends MouseAdapter {

	@Override
	public void mousePressed(MouseEvent e) {

		for (int i = 0; i < MainController.getView().getNodes().size(); i++) {
			int x = MainController.getView().getNodes().get(i).getX() + 12;
			int y = MainController.getView().getNodes().get(i).getY() + 12;
			int radius = MainController.getView().getNodes().get(i).getDiameter() / 2;

			if (Math.pow(x - e.getX(), 2) + Math.pow(y - e.getY(), 2) <= Math.pow(radius, 2)) {

				if (e.isPopupTrigger()) {
					MainController.getView().getNodePopUp().show(e.getComponent(), e.getX(), e.getY());
					
					MainController.setNodePropertyInt(i);

				}
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {

		for (int i = 0; i < MainController.getView().getNodes().size(); i++) {

			int x = MainController.getView().getNodes().get(i).getX() + 12;

			int y = MainController.getView().getNodes().get(i).getY() + 12;

			int radius = MainController.getView().getNodes().get(i).getDiameter() / 2;

			if (Math.pow(x - e.getX(), 2) + Math.pow(y - e.getY(), 2) <= Math.pow(radius, 2)) {

				if (e.isPopupTrigger()) {

					MainController.getView().getNodePopUp().show(e.getComponent(), e.getX(), e.getY());

					MainController.setNodePropertyInt(i);
				}
			}
		}
	}
}
