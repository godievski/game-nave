/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Bullet;
import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Godievski
 */
public class GestorBalas implements Serializable{
    private final ArrayList<Bullet> listaBalas;
    
    public GestorBalas(){
        this.listaBalas = new ArrayList<>();
    }
    public Bullet get(int index){
        if (index < this.listaBalas.size()){
            return this.listaBalas.get(index);
        }
        return null;
    }
    public void add(Bullet bala){
        this.listaBalas.add(bala);
    }
    public void remove(int index){
        this.listaBalas.remove(index);
    }
    public void dibujar(Graphics g){
        try {
            //DIBUJAR BALAS
            g.setColor(Color.WHITE);
            for (int i = 0; i < listaBalas.size(); i++){
                Bullet bala = this.listaBalas.get(i);
                bala.dibujar(g);
            }
        } catch (Exception e){
            JOptionPane.showMessageDialog(null,e.toString());
        }
    }
    public int size(){
        return this.listaBalas.size();
    }
    public void clear(){
        this.listaBalas.clear();
    }
}
