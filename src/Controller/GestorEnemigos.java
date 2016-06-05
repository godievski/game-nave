/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import Model.Enemy;
import java.io.Serializable;

/**
 *
 * @author Godievski
 */
public class GestorEnemigos implements Serializable {
    private final ArrayList<Enemy> listaEnemigos;
    
    public GestorEnemigos(){
        this.listaEnemigos = new ArrayList<>();
    }
    
    public Enemy get(int index){
        if (index < listaEnemigos.size()){
            return listaEnemigos.get(index);
        }
        return null;
    }
    public void add(Enemy enemy){
        this.listaEnemigos.add(enemy);
    }
    public void remove(int index){
        this.listaEnemigos.remove(index);
    }
    public void dibujar(Graphics g){
        try {
            g.setColor(Color.BLACK);
            for (int i = 0; i < listaEnemigos.size(); i++){
                Enemy enemy = listaEnemigos.get(i);
                enemy.dibujar(g);
            }
        } catch (Exception e){
            JOptionPane.showMessageDialog(null,e.toString());
        }
    }
    public int size(){
        return this.listaEnemigos.size();
    }
    public void clear(){
        this.listaEnemigos.clear();
    }
}
