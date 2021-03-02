
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map; 
import java.util.Scanner;
import java.util.Set;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Mohamed Omar
 */
public class WordCounterGui extends JFrame implements ActionListener {
    int amountOfWords = 0;
    
    private JButton whichFile = new JButton("Which File?");
    private static JLabel totalCount = new JLabel("Total Word Count");
    private static JLabel mostUsedWords = new JLabel("5 Most Used Words");
    private static JLabel leastUsedWords = new JLabel("5 Least Used Words");
    
        
        public WordCounterGui(){
            this.setTitle("Word Counter");
            this.setBounds(600, 300, 600, 600);
            this.getContentPane().setLayout(null);
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            //setting up my Which File Button
            this.whichFile.setBounds(40, 400, 400, 100);
            this.getContentPane().add(whichFile);
            this.whichFile.addActionListener(this);

            //setting up my Word Count Label
            this.totalCount.setBounds(40, 350, 350, 50);
            this.getContentPane().add(totalCount);

             //setting up my Word Count Label
            this.leastUsedWords.setBounds(40, 150, 350, 50);
            this.getContentPane().add(leastUsedWords);

             //setting up my Word Count Label
            this.mostUsedWords.setBounds(40, 250, 450, 50);
            this.getContentPane().add(mostUsedWords);
        }
    
    
    
        public HashMap WordCount(File input){
           HashMap<String,Integer> myHashMap = new HashMap();
           Scanner scan = null;
           int size = 0;
           String textSize = "";
           try{
               scan = new Scanner(input);
           }
           catch (Exception e){
           }
           
           while(scan.hasNext()){
                String word = scan.next();               
                String myWord = DeleteCharachters(word);
               
                if(!myWord.equalsIgnoreCase("")){
                    Integer count = myHashMap.get(myWord);
                    size++;
                    if(count == null){
                        myHashMap.put(myWord, 1);
                    }
                    else{
                        Integer updateCount = count+1;
                        myHashMap.put(myWord, updateCount);
                    }
                }  
            }
            amountOfWords = size;
            textSize = "Total Word Count = " + size;
            totalCount.setText(textSize);
            inOrder(myHashMap);
            return myHashMap;
}
       
       public String DeleteCharachters(String Word){
           Word = Word.toLowerCase(); //make it lower case
    //deletes Periods
        if(Word.contains(".")){
            Word = Word.replace(".", "");
        }
    //deletes Commas
        if(Word.contains(",")){
            Word = Word.replace(",", "");
        }
    //deletes Exclamation marks
        if(Word.contains("!")){
            Word = Word.replace("!", "");
        }  
    //deletes Parenthesis
        if(Word.contains("(")){
            Word = Word.replace("(", "");
        }  
        if(Word.contains(")")){
            Word = Word.replace(")", "");
        } 
        
        if(Word.contains("[")){
            Word = Word.replace("[", "");
        } 
        
        if(Word.contains("]")){
            Word = Word.replace("]", "");
        } 
        
        //deletes question marks
        if(Word.contains("?")){
            Word = Word.replace("?", "");
        }
        
        if(Word.contains(":")){
            Word = Word.replace(":", "");
        }
        
        if(Word.contains(";")){
            Word = Word.replace(";", "");
        } 
       return Word;
        
    }

       /**
        * Part 1.) getting my hash map copied into an array list
        * Part 2.) Goes through the array list and take the smallest value and putting the value in an In Order array then deleting that value
        * Part 3.) Printing the In Order Array List, and the 5 most and least used words.
        * 
        * @param myHashMap 
        */
    static private void inOrder(HashMap myHashMap){    
        //part1
        Set<Map.Entry<String, Integer>> entries = myHashMap.entrySet();
        ArrayList<Map.Entry<String,Integer>> MyArrayList= new ArrayList<>();
        ArrayList<Map.Entry<String,Integer>> InOrderList= new ArrayList<>();
        for(Map.Entry<String, Integer> myWord:entries){
            MyArrayList.add(myWord);
        }
        
        //part2
        for(int j = 0; j<MyArrayList.size(); j++) {
            Map.Entry<String,Integer> Mylowest = MyArrayList.get(j);
            
            for(int i = 0; i<MyArrayList.size(); i++){
                if( Mylowest.getValue() > MyArrayList.get(i).getValue()){
                    Mylowest = MyArrayList.get(i);
                }
            }
            InOrderList.add(Mylowest);
            MyArrayList.remove(Mylowest);
            j--; 
        }
        
    //Part 3.) 
        if(InOrderList.size()>=5){
            String MostUsed = "";
            String LeastUsed = "";
            for(int i = InOrderList.size()-1; i>InOrderList.size() - 6; i--){
                MostUsed += InOrderList.get(i) + "   \n";    
            }
            for(int i = 0; i<5; i++){
                LeastUsed +=  InOrderList.get(i) + "   \n";
            }
            leastUsedWords.setText(LeastUsed);
            mostUsedWords.setText(MostUsed);
        }else{ 
            System.out.println("Not enough words"); 
        }
    
    }
    
    private void TheFileButtonWasPushed(){
        System.out.println("Hit the file button");
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("TEXT FILES" , "txt" , "text"));
        System.out.println("I created the file chooser");
        int chooserSuccess = chooser.showOpenDialog(null);
        System.out.println(" I opened the file chooser, it returned " + chooserSuccess);
       
        if(chooserSuccess == JFileChooser.APPROVE_OPTION){
            File chosenFile = chooser.getSelectedFile();
            WordCount(chosenFile);
            System.out.println("you chose the file " + chosenFile.getAbsolutePath());
            System.out.println("you chose the file " + chosenFile.getName());
        }
        else{
            System.out.println("you hit cancel");
        }
    }
        
 
       
    @Override
    public void actionPerformed(ActionEvent ae) {
        System.out.println("The action event is " + ae);
        if (ae.getActionCommand().equals("Which File?")){
            this.TheFileButtonWasPushed();       
        } 
    }
       
       
}
