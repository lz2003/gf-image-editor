import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Represents the icons of photo editor
 * 
 * @author Lucy Zhao 
 * @version December 2020
 */
public abstract class Icon extends Actor
{
    protected ImageHolder image;
    protected boolean selected = false;
   
    /**
     * Closes the side bar 
     */
    public abstract void close(); 
}
