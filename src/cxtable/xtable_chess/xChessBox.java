/*this class is the individual chess board location (white | black)...
it also holds references to what piece it currently has on it*/
 
package cxtable.xtable_chess;

 import java.awt.*;
   import java.awt.event.*;

    public class xChessBox extends Panel{
   
      private Panel p;
      private Color background;
      private Label label = new Label("");
      private String occupied;
      private xPiece xp;
      private boolean back_co;
      private int _x,_y;
      private xChessMoveListener xcml = null;
      public xChessBox(boolean bo)
      {	back_co = bo;
         p = new Panel();
         if (bo == true)
         {background = new Color(192, 192, 192);
         }
         else {background = Color.white;}
      }
   
      public xChessBox(boolean bo, int x, int y, xChessMoveListener xcm)
      {	
         back_co = bo;
         p = new Panel();
         if (bo == true)
         {background = new Color(192,192,192);
         }
         else {background = Color.white;}
         xcml=xcm;
      
         _x=x; _y=y;
      }
   
      public void occupy (String who)
      {
         occupied=new String(""+who);
         label.setText(" "+who);
      }
   
      public void occupy (xPiece x_p)
      {

	 if (xp !=null)
		{
		if (xp.getControl().equals("GT"))
			{return;}
		}
		
         xp=x_p;
         occupied = new String(""+xp.getName());
        if (xp.getControl().equals("GT"))
		{background = Color.black;
		 occupied = "";
		}

	   label.setText(" "+occupied);
      }
   
      public xPiece getPiece()
      {
         return xp;}
   
      public String getpiece()
      {
         return occupied;
      }
   
      public Panel create()
      {p.setBackground(background);
         p.setLayout(new BorderLayout());
         p.add(label,BorderLayout.CENTER);
         p.setSize(new Dimension(20,20));
         p.setVisible(true);
         /*
	if (xcml !=null) {
            System.err.println("Added MouseListener at "+_x+","+_y);
            p.addMouseListener(this);
      		addMouseListener(this);
      		}
      	*/
	
         return p;
      }
      public boolean bckgrnd()
      {
         return back_co;}
   
      public void mouseClicked(MouseEvent me)
      {
      System.err.println("mouseClicked:"+_x+","+_y);
         xcml.select_piece(_x,_y);
      
      }
   
      public void mousePressed(MouseEvent me)
      {
      System.err.println("mousePressed:"+_x+","+_y);
      
      }
   
      public void mouseReleased(MouseEvent me)
      {
      System.err.println("mouseReleased:"+_x+","+_y);
      }
   
      public void mouseEntered(MouseEvent me)
      {
      }
   
      public void mouseExited(MouseEvent me)
      {
      }
   
   
   }
