/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Controller.BulletMoving;
import Controller.GestorBalas;
import Controller.GestorDisparos;
import View.WindowGame;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import javax.swing.JOptionPane;

/**
 *
 * @author Godievski
 */
public class Nave extends Sprite{
    
    //CONSTANT
    protected static final int WIDTH = 20;
    protected static final int HEIGHT = 20;
    private static final double VELOCIDAD = 2.2;
    private static final Color[] COLORES = {Color.CYAN,Color.GREEN,Color.BLUE,Color.ORANGE};
    public static final int HP = 4;
    public static final int POSX_INI = (int)((WindowGame.WINDOW_WIDTH - WIDTH)/2);
    public static final int POSY_INI = WindowGame.WINDOW_HEIGHT - HEIGHT - 20;
   
    //ATTRIBUTES
    private final GestorBalas balas = new GestorBalas();
    private int score = 0;
    private Point winPos;
    private int id;
    private Color color;
    private boolean spacePressed;
    private boolean mousePressed;
    private boolean specialShoot;
    //THREADS
    private GestorDisparos gestorDisparos;
    private BulletMoving movimientoBalas;
    
    public Nave(int id){
        super(POSX_INI, POSY_INI, VELOCIDAD, Nave.WIDTH, Nave.HEIGHT, Nave.HP);
        this.id = id;
        this.spacePressed = false;
        this.mousePressed = false;
        this.specialShoot = false;
        this.gestorDisparos = new GestorDisparos(this);
        this.movimientoBalas = new BulletMoving(this.balas);
        this.color = COLORES[id];
    }
     public void setWinPos(Point value) {
        this.winPos = value;
    }

    public Point getWinPos() {
        return this.winPos;
    }

    public GestorDisparos getGestorDisparos() {
        return this.gestorDisparos;
    }

    public BulletMoving getMovimientoBalas() {
        return this.movimientoBalas;
    }
    public void restartNave() {
        this.balas.clear();
        this.hp = HP;
        this.posX = POSX_INI;
        this.posY = POSY_INI;
        this.score = 0;
        this.spacePressed = false;
        this.mousePressed = false;
        this.specialShoot = false;
    }

    public boolean getSpecialShoot() {
        return this.specialShoot;
    }

    public void setSpecialShoot(boolean value) {
        this.specialShoot = value;
    }

    public boolean getMousePressed() {
        return this.mousePressed;
    }

    public void setMousePressed(boolean value) {
        this.mousePressed = value;
    }

    public boolean getSpacePressed() {
        return this.spacePressed;
    }

    public void setSpacePressed(boolean value) {
        this.spacePressed = value;
    }

    public int getID() {
        return this.id;
    }

    public GestorBalas getBalas(){
        return this.balas;
    }
    
    public void setScore(int value){
        this.score = value;
    }
    
    public int getScore(){
        return this.score;
    }
    
    public void incrementScore(int value){
        this.score += value;
    }
    
    public void mover(boolean left, boolean right, boolean up, boolean down){
        
        double x=0, y=0;
        if (left) x -=1 ;
        if (right) x +=1;
        if (up) y-=1;
        if (down) y+=1;
        double r = Math.sqrt(y*y + x*x);
        y = y*VELOCIDAD;
        x = x*VELOCIDAD;
        if (r != 0){
            y /= r;
            x /= r;
        }
        if (left)
            if (this.posX >= 0)
                this.posX += x;
        if (right)
            if (this.posX <= ((WindowGame.WINDOW_WIDTH) - (int)(Nave.WIDTH) ))
                this.posX += x;   
        if (up)
            if (this.posY >= WindowGame.WINDOW_HEIGHT/2)
                this.posY += y;
        if (down)
            if (this.posY <= POSY_INI)
                this.posY += y;
    }
    
    @Override
    public void dibujar(Graphics g){
        try {
            //DIBUJAR NAVE
            g.setColor(this.color);
            g.fillRect((int)posX,(int) posY, WIDTH, HEIGHT);
            
            //DIBUJAR BALAS
            balas.dibujar(g);
            
        } catch (Exception e){
            JOptionPane.showMessageDialog(null,e.toString());
        }
    }
    
    public void disparar(int tipo){
        Point mousePoint = MouseInfo.getPointerInfo().getLocation();
        int mouseX = (int)(mousePoint.getX() - this.winPos.getX());
        int mouseY = (int)(mousePoint.getY() - this.winPos.getY());
        if (mouseY < this.posY && tipo == Bullet.CLICK){
            balas.add(new Bullet((int)(this.posX) + this.width/2 - Bullet.WIDTH/2,
                (int)(this.posY) - Bullet.HEIGHT,
                tipo,mouseX,mouseY));
        } else if (tipo == Bullet.SPACE){
            balas.add(new Bullet((int)(this.posX) + this.width/2 - Bullet.WIDTH/2,
                (int)(this.posY) - Bullet.HEIGHT,
                tipo,mouseX,mouseY));
        }
    }
    public void special(){
        Bullet bullet = new Bullet ((int)(this.posX) + this.width/2 - Bullet.WIDTH/2,
                (int)(this.posY) - Bullet.HEIGHT);
        double xFin = bullet.posX + Math.sin(10 * Math.PI / 180);
        double yFin = bullet.posY - Math.cos(10 * Math.PI / 180);
        bullet.calcularVector(xFin, yFin);
        bullet.setTipo(Bullet.CLICK);
        balas.add(bullet);
        
        balas.add(new Bullet((int)(this.posX) + this.width/2 - Bullet.WIDTH/2,
                (int)(this.posY) - Bullet.HEIGHT));
        
        Bullet bullet2 = new Bullet ((int)(this.posX) + this.width/2 - Bullet.WIDTH/2,
                (int)(this.posY) - Bullet.HEIGHT);
        double xFin2 = bullet2.posX + Math.sin(-10 * Math.PI / 180);
        double yFin2 = bullet2.posY - Math.cos(-10 * Math.PI / 180);
        bullet2.calcularVector(xFin2, yFin2);
        bullet2.setTipo(Bullet.CLICK);
        balas.add(bullet2);
        
    }
}
