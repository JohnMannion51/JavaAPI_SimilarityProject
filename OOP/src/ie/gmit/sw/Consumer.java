package ie.gmit.sw;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author jono-
 * Consumer Class that implements Runnable creates multiple consumer 
 * threads that create hash codes for each word 
 *
 */
public class Consumer implements Runnable 
{
	
	private BlockingQueue<Shingle> q;
	private int k;
	private int[] minHashes;
	private Map<Integer, List<Integer>> map = new ConcurrentHashMap<>();
	private ExecutorService pool;
	private int docCount = 2;
	
	/**
	 * @param q BlockingQueue hard coded
	 * @param k int hash codes
	 * @param poolSize int thread pool size
	 */
	public Consumer(BlockingQueue<Shingle> q, int k, int poolSize) 
	{
		super();
		this.q = q;
		this.k = k;
		pool = Executors.newFixedThreadPool(poolSize);
		init();
	}//constructor
	
	public void init() 
	{
		
		Random random = new Random();
		minHashes = new int[k];
		for (int i = 0; i < minHashes.length; i++) 
		{
			minHashes[i] = random.nextInt();
			//System.out.println(minHashes);
		}//for
	}//init

@Override
	public void run()
	{
	
		try {
			
			while (docCount > 0) 
			{
				Shingle s = q.take();
				if (s instanceof Poison) 
				{
					docCount--;
				} 
				else {
					pool.execute( new Runnable() 
					{
						
						/* (non-Javadoc)
						 * @see java.lang.Runnable#run()
						 */
						@Override
						public void run() 
						{
							for (int i = 0; i < minHashes.length; i++) 
							{
								int value = s.getHashcode() ^ minHashes[i]; // ^ - xor(Random generated key)
								List<Integer> list = map.get(s.getDocId());
								if (list == null) 
								{
									list = new ArrayList<Integer>();
									for (int j = 0; j < minHashes.length; j++) 
									{
										list.add(Integer.MAX_VALUE);
										//System.out.println(s.getDocId()+">>>>>>---"  + value);
										map.put(s.getDocId(), list);
									}//for
									
								} //if
								else 
								{
									if (list.get(i) > value) 
									{
										list.set(i, value);
									}//if ^
								}//else ^^
								/**
								 * @param jaccard
								 * This checks the similarity between each document and 
								 * informs the user how similar the documents are (%)
								 * 
								 */
								List<Integer>intersection = new ArrayList<Integer>(map.get(2));
								intersection.retainAll(map.get(1));
								
								float jaccard =((float)intersection.size())/
										((k)+((float)intersection.size()));
								System.out.println(((jaccard*2)*100) + " % similarity ");
								
							}//for ^^^
						}//run ^^^^
					});//runnable ^^^^^
					
				}//else
			}//while
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//catch

	}//run

}//consumer


