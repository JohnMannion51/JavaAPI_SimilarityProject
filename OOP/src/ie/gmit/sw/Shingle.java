package ie.gmit.sw;

/**
 * @author jono-
 * This is the Shingle Class used to create hash code for similarity check
 */
public class Shingle {

	private int docId;
	private int hashcode;
	
	public Shingle() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param docId id number given to each document
	 * @param hashcode assigns hash code to each shingle
	 */
	public Shingle(int docId, int hashcode) {
		
		super();
		this.docId = docId;
		this.hashcode = hashcode;
	}

	/**
	 * @return the docId
	 */
	public int getDocId() {
		return docId;
	}

	/**
	 * @param docId the docId to set
	 */
	public void setDocId(int docId) {
		this.docId = docId;
	}

	/**
	 * @return the hashcode
	 */
	public int getHashcode() {
		return hashcode;
	}

	/**
	 * @param hashcode the hashcode to set
	 */
	public void setHashcode(int hashcode) {
		this.hashcode = hashcode;
	}

	
}
