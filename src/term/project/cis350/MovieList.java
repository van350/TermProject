package term.project.cis350;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;


public class MovieList {
	
	// the current movie holds the movie that is 
	// displayed on the screen OF COURSE I AM NOT
	// SURE WHAT THE OBJECT FOR A MOVIE IS AT THE 
	// MOMENT.
	private static int curMovie;
	private connectToMovieDB conToDB;
	
	public static final int TYPE_INT_ARGB = 2;
	private BufferedImage curPoster;
	private int dWidth;
	private int dHeight;
	private double fWidth;
	private double fHeight;
	
	private final int MIN_STARS = 0;
	private final int MAX_STARS = 5;
		
	public MovieList(){
	}
	
	
	//NOTE I WILL CALL THIS FUNCTION EVERYTIME I NEED A NEW MOVIE POSTER. 
	public ImageIcon getMoviePoster(int desHeight, int desWidth){
		System.out.println("Before Connect");
		conToDB = new connectToMovieDB();
		return getPosterAndSetCurMovie(desHeight, desWidth, false);
	}
	
	public ImageIcon getMovieToWatch(int desHeight, int desWidth){
		return getPosterAndSetCurMovie(desHeight, desWidth, true);
	}
	
	private ImageIcon getPosterAndSetCurMovie(int desHeight, int desWidth, boolean isWatchSug){
		dWidth 	= desWidth;
		dHeight 	= desHeight;
		try {
    	  
			// here a jpg and png work I am not sure what other image
			// formats work that is only the ones that I have tried.     	      	
			curPoster =  ImageIO.read(new File(System.getProperty("user.dir") + "/someMovie.jpg"));
			
			//NOTE: WE SHOULD UPDATE THE curMovie HERE =>
			//curMovie
    	    	  
			return new ImageIcon( scale(curPoster) );
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("BackGround Image File NOT FOUND\n");
			return null;
		}
	}
	
	/**
	 * scale image NOTE THIS IS COPIED FROM STACKOVERFLOW CHRIS VAN...
	 * 
	 * @param sbi image to scale
	 * @param imageType type of image
	 * @param dWidth width of destination image
	 * @param dHeight height of destination image
	 * @param fWidth x-factor for transformation / scaling
	 * @param fHeight y-factor for transformation / scaling
	 * @return scaled image
	 */
	private BufferedImage scale(BufferedImage sbi
			/*, int imageType, int dWidth, int dHeight, double fWidth, double fHeight*/) {
	    BufferedImage dbi = null;
	    if(sbi != null) {
	    	setScalingFactors();
	        dbi = new BufferedImage(dWidth, dHeight, TYPE_INT_ARGB/*imageType*/);
	        Graphics2D g = dbi.createGraphics();
	        AffineTransform at = AffineTransform.getScaleInstance(fWidth, fHeight);
	        g.drawRenderedImage(sbi, at);
	    }
	    return dbi;
	}
	
	private void setScalingFactors( ){
		fWidth 	= (double)dWidth 	/ (double)curPoster.getWidth();
		fHeight = (double)dHeight	/ (double)curPoster.getHeight();
	}
	
	
	public void setMovieScore(int numStars){
		// here need to do something with the number of starts
		// I will be calling this every time someone swipes right
		// or left. NOTE CURRENTLY I AM PLANNING ON A SCALE OF 
		// 0-5 IF THAT NEEDS TO CHANGE PLEASE LET ME KNOW AND I 
		// CAN MAKE THE ADJUSTMENTS.
		
		// curMovie ... = numStars 
	}
	/*
	 * This function is used to return a list of all the available
	 * movie genres. additionally it includes adding an all
	 * genres string that will always be at the top of the list. 
	 * 
	 */
	public String[] genreList(){
		ArrayList<String> types = new ArrayList<String>();
		types.add("Comedy");
		//....
		types.add("Action");
		types.sort(String::compareToIgnoreCase);
		
		// this order makes sure 'All' is always the
		// first in the list. 
		types.add(0,"Pick By Genre");
		
		return types.toArray(new String[types.size()]);
	}
	
	public String[] eraList(){
		ArrayList<String> era = new ArrayList<String>();
		era.add("1950's");
		//....
		era.add("1960's");
		era.add("1970's");
		era.add("1980's");
		era.add("1990's");
		era.add("2000's");
		era.add("2010's");
		era.sort(String::compareToIgnoreCase);
		
		// this order makes sure 'All' is always the
		// first in the list. 
		era.add(0,"Pick By Era");

		for(int i = 0; i < era.size() ; i++){
			System.out.println(era.get(i) + "\n");
		}
		return era.toArray(new String[era.size()]);
	}
	public String[] ratingList(){

		ArrayList<String> rateList = new ArrayList<String>();
		
		for(int i = 0; i <= MAX_STARS; i++){
			rateList.add( Integer.toString( i ) );
		}
		
		rateList.sort(String::compareToIgnoreCase);
		
		// this order makes sure 'All' is always the
		// first in the list. 
		rateList.add(0,"Pick By Rating");

		return rateList.toArray(new String[rateList.size()]);
	}
	public int getMaxStars(){
		return MAX_STARS;	
	}
	public int getMinStars(){
		return MIN_STARS;
	}
}
