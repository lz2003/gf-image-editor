import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.io.File;
import javax.swing.JOptionPane;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;

/**
 * Starter code for Image Manipulation Array Assignment.
 * 
 * The class Processor contains all of the code to actually perform
 * transformation. The rest of the classes serve to support that
 * capability. This World allows the user to choose transformations
 * and open files.
 * 
 * Add to it and make it your own!
 * 
 * @author Jordan Cohen
 * @author Lucy Zhao
 * @version December 2020
 */
public class Background extends World
{
    // Constants:
    private final String STARTING_FILE = "example.png";

    // Objects and Variables:
    private ImageHolder image;
    
    private Icon currSelect = null;

    private Flip hRevButton;
    private Flip vRevButton;
    private Rotate button90;
    private Rotate button180;
    private Filter filterButton;
    private Adjust adjButton;
    private Experimental expButton;
    private OpenSave open;
    private OpenSave save;
    
    private Undo undo;
    private Redo redo;
    
    private Bar bar;

    private String fileName;

    /**
     * Constructor for objects of class Background.
     * 
     */
    public Background()
    {    
        super(800, 600, 1); 
        // Initialize buttons and the image
        image = new ImageHolder(STARTING_FILE);
        hRevButton = new Flip("Horizontal");
        vRevButton = new Flip("Vertical");
        button90 = new Rotate("90");
        button180 = new Rotate("180");
        
        filterButton = new Filter(image);
        bar = new Bar();
        adjButton = new Adjust(image);
        expButton = new Experimental(image);
        undo = new Undo();
        redo = new Redo();
         
        open = new OpenSave("open");
        save = new OpenSave("save");
        
        // Add objects to the screen
        addObject (image, 330, 310);
        addObject (bar, getWidth()/2, 20);
        addObject (undo, 25, 20);
        addObject (redo, 70, 20);
        addObject (hRevButton, 115, 20);
        addObject (vRevButton, 160, 20);
        addObject (button90, 205, 20);
        addObject (button180, 250, 20);
        addObject (adjButton, 295, 20);
        addObject (filterButton, 340, 20);
        addObject (expButton, 385, 20);
        addObject(open, getWidth()-25, 20);
        addObject (save, getWidth()-70, 20);
        
        Processor.start(image.getBufferedImage());
    }

    /**
     * Act() method just checks for mouse input
     */
    public void act ()
    {
        checkMouse();
    }

    /**
     * Check for user clicking on a button
     */
    private void checkMouse ()
    {
        // Avoid excess mouse checks - only check mouse if somethething is clicked.
        if (Greenfoot.mouseClicked(null))
        {
            if (Greenfoot.mouseClicked(filterButton)){
                if (currSelect != null) currSelect.close();
                if (currSelect == filterButton)
                {
                    currSelect = null;
                }
                else {
                    filterButton.open();
                    currSelect = filterButton;
                }
            }
            else if (Greenfoot.mouseClicked(hRevButton)){
                Processor.flip(image.getBufferedImage(), "Horizontal");
            }
            else if (Greenfoot.mouseClicked(vRevButton)){
                Processor.flip(image.getBufferedImage(), "Vertical");
            }
            else if (Greenfoot.mouseClicked(undo)){
                if (Processor.canUndo())
                {
                    BufferedImage b = Processor.undo();
                    image.setImage(createGreenfootImageFromBI(b));
                }
            }
            else if (Greenfoot.mouseClicked(redo)){
                if (Processor.canRedo())
                {
                    BufferedImage b = Processor.redo();
                    image.setImage(createGreenfootImageFromBI(b));
                }
            }
            else if (Greenfoot.mouseClicked(button90)){
                BufferedImage b = Processor.rotate(image.getBufferedImage(), "90");
                image.setImage(createGreenfootImageFromBI(b));
            }
            else if (Greenfoot.mouseClicked(button180)){
                BufferedImage b = Processor.rotate(image.getBufferedImage(), "180");
                image.setImage(createGreenfootImageFromBI(b));
            }
            else if (Greenfoot.mouseClicked(adjButton))
            {
                if (currSelect != null) currSelect.close();
                if (currSelect == adjButton)
                {
                    currSelect = null;
                }
                else {
                    adjButton.open();
                    currSelect = adjButton;
                }
            }
            else if (Greenfoot.mouseClicked(expButton))
            {
                if (currSelect != null) currSelect.close();
                if (currSelect == expButton)
                {
                    currSelect = null;
                }
                else {
                    expButton.open();
                    currSelect = expButton;
                }
            }
            else if (Greenfoot.mouseClicked(open))
            {
                openFile ();
            }       
            else if (Greenfoot.mouseClicked(save))
            {
               saveFile ();
            }       
        }
        if (!Processor.canUndo()) undo.selected();
        else undo.unselected();
        
        if (!Processor.canRedo()) redo.selected();
        else redo.unselected();
    }
    
    /**
     * Allows the user to open a new image file.
     */
    private void openFile ()
    {
        // Use a JOptionPane to get file name from user
        String fileName = JOptionPane.showInputDialog("Please input a file name with extension");

        // If the file opening operation is successful, update the text in the open file button
        if (image.openFile (fileName)) {
            Processor.start(image.getBufferedImage());
            JOptionPane.showMessageDialog(null, "Successfully opened");
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Error opening. Note: Max dimensions supported is 600px by 600px.");
        }

    }
    
    /**
     * Allows the user to save the edited image file.
     * 
     * Credit for converting png to jpg: 
     * https://stackoverflow.com/questions/2290336/converting-png-into-jpeg
     */
    private void saveFile ()
    {
        // Use a JOptionPane to get file name from user
        String fileName = JOptionPane.showInputDialog("Please input a file name (no extension)");
        String extension = JOptionPane.showInputDialog("Please input an extension (png or jpg)");
        if (fileName != null && extension != null)
        {
            if (fileName.length() < 1) {
               JOptionPane.showMessageDialog(null, "File name should be greater than 0 characters!");
            }
            else if (extension.toLowerCase().equals("png")) {
                try {
                    fileName += ".png";
                    File f = new File (fileName);
                    ImageIO.write(image.getImage().getAwtImage(), "png", f); 
                    JOptionPane.showMessageDialog(null, "Saved as png");
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, "Failed to save");
                }
            }
            else if (extension.toLowerCase().equals("jpg")) {
                try {
                    fileName += ".jpg";
                    File f = new File (fileName);
                    BufferedImage currImage = image.getBufferedImage();
                    BufferedImage newImage = new BufferedImage(currImage.getWidth(), currImage.getHeight(), BufferedImage.TYPE_INT_RGB);
                    newImage.createGraphics().drawImage(currImage, 0, 0, null);
                    ImageIO.write(newImage, "jpg", f); 
                    JOptionPane.showMessageDialog(null, "Saved as jpg");
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, "Failed to save");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Invalid extension");
            }
        }
    }
    
    /**
    * Takes in a BufferedImage and returns a GreenfootImage.
    *
    * @param newBi The BufferedImage to convert.
    *
    * @return GreenfootImage A GreenfootImage built from the BufferedImage provided.
    */
    public static GreenfootImage createGreenfootImageFromBI (BufferedImage newBi)
    {
        GreenfootImage returnImage = new GreenfootImage (newBi.getWidth(), newBi.getHeight());
        BufferedImage backingImage = returnImage.getAwtImage();
        Graphics2D backingGraphics = (Graphics2D)backingImage.getGraphics();
        backingGraphics.drawImage(newBi, null, 0, 0);
        return returnImage;
    }
}

