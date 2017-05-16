package cxtable.core_comm;


/*this class is the data type to be stored for future reference when 
connecting...ideally..when xClientContinual receives a list of new 
connects , it makes a bunch of these...checks them against the ones
stored...so that it does not try to make a duplicate connection while one
is currently being created... We ran into some race situations when there
was transient net hang-time...this has pretty much solved them*/


  public class xConnect implements xRemovable{
   
      private String inet,servname,servreg,name,regid;
      private int port; private String my_ip,my_port;

      private xRemoveListen xrl;
   
   
      public xConnect(String i, String s, String sr, String n, String ri, int pt, String mi, String mp)
      {
         inet=i; servname=s; servreg=sr; name=n; regid=ri;
         port = pt; my_ip=mi; my_port=mp;
      }
   
      public String get_IP(){
         return inet;}
      public String get_ServName(){
         return servname;}
      public String get_ServReg(){
         return servreg;}
      public String get_Name(){
         return name;}
      public String get_RegID(){
         return regid;}
      public int get_Port(){
         return port;}

	public String get_my_Port()
	{return my_port;}
	public String get_my_IP()
	{return my_ip;}

      public void setRemoveListen(xRemoveListen x)
      {xrl=x;}
   
      public void kill()
      {
         if (xrl !=null){xrl.remove(this);
            return;}
         System.out.println("Kill called on a xConnect with a null xRemoveListen");
      }

	public void kill(xClientConn x)
	{
	if (xrl !=null){
		try{xConnectPool xcp = (xConnectPool)xrl;
		    xcp.remove(this,x);
		    return;
		    }
		    catch(Exception e){System.out.println("Weird remove called on an xConnect w/ no xConnectPool");
			      }
		xrl.remove(this);return;
		}
	System.out.println("Kill called on a xConnect w/ a null xRemoveListen");
	}
   
   
      public static boolean are_equal(xConnect one, xConnect two)
      {
      /*check, is this the SAME object?*/
         if (one==two){
            return true;}
      /*this is likely the best order, match IP and Port first, if they don't match
      the rest won't either, and we'll save the time checking it*/
         if (one.get_IP().equals(two.get_IP())==false){
            return false;}
         if (one.get_Port() != two.get_Port()){
            return false;}
         if (one.get_ServName().equals(two.get_ServName())==false){
            return false;}
         if (one.get_ServReg().equals(two.get_ServReg())==false){
            return false;}
         if (one.get_Name().equals(two.get_Name())==false){
            return false;}
         if (one.get_RegID().equals(two.get_RegID())==false){
            return false;}
      /*exhausted all comparisons, all true:*/
         return true;
      }
   
   /*done*/
   }
