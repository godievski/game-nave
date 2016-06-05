/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.awt.Graphics;
import java.io.Serializable;

/**
 *
 * @author Godievski
 */
public abstract class Sprite implements Serializable{
    protected double posX;
    protected double posY;
    protected int width;
    protected int height;
    protected double vel;
    protected int hp;
    protected double vectorX;
    protected double vectorY;

    public Sprite(int x, int y, double vel, int width, int height, int hp){
        this.posX = x;
        this.posY = y;
        this.vel = vel;
        this.width = width;
        this.height = height;
        this.hp = hp;
    }
    
    public void setVector(double x, double y){
        this.vectorX = x;
        this.vectorY = y;
    }
    
    public boolean isAlive(){
        return this.hp > 0;
    }
    
    public void calcularVector(double xFinal, double yFinal){
        double y = yFinal - this.posY;
        double x = xFinal - this.posX;
        double r = Math.sqrt(y*y + x*x);
        y = vel * y/r;
        x = vel * x/r;
        this.vectorX = x;
        this.vectorY = y;
    }
    
    public int getHP(){
        return this.hp;
    }
    public void setHP(int value){
        this.hp = value;
    }
    public void removeHP(){
        this.hp -= 1;
    }
    public double getPosX(){
        return this.posX;
    }
    public int getPosXInt(){
        return (int) this.posX;
    }
    public void setPosX(double value){
        this.posX = value;
    }
    public double getPosY(){
        return this.posY;
    }
    public int getPosYInt(){
        return (int)this.posY;
    }
    public void setPosY(double value){
        this.posY = value;
    }
    public int getWidth(){
        return this.width;
    }
    public int getHeight(){
        return this.height;
    }
    abstract public void dibujar(Graphics g);
}
