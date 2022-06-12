package simple.minds;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class MyJDBC extends JFrame {

    JButton next,lifeline, submit;
    JLabel qno,question;
    JRadioButton opt1, opt2, opt3, opt4;
    ButtonGroup options;
    public static int count=0, timer=15, score=0, ans_given=0,counter=0;


    String username;
    //constructor
    MyJDBC(String username){
        this.username=username;
        setBounds(50,30,1200,650);
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        //Quiz-image
        ImageIcon i1= new ImageIcon(ClassLoader.getSystemResource("simple/minds/icons/quiz.jpg"));
        Image i2=i1.getImage().getScaledInstance(1200,300,Image.SCALE_DEFAULT);
        ImageIcon i3=new ImageIcon(i2);
        JLabel l1= new JLabel(i3);
        l1.setBounds(0,0,1200,300);
        add(l1);

        qno=new JLabel("");
        qno.setFont(new Font("Verdana",Font.PLAIN,20));
        //quo.setForeground(new Color(30,144,254));
        qno.setBounds(50,350,50,25);
        add(qno);

        question=new JLabel("");
        question.setFont(new Font("Verdana",Font.PLAIN,20));
        //question.setForeground(new Color(30,144,254));
        question.setBounds(100,350,1000,25);
        add(question);

        //options
        opt1=new JRadioButton("");
        opt1.setFont(new Font("Verdana",Font.PLAIN,20));
        opt1.setBackground(Color.WHITE);
        opt1.setBounds(100,400,800,25);
        add(opt1);

        opt2=new JRadioButton("");
        opt2.setFont(new Font("Verdana",Font.PLAIN,20));
        opt2.setBackground(Color.WHITE);
        opt2.setBounds(100,430,800,25);
        add(opt2);

        opt3=new JRadioButton("");
        opt3.setFont(new Font("Verdana",Font.PLAIN,20));
        opt3.setBackground(Color.WHITE);
        opt3.setBounds(100,460,800,25);
        add(opt3);

        opt4=new JRadioButton("");
        opt4.setFont(new Font("Verdana",Font.PLAIN,20));
        opt4.setBackground(Color.WHITE);
        opt4.setBounds(100,490,800,25);
        add(opt4);
        //grouping options
        options= new ButtonGroup();
        options.add(opt1);
        options.add(opt2);
        options.add(opt3);
        options.add(opt4);

        //cta buttons
        next=new JButton("N e x t");
        next.setFont(new Font("",Font.PLAIN,18));
        next.setBackground(new Color(30,144,254));
        next.setForeground(Color.WHITE);
        next.setBounds(900,400,200,30);
        next.addActionListener(this::ActionPerformed);
        add(next);

        lifeline=new JButton("50 - 50");
        lifeline.setFont(new Font("",Font.PLAIN,18));
        lifeline.setBackground(new Color(30,144,254));
        lifeline.setForeground(Color.WHITE);
        lifeline.setBounds(900,450,200,30);
        lifeline.addActionListener(this::ActionPerformed);
        add(lifeline);

        submit=new JButton("S u b m i t");
        submit.setFont(new Font("",Font.PLAIN,18));
        submit.setBackground(new Color(30,144,254));
        submit.setForeground(Color.WHITE);
        submit.setBounds(900,500,200,30);
        submit.setEnabled(false);
        submit.addActionListener(this::ActionPerformed);
        add(submit);


        //starting quiz with 1st question
        start(0);

    }//constructor ends here

    //cta
    public void ActionPerformed(ActionEvent ae){
        try{
            //creating connection
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/quizapp","root","ramchandra@123");
            Statement statement= connection.createStatement();
            ResultSet resultSet=statement.executeQuery("select * from quiztable,quizanswertable");
            resultSet.next();//this bring the cursor to first row

            if(ae.getSource()==next){
                repaint();
                opt1.setEnabled(true);
                opt2.setEnabled(true);
                opt3.setEnabled(true);
                opt4.setEnabled(true);

                ans_given = 1;//this is just to move to next question and not ot check whether answer is given

                if(options.getSelection() == null){
                    statement.executeUpdate("insert into quizanswertable(given_answer) VALUES()");//Note: instead of pa[count], use query to add person-answer to database as given answer
                }else {
                    String personAnswer = options.getSelection().getActionCommand();//Note: instead of pa[count], use query to add person-answer to database as given answer
                    statement.executeUpdate("insert into quizanswertable(given_answer) VALUES(personAnswer)");
                }

                //this disables and enables 'next' and 'submit' buttons respectively
                //Note: this code is not repetition
                //As this will be executed if you reach to Q.8 by clicking 'next'
                if(count == 8){
                    next.setEnabled(false);
                    submit.setEnabled(true);
                }

                //this starts the new question move to next question
                start(++count);
            }

            else if(ae.getSource()==lifeline){
                if(count==2||count==4||count==6||count==8||count==9){
                    opt2.setEnabled(false);
                    opt3.setEnabled(false);
                }
                else{
                    opt1.setEnabled(false);
                    opt4.setEnabled(false);
                }
                lifeline.setEnabled(false);
            }

            else if(ae.getSource()==submit){
                ans_given=1;

                if(options.getSelection() == null){
                    statement.executeUpdate("insert into quizanswertable(given_answer) VALUES()");
                }else {
                    String personAnswer = options.getSelection().getActionCommand();//Note: instead of pa[count], use query to add person-answer to database as given answer
                    statement.executeUpdate("insert into quizanswertable(given_answer) VALUES(personAnswer)");
                }

                resultSet.first();
                //to calculate the score by comparing given answers and correct answer
                while(resultSet.next()){
                    String personAnswer=resultSet.getString("given_answer");
                    String correctAnswer= resultSet.getString("answer");
                    if(personAnswer.equals(correctAnswer)){
                        score+=10;
                    }
                }

                this.setVisible(false);//classing quiz frame
                new Score(username, score).setVisible(true);

            }

        }catch(Exception e){
            System.out.println(e);
        }

    }//Action performed method ends here

    //overwriting the paint function in 'repaint()' to change timer on frame(cause frames are static)
    public void paint(Graphics g){
        super.paint(g);



        String time="Time left: "+timer;
        g.setFont(new Font("",Font.BOLD,25));
        g.setColor(Color.RED);

        if(timer>0){
            g.drawString(time,940,400);
        }
        else{
            g.drawString("Times UP !!!",940,400);
        }

        timer--;

        try{
            Thread.sleep(1000);//this will give pause of 1 sec to program
            repaint();//here recursion occurs to keep changing time
        }
        catch(Exception e){
            e.printStackTrace();
        }

        if(ans_given == 1){
            ans_given = 0;
            timer = 15;
        }
        else if(timer < 0){

            timer = 15;//restarting the timer
            //resetting enabling to counter disabling due used lifeline
            opt1.setEnabled(true);
            opt2.setEnabled(true);
            opt3.setEnabled(true);
            opt4.setEnabled(true);


            //Note: this code is not repetition
            //As this will be executed if you reach to Q.8 due to time up
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/quizapp", "root", "ramchandra@123");
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("select * from quiztable,quizanswertable");
                resultSet.next();

                while(resultSet.next()){
                    counter++;
                }

                if (count == 8) {
                    next.setEnabled(false);
                    submit.setEnabled(true);
                    //this disables and enables 'next' and 'submit' buttons respectively
                }

                if (count == 9) {
                    if (options.getSelection() == null) {
                        statement.executeUpdate("insert into quizanswertable(given_answer) VALUES()");
                    } else {
                        String personAnswer = options.getSelection().getActionCommand();//Note: instead of pa[count], use query to add person-answer to database as given answer
                        statement.executeUpdate("insert into quizanswertable(given_answer) VALUES(personAnswer)");
                    }


                    resultSet.first();//bring cursor back to first row

                    //to calculate the score by comparing given answers and answer-array
                    while (resultSet.next()) {
                        String personAnswer = resultSet.getString("given_answer");
                        String correctAnswer = resultSet.getString("answer");
                        if (personAnswer.equals(correctAnswer)) {
                            score += 10;
                        }
                    }

                    this.setVisible(false);//clossing quiz frame
                    new Score(username, score).setVisible(true);

                } else {
                    if (options.getSelection() == null) {
                        statement.executeUpdate("insert into quizanswertable(given_answer) VALUES()");
                    } else {
                        String personAnswer = options.getSelection().getActionCommand();//Note: instead of pa[count], use query to add person-answer to database as given answer
                        statement.executeUpdate("insert into quizanswertable(given_answer) VALUES(personAnswer)");
                    }

                    start(++count);
                }

            }catch(Exception e){
                System.out.println(e);
            }
        }
    }


    //this sets questions and options on the screen
    public void start(int count){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/quizapp","root","ramchandra@123");
            Statement statement= connection.createStatement();
            ResultSet resultSet=statement.executeQuery("select * from quiztable");
            int counter2=0;
            while(counter2<=count ) {
                if (count == counter) {
                    resultSet.next();
                    counter2++;
                }
            }

            System.out.println(count);


                System.out.println(resultSet.getString("que_sentence"));
//            resultSet.getString("que_no")
                qno.setText("" + resultSet.getString("que_no") + "");
                question.setText("" + resultSet.getString("que_sentence") + "");
                opt1.setLabel("" + resultSet.getString("option1") + "");
                opt1.setActionCommand("" + resultSet.getString("option1") + "");
                opt2.setLabel("" + resultSet.getString("option2") + "");
                opt2.setActionCommand("" + resultSet.getString("option2") + "");
                opt3.setLabel("" + resultSet.getString("option3") + "");
                opt3.setActionCommand("" + resultSet.getString("option3") + "");
                opt4.setLabel("" + resultSet.getString("option4") + "");
                opt4.setActionCommand("" + resultSet.getString("option4") + "");
                options.clearSelection();


        }catch(Exception e){
            System.out.println(e);
        }

    }

    public static void main(String[] args) {
        new MyJDBC("").setVisible(true);
    }
}

















//attempt to make use of blunder_answer column
//this is not working

//        opt1.setEnabled(false);
//        opt2.setEnabled(false);
//        opt3.setEnabled(false);
//        opt4.setEnabled(false);
//
//        if(getText(opt1).equals(resultSet.getString("blunder_answer"))||opt1.equals(resultSet.getString("answer"))){
//        opt1.setEnabled(true);
//        }
//        if(opt2.equals(resultSet.getString("blunder_answer"))||opt2.equals(resultSet.getString("answer"))){
//        opt2.setEnabled(true);
//        }
//        if(opt3.equals(resultSet.getString("blunder_answer"))||opt3.equals(resultSet.getString("answer"))){
//        opt3.setEnabled(true);
//        }
//        if(opt4.equals(resultSet.getString("blunder_answer"))||opt4.equals(resultSet.getString("answer"))){
//        opt4.setEnabled(true);
//        }
