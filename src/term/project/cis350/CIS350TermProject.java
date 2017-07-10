package term.project.cis350;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;


public class CIS350TermProject {
	
	static JFrame f;								// holds the main JFrame of the Application
	static final int F_PADDING = 40 /2;				// defines padding around 'f' JFrame
	static JPanel p;								// This Panel holds the Arrow ICONS	
	static JPanel p2;								// This Panel holds the comboBoxes and JButton
	static final int P2_TOP_PAD = 800 /2;				// Top Padding for 'P2' proper placement
	static JLabel background;						// Object used to hold current movie poster displayed
	static JLabel rightArrow;						// Holds the right arrow Icon image 
	static final int RIGHT_ARROW_LEFT_PAD = 425 /2;	// Controls Left Padding for proper Right Arrow icon placement  
	static JLabel leftArrow;						// Holds the left arrow Icon image
	static final int LEFT_ARROW_LEFT_PAD = -175 /2;	// Controls Left Padding for proper Left Arrow icon placement
	static final int LEFT_ARROW_RIGHT_PAD = 200 /2;	// Controls Right Padding for proper Left Arrow icon placement
	static JComboBox genre;							// Holds genre related information for movie selection
	static JComboBox era;							// Holds era related information for movie selection
	static JComboBox othersRating;					// holds rating related information for movie selection
	static JButton getSuggestion;					// button that is used to get a movie suggestion
	
	static MovieList movieList;						// Object to initialize the MovieList Object
	
	static final int POSTERHEIGHT 	= 728 * 2 /2;		// derived from standard insert 7.28" Height.
	static final int HEIGHTFACTOR 	= 2;			// Used to scale preferred panel's height as needed
	static final int POSTERWIDTH  	= 494 * 2 /2;		// derived from standard insert 4.94" wide.'
	static final int WIDTHFACTOR 	= 1;			// Used to scale preferred panel's width as needed
	static final int CBFONTSIZE 	= 40 /2;			// Default Font Size used 
	static int maxStars;							// defines the max star rating for a movie.
	static int minStars;							// defines the min star rating for a movie.
	
	private static GridBagConstraints gbc;			// used for element placement in JFrame and JPanels

	/*
	 * Entrance to this application it initiates the 
	 * GUI and waits for user input or to exit the program
	 */
	public static void main(String[] args) {			
		initperiphObj();
		initGUI();		
	}
	
	/*
	 * initGUI is used to initiate the GUI associate with 
	 * the FlixPix Application. 
	 */
	private static void initGUI(){
		
		//Initiating the first movie poster
	    background = new JLabel( movieList.getMoviePoster(POSTERHEIGHT, POSTERWIDTH) );
	    //generating the right arrow icon image in the JLabel and creating a mouseListener
	    // event
	    rightArrow = new JLabel( new ImageIcon(System.getProperty("user.dir") + "/rightHollow.png"));
	    rightArrow.addMouseListener(new MouseAdapter(){
	      	  public void mousePressed(MouseEvent e){
	      		  getClickInfo(e);
	      	  }
	    });
	    //generating the left arrow icon image in the JLabel and creating a mouseListener
	    // event
	    leftArrow  = new JLabel( new ImageIcon(System.getProperty("user.dir") + "/leftHollow.png"));
	    leftArrow.addMouseListener(new MouseAdapter(){
	   	  	  public void mousePressed(MouseEvent e){
	      		  getClickInfo(e);
	      	  }
	    });
	      
	    //creating a JFrame that will hold all elements of this applications
	    // GUI information and setting the Application title. 
	    f = new JFrame("FlixPix");
	    // setting the background image via JPanel
	    f.setContentPane(background);
	    // setting application to exit on close/
	    f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	      
	    // calling function to place arrow panel appropriately.
	    initArrowPanel();

	    // calling function to place bottom elements appropriately. 
        gbc.insets = new Insets(0,0,0,0);
        initBottomElements();
          
        // setting the main JFrames size and visibility. 
	    int width = POSTERWIDTH + F_PADDING;
	    int height = POSTERHEIGHT + F_PADDING;
        f.setLocationRelativeTo(null);
	    f.setSize(width, height);
	    f.setVisible(true);
	}
	/*
	 * This function is used to initialize the movieList object as
	 * well as gets this applications maxStar and minStar values 
	 */
	private static void initperiphObj(){
		movieList = new MovieList();
		maxStars = movieList.getMaxStars();
		minStars = movieList.getMinStars();
	}
	
	/*
	 * getClickInfo is used to determine which element was
	 * clicked and calls the movieList object as needed
	 * to generate new movies to rate and suggestions for watching
	 */
	private static void getClickInfo(MouseEvent e){
		//int x = e.getX();
		//int y = e.getY();
		if(e.getSource() == rightArrow){
			//setting personal rating for currently displayed poster
			// to the maximum star rating. 
			movieList.setMovieScore(maxStars);			
			//getting new background poster for rating. 
			background = new JLabel( movieList.getMoviePoster(POSTERHEIGHT, POSTERWIDTH) );
		}else if(e.getSource() == leftArrow){
			//setting personal rating for currently displayed poster
			// to the minimal star rating. 
			movieList.setMovieScore(minStars);
			//getting new background poster for rating. 
			background = new JLabel( movieList.getMoviePoster(POSTERHEIGHT, POSTERWIDTH) );
		}else if(e.getSource() == getSuggestion){
			System.out.println("Hit Button");
			// when getSuggestion BTN is pressed it does not rate the
			// current movie on screen. 
			//getting new background poster for rating. 
			background = new JLabel( movieList.getMovieToWatch(POSTERHEIGHT, POSTERWIDTH) );
		}
		//System.out.println(x + " , "  + y);
	}
	
