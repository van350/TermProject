package term.project.cis350;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import info.movito.themoviedbapi.TmdbLists;
import info.movito.themoviedbapi.model.core.SessionToken;

public class ModListThread {
	ExecutorService addRec = Executors.newFixedThreadPool(10);
	static Queue queue = new LinkedList();
	private SessionToken session;
	private TmdbLists movList;
	private String recID;
	
	public ModListThread(TmdbLists recList, SessionToken thisSession, String listID) { 
		movList = recList;
		session = thisSession;
		recID = listID;
	};
	
	public void addToRec(ListModifier movie){
		queue.add(movie);
		addRec.submit(new Runnable() {
			public void run(){
				addToRec();
			}
		});
	}
	
	public void addToRec(){
		while(queue.peek() != null){
			ListModifier deQMovie = (ListModifier)queue.remove();
			Boolean isOnList = movList.isMovieOnList(recID, deQMovie.getMovieID());
			if(deQMovie.isAdd() && !isOnList){
				movList.addMovieToList( session, recID, deQMovie.getMovieID() );
			}else if(isOnList){
				movList.removeMovieFromList( session, recID, deQMovie.getMovieID() );
			}
		}
	}
}

