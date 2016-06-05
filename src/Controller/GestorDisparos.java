/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Bullet;
import Model.Nave;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.Serializable;
import java.rmi.RemoteException;

/**
 *
 * @author USUARIO
 */
public class GestorDisparos extends Thread implements Serializable{
    private final static int SLEEP_TIME = 10;
    private final static int DELAY_SHOTING = 150;
    private int time_shoting;
    private final Nave nave;
    private boolean playing;
  
    public GestorDisparos(Nave nave){
        this.time_shoting = 0;
        this.nave = nave;
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
                this.procesarDisparo();
                GestorDisparos.sleep(SLEEP_TIME);
            }
            catch (InterruptedException ex) {
                Logger.getLogger(BulletMoving.class.getName()).log(Level.SEVERE, null, ex);
            }
            catch (RemoteException ex) {
                Logger.getLogger(GestorDisparos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void procesarDisparo() throws RemoteException{
        if (this.nave.getSpecialShoot()){
            if (time_shoting % DELAY_SHOTING == 0){
                this.nave.special();
            }
            this.time_shoting += SLEEP_TIME;
        }
        else if (this.nave.getMousePressed()){
            if (time_shoting % DELAY_SHOTING == 0){
                this.nave.disparar(Bullet.CLICK);
            }
            this.time_shoting += SLEEP_TIME;
        }
        else if (this.nave.getSpacePressed()){
            if (time_shoting % DELAY_SHOTING == 0){
                this.nave.disparar(Bullet.SPACE);
            }
            this.time_shoting += SLEEP_TIME;
        }else{
            this.time_shoting = 0;
        }
    }
}
