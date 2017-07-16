package term.project.cis350;

import java.util.ArrayList;
import java.util.List;

import info.movito.themoviedbapi.model.Genre;
import info.movito.themoviedbapi.model.MovieDb;

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
	private List<Genre> genreList;
	
	private List<Integer> genreIDList;
	
	/**
	 * Tracks the year the movie was released.
	 */
	private int releaseYear;
	
	/**
	 * Tracks the movie's online rating.
	 */
	private float rating;
	
	/**
	 * Tracks the movie title
	 */
	private String title;
	
	/**
	 * Default constructor, saves the movie's information.
	 * @param id 			The movie's Movie DB ID.
	 * @param genres		The genres that apply to this movie.
	 * @param year			The year the movie was released.
	 * @param rating		The average rating the movie has online.
	 */
	public LocMov(MovieDb movie) {
		title = movie.getTitle();
		this.id = movie.getId();
		genreList = movie.getGenres();
		//System.out.println(movie.getReleaseDate() + " Here's the release date");
		releaseYear = Integer.parseInt((movie.getReleaseDate()).substring(0, 4));
		this.rating = movie.getVoteAverage();
		genreIDList = setGenreIds(movie.getGenres());
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
	public List<Genre> getGenres() {
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
	
	private List<Integer> setGenreIds(final List<Genre> genres){
		
		List<Integer> intList = new ArrayList<Integer>();
		for (Genre curGenre : genres) {
			intList.add(curGenre.getId());
		}
		return intList;
	}
	
	public boolean containsGenre(int tGenre){
		for(int genreId: genreIDList){
			if(genreId == tGenre){
				return true;
			}
		}
		return false;
	}
	
	public String getTitle(){
		return title;
	}
	
}
