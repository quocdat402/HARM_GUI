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




public class GUITest {
	
	private MainView view;
	private MainModel model;
	private MainController controller;
	
	private FrameFixture MainWindow;
	private FrameFixture Resultwindow;
	private FrameFixture Metricswindow;
	
	//limitation
	@BeforeClass
	  public static void setUpOnce() {
	    FailOnThreadViolationRepaintManager.install();
	  }
	
	@Before
	public void setUpTest() {
		
		//view = new MainView(model);
		
		model = GuiActionRunner.execute(() -> new MainModel());
		view = GuiActionRunner.execute(() -> new MainView(model));
		controller = GuiActionRunner.execute(() -> new MainController(model, view));
		
		//controller = new MainController(model, view);
		//controller.initController();
		
		MainWindow = new FrameFixture(view);
		MainWindow.show();
		
		
	
	}
	
	@Test
	public void clickButtonTest() {
		
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
