/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.GestorEnemigos;
import Controller.GestorNave;
import Model.Nave;
import View.DrawingSpace;
import View.GameInterface;
import View.WindowGame;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.ImageObserver;
import java.io.PrintStream;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author USUARIO
 */
public class DrawingSpace extends Canvas {
    private GameInterface gm;
    private int idPlayer;
    private Image dibujoAux;
    private Graphics gAux;
    private Dimension dimAux;
    private final Dimension dimPanel;
    private WindowGame wg;
    
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
            Nave nave = this.gm.getNaves().get(this.idPlayer);
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
            if (this.gm != null && this.gm.getNaves() != null) {
                Nave nave = this.gm.getNaves().get(this.idPlayer);
                if (nave != null) {
                    this.gAux.setColor(Color.WHITE);
                    this.gAux.drawString("SCORE: " + nave.getScore(), 10, 20);
                    this.gAux.drawString("LIFE: " + nave.getHP(), WindowGame.WINDOW_WIDTH - 50, 20);
                }
                this.gm.getNaves().dibujar(this.gAux);
            } else {
                System.out.println("Naves nulas");
            }
        }
        catch (RemoteException ex) {
            Logger.getLogger(DrawingSpace.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error de conexion");
        }
        
        try {
            if (this.gm != null && this.gm.getEnemies() != null) {
                this.gm.getEnemies().dibujar(this.gAux);
            } else {
                System.out.println("Enemigos nulas");
            }
        }
        catch (RemoteException ex) {
            Logger.getLogger(DrawingSpace.class.getName()).log(Level.SEVERE, null, ex);
        }
        g.drawImage(this.dibujoAux, 0, 0, this);
        g.dispose();
    }
}
