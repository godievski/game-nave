/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Bullet;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Godievski
 */
public class BulletMoving extends Thread implements Serializable{
    
    public GestorBalas listaBalas;
    private static final int SLEEP_TIME = 5;
    private boolean playing;
    
    public BulletMoving(GestorBalas listaBalas){
        this.listaBalas = listaBalas;
        this.playing = false;
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
        while(playing){
            try {
                for (int i = 0; i < listaBalas.size(); i++){
                    try{
                        Bullet bala = listaBalas.get(i);
                    if (bala != null)
                        bala.mover();
                    } catch (java.lang.IndexOutOfBoundsException ex){
                        // empty catch block
                    }
                }   
                sleep(SLEEP_TIME);
            } catch (InterruptedException ex) {
                Logger.getLogger(BulletMoving.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
