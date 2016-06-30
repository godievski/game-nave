/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;


import Model.Nave;
import java.awt.Graphics;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JOptionPane;

/**
 *
 * @author Godievski
 */
public class GestorNave extends UnicastRemoteObject implements IGestorNave,Serializable{
    private ArrayList<Nave> listNave;
    private ArrayList listNaveVivas; 
    private static Random random = new Random();
    
    public GestorNave() throws RemoteException{
        this.listNave = new ArrayList<>();
        this.listNaveVivas = new ArrayList();
    }
    
    public Nave get(int index) throws RemoteException{
        if (index < listNave.size()){
            return listNave.get(index);
        }
        return null;
    }
    public void add(Nave nave) throws RemoteException{
        this.listNave.add(nave);
        this.listNaveVivas.add(nave.getID());
    }
    public void remove(int index) throws RemoteException{
        this.listNave.remove(index);
    }
    public void dibujar(Graphics g) throws RemoteException{
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
    public int size() throws RemoteException{
        return this.listNave.size();
    }
    
    public void clear() throws RemoteException{
        this.listNave.clear();
    }
    
    public boolean isSomeOneAlive() throws RemoteException{
        boolean alive = false;
        for (int i = 0; i < listNave.size(); i++){
            Nave nave = listNave.get(i);
            alive = alive || (nave.getHP() > 0);
        }
        return alive;
    }
    public boolean areAlive() throws RemoteException{
        boolean alive = true;
        for (int i = 0; i < listNave.size(); i++){
            Nave nave = listNave.get(i);
            alive = alive && (nave.getHP() > 0);
        }
        return alive;
    }
    public void removeHP() throws RemoteException{
        for (int i = 0; i < this.listNave.size(); ++i) {
            Nave nave = this.listNave.get(i);
            nave.removeHP();
            if (nave.getHP() <= 0){
                int pos = getPositionByID(nave.getID());
                this.listNaveVivas.remove(pos);
            }
        }
    }
    public int getRandomId() throws RemoteException{
        int rand = random.nextInt(this.listNaveVivas.size());
        return (int) this.listNaveVivas.get(rand);
    }
    
    private int getPositionByID(int id) throws RemoteException{
        for (int i = 0; i < this.listNaveVivas.size(); i++){
            if ((int)listNaveVivas.get(i) == id)
                return i;
        }
        return -1;
    }
    public void restart() throws RemoteException{
        this.listNaveVivas.clear();
        for (Nave nave : this.listNave) {
            nave.restartNave();
            listNaveVivas.add(nave.getID());
        }
    }
}
