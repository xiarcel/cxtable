/*part of chess plugin*/
 package cxtable.xtable_chess;
  import java.util.Vector;

   interface xPiece{

   static int WHITE = 0; static int GREEN = 1; 
   static int BLACK = 2; static int RED = 3;
   
	
   
	public void setMoves(int i);

      public String getName();
   
      public String getControl();
      public int moved();
      public int getSide();
   
      public void move(int x_, int y_);
   
      public Vector valid_spots();
   
      public int getx();
      public int gety();
   
      public void clr();
      public void ded(int ii);
   	
   }
