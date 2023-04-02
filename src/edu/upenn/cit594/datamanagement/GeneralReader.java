package edu.upenn.cit594.datamanagement;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * This superclass has utility methods that are needed by the specific readers,
 * from sorting indexes to validating inputs to a CSV lexer.
 */

public class GeneralReader {
	
	// This class requires a reader (to be opened in extended class) and a boolean to define when the file is over
	protected BufferedReader reader;
	protected boolean end = false;
	
	/**
	 * Helper method to read a row of a CSV file and return tokens (CSV Lexer)
	 * @return Row (String []) : Row with values from CSV files
	 * @throws Exception : if error when reading the characters
	 */
    protected String[] readRow() throws Exception {
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
  	  
  	  // Loop and read each character at time until end of file or end of line
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
        	// Check if there is a field to be added to the List
  	    	if(!fields.isEmpty()) fields.add(field.toString());
  	    	// Set flag to true and break out of loop
  	    	end = true;
            break;
          }
  		
  		// CARRIAGE RETURN (optional in CRLF but needs to be skipped)
  		if (c == 13) continue;
  		
  		// LINE FEED (must be escaped)
  		if(c == 10 && !escaped) {
  	    	// Add the last field to the list, reset field length and break out of loop
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
  	  
  	  // If there are no fields to display and the end of the file has been reached, return null
  	  if(fields.isEmpty() && end) return null;
  	  // Otherwise, return the Array with the values of the row
  	  return row;
  }
    
    /**
     * Helper method to determine indexes of specified parameters
     * @param header (String[]) : array of strings with information about the header of the file
     * @param parameters (String[]) : array of strings to be matched in header
     * @return indexes (int[]) : array with indexes of matched parameters
     * @throws Exception : if header does not have any of the specified parameters
     */
    protected int[] getIndexes(String[] header, String[] parameters) throws Exception {
        // Create array with indexes for return
        int[] indexes = new int[parameters.length];
        Arrays.fill(indexes, -1);

        // Loop through the header and try to match each element to specified parameters
        for(int i = 0; i < header.length; i++) {
            for(int j = 0; j < parameters.length; j++) {
                if(header[i].equals(parameters[j])) {
                    indexes[j] = i;
                }
            }
        }
        // Check if all indexes were found, throw an Exception if not
        for(int index : indexes) {
            if(index == -1) {
                throw new Exception();
            }
        }
        // Return valid indexes
        return indexes;
    }

	
	/**
	 * Helper method to validate whether a Zip code is valid or not
	 * @param zip (String) : Zip code to be validated
	 * @param argument (String) : condition to determine if Zip is valid or not
	 * @return validatedZip (String) : Validated zip code or null if invalid
	 */
	protected String validateZip(String zip, String argument) {
		// Create new String for return
		String validatedZip = null;
		// Create pattern to compare
		Pattern pattern = Pattern.compile(argument);
		
		// If pattern matches, validate the Zip and return
		if(pattern.matcher(zip).find()) {
			validatedZip = zip;
		}
		
		return validatedZip;
	}
	
	/**
	 * Helper method to validate whether an integer is valid or not
	 * @param integer (String) : integer to be validated
	 * @param argument (String) : condition to determine if integer is valid or not
	 * @return validatedInteger (int) : Validated integer or -1 if invalid
	 */
	protected int validateInteger(String number, String argument) {
		// Create new int for return
		int validatedInteger = -1;
		// Create pattern to compare 
		Pattern pattern = Pattern.compile(argument);
		
		// If pattern matches, validate integer and return
		if(pattern.matcher(number).find()) {
			validatedInteger = Integer.parseInt(number);
		}
		
		return validatedInteger;
	}
	
	/**
	 * Helper method to validate whether an float is valid or not
	 * @param number (String) : float to be validated
	 * @param argument (String) : condition to determine if float is valid or not
	 * @return validatedFloat (float) : Validated float or -1 if invalid
	 */
	protected float validateFloat(String number, String argument) {
		// Create new float for return
		float validatedFloat = -1;
		// Create pattern to compare
		Pattern pattern = Pattern.compile(argument);
		
		// If pattern matches, validate float and return
		if(pattern.matcher(number).find()) {
			validatedFloat = Float.parseFloat(number);
		}
		
		return validatedFloat;
	}
	
	/**
	 * Helper method to validate whether a string is a valid date
	 * @param date (String) : string with date information
 	 * @return validatedDate (String): Validated date in a string format
	 */
	protected String validateDate(String date) {
		// Create new string to store validated date
		String validatedDate = null;
		// Compile pattern to match a date in the format YYYY:MM:DD hh:mm:ss
		Pattern pattern = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2}$");
		
		// If the pattern matches, split the string and grab just the YYYY:MM:DD information for the new string
		if(pattern.matcher(date).find()) {
			String[] splitDate = date.split("\\s+", 2);
			validatedDate = splitDate[0];
		}
		
		return validatedDate;
	}
}
