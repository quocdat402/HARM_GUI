package com.company;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class ArcMouseAdapter extends MouseAdapter implements Command{
	
	private int initNode;
	private int endNode;
	private int x1, y1, x2, y2;
	private MainView view = MainController.getView();
	
	private int test = 0;
	
	Arc ArcRedo = null;
	Arc arcTemp;
	
	@Override
	public void mousePressed(MouseEvent e) {
		
		if (MainController.getActivateArc() == 1) {
				
				
//				System.out.println(e.getSource());
//				System.out.println(e.getWhen());
//				System.out.println(e.getID());
//				System.out.println(e.getModifiers());
//				System.out.println(e.getClickCount());
//				System.out.println(e.getX());
//				System.out.println(e.getY());
				
				for (int i = 0; i < view.getNodes().size(); i++) {
					
					
					int x = view.getNodes().get(i).getX() + 12;
					int y = view.getNodes().get(i).getY() + 12;
					int radius = view.getNodes().get(i).getDiameter() / 2;

					if (Math.pow(x - e.getX(), 2) + Math.pow(y - e.getY(), 2) <= Math.pow(radius, 2)) {

						x1 = x;
						y1 = y;
						view.getCenterPanel().repaint();
						initNode = i;
						
						arcTemp = new Arc(x1, y1, x1, y1, Color.black, initNode, endNode, 0, MainController.getArcNumber(), 0, 0, 0, 0);
						view.getArcs().add(arcTemp);
					}
				}
			}
		
	}
	
	@Override
	public void mouseDragged(MouseEvent e)  {
		
		if (MainController.getActivateArc() == 1) {
			
				arcTemp.setX2(e.getX());
				arcTemp.setY2(e.getY());					
				view.getCenterPanel().repaint();				
		}
		
		
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {

		if (MainController.getActivateArc() == 1) {

//				System.out.println(e.getSource());
//				System.out.println(e.getWhen());
//				System.out.println(e.getID());
//				System.out.println(e.getModifiers());
//				System.out.println(e.getClickCount());
//			System.out.println(e.getX());
//			System.out.println(e.getY());
			

				for (int i = 0; i < view.getNodes().size(); i++) {
					int x = view.getNodes().get(i).getX() + 12;
					int y = view.getNodes().get(i).getY() + 12;
					int radius = view.getNodes().get(i).getDiameter() / 2;

					if (Math.pow(x - e.getX(), 2) + Math.pow(y - e.getY(), 2) <= Math.pow(radius, 2)) {

						x2 = x;
						if (x2 < x1) {

							x2 = x + 12;
							x1 = x1 - 12;

						} else if (x2 > x1) {

							x2 = x - 12;
							x1 = x1 + 12;

						} else {

							x2 = x;

						}
						y2 = y;
						endNode = i;
						
						view.getArcs().remove(arcTemp);
						
						if(initNode == endNode) {
							
							
						} else {
							
							Arc arc = new Arc(x1, y1, x2, y2, Color.black, initNode, endNode, 0, MainController.getArcNumber(), 0, 0, 0, 0);
							view.getArcs().add(arc);

							view.getCenterPanel().repaint();
							MainController.setArcNumber(MainController.getArcNumber() + 1);
							

							MainController.getStack().doCommand(new ArcMouseAdapter());
							
						}
						
						

					} else {
						view.getArcs().remove(arcTemp);
						view.getCenterPanel().repaint();
					}
				}
			
		}
	}

	@Override
	public void execute() {

		view.getArcs().add(ArcRedo);
		System.out.println("Add Arc! - Undo");

	}

	@Override
	public void undo() {
		int undoArcIndex = view.getArcs().size() - 1;
		ArcRedo = view.getArcs().get(undoArcIndex);
		view.getArcs().remove(undoArcIndex);
		System.out.println("Delete Arc! - Redo");
	}

}


