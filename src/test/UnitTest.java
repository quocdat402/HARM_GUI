package test;

import static org.junit.Assert.assertEquals;

import java.awt.Color;
import java.awt.event.MouseEvent;

import org.junit.Before;
import org.junit.Test;

import com.company.Arc;
import com.company.MainController;
import com.company.MainModel;
import com.company.MainView;
import com.company.Node;

import adapters.ArcMouseAdapter;
import adapters.NodeMouseAdapter;

public class UnitTest {
	
	private MainView view;
	private MainModel model;
	private MainController controller;
	
	@Before
	public void setUpTest() {
		
		view = new MainView(model);
		model = new MainModel();
		controller = new MainController(model, view);
		controller.initController();
		
	}
	
	@Test
	public void activateMethodTest() {
		
		controller.clearAllInfo();
		
		controller.activateArcInt();
		assertEquals(1, controller.getActivateArc());
		controller.setActivateArc(0);
	
		controller.activateDeleteInt();
		assertEquals(1, controller.getActivateDelete());
		controller.setActivateDelete(0);
		
		controller.activateGetInfoInt();
		assertEquals(1, controller.getActivateGetInfo());
		controller.setActivateGetInfo(0);
		
		controller.activateMoveInt();
		assertEquals(1, controller.getActivateMove());
		controller.setActivateMove(0);
		
		controller.activateNodeInt();
		assertEquals(1, controller.getActivateNode());
		controller.setActivateNode(0);
	}
	
	@Test
	public void testAttakerTargetSetting() {
				
		controller.clearAllInfo();
		
		Node node1 = new Node(500, 400, 24, Color.white, "node " + 0, 0, false,
				false);
		Node node2 = new Node(500, 400, 24, Color.white, "node " + 1, 1, false,
				false);
		
		model.getNodes().add(node1);
		model.getNodes().add(node2);
		
		controller.setNodePropertyInt(0);
		controller.nodeAttacker();
		
		assertEquals("Attacker", node1.getName());
		
		controller.setNodePropertyInt(1);
		controller.nodeTarget();
		
		assertEquals("Target", node2.getName());
		
	}
	
	@Test
	public void testArcBetweenNodes() {
				
		controller.clearAllInfo();
		
		Node node1 = new Node(500, 400, 24, Color.white, "node " + 0, 0, false,
				false);
		
		Node node2 = new Node(500, 400, 24, Color.white, "node " + 1, 1, false,
				false);
		
		Arc arc = new Arc(1, 2, 3, 4, Color.black, 0, 1, 1, 0, 0, 0, 0, 0);
		model.getArcs().add(arc);
		
		assertEquals(node1.getNumber(), arc.getInitNode());
		assertEquals(node2.getNumber(), arc.getEndNode());
		
	}
	
	@Test
	public void testAddNode() {
		
		view = new MainView(model);
		model = new MainModel();
		controller = new MainController(model, view);
		controller.initController();
		
		Node node = new Node(500, 400, 24, Color.white, "node " + 0, 0, false,
				false); 
		
		model.getNodes().add(node);
		assertEquals(500, node.getX());
		assertEquals(400, node.getY());
		assertEquals(24, node.getDiameter());
		assertEquals(Color.white, node.getColor());
		assertEquals("node 0", node.getName());
		assertEquals(0, node.getNumber());
	}
	
	@Test
	public void testAddArc() {
		
		view = new MainView(model);
		model = new MainModel();
		controller = new MainController(model, view);
		controller.initController();
		
		Arc arc = new Arc(1, 2, 3, 4, Color.black, 0, 1, 1, 0, 0, 0, 0, 0);
		model.getArcs().add(arc);
		
		assertEquals(1, arc.getX1());
		assertEquals(2, arc.getY1());
		assertEquals(3, arc.getX2());
		assertEquals(4, arc.getY2());
		assertEquals(0, arc.getInitNode());
		assertEquals(1, arc.getEndNode());		
		
				
	}

}
