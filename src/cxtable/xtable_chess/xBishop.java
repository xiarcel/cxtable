/*for xChessgamex plugin*/
package cxtable.xtable_chess;

   import java.util.Vector;

    public class xBishop implements xPiece{
   
      private String control,name;
      private int x,y;
      private xBishopMoves xbms;
      private int moves=0;
	private int playernum;
   
  
      public xBishop(int sd, int _x, int _y)
	{
	xbms=new xBishopMoves();
	x=_x; y=_y;
	control = new String("BI");
	name = new String("Bi-");
	if (sd == xPiece.WHITE)
		{name=name+"W";}
	if (sd == xPiece.BLACK)
		{name=name+"B";}
	if (sd == xPiece.RED)
		{name=name+"R";}
	if (sd == xPiece.GREEN)
		{name=name+"G";}
	playernum=sd;
	}

	public void setMoves(int i)
	{
	moves=i;
	}
   

      public String getName()
      {
         return name;
      }

  	public int getPlayerNum()
	{return playernum;}
   
      public String getControl()
      {
         return control;
      }
   
      public int getSide()
      {
         return playernum;
      }
      public int moved()
      {
         return moves;}
   
      public void move(int x_, int y_)
      {	moves++;
         x=x_;
         y=y_;
      }
      public int getx()
      {
         return x;}
      public int gety()
      {
         return y;}
   
      public Vector valid_spots()
      {	Vector v= xbms.diag(x,y);
         for (int i=0; i<v.size(); i++)
         {xMove temp = (xMove)v.elementAt(i);
            if(xChessGamex.debug==true){System.out.print("x:"+temp.getx()+", ");
            System.out.println("y:"+temp.gety());}
         }
         return v;
      }
      public void clr()
      {moves=0;}
   	public void ded(int ii)
   	{moves=moves-ii;}
   
   }
