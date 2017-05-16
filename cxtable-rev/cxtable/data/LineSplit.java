package cxtable.data;

/*This class takes a string filled with tags.... and when queried with
the key, returns either a single string (ssplit) or a string[] (split) of
all of the strings in between those tags...

This could be one source of slow-down || inefficiency... but it hasn't
yet proven itself that way in the limited benchmarking...  

The core of the communication in the program is done with tagged strings
(line by line) and so this is a major piece of the program...
*/
  

 import java.util.Vector;
 import java.util.HashMap;
    public class LineSplit{
   
      private static final HashMap linesplits=new HashMap();
      private HashMap line_results=new HashMap();
      private static final String[] empty_set=new String[0];
      boolean debug = false;
      private String log,key,skey,ekey;
      private int offset;
   
      public LineSplit ()
      {
         key ="";
         log ="";
      }		
      public LineSplit(String key, String source)
      {
	      setKey(key);
	      setLog(source);
      }
  
      public void setKey(String k_ey)
      {key = k_ey;
         skey = new String("<"+key+">");
         ekey = new String("</"+key+">");
         offset = skey.length();
      }
   
      public boolean isWellFormed()
      {
	      return ((log.indexOf(skey) > -1) && (log.indexOf(ekey)>-1));
      }
      public boolean isWellFormed(String key, String source)
      {
	      setLog(source);
	      setKey(key);
	      return isWellFormed();
      }
      
      public boolean isWellFormedForKey(String key)
      {
	      setKey(key);
	      return isWellFormed();
      }
      
      public boolean isWellFormedForSource(String s)
      {
	      setLog(s);
	      return isWellFormed();
      }
   
      public void setLog(String l_og)
      {
         log = l_og;
      }
   
      private String[] split_()
      {  
	      
	 if (!isWellFormed()) {return empty_set;}
	 LineResults lr=getLineResults();
	 if (lr != null)
	 {
		 return lr.getResults();
	 }
	 
	 
	 String[] result;
         Vector v= new Vector();
         int point = 0;
         int ln = log.length(); 
         int counter =0;
         int s,e;   
         while(point<ln)
         { 
            s=log.indexOf(skey,point);
            if (s==-1){
               break;}
            s=s+offset;
            e=log.indexOf(ekey,s);
         
            try{add(v,s,e);} 
               catch(Exception ez){
                  System.out.println(ez.toString());}
            point = e; 
            e = 0; s = 0;
         }
      
         if (v.size() < 1){result = new String[0];
            }
	  else{
         result = new String[v.size()];
         for (int i=0; i<result.length;i++)
         {
            try{result[i] = (String)v.elementAt(i);
            }
               catch(ClassCastException cce){
                  result[i] ="";}
         }
	 }
	 lr=new LineResults(key,result);
	 line_results.put(key,result);
	 
         return result;      
      }
      public String ssplit(String k_ey, String l_og)
      {
      /*returns the first occurrance of a 'key' tag's contents*/
         setKey(k_ey);setLog(l_og);
         return single();
      }
      
      public String[] split()
      {
	      return split_();
      }
   
      public String[] split(String k_ey)
      {
         setKey(k_ey);
         return split_();
      }
   
      public String[] split(String k_ey, String l_og)
      {setLog(l_og); setKey(k_ey); 
         if (debug){System.out.println("k_ey:"+k_ey);}
         return split_();}
   
   private String single()
   {

    if (!isWellFormed()) {return "";}
    LineResults lr=getLineResults(key);
    if (lr != null)
    {
	    return lr.getFirstResult();
    }
    String it ="";
    int s_,e_;
    try{
    s_ = log.indexOf(skey,0);
    s_=s_+offset;
    e_ = log.indexOf(ekey,s_);
    it = log.substring(s_,e_);
    }
    catch(Exception e){it="";}
    lr=new LineResults(key,new String[]{it});
    line_results.put(key,lr);
    return it;
   }
   
      public void add(Vector c,int st, int en)
      {
         c.addElement(new String(log.substring(st,en)));}
	 
	 
   private LineResults getLineResults(String k)
   {
	   return ((LineResults)line_results.get(k));
   }
   
   private LineResults getLineResults()
   {
	   return getLineResults(this.key);
   }
	   
   public static LineSplit getLineSplit(String key, String source)
   {
	   String hk=key+":"+source;
	   if (linesplits.containsKey(hk))
	   {
		   return ((LineSplit)linesplits.get(hk));
	   }
	   LineSplit ls=new LineSplit(key,source);
	   linesplits.put(hk,ls);
	   return ls;
   }
   
   /*
   public static void print(String s)
   {
	   System.err.println(s);
   }
   
   public static void print (String[] s)
   {
	   for (int i=0;i<s.length;i++)
	   {
		   print("s["+i+"]:"+s[i]);
	   }
   }
   
   
   public static void main(String[] args) throws Exception
   {
	
	   for (int i=1; i<args.length;i++)
	   {
		print(getLineSplit(args[i],args[0]).split());
	   }
   }
   */
   
   
   }

   class LineResults {
	   
	   private String[] results;
	   private String key;
	   
	   LineResults(String key, String[] results)
	   {
	   this.key=key;
	   this.results=results;
	   }
	   
	   
	   public String getKey()
	   {
		   return key;
	   }
	   
	   public String[] getResults()
	   {
		   return results;
	   }
	   
	   public String getFirstResult()
	   {
		   if (results != null && results.length >=1)
		   {
			   return results[0];
		   }
		   return "";
	   }
   }
   
	   

