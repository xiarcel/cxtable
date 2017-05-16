/*part of the chess game plugin*/
 package cxtable.xtable_chess;
  import java.util.Vector;

    public class xQueen implements xPiece{
   
      private String control,name;
      private int x,y;
      private xBishopMoves xbms;
      private xRookMoves xrms;
      private int moves=0;
	private int playernum;
   
  public xQueen(int sd, int _x, int _y)
	{x=_x; y=_y;
	xbms = new xBishopMoves();
	xrms = new xRookMoves();
	control =new String("QN");
	name = new String("Qn-");
	if (sd == xPiece.WHITE)
		{name=name+"W";}
	if (sd == xPiece.BLACK)
		{name=name+"B";}
	if (sd == xPiece.RED)
		{name=name+"R";}
	if (sd == xPiece.GREEN)
		{name=name+"G";}
	playernum= sd;
	}

	public int getPlayerNum()
	{return playernum;}

   
      public String getName()
      {
         return name;
      }
   
      public String getControl()
      {
         return control;
      }
   
      public int getSide()
      {
         return playernum;
      }
   	public void setMoves(int i)
	{
	moves=i;
	}
   
      public void move(int x_, int y_)
      {	moves++;
         x=x_;
         y=y_;
      }
      public int moved()
      {
         return moves;}
      public int getx()
      {
         return x;}
      public int gety()
      {
         return y;}
   
      public Vector valid_spots()
      {
         Vector first = xrms.v_h(x,y);
         Vector second = xbms.diag(x,y);			
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
      {moves=moves-ii;}
   
   }
