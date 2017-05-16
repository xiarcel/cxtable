package cxtable.xtable_chess;
/*for chess plugin*/
  
 import java.util.Vector;

    public class xKnight implements xPiece{
   
      private String name,control;
      private int x,y;
      private int moves=0;
	private int playernum;
   
    
      public xKnight(int sd, int _x, int _y)
	{x=_x; y=_y;
	control = new String("KN");
	name = new String("Kn-");
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

	public int getPlayerNum()
	{return playernum;}
	public void setMoves(int i)
	{
	moves=i;
	}
   
   
      public int moved()
      {
         return moves;}
   
      public String getName()
      {
         return name;}
   
      public String getControl()
      {
         return control;}
   
      public int getSide()
      {
         return playernum;
      }
   
      public void move(int x_, int y_)
      {
         x=x_;
         y=y_;
         moves++;
      //* *//
      }
   
      public int getx()
      {
         return x;}
      public int gety()
      {
         return y;}
   
      public Vector valid_spots()
      {
         Vector v=new Vector();
         xMove[] xms = new xMove[]{new xMove((x-2),(y-1)), new xMove((x-1),(y-2)), new xMove((x+2),(y-1)), new xMove((x+1),(y-2)), new xMove((x+1),(y+2)), new xMove((x+2),(y+1)), new xMove((x-1),(y+2)), new xMove((x-2),(y+1))};
      
         for (int i=0; i<xms.length; i++)
         {	
	if(xChessGamex.debug==true){System.out.println("Knight, x:"+xms[i].getx()+" y:"+xms[i].gety());}
            if (xms[i].on_board() == true) {v.addElement(xms[i]);}
         }
         return v;
      
      }
      public void clr()
      {moves=0;}
      public void ded(int ii)
      {moves=moves-ii;
      }
   
   }
