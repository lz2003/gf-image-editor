import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Represents the redo button
 * 
 * @author Lucy Zhao
 * @version December 2020
 */
public class Redo extends Icon
{
    /**
     * Constructor of Redo
     */
    public Redo() 
    {
        setImage("redo1.png");
        this.getImage().scale(40, 40);
    }
    
    protected void selected()
    {
        setImage("redo2.png");
        this.getImage().scale(40, 40);
    }
    
    protected void unselected()
    {
      setImage("redo1.png");
      this.getImage().scale(40, 40);
    }
    
    public void close()
    {
        
    }    
}
