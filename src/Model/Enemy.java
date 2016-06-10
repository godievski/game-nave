/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Controller.GestorNave;
import View.WindowGame;
import java.awt.Graphics;
import java.util.Random;

/**
 *
 * @author Godievski
 */
public class Enemy extends Sprite {
    
    private static final int WIDTH = 18;
    private static final int HEIGHT = 18;
    private static final double VELOCIDAD = 1.5;
    private static final int HP = 3;
    private static final int TIPO_RANDOM = 1;
    private static final int TIPO_NORMAL = 0;
    private static Random random = new Random();
    private final int score;
    private static final int MIN = (Enemy.WIDTH/2 + Nave.WIDTH/2);
    private static final int MAX = (WindowGame.WINDOW_WIDTH - Enemy.WIDTH - Nave.WIDTH/2);
    private int tipo;
    private int idNave;
    
    public Enemy(){
        super(0, 0,VELOCIDAD, WIDTH, HEIGHT, HP);
        int hp_temp = (int)(Math.random()*HP) + HP;
        this.hp = hp_temp;
        this.posX = (int)(Math.random() * (Enemy.MIN - Enemy.MAX)  + Enemy.MAX);
        this.score = hp_temp*10;
        this.tipo = random.nextInt(2);
        this.idNave = -1;
    }
    
    public int getScore(){
        return this.score;
    }
    public void mover(GestorNave naves){
        if (idNave == -1 || naves.get(idNave).getHP()<=0)
            idNave = naves.getRandomId();
        
        if (tipo == TIPO_NORMAL || naves == null)
            this.posY += VELOCIDAD;
        else if (tipo == TIPO_RANDOM)
            this.specialMove(naves.get(idNave));
    }
    
    public void setTipo(int tipo){
        this.tipo = tipo;
    }
    
    public void specialMove(Nave nave){
        this.calcularVector(nave.getPosX(), nave.getPosY());
        this.posY += this.vectorY;
        this.posX += this.vectorX;
    }
    
    @Override
    public void dibujar(Graphics g){
        g.fillOval((int)this.posX,(int) this.posY, this.width, this.height);
    }
}
