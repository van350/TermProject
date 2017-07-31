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
	 * tracks the URL path where the movie's poster can be found.
	 */
	private String posterPath;
	
	/**
	 * Tracks the movie's ID for the Movie DB.
	 */
	private int id;
	
	/**
	 * Tracks the genre ids that apply to this movie.
	 */
	private List<Genre> genreList;
	
	/**
	 * List of genres as Integer IDs.
	 */
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
	 * Tracks the movie title.
	 */
	private String title;
	
	/**
	 * Default constructor, saves the movie's information.
	 * @param movie		The movie that we are interested in.
	 */
	public LocMov(final MovieDb movie) {
		title = movie.getTitle();
		this.id = movie.getId();
		genreList = movie.getGenres();
        String releaseDate = movie
        		.getReleaseDate().substring(0, 4);
		releaseYear = Integer.parseInt(releaseDate);
		this.rating = movie.getVoteAverage();
		genreIDList = setGenreIds(movie.getGenres());
		posterPath = "https://image.tmdb.org/t/p/w300/"
				+ movie.getPosterPath();
	}
	
	/**
	 * Gets the movie's ID.
	 * @return id	The movie's online ID.
	 */
	public int getid() {
		return id;
	}
	
	/**
	 * gets the URL where the poster is saved.
	 * 
	 * @return	posterPath the URL to the poster.
	 */
	public String getPoster() {
		return posterPath;
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
	
	/**
	 * Takes the list of Genres and parses the information 
	 * into usable Integers.
	 * 
	 * @param genres	The list of genres that a movie has.
	 * @return intList	The list of genres as integers that a movie has.
	 */
	private List<Integer> setGenreIds(final List<Genre> genres) {
		
		List<Integer> intList = new ArrayList<Integer>();
		for (Genre curGenre : genres) {
			intList.add(curGenre.getId());
		}
		return intList;
	}
	
	/**
	 * checks if the genre ID passed as a parameter 
	 * matches any of the genre IDs that the movie has.
	 * @param tGenre	The genre ID that we're looking for.
	 * @return boolean	True if the movie has the 
	 * genre that is being searched for.
	 */
	public boolean containsGenre(final int tGenre) {
		for (int genreId: genreIDList) {
			if (genreId == tGenre) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Gets the title of the movie.
	 * 
	 * @return title	The title of the movie.
	 */
	public String getTitle() {
		return title;

	

	}
}
