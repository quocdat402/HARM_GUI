package package1;

import java.awt.Image;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
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
	
	private JMenuBar menubar;
	private JMenu file, help;
	private JMenuItem exit, about;
	
	private JButton circleImageButton;
	private ImageIcon circleImageIcon;
	private Image circleImage, circleNewImage;
	private JButton lineImageButton;
	private ImageIcon lineImageIcon;
	private Image lineImage, lineNewImage;
	
	public View() {
		
		//Create a basic frame
		frame = new JFrame();
		upperPanel = new JPanel();
		upperPanel.setLayout(new BoxLayout(upperPanel, BoxLayout.Y_AXIS));
		upperPanel.setLayout(new BoxLayout(upperPanel, BoxLayout.X_AXIS));
		lowerPanel = new JPanel();
		splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		
		//Add a circle button in the UpperPanel
		circleImageIcon = new ImageIcon(getClass().getResource("/circle.png"));
		circleImage = circleImageIcon.getImage();
		circleNewImage = circleImage.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		circleImageIcon = new ImageIcon(circleNewImage);
		circleImageButton = new JButton();
		circleImageButton.setIcon(circleImageIcon);
		upperPanel.add(circleImageButton);
		
		//Add a line button in the UpperPanel
		lineImageIcon = new ImageIcon(getClass().getResource("/line.png"));
		lineImage = lineImageIcon.getImage();
		lineNewImage = lineImage.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		lineImageIcon = new ImageIcon(lineNewImage);
		lineImageButton = new JButton();
		lineImageButton.setIcon(lineImageIcon);
		upperPanel.add(lineImageButton);		
		
		
		splitPane.setTopComponent(upperPanel);
				
		splitPane.setBottomComponent(lowerPanel);
				
		splitPane.setResizeWeight(0.05);
				
		frame.add(splitPane);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(1024, 600);
		
		//Create a menu
		menubar = new JMenuBar();
		frame.setJMenuBar(menubar);
		
		file = new JMenu("File");
		menubar.add(file);
		exit = new JMenuItem("Exit");
		file.add(exit);
		
		help = new JMenu("Help");
		menubar.add(help);
		about = new JMenuItem("About");
		help.add(about);
		
		//make a framea visible
		frame.setVisible(true);
		
		
				
	}	
	
}
