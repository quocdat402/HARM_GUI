package test;

import static org.junit.Assert.assertEquals;

import org.assertj.swing.edt.FailOnThreadViolationRepaintManager;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.company.MainController;
import com.company.MainModel;
import com.company.MainView;
import com.company.MetricsView;
import com.company.ResultView;




public class GUITest {
	
	private MainView view;
	private MainModel model;
	private MainController controller;
	
	private FrameFixture MainWindow;
	
	//limitation
	@BeforeClass
	  public static void setUpOnce() {
	    FailOnThreadViolationRepaintManager.install();
	  }
	
	@Before
	public void setUpTest() {
		
		model = GuiActionRunner.execute(() -> new MainModel());
		view = GuiActionRunner.execute(() -> new MainView(model));
		controller = GuiActionRunner.execute(() -> new MainController(model, view));		
		MainWindow = new FrameFixture(view);		
		MainWindow.show();
	
	}
	
	/*
	 * Test that GUI functions work as expected
	 */
	@Test
	public void MainViewTest() {		
		
		MainWindow.button("ClearButton").click();
		
		MainWindow.button("NodeButton").click();
		MainWindow.panel("centerPanel").click();
		assertEquals(1,model.getNodes().size());
		MainWindow.button("NodeButton").click();
		
		MainWindow.button("UndoButton").click();
		assertEquals(0,model.getNodes().size());
		
		MainWindow.button("RedoButton").click();
		assertEquals(1,model.getNodes().size());
				  
	}

}
