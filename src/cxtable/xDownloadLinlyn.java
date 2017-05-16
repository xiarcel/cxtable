package cxtable;

/*this is a modified Linlyn (under the KDE artistic license)...
it is for future filesharing issues...not currently in use...  */


 import java.io.*;
 import java.net.*;
 import java.util.*;

    public class xDownloadLinlyn extends Thread{
   
    // FOR DEBUGGING: set the variable to "true"
      private boolean DEBUG = false;
		private String dir="";
   	private String server,file; private int port;private xDownloadListener xdl;
    // constructor needs servername, username and passwd
      

	public xDownloadLinlyn(String _server, int _port, String _file, xDownloadListener _xdl)
			{
     		port = _port; server = _server; file = _file;xdl=_xdl;
			}
		public xDownloadLinlyn(String _server,	String _file, xDownloadListener _xdl)
			{
     		port = CNTRL_PORT; server = _server; file = _file;xdl=_xdl;
			}
		public void setDir(String d)
		{
		dir=d;
		}
		
		
		public void run()
			{	String contents="";
			   try { 
            ftpConnect(server);
                } 
            catch(IOException ioe) {
              xdl.dl_error(file, ioe); return;}
				try{
				contents = download(false);
					}
				catch(Exception e)
					{xdl.dl_error(file,e); return;}
				xdl.process(file,contents);
				return;
      }
      
      private String download(boolean asc)
      throws IOException {
         if (dir.equals("")==false){ftpSetDir(dir);}
         ftpSetTransferType(asc);
         dsock = ftpGetDataSock();
         InputStream is = dsock.getInputStream();
         ftpSendCmd("RETR "+file);
      
         String cont = getAsString(is);
         ftpLogout();
         return cont;
      }   
    ///////////////// private fields ////////////////////
      private boolean pauser = false;  // it's a hack. We're going to 
          // stall (refuse further requests) till we get a reply back 
          // from server for the current request.
   
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
         csock = new Socket(server, port);
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
         String[] possNum = new String[3];
         for(int j = 0; j < 3; j++) {
            // Get 3 characters, inverse order, check if digit/character
            possNum[j] = parts[0].substring(parts[0].length() - (j + 1),
                                 parts[0].length() - j); // next: digit or character?
            if(!Character.isDigit(possNum[j].charAt(0)))
               possNum[j] = "";
         }
         parts[0] = possNum[2] + possNum[1] + possNum[0];
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
         if (pauser) // i.e. we already issued a request, and are
                  // waiting for server to reply to it.  
         {
            if (dcis != null)
            {
               String discard = dcis.readLine(); // will block here
                    // preventing this further client request until server
                    // responds to the already outstanding one.
               if (DEBUG) {
                  System.out.println("keeping handler in sync"+
                                    " by discarding next response: ");
                  System.out.println(discard);
               }
               pauser = false;
            }
         }
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
         String reply = this.responseParser(dcis.readLine());
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
   
    // responseParser: check first digit of first line of response
    // and take action based on it; set up to read an extra line
    // if the response starts with "1"
      private String responseParser(String resp)
      throws IOException
      { // Check first digit of resp, take appropriate action.
         String digit1 = resp.substring(0, 1);
         if(digit1.equals("1")) {
            // server to act, then give response
            if(DEBUG) System.out.println("in 1 handler");
            // set pauser
            pauser = true;
            return resp;
         }
         else if(digit1.equals("2")) { // do usual handling
            if(DEBUG) System.out.println("in 2 handler");
            // reset pauser
            pauser = false;
            return resp;
         }
         else if(digit1.equals("3") || digit1.equals("4")
                || digit1.equals("5")) { // do usual handling
            if(DEBUG) System.out.println("in 3-4-5 handler");
            return resp;
         }
         else { // not covered, so return null
            return null;
         }
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

/*this software is based on Peter van der Linden and Steve Lynch's "Linlyn" ftp
utility.  The "upload" and "append" routines have been removed.  It has been threaded, and this
particular edit makes it purely the client download end with no upload
capabilities.  The "Linlyn" program is readily available at Peter's site
(<insert link>) and is licensed under the "Artistic License".
*/
