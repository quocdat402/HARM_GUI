package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

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
	private boolean isConnected;
	
	@Before
	public void setUpTest() {
		
		view = new MainView(model);
		model = new MainModel();
		controller = new MainController(model, view);
		controller.initController();
		
	}
	
	/*
	 * Test that server works
	 */
	@Test
	public void connectionTest() {
		
		MouseEvent e1 = new MouseEvent(view.getCenterPanel(), 501, 1, 16, 322, 122, 1, false);
		MouseEvent e2 = new MouseEvent(view.getCenterPanel(), 501, 1, 16, 496, 248, 1, false);
		MouseEvent e3 = new MouseEvent(view.getCenterPanel(), 501, 1, 16, 337, 132, 1, false);
		MouseEvent e4 = new MouseEvent(view.getCenterPanel(), 502, 1, 16, 509, 254, 1, false);
		
		controller.setActivateNode(1);		
		NodeMouseAdapter nodeMouseAdapter = new NodeMouseAdapter(model, view, controller);
		nodeMouseAdapter.mousePressed(e1);
		nodeMouseAdapter.mousePressed(e2);
		controller.setActivateNode(0);
		
		controller.setActivateArc(1);
		ArcMouseAdapter arcMouseAdapter = new ArcMouseAdapter(model, view, controller);
		arcMouseAdapter.mousePressed(e3);
		arcMouseAdapter.mouseReleased(e4);
		controller.setActivateArc(0);
		
		Node attacker = model.getNodes().get(0);
		Node target = model.getNodes().get(1);
		attacker.setAttacker(true);
		target.setTarget(true);	
		
		Arc arc = model.getArcs().get(0);
		
		arc.setRisk(5);
		arc.setCost(5);
		arc.setProbability(0.2);
		arc.setImpact(5);
		
		controller.setPort(controller.availablePort(controller.getPort()));
		controller.openServer();
	
		isConnected = false;
		
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e11) {
			e11.printStackTrace();
		}
		try {
			Socket socket = new Socket("localhost", controller.getPort());
			isConnected = true;
			socket.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
		
		assertTrue(isConnected);
				
	}
	
	
	/*
	 * Test activate methods in MainController class
	 */
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
	
	/*
	 * Test to set attacker and target
	 */
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
	
	
	
	
	/*
	 * Test add a single node and check all the values on it.
	 */
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
	
	/*
	 * Test add a single arc and check all the values on it.
	 */
	@Test
	public void testAddArc() {
		
		view = new MainView(model);
		model = new MainModel();
		controller = new MainController(model, view);
		controller.initController();
		
		Arc arc = new Arc(1, 2, 3, 4, Color.black, 0, 1, 1, 0, 5, 4, 0.5, 3);
		model.getArcs().add(arc);
		
		assertEquals(1, arc.getX1());
		assertEquals(2, arc.getY1());
		assertEquals(3, arc.getX2());
		assertEquals(4, arc.getY2());
		assertEquals(0, arc.getInitNode());
		assertEquals(1, arc.getEndNode());		
		assertEquals(0, arc.getVulnerability());
		assertEquals(5, arc.getRisk(), 0.1);
		assertEquals(4, arc.getCost(), 0.1);
		assertEquals(0.5, arc.getProbability(), 0.01);
		assertEquals(3, arc.getImpact(), 0.1);
		
				
	}

}
