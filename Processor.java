/**
 * Starter code for Processor - the class that processes images.
 * <p>
 * This class manipulated Java BufferedImages, which are effectively 2d arrays
 * of pixels. Each pixel is a single integer packed with 4 values inside it.
 * <p>
 * I have included two useful methods for dealing with bit-shift operators so
 * you don't have to. These methods are unpackPixel() and packagePixel() and do
 * exactly what they say - extract red, green, blue and alpha values out of an
 * int, and put the same four integers back into a special packed integer. 
 * 
 * @author Jordan Cohen
 * @author Lucy Zhao
 * @version December 2020
 */

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.util.ArrayList;
import java.awt.Color;
import java.lang.Math;
import java.util.stream.IntStream;

public class Processor  
{
    private static ArrayList<BufferedImage> versions;
    private static int index;
    
    /**
     * Applies filters on the image. Filters include: redify, blueify,
     * greenify, negative and grayscale
     * 
     * @param bi        the image to be adjusted
     * @param filter    the type of filter to be applied
     */
    public static void loopPixels (BufferedImage bi, String filter)
    {
        // Get image size to use in for loops
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();
        // Using array size as limit
        for (int x = 0; x < xSize; x++)
        {
            for (int y = 0; y < ySize; y++)
            {
                // Calls method in BufferedImage that returns R G B and alpha values
                // encoded together in an integer
                int rgb = bi.getRGB(x, y);
                // Call the unpackPixel method to retrieve the four integers for
                // R, G, B and alpha and assign them each to their own integer
                int[] rgbValues = unpackPixel (rgb);
                
                int alpha = rgbValues[0];
                int red = rgbValues[1];
                int green = rgbValues[2];
                int blue = rgbValues[3];

                if (filter.equals("blueify")) {
                    int[] fixed = blueify(red, green, blue);
                    red = fixed[0];
                    green = fixed[1];
                    blue = fixed[2];
                }
                else if (filter.equals("redify")) {
                    int[] fixed = redify(red, green, blue);
                    red = fixed[0];
                    green = fixed[1];
                    blue = fixed[2];
                }  
                else if (filter.equals("greenify")) {
                    int[] fixed = greenify(red, green, blue);
                    red = fixed[0];
                    green = fixed[1];
                    blue = fixed[2];
                }  
                else if (filter.equals("negative")) {
                    int[] fixed = negative(red, green, blue);
                    red = fixed[0];
                    green = fixed[1];
                    blue = fixed[2];
                }     
                else if (filter.equals("grayscale")) {
                    int fixed = grayscale(red, green, blue);
                    red = fixed; green = fixed; blue = fixed;
                }
                else if (filter.equals("swap"))
                {
                    // Swaps red with blue, green with red and blue with green
                    red = rgbValues[3]; green = rgbValues[1]; blue = rgbValues[2];
                }
                int newColour = packagePixel (red, green, blue, alpha);
                bi.setRGB (x, y, newColour);
            }
        }
        updateVersion(bi);
    }
    
    /**
     * This method will increase the green value while reducing the red 
     * and blue values.
     * 
     * @param r     the red colour
     * @param g     the green colour
     * @param b     the blue colour
     * 
     * @return int[] the new rgb values
     */
    public static int[] greenify (int r, int g, int b)
    {
        if (b >= 50) b--;
        if (g < 254) g+=2;
        if (r >= 50) r--;
        
        return new int[] {r, g, b};
    }
    
    /**
     * This method will increase the red value while reducing the blue 
     * and green values.
     * 
     * @param r     the red colour
     * @param g     the green colour
     * @param b     the blue colour
     * 
     * @return int[] the new rgb values
     */
    public static int[] redify (int r, int g, int b)
    {
        if (b >= 50) b--;
        if (r < 254) r+=2;
        if (g >= 50) g--;
        
        return new int[] {r, g, b};
    }
    
