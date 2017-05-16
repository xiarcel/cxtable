/*for chess plugin*/
package cxtable.xtable_chess;
   import java.util.Vector;

    public class xEmptyPiece implements xPiece{
   
      private int x,y;
   
      public xEmptyPiece()
      {x=0; y=0;}
   
   
      public String getControl()
      {
         return "EM";}
   
      public int moved()
      {
         return 0;}
	public void setMoves(int i)
	{
	
	}
   
   
      public int getSide()
      {
         return -1;
	}
   
      public String getName()
      {
         return "";}
   
      public void move(int x_, int y_)
      {
         x=x_;y=y_;}   
      public int getx()
      {
         return x;}
      public int gety()
      {
         return y;}
   
      public Vector valid_spots()
      {
         return new Vector();}
      public void clr()
      
      {}
      public void ded(int ii)
      {}
   
   
   }
