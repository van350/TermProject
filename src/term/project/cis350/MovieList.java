package term.project.cis350;
import java.awt.image.BufferedImage;

import java.util.ArrayList;


import javax.swing.ImageIcon;

/**
 * Aids in communication between controller and GUI.
 * 
 * @author Chris 
 *
 */
public class MovieList {

	/**
	 * holds the current era being searched for.
	 */
	private String curEra;				
	
	/**
	 * holds the current genre being searched for.
	 */
	private String curGenre;		
	
	/**
	 * holds the current rating minimum being searched for.
	 */
	private String curRating;		
	
	/**
	 * object used to manipulate an image.
	 */
	private ImgAdjust imgAdjust = new ImgAdjust();	
			
	
	/**
	 * object used to hold recommended movies.
	 */
	private Search search;
	
	/**
	 * BUFIMAGE used to hold the movie on FOCUS' poster.
	 */
	private BufferedImage curPoster;		
	
	/**
	 * defines the minimum star rating for a movie.
	 */
	private final int minStars;
	
	/**
	 * defines the maximum star rating for a movie.
	 */
	private final int maxStars;	
	
		/**
		 * holds a list of valid types.
		 */
	private ArrayList<String> types;
	
	/**
	 * holds a list of valid eras.
	 */
	private ArrayList<String> era;
	
	/**
	 * holds a list of valid rateList.
	 */
	private ArrayList<String> rateList;	
	
	/**
	 * Movie List constructor creates a new object of the MovieList class.
	 */
	public MovieList() {
		search = new Search();
		
		minStars = search.getMinStars();
		maxStars = search.getMaxStars();
		
		types = search.getGenreList();
		types.sort(String::compareToIgnoreCase);
		
		// this order makes sure DEF_GENRE is always the
		// first in the list. 
		curGenre = search.getDefGenre();
		types.add(0, curGenre);
		
		era = new ArrayList<String>();
		era.add("1950's");
		//....
		era.add("1960's");
		era.add("1970's");
		era.add("1980's");
		era.add("1990's");
		era.add("2000's");
		era.add("2010's");
		era.sort(String::compareToIgnoreCase);
		
		// this order makes sure DEF_ERA is always the
		// first in the list. 
		curEra = search.getDefERA();
		era.add(0, curEra);
		/*
		for(int i = 0; i < era.size() ; i++){
			System.out.println(era.get(i) + "\n");
		}
		*/
		rateList = new ArrayList<String>();
		
		for (int i = 0; i <= maxStars; i++) {
			rateList.add(Integer.toString(i));
		}
		
		// this order makes sure DEF_RATING is always the
		// first in the list. 
		curRating = search.getDefRating();
		rateList.add(0, curRating);

	}
	
	
	//NOTE I WILL CALL THIS FUNCTION EVERYTIME I NEED A NEW MOVIE POSTER. 
	/**
	 * Returns an ImageIcon that corresponds to the curMovie and scaled
	 * to the desired Height and width based on the input parameters 
	 * desHeight and desWidth respectively. 
	 * 
	 * @param desHeight defines the desired 
	 * height for the returned imageIcon
	 * @param desWidth defines the desired width for the returned imageIcon
	 * @return 	ImageIcon with the desired 
	 * width and height of the current Movie on FOCUS
	 * @see 	ImageIcon
	 */
	public ImageIcon getMoviePoster(final int desHeight, 
			final int desWidth) {
		//System.out.println("Before Connect");
		//conToDB = new connectToMovieDB();
		return getPosterAndSetCurMovie(desHeight, desWidth, false);
	}
	/**
	 * Returns an ImageIcon that corresponds to the current movie suggestion
	 * and scaled to the desired Height and 
	 * width based on the input parameters 
	 * desHeight and desWidth respectively. 
	 * 
	 * @param desHeight defines the desired 
	 * height for the returned imageIcon
	 * @param desWidth defines the desired width for the returned imageIcon
	 * @return 	ImageIcon with the desired 
	 * width and height of the current Movie suggested
	 * @see 	ImageIcon
	 */
	public ImageIcon getMovieToWatch(final int desHeight, 
			final int desWidth) {
		return getPosterAndSetCurMovie(desHeight, desWidth, true);
	}
	
	/**movie
	 * getPosterAndSetCurMovie returns a 
	 * ImageIcon that is scaled based to the desired
	 * input parameters. Additionally, it keeps 
	 * track if the call was based on a get
	 * movie suggestion or just another movie to be rated.  
	 * 
	 * @param desHeight is the desired height 
	 * in which the poster should be scaled to 
	 * @param desWidth is the desired width in 
	 * which the poster should be scaled to 
	 * @param isWatchSug is a bool that determines
	 *  if the poster selection was the result
	 * 			of a getMovie selections. 
	 * @return ImageIcon with desired width and height 
	 */
	private ImageIcon getPosterAndSetCurMovie(final int desHeight, 
			final int desWidth, final boolean isWatchSug) {
		//dWidth 	= ;
		//dHeight 	= ;
		try {
    	  
			// here a jpg and png work I am not 
			//sure what other image
			// formats work that is only the 
			//ones that I have tried.  
			/** gets the correct movie poster based 
			 * on if application is looking for a suggestion */
			
			
			if (isWatchSug) {
				curPoster = search.getMovieToWatch();
			} else {
				curPoster = search.getPoster();
			}
			
			
			//curPoster = search.getPoster();
					/*ImageIO.read(new File(
					 * System.getProperty("user.dir") +
					 *  "/someMovie.jpg"));*/

    	    	  
			return new ImageIcon(imgAdjust.scaleToSize(curPoster, 
					desWidth, desHeight));
		} catch (Exception e) {
		
			e.printStackTrace();
			System.out.println("BackGround Image File NOT FOUND\n");
			return null;
		}
	}
	
	
	/**
	 * setMovieScore is used to rate the curMovie object. For invalid inputs
	 * i.e. < minStars or > maxStars this function does nothing
	 * 
	 * @param numStars is used to set the curMovie rating for that movie. 
	 */
	public void setMovieScore(final int numStars) {
		if (maxStars >= numStars && minStars <= numStars) {
			search.updateRecFromRating(numStars);
		// here need to do something with the number of stars
		// I will be calling this every time someone swipes right
		// or left. NOTE CURRENTLY I AM PLANNING ON A SCALE OF 
		// 0-5 IF THAT NEEDS TO CHANGE PLEASE LET ME KNOW AND I 
		// CAN MAKE THE ADJUSTMENTS.
		
		// curMovie ... = numStars 
		} else {
			System.out.println("Invalid star entry");
		}
	}
	/**
	 * 
	 * This function is used to return a list of all the available
	 * movie genres. additionally it includes adding an all
	 * genres string that will always be at the top of the list. 
	 * 
	 * @return String[] returns a list of valid genres for this application
	 */
	public String[] genreList() {
		return types.toArray(new String[types.size()]);
	}

