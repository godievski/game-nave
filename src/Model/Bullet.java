/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.awt.Graphics;

/**
 *
 * @author Godievski
 */
public class Bullet extends Sprite {
    
    public static final int WIDTH = 5;
    public static final int HEIGHT = 7;
    private static final double VELOCIDAD = 2;
    private int tipo;
    
    public static final int SPACE = 0;
    public static final int CLICK = 1;
    
    public Bullet(int x, int y){
        super(x, y,VELOCIDAD, WIDTH, HEIGHT,1);
        tipo = SPACE;
    }
    public Bullet(int x, int y, int tipo, double mouseX, double mouseY){
        super(x, y,VELOCIDAD, WIDTH, HEIGHT,1);
        this.tipo = tipo;
        this.calcularVector(mouseX,mouseY);
    }
    
    public int getTipo(){
        return this.tipo;
    }
    
    public void setTipo(int value){
        this.tipo = value;
    }

    public void mover(){
        if (tipo == SPACE)
            this.posY -= this.vel;
        else if (tipo == CLICK){
            this.posY += this.vectorY;
            this.posX += this.vectorX;
        }
    }
    
    public void dibujar(Graphics g){
        g.fillRect((int)this.posX, (int)this.posY, this.width, this.height);
    }
}
