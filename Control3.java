/**
 * @author Griffin McPherson
 * Lab 3- An interpreter program for Cosc 20203 Programming Techniques
 */
import java.awt.FileDialog;
import java.awt.event.*;

public class Control3 extends View3 implements ActionListener{
	/**
	 * declaration of an instance of the Model3 Class
	 */
	public Model3 m;
	/**
	 * 
	 * The constructor for the Control3 Class. It contains the initialization of
	 * an instance of the Model3 class to gain access to the public methods
	 * in Model3.
	 */
	public Control3() {
		m = new Model3(this);
		readB.addActionListener(this);
		runB.addActionListener(this);
		saveB.addActionListener(this);
		resetB.addActionListener(this);
	}
	/**
	 * The actionPerformed method that indicates the method to execute depending
	 * on what button is clicked.
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o == readB) m.displayFile(m.getFileName());	
		if (o == runB) m.runProgram(); 
		if (o == saveB) m.saveOpenFiles(m.getFileSaveName());
		if (o == resetB) m.reset();
	}
	
	/**
	 * This is the main method.
	 * 
	 */
	public static void main(String args[]) {
		new Control3();
	}
	
	

}
