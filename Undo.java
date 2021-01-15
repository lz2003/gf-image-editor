import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Represents the undo button
 * 
 * @author Lucy Zhao
 * @version December 2020
 */
public class Undo extends Icon
{
    /**
     * Constructor of Undo
     */
    public Undo() 
    {
        setImage("undo1.png");
        this.getImage().scale(40, 40);
    }
    
    protected void selected()
    {
        setImage("undo2.png");
        this.getImage().scale(40, 40);
    }
    
    protected void unselected()
    {
      setImage("undo1.png");
      this.getImage().scale(40, 40);
    }
    
    public void close()
    {
        
    }
    
    /**
     * Act - do whatever the Filter wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        // Add your action code here.
    }       
}
