/*part of chess plugin*/
  
package cxtable.xtable_chess;
 import java.util.Vector;

    public class xPawn implements xPiece{
   
      private String control;
   
      private String name = new String("Pn-");
      private int x,y;
      private int moves =0;
      private boolean promoted = false;
      private xRookMoves xrm;
      private xBishopMoves xbm;
	private int playernum=-1;
   
	public xPawn(int sd, int _x, int _y)
 	{
	x=_x; y=_y;
	control = new String("PN");
	if (sd == xPiece.BLACK)
		{name=name+"B";}
	if (sd == xPiece.WHITE)
		{name=name+"W";}
	if (sd == xPiece.RED)
		{name=name+"R";}
	if (sd == xPiece.GREEN)
		{name=name+"G";}
	playernum=sd;

	}
	

      public int moved()
      {
         return moves;}
   
      public int getSide()
      {
         return playernum;
      }
   
      public String getControl()
      {
         return control;
      }
   	public void setMoves(int i)
	{
	moves=i;
	}
   
      public String getName()
      {
         return name;
      }
   
      public void move(int _xx, int _yy)
      {
         x= _xx;
         y= _yy;
         if (((playernum==0)&(x==0)) | ((playernum==2)&(x==7)))
         {control = new String("QN");
            name = new String("Qn-");
            if (playernum==0){name=name+"W";}
            else {name = name+"B";}
            xrm = new xRookMoves();
            xbm = new xBishopMoves();
            promoted=true;}
      
         moves++;
      }

     public Vector valid_spots()
     {
     if (promoted==true){return valid_promote();}
     Vector to_ret=new Vector();
      Vector to_sort=xPawnMoves.getMoves(x,y,playernum);
      for (int i=0; i<to_sort.size(); i++)
	{xMove m = (xMove)to_sort.elementAt(i);
                    if (m.on_board()==true){to_ret.addElement(m);}
	}
     return to_ret;
     }

      public int getx()
      {
         return x;}
      public int gety()
      {
         return y;}
   
      public Vector valid_promote()
      {
      
         Vector first = xrm.v_h(x,y);
         Vector second = xbm.diag(x,y);			
         Vector third = new Vector();
      
         for (int i=0; i<first.size(); i++)
         {xMove temp = (xMove)first.elementAt(i);
            third.addElement(temp);
         }
         for (int j=0; j<second.size(); j++)
         {xMove tmp = (xMove)second.elementAt(j);
            third.addElement(tmp);
         }
         first.removeAllElements();
         second.removeAllElements();
         return third;
      
      
      }
      public void clr()
      {moves=0;}
      public void ded(int ii)
      {
         moves=moves-ii;
      }
   
   
   }
