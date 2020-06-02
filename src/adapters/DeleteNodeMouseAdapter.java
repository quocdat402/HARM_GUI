package adapters;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.SwingUtilities;

import com.company.Arc;
import com.company.MainController;
import com.company.MainModel;
import com.company.MainView;
import com.company.Node;

import undoredoStack.Command;

public class DeleteNodeMouseAdapter extends MouseAdapter implements Command {
	
	private MainModel model;
	private MainView view;
	private MainController controller;
	
	public DeleteNodeMouseAdapter(MainModel m, MainView v, MainController c) {
		
		this.model = m;
		this.view = v;
		this.controller = c;
		
	}

	/**
	 * Delete a selected node and arcs connected to the node
	 */
	@Override
	public void mousePressed(MouseEvent e) {

		List<Integer> arcReset = new ArrayList<>();
		
		controller.setDeleteNodesArcsCounter(0);
		
		if(SwingUtilities.isLeftMouseButton(e)) {
		
		if (controller.getActivateDelete() == 1) {
			
			for (int i = 0; i < model.getNodes().size(); i++) {

				int x = model.getNodes().get(i).getX() + 12;

				int y = model.getNodes().get(i).getY() + 12;

				int radius = model.getNodes().get(i).getDiameter() / 2;
				
				/*
				 * Check that user's mouse pointer is on any one of the Node the centrepane
				 */
				if (Math.pow(x - e.getX(), 2) + Math.pow(y - e.getY(), 2) <= Math.pow(radius, 2)) {

					int j = 0;
					
					/*
					 * Check there is any connected arc to the node
					 */
					while (j < model.getArcs().size()) {

						if (model.getNodes().get(i).getNumber() == model.getArcs().get(j)
								.getEndNode()) {

							controller.getDeleteNodesArcs().add(model.getArcs().get(j));
							
							model.getArcs().remove(j);
							
							controller.setDeleteNodesArcsCounter(controller.getDeleteNodesArcsCounter()+1);
							
							controller.setArcNumber(controller.getArcNumber() - 1);

							controller.getView().getCenterPanel().repaint();

						} else {

							j++;
						}

					}
					
					/*
					 * Check there is any connected arc to the node
					 */
					int k = 0;
					while (k < model.getArcs().size()) {

						if (model.getNodes().get(i).getNumber() == model.getArcs().get(k)
								.getInitNode()) {

							controller.getDeleteNodesArcs().add(model.getArcs().get(k));
							
							model.getArcs().remove(k);
							
							controller.setDeleteNodesArcsCounter(controller.getDeleteNodesArcsCounter()+1);
							
							controller.setArcNumber(controller.getArcNumber() - 1);

							controller.getView().getCenterPanel().repaint();

						} else {

							k++;
						}

					}
					
					controller.getDeleteNodes().add(model.getNodes().get(i));
				
					//Delete Nodes from list of Nodes
					model.getNodes().remove(i);
					
					//Reset Node numbers
					for(int a = i; a < model.getNodes().size(); a++) {
						
						arcReset.add(a);
						arcReset.add(a+1);
						
						if(model.getNodes().get(a).isAttacker()) {
							
							model.getNodes().get(a).setNumber(a);
							continue;
							
						}
						
						if(model.getNodes().get(a).isTarget()) {
							
							model.getNodes().get(a).setNumber(a);
							continue;
							
						}
						
						
						
						model.getNodes().get(a).setName("node " + a);
						model.getNodes().get(a).setNumber(a);
						
					}
					
					System.out.println(arcReset);
					
					Set<Integer> set = new HashSet<>(arcReset);
					arcReset.clear();
					arcReset.addAll(set);
					
					for(Integer arcInt : arcReset) {
						
						System.out.println(arcInt);
						
						for(Arc arc: model.getArcs()) {
							
							if(arc.getInitNode() == arcInt) {
								
								arc.setInitNode(arcInt = 1);
								
							}
							
							if(arc.getEndNode() == arcInt) {
								
								arc.setEndNode(arcInt - 1);
								
							}
							
						}
												
					}

					controller.setNodeNumber(controller.getNodeNumber() - 1);
					
					controller.getView().getCenterPanel().repaint();

					/*
					 * Add deleting node command into stack for undo and redo action
					 */
					controller.getStack().doCommand(new DeleteNodeMouseAdapter(model, view, controller));

				}
			}
			controller.getCounterList().add(controller.getDeleteNodesArcsCounter());
		}
		
		}
	}

	/**
	 * Redo action of deleting node.
	 */
	@Override
	public void execute() {
		
		int count = controller.getCounterRedoList().get(0);
		
		/*
		 * Delete node added from undo action
		 */
		Node node = controller.getDeleteNodesRedo().get(controller.getDeleteNodesRedo().size() - 1);
		controller.getDeleteNodes().add(node);		
		model.getNodes().remove(node);		
		controller.getDeleteNodesRedo().remove(node);
		
		/*
		 * Delete the arcs added from undo action
		 */
		for(int i = 1; i < count + 1; i++) {
			
			Arc arc = controller.getDeleteNodesArcsRedo().get( controller.getDeleteNodesArcsRedo().size() - 1);			
			controller.getDeleteNodesArcs().add(arc);			
			model.getArcs().remove(arc);			
			controller.getDeleteNodesArcsRedo().remove(arc);
			
		}
		
		controller.getCounterList().add(0, count);		
		controller.getCounterRedoList().remove(0);		
		System.out.println("Delete Node - Delete Redo!");

	}

	/**
	 * Redo action of deleting node.
	 */
	@Override
	public void undo() {
		
		int count = controller.getCounterList().get(0);
		
		controller.getCounterRedoList().add(count);

		/*
		 * Add deleted node into List<Node>
		 */
		Node node = controller.getDeleteNodes().get(controller.getDeleteNodes().size() - 1);		
		controller.getDeleteNodesRedo().add(node);		
		model.getNodes().add(node.getNumber(), node);		
		controller.getDeleteNodes().remove(node);

		/*
		 * Add all the deleted arcs into List<Arc>
		 */
		for(int i = 1; i < count + 1; i++) {
			
			Arc arc = controller.getDeleteNodesArcs().get( controller.getDeleteNodesArcs().size() - count -1 + i);			
			controller.getDeleteNodesArcsRedo().add(arc);			
			model.getArcs().add(arc);			
			controller.getDeleteNodesArcs().remove(arc);
			
		}
		
		controller.getCounterList().remove(0);		
		System.out.println("Add Node - Delete Undo!");

	}

}
