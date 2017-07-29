package term.project.cis350;

import java.util.List;

import info.movito.themoviedbapi.model.MovieDb;

/**
 * The ListModifier class holds one movie in the form of:
 * Its integer MovieId, 'movID'
 * A boolean flag to indicate if this movie was rated highly, i.e. 'add'
 * A String that holds the movies title, 'title'
 * @author Siberian 
 *
 */
public class ListModifier {
	
	/**
	 * The integer equivalent of this movie, or 'movieID'.
	 */
	private int 	movID;
	
	/**
	 * Used to determine if the movie is a candidate 
	 * to be added to a recommendation or watch list.
	 */
	private boolean add;
	
	/**
	 * Used to hold this movies title.
	 */
	private String title;
	
	/**
	 * This Constructor generates an object associated with this movie
	 * including the movieId, toAdd, and the name.
	 * @param mId is the integer equivalent of the movie.
	 * @param toAdd is a boolean to determine if the user rated highly.
	 * @param name is the String title of this movie.
	 */
	public ListModifier(final int mId, 
			final Boolean toAdd, final String name) {
		movID = mId;
		add = toAdd;
		title = name;
		
	}
	
	
	
	
	
	
	/**
	 * getMovieID() returns this objects integer movieID.
	 * @return integer associated with this movieID.
	 */
	public int getMovieID() {
		return movID;
	}
	
	/**
	 * isAdd() returns a boolean true if this movie should be
	 * added to a list. 
	 * @return boolean true movie is something the user rated
	 * highly false otherwise.
	 */
	public boolean isAdd() {
		return add;
	}
	
	/**
	 * getName() returns the string title of this objects name.
	 * @return String is this objects movie title.
	 */
	public String getName() {
		return title;
	}
}
