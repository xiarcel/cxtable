package cxtable.plugin;


/*this interface is mainly for plugs... it pipes write to the xClientConn...
although by simply implementing xPluginable, one is given an xOutListen
object to send through..*/
 

  public interface xOutListen{
   
      public void send(String s);
   }
