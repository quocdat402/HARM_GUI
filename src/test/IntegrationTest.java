package test;

import org.junit.*;

import com.company.Arc;
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

import java.awt.Color;
import java.awt.event.MouseEvent;

import javax.swing.JTable;

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
	
	
	/*
	 * Test all the functions in metrics frame.
	 */
	@Test
	public void metricsFrameTest() {
		
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
		
		controller.metricsAction();
		controller.getMetricsView().setVisible(false);
		JTable table = controller.getMetricsView().getTable();
		
		int rowCount = table.getRowCount();
		
		assertEquals(2, rowCount);
		
		Arc arc = model.getArcs().get(0);
		arc.setRisk(5);
		arc.setCost(4);
		arc.setProbability(0.2);
		arc.setImpact(3);
		
		controller.getMetricsView().connectionAction();
		String connection = (String)table.getValueAt(arc.getInitNode(), arc.getEndNode());
		assertEquals("0->1", connection);
		
		controller.getMetricsView().riskAction();
		double risk = (double) table.getValueAt(arc.getInitNode(), arc.getEndNode());
		assertEquals(5, (int)risk);
		
		controller.getMetricsView().costAction();
		double cost = (double) table.getValueAt(arc.getInitNode(), arc.getEndNode());
		assertEquals(4, (int)cost);
		
		controller.getMetricsView().probAction();
		double prob = (double) table.getValueAt(arc.getInitNode(), arc.getEndNode());
		assertEquals(0.2, prob, 0.01);
		
		controller.getMetricsView().impactAction();
		double impact = (double) table.getValueAt(arc.getInitNode(), arc.getEndNode());
		assertEquals(3, (int)impact);
		
	}
	
	/*
	 * Test analysis of attack graph returns right output 
	 * and all the functions in ResultView return right value.
	 */
	@Test
	public void analysisTest() {	
		
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
		controller.openClient();
		
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
	
	/*
	 * Test multiple undo and redo functions
	 */
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
	
	/*
	 * Test signle redo and undo function
	 */
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
	
	/*
	 * Test "Clear All" functions
	 */
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
	
	/*
	 * Test moving nodes function with MouseAdapters
	 */
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
	
	/*
	 * Test deleting nodes and arcs function with MouseAdapters
	 */
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
	
	/*
	 * Test deleting nodes function with MouseAdapters
	 */
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
	
	/*
	 * Test adding nodes function with MouseAdapters
	 */
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
	
	/*
	 * Test adding arcs function with MouseAdapters
	 */
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
	
	/*
	 * Test adding arc between two nodes
	 */
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
}
