DROP TABLE quiztable;
DROP TABLE quizanswertable;
CREATE TABLE quiztable (
	que_no INT NOT NULL ,
    que_sentence VARCHAR(100),
    option1 VARCHAR(50),
    option2 VARCHAR(50),
    option3 VARCHAR(50),
    option4 VARCHAR(50),
    PRIMARY KEY(que_no)
);
CREATE TABLE quizanswertable(
	que_no INT ,
    answer VARCHAR(50),
    blunder_answer VARCHAR(50),
    given_answer VARCHAR(50),
    PRIMARY KEY(que_no)
);

INSERT INTO quiztable VALUES(1,"Which of the following is used to find and fix bugs in the Java programs?","JVM","JDB","JDK","JRE");
INSERT INTO quiztable VALUES(2,"What is the return type of the hashCode() method in the Object class?","int","Object","long","void");
INSERT INTO quizanswertable VALUES(1,"JDB","JRE","");

SELECT * FROM quizanswertable;
