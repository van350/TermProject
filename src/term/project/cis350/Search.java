package term.project.cis350;

/**
import info.movito.themoviedbapi.model.changes.ChangesItems;


import info.movito.themoviedbapi.model.Artwork;
import info.movito.themoviedbapi.AbstractTmdbApi;

import info.movito.themoviedbapi.model.core.*;
import info.movito.themoviedbapi.tools.ApiUrl;
import info.movito.themoviedbapi.tools.MovieDbException;
import info.movito.themoviedbapi.model.Language;
import org.json.CDL;
import org.json.CDL;
import org.json.Cookie;
import org.json.Cookie;
import org.json.CookieList;
import org.json.CookieList;
import org.json.HTTP;
import org.json.HTTP;
import org.json.HTTPTokener;
import org.json.HTTPTokener;
import org.json.JSONArray;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONException;
import org.json.JSONML;
import org.json.JSONML;
import org.json.JSONObject;
import org.json.JSONObject;
import org.json.JSONString;
import org.json.JSONString;
import org.json.JSONStringer;
import org.json.JSONStringer;
import org.json.JSONTokener;
import org.json.JSONTokener;
import org.json.JSONWriter; 
import org.json.JSONWriter;
import org.json.XML;
import org.json.XML;
import org.json.XMLTokener;
import org.json.XMLTokener;
import info.movito.themoviedbapi.model.core.IdElement;
import info.movito.themoviedbapi.model.people.PersonCast;
import info.movito.themoviedbapi.model.MovieImages;
import info.movito.themoviedbapi.model.people.PersonCrew;
import info.movito.themoviedbapi.model.Reviews;
import info.movito.themoviedbapi.model.Video;
import info.movito.themoviedbapi.TmdbSearch;
import info.movito.themoviedbapi.TmdbMovies.MovieMethod;
import info.movito.themoviedbapi.TmdbAccount.MovieListResultsPage;
*/


//Let's make a change!     
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import info.movito.themoviedbapi.TmdbAccount.MediaType;
import info.movito.themoviedbapi.model.config.Account;
import info.movito.themoviedbapi.TmdbDiscover;
import info.movito.themoviedbapi.TmdbLists;
import info.movito.themoviedbapi.TmdbAccount;


import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbAuthentication;
import info.movito.themoviedbapi.TmdbMovies;

import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.config.TokenSession;
import info.movito.themoviedbapi.model.core.AccountID;
import info.movito.themoviedbapi.model.core.MovieResultsPage;
import info.movito.themoviedbapi.model.core.SessionToken;

import info.movito.themoviedbapi.model.MovieList;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Class that contains methods for searching, 
 * rating, and saving movies to watch later.
 * 
 * @author Dan Schindler
 *
 */
public class Search {
	
	
	/**Used to keep track of search results. */
	private MovieResultsPage searchResults;
	
	/**used to keep track of the output. */
	private List<MovieDb> output;
	
	/**Used to keep track of the recommended movies list ID. */
	private String recId;
	
	/**Used to keep track of the TmdbAccount being used. */
	private TmdbAccount tmdbAccount;
	
	/**Used to track the suggested movie to watch. */
	private MovieDb suggestedMovie;
	
	/**Used to keep track of the movie database account being used. */
	private Account act;
	
	/**Tracks the account ID for the account being used. */
	private AccountID actId;
	
	/**Tracks the TmdbApi used in the wrapper. */
	private   TmdbApi tmdbApi;
	
	/**Tracks the session token used in the wrapper. */
	private   SessionToken sessionToken;
	
	/**Allows use of TmdbMovies class methods. */
	private TmdbMovies moviesTool;
	
	/**Tracks the movie currently being displayed. */
	private MovieDb currentMovie;

	
	/**Allows the use of TmdbLists class methods. */
	private TmdbLists listTool;
	
	/** sets the minimum movie date year */
	private static int minDate = 0;

	/** sets the maximum movie date year */
	private static int maxDate = 5000;
	
	/** sets default and application min rating of interest*/
	private static int minRating = 0;
	
	
	private static final int MIN_STARS = 0;
	
	private static final int MAX_STARS = 10;
	
	private static final String ALL_GENRE 	= "Pick By Genre";	
	private static final String ALL_ERA 	= "Pick By Era"; 	// Defines the default message for the era combo box
	private static final String ALL_RATING 	= "Pick By Rating";	// Defines the default message for the Rating combo box
	