    /**
     * Example colour altering method by Mr. Cohen. This method will
     * increase the blue value while reducing the red and green values.
     * 
     * Demonstrates use of packagePixel() and unpackPixel() methods.
     * 
     * @param r     the red colour
     * @param g     the green colour
     * @param b     the blue colour
     * 
     * @return int[] the new rgb values
     */
    public static int[] blueify (int r, int g, int b)
    {
        if (b < 254) b += 2;
        if (r >= 50) r--;
        if (g >= 50) g--;
        
        return new int[] {r, g, b};
    }

    /**
     * Applies a negative filter to the image
     * 
     * @param r     the red colour
     * @param g     the green colour
     * @param b     the blue colour
     * 
     * @return int[] the new rgb values
     */
    public static int[] negative (int r, int g, int b)
    {
        b = 255 - b;
        r = 255 - r;
        g = 255 - g;
        return new int[] {r, g, b};
    }
    
    /**
     * Applies a grayscale filter to the image
     * 
     * @param r     the red colour
     * @param g     the green colour
     * @param b     the blue colour
     * 
     * @return int  the average value
     */
    public static int grayscale (int r, int g, int b)
    {
        int average = (b + r + g) / 3;
        return average;
    }
    
    /**
     * Applies a pixelate effect to the image.
     * 
     * @param bi    the image
     * @author      bchociej from StackOverFlow
     * https://stackoverflow.com/questions/15777821/how-can-i-pixelate-a-jpg-with-java
     */
    public static void pixelate (BufferedImage bi)
    {
        int PIX_SIZE = 6;
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();
        for (int x = 0; x < xSize; x+=PIX_SIZE)
        {
            for (int y = 0; y < ySize; y+=PIX_SIZE)
            {
                int rgb = bi.getRGB(x, y);
                for (int xd = x; (xd < x + PIX_SIZE) && (xd < xSize); xd++)
                {
                   for(int yd = y; (yd < y + PIX_SIZE) && (yd < ySize); yd++) 
                   {
                     bi.setRGB(xd, yd, rgb);
                   }
                }
            }
        }
        updateVersion(bi);
    }
    
    /**
     * Applies a gaussian blur effect to the image.
     * 
     * @param bi    the image
     * @author      fabian from StackOverFlow
     * https://stackoverflow.com/questions/39684820/java-implementation-of-gaussian-blur 
     */
    public static void gaussianBlur (BufferedImage bi)
    {
        int[] filter = {1, 2, 1, 2, 4, 2, 1, 2, 1};
        int filterWidth = 3;
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();
        int sum = IntStream.of(filter).sum();
        
        int[] input = bi.getRGB(0, 0, xSize, ySize, null, 0, xSize);

        int[] output = new int[input.length];
        
        int pixelIndexOffset = xSize - filterWidth;
        int centerOffsetX = filterWidth / 2;
        int centerOffsetY = filter.length / filterWidth / 2;
        for (int h = ySize - filter.length / filterWidth + 1, w = xSize - filterWidth + 1, y = 0; y < h; y++)
        {
            for (int x = 0; x < w; x++)
            {
                int red = 0, green = 0, blue = 0;

                for (int filterIndex = 0, pixelIndex = y * xSize + x; filterIndex < filter.length; pixelIndex += pixelIndexOffset)
                {
                    for (int fx = 0; fx < filterWidth; fx++, pixelIndex++, filterIndex++) {
                        int col = input[pixelIndex];
                        int factor = filter[filterIndex];
    
                        // sum up color channels seperately
                        red += ((col >>> 16) & 0xFF) * factor;
                        green += ((col >>> 8) & 0xFF) * factor;
                        blue += (col & 0xFF) * factor;
                    }
                }
                red /= sum;
                green /= sum;
                blue /= sum;
                
                output[x + centerOffsetX + (y + centerOffsetY) * xSize] = (red << 16) | (green << 8) | blue | 0xFF000000;

            }
        }
        BufferedImage result = new BufferedImage(xSize, ySize, BufferedImage.TYPE_INT_ARGB);
        result.setRGB(0, 0, xSize, ySize, output, 0, xSize);
        
        // move pixels from result to bi
        for (int x = 0; x < xSize; x++) {
            for (int y = 0; y < ySize; y++) {
                bi.setRGB(x, y, result.getRGB(x, y));
            }
        }
        updateVersion(bi);
    }
    
