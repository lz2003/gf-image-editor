import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.image.BufferedImage;
/**
 * Controls the adjustment tab (represent with the sliders)
 * 
 * @author Lucy Zhao
 * @version December 2020
 */
public class Adjust extends Icon
{
    private Slider hue;
    private TextButton hueTitle;
    
    private TextButton addCon;
    private TextButton subCon;
    
    private TextButton addLight;
    private TextButton subLight;
    
    private TextButton addWarm;
    private TextButton addCool;
    
    private float prevHue;
    
    /**
     * Constructor for the Adjust class
     * 
     * @param image     the image to be adjusted
     */
    public Adjust(ImageHolder image)
    {
        this.image = image;
        prevHue = 0;
        setImage("sliders1.png");
        this.getImage().scale(40, 40);
    }
    
    private void selected()
    {
        setImage("sliders2.png");
        this.getImage().scale(40, 40);
    }
    
    private void unselected()
    {
      setImage("sliders1.png");
      this.getImage().scale(40, 40);
    }
    
    /**
     * Initializes the text buttons and slider
     */
    public void open()
    {
        hueTitle = new TextButton("[ Hue ]");
        hue = new Slider(125, 20);
        addCon = new TextButton("[ + Contrast ]");
        subCon = new TextButton("[ - Contrast ]");
        addLight = new TextButton("[ + Brighten ]");
        subLight = new TextButton("[ - Darken ]");
        addWarm = new TextButton("[ + Warmer ]");
        addCool = new TextButton("[ - Cooler ]");
        getWorld().addObject (hue, 650 + hue.getImage().getWidth()/2, 200);
        getWorld().addObject (hueTitle, 710, 175);
        getWorld().addObject (addCon, 650 + addCon.getWidth()/2, 250);
        getWorld().addObject (subCon, 650 + subCon.getWidth()/2, 300);
        getWorld().addObject (addLight, 650 + addLight.getWidth()/2, 350);
        getWorld().addObject (subLight, 650 + subLight.getWidth()/2, 400);
        getWorld().addObject (addWarm, 650 + addWarm.getWidth()/2, 450);
        getWorld().addObject (addCool, 650 + addCool.getWidth()/2, 500);

        hue.setValue((int)(prevHue * 100));

        selected();
        selected = true;
    }
    
    /**
     * removed the text buttons and slider
     */
    public void close()
    {
        getWorld().removeObject(hue);
        getWorld().removeObject(hueTitle);
        getWorld().removeObject(addCon);
        getWorld().removeObject(subCon);
        getWorld().removeObject(addLight);
        getWorld().removeObject(subLight);
        getWorld().removeObject(addWarm);
        getWorld().removeObject(addCool);

        unselected();
        selected = false;
    }

    /**
     * Act - do whatever the Adjust wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        if (selected)
        {
            if (prevHue != hue.getFloat())
            {
                Processor.adjustHue(image.getBufferedImage(), hue.getFloat()-prevHue);
                prevHue = hue.getFloat();
            }
            if (Greenfoot.mouseClicked(addCon))
            {
                Processor.adjustFilters(image.getBufferedImage(), "contrast", true);
            }
            else if (Greenfoot.mouseClicked(subCon))
            {
                Processor.adjustFilters(image.getBufferedImage(), "contrast", false);
            }
            else if (Greenfoot.mouseClicked(addLight))
            {
                Processor.adjustFilters(image.getBufferedImage(), "light", true);
            }
            else if (Greenfoot.mouseClicked(subLight))
            {
                Processor.adjustFilters(image.getBufferedImage(), "light", false);
            }
            else if (Greenfoot.mouseClicked(addWarm))
            {
                Processor.adjustFilters(image.getBufferedImage(), "warm", true);
            }
            else if (Greenfoot.mouseClicked(addCool))
            {
                Processor.adjustFilters(image.getBufferedImage(), "warm", false);
            }
        }
    }    
}
