package test;

import java.awt.event.MouseEvent;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.company.MainController;
import com.company.MainModel;
import com.company.MainView;

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
