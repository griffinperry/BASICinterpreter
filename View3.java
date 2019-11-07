import javax.swing.*;
import java.awt.*;
public class View3 extends JFrame{
	
	JButton readB = new JButton("Read");
	JButton runB = new JButton("Run");
	JButton saveB = new JButton("Save");
	JButton resetB = new JButton("Reset");
	
	JPanel topPanel = new JPanel(new GridLayout(1,4));
	JPanel centerPanel = new JPanel();
	JPanel mainPanel = new JPanel(new BorderLayout());
	JTextArea textArea = new JTextArea(20,40);
	JScrollPane scroll = new JScrollPane(textArea);
	/**
	 * The constructor for the View3 class that adds the GUI Components to the 
	 * declared panels, adds the panels to the JFrame, and specifies JFrame 
	 * attributes such as background color, bounds, and visibility.
	 */
	public View3() {
		
		topPanel.setBackground(new Color(224, 226, 255));
		mainPanel.setBackground(new Color(224, 226, 255));
		centerPanel.setBackground(new Color(224, 226, 255));
		topPanel.add(readB);
		topPanel.add(runB);
		topPanel.add(saveB);
		topPanel.add(resetB);
		centerPanel.add(scroll);
		mainPanel.add(topPanel, BorderLayout.NORTH);
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		setTitle("Basic interpreter");
		add(mainPanel);
		setBounds(50,50,520,420);
		setVisible(true);
		//validate();
	}
	/**
	 * This is the main method.
	 */
	public static void main(String args[]) {
		new View3();
	}


}
