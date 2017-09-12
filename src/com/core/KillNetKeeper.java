package com.core;

import java.io.*;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lipengd@zbj.com on 2017/9/11.
 */
public class KillNetKeeper {
    private String commondPrefix ;
    private String filePath ;
    private String wifiExe ;
    private String batPath ;
    private String exeBatPrefix;
    private final String ERROR_MESSAGE = "开启失败，请重试";
    private static final String PRETREATEMENT = "%1 %2\n" +
            "ver|find \"5.\">nul&&goto :st\n" +
            "mshta vbscript:createobject(\"shell.application\").shellexecute(\"%~s0\",\"goto :st\",\"\",\"runas\",1)(window.close)&goto :eof\n" +
            " \n" +
            ":st\n" +
            "copy \"%~0\" \"%windir%\\system32\"\n";

    public KillNetKeeper(String filePath, String wifiExe, String batPath){
        this.filePath = filePath;
        this.wifiExe = wifiExe;
        this.batPath = batPath;
        init();
    }

    private void init() {
        String path = System.getProperty("user.dir");
        Properties properties = new Properties();
        try {
            properties.load(new InputStreamReader(new FileInputStream(path+"/resources/location.properties")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        commondPrefix = (String) properties.get("COMMOND_PREFIX");
        exeBatPrefix = (String) properties.get("EXEBAR_COMMOND_PREFIX");

    }



    //获取进程名
    private  String getName(String filePath){
        File file = new File(filePath);
        if(file.exists()){
            File[] files = file.listFiles();
            for(File file1 : files){
                if(file1.isFile()){
                    String name = file1.getName();
                    if(match(name)){
                        return name ;
                    }
                }
            }
        }
        return null;
    }

    //正则匹配
    private  boolean match(String name){
        Pattern pattern = Pattern.compile("\\w{7,8}-\\w{4}-\\w{4}-\\w{4}-\\w{12}");
        Matcher matcher = pattern.matcher(name);
        return matcher.find();
    }

    //输出到文件
    private  void output(String content) throws IOException {
        File file = new File(batPath);
        FileWriter writer = new FileWriter(file);
        writer.write(PRETREATEMENT + content);
        writer.flush();
        writer.close();
    }

    //关闭cmd
    private  void killProcess(){
        Runtime rt = Runtime.getRuntime();
        try {
            rt.exec("cmd.exe /C start wmic process where name='cmd.exe' call terminate");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //结束进程
    public  String  killNetkepper() {
        String processName = getName(filePath);
        Runtime runtime = Runtime.getRuntime();
        if(processName != null){
            String commond = commondPrefix + processName;
            String commond2 = exeBatPrefix + batPath;
            try {
                output(commond);
                runtime.exec(commond2);
                Runtime.getRuntime().exec(wifiExe);
                killProcess();
            }catch (Exception e){
                return ERROR_MESSAGE;
            }
        }
        return null;
    }
}
