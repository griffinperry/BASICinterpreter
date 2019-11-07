
/**
Initially written by 
@author Cay Hortsman   BIG JAVA2

with major modificationn by Antonio Sanchez for
  Cosc  20203 Programming Techniques

@version 1.05 2019-10-15
*/

/**  float, characters and integers

 * This class breaks up a string dscribing an expression
 * into tokens: numbers, parentheses, and operators.
 * It assumes that there are no other characters in the string.
 * Notice that blanks are not allowed.
 */
public class ExpressionTokenizerVar {
	
	private String input;
	private int start;
	private int end;
	final boolean verbose = true;
	
	/**
	 * Constructs a tokenizer.
	 * with vairation of storing a variable in a hash table
	 * @param anInput the string to tokenizer
	 */
		//public void resetExpressionTokenizer(String anInput) {
		public ExpressionTokenizerVar(String anInput) {
	    input = anInput;
		start = 0;
		end = 0;
		nextToken();	//sets start and end for first token
	}

	
	/**
	 * Peeks at the next token without consuming it.
	 * @return the next token or null if there are no more tokens.
	 */
	public String peekToken() {
	   	if (start >= input.length())
			{   if (verbose)  System.out.println  ("PEEK TOKEN  with Lenght of " + (end- + start)  + " at " + start + " String is " + input.substring(start, end)  );  
		      return null;}
	   return input.substring(start, end);
	}
	
	/**
	 * Gets the next token and moves the tokenizer to the following token.
	 * @return the next token or null if there are no more tokens.
	 */
	public String nextToken() {
		String r = peekToken();
		start = end;
		if (verbose)  System.out.println  ("next token  with start at " + start   ); 
	   if (start >= input.length())
			 { if (verbose)  System.out.println  ("token delivered => " + r);return r;}		
		if(nextTokenFloat( r))  return r;
		 if (nextTokenInteger(r))  return r;
		 if (nextTokenVar(r))   return r;
		 if (verbose)  System.out.println  ("token delivered => " + r);
		end = start + 1; 
		
		return r;
	}
   /**
	 * Gets the next token and moves the tokenizer to the following token of an integer
	 * 
	 * @return the next token or null if there are no more tokens.
	 */
	public boolean nextTokenInteger(String r) {
		
	   if (verbose)  System.out.println  ("Digit at location  " + start);
	   if (Character.isDigit(input.charAt(start))) {
			end = start + 1;
			while (end < input.length() &&
					Character.isDigit(input.charAt(end)))
				end++;
	      
	       return true;				
		}
        else return false;
     }
 /**
	 * Gets the next token and moves the tokenizer to the following token of an variable
	 * @return the next token or null if there are no more tokens.
	 */
	public boolean nextTokenVar(String r) {
		
	  if ( Character.isLetter(input.charAt(start)) ) 
		    {   
		       if (verbose)  System.out.println  ("letter at location " + start);
		       end = start + 1;
		       while (end < input.length() &&  
					  Character.isLetterOrDigit(input.charAt(end))) end++;  
			
	       return true;   
		    }
		   
		    else return false;
		 }

  /**
	 * Gets the next token and moves the tokenizer to the following token of an integer
	 * @return the next token or null if there are no more tokens.
	 */
	public boolean nextTokenFloat(String r) {
		
	   if (verbose)  System.out.println  ("Digit at location " + start);
	   if (Character.isDigit(input.charAt(start))) {
			end = start + 1;
			while (end < input.length() &&
					Character.isDigit(input.charAt(end)))
				end++;
		   if (end < input.length() && input.charAt(end)=='.')  // check if it has a fraction
		      {end++;
		        while (end < input.length() &&
					Character.isDigit(input.charAt(end)))
				end++;
			   }	
	        
	       return true;				
		}
        else return false;
     }

		   
	
}
