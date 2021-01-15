import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Represents the flipping buttons
 * @author Lucy Zhao
 * @version December 2020
 */
public class Flip extends Icon
{
    /**
     * Constructor of Flip
     * 
     * @param flip    what kind of flip (horizontal or vertical)
     */
    public Flip(String flip) 
    {
        if (flip.equals("Vertical"))
            setImage("vflip1.png");
        else
            setImage("hflip1.png");
        this.getImage().scale(40, 40);
    }

    public void close()
    {
        
    }

    /**
     * Act - do whatever the Flip wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        // Add your action code here.
    }    
}