	private static String curEra	= ALL_GENRE;				// holds the current era being searched for
	private static String curGenre	= ALL_ERA;			// holds the current genre being searched for
	private static String curRating = ALL_RATING;			// holds the current rating minimum being searched for
	
	
	/** used as an increment for a date input parameter */
	private static final int SHORTDECADE = 9;
	
	/**Default constructor connects to a user's account.
	 * Then it checks if this program has been run on their account before.
	 * If they have not used this program before, it creates a 
	 * list to track recommended movies.
	 * If they have used this program before, 
	 * it finds the ID of the recommended movie list and saves it. */
	public Search() {
		System.out.println("IN connect");
		tmdbApi = new TmdbApi("978613ab37cca0d42531d612540d5fac");
		moviesTool = new TmdbMovies(tmdbApi);
		

		sessionToken = getSessionToken();
		tmdbAccount = tmdbApi.getAccount();
		act = tmdbAccount.getAccount(sessionToken);
		actId = new AccountID(act.getId());
		listTool = new TmdbLists(tmdbApi);
		output = moviesTool.getPopularMovies("en-US", 0)
				.getResults();
		
		if (!recListCheck()) {
			recId = listTool.createList(sessionToken, 
					"CIS 350 Recommended Movie List", 
					"Movies that we recommend");
			output = moviesTool.getPopularMovies("en-US", 0)
					.getResults();
			currentMovie = moviesTool.getPopularMovies("en-US", 0)
					.getResults().get(1);
		}
		
		System.out.println("current movie ID: " + currentMovie.getId());
	}
	
	
	
	public int getMinStars(){
		return MIN_STARS;
	}

	public int getMaxStars(){
		return MAX_STARS;
	}
	
	public String getDefGenre(){
		return ALL_GENRE;
	}

	public String getDefRating(){
		return ALL_RATING;
	}

	public String getDefERA(){
		return ALL_ERA;
	}
	
	public Boolean setGenre(String genre){
		curGenre = genre;
		
		updateSearch();
		return true; // because successful
	}

	public Boolean setEra(String era){
		curEra = era;
		try{
			minDate = Integer.parseInt( era.substring(0,4) );
			maxDate = minDate + SHORTDECADE;
		}catch(Exception e){
			minDate = 0;
			maxDate = 5000;
		}
		
		updateSearch();
		return true; // because successful
	}

	public Boolean setRating(String rating){
		curRating =rating;
		try{
			minRating = Integer.parseInt(rating);
			if(minRating < MIN_STARS){
				minRating = MIN_STARS;
			}else if(minRating > MAX_STARS){
				minRating = MAX_STARS;
			}
		}catch(Exception e){
			minRating = 0;
		}
		
		updateSearch();
		return true; // because successful
	}
	/**Checks if there is already a recommended movie list
	 * created from this program. If there is no such list, this method
	 * creates a list.
	 * 
	 *  @return boolean Whether or not the list exists already.
	 *  
	 *  */
	private boolean recListCheck() {
		String nameCheck = "CIS 350 Recommended Movie List";
		for (MovieList lists: tmdbAccount
				.getLists(sessionToken, actId, "en-US", 0)) {
			
			
			if (nameCheck.equalsIgnoreCase(lists.getName())) {
				recId = lists.getId();
				if(!(lists.getItemCount() > 0)){
					currentMovie = moviesTool.getPopularMovies("en-US", 0)
							.getResults().get(1);
					output = moviesTool.getPopularMovies("en-US", 0)
							.getResults();
					
				} else {
					updateSearch();
				}
				return true;
			}
			
		}
		return false;
	}
	
	public void updateRecFromRating(final int rating) {
		if(rating >= MAX_STARS/2){
			updateRecPositive(rating);
		} else {
			updateRecNegative(rating);
		}
	}
	
