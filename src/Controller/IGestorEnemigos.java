/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Enemy;
import java.awt.Graphics;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author USUARIO
 */
public interface IGestorEnemigos extends Remote {
    public Enemy get(int index) throws RemoteException;
    public void add(Enemy enemy) throws RemoteException;
    public void remove(int index) throws RemoteException;
    public void dibujar(Graphics g) throws RemoteException;
    public int size() throws RemoteException;
    public void clear() throws RemoteException;
}
