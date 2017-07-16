package term.project.cis350;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import info.movito.themoviedbapi.TmdbLists;
import info.movito.themoviedbapi.model.core.SessionToken;

public class ModWatchListThread {
	private Queue<List<ListModifier>> queue = new LinkedList<List<ListModifier>>();
	private SessionToken session;
	private TmdbLists movList;
	private String recID;
	
	public ModWatchListThread(TmdbLists recList, SessionToken thisSession, String listID) { 
		movList = recList;
		session = thisSession;
		recID = listID;
	};
	
	public void addToList(List<ListModifier> movies) {
		
		
		queue.add(movies);
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
	
	public void addToRec() {
		while (queue.peek() != null) { 
			
			List<ListModifier> listDeQMovie = (List<ListModifier>) queue.remove();
			for (ListModifier deQMovie : listDeQMovie) {
				
				Boolean isOnList = movList.isMovieOnList(recID, deQMovie.getMovieID());
				if (deQMovie.isAdd() && !isOnList) {
					System.out.println(deQMovie.getName() + "Is the last name in add");
					movList.addMovieToList(session, recID, deQMovie.getMovieID());
				} else if (!deQMovie.isAdd() && isOnList) {
					System.out.println(deQMovie.getName() + "Is the last name in take away");
					movList.removeMovieFromList(session, recID, deQMovie.getMovieID());
				}
			}
		}
		System.out.println("Done Working on threadList In ToWatch");
	}
}

