
/**
Initially written by 
@author Cay Hortsman   BIG JAVA2

with major modificationn by Antonio Sanchez for
  Cosc  20203 Programming Techniques

@version 1.05 2019-10-15
*/

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;


public class Assignments  {
		
	Hashtable<String,Double>  symbolTable = new Hashtable<String,Double>();
	/**
	 * assigns the expression to the given variable, and stores this assignment into a hash table.
	 * @param line the String to be parsed for assigning an expression to a variable
	 */
	public void assignVar(String line) {
		Scanner in = new Scanner(line);
	    
	    EvaluatorVar e = new EvaluatorVar(symbolTable);
	    boolean execute = true;
	    while (in.hasNext())
	    { 
	      String input = in.nextLine();
	     input = input.replaceAll("\\s+",""); // skipping blanks
	      Variable value = e.getAssignmentValue(input);
          symbolTable.put(value.LHS,value.RHS);  // direct Double casting
		  System.out.println( value.LHS + "=" + value.RHS);

	    }
	}

		
}