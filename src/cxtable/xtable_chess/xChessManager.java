/* this class handles copies of the chessboard for eval...as well as
xxxmate checking... a worker class for xChessGamex*/

package cxtable.xtable_chess;
   import java.util.Vector;

    public class xChessManager{
      private xKnightMoves xkn;
      private xRookMoves xrm;
      private xBishopMoves xbm;
      private xChessValidator xcv;
      static int upper=8;
   
      public xChessManager()
      {
         xkn = new xKnightMoves();
         xrm = new xRookMoves();
         xbm = new xBishopMoves();
         xcv = new xChessValidator();
      }
   
      public static void setUpper(int i){upper=i;}

      public xChessBox[][] mutable(xChessBox[][] xx)
      {
         xChessBox[][] copy = new xChessBox[upper][upper];
         for (int i=0; i<upper; i++)
         {
            for (int j=0; j<upper; j++)
            {
               copy[i][j] = new xChessBox(xx[i][j].bckgrnd());
               xPiece temp = xx[i][j].getPiece();
               int sid = temp.getSide();
               String who = temp.getControl();
               if(who.equals("GT"))
	{copy[i][j].occupy(new xGutter());}
               if (who.equals("EM")==true)
               {copy[i][j].occupy(new xEmptyPiece());
                  continue;}
               if (who.equals("PN")==true)
               {copy[i][j].occupy(new xPawn(sid,i,j));
                  continue;}
               if (who.equals("KN")==true)
               {copy[i][j].occupy(new xKnight(sid,i,j));
                  continue;}
               if (who.equals("BI")==true)
               {copy[i][j].occupy(new xBishop(sid,i,j));
                  continue;}
               if (who.equals("RK")==true)
               {copy[i][j].occupy(new xRook(sid,i,j));
                  continue;}
               if (who.equals("QN")==true)
               {copy[i][j].occupy(new xQueen(sid,i,j));
                  continue;}
               if (who.equals("KG")==true)
               {copy[i][j].occupy(new xKing(sid,i,j));
                  continue;}
               else {copy[i][j].occupy(new xEmptyPiece());}
            }
         }
         return copy;
      //*this is the end of the method*//
      }
   
     
      public Vector legal_moves(int a, int b)
      {
         Vector legs = new Vector();
         Vector asrook=xrm.v_h(a,b);
         Vector asbish=xbm.diag(a,b);
         Vector asknight=xkn.lmove(a,b);
         legs.addElement(asrook); legs.addElement(asbish); legs.addElement(asknight);
         return legs;
      }
   
      public boolean check(int a,int b, xChessBox[][] xx, int sid)
      
      { 
         if ((a<0)|(b<0))
         {System.out.println("Not a spot on the board, no-check!");
            return false;}

      
      /*pass a,b as the king on "side" for int sid.. Pass in the muted xChessBox[][]
      with move already completed... Check for the 'Check' condition by
      running this 'a,b' through the legal_moves generator... The vector returned from
      legal_moves contains a set of Rook moves in the Vector at 0, Bishop moves in the Vector at 1;
      Knight moves in the Vector at 2; */
      
         if (xx[a][b].getPiece().getControl().equals("KG")==false)
         {/*not a king*/ 
            System.out.println("Only kings can be in check!");
            return false;
         /*let's not waste time we don't have to!!*/
         }
         Vector total = legal_moves(a,b);
         if (total.size() !=  3)
         {System.out.println("Invalid legal_move check!");
            return false;
         }
         Vector rooks = (Vector)total.elementAt(0);
         Vector bishes = (Vector)total.elementAt(1);
         Vector knighted = (Vector)total.elementAt(2);
      
      /*Start with "Knight".. this could only contain at MOST '8' legal moves, the shortest, 
      and does not require a cumulative 'path' check, only that a knight of the opposite 
      side is there*/
      
         while(!knighted.isEmpty())
         {
            xMove thisone = (xMove)knighted.elementAt(0);
            int x= thisone.getx(); int y=thisone.gety();
            xPiece who_there = xx[x][y].getPiece();
            if (who_there.getSide() == sid)
            {knighted.removeElementAt(0);
               continue;}
            if (who_there.getControl().equals("KN") == true)
            {System.out.println("Check By Knight");
               return true;}
            knighted.removeElementAt(0);
         }
         int ap = a+1; int an=a-1;
         int bp = b+1; int bn=b-1;
         xPiece[] pns =new xPiece[4];
         try{pns[0]= xx[an][bn].getPiece();
         }
            catch(Exception e) {
               pns[0] = new xEmptyPiece();}
         try{pns[1] = xx[an][bp].getPiece();
         }
            catch(Exception e) {
               pns[1] = new xEmptyPiece();}
         try{pns[2] = xx[ap][bn].getPiece();
         }
            catch(Exception e) {
               pns[2] = new xEmptyPiece();}
         try{pns[3] = xx[ap][bp].getPiece();
         }
            catch(Exception e) {
              pns[3] = new xEmptyPiece();}
      
      /*the four xPieces above are in case we have an opposite pawn on diag*/
       
         for (int i=0; i<pns.length; i++)
         {
            if (pns[i].getSide() != sid)
            {
               if (pns[i].getControl().equals("PN"))
               {
                  System.out.println("Check by pawn");
                  return true;}
            }
         }
      /*here is where we validate as bishop, to see if a 'valid' move exists along
      bishop lines to a piece that is either a Queen or Bishop*/
         while (!bishes.isEmpty())
         {
            xMove thischeck = (xMove)bishes.elementAt(0);
            int x= thischeck.getx(); int y=thischeck.gety();
            xPiece thispiece = xx[x][y].getPiece();
            if (thispiece.getSide() == sid)
            {bishes.removeElementAt(0);
               continue;
            }
            String s = thispiece.getControl();
            if (s.equals("QN") | s.equals("BI") )
            {System.out.print("Eval'ing a QN or BI at "+x+","+y+"::");
               if (xcv.valbish(a,b,x,y,xx) == true)
               {System.out.println("In Check");
                  return true;}
               System.out.println("No Check");
            }
            bishes.removeElementAt(0);
         }
      
      /*OK, and here is the check for a QN or RK at for RookMoves*/
      
         while (!rooks.isEmpty())
         {
            xMove thischeck = (xMove)rooks.elementAt(0);
            int x= thischeck.getx(); int y= thischeck.gety();
            xPiece thispiece = xx[x][y].getPiece();
            int boop = thispiece.getSide();
            String con = thispiece.getControl();
            if (boop == sid) {rooks.removeElementAt(0);
               continue;}
            if ((con.equals("QN"))| (con.equals("RK")))
            {
               System.out.print("Eval'ing a RK or QN at "+x+","+y+"::");
               if (xcv.valrook(a,b,x,y,xx) == true)
               {System.out.println("In Check");
                  return true;
               }
               System.out.println("Not in check");
            }
            rooks.removeElementAt(0);
         /*end of while() loop*/
         
         }
      /*OK... if the Knight, {mini-pawn}, Bishop and Rook Checks do not
      cause a return of true by this point, there is nothing near this King that 
      puts him in check...*/
      
         return false;
      }
   
      public boolean xxxmate(xChessBox[][] xcbx, int qside)
      {

         Vector v = new Vector();
         for (int i=0; i<upper; i++)
         {
            for (int j=0; j<upper; j++)
            {
               xPiece xp = xcbx[i][j].getPiece();
               if (xp.getSide() == qside)
               {v.addElement(xp);}
            }
         }
         while (!v.isEmpty())
         {
            xPiece valida = (xPiece)v.elementAt(0);
            if (any_good(valida,xcbx,qside) == true)
            {
               return false;}
            v.removeElementAt(0);
         }
         return true;
      }
   
      public boolean any_good(xPiece xpi,xChessBox[][] xcx, int b)
      {
         Vector mvs = xpi.valid_spots();
         int fx = xpi.getx();
         int fy = xpi.gety();
         int tx,ty;
      
         while (!mvs.isEmpty())
         {
            xMove xm = (xMove)mvs.elementAt(0);
            tx = xm.getx(); ty=xm.gety();
         
         
            xcv.setXBoxes(xcx);
            if (xcv.validate(fx,fy,tx,ty) == false)
            {mvs.removeElementAt(0); 
               continue;}
         	/*changed*/

         	if (xcx[tx][ty].getPiece().getSide()==b)
         		{if (xcx[tx][ty].getPiece().getControl().equals("EM")==false)
         			{mvs.removeElementAt(0);continue;}
         		}
         xChessBox[][] tmove = mutable(xcx);
            xPiece itt = tmove[fx][fy].getPiece();
            tmove[fx][fy].occupy(new xEmptyPiece());
            tmove[tx][ty].occupy(itt);
            itt.move(tx,ty);
         	/*reflects copy*/
         
            xKingLocation[] xkl = xKingLocation.findKings(tmove);
            int m=-1; int n=-1;
            for (int q=0; q<xkl.length;q++)
            {if (xkl[q].getSide() == b)
	{m=xkl[q].getx(); n=xkl[q].gety();break;}
            }
 	if ((n==-1)|(m==-1))
		{System.out.println("No king found to match!");return true;}
          
            if (check(m,n,tmove,b)==false)
            {	System.out.println("Move at "+tx+","+ty+":No Check!");
               return true;}
            mvs.removeElementAt(0);
         }
      
      
         return false;}
   
   
   
   //*end*//
   }

