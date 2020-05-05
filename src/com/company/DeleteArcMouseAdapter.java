package com.company;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

//Fix undo Problem
public class DeleteArcMouseAdapter extends MouseAdapter implements Command{

	
	
	@Override
	public void mousePressed(MouseEvent e) {

		

		if (MainController.getActivateDelete() == 1) {

			if (e.getButton() == MouseEvent.BUTTON1) {

				// line detection
				for (int i = 0; i < MainController.getView().getArcs().size(); i++) {

					Arc arc = MainController.getView().getArcs().get(i);

					if (distance(arc.getX1(), arc.getY1(), e.getX(), e.getY()) + distance(arc.getX2(), arc.getY2(),
							e.getX(), e.getY()) < distance(arc.getX1(), arc.getY1(), arc.getX2(), arc.getY2())
									* 1.002) {

						MainController.getDeleteArcs().add(arc);
						MainController.setDeleteArc(arc);
						
						MainController.getView().getArcs().remove(i);
						MainController.setArcNumber(MainController.getArcNumber() - 1);
						MainController.getView().getCenterPanel().repaint();
						
						MainController.getStack().doCommand(new DeleteArcMouseAdapter());
					}
				}
			}
		}
	}

	@Override
	public void execute() {		
		
		Arc arc = MainController.getDeleteArcsRedo().get(MainController.getDeleteArcsRedo().size() -1);
		
		MainController.getView().getArcs().remove(arc);
		
		MainController.getDeleteArcs().add(arc);
		
		MainController.getDeleteArcsRedo().remove(arc);
		
		for(Arc arc1 : MainController.getDeleteArcsRedo()) {
			
			System.out.println(arc1.getNumber());
			
		}
		
		System.out.println("Delete - Redo!");
		
	}

	@Override
	public void undo() {
		
		Arc arc = MainController.getDeleteArcs().get(MainController.getDeleteArcs().size() - 1);
		
		MainController.getView().getArcs().add(arc.getNumber(), arc);
		
		MainController.getDeleteArcsRedo().add(arc);
		
		MainController.getDeleteArcs().remove(arc);
		
		for(Arc arc1 : MainController.getDeleteArcs()) {
			
			System.out.println(arc1.getNumber());
			
		}
		
		System.out.println("Delete - Undo!");			
	}
	
	public double distance(int x1, int y1, int x2, int y2) {
		return Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
	}
	
}
