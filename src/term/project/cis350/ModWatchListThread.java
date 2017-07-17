package term.project.cis350;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


import info.movito.themoviedbapi.TmdbLists;
import info.movito.themoviedbapi.model.core.SessionToken;

/**
 * This thread allows the gui to quickly update and 
 * offer new movies while the Movie DB is updated.
 * Differs from ModRecListThread by focusing on
 * the watch later list instead of the recommended
 * movie list.
 * 
 * @author Chris
 *
 */
public class ModWatchListThread {
	
	/**
	 * This queue handles the list of ListModifier objects,
	 *  allowing us to have several movies
	 *   ready to be uploaded.
	 */
	private Queue<List<ListModifier>> queue 
	= new LinkedList<List<ListModifier>>();
	
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
	 * @param listID			The ID of the watch later list.
	 */
	public ModWatchListThread(final TmdbLists recList,
			final SessionToken thisSession, final String listID) { 
		movList = recList;
		session = thisSession;
		recID = listID;
	};
	
	
	/**
	 * Creates and runs a thread that adds 
	 * movies to the watch later list online.
	 * 
	 * @param movies	movie that needs to be added to the online list.
	 */
	public void addToList(final List<ListModifier> movies) {
		
		
		queue.add(movies);
		/*addRec.submit(new Runnable() {
			public void run(){
				addToRec();
			}
		});*/
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				addToRec();
			}
		});
		t.setDaemon(true);
		t.start();
		System.out.println("Thread Finished");
	}
	
	
	
	/**
	 * Adds the movies to 
	 * the online watch later list on the Movie DB.
	 * 
	 */
	public void addToRec() {
		while (queue.peek() != null) { 
			
			List<ListModifier> listDeQMovie = 
					(List<ListModifier>) queue.remove();
			for (ListModifier deQMovie : listDeQMovie) {
				
				Boolean isOnList = movList.isMovieOnList(
						recID, deQMovie.getMovieID());
				if (deQMovie.isAdd() && !isOnList) {
					System.out.println(deQMovie.getName()
							+ "Is the last"
							+ " name in add");
					movList.addMovieToList(session, recID,
							deQMovie.getMovieID());
				} else if (!deQMovie.isAdd() && isOnList) {
					System.out.println(deQMovie.getName() 
							+ "Is the last name"
							+ " in take away");
					movList.removeMovieFromList(session,
							recID, deQMovie
							.getMovieID());
				}
			}
		}
		System.out.println("Done Working on threadList In ToWatch");
	}
}

