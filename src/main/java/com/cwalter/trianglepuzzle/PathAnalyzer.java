package com.cwalter.trianglepuzzle;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;

/**
 * @author Chris Walter
 *
 * Finds the maximum value for a path of nodes in a file provided by command line argument.
 * This solution is optimized for memory usage and speed. Please note however, that due to these
 * optimizations no other information regarding the file is available including the path that was
 * taken. This allows us to run with very little memory used and would allow this 
 * solution to scale out to extremely large file sizes.
 * 
 * Also please note that this solution is incompatible with unbalanced trees as the documentation
 * provided gave no reason to believe one would be analyzed.
 */
public class PathAnalyzer {
	
	private PrintStream out;
	
	private PrintStream err;
	
	/**
	 * Analyzes the paths of a file.
	 * 
	 * @param out Stream results will be printed to.
	 * @param err Stream errors will be printed to.
	 */
	public PathAnalyzer(PrintStream out, PrintStream err) {
		if(out == null) {
			throw new IllegalArgumentException("out cannot be null.");
		}
		
		if(err == null) {
			throw new IllegalArgumentException("err cannot be null");
		}
		
		this.out = out;
		this.err = err;
	}
	
	/**
	 * Retrieves the value of the highest path from a file.
	 * @param fileName Name of a file.
	 * @param delimeter used by 
	 * @return maximum path from a file.
	 * @throws ProcessException thrown when there is an error processing the file
	 */
	public int getMaxPathFromFile(String fileName, String delimeter) throws ProcessException {
		if(fileName == null) {
			throw new IllegalArgumentException("FileName cannot be null.");
		}
		
		if(delimeter == null) {
			throw new IllegalArgumentException("Delimeter cannot be null.");
		}
		
		try(BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
			return getMaxPathFromReader(reader, delimeter);
		} catch (FileNotFoundException ex) {
			err.println("File '" + fileName + "' not found: " + ex.getMessage());
			throw new ProcessException("Unable to find file.", ex);
		} catch (IOException ex) {
			err.println("Error reading file '" + fileName + "' : " + ex.getMessage());
			throw new ProcessException("Unable to read file.", ex);
		} catch(NumberFormatException ex) {
			err.println("Unexpected error converter string to integer.");
			throw new ProcessException("Unable to parse file.", ex);
		}
	}
	
	/**
	 * @param fileName Name of the file we will be identifying the max path value of.
	 * @param delimeter Value that we should be splitting each line on.
	 * @return The max path found in the file. -1 if an error is encountered.
	 * @throws IOException thrown if there is an error reader the file.
	 * @throws NumberFormatException thrown if there is an error parsing integers.
	 */
	public int getMaxPathFromReader(BufferedReader reader, String delimeter) throws NumberFormatException, IOException {
			if(reader == null) {
				throw new IllegalArgumentException("Reader cannot be null.");
			}
			
			if(delimeter == null) {
				throw new IllegalArgumentException("Delimeter cannot be null.");
			}
			
			String line = getNext(reader);
			if(line == null) {
				err.println("Reader has no content.");
				return 0;
			}
			
			String[] nodeValues;
			int[] previousMaxPaths = { Integer.parseInt(line) };
			int[] currentMaxPaths;
			int currentValue;
			int leftIndex;
			int rightIndex;
			int leftValue;
			int rightValue;
			int placeAt;
			String node;
			String[] tempNodeValues;
			while((line = getNext(reader)) != null) {
				nodeValues = line.split(delimeter);
				tempNodeValues = new String[nodeValues.length];
				
				placeAt = 0;
				for(int i = 0; i < nodeValues.length; i++) {
					node = nodeValues[i].trim();
					if(!node.isEmpty()) {
						tempNodeValues[placeAt++] = node;
					}
				}
				
				//Line had extra spaces. Clean it up.
				if(placeAt != nodeValues.length) {
					nodeValues = new String[placeAt];
					System.arraycopy(tempNodeValues, 0, nodeValues, 0, placeAt);
				}
				
				if(nodeValues.length != previousMaxPaths.length + 1) {
					err.println("File does not expand by one record per row as expected.");
					return -1;
				}
				
				currentMaxPaths = new int[nodeValues.length];
				for(int i = 0; i < nodeValues.length; i++) {
					leftIndex = i - 1;
					rightIndex = i;
					currentValue =  Integer.parseInt(nodeValues[i]);
					
					if(leftIndex == -1) {
						leftValue = 0;
					} else {
						leftValue = previousMaxPaths[leftIndex];
					}
					
					if(rightIndex == previousMaxPaths.length) {
						rightValue = 0;
					} else {
						rightValue = previousMaxPaths[rightIndex];
					}
					
					if(leftValue > rightValue) {
						currentMaxPaths[i] = currentValue + leftValue;
					} else {
						currentMaxPaths[i] = currentValue + rightValue;
					}
				}
				previousMaxPaths = currentMaxPaths;
			}
			
			int maxPath = previousMaxPaths[0];
			for(int i = 1; i < previousMaxPaths.length; i++) {
				if(previousMaxPaths[i] > maxPath) {
					maxPath = previousMaxPaths[i];
				}
			}
			return maxPath;
	}
	
	public String getNext(BufferedReader reader) throws IOException {
		if(reader == null) {
			throw new IllegalArgumentException("Reader cannot be null.");
		}
		
		String line;
		while((line = reader.readLine()) != null) {
			line = line.trim();
			if(!line.isEmpty()) {
				return line;
			}
		}
		return null;
	}
	
	public void printMaxPath(int maxPath) {
		if(maxPath == -1) {
			err.println("Error encountered while evaluating max path. Please review console output.");
		} else {
			out.println("Max path of '" + maxPath + "' found");
		}
	}

	/**
	 * @param args First argument should be the name of the file we will be processing.
	 */
	public static void main(String... args) {
		if(args == null || args.length != 1) {
			throw new IllegalArgumentException("Please provide the name of the file you wish to process.");
		}
		
		String fileName = args[0];
		
		PathAnalyzer maxPath = new PathAnalyzer(System.out, System.err);
		
		try {
			maxPath.printMaxPath(maxPath.getMaxPathFromFile(fileName, " "));
		} catch(IllegalArgumentException ex) {
			System.err.println(ex.getMessage());
		} catch (ProcessException ex) {
			System.err.println(ex.getMessage());
		}
	}
	
}
