/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.IGestorEnemigos;
import Controller.IGestorNave;
import Model.Enemy;
import Model.Nave;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author USUARIO
 */
public class DrawingSpace extends Canvas implements Serializable{
    private GameInterface gm;
    private final int idPlayer;
    private Image dibujoAux;
    private Graphics gAux;
    private Dimension dimAux;
    private final Dimension dimPanel;
    private final WindowGame wg;
    
    public DrawingSpace (WindowGame wg, GameInterface gm, int idPlayer, Dimension d){
        this.initComponents();
        this.gm = gm;
        this.idPlayer = idPlayer;
        this.setSize(d);
        this.wg = wg;
        this.dimPanel = d;
    }
    private void initComponents(){
        this.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                formMouseReleased(evt);
            }
        });
    }
    
    private void formMousePressed(java.awt.event.MouseEvent evt) {                                  
        // TODO add your handling code here:
        try {
            Nave nave = wg.naves.get(this.idPlayer);
            if (!nave.getSpacePressed() && !nave.getSpecialShoot()) {
                this.wg.setMousePressed(true);
            }
        }
        catch (RemoteException ex) {
            Logger.getLogger(DrawingSpace.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            this.gm.setFlags(this.idPlayer, this.wg.getSpacePressed(), this.wg.getMousePressed(), this.wg.getSpecialShoot());
        }
        catch (RemoteException ex) {
            Logger.getLogger(WindowGame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }                                 
    private void formMouseReleased(java.awt.event.MouseEvent evt) {                                   
        // TODO add your handling code here:
        this.wg.setMousePressed(false);
        try {
            this.gm.setFlags(this.idPlayer, this.wg.getSpacePressed(), this.wg.getMousePressed(), this.wg.getSpecialShoot());
        }
        catch (RemoteException ex) {
            Logger.getLogger(WindowGame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }  
    
    @Override
    public void update(Graphics g){
        paint(g);
    }
    
    @Override
    public void paint(Graphics g){
        if (gAux == null || dimAux == null || dimPanel.width != dimAux.width ||
                dimPanel.height != dimAux.height){
            dimAux = dimPanel;
            dibujoAux = createImage(dimAux.width,dimAux.height);
            gAux = dibujoAux.getGraphics();
        }

        //FONDO
        gAux.setColor(Color.MAGENTA);
        gAux.fillRect(0, 0, WindowGame.WINDOW_WIDTH, WindowGame.WINDOW_HEIGHT);
        
        try {
            if (this.gm != null) {
                Nave nave = this.wg.naves.get(this.idPlayer);
                int hp = nave.getHP();
                int score = nave.getScore();
                this.gAux.setColor(Color.WHITE);
                this.gAux.drawString("SCORE: " + score, 10, 20);
                this.gAux.drawString("LIFE: " + hp, WindowGame.WINDOW_WIDTH - 50, 20);
                
                dibujarNaves(wg.naves,gAux);
            } else {
                System.out.println("Naves nulas");
            }
        }
        catch (RemoteException ex) {
            Logger.getLogger(DrawingSpace.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error de conexion Naves");
        }
        
        
        if (wg.enemies != null) {
            dibujarEnemigos(wg.enemies,gAux);
        }
        
        g.drawImage(this.dibujoAux, 0, 0, this);
        g.dispose();
    }
    
    private void dibujarNaves(IGestorNave naves, Graphics g){
        try {
            for (int i = 0; i < naves.size(); i++){
                Nave nave = naves.get(i);
                if (nave.isAlive())
                    nave.dibujar(g);
            }
        } catch (Exception e){
            JOptionPane.showMessageDialog(null,e.toString());
        }
    }
    private void dibujarEnemigos(IGestorEnemigos enemies, Graphics g){
        try {
            g.setColor(Color.BLACK);
            for (int i = 0; i < enemies.size(); i++){
                Enemy enemy = enemies.get(i);
                enemy.dibujar(g);
            }
        } catch (Exception e){
            JOptionPane.showMessageDialog(null,e.toString());
        }
    }
}
