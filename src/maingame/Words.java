/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maingame;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Gerryl
 */
public class Words {
    private ArrayList<String> WordList;
    private ArrayList<String> WordListCopy;
    private char[] WordinChars;
    protected Random random;

    public Words(ArrayList<String> wordList) {
        this.WordList = wordList;
        WordListCopy = new ArrayList<>(WordList);
        random = new Random();
    }
    
    public char[] Newword(){
        int number = random.nextInt(WordListCopy.size()-1);
        
        WordinChars = WordListCopy.get(number).toCharArray();
        System.out.println("Word chosen "+WordListCopy.get(number)+" number: "+number);
        WordListCopy.remove(number);
        return WordinChars;
    }
    
    public void Resetwords(){
        WordListCopy.clear();
        WordListCopy = new ArrayList<>(WordList);
    }
}
