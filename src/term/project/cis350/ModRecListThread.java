package term.project.cis350;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import info.movito.themoviedbapi.TmdbAccount;
import info.movito.themoviedbapi.TmdbLists;
import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.TmdbMovies.MovieMethod;
import info.movito.themoviedbapi.model.MovieDb;
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
	
	private TmdbMovies moviesTool;
	
	private TmdbAccount tmdbAccount;
	
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
			final SessionToken thisSession, final String listID, final TmdbAccount tmdbAccount, TmdbMovies moviesTool) { 
		movList = recList;
		session = thisSession;
		recID = listID;
		this.tmdbAccount = tmdbAccount;
		this.moviesTool = moviesTool;
	};
	
	
	
	
	
	public void getSimilarMoviesTwo(LocMov currentMovie, boolean isPositive, List<LocMov> recList, int rating){
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				
				getSimilarMovies(currentMovie, isPositive, recList);
				rateMovie(currentMovie, rating);
				
				
			}
			});
		t.setDaemon(true);
		t.start();
		System.out.println("Thread Finished");
		
	}
	
	public void getSimilarMovies(LocMov currentMovie, boolean isPositive, List<LocMov> recList){
		
		
		List<ListModifier> listMod = new ArrayList<ListModifier>();
		List<MovieDb> listMovieDb = new ArrayList<MovieDb>();
		
		
		
		List<MovieDb> newRecs = moviesTool.getSimilarMovies(
				currentMovie.getid(), "en-US", 0).getResults();
		
		
		
		for (MovieDb movie: newRecs) {
			MovieDb tMov = moviesTool
					.getMovie(
						movie.getId(),
						"en-US",
						MovieMethod.values());
			
			
			listMod.add(new ListModifier(
					tMov.getId(),
					isPositive, tMov.getTitle()));
			listMovieDb.add(tMov);
			
			LocMov tmovie = new LocMov(tMov);
		if (!recList.contains(tmovie)) {
			Search.addToLocalRecList(tmovie);
		}
		}
		
		
		if (listMod.size() > 0) {
			addToList(listMod);
		}
		
		
		for (MovieDb movie: newRecs) {
			
			
			
			listMod.add(new ListModifier(
					movie.getId(),
					isPositive, movie.getTitle()));
			
			
			//listMovieDb.add(movie);
		}
		
		
		
		
	}
	
	
	
	
	
	/**
	 * Assigns the user's rating to the current movie.  
	 * 
	 * @param rating	the rating that the user wants to assign to 
	 * 	the movie.
	 * 
	 * FIXME:
	 * 		We ought to move this to the thread too.
	 * */
	public void rateMovie(final LocMov currentMovie, final int rating) {
		 long startTime;
			
		 long endTime;
		
		 long elapsedTime;
		startTime = System.currentTimeMillis();
		tmdbAccount.postMovieRating(session,
				currentMovie.getid(), rating);
	
		endTime = System.currentTimeMillis();
		elapsedTime = endTime - startTime;
		System.out.println("Time cost to run rateMovie is: " + elapsedTime);
		
	}
	
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

