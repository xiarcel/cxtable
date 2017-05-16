/*part of the chess game plugin*/
package cxtable.xtable_chess;
   import java.util.Vector;

    public class xRook implements xPiece{
   
      private String control,name;
      private int x,y;
      private xRookMoves xrms;
      private int moves=0;
	private int playernum=-1;
  

      public xRook (int sd, int _x, int _y)
	{
	xrms = new xRookMoves();
	x=_x; y=_y; playernum=sd;
	control=new String("RK");
	name = new String("Rk-");
	if (sd == xPiece.WHITE)
		{name=name+"W";}
	if (sd == xPiece.RED)
		{name=name+"R";}
	if (sd == xPiece.BLACK)
		{name=name+"B";}
	if (sd == xPiece.GREEN)
		{name=name+"G";}
	}

   
      public String getName()
      {
         return name;}      
   
      public int moved()
      {
         return moves;}
   
   	public void setMoves(int i)
	{
	moves=i;
	}
   
      public String getControl()
      {
         return control;}     
   
      public int getSide()
      {
         return playernum;}
   
      public void move(int x_, int y_)
      {
         moves++;
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
      {
         return xrms.v_h(x,y);
      }
      public void clr()
      {moves=0;}
      public void ded(int ii)
      {moves=moves-ii;}
   
   
   }
