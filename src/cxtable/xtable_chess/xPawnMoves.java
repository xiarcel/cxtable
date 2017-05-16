package cxtable.xtable_chess;
import java.util.Vector;

 public class xPawnMoves{


public static Vector getMoves(int x, int y, int side)
{

 Vector mvs=new Vector();
 if (side==2)
    {
    if(x==1){mvs.addElement(new xMove(3,y));}
    mvs.addElement(new xMove((x+1),y));
    mvs.addElement(new xMove((x+1),(y+1)));
    mvs.addElement(new xMove((x+1),(y-1)));
    }

 if (side ==0)
    {
    if(x==(xMove.upper-1)){mvs.addElement(new xMove((x-2),y));}
    mvs.addElement(new xMove((x-1),y));
    mvs.addElement(new xMove((x-1),(y+1)));
    mvs.addElement(new xMove((x-1),(y-1)));
    }

 if (side == 1)
    {
   if (y==1){mvs.addElement(new xMove(x,3));}
   mvs.addElement(new xMove(x,(y+1)));
   mvs.addElement(new xMove((x-1),(y+1)));
   mvs.addElement(new xMove((x+1),(y+1)));
   }

 if (side == 3)
  {
  if (y==14){mvs.addElement(new xMove(x,12));}
  mvs.addElement(new xMove(x,(y-1)));
  mvs.addElement(new xMove((x-1),(y-1)));
  mvs.addElement(new xMove((x+1),(y-1)));
  }

return mvs;
}

}

