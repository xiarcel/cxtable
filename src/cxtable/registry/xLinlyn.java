package cxtable.registry;

/*
this class is a modified version of "LInlyn" by Peter van der Linden and
Steve Lynch...  licensed under the KDE Artistic License
This FTP transfers files (writes the Registry's location to its specified
online file...)
There are (and might be) various modifications to this code.. they are
considered as "linked" to the application at runtime the same way that all
Java code is linked at runtime... 

Out of respect for the authors of this class...it, and all *Linlyn classes are
considered as released under the KDE artistic license... the remainder of
the program is released under the GNU GPL

*/



   import java.io.*;       
   import java.net.*;  
   import java.lang.Object;  
   import java.util.*;  

    public class xLinlyn {
   
      private boolean DEBUG = true;
      private String server,user,pass,dir,file,what;
   
      public xLinlyn (String s_v, String u_s, String p_s, String d_i)
      {server=s_v; user=u_s; pass=p_s; dir=d_i;}
   
   
      public void conn_post(String fil, String wha) throws IOException
      {   file = fil; what = wha;
         ftpConnect(server);
         ftpLogin(user, pass);
         ftpSetDir(dir);
         ftpSetTransferType(false);
         dsock = ftpGetDataSock();
         OutputStream os = dsock.getOutputStream();
         DataOutputStream dos = new DataOutputStream(os);
         ftpSendCmd("APPE "+file); 
         dos.writeBytes(what);
         dos.flush();
         dos.close();
         ftpLogout();
      } 
   
      public void replace (String fil, String wha) throws IOException
      {
         file=fil;what=wha;
         ftpConnect(server);
         ftpLogin(user, pass);
         ftpSetDir(dir);
         ftpSetTransferType(false);
         dsock = ftpGetDataSock();
         OutputStream os = dsock.getOutputStream();
         DataOutputStream dos = new DataOutputStream(os);
         ftpSendCmd("DELE "+file);
         ftpSendCmd("STOR "+file);
         dos.writeBytes(what);    
         dos.flush();
         dos.close();
         ftpLogout();
      }
      public void erase(String fil) throws IOException{
         ftpConnect(server);
         ftpLogin(user, pass);
         ftpSetDir(dir);
         ftpSetTransferType(false);
         dsock = ftpGetDataSock();
         OutputStream os = dsock.getOutputStream();
         DataOutputStream dos = new DataOutputStream(os);
         ftpSendCmd("DELE "+fil);
         ftpLogout();
      }
   
      public void setDeb(boolean boo)
      {DEBUG=boo;}
      private String getAsString(InputStream is) {
      
         int c=0;
         char lineBuffer[]=new char[128], buf[]=lineBuffer;
         int room= buf.length, offset=0;
         try {
         loop: 
            while (true) {
              // read chars into a buffer which grows as needed
               switch (c = is.read() ) {
                  case -1: 
                     break loop;
                  
                  default: 
                     if (--room < 0) {
                        buf = new char[offset + 128];
                        room = buf.length - offset - 1;
                        System.arraycopy(lineBuffer, 0, 
                                        buf, 0, offset);
                        lineBuffer = buf;
                     }
                     buf[offset++] = (char) c;
                     break;
               }
            }
         } 
         
            catch(IOException ioe) {
            
               ioe.printStackTrace();}
         if ((c == -1) && (offset == 0)) {
            return null;
         }
         return String.copyValueOf(buf, 0, offset);
      }
   
   
      private void ftpConnect(String server)
      throws IOException {
      
        // Set up socket, control streams, connect to ftp server
        // Open socket to server control port 21
         csock = new Socket(server, CNTRL_PORT);
        // Open control streams
         InputStream cis = csock.getInputStream();
         dcis =  new BufferedReader(new InputStreamReader(cis));
         OutputStream cos = csock.getOutputStream();
         pos = new PrintWriter(cos, true); // set auto flush true.
        // See if server is alive or dead... 
         String numerals = responseHandler(null); 
         if(numerals.substring(0,3).equals("220")) // ftp server alive
            ; // System.out.println("Connected to ftp server");
         else System.err.println("Error connecting to ftp server.");
      }
   
   
      private void ftpLogin(String user, String pass)
      throws IOException {
      
         ftpSendCmd("USER "+user);
         ftpSendCmd("PASS "+pass);
      }
   
   
      private void ftpSetDir(String dir)
      throws IOException { 
      
        // cwd to dir
         ftpSendCmd("CWD "+dir);
      }
   
   
      private void ftpSetTransferType(boolean asc)
      throws IOException {
      
      // set file transfer type
         String ftype = (asc? "A" : "I");
         ftpSendCmd("TYPE "+ftype);
      }    
   
   
      private Socket ftpGetDataSock()
      throws IOException {
      
         // Go to PASV mode, capture server reply, parse for socket setup
         // V2.1: generalized port parsing, allows more server variations
         String reply = ftpSendCmd("PASV");
      
         // New technique: just find numbers before and after ","!
         StringTokenizer st = new StringTokenizer(reply, ",");
         String[] parts = new String[6]; // parts, incl. some garbage
         int i = 0; // put tokens into String array
         while(st.hasMoreElements()) {
            // stick pieces of host, port in String array
            try {
               parts[i] = st.nextToken();
               i++;
            } 
            
               catch(NoSuchElementException nope){
               
                  nope.printStackTrace();}
         } // end getting parts of host, port
      
        // Get rid of everything before first "," except digits
         String[] diggies = new String[3];
         for(int j = 0; j < 3; j++) {
            // Get 3 characters, inverse order, check if digit/character
            diggies[j] = parts[0].substring(parts[0].length() - (j + 1),
                                 parts[0].length() - j); // next: digit or character?
            if(!Character.isDigit(diggies[j].charAt(0)))
               diggies[j] = "";
         }
         parts[0] = diggies[2] + diggies[1] + diggies[0];
        // Get only the digits after the last ","
         String[] porties = new String[3];
         for(int k = 0; k < 3; k++) {
            // Get 3 characters, in order, check if digit/character
            // May be less than 3 characters
            if((k + 1) <= parts[5].length())
               porties[k] = parts[5].substring(k, k + 1);
            else porties[k] = "FOOBAR"; // definitely not a digit!
            // next: digit or character?
            if(!Character.isDigit(porties[k].charAt(0)))
               porties[k] = "";
         } // Have to do this one in order, not inverse order
         parts[5] = porties[0] + porties[1] + porties[2];
        // Get dotted quad IP number first
         String ip = parts[0]+"."+parts[1]+"."+parts[2]+"."+parts[3];
      
        // Determine port
         int port = -1;
         try { // Get first part of port, shift by 8 bits.
            int big = Integer.parseInt(parts[4]) << 8;
            int small = Integer.parseInt(parts[5]);
            port = big + small; // port number
         } 
         
            catch(NumberFormatException nfe) {
            
               nfe.printStackTrace();}
         if((ip != null) && (port != -1))
            dsock = new Socket(ip, port);
         else throw new IOException();
         return dsock;
      }
   
   
      private String ftpSendCmd(String cmd)
      throws IOException
      
      { // This sends a dialog string to the server, returns reply
      // V2.0 Updated to parse multi-string responses a la RFC 959
      // Prints out only last response string of the lot.
         pos.print(cmd + "\r\n" );
         pos.flush(); 
         String response = responseHandler(cmd);
         return response;
      }
   
     // new method to read multi-line responses
     // responseHandler: takes a String command or null and returns
     // just the last line of a possibly multi-line response
   
      private String responseHandler(String cmd) 
      throws IOException
      
      { // handle more than one line returned
         String reply = dcis.readLine();
         String numerals = reply.substring(0, 3);
         String hyph_test = reply.substring(3, 4);
         String next = null;
         if(hyph_test.equals("-")) {
            // Create "tester", marks end of multi-line output
            String tester = numerals + " ";
            boolean done = false;
            while(!done) { // read lines til finds last line
               next = dcis.readLine();
                // Read "over" blank line responses
               while (next.equals("") || next.equals("  ")) {
                  next = dcis.readLine();
               }
            
                // If next starts with "tester", we're done
               if(next.substring(0,4).equals(tester))
                  done = true;
            }
         
            if(DEBUG)
               if(cmd != null)
                  System.out.println("Response to: "+cmd+" was: "+next);
               else
                  System.out.println("Response was: "+next);
            return next;
         
         } 
         else // "if (hyph_test.equals("-")) not true"
            if(DEBUG)
               if(cmd != null)
                  System.out.println("Response to: "+cmd+" was: "+reply);
               else
                  System.out.println("Response was: "+reply);
         return reply;
      }
   
   
      private void ftpLogout() {// logout, close streams
      
         try { 
            if(DEBUG) System.out.println("sending BYE");
            pos.print("BYE" + "\r\n" );
            pos.flush();
            pos.close();
            dcis.close();
            csock.close();
            dsock.close();
         } 
         
            catch(IOException ioe) {
            
               ioe.printStackTrace();}
      }
   
   
      private static final int CNTRL_PORT = 21;
      private Socket csock = null;
      private Socket dsock = null;
      private BufferedReader dcis;
      private PrintWriter pos;
   }

