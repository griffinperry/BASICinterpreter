import java.awt.FileDialog;
import java.awt.Frame;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Map.Entry;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.swing.*;
import java.awt.*;
import java.util.regex.*;

public class Model3 {
	/**
	 * declaration of a Control3 object
	 */
	public Control3 c;
	/**
	 * an instance of the Assignments class to allow for the assignment of an expression to a variable
	 */
	public Assignments assign = new Assignments();
	Hashtable<Integer,Integer>  lineItemsHash = new Hashtable<Integer,Integer>();
	Vector<Line> lineItems = new Vector();
	JFrame console = new JFrame("Console");
	JTextArea consoleArea = new JTextArea(20,40);
	JScrollPane consolePane = new JScrollPane(consoleArea);
	String comment;
	String conditional;
	String assignment;
	String goTo;
	String end;
	String print;
	/**
	 * The constructor for the Model3 Class.
	 * 
	 * @param fromC passes in an instance of the Control2 class for access to components and methods in
	 * the Control2 Class and View2 Class that Control2 extends.
	 */
	public Model3(Control3 fromC) {
		c = fromC;
	}

	/**
	 * opens a FileDialog for opening a file and retrieves the path and name of the file chose
	 * @return the path and name of file opened
	 */
	public String getFileName() {
		FileDialog chooser= new FileDialog(c, "Open...", FileDialog.LOAD);
		chooser.setVisible(true);
		String dir = chooser.getDirectory();
		System.out.println("\nThe file path using host IO is " + dir);
		String fileName = chooser.getFile();
		System.out.println("The filename chosen is: " + fileName);
		return dir + fileName;
	}
	/**
	 * opens a FileDialog for saving a file and retrieves the path and name of the file saving
	 * @return the path and name of the file saved
	 */
	public String getFileSaveName()
	{   System.out.println ("proces get file name");
	    FileDialog chooser2 = new FileDialog(c,"Save...", FileDialog.SAVE);
		chooser2.setVisible(true);
		String name = chooser2.getFile();
		String dir = chooser2.getDirectory();
		 System.out.println("\nThe file path using host IO is " + dir);
		 System.out.println("\nThe file name is " + name);
		return dir + name; 
	}
		/**
		 * returns the program to the original state
		 */
		public void reset(){
			c.textArea.setText("");
			consoleArea.setText("");
			console.setVisible(false);
		}
		/**
		 * reads the file that was opened from the FileDialog and appends each line to the JTextArea
		 * @param fileName the name of the file to read from
		 */
		public void displayFile(String fileName) {
		System.out.println("displayFile() executed");
		//reset();
		 try{
				BufferedReader in = new BufferedReader(new FileReader(fileName));
				String line;
				//c.textArea.setText("test");
				// reading line by line
					while ((line = in.readLine()) != null)
						 { System.out.println(line);
						c.textArea.append(line + "\n");
						 
						 }
					in.close();
		       }
		       catch (IOException e) {
					 c.textArea.setText("We got an ERROR: " + e);
				}
			}
		/**
		 * prints the lines from the JTextArea into a new text file that is saved
		 * @param fileName the name of the new text file to save
		 */
		public void saveOpenFiles(String fileName) {
	        String  outputFileName = fileName;
	        try {
	          PrintWriter out = new PrintWriter(outputFileName);
	          StringReader sr = new StringReader(c.textArea.getText());
			  BufferedReader br = new BufferedReader(sr);
			  String nextLine = "";
					while ((nextLine = br.readLine()) != null){
					out.println(nextLine);
	          }
	          out.close(); 
	          System.out.println("Saving text into file "
	          +outputFileName);
	         }
	        catch (IOException exception) {
	        	System.out.println("Error processing file: " + exception); }
		}
		/**
		 * stores an instance of the Line class into a vector and stores the index of the vector into a hash table, using
		 * the line number as the key.
		 * @throws NumberFormatException in case line numbers are not provided in the program and the method attempts to parse a non-integer.
		 * @throws StringIndexOutOfBoundsException in case an index is either negative or greater than the size of the string
		 */
		public void saveLineItems() throws NumberFormatException, StringIndexOutOfBoundsException{
			StringReader sr = new StringReader(c.textArea.getText());
			BufferedReader br = new BufferedReader(sr);
			String nextLine = "";
			try {
				int i =0;
				int lines=0;
				while ((nextLine = br.readLine()) != null){
					Line lineObject = new Line();
					int firstSpace = nextLine.indexOf(' ');
					System.out.println("First occurence of space is index: "+firstSpace);
					lineObject.lineNumber = Integer.valueOf(nextLine.substring(0,firstSpace));
					lineObject.instr = nextLine.substring(firstSpace+1);
					
					lineItems.add(i, lineObject); 
					System.out.println("Element in Vector["+i+"] is "+lineObject.instr);
					
					lineItemsHash.put(lineObject.lineNumber, i);
					System.out.println("The index for line # "+ lineObject.lineNumber + " is " + lineItemsHash.get(lineObject.lineNumber));
					i++;
					}
				
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		/**
		 * runs the program when clicking the Run button, calling the saveLineItems(), lineIterate(), and displayConsole() methods.
		 */
		public void runProgram() {
			try {
			saveLineItems(); }
			catch(NumberFormatException e) {
				System.out.println("NumberFormatException: Ensure line numbers are provided.");}
			catch(StringIndexOutOfBoundsException e) {
				System.out.println("StringIndexOutOfBoundsException: Please provide proper syntax with line numbers");}
			
			lineIterate();
			displayConsole();
		}
		/**
		 * sets up and displays the console frame with the resultant appended text depending on the execution of the lineIterate() method.
		 */
		public void displayConsole() {
			consoleArea.setBackground(Color.BLACK);
			consoleArea.setForeground(Color.WHITE);
			Font f = new Font("Inconsolata", Font.BOLD, 18);
			consoleArea.setFont(f);
			console.add(consolePane);
			console.setBounds(0,0, 500, 400);
			console.setVisible(true);
		}
		/**
		 * evaluates a given String compared to a given regular expression
		 * @param theRegex a String representing the regular expression to compile and compare to
		 * @param str2Check a String to check if it matches with the given regular expression
		 * @return boolean value of whether the String matches the given regular expression
		 */
		public boolean regexChecker(String theRegex, String str2Check){

	        // You define your regular expression (REGEX) using Pattern

	        Pattern checkRegex = Pattern.compile(theRegex);
	        Matcher regexMatcher = checkRegex.matcher( str2Check );
	        boolean result = regexMatcher.find();
	        //boolean result = Pattern.matches(theRegex, str2Check);
	        return result;
		}
		/**
		 * assigns the given variable to an expression
		 * @param lineIt the String to parse and determine the variable and expression to assign
		 */
		public void assignVar(String lineIt) {
			
			int equalsIndex = lineIt.indexOf('=');
			String LHS = lineIt.substring(0, equalsIndex); 
			String RHS = lineIt.substring(equalsIndex+1);
			System.out.println("Variable is " +LHS+ ", expression is " + RHS);
			assign.assignVar(lineIt);
		}
		/**
		 * prints the given variable, if it exists, to the console. If the variable is not a key in the symbolTable Hash Table,
		 * 0.0 is printed.
		 * @param lineIt the String to be parsed to determine what variable to print
		 */
		public void printFunction(String lineIt) {
			int spaceIndex = lineIt.indexOf(' ');
			String var = lineIt.substring(spaceIndex + 1).trim();
			if(assign.symbolTable.containsKey(var)) {
			String varPrint = String.valueOf(assign.symbolTable.get(var));
			System.out.println(varPrint);
			consoleArea.append(varPrint+"\n");}
			else consoleArea.append("0.0" + "\n");
		}
		/**
		 * sets the regular expressions that are to be compiled and matched to the lines of code
		 */
		public void setRegExps() {
			comment = "\\/\\/";
			conditional = "if\\s*\\(\\s*[A-Za-z]\\s*=\\s*\\d+\\s*\\)";
			assignment = "[A-Za-z]\\s*"+"=";
			goTo = "goto\\s*"+"\\d+";
			end = "end";
			print = "print\\s*";
		}
		/**
		 * iterates through the program, determines what type of statement is given, and executes the necessary
		 * code depending if the line is a comment, a conditional statement, an assignment, a goto, a print
		 * statement, or an end statement.
		 */
		public void lineIterate() {
			setRegExps();
			ListIterator<Line> it = lineItems.listIterator();
			boolean firstLine = true;
			String lineStart="";
			
			while(it.hasNext()) {
				Line lineIt = (Line) it.next();
				if(firstLine) {
				lineStart = String.valueOf(lineIt.lineNumber);
				}
			
				if(regexChecker(comment,lineIt.instr)) {
					System.out.println("Line "+lineIt.lineNumber + " is a comment");
					continue;
				}
				else if(regexChecker(conditional, lineIt.instr.trim())) {
					
					if(firstLine) {
						consoleArea.append("Running from line " + lineStart + "\n");
						consoleArea.append("______________________________" + "\n");
						firstLine = false; 
					}
					
					
					System.out.println("Line "+lineIt.lineNumber + " is a conditional");
					int par1Index = lineIt.instr.indexOf("(");
					int par2Index = lineIt.instr.indexOf(')');
					String ifStat = lineIt.instr.substring(par1Index+1, par2Index);
					System.out.println("If statement is: "+ ifStat);
					int equalsIndex = lineIt.instr.indexOf('=');
					String LHS = lineIt.instr.substring(par1Index+1, equalsIndex).trim(); 
					String RHS = lineIt.instr.substring(equalsIndex+1, par2Index).trim();
					System.out.println("value to test is "+RHS);
					System.out.println("variable to test is "+LHS);
					if (Integer.parseInt(RHS) == assign.symbolTable.get(LHS)) {
					
						String thenStat = lineIt.instr.substring(par2Index +1);
						String thenStat2 = lineIt.instr.substring(par2Index +2);
				
						if(regexChecker(assignment, thenStat)) {
							System.out.println("Conditional result for "+ "Line "+lineIt.lineNumber + " is an assigment");
							assignVar(thenStat);
							
						}
						else if(regexChecker(goTo, thenStat)) {
							System.out.println("Conditional result for "+ "Line "+lineIt.lineNumber + " is a goto");
							int spaceIndex = thenStat2.indexOf(' ');
							String gotoLine = thenStat2.substring(spaceIndex +1);
							int newKey = Integer.parseInt(gotoLine.trim());
							if(lineItemsHash.containsKey(newKey)) {
							int newIndex = lineItemsHash.get(Integer.parseInt(gotoLine.trim()));
							System.out.println("New index for iterator is: " + newIndex);
							ListIterator<Line> newIt = lineItems.listIterator(newIndex);
							it = newIt;
							}
							else {
								System.out.println("Line number "+newKey+ " doesn't exist");
								continue;
							}
						}
						else if(regexChecker(print, thenStat)) {
							System.out.println("Conditional result for "+ "Line "+lineIt.lineNumber + " is a print statement");
							printFunction(thenStat);
						}
						else System.out.println("Conditional result for "+ "Line type is unknown for line " + lineIt.lineNumber);
					}
					else {
						System.out.println("The conditional statement evaluated to false");
						
					}
				}
				else if(regexChecker(assignment, lineIt.instr)) {
					if(firstLine) {
						consoleArea.append("Running from line " + lineStart + "\n");
						consoleArea.append("______________________________" + "\n");
						firstLine = false; 
					}
					System.out.println("Line "+lineIt.lineNumber + " is an assigment");
					String instruc = lineIt.instr;
					assignVar(instruc);
				}
				else if(regexChecker(goTo, lineIt.instr)) {
					if(firstLine) {
						consoleArea.append("Running from line " + lineStart + "\n");
						consoleArea.append("______________________________" + "\n");
						firstLine = false; 
					}
					System.out.println("Line "+lineIt.lineNumber + " is a goto");
					int spaceIndex = lineIt.instr.indexOf(' ');
					String gotoLine = lineIt.instr.substring(spaceIndex +1);
					int newKey = Integer.parseInt(gotoLine.trim());
					if(lineItemsHash.containsKey(newKey)) {
					int newIndex = lineItemsHash.get(newKey);
					System.out.println("New index for iterator is: " + newIndex);
					ListIterator<Line> newIt = lineItems.listIterator(newIndex);
					it = newIt;
					}
					else {
						System.out.println("Line number "+newKey+ " doesn't exist");
						continue;
					}
				}
				else if(regexChecker(end, lineIt.instr)) {
					if(firstLine) {
						consoleArea.append("Running from line " + lineStart + "\n");
						consoleArea.append("______________________________" + "\n");
						firstLine = false; 
					}
					System.out.println("Line "+lineIt.lineNumber + " is the end of the program.");
					consoleArea.append("______________________________" + "\n");
					consoleArea.append("The program ended properly at line " + lineIt.lineNumber + "\n");
					break;
				}
				else if(regexChecker(print, lineIt.instr)) {
					if(firstLine) {
						consoleArea.append("Running from line " + lineStart + "\n");
						consoleArea.append("______________________________" + "\n");
						firstLine = false; 
					}
					System.out.println("Line "+lineIt.lineNumber + " is a print statement");
					String instructions = lineIt.instr;
					printFunction(instructions);
				}
				else System.out.println("Line type is unknown for line " + lineIt.lineNumber);
			}	
		} 
	}
	

