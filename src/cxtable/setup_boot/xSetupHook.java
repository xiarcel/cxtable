package cxtable.setup_boot;

/*this class is part of the setup class ran from outside the xTable main 
function..for creating setup files*/


public interface xSetupHook{
public void update();
public void setFTP(xFTPData[] x);
public void setHTTP(xHTTPData[] x);
public void setPHP(xPHPData[] x);
public void setPHPServ(xPHPServData[] x);

}
