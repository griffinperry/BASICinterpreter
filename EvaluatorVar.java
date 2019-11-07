
/**
Initially written by 
@author Cay Hortsman   BIG JAVA2

with major modificationn by Antonio Sanchez for
  Cosc  20203 Programming Techniques

@version 1.05 2019-10-15
*/



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class EvaluatorVar {
    final boolean verbose = true;
	private ExpressionTokenizerVar tokenizer;
	public Hashtable  symbolTable;  // Hashtable<String,Integer>;
	
	/**
	 * Constructs an evaluator.
	 * @param inSymbolTable a Hashtable to be assigned to symbolTable
	 */
	
public EvaluatorVar(Hashtable inSymbolTable)
	  { symbolTable = inSymbolTable; }
	
/**
	 * Evaluates the expression.
	 * @param Expression the string to determine the assignment value from
	 * @return the value of the assignment expression
	 */
	public Variable getAssignmentValue(String Expression) {
	    Variable value = new Variable();
	    value.RHS = 0;
	    tokenizer = new ExpressionTokenizerVar(Expression);
	    String next = tokenizer.peekToken();
		value.LHS = tokenizer.nextToken();  // determine variable name 
	    next = tokenizer.peekToken();  
	    if ("=".equals(next) )
			   { tokenizer.nextToken();  // discard =
			     value.RHS = getExpressionValue();   // determine variable value
			    }
			   else System.out.println  ("Assignment error" ); 
	    if (verbose)  System.out.println  ("value delivered Assign " + value.RHS); 	   
		return value;
	}
	




	/**
	 * Evaluates the expression.
	 * @return the value of the expression
	 */
	public double getExpressionValue() {  
		double value = getTermValue();  // go search for * /  terms
		boolean done = false;
		while(!done) {
			String next = tokenizer.peekToken();
			if ("+".equals(next) || "-".equals(next)) {
				tokenizer.nextToken();	//Discard the "+" or "-"
				double value2 = getTermValue();
				if ("+".equals(next)) value += value2;
				else value -= value2;
			}
			else done = true;
		}
		return value;
	}
	
	/**
	 * Evaluates the next term in the expression.
	 * @return the value of the term
	 */
	public double getTermValue() {
		double value = getFactorValue(); // go search for ( )  
		boolean done = false;
		while(!done) {
			String next = tokenizer.peekToken();
			if ("*".equals(next) || "/".equals(next)) {
				tokenizer.nextToken();	//Discard the "*" or "/"
				double value2 = getFactorValue();
				if ("*".equals(next)) value *= value2;
				else value /= value2;
			}
			else done = true;
		}
		return value;
	}
	
	/**
	 * Evaluates the next factor found in the expression.
	 * @return the value of the factor
	 */
	public double getFactorValue() {
		double value; String variable;
		String next = tokenizer.peekToken();
		if ("(".equals(next)) {
			tokenizer.nextToken();	//Discard the "("
			value = getExpressionValue();  //  recursively go back to expression value
			tokenizer.nextToken();	//Discard the ")"
		}
		  else { variable = tokenizer.nextToken();
		         try { value = Double.parseDouble(variable); }
                 catch (NumberFormatException e)      
                   {  // try a set of Coded values for  value in the hashtable
                      if (symbolTable.containsKey(variable)) 
                         value = (double) (symbolTable.get(variable)) ;
                         else value = 0;
                 }
               }   
        if (verbose)  System.out.println  ("Factor value delivered " + value); 
        return value;
	}	
}
