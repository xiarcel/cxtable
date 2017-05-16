/*another worker class for chess...translates the moves to spots
in the array...*/
package cxtable.xtable_chess;
    public class xChessMove{
   
      public xChessMove()
      {}
   
      public int process(char c)
      {
         if (c=='a'| c=='A') {
            return 0;}
         if (c=='b'| c =='B') {
            return 1;}
         if (c=='c' | c== 'C') {
            return 2;}
         if (c=='d' | c=='D') {
            return 3;}
         if (c=='e' | c=='E') {
            return 4;}
         if (c=='f' | c=='F') {
            return 5;}
         if (c=='g' | c=='G') {
            return 6;}
         if (c=='h' | c=='H') {
            return 7;}
	if (xMove.upper == 15){return proc_tourno(c);}

         return -1;
      }

	private int proc_tourno(char x)
	{
	if (x =='i' | x=='I')
		{return 8;}
	if (x =='j' | x=='J')
		{return 9;}
	if (x == 'k' | x=='K')
		{return 10;}
	if (x == 'l' | x=='L')
		{return 11;}
	if (x == 'm' | x == 'M')
		{return 12;}
	if (x == 'n' | x == 'N')
		{return 13;}
	if (x == 'o' | x == 'O')
		{return 14;}
	if (x == 'p' | x== 'P')
		{return 15;}
	return -1;
	}


   
      public char proc(int i)
      {
         if (i == 0) {
            return 'A';}
         if (i == 1) {
            return 'B';}
         if (i == 2) {
            return 'C';}
         if (i == 3) {
            return 'D';}
         if (i == 4) {
            return 'E';}
         if (i == 5) {
            return 'F';}
         if (i == 6) {
            return 'G';}
         if (i == 7) {
            return 'H';}
         return ' ';
      
      }
   
   
   }
