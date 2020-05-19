package test;

import static org.junit.Assert.assertEquals;

import org.assertj.swing.edt.FailOnThreadViolationRepaintManager;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.company.Main;
import com.company.MainController;
import com.company.MainModel;
import com.company.MainView;

public class GUITest {
	
	private MainView view;
	private MainModel model;
	private MainController controller;
	
	private FrameFixture window;
	
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
		
		window = new FrameFixture(view);
		window.show();
		
		
	
	}
	
	@Test
	public void clickButtonTest() {
		
		window.button("NodeButton").click();
		window.panel("centerPanel").click();
		assertEquals(1,model.getNodes().size());
		window.button("NodeButton").click();
		
		window.button("UndoButton").click();
		assertEquals(0,model.getNodes().size());
		
		window.button("RedoButton").click();
		assertEquals(1,model.getNodes().size());
		
		window.button("DeleteButton").click();
		window.panel("centerPanel").click();
		//assertEquals(0,model.getNodes().size());
				  
	}

}
