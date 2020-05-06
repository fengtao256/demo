package com.example.JavaExp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class Client extends JFrame implements ActionListener {
    Socket socket ;
    JTextArea t1 = new JTextArea() ;
    JTextField t2 = new JTextField(20) ;
    JButton b1 = new JButton("send") ;
    JButton b2 = new JButton("连接服务器") ;
    DataOutputStream out ; //输出流
    DataInputStream in ; //输入流
    public Client() {
        JScrollPane jsp = new JScrollPane(t1);
        this.getContentPane().add(jsp, "Center");
        JPanel p1 = new JPanel();
        p1.add(t2);
        p1.add(b1);
        JPanel p2 = new JPanel();
        p2.add(b2);
        this.getContentPane().add(p2, "North");
        this.getContentPane().add(p1, "South");
        b1.addActionListener(this);
        b2.addActionListener(this);
        setTitle("客户端");
        setSize(340, 200);
        setVisible(true);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                setVisible(false);
                System.exit(0);
            }
        });
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == b1) {
            try{
                out = new DataOutputStream(socket.getOutputStream());
                String n = t2.getText() ;
                out.writeUTF(n);
                t2.setText("");
            }catch(Exception e1) {
                e1.printStackTrace();
            }
        }else if(e.getSource() == b2) {
            try{
                socket = new Socket("127.0.0.1",7777) ;
            }catch (Exception e2) {
                e2.printStackTrace();
            }
            new CommunnionClient(this).start();
        }
    }
    public static void main(String[] args){
        Client mainFrame = new Client() ;
    }
}
class CommunnionClient extends Thread{
    Client fp ;
    CommunnionClient(Client fp){
        this.fp = fp ;
    }
    public void run(){
        String msg = null ;
        while (true) {
            try{
                fp.in = new DataInputStream(fp.socket.getInputStream());
                msg = fp.in.readUTF();
                if(msg != null){
                    fp.t1.append(msg+"\n");
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            try{
                fp.out.close();
                fp.in.close();
                fp.socket.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
