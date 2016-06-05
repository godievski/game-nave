/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Enemy;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import static java.lang.Thread.sleep;

/**
 *
 * @author Godievski
 */
public class EnemyMoving extends Thread implements Serializable{
    private static final int SLEEP_TIME = 15;
    private static final int NEW_ENEMY_TIME = 900;
    private static int contador = 0;
    private boolean playing;
    private final GestorNave naves;
    private final GestorEnemigos enemies;
    
    public EnemyMoving(GestorNave naves, GestorEnemigos enemies){
        this.playing = false;
        this.enemies = enemies;
        this.naves = naves;
    }
    
    public void stopIt(){
        this.playing = false;
    }
    
    public boolean isPlaying(){
        return this.playing;
    }
    
    @Override
    public void run() {
        playing = true;
        int num = 0;
        while(playing){
            try {
                if (contador % NEW_ENEMY_TIME == 0){
                    Enemy enemigo = new Enemy();
                    this.enemies.add(enemigo);
                    System.out.println("Enemy added " + num++);
                }

                for (int i = 0; i < this.enemies.size(); i++){
                    Enemy enemigo = this.enemies.get(i);
                    if (enemigo != null){
                            enemigo.mover(naves.get(0));
                    }
                        
                }
                sleep(SLEEP_TIME);
                contador += SLEEP_TIME;
            } catch (InterruptedException ex) {
                Logger.getLogger(BulletMoving.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
