package adapters;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import com.company.Arc;
import com.company.MainController;
import com.company.MainModel;
import com.company.MainView;

import undoredoStack.Command;

public class DeleteArcMouseAdapter extends MouseAdapter implements Command{
	
	private MainModel model;
	private MainView view;
	private MainController controller;
	
	public DeleteArcMouseAdapter(MainModel m, MainView v, MainController c) {
		
		this.model = m;
		this.view = v;
		this.controller = c;
		
	}

	
	/**
	 * Delete a selected Arc when the mouse is pressed
	 */
	@Override
	public void mousePressed(MouseEvent e) {

		if (controller.getActivateDelete() == 1) {

			if (e.getButton() == MouseEvent.BUTTON1) {
				
				
				for (int i = 0; i < model.getArcs().size(); i++) {

					Arc arc = model.getArcs().get(i);
					
					/*
					 * Check that user's mouse pointer is on any one of the arc on the centrepane
					 */
					if (distance(arc.getX1(), arc.getY1(), e.getX(), e.getY()) + distance(arc.getX2(), arc.getY2(),
							e.getX(), e.getY()) < distance(arc.getX1(), arc.getY1(), arc.getX2(), arc.getY2())
									* 1.002) {

						controller.getDeleteArcs().add(arc);
						controller.setDeleteArc(arc);
						
						/*
						 * Delete a selected arc
						 */
						model.getArcs().remove(i);
						controller.setArcNumber(controller.getArcNumber() - 1);
						controller.getView().getCenterPanel().repaint();
						
						/*
						 * Add deleting arc command into stack for undo and redo action
						 */
						controller.getStack().doCommand(new DeleteArcMouseAdapter(model, view, controller));
					}
				}
			}
		}
	}

	/**
	 * Redo action of deleting arc
	 */
	@Override
	public void execute() {		
		
		/*
		 * Create a temporary arc for redo
		 */
		Arc arc = controller.getDeleteArcsRedo().get(controller.getDeleteArcsRedo().size() -1);
		
		/*
		 * Delete a recently added arc from List<Arc> array
		 */
		model.getArcs().remove(arc);
		
		controller.getDeleteArcs().add(arc);
		
		controller.getDeleteArcsRedo().remove(arc);
		
		System.out.println("Delete - Redo!");
		
	}

	/**
	 * Undo action of deleting arc.
	 */
	@Override
	public void undo() {
		
		/*
		 * Create a temporarily arc for undo
		 */
		Arc arc = controller.getDeleteArcs().get(controller.getDeleteArcs().size() - 1);
		
		/*
		 * Add a deleted arc into List<Arc> array
		 */
		model.getArcs().add(arc.getNumber(), arc);
		
		controller.getDeleteArcsRedo().add(arc);
		
		controller.getDeleteArcs().remove(arc);
		
		System.out.println("Delete - Undo!");			
	}
	
	/**
	 * Calculate distance between two points (x1, y1) and (x2, y2)
	 */
	public double distance(int x1, int y1, int x2, int y2) {
		return Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
	}
	
}
