package ie.gmit.sw;
//library imports
/**
 *java.io imports
 */
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
/**
 *java.util imports
 */
import java.util.*;
import java.util.concurrent.BlockingQueue;

/**
 * @author jono-
 * This is the DocumentParser Class that implements Runnable.
 * This Class is used to parse the 2 files from the menu (user input). 
 * The parsed files are then placed in a Deque (linkedList) and a blocking queue 
 * is created that will contain the docID and the hashcode from the Shingle Class.
 * 
 */
public class DocumentParser implements Runnable {
	/**
	 * DocumentParser Class
	 * Declared variables, linked list and Blocking Queue
	 */
	
	private int docId;
	private Deque<String> buffer = new LinkedList<>();
	private BlockingQueue<Shingle> q;
	private String file;
	private int shingle, k;
	
	/**
	 * DocumentParser Constructor with super
	 * @param file String to be read in
	 * @param q BlockingQueue for shingles
	 * @param shingle shingle size (integer)
	 * @param k hash amounts (integer)
	 * @param docId document Id (integer)
	 */
	public DocumentParser(String file,BlockingQueue<Shingle> q,  int shingle, int k, int docId) {
		
		super();
		this.docId = docId;
		this.q = q;
		this.file = file;
		this.shingle = shingle;
		this.k = k;
	}
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			String line = null;
			while ((line = br.readLine()) != null) {
				
				String uLine = line.toUpperCase();
				String[] words = uLine.split("");
				addWordsToBuffer(words);
				Shingle s = getNextShingle();
				q.put(s);
			}
			flushBuffer();
			br.close();
			/**
			 * @author jono-
			 *exceptions to be caught
			 */
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * @param words String array
	 */
	private void addWordsToBuffer(String[] words) {
		
		for (String s : words) {
			buffer.addLast(s);
			//System.out.print(s);
		}

	}
	
	/**
	 * @return
	 */
	private Shingle getNextShingle() {
		
		StringBuilder sb = new StringBuilder();
		int counter = 0;
		while (counter < shingle) {
			if (buffer.peek() != null) {
				sb.append(buffer.poll());
				counter++;
			}
		}
		if (sb.length() > 0) {
			
			
			return (new Shingle(docId, sb.toString().hashCode()));
			
		} else {
			return null;
		}

	}
	
	/**
	 * @throws Exception
	 */
	private void flushBuffer() throws Exception{
		
		while(buffer.size()>0) {
			Shingle  s = getNextShingle();
			if(s!=null) {
				q.put(s);
			}else {
				q.put(new Poison(docId,0));
			}
		}
	}

}

