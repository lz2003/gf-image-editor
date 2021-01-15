  import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Controls the filters tab (represented with a filter)
 * 
 * @author Lucy Zhao
 * @version December 2020
 */
public class Filter extends Icon
{
    private TextButton negButton;
    private TextButton blueButton;
    private TextButton redButton;
    private TextButton greenButton;
    private TextButton grayButton;
    /**
     * Constructor of Filter
     * 
     * @param image     the image to be editted
     */
    public Filter(ImageHolder image) 
    {
        this.image = image;
        setImage("filters1.png");
        this.getImage().scale(40, 40);
    }
    
    private void selected()
    {
        setImage("filters2.png");
        this.getImage().scale(40, 40);
    }
    
    private void unselected()
    {
      setImage("filters1.png");
      this.getImage().scale(40, 40);
    }
    
    /**
     * Initializes the text buttons
     */
    public void open()
    {
        blueButton = new TextButton(" [ Blue-ify ] ");
        redButton = new TextButton(" [ Red-ify ] ");
        greenButton = new TextButton(" [ Green-ify ] ");
        negButton = new TextButton(" [ Negative ] ");
        grayButton = new TextButton(" [ Grayscale ] ");
        
        getWorld().addObject (blueButton, 650 + blueButton.getWidth()/2, 200);
        getWorld().addObject (redButton, 650 + redButton.getWidth()/2, 250);
        getWorld().addObject (greenButton, 650 + greenButton.getWidth()/2, 300);
        getWorld().addObject (negButton, 650 + negButton.getWidth()/2, 350);
        getWorld().addObject (grayButton, 650 + grayButton.getWidth()/2, 400);
        selected();
        selected = true;
    }
    
    /**
     * Removes the text buttons
     */
    public void close()
    {
        getWorld().removeObject(negButton);
        getWorld().removeObject(grayButton);
        getWorld().removeObject(blueButton);
        getWorld().removeObject(redButton);
        getWorld().removeObject(greenButton);
        unselected();
        selected = false;
    }

    /**
     * Act - do whatever the Filter wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        if (selected)
        {
            if (Greenfoot.mouseClicked(blueButton)) {
                Processor.loopPixels(image.getBufferedImage(), "blueify");
            }
            else if (Greenfoot.mouseClicked(redButton)) {
                Processor.loopPixels(image.getBufferedImage(), "redify");
            }
            else if (Greenfoot.mouseClicked(greenButton)) {
                Processor.loopPixels(image.getBufferedImage(), "greenify");
            }
            else if (Greenfoot.mouseClicked(negButton)) {
                Processor.loopPixels(image.getBufferedImage(), "negative");
            }
            else if (Greenfoot.mouseClicked(grayButton)) {
                Processor.loopPixels(image.getBufferedImage(), "grayscale");
            }
        }
    }    
}
