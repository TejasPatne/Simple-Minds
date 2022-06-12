package simple.minds;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Database extends JFrame implements ActionListener {

    JButton next,lifeline, submit;
    JLabel qno,question;
    JRadioButton opt1, opt2, opt3, opt4;
    ButtonGroup options;

    String username;
    int count=0,score=0;

    Database(String username){
        this.username=username;

        setBounds(50,30,1200,650);
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);
        setVisible(true);

        ImageIcon i1= new ImageIcon(ClassLoader.getSystemResource("simple/minds/icons/quiz.jpg"));
        Image i2=i1.getImage().getScaledInstance(1200,300,Image.SCALE_DEFAULT);
        ImageIcon i3=new ImageIcon(i2);
        JLabel l1= new JLabel(i3);
        l1.setBounds(0,0,1200,300);
        add(l1);

        next= new JButton("N e x t");
        lifeline= new JButton("L i f e l i n e");
        submit= new JButton("S u b m i t");

        qno=new JLabel("1");
        question=new JLabel("This is question");
        opt1=new JRadioButton("option 1");
        opt2=new JRadioButton("option 2");
        opt3=new JRadioButton("option 3");
        opt4=new JRadioButton("option 4");

        options= new ButtonGroup();
        options.add(opt1);
        options.add(opt2);
        options.add(opt3);
        options.add(opt4);

        qno.setFont(new Font("Verdana",Font.PLAIN,20));
        question.setFont(new Font("Verdana",Font.PLAIN,20));
        opt1.setFont(new Font("Verdana",Font.PLAIN,20));
        opt2.setFont(new Font("Verdana",Font.PLAIN,20));
        opt3.setFont(new Font("Verdana",Font.PLAIN,20));
        opt4.setFont(new Font("Verdana",Font.PLAIN,20));

        qno.setBounds(50,350,50,25);
        question.setBounds(100,350,1000,25);

        opt1.setBounds(100,400,800,25);
        opt2.setBounds(100,430,800,25);
        opt3.setBounds(100,460,800,25);
        opt4.setBounds(100,490,800,25);

        next.setBounds(900,400,200,30);
        lifeline.setBounds(900,450,200,30);
        submit.setBounds(900,500,200,30);

        next.setFont(new Font("",Font.PLAIN,18));
        lifeline.setFont(new Font("",Font.PLAIN,18));
        submit.setFont(new Font("",Font.PLAIN,18));

        next.setBackground(new Color(30,144,254));
        lifeline.setBackground(new Color(30,144,254));
        submit.setBackground(new Color(30,144,254));

        opt1.setBackground(Color.WHITE);
        opt2.setBackground(Color.WHITE);
        opt3.setBackground(Color.WHITE);
        opt4.setBackground(Color.WHITE);

        next.setForeground(Color.WHITE);
        lifeline.setForeground(Color.WHITE);
        submit.setForeground(Color.WHITE);

        add(qno);
        add(question);

        add(opt1);
        add(opt2);
        add(opt3);
        add(opt4);

        add(next);
        add(lifeline);
        add(submit);

        next.addActionListener(this);
        lifeline.addActionListener(this);
        submit.addActionListener(this);

        next.setEnabled(true);
        submit.setEnabled(false);

        start(0);
    }

    public void actionPerformed(ActionEvent ae){
        opt1.setEnabled(true);
        opt2.setEnabled(true);
        opt3.setEnabled(true);
        opt4.setEnabled(true);

        try {
            String answer=options.getSelection().getActionCommand();
            String query="update quizanswertable set given_answer= '"+answer+"' where que_no="+(count+1)+"";

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/quizapp", "root", "ramchandra@123");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from quizanswertable");



            int counter = 0;

            while (counter <= count) {
                resultSet.next();
                counter++;
            }


            if (ae.getSource() == next) {
                if (options.getSelection() == null) {
                    //"";
                } else {
                    statement.executeUpdate(query);
                    System.out.println("query executed succesfully");
                }

                if(count==8){
                    next.setEnabled(false);
                    submit.setEnabled(true);
                }



                count++;
                start(count);

            } else if (ae.getSource() == lifeline) {
                opt1.setEnabled(false);
                opt2.setEnabled(false);
                opt3.setEnabled(false);
                opt4.setEnabled(false);

                String Tester1=resultSet.getString("answer");
                String Tester2=resultSet.getString("blunder_answer");

                if(opt1.getText().equals(Tester1)||opt1.getText().equals(Tester2)){
                    opt1.setEnabled(true);
                }
                if(opt2.getText().equals(Tester1)||opt2.getText().equals(Tester2)){
                    opt2.setEnabled(true);
                }
                if(opt3.getText().equals(Tester1)||opt3.getText().equals(Tester2)){
                    opt3.setEnabled(true);
                }
                if(opt4.getText().equals(Tester1)||opt4.getText().equals(Tester2)){
                    opt4.setEnabled(true);
                }

                lifeline.setEnabled(false);

            } else if (ae.getSource() == submit) {

                statement.executeUpdate(query);

                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/quizapp", "root", "ramchandra@123");
                Statement stmt = connection.createStatement();
                ResultSet rsSet = statement.executeQuery("select * from quizanswertable");
                while(rsSet.next()) {
                    if (rsSet.getString("answer").equals(rsSet.getString("given_answer"))) {
                        score += 10;
                    } else {
                        //do nothing
                    }
                }

                stmt.close();
                rsSet.close();
                con.close();

                this.setVisible(false);//classing quiz frame
                new Score(username, score).setVisible(true);
            }
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void start(int count){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/quizapp", "root", "ramchandra@123");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from quiztable");

            int counter=0;

            while (counter<=count){
                resultSet.next();
                counter++;
            }

            System.out.println(resultSet.getString("que_sentence"));
            qno.setText("" + resultSet.getString("que_no") + "");
            question.setText("" + resultSet.getString("que_sentence") + "");
            opt1.setLabel("" + resultSet.getString("option1") + "");
            opt1.setActionCommand("" + resultSet.getString("option1") + "");
            opt2.setLabel("" + resultSet.getString("option2") + "");
            opt2.setActionCommand("" + resultSet.getString("option2") + "");
            opt3.setLabel("" + resultSet.getString("option3")+"");
            opt3.setActionCommand("" + resultSet.getString("option3") + "");
            opt4.setLabel("" + resultSet.getString("option4") + "");
            opt4.setActionCommand("" + resultSet.getString("option4") + "");
            options.clearSelection();
            //repaint();

        }catch (Exception e){
            System.out.println(e);
        }
    }



    public static void main(String []args){
        new Database("");
    }

}
