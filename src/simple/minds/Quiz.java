package simple.minds;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Quiz extends JFrame {

    JButton next,lifeline, submit;
    JLabel qno,question;
    JRadioButton opt1, opt2, opt3, opt4;
    ButtonGroup options;
    public static int count=0, timer=15, score=0, ans_given=0;
    String q[][]= new String[10][5];
    String qa[]= new String[10];
    String pa[]= new String[10];

    //constructor
    String username;
    Quiz(String username){
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


        //questions data
        q[0][0] = "Which is used to find and fix bugs in the Java programs.?";
        q[0][1] = "JVM";
        q[0][2] = "JDB";
        q[0][3] = "JDK";
        q[0][4] = "JRE";

        q[1][0] = "What is the return type of the hashCode() method in the Object class?";
        q[1][1] = "int";
        q[1][2] = "Object";
        q[1][3] = "long";
        q[1][4] = "void";

        q[2][0] = "Which package contains the Random class?";
        q[2][1] = "java.util package";
        q[2][2] = "java.lang package";
        q[2][3] = "java.awt package";
        q[2][4] = "java.io package";

        q[3][0] = "An interface with no fields or methods is known as?";
        q[3][1] = "Runnable Interface";
        q[3][2] = "Abstract Interface";
        q[3][3] = "Marker Interface";
        q[3][4] = "CharSequence Interface";

        q[4][0] = "In which memory a String is stored, when we create a string using new operator?";
        q[4][1] = "Stack";
        q[4][2] = "String memory";
        q[4][3] = "Random storage space";
        q[4][4] = "Heap memory";

        q[5][0] = "Which of the following is a marker interface?";
        q[5][1] = "Runnable interface";
        q[5][2] = "Remote interface";
        q[5][3] = "Readable interface";
        q[5][4] = "Result interface";

        q[6][0] = "Which keyword is used for accessing the features of a package?";
        q[6][1] = "import";
        q[6][2] = "package";
        q[6][3] = "extends";
        q[6][4] = "export";

        q[7][0] = "In java, jar stands for?";
        q[7][1] = "Java Archive Runner";
        q[7][2] = "Java Archive";
        q[7][3] = "Java Application Resource";
        q[7][4] = "Java Application Runner";

        q[8][0] = "Which of the following is a mutable class in java?";
        q[8][1] = "java.lang.StringBuilder";
        q[8][2] = "java.lang.Short";
        q[8][3] = "java.lang.Byte";
        q[8][4] = "java.lang.String";

        q[9][0] = "Which of the following option leads to the portability and security of Java?";
        q[9][1] = "Bytecode is executed by JVM";
        q[9][2] = "The applet makes the Java code secure and portable";
        q[9][3] = "Use of exception handling";
        q[9][4] = "Dynamic binding between objects";


        //answers data
        qa[0] = "JDB";
        qa[1] = "int";
        qa[2] = "java.util package";
        qa[3] = "Marker Interface";
        qa[4] = "Heap memory";
        qa[5] = "Remote interface";
        qa[6] = "import";
        qa[7] = "Java Archive";
        qa[8] = "java.lang.StringBuilder";
        qa[9] = "Bytecode is executed by JVM";


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
    }

    //cta
    public void ActionPerformed(ActionEvent ae){
        if(ae.getSource()==next){
            repaint();
            opt1.setEnabled(true);
            opt2.setEnabled(true);
            opt3.setEnabled(true);
            opt4.setEnabled(true);

            ans_given = 1;//this is just to move to next question and not ot check whether answer is given
            if(options.getSelection() == null){
                pa[count] = "";
            }else {
                pa[count] = options.getSelection().getActionCommand();
            }

            //this disables and enables 'next' and 'submit' buttons respectively
            //Note: this code is not repetition
            //As this will be executed if you reach to Q.8 by clicking 'next'
            if(count == 8){
                next.setEnabled(false);
                submit.setEnabled(true);
            }

            //this helps to move to next question
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
                pa[count] = "";
            }else {
                pa[count] = options.getSelection().getActionCommand();
            }

            //to calculate the score by comparing given answers and answer-array
            for(int i =0 ; i < pa.length ; i++){
                if(pa[i].equals(qa[i])){
                    score+=10;
                }
            }
            this.setVisible(false);//classing quiz frame
            new Score(username, score).setVisible(true);
        }
    }

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
            repaint();//this will keep on displaying new timer value
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

            //this disables and enables 'next' and 'submit' buttons respectively
            //Note: this code is not repetition
            //As this will be executed if you reach to Q.8 due to time up
            if(count == 8){
                next.setEnabled(false);
                submit.setEnabled(true);
            }
            if(count == 9){
                if(options.getSelection() == null){
                    pa[count] = "";
                }else {
                    pa[count] = options.getSelection().getActionCommand();
                }

                //to calculate the score by comparing given answers and answer-array
                for(int i =0 ; i < pa.length ; i++){
                    if(pa[i].equals(qa[i])){
                        score+=10;
                    }
                }
                this.setVisible(false);//classing quiz frame
                new Score(username, score).setVisible(true);
            }
            else{
                if(options.getSelection() == null){
                    pa[count] = "";
                }else {
                    pa[count] = options.getSelection().getActionCommand();
                }
                count++;
                start(count);
            }
        }
    }


    //this sets questions and options on the screen
    public void start(int count){
        qno.setText(""+(count+1)+".");
        question.setText(q[count][0]);
        opt1.setLabel(q[count][1]);
        opt1.setActionCommand(q[count][1]);
        opt2.setLabel(q[count][2]);
        opt2.setActionCommand(q[count][2]);
        opt3.setLabel(q[count][3]);
        opt3.setActionCommand(q[count][3]);
        opt4.setLabel(q[count][4]);
        opt4.setActionCommand(q[count][4]);
        options.clearSelection();
    }

    public static void main(String[] args) {
        new Quiz("").setVisible(true);
    }
}
