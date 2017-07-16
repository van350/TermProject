package term.project.cis350;

public class ListModifier {
	private int 	movID;
	private boolean add;
	private String title;
	public ListModifier(int movieId, Boolean toAdd, String name){
		movID = movieId;
		add = toAdd;
		title = name;
	}
	
	public int getMovieID(){
		return movID;
	}
	
	public boolean isAdd(){
		return add;
	}
	
	public String getName(){
		return title;
	}
}
