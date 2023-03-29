package edu.upenn.cit594.datamanagement;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {
	protected BufferedReader reader;
	protected boolean end = false;
	
	// TODO: Check edge cases from HOMEWORK 7

    public String[] readRow() throws Exception {
  	  // Initialize a list to store the fields of the row
  	  List<String> fields = new ArrayList<>();
  	  // Initialize a string builder to store the current field
  	  StringBuilder field = new StringBuilder();
  	  // Initialize a boolean flag to indicate if the field is escaped
  	  boolean escaped = false;
  	  // Initialize an int variable to store the current character
  	  int c;

  	  // Check if stream is over
  	  if (end) return null;
  	  
  	  // Loop until end of file or end of line
  	  while (true) {
  		c = reader.read();
  		
  	    // DOUBLE QUOTES
  		if (c == '"') {
  	      // If the field is escaped
  	      if (escaped) {
  	        // Peek if the next character is also a double quote
  	    	int next = reader.read();
  	        if (next == '"') {
  	          // Append one double quote to the field and skip next character
  	          field.append('"');
  	          continue;
  	        } else {
  	          // End of escaped field
  	          escaped = false;
  	          c = next;
  	        }
  	      } else {
  	        // Start of escaped field
  	        escaped = true;
  	        continue;
  	      }
  		}
  		
  		// END OF STREAM
          if (c == -1) {
  	    	if(!fields.isEmpty()) fields.add(field.toString());
  	    	end = true;
              break;
          }
  		
  		// CARRIAGE RETURN (optional in CRLF but needs to be skipped)
  		if (c == 13) continue;
  		
  		// LINE FEED (must be escaped)
  		if(c == 10 && !escaped) {
  	    	// Add the last field to the list
  	    	fields.add(field.toString());
  	        field.setLength(0);
              break;
  		}
  	      
  	    // COMMA
  	   if (c == ',') {
  	      if (!escaped) {
  	        // Add the current field to the list and clear it for next field
  	        fields.add(field.toString());
  	        field.setLength(0);
  	        continue;
  	      } else {
  	        // Append comma to the escaped field as part of content
  	        field.append(',');
  	        continue;
  	      }
  	   }
  	   
  	    // TEXT DATA
  	    // Append any other character to the current field
  	    field.append((char) c);
  	    
  	  }
  	  
  	  // Convert list into array and return it 
  	  String[] row = fields.toArray(new String[fields.size()]);
  	  
  	  if(fields.isEmpty() && end) return null;
  	      	      	 
  	  return row;
  	  
  }
}
