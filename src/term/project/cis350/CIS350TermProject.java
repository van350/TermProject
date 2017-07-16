package term.project.cis350;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

/**
 * This class is used to create the GUI.
 * 
 * @author Chris
 *
 */
public final class CIS350TermProject {
	
	/**
	 * Unused Constructor.
	 */
	private CIS350TermProject() { }

	/**
	 * Object to initialize the MovieList Object.
	 */
	private static MovieList movieList;	
	
	/**
	 * derived from standard insert 7.28" Height.
	 */
	static final int POSTERHEIGHT 	= 728 * 2 / 3;
	
	/**
	 * Used to scale preferred panel's height as needed.
	 */
	static final int HEIGHTFACTOR 	= 1;
	
	/**
	 * derived from standard insert 4.94" wide.
	 */
	static final int POSTERWIDTH  	= 
				(int) (POSTERHEIGHT / 1.47);
	
	/**
	 * Used to scale preferred panel's width as needed.
	 */
	static final int WIDTHFACTOR 	= 1;
	
	/**
	 * Default Font Size used.
	 */
	static final int CBFONTSIZE 	= 40 / 3;
	
	/**
	 *  holds the main JFrame of the Application.
	 */
	private static JFrame f;
	
	/**
	 * defines padding around 'f' JFrame.
	 */
	static final int F_PADDING = 40 / 3;	
	
	/**
	 * This Panel holds the Arrow ICONS.
	 */
	private static JPanel p;
	
	/**
	 * Object used to hold current movie poster displayed.
	 */
	private static JLabel background;
	
	/**
	 *  Holds the right arrow Icon image.
	 */
	private static JLabel rightArrow;
	
	/**
	 * Controls Left Padding for proper Right Arrow icon placement.
	 */
	static final int RIGHT_ARROW_LEFT_PAD = 0;
	
	/**
	 * Holds the setting menu Icon image.
	 */
	private static JLabel setting;
	
	/**
	 * Controls the top padding for the setting icon.
	 */
	static final int SETTING_TOP_PAD = -200;
	
	/**
	 * Holds the left arrow Icon image.
	 */
	private static JLabel leftArrow;
	
/**
 * Controls the top padding for right and left arrow image icons.
 */
	static final int RL_ARROW_TOP_PAD = 100;
	
	/**
	 * represents the percentage of panel width the arrow should take.
	 */
	static final double ARROW_PERC_WID = 0.5;
	
	/**
	 * represents the percentage of panel height the arrow should take.
	 */
	static final double ARROW_PERC_HEI = 0.5;
	
	/**
	 * Controls Left Padding for proper Left Arrow icon placement.
	 */
	static final int LEFT_ARROW_LEFT_PAD = -175 / 3;	
	
	/**
	 * Controls Right Padding for proper Left Arrow icon placement.
	 */
	static final int LEFT_ARROW_RIGHT_PAD = 90;
	
	
	
/**
 * This Panel holds the comboBoxes and JButton.
 */
	private static JPanel p2;
	
	/**
	 * Bottom Padding for 'P2' proper placement.
	 */
	static final int P2_TOP_PAD = 225 -  RL_ARROW_TOP_PAD;			
	
	/**
	 *  Holds genre related information for movie selection.
	 */
	private static JComboBox<String> 	genre;
	
	/**
	 * Holds era related information for movie selection.
	 */
	private static JComboBox<String> 	era;
	
	/**
	 * holds rating related information for movie selection.
	 */
	private static JComboBox<String>	othersRating;
	
	/**
	 * button that is used to get a movie suggestion.
	 */
	private static JButton 				getSuggestion;
	
	/**
	 * defines the max star rating for a movie.
	 */
	private static int maxStars;
	
	/**
	 * defines the min star rating for a movie.
	 */
	private static int minStars;
	
	/**
	 * used for element placement in JFrame and JPanels.
	 */
	private static GridBagConstraints gbc;

	/**
	 * Entrance to this application it initiates the 
	 * GUI and waits for user input or to exit the program.
	 * 
	 * @param args unused input.
	 */
	public static void main(final String[] args) {			
		initperiphObj();
		initGUI();		
	}
	