	/**
	 * If the user 'swiped' right and wants to add movie to watch later list
	 *  and they want more recommendations like this one, 
	 *  this method updates
	 *  the recommended list as well as the current movie.
	 *  
	 *  @param rating The rating that the user gave the movie 
	 *  (v1.0 is automatically full score)
	 *  
	 *  
	 */
	private void updateRecPositive(final int rating) {
		//FIXME
		
		List<MovieDb> newRecs = moviesTool.getSimilarMovies(currentMovie.getId(), "en-US", 0).getResults();
		for (MovieDb movie: newRecs) {
			if (!listTool.isMovieOnList(recId, movie.getId())) {
				listTool.addMovieToList(sessionToken, 
						recId, movie.getId());
			}
			
		}
		rateMovie(rating);
		tmdbAccount.addToWatchList(sessionToken, actId, 
				currentMovie.getId(), MediaType.MOVIE);
		updateOutput();
	}
	
	
	/**
	 * If the user 'swiped' left and doesn't 
	 * 	want to add movie to watch later list
	 *  and they don't want more recommendations like this one, 
	 *  this method updates
	 *  the recommended list as well as the current movie.
	 *  
	 *  @param rating The rating that the user gave the movie 
	 *  (v1.0 is automatically zero score)
	 */
	private void updateRecNegative(final int rating) {
		List<MovieDb> negRecs = currentMovie.getSimilarMovies();
		for (MovieDb movie: negRecs) {
			if (listTool.isMovieOnList(recId, movie.getId())) {
				listTool.removeMovieFromList(sessionToken, 
						recId, movie.getId());
			}
			
		}
		rateMovie(rating);
		updateOutput();
	}
	
	/**
	 * This method updates the search list and updates the output by 
	 * calling private methods.
	 * 
	 * @param genre The genre of the movie in the form of a string. 
	 * @param date the year that the movie was released.
	 * @param avgRating the average rating score that the user wants.
	 * 
	 * 
	 */
	private void updateSearch() {
		//FIXME
		basicSearch();
		updateOutput();
		
	}
	
	/**
	 * Updates the current movie by comparing the search 
	 * results to the recommended list.
	 * 
	 * 
	 * */
	private void updateOutput() {
		//FIXME
		output.clear();
		/*
		for (MovieDb searchResultMovie: searchResults) {
			if (listTool.isMovieOnList(recId, 
					searchResultMovie.getId())) {
				output.add(searchResultMovie);
			}
		}*/
		
		if (output.size() > 0) {
			currentMovie = output.get(0);
		} else {
			currentMovie = moviesTool.getPopularMovies("en-US", 0)
					.getResults().get(1);
		}
		
		
	}
	
	
	/**
	 * Returns the current movie's poster. 
	 *  
	 * @return picture	The poster's picture.
	 * 
	 * 
	 * 
	 * */  
	public BufferedImage getPoster() {
		//thank you stackoverflow for the help
		//https://stackoverflow.com/questions/19447104/
		//		load-image-from-a-filepath-via-bufferedimage
		BufferedImage picture = null;
		System.out.println("You are here");
		System.out.println();
		if(currentMovie != null){
			System.out.println("Movie Not Null");
		}else{
			System.out.println("Movie NULL");
		}
		System.out.print(currentMovie.getPosterPath());
		String filePath = "https://image.tmdb.org/t/p/w300/" + currentMovie.getPosterPath();
		
		System.out.println(filePath);
		try {
			URL url = new URL(filePath);
			BufferedImage bi = ImageIO.read(url);
			
		picture = bi;//ImageIO.read((new File(filePath)));
		
		} catch (IOException e) {
			System.err.println("IOException: " 
					+ e.getMessage());
					e.printStackTrace();    
		
		}     
		//return currentMovie.getImages().get(0).getFilePath();
		return picture;
	}
	
	
	/**
	 * Assigns the user's rating to the current movie.  
	 * 
	 * @param rating	the rating that the user wants to assign to 
	 * 	the movie.
	 * 
	 * 
	 * */
	public void rateMovie(final int rating) {
		tmdbAccount.postMovieRating(sessionToken,
				currentMovie.getId(), rating);
	}
	
