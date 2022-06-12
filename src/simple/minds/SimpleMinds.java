package simple.minds;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SimpleMinds extends JFrame{
    JButton b1, b2;
    JTextField t1;

    SimpleMinds(){
        setBounds(200,150,900,500);//setting where the window will appear
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        ImageIcon i1= new ImageIcon(ClassLoader.getSystemResource("simple/minds/icons/login.jpeg"));
        JLabel l1= new JLabel(i1);
        l1.setBounds(0,0,450,500);
        add(l1);

        JLabel l2= new JLabel("Simple Minds");
        l2.setFont(new Font("Serif", Font.BOLD,45));
        l2.setForeground(new Color(30,144,254));
        l2.setBounds(520,70,450,50);
        add(l2);

        JLabel l3= new JLabel("Enter Name : ");
        l3.setFont(new Font("San-serif", Font.PLAIN,18));
        l3.setBounds(500,170,150,30);
        add(l3);

        t1= new JTextField();
        t1.setFont(new Font("San-serif", Font.PLAIN,18));
        t1.setBounds(620,170,180,25);
        add(t1);

        b1=new JButton("R u l e s");
        b1.setBounds(520,250,100,30);
        b1.setBackground(new Color(30,144,254));
        b1.setForeground(Color.WHITE);
        b1.addActionListener(this::ActionPerformed);
        add(b1);

        b2=new JButton("E x i t");
        b2.setBounds(680,250,100,30);
        b2.setBackground(new Color(30,144,254));
        b2.setForeground(Color.WHITE);
        b2.addActionListener(this::ActionPerformed);
        add(b2);

        setVisible(true);
    }

    public void ActionPerformed(ActionEvent ae){
        if(ae.getSource()==b1){
            String name= t1.getText();
            this.setVisible(false);
            new Rules(name);
        }
        else{
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        new SimpleMinds();//creating object to see the window
    }
}
