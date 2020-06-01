package test;

import org.junit.*;

import com.company.MainController;
import com.company.MainModel;
import com.company.MainView;
import com.company.Node;
import com.company.ResultView;

import adapters.ArcMouseAdapter;
import adapters.DeleteNodeMouseAdapter;
import adapters.MoveMouseAdapter;
import adapters.NodeMouseAdapter;

import static org.junit.Assert.assertEquals;

import java.awt.event.MouseEvent;

public class IntegrationTest {
	
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
	public void analysisTest() {	
		
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
		
//		attacker.setRisk(5);
//		attacker.setCost(5);
//		attacker.setProbability(0.2);
//		attacker.setImpact(5);
//		
//		target.setRisk(5);
//		target.setCost(5);
//		target.setProbability(0.2);
//		target.setImpact(5);
		
		controller.attackGraphAction();
		
		assertEquals("\n" 
				+ "Number of hosts: 2" + "\n"
				+ "Risk: 5.0" + "\n"
				+ "Cost: 5.0" + "\n"
				+ "Mean of attack path lengths: 1" + "\n"
				+ "Mode of attack path lengths: 1" + "\n"
				+ "Shortest attack path length: 1" + "\n"
				+ "Return of Attack: 1.0" + "\n"
				+ "Probability of attack success: 0.19999999999999996" + "\n"
				+ "Standard Deviation of attack path lengths: 0", ResultView.getTextPane().getText());
		
		
	}
	
	@Test
	public void testMultipleUndoRedo1() {
		
		controller.clearAllInfo();
		
		MouseEvent e1 = new MouseEvent(view.getCenterPanel(), 501, 1, 16, 322, 122, 1, false);
		MouseEvent e2 = new MouseEvent(view.getCenterPanel(), 501, 1, 16, 496, 248, 1, false);
		MouseEvent e3 = new MouseEvent(view.getCenterPanel(), 501, 1, 16, 337, 132, 1, false);
		MouseEvent e4 = new MouseEvent(view.getCenterPanel(), 502, 1, 16, 509, 254, 1, false);
		MouseEvent e5 = new MouseEvent(view.getCenterPanel(), 501, 1, 16, 123, 123, 1, false);
		MouseEvent e6 = new MouseEvent(view.getCenterPanel(), 501, 1, 16, 445, 445, 1, false);
		
		controller.setActivateNode(1);		
		NodeMouseAdapter nodeMouseAdapter = new NodeMouseAdapter(model, view, controller);
		nodeMouseAdapter.mousePressed(e1);
		nodeMouseAdapter.mousePressed(e2);
		nodeMouseAdapter.mousePressed(e5);
		nodeMouseAdapter.mousePressed(e6);
		controller.setActivateNode(0);
		
		controller.setActivateArc(1);
		ArcMouseAdapter arcMouseAdapter = new ArcMouseAdapter(model, view, controller);
		arcMouseAdapter.mousePressed(e3);
		arcMouseAdapter.mouseReleased(e4);
		controller.setActivateArc(0);
		
		assertEquals(4, model.getNodes().size());
		assertEquals(1, model.getArcs().size());
		
		controller.undoAction();
		controller.undoAction();
		controller.undoAction();
		
		assertEquals(2, model.getNodes().size());
		assertEquals(0, model.getArcs().size());
		
		controller.redoAction();
		controller.redoAction();
		controller.redoAction();
		
		assertEquals(4, model.getNodes().size());
		assertEquals(1, model.getArcs().size());
		
		
	}
	
	@Test
	public void testSimpleUndoRedo1() {
		
		controller.clearAllInfo();
		MouseEvent e1 = new MouseEvent(view.getCenterPanel(), 501, 1, 16, 287, 152, 1, false);
		
		controller.setActivateNode(1);		
		NodeMouseAdapter nodeMouseAdapter = new NodeMouseAdapter(model, view, controller);
		nodeMouseAdapter.mousePressed(e1);
		controller.setActivateNode(0);
		
		controller.undoAction();

		assertEquals(0, model.getNodes().size());
		
		controller.redoAction();
		
		assertEquals(1, model.getNodes().size());
		
	}
	
	@Test
	public void testClearAll() {
		controller.clearAllInfo();
		
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
		
		controller.clearAllInfo();
		
		assertEquals(0, model.getNodes().size());
		assertEquals(0, model.getArcs().size());
		
	}
	
