package simple.minds;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Rules extends JFrame {
    JButton b1, b2;
    String username;
    Rules(String username){
        this.username=username;
        setBounds(250,50,800,600);
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        JLabel l1=new JLabel("Welcome "+ username+" to Simple Minds");
        l1.setFont(new Font("Verdana",Font.BOLD,30));
        l1.setForeground(new Color(30,144,254));
        l1.setBounds(30,20,800,50);
        add(l1);

        JLabel l2=new JLabel("");
        l2.setFont(new Font("serif",Font.PLAIN,16));
        //l2.setForeground(new Color(30,144,254));
        l2.setBounds(30,80,700,350);
        l2.setText(
                "<html>"+
                        "1. Fill up your personal details carefully."+"<br><br>"+
                        "2. All questions are compulsory."+"<br><br>"+
                        "3. Each correct answer has 10 points."+"<br><br>"+
                        "4. You are allowed to answer only once, make sure that you choose correct option."+"<br><br>"+
                        "5. You cannot revisit question, ones Submitted"+"<br><br>"+
                "</html>"
        );
        add(l2);

        b1=new JButton("B a c k");
        b1.setFont(new Font("serif",Font.PLAIN,18));
        b1.setBackground(new Color(30,144,254));
        b1.setForeground(Color.WHITE);
        b1.setBounds(250,470,100,30);
        b1.addActionListener(this::ActionPerformed);
        add(b1);

        b2=new JButton("S t a r t");
        b2.setFont(new Font("serif",Font.PLAIN,18));
        b2.setBackground(new Color(30,144,254));
        b2.setForeground(Color.WHITE);
        b2.setBounds(450,470,100,30);
        b2.addActionListener(this::ActionPerformed);
        add(b2);

        setVisible(true);
    }

    public void ActionPerformed(ActionEvent ae){
        if(ae.getSource()==b1){
            this.setVisible(false);
            new SimpleMinds().setVisible(true);
        }
        else{
            this.setVisible(false);
            new Database(username).setVisible(true);
        }
    }

    public static void main(String[] args) {
        new Rules("");
    }
}
