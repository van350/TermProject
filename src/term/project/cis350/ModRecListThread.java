package term.project.cis350;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


import info.movito.themoviedbapi.TmdbLists;
import info.movito.themoviedbapi.model.core.SessionToken;

/**
 * This thread allows the gui to quickly update and 
 * offer new movies while the Movie DB is updated.
 * 
 * @author Chris
 *
 */
public class ModRecListThread {
	
	/**
	 * This queue handles the list of ListModifier objects,
	 *  allowing us to have several batches of 
	 *  recommendations ready to be uploaded.
	 */
	private Queue<List<ListModifier>> queue1 = 
			new LinkedList<List<ListModifier>>();
	
	/**
	 * Allows us to connect to the Movie DB.
	 */
	private SessionToken session;
	
	/**
	 * Allows use of list-related wrapper methods.
	 */
	private TmdbLists movList;
	
	/**
	 * saves the ID for the recommended list.
	 */
	private String recID;
	
	/**
	 * Constructor.
	 * 
	 * 
	 * @param recList			The TmdbLists object created 
	 * 							   elsewhere.
	 * 
	 * @param thisSession		The session token.
	 * 
	 * @param listID			The ID of the recommended list.
	 */
	public ModRecListThread(final TmdbLists recList, 
			final SessionToken thisSession, final String listID) { 
		movList = recList;
		session = thisSession;
		recID = listID;
	};
	
	/**
	 * Creates and runs a thread that adds 
	 * movies to the recommended list online.
	 * 
	 * @param movies	The batch of recommendations
	 * 		that need to be added to the online list.
	 */
	public void addToList(final List<ListModifier> movies) {
		
		
		queue1.add(movies);
		/*addRec.submit(new Runnable() {
			public void run(){
				addToRec();
			}
		});*/
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				addToRec();
			}
		});
		t.setDaemon(true);
		t.start();
		System.out.println("Thread Finished");
	}
	
	
	/**
	 * Adds the batches of recommendations to 
	 * the online recommended list on the Movie DB.
	 * 
	 */
	public void addToRec() {
		while (queue1.peek() != null) { 
			List<ListModifier> listDeQMovie = queue1.remove();
			System.out.println("List length in thred of rec list "
			+ listDeQMovie.size());
			for (ListModifier deQMovie : listDeQMovie) {
				
				Boolean isOnList = movList.isMovieOnList(recID,
						deQMovie.getMovieID());
				if (deQMovie.isAdd() && !isOnList) {
					//System.out.println(deQMovie.getName() 
					//+"Is the last name in add");
					movList.addMovieToList(session, 
							recID, 
							deQMovie.getMovieID());
					
				} else if (!deQMovie.isAdd() && isOnList) {
					//System.out.println(deQMovie.getName()
					//+ "Is the last name in take away");
					movList.removeMovieFromList(session, 
							
							recID, 
							deQMovie.getMovieID());
				}
				
			}
		}
	System.out.println("Done Working on threadList In Recommended");
	}
}

