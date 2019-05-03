/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maingame;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 *
 * @author Gerryl
 */
public class Players extends Service<Boolean>{
    
    Gameboard player1;
    Gameboard player2;
    Gameboard playersingle;
    
    public Players(Gameboard player1, Gameboard player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.player1.startGame();
        this.player2.startGame();
        
    }
    
    public Players(Gameboard playersingle){
        this.playersingle = playersingle;
        this.playersingle.startGame();
        
    }
    
    @Override
    protected Task<Boolean> createTask() {
        return new Task<Boolean>() {
            @Override
            protected Boolean call() throws Exception {
                if(player1 !=null && player2 != null){
                    twoplayers();
                    
                }else{
                    SinglePlayer();
                }
                return true;
            }
        };
    }
    
    private void twoplayers()throws Exception{
        Thread.sleep(500);
        while (!player1.getGameoverStatus()) {
            player1.turnActive();
        }
        
        while (!player2.getGameoverStatus()) {
            player2.turnActive();
        }
        player2.setDisable(false);
        Thread.sleep(1000);
    }
    
    private void SinglePlayer() throws Exception{
        Thread.sleep(500);
        while (!playersingle.getGameoverStatus()) {
            playersingle.turnActive();
        }
        playersingle.setDisable(false);
        playersingle.getTimer().setTimePause();
        Thread.sleep(1000);
    }
}
