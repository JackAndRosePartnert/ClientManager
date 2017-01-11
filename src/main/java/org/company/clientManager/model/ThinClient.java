package org.company.clientManager.model;
public class ThinClient{
    private String name;
    private String ip;
    private String mac;
    
    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getIp(){
        return this.ip;
    }

    public void setIp(String ip){
        this.ip = ip;
    }

    public String getMac(){
        return this.mac;
    }

    public void setMac(String mac){
        this.mac = mac;
    }

}
