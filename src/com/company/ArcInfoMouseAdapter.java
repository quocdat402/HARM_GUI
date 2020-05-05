package com.company;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ArcInfoMouseAdapter extends MouseAdapter {
	
	@Override
	public void mousePressed(MouseEvent e) {

		for (int i = 0; i < MainController.getView().getArcs().size(); i++) {

			Arc arc = MainController.getView().getArcs().get(i);

			if (distance(arc.getX1(), arc.getY1(), e.getX(), e.getY()) + distance(arc.getX2(), arc.getY2(),
					e.getX(), e.getY()) < distance(arc.getX1(), arc.getY1(), arc.getX2(), arc.getY2()) * 1.002) {

				if (e.isPopupTrigger()) {
					MainController.getView().getArcPopUp().show(e.getComponent(), e.getX(), e.getY());
					
					MainController.setArcPropertyInt(i);
				}

			}

		}

	}

	@Override
	public void mouseReleased(MouseEvent e) {

		for (int i = 0; i < MainController.getView().getArcs().size(); i++) {

			Arc arc = MainController.getView().getArcs().get(i);

			if (distance(arc.getX1(), arc.getY1(), e.getX(), e.getY()) + distance(arc.getX2(), arc.getY2(),
					e.getX(), e.getY()) < distance(arc.getX1(), arc.getY1(), arc.getX2(), arc.getY2()) * 1.002) {

				if (e.isPopupTrigger()) {
					MainController.getView().getArcPopUp().show(e.getComponent(), e.getX(), e.getY());
					MainController.setArcPropertyInt(i);
				}
			}
		}
	}
	
	public double distance(int x1, int y1, int x2, int y2) {
		return Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
	}

}