	 /**
	  * Uses the Discover feature of the API to generate the search list.
	  * 
	  * @param genre	the genre that is being searched for.
	  * @param date		the year that is being searched for.
	  * @param avgRating	the average rating that is being searched for.
	  */
	private void basicSearch() {
		
		int genreId;
		System.out.println("Here");
		if (curGenre.equalsIgnoreCase("action")) {
			genreId = 28;
		} else if (curGenre.equalsIgnoreCase("adventure")) {
			genreId = 12;
		} else if (curGenre.equalsIgnoreCase("animation")) {
			genreId = 16;
		} else if (curGenre.equalsIgnoreCase("comedy")) {
			genreId = 35;
		} else if (curGenre.equalsIgnoreCase("crime")) {
			genreId = 80;
		} else if (curGenre.equalsIgnoreCase("documentary")) {
			genreId = 99;
		} else if (curGenre.equalsIgnoreCase("drama")) {
			genreId = 18;
		} else if (curGenre.equalsIgnoreCase("family")) {
			genreId = 10751;
		} else if (curGenre.equalsIgnoreCase("fantasy")) {
			genreId = 14;
		} else if (curGenre.equalsIgnoreCase("history")) {
			genreId = 36;
		} else if (curGenre.equalsIgnoreCase("horror")) {
			genreId = 27;
		} else if (curGenre.equalsIgnoreCase("music")) {
			genreId = 10402;
		} else if (curGenre.equalsIgnoreCase("mystery")) {
			genreId = 9648;
		} else if (curGenre.equalsIgnoreCase("romance")) {
			genreId = 10749;
		} else if (curGenre.equalsIgnoreCase("science fiction")) {
			genreId = 878;
		} else if (curGenre.equalsIgnoreCase("tv movie")) {
			genreId = 10770;
		} else if (curGenre.equalsIgnoreCase("thriller")) {
			genreId = 53;
		} else if (curGenre.equalsIgnoreCase("war")) {
			genreId = 10752;
		} else if (curGenre.equalsIgnoreCase("western")) {
			genreId = 37;
		} else {
			genreId = 0;
		}
		int releaseDate;
		output.clear();
		if(genreId != 0){
			for(int i = 0; i < listTool.getList(recId).getItemCount(); i++){
				MovieDb thisMovie = listTool.getList(recId).getItems().get(i);
				releaseDate = Integer.parseInt((thisMovie.getReleaseDate().substring(0, 4)));
				if(thisMovie.getGenres().contains(genreId) && thisMovie.getUserRating() >= minRating && releaseDate >= minDate && releaseDate <= maxDate){
					output.add(thisMovie);
				}
			}
		} else {
			for(int i = 0; i < listTool.getList(recId).getItemCount(); i++){
				MovieDb thisMovie = listTool.getList(recId).getItems().get(i);
				releaseDate = Integer.parseInt((thisMovie.getReleaseDate().substring(0, 4)));
				if(thisMovie.getUserRating() >= minRating && releaseDate >= minDate && releaseDate <= maxDate){
					output.add(thisMovie);
				}
			}
		}
		
		
		
	}
	
	
	 /**
	  * Retrieves a movie from the watch later list,
	  *  notes which movie it is,
	  * 	and then returns the poster for the movie.
	  * 
	  * @return suggestedPoster	The poster of the suggested movie.
	  * */
	public BufferedImage getMovieToWatch() {
		Random rando = new Random();
		MovieResultsPage watchList = 
				tmdbAccount
				.getWatchListMovies(sessionToken, actId, 0);
		int upperLimit = watchList.getTotalResults();
		int moviePick = rando.nextInt(upperLimit);
		suggestedMovie = watchList.getResults().get(moviePick);
		
		BufferedImage suggestedPoster = null;
String filePath = "https://image.tmdb.org/t/p/w300/" + suggestedMovie.getPosterPath();
		
		System.out.println(filePath);
		try {
			URL url = new URL(filePath);
			BufferedImage bi = ImageIO.read(url);
			
			suggestedPoster = bi;//ImageIO.read((new File(filePath)));
		
		} catch (IOException e) {
			System.err.println("IOException: " 
					+ e.getMessage());
					e.printStackTrace();    
		
		}     
		//return currentMovie.getImages().get(0).getFilePath();
		return suggestedPoster;
	}
	
	
	/**
	 * used for logging in to the movie database.
	 * @return sessionToken	the session login information.
	 */
	private SessionToken getSessionToken() {
		TmdbAuthentication tmdbAuth = tmdbApi.getAuthentication();
		TokenSession tokenSession = 
				tmdbAuth.getSessionLogin("Schindld",
						"CIS350supergroup");
		
		System.out.println("Session ID: " 
		+ tokenSession.getSessionId());
		
		SessionToken sessionToken = 
				new SessionToken(tokenSession.getSessionId());
		return sessionToken;
	}
}
