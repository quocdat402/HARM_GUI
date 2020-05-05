package testing;

import org.junit.*;

import com.company.Arc;
import com.company.MainController;
import com.company.MainModel;
import com.company.MainView;
import com.company.Node;

import static org.junit.Assert.assertEquals;

import java.awt.Color;

public class testingObject {

	private MainView view;
	private MainModel model;
	private MainController controller;
	
	@Test
	public void testAttakerTargetSetting() {
		
		view = new MainView();
		model = new MainModel();
		controller = new MainController(model, view);
		controller.initController();
		
		Node node1 = new Node(500, 400, 24, Color.white, "node " + 0, 0, false,
				false);
		Node node2 = new Node(500, 400, 24, Color.white, "node " + 1, 1, false,
				false);
		
		view.getNodes().add(node1);
		view.getNodes().add(node2);
		
		controller.setNodePropertyInt(0);
		controller.nodeAttacker();
		
		assertEquals("Attacker", node1.getName());
		
		controller.setNodePropertyInt(1);
		controller.nodeTarget();
		
		assertEquals("Target", node2.getName());
		
	}
	
	@Test
	public void testArcBetweenNodes() {
		
		view = new MainView();
		
		Node node1 = new Node(500, 400, 24, Color.white, "node " + 0, 0, false,
				false);
		
		Node node2 = new Node(500, 400, 24, Color.white, "node " + 1, 1, false,
				false);
		
		Arc arc = new Arc(1, 2, 3, 4, Color.black, 0, 1, 1.2, 1, 1.5, 2.5, 3.5, 4.5);
		view.getArcs().add(arc);
		
		assertEquals(node1.getNumber(), arc.getInitNode());
		assertEquals(node2.getNumber(), arc.getEndNode());
		
	}
	
	@Test
	public void testAddNode() {
		
		view = new MainView();
		
		Node node = new Node(500, 400, 24, Color.white, "node " + 0, 0, false,
				false); 
		
		view.getNodes().add(node);
		assertEquals(500, node.getX());
		assertEquals(400, node.getY());
		assertEquals(24, node.getDiameter());
		assertEquals(Color.white, node.getColor());
		assertEquals("node 0", node.getName());
		assertEquals(0, node.getNumber());
	}
	
	@Test
	public void testAddArc() {
		
		view = new MainView();
		
		Arc arc = new Arc(1, 2, 3, 4, Color.black, 0, 1, 1.2, 1, 1.5, 2.5, 3.5, 4.5);
		view.getArcs().add(arc);
		
		assertEquals(1, arc.getX1());
		assertEquals(2, arc.getY1());
		assertEquals(3, arc.getX2());
		assertEquals(4, arc.getY2());
		assertEquals(0, arc.getInitNode());
		assertEquals(1, arc.getEndNode());		
		assertEquals(1.2, arc.getVulnerability(),0);
		assertEquals(1, arc.getNumber());
		assertEquals(1.5, arc.getRisk(),0);
		assertEquals(2.5, arc.getCost(),0);
		assertEquals(3.5, arc.getProbability(),0);
		assertEquals(4.5, arc.getImpact(),0);
				
	}
}
