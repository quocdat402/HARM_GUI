package test;

import java.awt.event.MouseEvent;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.company.Arc;
import com.company.MainController;
import com.company.MainModel;
import com.company.MainView;
import com.company.Node;

import adapters.ArcMouseAdapter;
import adapters.NodeMouseAdapter;

public class ExceptionTest {

	private MainView view;
	private MainModel model;
	private MainController controller;
	
	@Rule
	public final ExpectedException exception = ExpectedException.none();
	
	@Before
	public void setUpTest() {
		
		view = new MainView(model);
		model = new MainModel();
		controller = new MainController(model, view);
		controller.initController();
		
	}
	
	@Test
	public void probExceptionTest() {
		
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
		
		exception.expect(IllegalArgumentException.class);
		arc.setProbability(1.5);
		
		
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
		
		exception.expect(IllegalArgumentException.class);
		controller.attackGraphAction();
		
		attacker.setAttacker(true);
		target.setTarget(true);	
		
		Arc arc = model.getArcs().get(0);		
		
		arc.setRisk(5);
		arc.setCost(5);
		arc.setProbability(0.2);
		arc.setImpact(5);
		
		exception.expect(IllegalArgumentException.class);
		controller.attackGraphAction();
		
	}
	
	@Test
	public void createArcOnSameNodeExceptino() {
		
		controller.clearAllInfo();
		MouseEvent e1 = new MouseEvent(view.getCenterPanel(), 501, 1, 16, 322, 122, 1, false);
		MouseEvent e2 = new MouseEvent(view.getCenterPanel(), 501, 1, 16, 337, 132, 1, false);
		MouseEvent e3 = new MouseEvent(view.getCenterPanel(), 501, 1, 16, 335, 130, 1, false);
		
		controller.setActivateNode(1);
		NodeMouseAdapter nodeMouseAdapter = new NodeMouseAdapter(model, view, controller);
		nodeMouseAdapter.mousePressed(e1);
		controller.setActivateNode(0);
				
		controller.setActivateArc(1);
		ArcMouseAdapter arcMouseAdapter = new ArcMouseAdapter(model, view, controller);
		arcMouseAdapter.mousePressed(e2);
		exception.expect(UnsupportedOperationException.class);
		
		arcMouseAdapter.mouseReleased(e3);
		controller.setActivateArc(0);
		
	}
	
	@Test
	public void testNodeNegativePointException() {
		
		controller.clearAllInfo();	
		MouseEvent e1 = new MouseEvent(view.getCenterPanel(), 501, 1, 16, -1, -2, 1, false);

		controller.setActivateNode(1);
		
		exception.expect(IllegalStateException.class);
		NodeMouseAdapter nodeMouseAdapter = new NodeMouseAdapter(model, view, controller);
		nodeMouseAdapter.mousePressed(e1);
		
		controller.setActivateNode(0);
		
	}
	
	@Test
	public void testUndoException() {
		
		controller.clearAllInfo();
		exception.expect(IllegalStateException.class);
		controller.undoAction();
		
	}
	
	@Test
	public void testRedoException() {
		
		controller.clearAllInfo();				
		exception.expect(IllegalStateException.class);
		controller.redoAction();
	}
	
}
