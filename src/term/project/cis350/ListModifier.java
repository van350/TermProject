package term.project.cis350;

public class ListModifier {
	private int 	movID;
	private boolean add;
	public ListModifier(int movieId, Boolean toAdd){
		movID = movieId;
		add = toAdd;
	}
	
	public int getMovieID(){
		return movID;
	}
	
	public boolean isAdd(){
		return add;
	}
}