	/**
	 * setGenre is used to set the current Genre 
	 * to get results from. This function returns
	 * Boolean true if the genre was set or set
	 *  to a different genre than its previous state.
	 * if the genre remains the same the function returns false. 
	 * 
	 * @param sentGenre String that is used
	 *  to set the current genre of interest.
	 * @return Boolean returns true if genre was changed false otherwise. 
	 */
	public Boolean setGenre(final String sentGenre) {
		System.out.println(sentGenre);
		if (!curGenre.equalsIgnoreCase(sentGenre)) {
			// ALSO NEED TO DO A CHECK 
			//TO ENSURE THAT THIS IS A VALID STRING INPUT. 
			// I.E. is contained in the 
			curGenre = sentGenre;
			search.setGenre(curGenre);
			// here because the sent era was different 
			// we need to update the search results to reflect
			// this
			return true;
		}	// otherwise we do nothing. 
		return false;
	}
	
	/**
	 * returns the list of available genres made in the search class.
	 * @return	search.getGenreAvail	the list of available genres.
	 */
	public String[] genreListAvail() {
		
		return search.getGenreAvail();
	}


	/**
	 * gets the comboBox Selected String desired.
	 * @return returns selected String from the Available Genres
	 */
	public String getSelectGenre() {
		return search.getCurGenre();
	}
	
	/**
	 * eraList() returns a list of 
	 * valid era strings that can be used to select 
	 * an era to get movie suggestions or movies to rate. 
	 * 
	 * @return	String[] is a list of 
	 * valid era strings for the application. 
	 */
	public String[] eraList() {

		return era.toArray(new String[era.size()]);
	}
	/**
	 * gets the comboBox Selected Index desired.
	 * @return returns selected string 
	 */
	public String getSelectedEra() {
		return search.getCurEra();
	}
	
	
	/**
	 * setEra() is used to set the current 
	 * search mode by an era's criteria. the function
	 * returns true if the era was change 
	 * to a different era or false if nothing was changed  
	 * @param sentEra is a string that is used 
	 * to change the current era search criteria. 
	 * @return	returns true if the era was changed false otherwise. 
	 */
	public Boolean setEra(final String sentEra) {
		System.out.println(sentEra);
		if (!curEra.equalsIgnoreCase(sentEra)) {
			// ALSO NEED TO DO A CHECK TO 
			//ENSURE THAT THIS IS A VALID STRING INPUT. 
			// I.E. is contained in the 
			curEra = sentEra;
			search.setEra(curEra);
			// here because the sent era was different 
			// we need to update the search results to reflect
			// this
			return true;
		}	// otherwise we do nothing. 
		return false;
	}
	/**
	 * ratingList() returns a string[] 
	 * that contains valid rating options for this
	 * application search results. 
	 * @return String[] contains a list of 
	 * valid rating options for this applications search results. 
	 */
	public String[] ratingList() {
		// returning an array of strings 
		//that contain valid rating options. 
		return rateList.toArray(new String[rateList.size()]);
	}
	/**
	 * used to get the current rating selection.
	 * @return returns a string associated with the current 
	 * 			rating selected
	 */
	public String getSelectedRating() {
		return search.getCurRating();
	}

	/**
	 * setRating() returns true if the input string caused a change in the 
	 * current movie rating search 
	 * criteria. or false otherwise. This function
	 * is used to set the search criteria for getting a curMovie object. 
	 * @param sentRating is a string to set the search criteria 
	 * @return Boolean returns true 
	 * if the rating criteria was changed, false otherwise. 
	 */
	public Boolean setRating(final String sentRating) {
		System.out.println(sentRating);
		if (!curRating.equalsIgnoreCase(sentRating)) {
			// ALSO NEED TO DO A CHECK TO 
			//ENSURE THAT THIS IS A VALID STRING INPUT. 
			// I.E. is contained in the 
			curRating = sentRating;
			search.setRating(curRating);
			// here because the sent era was different 
			// we need to update the search results to reflect
			// this
			return true;
		}	// otherwise we do nothing. 
		return false;
	}
	
	/**
	 * getMaxStar() returns the maximum valid 
	 * star rating for this application.
	 * @return int returns the maximum valid 
	 * star rating for this application.
	 */
	public int getMaxStars() {
		return maxStars;	
	}
	/**
	 * getMinStars() returns the 
	 * minimum valid star rating for this application. 
	 * @return int returns the 
	 * minimum valid star rating for this application
	 */
	public int getMinStars() {
		return minStars;
	}
}
