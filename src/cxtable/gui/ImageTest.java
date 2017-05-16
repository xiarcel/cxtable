package cxtable.gui;



/*probably needs a name change...teaching myself how to add an
image... this is used for the logo...*/
  

  import java.awt.*;
   import java.awt.event.*;


    public class ImageTest extends Panel{
   
      String path = System.getProperty("user.dir")+System.getProperty("file.separator");
      private Image img;
      private String imgname;
      Graphics g;
   
      public ImageTest(String n)
      {
         imgname=path+n;
         System.out.println("run");
      
         try{System.out.println("try{");
            img = Toolkit.getDefaultToolkit().getImage(imgname);
            System.out.println("}");
	    
		}
         
            catch(Exception e){
            
               System.out.println("Failed");}
      
      }
   
   
      public void paint(Graphics g)
      {	try{setSize(new Dimension((img.getWidth(null)+100),(img.getHeight(null)+100)));}
		catch(Exception e)
			{System.out.println("setImageSize() not supported");
			 System.err.println("non-fatal:setImageSize() not supported");
			}

     	
         g.drawImage(img,0,0,this);
	}
   
      public static void main(String[] args)
      { Frame f=new Frame("Image");
	Panel i =new ImageTest("cxtlogo.jpg");
	

         f.add(i);
	
      
         f.addWindowListener(
                            
                               new WindowAdapter(){
                               
                               
                                  public void windowClosing(WindowEvent we){
                                  
                                     System.exit(0);}});
         f.pack();
         f.setVisible(true);
      
      }
   }