	@Test
	public void testMoveNodes() {
		
		controller.clearAllInfo();
		
		MouseEvent e1 = new MouseEvent(view.getCenterPanel(), 501, 1, 16, 287, 152, 1, false);
		MouseEvent e2 = new MouseEvent(view.getCenterPanel(), 501, 1, 16, 562, 298, 1, false);
		MouseEvent e3 = new MouseEvent(view.getCenterPanel(), 501, 1, 16, 111, 222, 1, false);
		
		
		controller.setActivateNode(1);		
		NodeMouseAdapter nodeMouseAdapter = new NodeMouseAdapter(model, view, controller);
		nodeMouseAdapter.mousePressed(e1);
		nodeMouseAdapter.mousePressed(e2);
		controller.setActivateNode(0);
		
		controller.setActivateMove(1);
		MoveMouseAdapter moveMouseAdapter = new MoveMouseAdapter(model, view, controller);
		moveMouseAdapter.mousePressed(e1);
		moveMouseAdapter.mouseReleased(e3);
		
		assertEquals(111, model.getNodes().get(0).getX());
		assertEquals(222, model.getNodes().get(0).getY());
		
	}
	
	
	
	@Test
	public void testDeleteNodesandArcs() {
		
		controller.clearAllInfo();
		
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
		
		controller.setActivateDelete(1);
		DeleteNodeMouseAdapter deleteNodeMouseAdapter = new DeleteNodeMouseAdapter(model, view, controller);
		deleteNodeMouseAdapter.mousePressed(e4);
		
		assertEquals(1, model.getNodes().size());
		assertEquals(0, model.getArcs().size());
	}
	
	@Test
	public void testDeleteNodes() {
		
		controller.clearAllInfo();
		
		MouseEvent e1 = new MouseEvent(view.getCenterPanel(), 501, 1, 16, 287, 152, 1, false);
		MouseEvent e2 = new MouseEvent(view.getCenterPanel(), 501, 1, 16, 299, 163, 1, false);
		
		controller.setActivateNode(1);
		NodeMouseAdapter nodeMouseAdapter = new NodeMouseAdapter(model, view, controller);
		nodeMouseAdapter.mousePressed(e1);
		controller.setActivateNode(0);
		
		controller.setActivateDelete(1);
		DeleteNodeMouseAdapter deleteNodeMouseAdapter = new DeleteNodeMouseAdapter(model, view, controller);
		deleteNodeMouseAdapter.mousePressed(e2);
		
		assertEquals(0, model.getNodes().size());
		
	}
	
	@Test
	public void testNodeMouseAdapter() {
		
		controller.clearAllInfo();
		
		MouseEvent e1 = new MouseEvent(view.getCenterPanel(), 501, 1, 16, 287, 152, 1, false);
		MouseEvent e2 = new MouseEvent(view.getCenterPanel(), 501, 1, 16, 562, 298, 1, false);
		
		controller.setActivateNode(1);
		
		NodeMouseAdapter nodeMouseAdapter = new NodeMouseAdapter(model, view, controller);
		nodeMouseAdapter.mousePressed(e1);
		nodeMouseAdapter.mousePressed(e2);
		
		assertEquals(0, model.getNodes().get(0).getNumber());
		assertEquals(1, model.getNodes().get(1).getNumber());
		
	}
	
	@Test
	public void testArcMouseAdapter() {
		
		controller.clearAllInfo();
		
		MouseEvent e1 = new MouseEvent(view.getCenterPanel(), 501, 1, 16, 287, 152, 1, false);
		MouseEvent e2 = new MouseEvent(view.getCenterPanel(), 501, 1, 16, 562, 298, 1, false);
		MouseEvent e3 = new MouseEvent(view.getCenterPanel(), 501, 1, 16, 299, 163, 1, false);
		MouseEvent e4 = new MouseEvent(view.getCenterPanel(), 502, 1, 16, 577, 305, 1, false);
		
		controller.setActivateNode(1);
		
		NodeMouseAdapter nodeMouseAdapter = new NodeMouseAdapter(model, view, controller);
		nodeMouseAdapter.mousePressed(e1);
		nodeMouseAdapter.mousePressed(e2);
		controller.setActivateNode(0);
		controller.setActivateArc(1);
		
		ArcMouseAdapter arcMouseAdapter = new ArcMouseAdapter(model, view, controller);
		arcMouseAdapter.mousePressed(e3);
		arcMouseAdapter.mouseReleased(e4);
		
		assertEquals(0, model.getArcs().get(0).getNumber());
		assertEquals(0, model.getArcs().get(0).getInitNode());
		assertEquals(1, model.getArcs().get(0).getEndNode());
	}
}
