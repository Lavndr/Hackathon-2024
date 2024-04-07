import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.Popup;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.PopupMenu;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class UserFrame {

    private ArrayList<Integer> ansNums = new ArrayList<Integer>();

    private JButton easyB, medB, hardB;
    private int numbers;

    private int winCount = 0;
    
    private int num1; 
    private int num2;

    private JFrame frame;
    private JPanel startingPanel;
    //converted JPanel to layeredpane
    private JPanel gamePanel;   
    private JPanel mathPanel; 
    private JLabel eqProblem;
    private JPanel winPanel; 

    private JTabbedPane pane; 
    private ImageIcon map;
    private ArrayList<JButton> answerButtons = new ArrayList<JButton>(); 


    private ArrayList<JButton> Problems = new ArrayList<JButton>();
    private ArrayList<Boolean> isChallenge = new ArrayList<Boolean>();

    private ImageIcon backgroundMath; 

    private Image congratsImg; 

    private Image startPage;
    
    private int pos = 0;
    
    private Boolean isFirstRed = false; 
    
    
    public UserFrame() {

        congratsImg = (new ImageIcon("Congrats.png")).getImage();
        congratsImg = congratsImg.getScaledInstance(1500, 800, Image.SCALE_DEFAULT);
        winPanel = new JPanel() {
            @Override
            public void paintComponent(Graphics g){
            super.paintComponent(g);
            g.drawImage(congratsImg, 0, 0, this);
        }
        }; 


        pane = new JTabbedPane(); 


        frame = new JFrame(); 
        frame.setTitle("Find The Treasure");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(1920, 1080);
        frame.setLocationRelativeTo(null);

        //starting background
        startPage = (new ImageIcon("Starting_Page.png")).getImage();
        startPage = startPage.getScaledInstance(1440, 760, Image.SCALE_SMOOTH);

        startingPanel = new JPanel(new GridLayout(1, 3, 10, 10)) {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(startPage, 0, 0, this);
            }
        };
        
        map = new ImageIcon("Map_Trails.png");
        Image mapImg = map.getImage();
        Image mapScale = mapImg.getScaledInstance(1420, 780, Image.SCALE_DEFAULT);
        map = new ImageIcon(mapScale);
        
        //change info here
        gamePanel = new JPanel(null) {
            @Override
            public void paintComponent(Graphics g){
            super.paintComponent(g);
            g.drawImage(map.getImage(), 0, 0, this);
        }
        };
        gamePanel.setBounds(0, 0, 500, 500);

        
        backgroundMath = new ImageIcon("Math_problem_background.jpeg");
        Image mathImg = backgroundMath.getImage(); 
        mathImg = mathImg.getScaledInstance(1920, 1080, Image.SCALE_SMOOTH);
        backgroundMath = new ImageIcon(mathImg);

        //create panel to do math 
        mathPanel = new JPanel(null) {
            @Override 
            public void paintComponent(Graphics g) {
                super.paintComponent(g); 
                g.drawImage(backgroundMath.getImage(), 0, 0, this); 
            }
        };



        //create panel to do math 
        mathPanel = new JPanel(null) {
            @Override 
            public void paintComponent(Graphics g) {
                super.paintComponent(g); 
                g.drawImage(backgroundMath.getImage(), 0, 0, this); 
            }
        };  

        
        easyB = StartingButton(null, startingPanel);
        medB = StartingButton(null, startingPanel);
        hardB = StartingButton(null, startingPanel);

        easyB.setOpaque(true);
        easyB.setContentAreaFilled(true);
        easyB.setBorderPainted(false);
        easyB.setBackground(new Color(0, 0, 0, 5));
        
        medB.setOpaque(true);
        medB.setContentAreaFilled(true);
        medB.setBorderPainted(false);
        medB.setBackground(new Color(0, 0, 0, 5));
        
        hardB.setOpaque(true);
        hardB.setContentAreaFilled(true);
        hardB.setBorderPainted(false);
        hardB.setBackground(new Color(0, 0, 0, 5));
        

        pane.add(startingPanel, 0);
        pane.add(gamePanel, 1);
        pane.add(mathPanel, 2); 
        pane.add(winPanel, 3);
        
        pane.setEnabledAt(0, true);
        pane.setEnabledAt(1, false); 
        pane.setEnabledAt(2, false);
        
        executeMathProblem();

        frame.add(pane);


    }


    //
    public void show() {
        frame.setVisible(true);
    }


    public JButton StartingButton(String title, JPanel p) {
        JButton button = new JButton(title) {
            {
                setSize(100, 100);
                setFont(new Font("BM Hanna 11yrs Old Regular", Font.BOLD, 24));
            }
        };

        button.addActionListener(new ActionListener() {
            @Override 
            public void actionPerformed(ActionEvent e) {
                //configures number of problems
                if(title == "Easy Mode") 
                numbers = 10; 
                else if (title == "Medium Mode") 
                numbers = 15;
                else 
                numbers = 20;

                //clear panel to prepare for game
                pane.setEnabledAt(0, false);
                pane.setEnabledAt(1, true);
                pane.setSelectedIndex(1);
                createLevel(numbers);
                
            }
        }); 
        
        
        p.add(button);
        return button;
    }


    //develops level based on difficulty level
    public void createLevel(int difficulty) {
        //configure hashmap here to determine amount of levels
        for (int i = 0; i < 31; i++) {

            if (i < difficulty) {
                isChallenge.add(true); 
            }
            else 
            isChallenge.add(false);

            Problems.add(new JButton());

        }

        Collections.shuffle(isChallenge);



        for(int jBut = 0; jBut < isChallenge.size(); jBut++) {
            if(isChallenge.get(jBut) == true) {
            Problems.get(jBut).setBackground(new Color(255, 0, 0, 40));
            }
            else 
            Problems.get(jBut).setBackground(new Color(0, 255, 0, 40));
            
            Problems.get(jBut).setOpaque(true);
            Problems.get(jBut).setContentAreaFilled(true);
            Problems.get(jBut).setBorderPainted(false);
            Problems.get(jBut).setSize(75, 75);
            moveButtons();
            gamePanel.add(Problems.get(jBut));


            if (isChallenge.get(jBut) == true) {


                Problems.get(jBut).addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {

                        JButton j = (JButton) e.getSource();
                        for (int numBut = 0; numBut < Problems.size(); numBut++) {
                            if (j.equals(Problems.get(numBut))) {
                                for(int i = numBut + 1; i < Problems.size(); i++){
                                    if(isChallenge.get(i) == true){
                                        Problems.get(i).setEnabled(true);
                                        break;
                                    }
                                }
                                
                            }
                            
                        }
                        
                        pane.setEnabledAt(2, true);
                        pane.setEnabledAt(1, false);
                        pane.setSelectedIndex(2); 
    
    
                        //call executeMathProblem();
                        configureMathProblem();
                        j.setEnabled(false);

                        j.setBackground(new Color(0, 255, 0, 40));

                    }
                    
                });
            }
            else {
                Problems.get(jBut).setEnabled(false);
            }

            
        } 
        for (int setEn = 0; setEn < isChallenge.size(); setEn++) {
            if (isChallenge.get(setEn).equals(true) && isFirstRed.equals(false)) {
                Problems.get(setEn).setEnabled(true);
                isFirstRed = true;
            }
            else if (isChallenge.get(setEn).equals(true) && isFirstRed.equals(true)) {
                Problems.get(setEn).setEnabled(false);
            }

        }


    }

    public void configureMathProblem() {
        Random randInt = new Random(); 

        if(numbers == 10)
        {num1 = randInt.nextInt(6) + 1;
         num2 = randInt.nextInt(6) + 1; 
        } 
        else if(numbers == 15) 
        {num1 = randInt.nextInt(12) + 1; 
         num2 = randInt.nextInt(12) + 1;
        }
        else if(numbers == 20) 
        {num1 = randInt.nextInt(20) + 1;
         num2 = randInt.nextInt(20) + 1;
        }
        eqProblem.setText(num1 + " x " + num2 + " = "); 


        for(int num = 0; num < 3; num++) {
            answerButtons.get(num).setText(Integer.toString(ansNums.get(num))); //add text to the button corresponding to the value 
        }
        answerButtons.get(randInt.nextInt(3)).setText(Integer.toString(num1 * num2)); 
        
    }


    public void executeMathProblem() {
        //mathgamehere
        //one label 
        //3 buttons 

        Random randInt = new Random(); 


        if(numbers ==  10)
        {num1 = randInt.nextInt(6) + 1;
         num2 = randInt.nextInt(6) + 1; 
        } 
        else if(numbers == 15) 
        {num1 = randInt.nextInt(12) + 1; 
         num2 = randInt.nextInt(12) + 1;
        }
        else if(numbers == 20) 
        {num1 = randInt.nextInt(20) + 1;
         num2 = randInt.nextInt(20) + 1;
        }

 
        eqProblem = new JLabel(num1 + " x " + num2 + " =" ); //find 2 numbers 
        eqProblem.setFont(new Font("Arial", Font.BOLD, 200));
        eqProblem.setLocation(40, -100);
        eqProblem.setSize(1000, 1000);
        mathPanel.add(eqProblem);
        
        for(int i = 0; i < 3; i++) {
            int randomNum = (num1 * num2 + randInt.nextInt(10 + 10) - 10); 
            if(ansNums.contains(randomNum) || randomNum < 1) {
                i--; 
                
            }
            else {
                ansNums.add(randomNum);
            }
            
        }


        for(int num = 0; num < 3; num++) {
            answerButtons.add(new JButton(Integer.toString(ansNums.get(0)))); //add text to the button corresponding to the value 
            answerButtons.get(num).setSize(450, 225);
            answerButtons.get(num).setLocation(840, 25 + 250 * num);
            answerButtons.get(num).setFont(new Font("Arial", Font.BOLD, 75));
            mathPanel.add(answerButtons.get(num));

            answerButtons.get(num).addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //correspond to value of the button
                    JButton yuh = (JButton) e.getSource();
                    if (Integer.parseInt(yuh.getText()) == num1 * num2) {
                        winCount++;
                        pane.setEnabledAt(1, true); 
                        pane.setEnabledAt(2, false); 
                        pane.setSelectedIndex(1);
                        pos++;
                    } 
                    if (winCount == numbers) {
                        System.out.println("YOU WINN!!!!");
                        pane.setEnabledAt(1, false); 
                        pane.setEnabledAt(3, true); 
                        pane.setSelectedIndex(3);
                    }
                }
                
            });
        }

        answerButtons.get(randInt.nextInt(3)).setText(Integer.toString(num1 * num2)); 



    }


    public void moveButtons() {
        Problems.get(0).setLocation(371, 610); 
        Problems.get(1).setLocation(470, 566);
        Problems.get(2).setLocation(535, 515);//
        Problems.get(3).setLocation(553, 424);//
        Problems.get(4).setLocation(508, 367);// 
        Problems.get(5).setLocation(426, 325);//
        Problems.get(6).setLocation(325, 285);//
        Problems.get(7).setLocation(247, 225);
        Problems.get(8).setLocation(235, 146);//
        Problems.get(9).setLocation(290, 70);//
        Problems.get(10).setLocation(400, 50);//
        Problems.get(12).setLocation(780, 170);//
        Problems.get(11).setLocation(720, 130);//
        Problems.get(13).setLocation(800, 281);
        Problems.get(14).setLocation(720, 360);
        Problems.get(15).setLocation(725, 449);
        Problems.get(16).setLocation(780, 527);
        Problems.get(17).setLocation(1070, 648);//
        Problems.get(18).setLocation(1150, 665);//
        Problems.get(19).setLocation(1250, 630);//
        Problems.get(20).setLocation(1300, 550);//
        Problems.get(21).setLocation(1320, 440);
        Problems.get(22).setLocation(1300, 350);
        Problems.get(23).setLocation(1207, 280);
        Problems.get(24).setLocation(1150, 200);//
        Problems.get(25).setLocation(1050, 164);//<----
        Problems.get(26).setLocation(957, 123);
        Problems.get(27).setLocation(927, 59);
        Problems.get(28).setLocation(986, 8);
        Problems.get(29).setLocation(1074, 18);
        Problems.get(30).setLocation(1200, 36);

    }

}
