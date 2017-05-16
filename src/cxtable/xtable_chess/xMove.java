/*part of the chess plugin*/
package cxtable.xtable_chess;
    public class xMove{
   
      private int x;
      private int y;
      private boolean valid = true;
	public static int upper=7;
   
      public xMove(int _x, int _y)
      {
         x=_x; y=_y;
         if ((x<0)|(x>upper)|(y<0)|(y>upper))
         {valid = false;}
      }
   
      public boolean on_board()
      {
         return valid;
      }
   
public static void setUpper(int i)
{upper=i;}

   public int getx()
   {return x;}
   public int gety()
   {return y;}
   
   
   }