	static void getComboBoxClickInfo(ActionEvent e){
		if(e.getSource() == era){
			movieList.setEra( era.getSelectedItem().toString() );
		}
		if(e.getSource() == othersRating){
			movieList.setEra( othersRating.getSelectedItem().toString() );
		}
		if(e.getSource() == genre){
			movieList.setEra( genre.getSelectedItem().toString() );
		}
	}
	
	/*
	 * initArrowPanel is used to generate the appropriate placement of
	 * the arrow icon images within the application based on the desired
	 * poster width and height. 
	 */
	private static void initArrowPanel(){
		
		// setting the default Frame position. 
		final int FRAME_POS = 2;
		// setting the default vertical position
		final int P_VERT_POS = 1;
		// setting the default horizontal position
		final int P_HORI_POS = 1;

		// initializing the JPanel that is used to hold the arrow
		// icons within the main JFrame, setting the background to 
		// transparent and setting the preferred size of the panel
		p = new JPanel( new GridBagLayout() );
      	p.setOpaque(false);
      	p.setPreferredSize(new Dimension((int) (POSTERWIDTH), (int) (POSTERHEIGHT ) ));
      
      	f.setLayout(new GridBagLayout());
      	
      	// initializing the GridBagConstraints. 
      	gbc = new GridBagConstraints();      	
      	//setting the Padding associated with proper placement of the right arrow icon      	
      	gbc.insets = new Insets(0,RIGHT_ARROW_LEFT_PAD,0,0);		
      	// setting the vertical and horizontal location for the gbc
      	gbc.gridx = P_HORI_POS + 1;
      	gbc.gridy = P_VERT_POS;
      	p.add( rightArrow, gbc);
      	// setting the vertical and horizontal location for the gbc
      	gbc.gridx = P_HORI_POS - 1;
      	gbc.gridy = P_VERT_POS;
      	//setting the padding associated with the proper placement of the left arrow icon
      	gbc.insets = new Insets(0,LEFT_ARROW_LEFT_PAD,0,LEFT_ARROW_RIGHT_PAD);
      	p.add(leftArrow, gbc);
      	// setting the vertical and horizontal location for the gbc
      	gbc.gridx = FRAME_POS;
      	gbc.gridy = FRAME_POS;      
      	f.add(p);
	}
	
	/*
	 * This function handles the initialization and placement of the 
	 * ComboBoxes for this application. 
	 */
	private static void initBottomElements(){
		// setting the default vertical distance associated with the ComboBox options
		final int VERTDIST = 4;
		// setting the default horizontal distance associated with the ComboBox options
		final int HORIDIST = 2;
		//initializing the JPanel object that holds the ComboBoxes and the suggest movie button
		// additionally it sets the preferred size and make it transparent. 
	    p2 = new JPanel( new GridBagLayout() );
	    p2.setPreferredSize(new Dimension((int) (POSTERWIDTH * WIDTHFACTOR), (int) (POSTERHEIGHT * HEIGHTFACTOR) ));
	    p2.setOpaque(false);
	      
	    //generating the genra, era, and othersRating JComboBox with the appropriate font configuration
	    genre = new JComboBox( movieList.genreList() );
	    Font font = new Font((String) "", Font.PLAIN, CBFONTSIZE);
	    genre.setFont(font);
	    genre.addActionListener(new ActionListener (){
			@Override
			public void actionPerformed(ActionEvent e) {
				getComboBoxClickInfo(e);
			}
	    });
	    era = new JComboBox( movieList.eraList() );
	    era.setFont(font);
	    era.addActionListener(new ActionListener (){

			@Override
			public void actionPerformed(ActionEvent e) {
				getComboBoxClickInfo(e);
			}
	    });
	    othersRating = new JComboBox( movieList.ratingList() );
	    othersRating.setFont(font);
	    othersRating.addActionListener(new ActionListener (){
			@Override
			public void actionPerformed(ActionEvent e) {
				getComboBoxClickInfo(e);
			}
	    });
		
	    getSuggestion =  new JButton("Get Movie");
	    getSuggestion.setFont(new Font("", Font.PLAIN, CBFONTSIZE));
	    // adding a MouseListener to the getSuggestion BTN. 
	    getSuggestion.addMouseListener(new MouseAdapter(){
  	 	  	public void mousePressed(MouseEvent e){
  	 		  	getClickInfo(e);
  	 	  	}
        });
		
	    /************************************ section start *****************************************/
	    // the folloging section defines the proper placement of the 
	    // combo boxes and the get suggestion buttons. 
	    gbc.gridx = HORIDIST - 1;
	    gbc.gridy = VERTDIST;
	    p2.add( genre , gbc);
	    gbc.gridx = HORIDIST;
	    gbc.gridy = VERTDIST;
	    p2.add( era , gbc);
	    gbc.gridx = HORIDIST + 1;
	    gbc.gridy = VERTDIST;
        p2.add( othersRating , gbc);
	    gbc.gridx = HORIDIST;
	    gbc.gridy = VERTDIST + 1;
	    p2.add( getSuggestion , gbc);

	    gbc.gridx = 0;
	    gbc.gridy = VERTDIST + 1;
	    gbc.insets = new Insets(P2_TOP_PAD,0,0,0);
        f.add(p2, gbc);
	    /************************************* section  end ******************************************/
	}
}