    /**
     * Distorts the imagine into a spherical shape
     * Credit: http://popscan.blogspot.com/2012/04/fisheye-lens-equation-simple-fisheye.html
     * 
     * @param bi    the image to be distorted
     */
    public static void spherify (BufferedImage bi)
    {
        double w = bi.getWidth();
        double h = bi.getHeight();
        int[] srcpixels = bi.getRGB(0, 0, (int)w, (int)h, null, 0, (int)w);
        int[] dstpixels = new int[(int)(w*h)];            
        for (int y = 0; y < h; y++) {                                
            double ny = ((2*y)/h)-1;                        
            double ny2 = ny*ny;                                
            for (int x = 0; x < w; x++) {                            
                double nx = ((2*x)/w)-1;                    
                double nx2 = nx*nx;
                double r = Math.sqrt(nx2+ny2);                
                if (0.0 <= r && r <= 1.0) {                            
                    double nr = Math.sqrt(1.0-r*r);            
                    nr = (r + (1.0-nr)) / 2.0;
                    if (nr<=1.0) {
                        double theta = Math.atan2(ny,nx);         
                        double nxn = nr*Math.cos(theta);        
                        double nyn = nr*Math.sin(theta);        
                        int x2 = (int)(((nxn+1)*w)/2.0);        
                        int y2 = (int)(((nyn+1)*h)/2.0);        
                        int srcpos = (int)(y2*w+x2);            
                        if (srcpos >= 0 & srcpos < w*h) {
                            dstpixels[(int)(y*w+x)] = srcpixels[srcpos];    
                        }
                    }
                }
            }
        }
        BufferedImage newBi = new BufferedImage((int)w, (int)h, 3);
        newBi.setRGB(0, 0, (int)w, (int)h, dstpixels, 0, (int)w);
        
        // move pixels from result to bi
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                bi.setRGB(x, y, newBi.getRGB(x, y));
            }
        }
        updateVersion(bi);
        
    }
    /** 
     * Adjust the hues based on a percentage. Used the javadocs to find
     * approriate methods:
     * https://docs.oracle.com/javase/8/docs/api/java/awt/Color.html#RGBtoHSB-int-int-int-float%3aA-
     * 
     * @param bi            the image to be adjusted
     * @param percentage    the percentage to change the hue
     * 
     */
    public static void adjustHue (BufferedImage bi, float percentage)
    {
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();
        for (int x = 0; x < xSize; x++)
        {
            for (int y = 0; y < ySize; y++)
            {
                int rgb = bi.getRGB(x, y);
                int[] rgbValues = unpackPixel (rgb);
                
                int alpha = rgbValues[0];
                int red = rgbValues[1];
                int green = rgbValues[2];
                int blue = rgbValues[3];

                float[] hsbValues = Color.RGBtoHSB(red, green, blue, null);
     
                hsbValues[0] += percentage;

                int c = Color.HSBtoRGB(hsbValues[0], hsbValues[1], hsbValues[2]);
                red = (c>>16)&0xFF; green = (c>>8)&0xFF; blue = c&0xFF;
                int newColour = packagePixel (red, green, blue, alpha);
                bi.setRGB (x, y, newColour);
            }
        }
        updateVersion(bi);
    }
    
    /**
     * Adjusts the contrast, brightness and warmness/coolness of an
     * image.
     * Credit for contract formula:
     * https://www.dfstudios.co.uk/articles/programming/image-programming-algorithms/image-processing-algorithms-part-5-contrast-adjustment/
     * 
     * @param bi    the image to be adjusted
     * @param type  type of filter to be applied
     * @param isAdd whether to add or subtract the filter
     */
    public static void adjustFilters (BufferedImage bi, String type, boolean isAdd)
    {
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();
        for (int x = 0; x < xSize; x++)
        {
            for (int y = 0; y < ySize; y++)
            {
                int rgb = bi.getRGB(x, y);
                int[] rgbValues = unpackPixel (rgb);
                
                int alpha = rgbValues[0];
                int red = rgbValues[1];
                int green = rgbValues[2];
                int blue = rgbValues[3];
                
                if (type.equals("contrast"))
                {
                    if (isAdd)
                    {
                        if (red < 250) red = (int)(1.02*(red-128)+130); 
                        if (blue < 250) blue = (int)(1.02*(blue-128)+130);
                        if (green < 250) green = (int)(1.02*(green-128)+130);
                    }
                    else
                    {
                        red = (int)(0.98*(red-128)+130); 
                        blue = (int)(0.98*(blue-128)+130);
                        green = (int)(0.98*(green-128)+130);
                    }
                }   
                else if (type.equals("light")) {
                    if (isAdd)
                    {
                        if (red < 250) red += 2;
                        if (blue < 250) blue += 2;
                        if (green < 250) green += 2;
                    }
                    else
                    {
                        if (red > 5) red -= 2;
                        if (blue > 5) blue -= 2;
                        if (green > 5) green -= 2;
                    }
                }
                else if (type.equals("warm")) {
                    if (isAdd)
                    {
                        if (red < 220) red += 2;
                        if (blue > 50) blue -= 2;
                    }
                    else
                    {
                        if (red > 50) red -= 2;
                        if (blue < 220) blue += 2;
                    }
                }

                int newColour = packagePixel (red, green, blue, alpha);
                bi.setRGB (x, y, newColour);
            }
        }
        updateVersion(bi);
    }
    
    /**
     * Flips the image either horizontally or vertically
     * 
     * @param bi    the image to be flipped
     * @param s     the type of flip
     */
    public static void flip (BufferedImage bi, String s)
    {
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();
        // Temp image, to store pixels as we reverse everything
        BufferedImage newBi = new BufferedImage (xSize, ySize, 3);
        
        if (s.equals("Horizontal"))
        {
            for (int x = 0; x < xSize / 2; x++) {
                for (int y = 0; y < ySize; y++)
                {   
                    int pixel = bi.getRGB(x, y);
                    newBi.setRGB(x, y, bi.getRGB(xSize-x-1, y));
                    newBi.setRGB(xSize-x-1, y, pixel);
                }
            }
        }
        if (s.equals("Vertical"))
        {
            for (int y = 0; y < ySize / 2; y++) {
                for (int x = 0; x < xSize; x++)
                {   
                    int pixel = bi.getRGB(x, y);
                    newBi.setRGB(x, y, bi.getRGB(x, ySize-y-1));
                    newBi.setRGB(x, ySize-y-1, pixel);
                }
            }
        }
        for (int x = 0; x < xSize; x++) {
            for (int y = 0; y < ySize; y++)
            {   
                int pixel = newBi.getRGB(x, y);
                bi.setRGB(x, y, pixel);
            }
        }
        updateVersion(bi);
    }
    
    /**
     * Rotates the image either by 90 or 180 degrees counter clockwise
     * 
     * @param bi    the image to be rotated
     * @param r     the type of rotation
     */
    public static BufferedImage rotate (BufferedImage bi, String r)
    {
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();
        BufferedImage newBi;
        if (r.equals("90"))
        {
            newBi = new BufferedImage (ySize, xSize, 3);
            int newRow = 0, newCol;
            for (int y = ySize-1; y >= 0; y--) {
                newCol = 0;
                for (int x = 0; x < xSize; x++)
                {   
                    int pixel = bi.getRGB(x, y);
                    newBi.setRGB(newRow, newCol, pixel);
                    newCol ++;
                }
                newRow++;
            }
        }
        else
        {
            newBi = new BufferedImage (xSize, ySize, 3);
            for (int y = 0; y < ySize / 2; y++) {
                for (int x = 0; x < xSize; x++)
                {   
                    int pixel = bi.getRGB(x, y);
                    newBi.setRGB(x, y, bi.getRGB(x, ySize-y-1));
                    newBi.setRGB(x, ySize-y-1, pixel);
                }
            }
        }
        updateVersion(bi);
        return newBi;
    }
    
    /**
     * Resets the undo/redo ArrayList
     * 
     * @param bi    the first/original image
     */
    public static void start(BufferedImage bi)
    {
        versions = new ArrayList<BufferedImage>();
        versions.add(deepCopy(bi));
        index = 0;
    }
    
    /**
     * Updates the ArrayList with all the versions
     * 
     * @param bi    the newest editted version of the image
     */
    public static void updateVersion(BufferedImage bi)
    {
        // Remove all redo copies once a change is made
        while (index != versions.size()-1)
        {
            versions.remove(versions.size()-1);
        }
        index++;
        versions.add(deepCopy(bi));
    }
    
    /**
     * Undos a change, given that there is an existing previous version
     */
    public static BufferedImage undo() 
    {
        if (index > 0)
        {
            index--;
        }
        return versions.get(index);
    }
    
    /**
     * Redos a change, given that there is an existing redo version
     */
    public static BufferedImage redo() 
    {
        if (index < versions.size()-1)
        {
            index++;
        }
        return versions.get(index);
    }
    
    /**
     * Returns if redos can be made
     * 
     * @return boolean  true if redos can be made, otherwise false
     */
    public static boolean canRedo()
    {
        return index < versions.size()-1;
    }
    
    /**
     * Returns if undos can be made
     * 
     * @return boolean  true if undos can be made, otherwise false
     */
    public static boolean canUndo()
    {
        return index > 0;
    }
    
    /**
     * Makes a deep copy of a image.
     * 
     * @param bi                the image to be duplicated
     * @return BufferedImage    the duplicate image
     */
    public static BufferedImage deepCopy(BufferedImage bi) {  
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultip = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultip, null);
    }

    /**
     * Takes in an rgb value - the kind that is returned from BufferedImage's
     * getRGB() method - and returns 4 integers for easy manipulation.
     * 
     * By Jordan Cohen
     * Version 0.2
     * 
     * @param rgbaValue The value of a single pixel as an integer, representing<br>
     *                  8 bits for red, green and blue and 8 bits for alpha:<br>
     *                  <pre>alpha   red     green   blue</pre>
     *                  <pre>00000000000000000000000000000000</pre>
     * @return int[4]   Array containing 4 shorter ints<br>
     *                  <pre>0       1       2       3</pre>
     *                  <pre>alpha   red     green   blue</pre>
     */
    public static int[] unpackPixel (int rgbaValue)
    {
        int[] unpackedValues = new int[4];
        // alpha
        unpackedValues[0] = (rgbaValue >> 24) & 0xFF;
        // red
        unpackedValues[1] = (rgbaValue >> 16) & 0xFF;
        // green
        unpackedValues[2] = (rgbaValue >>  8) & 0xFF;
        // blue
        unpackedValues[3] = (rgbaValue) & 0xFF;

        return unpackedValues;
    }

    /**
     * Takes in a red, green, blue and alpha integer and uses bit-shifting
     * to package all of the data into a single integer.
     * 
     * @param   int red value (0-255)
     * @param   int green value (0-255)
     * @param   int blue value (0-255)
     * @param   int alpha value (0-255)
     * 
     * @return int  Integer representing 32 bit integer pixel ready
     *              for BufferedImage
     */
    public static int packagePixel (int r, int g, int b, int a)
    {
        int newRGB = (a << 24) | (r << 16) | (g << 8) | b;
        return newRGB;
    }
    

}
