package package1;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSplitPane;



public class View {
	
	private JFrame frame;
	private JPanel upperPanel;
	private JPanel lowerPanel;
	private JSplitPane splitPane;
	private JLabel imageLabel1;	
	private ImageIcon image;
	
	public View() {
		
		//Create a basic frame
		frame = new JFrame();
		upperPanel = new JPanel();
		lowerPanel = new JPanel();
		splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		
		//image = new ImageIcon(getClass().getResource("circle.png"));
		//imageLabel1 = new JLabel(image);
		//upperPanel.add(imageLabel1);
		
		
		splitPane.setTopComponent(upperPanel);
		
		
		
		splitPane.setBottomComponent(lowerPanel);
		
		
		splitPane.setResizeWeight(0.15);
		
		
		frame.add(splitPane);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(1024, 600);
		frame.setVisible(true);
		
		
				
	}	
	
}
