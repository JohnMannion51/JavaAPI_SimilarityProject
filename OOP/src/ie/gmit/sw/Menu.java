package ie.gmit.sw;

import java.util.Scanner;
/**
 * @author jono-
 * This is the Menu Class that uses the java.util.Scanner import which allows the user to input 
 * file names that then get passed to the Launcher Class to launch the threads.
 * The Menu Class also contains declared variables needed for the menu to run and 
 * the getter and setter methods for string file names.
 * The Menu Class also contains a basic menu for user input.
 * 
 */
public class Menu {
	
	
		private static Scanner scanner = new Scanner (System.in);
		/**
		 * @scanner
		 * Required for console input
		 */
		private String f1;// file 1
		private String f2;// file 2
		/**
		 *
		 * Declared variables
		 */
	
		
		
		/**
		 * @return the f1
		 */
		public String getF1() {
			return f1;
		}
		/**
		 * @param f1 the f1 to set
		 */
		public void setF1(String f1) {
			this.f1 = f1;
		}
		/**
		 * @return the f2
		 */
		public String getF2() {
			return f2;
		}
		/**
		 * @param f2 the f2 to set
		 */
		public void setF2(String f2) {
			this.f2 = f2;
		}
	public void show() {
		/**
		 * Menu options for user input that is kept alive using while loop
		 * 
		 */
		// TODO Auto-generated method stub
		System.out.println("==Document Similarity API==");
		System.out.println("Please enter file name 1");
		f1 = scanner.next();
		System.out.println("===========================");
		System.out.println("Please enter file name 2");
		f2 = scanner.next();
		
		try {
			Launcher.launch(f1, f2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}// show
		
		
	
}// Menu


