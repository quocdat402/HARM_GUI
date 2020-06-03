package test;


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
	
	//limitation
	@BeforeClass
	  public static void setUpOnce() {
	    FailOnThreadViolationRepaintManager.install();
	  }
	
	@Before
	public void setUpTest() {
		
		//Initialize classes to run the program
		model = GuiActionRunner.execute(() -> new MainModel());
		view = GuiActionRunner.execute(() -> new MainView(model));
		controller = GuiActionRunner.execute(() -> new MainController(model, view));		
		
		//Run mock GUI.
		MainWindow = new FrameFixture(view);		
		MainWindow.show();
	
	}
	
	/*
	 * Test that GUI functions work as expected
	 */
	@Test
	public void MainViewTest() {		
		
		//Press clear button
		MainWindow.button("ClearButton").click();
		
		//Press node button
		MainWindow.button("NodeButton").click();
		MainWindow.panel("centerPanel").click();
		//assertEquals(1,model.getNodes().size());
		MainWindow.button("NodeButton").click();
		
		//Press undo button
		MainWindow.button("UndoButton").click();
		//assertEquals(0,model.getNodes().size());
		
		//Press redo button
		MainWindow.button("RedoButton").click();
		//assertEquals(1,model.getNodes().size());
				  
	}

}
