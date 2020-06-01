package adapters;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.company.Arc;
import com.company.MainController;
import com.company.MainModel;
import com.company.MainView;
import com.company.Node;

public class GetInfoMouseAdapter extends MouseAdapter {
	
	private MainModel model;
	private MainView view;
	private MainController controller;
	
	public GetInfoMouseAdapter(MainModel m, MainView v, MainController c) {
		
		this.model = m;
		this.view = v;
		this.controller = c;
		
	}

	@Override
	public void mousePressed(MouseEvent e) {

		if (controller.getActivateGetInfo() == 1) {

			for (int i = 0; i < model.getArcs().size(); i++) {

				Arc arc = model.getArcs().get(i);

				if (distance(arc.getX1(), arc.getY1(), e.getX(), e.getY())
						+ distance(arc.getX2(), arc.getY2(), e.getX(),
								e.getY()) < distance(arc.getX1(), arc.getY1(), arc.getX2(), arc.getY2()) * 1.002) {

					System.out.println("X1 = " + arc.getX1() + ", Y1 = " + arc.getY1());
					System.out.println("X2 = " + arc.getX2() + ", Y2 = " + arc.getY2());
					System.out.println("InitNode = " + arc.getInitNode() + ", EndNode = " + arc.getEndNode());
					
					System.out.println("Number = " + arc.getNumber());
				}

			}

			for (int i = 0; i < model.getNodes().size(); i++) {
				int x = model.getNodes().get(i).getX() + 12;
				int y = model.getNodes().get(i).getY() + 12;
				int radius = model.getNodes().get(i).getDiameter() / 2;
				Node node = model.getNodes().get(i);

				if (Math.pow(x - e.getX(), 2) + Math.pow(y - e.getY(), 2) <= Math.pow(radius, 2)) {

					System.out.println("X = " + node.getX() + ", Y = " + node.getY());
					System.out.println("Diameter = " + node.getDiameter() + ", Name = " + node.getName());
					System.out.println("Number = " + node.getNumber());
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
