package term.project.cis350;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import info.movito.themoviedbapi.TmdbLists;
import info.movito.themoviedbapi.model.core.SessionToken;

public class ModRecListThread {
	private Queue<List<ListModifier>> queue1 = new LinkedList<List<ListModifier>>();
	private SessionToken session;
	private TmdbLists movList;
	private String recID;
	
	public ModRecListThread(TmdbLists recList, SessionToken thisSession, String listID) { 
		movList = recList;
		session = thisSession;
		recID = listID;
	};
	
	public void addToList(List<ListModifier> movies) {
		
		
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
	
	public void addToRec() {
		while (queue1.peek() != null) { 
			List<ListModifier> listDeQMovie = queue1.remove();
			System.out.println("List length in thred of rec list " + listDeQMovie.size());
			for (ListModifier deQMovie : listDeQMovie) {
				
				Boolean isOnList = movList.isMovieOnList(recID, deQMovie.getMovieID());
				if (deQMovie.isAdd() && !isOnList) {
					//System.out.println(deQMovie.getName() + "Is the last name in add");
					movList.addMovieToList(session, recID, deQMovie.getMovieID());
					
				} else if (!deQMovie.isAdd() && isOnList) {
					//System.out.println(deQMovie.getName() + "Is the last name in take away");
					movList.removeMovieFromList(session, recID, deQMovie.getMovieID());
				}
				
			}
		}
	System.out.println("Done Working on threadList In Recommended");
	}
}

