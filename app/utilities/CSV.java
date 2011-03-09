package utilities;

import java.io.*;
import java.util.*;

public class CSV {

	ArrayList<ArrayList<Cell>> data = new ArrayList<ArrayList<Cell>>();
	ArrayList<String> head = new ArrayList<String>();
	private Boolean header;

	private static class Cell<Type extends Comparable<? super Type>> {
		Type content;

		Cell(Type cont) {
			content = cont;
		}

		public Type getContent() {
			return this.content;
		}

		public String toString() {
			return this.content.toString();
		}
	}

	public Boolean hasHeader() {
		return this.header;
	}
	
	public ArrayList<String> getHeader() {
		return this.head;
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

	public CSV(File csv, Boolean header, String seperator) {
		this.header = header;

		try {
			FileInputStream fstream = new FileInputStream(csv);

			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			if (header) {
				String[] headers = br.readLine().split(seperator);
				for (String h : headers) {
					head.add(h);
				}
			}
			while ((strLine = br.readLine()) != null) {
				String[] line = strLine.split(seperator);
				ArrayList<Cell> row = new ArrayList<Cell>();
				for (String l : line) {
					Cell<String> cell = new Cell(l);
					row.add(cell);
				}
				data.add(row);
			}
			in.close();
		} catch (IOException e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
	}

	public void print() {
		int counter = 0;
		String[][] cont = this.getArray();
		for (int c = 0; c < cont.length; c++) {
			for (int cc = 0; cc < cont[c].length; cc++) {
				System.out.print(cont[c][cc]);
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