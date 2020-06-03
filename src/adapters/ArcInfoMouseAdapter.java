package adapters;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.company.Arc;
import com.company.MainController;
import com.company.MainModel;
import com.company.MainView;

public class ArcInfoMouseAdapter extends MouseAdapter {
	
	private MainModel model;
	private MainView view;
	private MainController controller;
	
	/**
	 * Initialize ArcInfoMouseAdapter
	 */
	public ArcInfoMouseAdapter(MainModel m, MainView v, MainController c) {
		
		this.model = m;
		this.view = v;		
		this.controller = c;
		
	}
	
	/**
	 * Show the pop-up menu when the mouse click is pressed and released
	 */
	@Override
	public void mousePressed(MouseEvent e) {

		for (int i = 0; i < model.getArcs().size(); i++) {

			Arc arc = model.getArcs().get(i);
			
			/*
			 * Check that user's mouse pointer is on any one of the arc the centrepane
			 */
			if (distance(arc.getX1(), arc.getY1(), e.getX(), e.getY()) + distance(arc.getX2(), arc.getY2(),
					e.getX(), e.getY()) < distance(arc.getX1(), arc.getY1(), arc.getX2(), arc.getY2()) * 1.002) {

				if (e.isPopupTrigger()) {
					view.getArcPopUp().show(e.getComponent(), e.getX(), e.getY());					
					controller.setArcPropertyInt(i);
				}

			}

		}

	}

	/**
	 * Show the pop-up menu when the mouse click is pressed and released
	 */
	@Override
	public void mouseReleased(MouseEvent e) {

		for (int i = 0; i < model.getArcs().size(); i++) {

			Arc arc = model.getArcs().get(i);

			/*
			 * Check that user's mouse pointer is on any one of the arc the centrepane
			 */
			if (distance(arc.getX1(), arc.getY1(), e.getX(), e.getY()) + distance(arc.getX2(), arc.getY2(),
					e.getX(), e.getY()) < distance(arc.getX1(), arc.getY1(), arc.getX2(), arc.getY2()) * 1.002) {

				if (e.isPopupTrigger()) {
					view.getArcPopUp().show(e.getComponent(), e.getX(), e.getY());
					controller.setArcPropertyInt(i);
				}
			}
		}
	}
	
	/**
	 * Calculate distance between two points (x1, y1) and (x2, y2)
	 */
	public double distance(int x1, int y1, int x2, int y2) {
		return Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
	}

}
