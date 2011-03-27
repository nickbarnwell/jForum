package utilities;

import java.io.*;
import java.util.*;
/**
 * This is a basic implementation of a CSV parser using 2D arrays and loops.
 * @author Nick Barnwell
 * @mastery Parsing a text file or other data stream
 * @mastery Aspect #8: Encapsulation
 * @mastery Arrays of 2 or more dimensions
 */
public class CSV {
	private ArrayList<ArrayList<Cell>> data = new ArrayList<ArrayList<Cell>>(); //Creates an array of arrays to hold the cell data 
	private ArrayList<String> head = new ArrayList<String>(); //Keep track of header
	private Boolean header;

	private static class Cell<Type extends Comparable<? super Type>> {
		Type content;
		
		Cell(Type cont) {
			content = cont;
		}
		public Type getContent() {
			return content;
		}

		public String toString() {
			return content.toString();
		}
	}

	public Boolean hasHeader() {
		return header;
	}
	
	public ArrayList<String> getHeader() {
		return head;
	}


	public ArrayList<ArrayList<String>> getData() {
		ArrayList<ArrayList<String>> dat = new ArrayList<ArrayList<String>>();
		for(ArrayList<Cell> row:data) {
			ArrayList<String> rows = new ArrayList<String>();
			for(Cell cell:row) {
				rows.add(cell.toString());
			}
			dat.add(rows);
		}
		return dat;
	}
	/**
	 * Returns CSV as an array of strings. Useful for JSOn serialization
	 * @return String[][] array
	 * 
	 * @mastery Aspect #19: Arrays of 2 or more dimension
	 */
	public String[][] getArray() {
		String[][] array = new String[data.size()][head.size()];
		int counter = 0;
		for (ArrayList<Cell> row : data) {
			for (int c = 0; c < row.size(); c++) {
				array[counter][c] = row.get(c).toString();
			}
			counter++;
		}
		return array;
	}
	/**
	 * Constructor for the CSV 
	 * @param csv CSV POSTed as multipart file.
	 * @param header Whether or not a header exists
	 * @param seperator The seperator used for the CSV parser
	 * 
	 * @mastery Aspect #9: Parsing a text file or other data stream
	 */
	public CSV(File csv, Boolean header, String seperator) {
		this.header = header;
		
		try {
			//Open input streams
			FileInputStream fstream = new FileInputStream(csv);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine; //Initalize string for each line
			if (header) { //Parse header
				//Split into string array on seperator
				String[] headers = br.readLine().split(seperator); 
				for (String h : headers) {
					head.add(h); //Foreach string in array add to arraylist
				}
			}
			//While lines left in file, loop and add row to array of rows
			while ((strLine = br.readLine()) != null) {
				String[] line = strLine.split(seperator);
				ArrayList<Cell> row = new ArrayList<Cell>();
				for (String l : line) {
					Cell<String> cell = new Cell(l);
					row.add(cell);
				}
				data.add(row);
			}
			in.close();//Close file. Discards it from temp
		} catch (IOException e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
	}
	/**
	 * Use 2D Arrays to Print CSV for debugging purposes
	 * 
	 * @mastery Aspect 19
	 */
	public void print() {
		int counter = 0;
		String[][] cont = this.getArray();
		//Loop through rows and columns, print
		for (int row= 0; row < cont.length; row++) {
			for (int column = 0; column < cont[row].length; column++) {
				System.out.print(cont[row][column]);
			}
			System.out.println();
			counter++;
		}
	}

	//
//	public static void main(String[] args) {
//		File text = new File(
//				"C:\\Users\\Nick Barnwell\\Documents\\Eclipse Workspace Work\\jforum\\app\\utilities\\emaillist.csv");
//		CSV csv = new CSV(text, true, ",");
//		csv.print();
//	}
}