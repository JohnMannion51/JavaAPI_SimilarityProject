package ie.gmit.sw;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
/**
 * @author jono-
 * This is the Lanucher Class that is used to take the user inputs 
 * of the parsed files. The parsed files are then passed into 
 * a LinkedBlockingQueue, the threads containing the two parsed files 
 * are launched containing the file name, blocking queue size, shingle size, hash size and the docId.
 * Then a third consumer thread is launched to be used to compare 
 * similarity between the documents.
 */

public class Launcher {
	

	private static int shingleSize = 6;
	private static int k = 200;
	private static int poolSize = 50;
	private static int queueSize = 40;
	/**
	 * 
	 * Declared constant variables and shared variables of files
	 */
	public static void launch(String f1, String f2) throws Exception{
		/**
		 * 
		 * Launch method used to launch threads
		 */	
		BlockingQueue<Shingle> q = new LinkedBlockingQueue<>(queueSize);
		Thread t1 = new Thread(new DocumentParser(f1, q, shingleSize, k, 1),"T1");//worker thread
		Thread t2 = new Thread(new DocumentParser(f2, q, shingleSize, k, 2),"T2");//worker thread
		
		Thread t3 = new Thread(new Consumer( q, k, poolSize),"T3");//consumer thread
	
		t1.start();
		t2.start();
		t3.start();
		t1.join();
		t2.join();
		t3.join();
		
		
	}//launch
}//launcher