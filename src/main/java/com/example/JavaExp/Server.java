package com.example.JavaExp;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends JFrame implements ActionListener {

    ServerSocket serverSocket ; //定义服务器套接字
    Socket socket ; //定义客户套接字
    JTextArea jTextArea = new JTextArea() ;
    JTextField jTextField = new JTextField(20) ;
    JButton jButton = new JButton("send") ;
    DataOutputStream out ; //定义数据输出流
    DataInputStream in ; //定义数据输入流
    String cname = null ;
    public Server() throws IOException {
        serverSocket = new ServerSocket(7777) ; //用7777端口
        JScrollPane jsp = new JScrollPane(jTextArea) ;
        this.getContentPane().add(jsp,"Center") ;
        JPanel p1 = new JPanel() ;
        p1.add(jTextField) ;
        p1.add(jButton) ;
        this.getContentPane().add(p1,"South");
        jButton.addActionListener(this);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                setVisible(false);
                System.exit(0);
            }
        });
        setTitle("服务器");
        setSize(340,200);
        setVisible(true);
        try{
            socket = serverSocket.accept() ; //阻塞等待客户请求
            System.out.println("aa");//有客户请求则产生一个socket对象，并继续执行
            out = new DataOutputStream(socket.getOutputStream());
            //由socket对象得到输入流，并且构造相应的DataOutputStream对象
            in = new DataInputStream(socket.getInputStream());
            //由socket对象得到输出流，并构造DataInputStream对象
            out.writeUTF("你已经成功连接服务器");//给客户反馈信息
            Communnion th = new Communnion(this) ;
            th.start();
        }catch (Exception e){}
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    out.writeUTF("bye");
                } catch (IOException ex) {
                    dispose();
                    System.exit(0);
                }
            }
        });
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String n = jTextField.getText() ;
        try {
            out.writeUTF(n);
            jTextField.setText("信息输入。。。。。。");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception{
        Server mainFrame = new Server() ;
    }
}
//自定义线程类
class Communnion extends Thread{
    Server fp ;
    Communnion(Server fp){
        this.fp =fp ;
    }
    public void run(){
        String msg = null ;
        while(true){
            try {
                msg = fp.in.readUTF() ;
                if(msg != null) {
                    fp.jTextArea.append(msg+"\n");
                }
            } catch (IOException e) {
                break ;
            }
        }
        try{
            fp.out.close();
            fp.in.close();
            fp.socket.close();
            fp.serverSocket.close();
        }catch (Exception e){

        }
    }
}