	/*
	 * initGUI is used to initiate the GUI associate with 
	 * the FlixPix Application. 
	 */
	/**
	 * initGUI() is used to initiate the 
	 * components contained within this applications.
	 * JFrame. 
	 */
	private static void initGUI() {
		
		//Initiating the first movie poster
	    background = new JLabel(movieList
	    		.getMoviePoster(POSTERHEIGHT, POSTERWIDTH));
	    //generating the right arrow icon image in
	    //the JLabel and creating a mouseListener
	    //event
	    ImageIcon settingImgIcon = 
	    		new ImageIcon(System.getProperty("user.dir")
	    				+ "/Hamburger_icon.svg2.png");
	    setting = new JLabel(settingImgIcon);
	    setting.addMouseListener(new MouseAdapter() {
	      	  public void mousePressed(final MouseEvent e) {
	      		  getClickInfo(e);
	      	  }
	    });
	    
	    
	    
	    ImageIcon rArrow = new ImageIcon(System.getProperty("user.dir")
	    		+ "/rightHollow2.png");
	    rightArrow = new JLabel(rArrow);
	    rightArrow.addMouseListener(new MouseAdapter() {
	      	  public void mousePressed(final MouseEvent e) {
	      		  getClickInfo(e);
	      	  }
	    });
	    //generating the left arrow icon image 
	    //in the JLabel and creating a mouseListener
	    // event
	    ImageIcon lArrow =  
	    		new ImageIcon(System.getProperty("user.dir") 
	    				+ "/leftHollow2.png");
	    leftArrow  = new JLabel(lArrow);
	    leftArrow.addMouseListener(new MouseAdapter() {
	   	  	  public void mousePressed(final MouseEvent e) {
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
        gbc.insets = new Insets(0, 0, 0, 0);
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
	/**
	 * initPeriphObj() is used to 
	 * initiate other peripheral objects needed to properly
	 * execute this application. As well as 
	 * handle generating any class specific variables
	 * needed from peripheral objects generated. 
	 */
	private static void initperiphObj() {
		movieList = new MovieList();
		maxStars = movieList.getMaxStars();		
		minStars = movieList.getMinStars();
	}
	
	/*
	 * getClickInfo is used to determine which element was
	 * clicked and calls the movieList object as needed
	 * to generate new movies to rate and suggestions for watching
	 */
	/**
	 * getClickInfo(MouseEvent) is used to 
	 * capture mouse clicks information and
	 * execute calls to the movieList object 
	 * in order to cause user input and reaction
	 * to the application.
	 * 
	 * @param e MouseEvent is used to determine 
	 * what element of importance, if any, was clicked. 
	 */
	private static void getClickInfo(final MouseEvent e) {

		if (e.getSource() == rightArrow) {
			
			getNewBackGround(maxStars);
			
		} else if (e.getSource() == leftArrow) {
			getNewBackGround(minStars);
		} else if (e.getSource() == getSuggestion) {
			getNewBackGround(-1);
		} else if (e.getSource() == setting) {
			System.out.println("Hit Setting");
				
		
		}

	}
	
	/**
	 * Sets the rating for the current movie and then updates the GUI.
	 * @param setLastMovieRating The rating given to the most recent movie.
	 */
	private static void getNewBackGround(final int setLastMovieRating) {
		
		if (setLastMovieRating > -1) {
			if(setLastMovieRating == 0){
				//getting new background poster for rating. 
				background = new JLabel(movieList
						.getMoviePoster(
								POSTERHEIGHT, 
								POSTERWIDTH));
			}else{
				//setting personal rating for currently displayed poster
				// to the maximum star rating. 
				movieList.setMovieScore(setLastMovieRating);
				//getting new background poster for rating. 
				background = new JLabel(movieList
						.getMoviePoster(
								POSTERHEIGHT, 
								POSTERWIDTH));
			}
		} else {
			background = new JLabel(movieList
					.getMovieToWatch(
							POSTERHEIGHT, 
							POSTERWIDTH));
		}
		//f.repaint();
		//background.repaint();
	    //f.setContentPane(background);
		f.dispose();
		initGUI();

	}
	
	/**
	 * getComboBoxClickInfo(ActionEvent) 
	 * is used to capture combo box selections
	 * and cause a reaction in the movieList 
	 * object according the the combo box ActionEvent
	 * that was generated.
	 * 
	 * @param e ActionEvent is used to
	 *  determine what combo box entry was clicked.
	 */
	static void getComboBoxClickInfo(final ActionEvent e) {
		if (e.getSource() == era) {
			movieList.setEra(era.getSelectedItem().toString());
		}
		if (e.getSource() == othersRating) {
			movieList.setRating(othersRating
					.getSelectedItem().toString());
		}
		if (e.getSource() == genre) {
			movieList.setGenre(genre.getSelectedItem().toString());
		}
		

	    getNewBackGround(0);
	}
	
	/**
	 * initArrowPanel() is used to 
	 * generate and place the arrow icons on the application 
	 * screen that are used to rate the current poster background. 
	 */
	private static void initArrowPanel() {
		
		// setting the default Frame position. 
		final int framePos = 2;
		// setting the default vertical position
		final int pVertPos = 1;
		// setting the default horizontal position
		final int pHoriPos = 1;

		// initializing the JPanel that is used to hold the arrow
		// icons within the main JFrame, setting the background to 
		// transparent and setting the preferred size of the panel
		p = new JPanel(new GridBagLayout());
      	p.setOpaque(false);
      	p.setPreferredSize(new Dimension((int) 
      			(POSTERWIDTH), (int) (POSTERHEIGHT)));
      
      	f.setLayout(new GridBagLayout());
      	
      	// initializing the GridBagConstraints. 
      	gbc = new GridBagConstraints();
      	// setting the vertical and horizontal location for the gbc
      	gbc.gridx = pHoriPos + 1;
      	gbc.gridy = pVertPos;
      	//setting the Padding associated with 
      	//proper placement of the right arrow icon      	
      	gbc.insets = new Insets(
      			SETTING_TOP_PAD,
      			RIGHT_ARROW_LEFT_PAD, 0, 0);	
      	p.add(setting, gbc);
      	//setting the Padding associated with 
      	//proper placement of the right arrow icon      	
      	gbc.insets = new Insets(
      			RL_ARROW_TOP_PAD, RIGHT_ARROW_LEFT_PAD, 0, 0);		
      	p.add(rightArrow, gbc);
      	// setting the vertical and horizontal location for the gbc
      	gbc.gridx = pHoriPos - 1;
      	gbc.gridy = pVertPos;
      	//setting the padding associated with 
      	//the proper placement of the left arrow icon
      	gbc.insets = new Insets(RL_ARROW_TOP_PAD,
      			LEFT_ARROW_LEFT_PAD, 0, LEFT_ARROW_RIGHT_PAD);
      	p.add(leftArrow, gbc);
      	// setting the vertical and horizontal location for the gbc
      	gbc.gridx = framePos;
      	gbc.gridy = framePos;      
      	f.add(p);
	}
	
	/**
	 * initBottomElements() function is 
	 *used to handle the initialization and placement
	 * of the combo boxes for this application. 
	 */
	private static void initBottomElements() {
		// setting the default vertical 
		//distance associated with the ComboBox options
		final int vertDist = 4;
		// setting the default horizontal 
		//distance associated with the ComboBox options
		final int horiDist = 2;
		//initializing the JPanel object that 
		//holds the ComboBoxes and the suggest movie button
		// additionally it sets the preferred 
		//size and make it transparent. 
	    p2 = new JPanel(new GridBagLayout());
	    p2.setPreferredSize(new Dimension((int) (
	    		POSTERWIDTH * WIDTHFACTOR), 
	    		(int) (POSTERHEIGHT * HEIGHTFACTOR)));
	    p2.setOpaque(false);
	      
	    //generating the genra, era, and 
	    //othersRating JComboBox with the appropriate font configuration
	    genre = new JComboBox<>(movieList.genreList());
	    Font font = new Font((String) "", Font.PLAIN, CBFONTSIZE);
	    //genre.setSelectedIndex(movieList.getSelectIndex());
	    genre.setFont(font);
	    genre.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				getComboBoxClickInfo(e);
			}
	    });
	    era = new JComboBox<>(movieList.eraList());
	    era.setSelectedItem(movieList.getSelectedEra());
	    era.setFont(font);
	    era.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				getComboBoxClickInfo(e);
			}
	    });
	    othersRating = new JComboBox<>(movieList.ratingList());
	    othersRating.setSelectedItem(movieList.getSelectedRating());
	    othersRating.setFont(font);
	    othersRating.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				getComboBoxClickInfo(e);
			}
	    });
		
	    getSuggestion =  new JButton("Get Movie");
	    getSuggestion.setFont(new Font("", Font.PLAIN, CBFONTSIZE));
	    // adding a MouseListener to the getSuggestion BTN. 
	    getSuggestion.addMouseListener(new MouseAdapter() {
  	 	  	public void mousePressed(final MouseEvent e) {
  	 		  	getClickInfo(e);
  	 	  	}
        });
		
	    /********************** section start **********************/
	    // the following section defines the proper placement of the 
	    // combo boxes and the get suggestion buttons. 
	    gbc.gridx = horiDist - 1;
	    gbc.gridy = vertDist;
	    p2.add(genre, gbc);
	    gbc.gridx = horiDist;
	    gbc.gridy = vertDist;
	    p2.add(era, gbc);
	    gbc.gridx = horiDist + 1;
	    gbc.gridy = vertDist;
        p2.add(othersRating, gbc);
	    gbc.gridx = horiDist;
	    gbc.gridy = vertDist + 1;
	    p2.add(getSuggestion, gbc);

	    gbc.gridx = 0;
	    gbc.gridy = vertDist + 1;
	    gbc.insets = new Insets(P2_TOP_PAD, 0, 0, 0);
        f.add(p2, gbc);
	    /*************************** section  end **********/

	}
}