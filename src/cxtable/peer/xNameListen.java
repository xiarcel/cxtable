package cxtable.peer;

/*this interface is for those pieces waiting for the name of the
peer..most likely not in use anymore.. and if so..not necessary...
although it does allow multiple parts to listen at the same time, and be
notified*/


public interface xNameListen{

public void login_name(String name);
}
