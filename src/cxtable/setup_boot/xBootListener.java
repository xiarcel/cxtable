package cxtable.setup_boot;
/*interface for booting... two methods for xFTP or xHTTP... this really needs
to be re-worked so that it can accept more anonymous types...
perhaps xBootData interface*/


public interface xBootListener{
public void boot(xFTPData x);
public void boot(xHTTPData x);
}
