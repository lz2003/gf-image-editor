import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Represents the saving and opening of images
 * 
 * @author Lucy Zhao
 * @version December 2020
 */
public class OpenSave extends Icon
{
    /**
     * Constructor for OpenSave
     * 
     * @param type  whether it is a save or open button
     */
    public OpenSave(String type) 
    {
        if (type.equals("save"))
        {
            setImage("save1.png");
        }
        else
        {
            setImage("open1.png");
        }
        this.getImage().scale(40, 40);
    }
    
    public void close()
    {
        
    }    
}
