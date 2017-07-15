package term.project.cis350;

import java.util.List;

/**
 * This class is used to locally keep track of movies.
 * 
 * @author Dan Schindler
 *
 */
public class LocMov {
	
	/**
	 * Tracks the movie's ID for the Movie DB.
	 */
	private int id;
	
	/**
	 * Tracks the genre ids that apply to this movie.
	 */
	private List<Integer> genreList;
	
	/**
	 * Tracks the year the movie was released.
	 */
	private int releaseYear;
	
	/**
	 * Tracks the movie's online rating.
	 */
	private float rating;
	
	/**
	 * Default constructor, saves the movie's information.
	 * @param id 			The movie's Movie DB ID.
	 * @param genres		The genres that apply to this movie.
	 * @param year			The year the movie was released.
	 * @param rating		The average rating the movie has online.
	 */
	public LocMov(final int id, final List<Integer> genres, 
			final int year, final int rating) {
		this.id = id;
		genreList = genres;
		releaseYear = year;
		this.rating = rating;
	}
	
	/**
	 * Gets the movie's ID.
	 * @return id	The movie's online ID.
	 */
	public int getid() {
		return id;
	}
	
	/**
	 * Returns the list of genres that apply to this movie as integers.
	 * @return genreList	The list of genre IDs that apply to this movie.
	 */
	public List<Integer> getGenres() {
		return genreList;
	}
	
	
	/**
	 * Returns the year the movie was released.
	 * @return	releaseYear		The year the movie was released.
	 */
	public int getYear() {
		return releaseYear;
	}
	
	
	/**
	 * Returns the movie's rating.
	 * @return	rating 		The movie's online rating.
	 */
	public float getRating() {
		return rating;
	}
}
