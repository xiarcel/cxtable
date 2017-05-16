package cxtable;
/*this is a timer class..it cues the go() method either
recurrently, or once...depending on how it is set...after it sleeps 
for a set period*/

    public class xTimer extends Thread{
   
      private xTimed xt;
      private long slp;
      private boolean recurring;
   
      public xTimer (long l, xTimed x, boolean recur)
      {
         recurring = recur;
         xt=x; slp = l;
      }
   
      public void run()
      {
         do{
            try{Thread.sleep(slp);}
               catch(Exception e){
               }
            xt.go();
         }
         while(recurring);
      }
   
      public void kill()
      {
         recurring = false;
      }
   
   /*end*/
   }

