package simple.minds;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Score extends JFrame {
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    LocalDateTime now = LocalDateTime.now();

    Score(String username, int Score){
        setBounds(250,50,850,600);
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        ImageIcon i1=new ImageIcon(ClassLoader.getSystemResource("simple/minds/icons/score.png"));
        Image i2=i1.getImage().getScaledInstance(400,300,Image.SCALE_DEFAULT);
        ImageIcon i3=new ImageIcon(i2);

        JLabel l1= new JLabel(i3);
        JLabel l2=new JLabel("Thank you "+username+" for playing Simple Minds");
        JLabel l3=new JLabel("Your score is "+Score);
        JLabel l4=new JLabel(" "+dtf.format(now));

        JButton b1=new JButton("Play Again");

        l1.setBounds(20,150,400,300);
        l2.setBounds(45,50,700,30);
        l3.setBounds(450,200,350,50);
        l4.setBounds(500,250,350,50);

        b1.setBounds(500,300,200,30);

        l2.setForeground(new Color(72,192,255));
        b1.setForeground(Color.WHITE);

        b1.setBackground(new Color(30,144,254));

        l2.setFont(new Font("Verdana",Font.PLAIN,25));
        l3.setFont(new Font("Cambria",Font.BOLD,40));
        l4.setFont(new Font("Cambria",Font.PLAIN,20));

        b1.setFont(new Font("",Font.PLAIN,18));

        b1.addActionListener(this::ActionPerformed);

        add(l1);
        add(l2);
        add(l3);
        add(l4);
        add(b1);
    }

    public void ActionPerformed(ActionEvent ae){
        this.setVisible(false);
        new SimpleMinds().setVisible(true);
    }

    public static void main(String[] args) {
        new Score("",0).setVisible(true);

    }
}
