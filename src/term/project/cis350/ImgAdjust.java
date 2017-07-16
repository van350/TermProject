package term.project.cis350;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 * Adjusts the size of an image file.
 * 
 * @author Chris
 *
 */
public class ImgAdjust {

	/**
	 * used as a constant for the image type used.
	 */
	public static final int TYPE_INT_ARGB = 2;
	
	/**
	 * constructor does nothing.
	 */
	ImgAdjust() { };
	
	/**
	 * scale image NOTE THIS IS COPIED FROM STACKOVERFLOW CHRIS VAN...
	 * 
	 * @param sbi image to scale
	 * @param dWidth width of destination image
	 * @param dHeight height of destination image

	 * @return scaled image
	 */
	public BufferedImage scaleToSize(final BufferedImage sbi, 
			/*int imageType,*/ final int dWidth, final int dHeight
			/*, double fWidth, double fHeight*/) {

		
	    BufferedImage dbi = null;
	    if (sbi != null) {
	    	// this section is used to set the scaling factors
			double fWidth 	= 
					(double) dWidth 	
					/ (double) sbi.getWidth();
			double fHeight = 
					(double) dHeight	
					/ (double) sbi.getHeight();
			
	        dbi = new BufferedImage(dWidth, dHeight, 
	        		TYPE_INT_ARGB/*imageType*/);
	        Graphics2D g = dbi.createGraphics();
	        AffineTransform at = AffineTransform
	        		.getScaleInstance(fWidth, fHeight);
	        g.drawRenderedImage(sbi, at);
	    }
	    return dbi;
	}
	/**
	 * scale image NOTE THIS IS COPIED FROM STACKOVERFLOW CHRIS VAN...
	 * 
	 * @param sbi image to scale
	 * @param refWidth is the reference Width
	 * @param percOfWidth desired percentage of refWidth
	 * @param refHeight is the reference Height
	 * @param percOfHeight desired percentage of refHeight
	 * @return scaled image based on percentage or height and width
	 */
	public BufferedImage scaleToPercent(
			final BufferedImage sbi, 
			final double refWidth, 
			final double percOfWidth,
			final double refHeight, 
			final double percOfHeight) {
		
	    BufferedImage dbi = null;
	    if (sbi != null) {
	    	// sets desired width of input image
	    	double dWidth = 
	    			refWidth * percOfWidth;		
	    	// sets desired height of input image
	    	double dHeight = 
	    			refHeight * percOfHeight;			
	    	// this section is used to set the scaling factors
			double fWidth 	= 
					(double) dWidth 
					/ (double) sbi.getWidth();
			double fHeight = 
					(double) dHeight 
					/ (double) sbi.getHeight();

	        dbi = new BufferedImage((int) dWidth, (int) dHeight, 
	        				TYPE_INT_ARGB/*imageType*/);
	        Graphics2D g = dbi.createGraphics();
	        AffineTransform at = AffineTransform
	        		.getScaleInstance(fWidth, fHeight);
	        g.drawRenderedImage(sbi, at);
	    }
	    return dbi;
	}
}
