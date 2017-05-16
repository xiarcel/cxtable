/*for Chess plugin*/
package cxtable.xtable_chess;
import java.util.Vector;

 public class xGutter implements xPiece
{

public xGutter()
{}

public int getSide(){return -1;}
public int getx(){return -1;}
public int gety(){return -1;}

public void move(int i, int j)
{}
public void ded(int i){}

public void clr(){}

public Vector valid_spots()
{
return new Vector();
}

public int moved(){return 0;}
public void setMoves(int i){}

public String getName(){return "";}
public String getControl(){return "GT";}



}

