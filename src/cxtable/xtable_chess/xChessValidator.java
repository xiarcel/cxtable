/*another worker class for xChessGamex...this evaluates moves
to see if while they are on the board, are they legal.... are there pieces in
the way of the rook-sweep...  for example...*/

/*for xTournoChess, need to re-work valkastle/valking...unsure why there is
castling work in valking...*/
package cxtable.xtable_chess;

    public class xChessValidator{
   
      private xChessBox[][] xcba;
      private xBishopMoves xbms= new xBishopMoves();
      private xRookMoves xrms= new xRookMoves();
      private xKnightMoves xkms= new xKnightMoves();
	public static int size = 8;

   
   
      public xChessValidator(xChessBox[][] x_cba)
      {
         xcba = x_cba;
      
      }
   
      public xChessValidator()
      {
      //*empty constructor*//
      }
   
   public static void setSize(int s)
	{size=s;}

      public void setXBoxes(xChessBox[][] xCh)
      {
         xcba = xCh;
      }
      public boolean validate(int fx,int fy, int zx, int zy)
      {
         String s = xcba[fx][fy].getPiece().getControl();
	if (s.equals("GT")){return false;}

	String ss = xcba[zx][zy].getPiece().getControl();
	if (s.equals("GT")){return false;}
      

         if (s.equals("PN")==true)
         {
            return valpawn(fx,fy,zx,zy,xcba);}
         if (s.equals("QN")==true)
         {
            return valqueen(fx,fy,zx,zy,xcba);}
         if (s.equals("RK")==true)
         {
            return valrook(fx,fy,zx,zy,xcba);}
         if (s.equals("BI")==true)
         {
            return valbish(fx,fy,zx,zy,xcba);}
         if (s.equals("KG")==true)
         {
            /*block which follows should either have valkastle fixed...or*/
            int a = fy-zy;
            if (((fx==0)&(zx==0)) | ((fx==7)&(zx==7)))
            {
               if ((a==2)|(a==-2))
               {
                  return valkastle(fx,fy,zx,zy,xcba);
               }
            }
         
            return valking(fx,fy,zx,zy,xcba);}
      
      
         return true;
      }
   
      public boolean valpawn(int a,int b,int c,int d, xChessBox[][] xx)
      {
      int cont = xx[a][b].getPiece().getSide();
      if ((cont==0)|(cont==2))
       {  
         if (b !=d)
         {
            if (xx[c][d].getPiece().getControl().equals("EM")){
               return false;}
         }
         if (b == d)
         {
            if (xx[c][d].getPiece().getControl().equals("EM") == false) {
               return false;}
         }
       }
       else{
              if (a != c)
	{if (xx[c][d].getPiece().getControl().equals("EM")){
	 return false;}
	}
              if (a == c)
                 {
                  if (xx[c][d].getPiece().getControl().equals("EM")==false){
	return false;}
                 }
              }  
       return true;
      
   }
   
      public boolean valqueen(int a,int b, int c, int d, xChessBox[][] xx)
      {
         if ((b == d) | (a ==c))
         {
            return valrook(a,b,c,d,xx);}
         if ((b !=d ) & (a !=c))
         {System.out.println("Queen evaluated as bishop");
            return valbish(a,b,c,d,xx);}
         return true;	
      }
   
      public boolean valrook(int a,int b, int c, int d, xChessBox[][] xx)
      {
         if (a==c)
         {
            if (b<d)
            {
               for (int x=b+1; x<d; x++)
               {
                  if (xx[a][x].getPiece().getControl().equals("EM")==false)
                  {
                     return false;}
               }
               return true;
            }
            if (d<b)
            {
               for (int x=b-1; x>d; x--)
               {
                  if (xx[a][x].getPiece().getControl().equals("EM")==false)
                  {
                     return false;}
               }
               return true;
            }
         }
      
         if (b==d)
         {
            if (a<c)
            {
               for (int x=a+1; x<c; x++)
               {
                  if (xx[x][b].getPiece().getControl().equals("EM")==false)
                  {
                     return false;}
               }
               return true;
            }
            if (a>c)
            {
               for (int x=a-1; x>c; x--)
               {
                  if (xx[x][b].getPiece().getControl().equals("EM")==false)
                  {
                     return false;}
               }
               return true;
            }
         }
         return true;
      }
   
      public boolean valbish(int a,int b, int c, int d, xChessBox[][] xx)
      {
      
      
         if ((a>c) & (b>d))
         {int i=1;
            while ((a-i)!=c)
            {
               if (xx[(a-i)][(b-i)].getPiece().getControl().equals("EM")==false)
               {
                  return false;}
               i++;
            }
            return true;}
      
         if ((a>c) & (b<d))
         {int i=1;
            while ((a-i)!=c)
            {
               if (xx[(a-i)][(b+i)].getPiece().getControl().equals("EM")==false)
               {
                  return false;}
               i++;
            }
            return true;
         }
      
         if ((a<c) & (b>d))
         {int i=1;
            while ((a+i)!=c)
            {
               if (xx[(a+i)][(b-i)].getPiece().getControl().equals("EM")==false)
               {
                  return false;}
               i++;
            }
            return true;
         }
      
         if ((a<c) & (b<d))
         {int i=1;
            while ((a+i)!=c)
            {
               if (xx[(a+i)][(b+i)].getPiece().getControl().equals("EM")==false)
               {
                  return false;}
               i++;
            }
            return true;
         }
      
         return true;
      }
   
      public boolean valking(int a,int b, int c, int d, xChessBox[][] xx)
      {
      //*main purpose is to check for "Castling"*//
      
         if ((a==7) & (c==7))
         { 
            if ((b-d)==2 | (b-d) == -2)
            {
               if (b>d)
               {
                  for (int x=b-1; x>0; x--)
                  {
                     if (xx[a][x].getPiece().getControl().equals("EM")==false){
                        return false;}
                  }
                  if (xx[a][0].getPiece().moved()>0)
                  {
                     return false;}
                  xPiece rk = xx[a][0].getPiece();
                  xx[a][0].occupy(new xEmptyPiece());
                  rk.move(a,(d+1));
                  xx[a][(d+1)].occupy(rk);
                  return true;}
               if (b<d)
               {
                  for (int x=b+1; x<7; x++)
                  {
                     if (xx[a][x].getPiece().getControl().equals("EM")==false){
                        return false;}
                  }
                  if (xx[a][7].getPiece().moved()>0){
                     return false;}
                  xPiece rk = xx[a][7].getPiece();
                  xx[a][7].occupy(new xEmptyPiece());
                  rk.move(a,(d-1));
                  xx[a][(d-1)].occupy(rk);
                  return true;
               }
            }
         }
      
         if((a==0)&(c==0))
         {	
            if (((b-d)==2)| ((b-d)==-2))
            {
               if (b>d)
               {
                  for (int x=b-1; x>0; x--)
                  {
                     if (xx[a][x].getPiece().getControl().equals("EM")==false){
                        return false;}
                  }
                  if (xx[a][0].getPiece().moved()>0)
                  {
                     return false;}
                  xPiece rk = xx[a][0].getPiece();
                  xx[a][0].occupy(new xEmptyPiece());
                  rk.move(a,(d+1));
                  xx[a][(d+1)].occupy(rk);
                  return true;}
               if (b<d)
               {
                  for (int x=b+1; x<7; x++)
                  {
                     if (xx[a][x].getPiece().getControl().equals("EM")==false){
                        return false;}
                  }
                  if (xx[a][7].getPiece().moved()>0){
                     return false;}
                  xPiece rk = xx[a][7].getPiece();
                  xx[a][7].occupy(new xEmptyPiece());
                  rk.move(a,(d-1));
                  xx[a][(d-1)].occupy(rk);
                  return true;
               }
            }
         }
         return true;
      
      }
      public boolean valkastle(int a, int b, int c, int d, xChessBox[][] xx)
      {System.out.println("Valkastle");
         if (xx[a][b].getPiece().moved()==0)
         {
         /*evaluate castle*/
            if (d>b)
            {
               xPiece rk = xx[a][7].getPiece();
	int rkspot=rk.gety();
               if (rk.moved() != 0) {
                  return false;}
               for (int i=6; i>b; i--)
               {
                  if (xx[a][i].getPiece().getControl().equals("EM")==false)
                  {
                     return false;}
               }
               rk.move(a,(rkspot-2));
	xx[a][(rkspot-2)].occupy(rk);
               xx[a][(rkspot)].occupy(new xEmptyPiece());
               return true;
            }
         
            if (d<b)
            {
               xPiece rk = xx[a][0].getPiece();
	int rkspot=rk.gety();
               if (rk.moved() != 0) {
                  return false;}
               for (int i=1; i<b; i++)
               {
                  if (xx[a][i].getPiece().getControl().equals("EM")==false)
                  {
                     return false;}
               }
	rk.move(a,(rkspot+3));
	xx[a][(rkspot+3)].occupy(rk);
	xx[a][rkspot].occupy(new xEmptyPiece());
               return true;			
            }
         }
         return false;
      }
   
   }
