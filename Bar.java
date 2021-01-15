import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Represents a coloured bar
 * 
 * @author Lucy Zhao
 * @version December 2020
 */
public class Bar extends Actor
{
    private GreenfootImage bar;
    /**
     * Constructor of Bar
     */
    public Bar() 
    {
        bar = new GreenfootImage(800, 40); 
        bar.setColor (new Color(233, 233, 233));
        bar.fill();
        setImage(bar);
    }
}
