/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.Collision;
import Controller.EnemyMoving;
import Controller.GestorEnemigos;
import Controller.GestorNave;
import Model.Nave;
import java.awt.Point;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Godievski
 */
public class Game extends UnicastRemoteObject implements GameInterface{
    //THREADS
    private EnemyMoving movimientoEnemigos;
    private Collision controladorColisiones;
    //SPRITES
    public GestorNave naves;
    public GestorEnemigos enemies;
    //FLGAS
    private boolean ready;
    
    public Game() throws java.rmi.RemoteException{
         //SPRITES
        this.naves = new GestorNave();
        this.enemies = new GestorEnemigos();
         //THREADS
        this.movimientoEnemigos = new EnemyMoving(this.naves, this.enemies);
        this.controladorColisiones = new Collision(this.naves, this.enemies);
        this.ready = false;
    }

    @Override
    public boolean getState() throws RemoteException {
        return this.ready;
    }
    
    @Override
    public int getPlayerHP(int index) throws RemoteException{
        return this.naves.get(index).getHP();
    }
    @Override
    public int getPlayerScore(int index) throws RemoteException{
        return this.naves.get(index).getScore();
    }
    
    @Override
    public void initGame(int idPlayer) throws RemoteException {
        Nave n = this.naves.get(idPlayer);
        if (!n.getGestorDisparos().isPlaying()) {
            n.getGestorDisparos().start();
            System.out.println("Player " + idPlayer + " starts thread 1");
        }
        if (!n.getMovimientoBalas().isPlaying()) {
            n.getMovimientoBalas().start();
            System.out.println("Player " + idPlayer + " starts thread 2");
        }
        if (idPlayer != 0) {
            return;
        }
        this.enemies.clear();
        for (int i = 0; i < this.naves.size(); ++i) {
            Nave nave = this.naves.get(i);
            nave.restartNave();
        }
        if (this.movimientoEnemigos == null) {
            this.newEnemyMoving();
        }
        if (this.controladorColisiones == null) {
            this.newCollision();
        }
        if (!this.movimientoEnemigos.isPlaying()) {
            this.enemyMovingStart();
        }
        if (!this.controladorColisiones.isPlaying()) {
            this.collisionStart();
        }
        this.ready = true;
    }
    
     @Override
    public void endGame(int idPlayer) throws RemoteException {
        if (idPlayer != 0) {
            return;
        }
        this.ready = false;
        if (this.movimientoEnemigos.isPlaying()) {
            this.movimientoEnemigos.stopIt();
            try {
                this.movimientoEnemigos.join();
            }
            catch (InterruptedException var2_2) {
                // empty catch block
            }
            this.movimientoEnemigos = null;
        }
        if (this.controladorColisiones.isPlaying()) {
            this.controladorColisiones.stopIt();
            try {
                this.controladorColisiones.join();
            }
            catch (InterruptedException var2_3) {
                // empty catch block
            }
            this.controladorColisiones = null;
        }
    }
    @Override
    public void joinGame(Nave nave) throws RemoteException{
        this.naves.add(nave);
        System.out.println("Nave added");
    }
    
    @Override
    public GestorNave getNaves() throws RemoteException{
        return this.naves;
    }
    
    @Override
    public GestorEnemigos getEnemies() throws RemoteException{
        return this.enemies;
    }
    
    @Override
    public Nave createPlayer() throws RemoteException{
        System.out.println("Player " + this.naves.size() + " created.");
        Nave nave = new Nave(this.naves.size());
        this.joinGame(nave);
        return nave;
    }
    
    @Override
    public void moverPlayer(int index, boolean left, boolean right, boolean up, boolean down) throws RemoteException{
        this.naves.get(index).mover(left, right, up, down);
    }
    @Override
    public void updateWindowPosition(int idPlayer, Point point) throws RemoteException {
        this.naves.get(idPlayer).setWinPos(point);
    }

    @Override
    public void setFlags(int idPlayer, boolean spacePressed, boolean mousePressed, boolean specialShot) throws RemoteException {
        Nave nave = this.naves.get(idPlayer);
        nave.setSpacePressed(spacePressed);
        nave.setMousePressed(mousePressed);
        nave.setSpecialShoot(specialShot);
    }
    @Override
    public EnemyMoving getEnemyMoving() throws java.rmi.RemoteException{
        return this.movimientoEnemigos;
    }
    
    @Override
    public void setEnemyMoving(EnemyMoving em) throws RemoteException {
        this.movimientoEnemigos = em;
    }
    
    @Override
    public void newEnemyMoving() throws RemoteException {
        this.movimientoEnemigos = new EnemyMoving(this.naves, this.enemies);
    }
    @Override
    public void enemyMovingStart() throws RemoteException{
        this.movimientoEnemigos.start();
    }
    @Override
    public Collision getCollision() throws RemoteException {
        return this.controladorColisiones;
    }

    @Override
    public void setCollision(Collision value) throws RemoteException {
        this.controladorColisiones = value;
    }

    @Override
    public void newCollision() throws RemoteException {
        this.controladorColisiones = new Collision(this.naves, this.enemies);
    }

    @Override
    public void collisionStart() throws RemoteException {
        this.controladorColisiones.start();
    }
    public static void main(String[] args) {
        /*SERVIDOR*/
        //System.setProperty("java.security.policy", "C:/Temp/CLIENTE1/build/classes/java.policy");
        try {
            Game gm = new Game();
            Naming.rebind("//localhost/Godie", gm);
            System.out.println("Server created");
            System.out.println(InetAddress.getLocalHost().getHostAddress());
        }catch (RemoteException | MalformedURLException | UnknownHostException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        /**/ 
        /**/
    }

    
}
