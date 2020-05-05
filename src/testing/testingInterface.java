package testing;

import org.junit.*;

import com.company.ArcMouseAdapter;
import com.company.MainController;
import com.company.MainModel;
import com.company.MainView;
import com.company.NodeMouseAdapter;

import static org.junit.Assert.assertEquals;

import java.awt.event.MouseEvent;

public class testingInterface {
	
	private MainView view;
	private MainModel model;
	private MainController controller;
	
	@Before
	public void setUpTest() {
		
		view = new MainView();
		model = new MainModel();
		controller = new MainController(model, view);
		controller.initController();
		
	}
	
	@Test
	public void testDeleteArcs() {
		
		
		
	}
	
	@Test
	public void testDeleteNodes() {
		
		
	}
	
	@Test
	public void testNodeMouseAdapter() {
		
		controller.clearAllInfo();
		
		MouseEvent e1 = new MouseEvent(view.getCenterPanel(), 501, 1, 16, 287, 152, 1, false);
		MouseEvent e2 = new MouseEvent(view.getCenterPanel(), 501, 1, 16, 562, 298, 1, false);
		
		controller.setActivateNode(1);
		
		NodeMouseAdapter nodeMouseAdapter = new NodeMouseAdapter();
		nodeMouseAdapter.mousePressed(e1);
		nodeMouseAdapter.mousePressed(e2);
		
		assertEquals(0, view.getNodes().get(0).getNumber());
		assertEquals(1, view.getNodes().get(1).getNumber());
		
	}
	
	@Test
	public void testArcMouseAdapter() {
		
		controller.clearAllInfo();
		
		MouseEvent e1 = new MouseEvent(view.getCenterPanel(), 501, 1, 16, 287, 152, 1, false);
		MouseEvent e2 = new MouseEvent(view.getCenterPanel(), 501, 1, 16, 562, 298, 1, false);
		MouseEvent e3 = new MouseEvent(view.getCenterPanel(), 501, 1, 16, 299, 163, 1, false);
		MouseEvent e4 = new MouseEvent(view.getCenterPanel(), 502, 1, 16, 577, 305, 1, false);
		
		controller.setActivateNode(1);
		
		NodeMouseAdapter nodeMouseAdapter = new NodeMouseAdapter();
		nodeMouseAdapter.mousePressed(e1);
		nodeMouseAdapter.mousePressed(e2);
		controller.setActivateNode(0);
		controller.setActivateArc(1);
		
		ArcMouseAdapter arcMouseAdapter = new ArcMouseAdapter();
		arcMouseAdapter.mousePressed(e3);
		arcMouseAdapter.mouseReleased(e4);
		
		assertEquals(0, view.getArcs().get(0).getNumber());
		assertEquals(0, view.getArcs().get(0).getInitNode());
		assertEquals(1, view.getArcs().get(0).getEndNode());
	}
}
