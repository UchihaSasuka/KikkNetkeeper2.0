package com.util;

import java.io.*;
import java.util.Properties;

/**
 * Created by lipengd@zbj.com on 2017/9/12.
 */
public class CacheUtil {
    private String batPath;
    private String netKepperPath;
    private String wifiPath;
    private Properties properties;
    private String path;
    public CacheUtil(){
        init();
    }

    public void init() {
        properties = new Properties();
        try {
            path = System.getProperty("user.dir");
            InputStream inputStream =  new FileInputStream(path + "/resources/location.properties");
            properties.load(inputStream);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        batPath = (String) properties.get("BAT_PATH");
        netKepperPath = (String) properties.get("NETKEPEER_PATH");
        wifiPath = (String) properties.get("WIFI_PATH");
    }

    public void store(){
        properties.setProperty("BAT_PATH", batPath);
        properties.setProperty("NETKEPEER_PATH", netKepperPath);
        properties.setProperty("WIFI_PATH", wifiPath);
        try {
            properties.store(new FileOutputStream(path+"/resources/location.properties"), null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getBatPath() {
        return batPath;
    }

    public void setBatPath(String batPath) {
        this.batPath = batPath;
    }

    public String getNetKepperPath() {
        return netKepperPath;
    }

    public void setNetKepperPath(String netKepperPath) {
        this.netKepperPath = netKepperPath;
    }

    public String getWifiPath() {
        return wifiPath;
    }

    public void setWifiPath(String wifiPath) {
        this.wifiPath = wifiPath;
    }
}
