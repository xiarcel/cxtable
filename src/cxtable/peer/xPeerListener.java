package cxtable.peer;
import cxtable.core_comm.xClientConn;

/*listener interface for evoking the peer_enable*/


   public interface xPeerListener{
   
      public void peer_enable(String reg_id_local, String client_id, xClientConn x);
   
   }

