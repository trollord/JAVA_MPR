import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.io.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.*;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;

class userDetails {
    String userName;
    int userPass;

    public userDetails(String userName, int userPass) {
        this.userName = userName;
        this.userPass = userPass;
    }

}

class Score {

    String name;
    int points;

    public Score(String name, int points) {
        this.name = name;
        this.points = points;
    }
}

class pointsCompare implements Comparator<Score> {

    @Override
    public int compare(Score s1, Score s2) {
        return s2.points - s1.points;
    }
}

public class RockPaperScissor {

    public static void main(String args[]) throws Exception {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {

            System.out.println(e.getMessage());
        }

        String url = "jdbc:mysql://localhost:3306/gaming_palacio";
        String user = "root";
        String passwd = "Mandar@165";

        /*
         * System.out.println("Enter your name"); String name = sc.nextLine();
         */

        // LOGIN AND REGISTER SYSTEM
        Scanner sc = new Scanner(System.in);
        String name = "";
        String signIn;
        int attemptCount = 0;
        System.out.println(
                "If new user enter register else enter login (new user has to first register and then login again) or enter guest to play as a guest");

        signIn = sc.nextLine();

        if (signIn.equals("register")) {
            FileWriter writerr = new FileWriter("register.txt", true);// change-----------------------------------
            BufferedWriter bufferr = new BufferedWriter(writerr);// change-----------------------------------

            String username;
            int password;
            System.out.println("enter your username");
            username = sc.nextLine();

            System.out.println("enter password (numerical values accepted)");
            password = sc.nextInt();

            bufferr.write(username + " " + password);// change-----------------------------------
            try {

                Connection con = DriverManager.getConnection(url, user, passwd);
                String s1 = "insert into register(u_name,p_pass) values(?,?)";
                PreparedStatement pstmt = con.prepareStatement(s1);
                // String query = "Select name,rollno from stud";
                pstmt.setString(1, username);
                pstmt.setInt(2, password);
                pstmt.executeUpdate();

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            bufferr.newLine();// change-----------------------------------
            bufferr.close();// change-----------------------------------

            signIn = "login";
            System.out.println("thanks for registering. Restart the game and proceed to login");

            System.exit(0);

        }

        else if (signIn.equals("login")) {

            String usertemp;
            int passtemp;

            System.out.println("enter your username");
            usertemp = sc.nextLine();

            System.out.println("enter your password(numeric)");
            passtemp = sc.nextInt();

            BufferedReader readerr = new BufferedReader(new FileReader("register.txt"));// change-----------------------------------
            ArrayList<userDetails> userRecords = new ArrayList<userDetails>();// change-----------------------------------
            String currLine = readerr.readLine();

            while (currLine != null) {

                String[] userDetail = currLine.split(" ");
                String playerName = userDetail[0];
                int playerPass = Integer.valueOf(userDetail[1]);
                userRecords.add(new userDetails(playerName, playerPass));// change-----------------------------------

                currLine = readerr.readLine();// change-----------------------------------

            }

            // System.out.println(userRecords.get(1).userName+"
            // "+userRecords.get(1).userPass);

            boolean found = false;

            for (int i = 0; i < userRecords.size(); i++) {
                if (userRecords.get(i).userName.equals(usertemp) && userRecords.get(i).userPass == passtemp) {
                    name = usertemp;
                    found = true;
                    break;
                }
            }

            if (found == false) {
                System.out.println("entries dont match");
                System.out.println("restart the game and login again with correct credentials");
                System.exit(0);
            }

        }

        else if (signIn.equals("guest")) {

            name = "guest" + (int) (Math.random() * 100000);

        }

        System.out.println("***************__ WELCOME " + name + " TO ROCK,PAPER,SCISSORS!! __********************");

        int opponent_points = 0;
        int my_points = 0;

        while (true) {
            Thread.sleep(2500);

            System.out.println("\n\nEnter Your move (rock,paper,scissor). To quit the game, enter 'quit' ");

            String myMove = sc.nextLine();

            if (myMove.equals("quit")) {
                break;
            }

            if (!myMove.equals("rock") && !myMove.equals("paper") && !myMove.equals("scissor")) {
                System.out.println("your move is invalid");
            }

            else {
                int rand = (int) (Math.random() * 3);

                String[] opponent = new String[] { "rock", "paper", "scissor" };

                if (myMove.equals(opponent[rand])) {
                    System.out.println("It's a tie!!");
                    my_points++;
                    opponent_points++;
                } else if (myMove.equals("rock") && opponent[rand].equals("scissor")
                        || myMove.equals("scissor") && opponent[rand].equals("paper")
                        || myMove.equals("paper") && opponent[rand].equals("rock")) {
                    System.out.println("You won!! wohoo");
                    my_points++;
                } else {

                    System.out.println("you lost! better luck next time");
                    opponent_points++;
                }

            }

        }

        if (my_points > opponent_points) {
            System.out.println("You won the game! your points are: " + my_points);
        } else if (my_points == opponent_points) {
            System.out.println("Its a draw! your points are: " + my_points);
        } else {
            System.out.println("opponent won the game by a margin of: " + (opponent_points - my_points));
        }

        // Entering name and scores of players after every game session

        FileWriter writer = new FileWriter("data.txt", true);// change-----------------------------------
        BufferedWriter buffer = new BufferedWriter(writer);// change-----------------------------------
        buffer.write(name + " " + my_points);// change-----------------------------------
        buffer.newLine();// change-----------------------------------
        buffer.close();// change-----------------------------------
        try {

            Connection con = DriverManager.getConnection(url, user, passwd);
            String s1 = "insert into data(p_name,p_score) values(?,?)";
            PreparedStatement pstmt = con.prepareStatement(s1);
            // String query = "Select name,rollno from stud";
            pstmt.setString(1, name);
            pstmt.setInt(2, my_points);
            pstmt.executeUpdate();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Thread.sleep(1000);

        System.out.println("Enter 1 if you want to see the scoreboard else enter 0 to exit");
        int choice;
        choice = sc.nextInt();

        if (choice == 1) {
            // ScoreBoard of players currently played till now
            BufferedReader reader = new BufferedReader(new FileReader("data.txt"));// change-----------------------------------
            ArrayList<Score> scoreRecords = new ArrayList<Score>();

            String currentLine = reader.readLine();// change-----------------------------------

            while (currentLine != null) {
                String[] ScoreDetails = currentLine.split(" ");
                String playerName = ScoreDetails[0];
                int playerPoints = Integer.valueOf(ScoreDetails[1]);

                scoreRecords.add(new Score(playerName, playerPoints));
                currentLine = reader.readLine();
            }

            Collections.sort(scoreRecords, new pointsCompare());
            // System.out.println(scoreRecords.get(0).name);

            BufferedWriter wr = new BufferedWriter(new FileWriter("outputData.txt"));

            /*
             * for(Score score: scoreRecords){ wr.write(score.name);
             * wr.write(" "+score.points); wr.newLine(); }
             */

            for (int i = 0; i < scoreRecords.size(); i++) {
                wr.write(scoreRecords.get(i).name + " " + scoreRecords.get(i).points + "\n");
            }
            wr.close();

            System.out.println("*********_THE LEADERBOARD_**********");
            System.out.println("        NAME     " + "POINTS");
            System.out.printf("_____________________________________\n");
            String query2 = "SELECT * FROM data ORDER BY p_score ASC";
            String query = "SELECT * FROM data LIMIT 5";

            // String url = "jdbc:mysql://localhost:3306/gaming_palacio";
            // String user = "root";
            // String passwd = "Mandar@165";
            Connection con = DriverManager.getConnection(url, user, passwd);
            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery(query2);
            ResultSet rs2 = stmt.executeQuery(query);
            while (rs2.next()) {
                String one = rs2.getString("p_name");
                Integer two = rs2.getInt("p_score");
                System.out.println(one + "\t--" + two + "\t");
            }

            // for (int i = 0; i < scoreRecords.size(); i++) {

            // System.out.format("%2d) ", (i + 1));
            // System.out.format("%8s", scoreRecords.get(i).name);
            // System.out.format(" %4d", scoreRecords.get(i).points);
            // System.out.println("\n");
            // ;

            // // System.out.println((i+1)+") "+scoreRecords.get(i).name+"
            // // "+scoreRecords.get(i).points);
            // }

            // System.out.println("\n");

            Thread.sleep(100);
            System.out.println("Thanks for playing with us. We hope to see you soon..");

        } else {

            Thread.sleep(1000);
            System.out.println("Thanks for playing with us. We hope to see you soon..");

        }

    }

}