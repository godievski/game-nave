/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;


import Model.Nave;
import java.awt.Graphics;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JOptionPane;

/**
 *
 * @author Godievski
 */
public class GestorNave implements Serializable{
    private ArrayList<Nave> listNave;
    private ArrayList listNaveVivas; 
    private static Random random = new Random();
    
    public GestorNave(){
        this.listNave = new ArrayList<>();
        this.listNaveVivas = new ArrayList();
    }
    
    public Nave get(int index){
        if (index < listNave.size()){
            return listNave.get(index);
        }
        return null;
    }
    public void add(Nave nave){
        this.listNave.add(nave);
        this.listNaveVivas.add(nave.getID());
    }
    public void remove(int index){
        this.listNave.remove(index);
    }
    public void dibujar(Graphics g){
        try {
            for (int i = 0; i < listNave.size(); i++){
                Nave nave = listNave.get(i);
                if (nave.isAlive())
                    nave.dibujar(g);
            }
        } catch (Exception e){
            JOptionPane.showMessageDialog(null,e.toString());
        }
    }
    public int size(){
        return this.listNave.size();
    }
    
    public void clear(){
        this.listNave.clear();
    }
    
    public boolean isSomeOneAlive(){
        boolean alive = false;
        for (int i = 0; i < listNave.size(); i++){
            Nave nave = listNave.get(i);
            alive = alive || (nave.getHP() > 0);
        }
        return alive;
    }
    public boolean areAlive(){
        boolean alive = true;
        for (int i = 0; i < listNave.size(); i++){
            Nave nave = listNave.get(i);
            alive = alive && (nave.getHP() > 0);
        }
        return alive;
    }
    public void removeHP() {
        for (int i = 0; i < this.listNave.size(); ++i) {
            Nave nave = this.listNave.get(i);
            nave.removeHP();
            if (nave.getHP() <= 0){
                int pos = getPositionByID(nave.getID());
                this.listNaveVivas.remove(pos);
            }
        }
    }
    public int getRandomId(){
        int rand = random.nextInt(this.listNaveVivas.size());
        return (int) this.listNaveVivas.get(rand);
    }
    
    private int getPositionByID(int id){
        for (int i = 0; i < this.listNaveVivas.size(); i++){
            if ((int)listNaveVivas.get(i) == id)
                return i;
        }
        return -1;
    }
    public void restart(){
        this.listNaveVivas.clear();
        for (Nave nave : this.listNave) {
            nave.restartNave();
            listNaveVivas.add(nave.getID());
        }
    }
}
