package com.gui;

import com.core.KillNetKeeper;
import com.util.CacheUtil;
import javafx.stage.FileChooser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created by lipengd@zbj.com on 2017/9/12.
 */
public class Frame extends JFrame{
    private final int WIDTH = 400;
    private final int HEIGHT = 400;
    private final String TITLE = "killNetKepeer2.0";
    private final String WELCOM= "欢迎使用killNetKepeer2.0";
    private int positonX;
    private int positionY;
    private JLabel label;
    private Panel panel;
    private JLabel batPathLabel;
    private JLabel netkepperPathLabel;
    private JLabel wifiPathLabel;
    private JTextField batPathText;
    private JTextField netkepperText;
    private JTextField wifiPathText;
    private JButton batPathButton;
    private JButton netkepperButton;
    private JButton wifiPathTButton;
    private JButton openWifi;
    private JButton cancel;

    public Frame(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        positonX = ((int)screenSize.getWidth() - WIDTH) / 2;
        positionY = ((int)screenSize.getHeight() - HEIGHT) / 2;
        this.setBounds( positonX, positionY, WIDTH, HEIGHT);
        this.setTitle(TITLE);
        this.setResizable(false);
        init();
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void init(){
        CacheUtil cacheUtil = new CacheUtil();
        panel = new Panel();
        label = new JLabel(WELCOM);
        label.setFont(new Font("微软雅黑", Font.BOLD, 30));

        batPathLabel = new JLabel("BAT文件路径:");
        netkepperPathLabel = new JLabel("netkepper文件路径:");
        wifiPathLabel = new JLabel("猎豹wifi路径:");

        batPathText = new JTextField(cacheUtil.getBatPath(),30);
        netkepperText = new JTextField(cacheUtil.getNetKepperPath(),30);
        wifiPathText = new JTextField(cacheUtil.getWifiPath(),30);

        batPathButton = new JButton("选择目录");
        netkepperButton = new JButton("选择目录");
        wifiPathTButton = new JButton("选择目录");

        openWifi = new JButton("开启wifi");
        cancel = new JButton("取消");

        addAction(batPathButton, batPathText, JFileChooser.FILES_ONLY);
        addAction(netkepperButton, netkepperText, JFileChooser.DIRECTORIES_ONLY);
        addAction(wifiPathTButton, wifiPathText, JFileChooser.FILES_ONLY);

        openWifi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String batPath = batPathText.getText();
                String netKepperPath = netkepperText.getText();
                String wifiPath = wifiPathText.getText();
                cacheUtil.setBatPath(batPath);
                cacheUtil.setNetKepperPath(netKepperPath);
                cacheUtil.setWifiPath(wifiPath);
                cacheUtil.store();
                KillNetKeeper killNetKeeper = new KillNetKeeper(netKepperPath, wifiPath, batPath);
                killNetKeeper.killNetkepper();
            }
        });

        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        panel.add(label);

        panel.add(batPathLabel);
        panel.add(batPathButton);
        panel.add(batPathText);

        panel.add(netkepperPathLabel);
        panel.add(netkepperButton);
        panel.add(netkepperText);

        panel.add(wifiPathLabel);
        panel.add(wifiPathTButton);
        panel.add(wifiPathText);

        panel.add(openWifi);
        panel.add(cancel);

        this.add(panel);
    }


    public void addAction(JButton button, JTextField textField, int flieType){
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                chooser.setFileSelectionMode(flieType);
                chooser.showOpenDialog(null);
                File file = chooser.getSelectedFile();
                if(file != null){
                    String path = file.getAbsolutePath();
                    textField.setText(path);
                }
            }
        });
    }



    public static void main(String[] args) {
        new Frame();
    }


}
