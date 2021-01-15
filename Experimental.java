import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.image.BufferedImage;
/**
 * Controls the actions of the Experimental tab (represented with a 
 * flask)
 * 
 * @author Lucy Zhao
 * @version December 2020
 */
public class Experimental extends Icon
{
    private TextButton pixButton;
    private TextButton blurButton;
    private TextButton swapButton;
    private TextButton sphereButton;
    /**
     * Constructor of Experimental
     * 
     * @param image     the image to be edited
     */
    public Experimental(ImageHolder image) 
    {
        this.image = image;
        setImage("exp1.png");
        this.getImage().scale(40, 40);
    }
    
    private void selected()
    {
        setImage("exp2.png");
        this.getImage().scale(40, 40);
    }
    
    private void unselected()
    {
      setImage("exp1.png");
      this.getImage().scale(40, 40);
    }
    
    /**
     * Initializes the text buttons
     */
    public void open()
    {
        pixButton = new TextButton("[ Pixelate ]");
        getWorld().addObject(pixButton, 650 + pixButton.getWidth()/2, 200);
        blurButton = new TextButton("[ Blur ]");
        getWorld().addObject(blurButton, 650 + blurButton.getWidth()/2, 250);
        swapButton = new TextButton("[ swapRGB ]");
        getWorld().addObject(swapButton, 650 + swapButton.getWidth()/2, 300);
        sphereButton = new TextButton("[ Spherify ]");
        getWorld().addObject(sphereButton, 650 + sphereButton.getWidth()/2, 350);
        selected();
        selected = true;
    }
    
    /**
     * Removes the text buttons
     */
    public void close()
    {
        getWorld().removeObject(pixButton);
        getWorld().removeObject(blurButton);
        getWorld().removeObject(swapButton);
        getWorld().removeObject(sphereButton);

        unselected();
        selected = false;
    }
    
    /**
     * Act - do whatever the Experimental wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        if (selected)
        {
            if (Greenfoot.mouseClicked(pixButton))
            {
                Processor.pixelate(image.getBufferedImage());
            }
            else if (Greenfoot.mouseClicked(blurButton))
            {
                Processor.gaussianBlur(image.getBufferedImage());
            }
            else if (Greenfoot.mouseClicked(swapButton))
            {
                Processor.loopPixels(image.getBufferedImage(), "swap");
            }
            else if (Greenfoot.mouseClicked(sphereButton))
            {
                Processor.spherify(image.getBufferedImage());
            }
        }
    }    
}
