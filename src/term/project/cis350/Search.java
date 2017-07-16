package term.project.cis350;

import java.util.ArrayList;

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
import info.movito.themoviedbapi.model.Genre;
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
	
	/** used to adjust remote Recommendation list on movieDb via threading. */
	private ModRecListThread modRecRemote;
	
	/** used to adjust remote Watch list on movieDb via threading. */
	private ModWatchListThread modWatchRemote;
	
	/** used to locally hold the recommended List. */
	private List<LocMov> recList;

	/** used to locally hold the recommended refined List by search. */
	private List<LocMov> recSearched;

	/** used to locally hold the watch List. */
	private List<LocMov> watchList;
	
	/** used to locally hold the watch refined List by search. */
	private List<LocMov> watchSearched;	
	
	/** holds the list of all offered genres. */
	private ArrayList<String> genreList;
	
	/** holds a list of all available genres. */
	private ArrayList<String> genreListAvail = new ArrayList<String>();
	
	/**Used to keep track of search results. */
	private MovieResultsPage searchResults;
	
	/**used to keep track of the output. */
	private List<MovieDb> output;
	
	/**Used to keep track of the recommended movies list ID. */
	private String recId;
	
	private String watchLater;
	
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
	
	/** sets the minimum movie date year. */
	private static int minDate = 0;

	/** sets the maximum movie date year. */
	private static int maxDate = 5000;
	
	/** sets default and application minimum rating of interest. */
	private static int minRating = 1;
	
	
	private static final int MIN_STARS = 1;
	
	private static final int MAX_STARS = 10;
	
	private static final String ALL_GENRE 	= "Pick By Genre";	
	private static final String ALL_ERA 	= "Pick By Era"; 	// Defines the default message for the era combo box
	private static final String ALL_RATING 	= "Pick By Rating";	// Defines the default message for the Rating combo box
	
	private static String curEra	= ALL_ERA;				// holds the current era being searched for
	private static String curGenre	= ALL_GENRE;			// holds the current genre being searched for
	private static String curRating = ALL_RATING;			// holds the current rating minimum being searched for
	
	
	/** used as an increment for a date input parameter. */
	private static final int SHORTDECADE = 9;
	
	/**Default constructor connects to a user's account.
	 * Then it checks if this program has been run on their account before.
	 * If they have not used this program before, it creates a 
	 * list to track recommended movies.
	 * If they have used this program before, 
	 * it finds the ID of the recommended movie list and saves it. */
	public Search() {
					
		recList = new ArrayList<LocMov>();
		recSearched = new ArrayList<LocMov>();
		watchList = new ArrayList<LocMov>();
		watchSearched = new ArrayList<LocMov>();
		
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
		if (!watchLaterCheck()) {
			watchLater = listTool.createList(sessionToken, 
					"CIS 350 Movies that interested you", 
					"Movies that you rated highly");
		}
		
		modRecRemote = new ModRecListThread(listTool, sessionToken, recId);
		modWatchRemote = new ModWatchListThread(listTool, sessionToken, watchLater);
		
		System.out.println("current movie ID: " + currentMovie.getId());
	}
	
	
	
	public int getMinStars() {
		return MIN_STARS;
	}

	public int getMaxStars() {
		return MAX_STARS;
	}
	
	public String getDefGenre() {
		return ALL_GENRE;
	}
	
	public String getCurGenre() {
		return curGenre;
	}

	public String getDefRating() {
		return ALL_RATING;
	}

	public String getDefERA() {
		return ALL_ERA;
	}
	
	public Boolean setGenre(String genre) {
		curGenre = genre;
		
		updateSearch();
		return true; // because successful
	}

	public ArrayList<String> getGenreList() {
		genreList = new ArrayList<String>(); 
		genreList.add("action");
		genreList.add("adventure");
		genreList.add("animation");
		genreList.add("comedy");
		genreList.add("crime");
		genreList.add("documentary");
		genreList.add("drama");
		genreList.add("family");
		genreList.add("fantasy");
		genreList.add("history");
		genreList.add("horror");
		genreList.add("music");
		genreList.add("mystery");
		genreList.add("romance");
		genreList.add("science fiction");
		genreList.add("tv movie");
		genreList.add("thriller");
		genreList.add("war");
		genreList.add("western");
		
		return genreList;
	}
	
	public String[] getGenreAvail() {
		genreListAvail.add(0, ALL_GENRE);
		int tempSize = genreListAvail.size();
		return genreListAvail.toArray(new String[tempSize]);
	}
	
	public Boolean setEra(String era) {
		curEra = era;
		try {
			minDate = Integer.parseInt(era.substring(0, 4));
			maxDate = minDate + SHORTDECADE;
		} catch (Exception e) {
			minDate = 0;
			maxDate = 5000;
		}
		
		updateSearch();
		return true; // because successful
	}
	public String getCurEra() {
		return curEra;
	}

	public Boolean setRating(String rating) {
		curRating = rating;
		try {
			minRating = Integer.parseInt(rating);
			if (minRating < MIN_STARS) {
				minRating = MIN_STARS;
			} else if (minRating > MAX_STARS) {
				minRating = MAX_STARS;
			}
		} catch (Exception e) {
			minRating = 0;
		}
		
		updateSearch();
		return true; // because successful
	}
	
	public String getCurRating() {
		return curRating;
	}
	
	/**Checks if there is already a recommended movie list
	 * created from this program. If there is no such list, this method
	 * creates a list.
	 * 
	 *  @return boolean Whether or not the list exists already.
	 *  
	 *  */
	private boolean recListCheck() {
		
		for (MovieList lists: tmdbAccount
				.getLists(sessionToken, actId, "en-US", 0)) {
			
			String nameCheck = "CIS 350 Recommended Movie List";
			if (nameCheck.equalsIgnoreCase(lists.getName())) {
				recId = lists.getId();
				if (lists.getItemCount() == 0) {
					currentMovie = moviesTool.getPopularMovies("en-US", 0)
							.getResults().get(1);
					output = moviesTool.getPopularMovies("en-US", 0)
							.getResults();
					
					
				} else {
					for (MovieDb thisMovie : listTool.getList(recId).getItems()) {
						MovieDb tMov = moviesTool.getMovie(thisMovie.getId(), "en-US", null);
						recList.add(new LocMov(tMov));
					}
					updateSearch();
				}
				return true;
			}
			
		}
		return false;
	}
	 
	private boolean watchLaterCheck() {
		String nameCheck;
		for (MovieList lists: tmdbAccount.getLists(sessionToken, actId, "en-US", 0)) {
			nameCheck = "CIS 350 Movies that interested you";
			if (nameCheck.equalsIgnoreCase(lists.getName())) {
				watchLater = lists.getId();
				for (MovieDb thisMovie : listTool.getList(watchLater).getItems()) {
					MovieDb tMov = moviesTool.getMovie(thisMovie.getId(), "en-US", null);
					watchList.add(new LocMov(tMov));
				}
				return true;
			}
		}
		return false;
	}
	
	public void updateRecFromRating(final int rating) {
		if (rating >= MAX_STARS / 2) {
			updateRecPositive(rating);
		} else {
			updateRecNegative(rating);
		}
		genreListAvail.clear();
		for (LocMov locMov : recList) {
			updateGenreList(locMov);
		}
		int here = genreListAvail.size();
		here = 54;
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
		manageList(rating, true);
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
		manageList(rating, false);
		/*
		List<MovieDb> negRecs = moviesTool.getSimilarMovies(currentMovie.getId(), "en-US", 0).getResults();
		for (MovieDb movie: negRecs) {
			if (listTool.isMovieOnList(recId, movie.getId())) {
				listTool.removeMovieFromList(sessionToken, 
						recId, movie.getId());
			}
			
		}
		if(listTool.isMovieOnList(watchLater, currentMovie.getId())){
			listTool.removeMovieFromList(sessionToken, watchLater, currentMovie.getId());
		}
		rateMovie(rating);
		*/
		updateOutput();
	}
	

	private void manageList(final int rating, boolean isPositive) {

		List<MovieDb> newRecs = moviesTool.getSimilarMovies(currentMovie.getId(), "en-US", 0).getResults();
		
		/** stores an list of ListModifier objects to be used in remote thread*/
		List<ListModifier> listMod = new ArrayList<ListModifier>();
		List<MovieDb> listMovieDb = new ArrayList<MovieDb>();
		String curTitle = currentMovie.getTitle();
		boolean isOnWatchList = listTool.isMovieOnList(watchLater,  currentMovie.getId());
		
		if (!listTool.isMovieOnList(watchLater, currentMovie.getId()) || !isPositive) {
			if (listMod != null && listMod.size() > 0) {
				listMod.clear();
			}
			
			MovieDb tMov = moviesTool.getMovie(currentMovie.getId(), "en-US", null);
			listMod.add(new ListModifier(tMov.getId(), isPositive, tMov.getTitle()));
			modWatchRemote.addToList(listMod);
			watchList.add(new LocMov(tMov));
			//listTool.addMovieToList(sessionToken, watchLater, currentMovie.getId());
		}		
		
		for (MovieDb movie: newRecs) {
			MovieDb tMov = moviesTool.getMovie(movie.getId(), "en-US", null);			
			listMod.add(new ListModifier(tMov.getId(), isPositive, tMov.getTitle()));
			listMovieDb.add(tMov);
		}
		
		int counter = 0;
		for (MovieDb movie: newRecs) {
			float avgRate = movie.getVoteAverage();
			String year = movie.getReleaseDate();
			List<Genre> genres = movie.getGenres();
			
			
			if (!recList.contains(listMod.get(counter)) && isPositive) {
				//MovieDb movDb = 
				recList.add(new LocMov(listMovieDb.get(counter)));
			}
			counter++;
			/*
			if (!listTool.isMovieOnList(recId, movie.getId())) {
				listTool.addMovieToList(sessionToken, 
						recId, movie.getId());
			}*/			
		}

		if (listMod.size() > 0) {
			modRecRemote.addToList(listMod);
		}
		rateMovie(rating);
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
		
		
	}
	
	/**
	 * Updates the current movie by comparing the search 
	 * results to the recommended list.
	 * 
	 * 
	 * */
	private void updateOutput() {
		//FIXME
		//output.clear();
		/*
		for (MovieDb searchResultMovie: searchResults) {
			if (listTool.isMovieOnList(recId, 
					searchResultMovie.getId())) {
				output.add(searchResultMovie);
			}
		}*/
		Random rando = new Random();
		/*
		int range = output.size();
		System.out.println("The range if: " + range);
		*/
		if (recSearched.size() > 0) {
			/** random recommendation output index*/
			int randRecSearch = recSearched.get(rando.nextInt(recSearched.size())).getid();
			currentMovie = moviesTool.getMovie(randRecSearch, recId, null);
					
		} else {
			/** if recSearched has size 0 this populates it with the a random popular movie */
			currentMovie = moviesTool.getPopularMovies("en-US", 0)
					.getResults().get(rando.nextInt(moviesTool.getPopularMovies("en-US", 0).getResults().size()));
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
		
		String filePath = "https://image.tmdb.org/t/p/w300/" + currentMovie.getPosterPath();
		
		try {
			URL url = new URL(filePath);
			picture = ImageIO.read(url);
			
		
		} catch (IOException e) {
			System.err.println("IOException: " 
					+ e.getMessage());
					e.printStackTrace();    
		
		}     
		
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
		//System.out.println("Here");
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
		float rating;
		output.clear();
		boolean hasGenre = false;
		
		if (recSearched.size() > 0) {
			recSearched.clear();
		}
		if (watchSearched.size() > 0) {
			watchSearched.clear();
		}
		
		if (genreId != 0) {
			//for(int i = 0; i < recList.size() /*listTool.getList(recId).getItemCount()*/; i++){
			for (LocMov movie: recList) {
				if (movie.containsGenre(genreId) 
				&& movie.getRating() 	>= minRating 
				&& movie.getYear() 		>= minDate 
				&& movie.getYear() 		<= maxDate) {
					/** assuming there are no duplicated in remote & local recList */
					recSearched.add(movie);
					System.out.println("Here's the current movies: " + movie.getTitle());
				} 

			}
			System.out.println("Number of recSearched = " + recSearched.size());
			for (LocMov movie: watchList) {
				if (movie.containsGenre(genreId) 
				   && movie.getRating() 	>= minRating
				   && movie.getYear() 		>= minDate
				   && movie.getYear() 		<= maxDate) {
					/** assuming there are no duplicated in remote & local recList */
					watchSearched.add(movie);	
				}
			}
			System.out.println("Number of watchSearched = " + watchSearched.size());
		} else {
			for (LocMov movie: recList) {
				if (movie.getRating() 	>= minRating
				   && movie.getYear() 		>= minDate 
				   && movie.getYear() 		<= maxDate) {
					/** assuming there are no duplicated in remote & local recList */
					recSearched.add(movie);					
				}
		
			}
			System.out.println("Number of recSearched = " + recSearched.size());
			for (LocMov movie: watchList) {
				if (movie.getRating() 	>= minRating
				   && movie.getYear() 		>= minDate
				   && movie.getYear() 		<= maxDate) {
					/** assuming there are no duplicated in remote & local recList */
					watchSearched.add(movie);					
				}
			}
			System.out.println("Number of watchSearched = " + watchSearched.size());
		}
		updateOutput();
		int here = 34;
		here = 44;
		
		
	}
	
	private void updateGenreList(LocMov movie) {
		for (Genre genre : movie.getGenres()) {
			if (!genreListAvail.contains(genre.getName())) {
				genreListAvail.add(genre.getName());
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
		if (watchSearched.size() > 0) {
			int randId =  watchSearched.get(rando.nextInt(watchSearched.size())).getid();
			currentMovie = moviesTool.getMovie(randId, watchLater, null);
		} else {
				System.out.println("** WatchSearched Nothing to pick From **");
		}
		
		BufferedImage suggestedPoster = null;
		String filePath = "https://image.tmdb.org/t/p/w300/" + currentMovie.getPosterPath();
		
		System.out.println(filePath);
		try {
			URL url = new URL(filePath);
			suggestedPoster = ImageIO.read(url);
			
		} catch (IOException e) {
			System.err.println("IOException: " 
					+ e.getMessage()); 
					e.printStackTrace();
		
		}     
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
