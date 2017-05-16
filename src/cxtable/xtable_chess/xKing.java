/*for chess plugin*/
package cxtable.xtable_chess;
   import java.util.Vector;
	import java.awt.TextField;
    public class xKing implements xPiece{
   
      private String control,name;
      private int x,y;

      private int moves=0;
 	private int playernum;
	TextField bugnull;


      public xKing(int sd, int _x, int _y)
      { x=_x; y=_y; control=new String("KG");
	name = new String("Kg-");
	if (sd == xPiece.BLACK)
		{name=name+"B";}
	if (sd == xPiece.WHITE)
		{name=name+"W";}
	if (sd == xPiece.RED)
		{name=name+"R";}
	if (sd == xPiece.GREEN)
		{name=name+"G";}
	playernum=sd;
	moves =0;
       }

	public int getPlayerNum()
	{return playernum;}
	public void setMoves(int i)
	{
	moves=i;
	}
   
   
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
      public int moved()
      {
         return moves;}
   
      public void move(int x_, int y_)
      {
         moves++;
	/*if (moves==1) {bugnull.setText("whoops");}*/
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
      {Vector v=new Vector();
         if (moves==0)
         {
            xMove left = new xMove(x,y-2);
            xMove right = new xMove(x,y+2);
         		 //*castling*//
            if (left.on_board()==true) {v.addElement(left);}
            if (right.on_board()==true) {v.addElement(right);}
         }
         xMove[] xma = new xMove[]{new xMove((x-1),(y-1)), new xMove((x-1),y), new xMove((x-1),(y+1)), new xMove(x,(y-1)), new xMove(x,(y+1)), new xMove((x+1),(y-1)), new xMove((x+1),y), new xMove((x+1),(y+1))};
         for (int i=0; i<xma.length; i++)
         {
            if (xma[i].on_board()==true) {v.addElement(xma[i]);}
         }
         return v;
      
      }
      public void clr()
      {moves=0;}
      public void ded(int ii)
      {
         moves=moves-ii;
      }
   }
