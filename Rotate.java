import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Represents the rotation buttons
 * 
 * @author Lucy Zhao
 * @version December 2020
 */
public class Rotate extends Icon
{
    /**
     * Constructor of Rotate
     * 
     * @param rotate    what rotation (90 or 180)
     */
    public Rotate(String rotate) 
    {
        if (rotate.equals("90"))
            setImage("90r1.png");
        else
            setImage("180r1.png");
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
