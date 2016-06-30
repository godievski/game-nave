/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Nave;
import java.awt.Graphics;
import java.rmi.Remote;
import java.rmi.RemoteException;
import javax.swing.JOptionPane;

/**
 *
 * @author USUARIO
 */
public interface IGestorNave extends Remote{
    public Nave get(int index) throws RemoteException;
    public void add(Nave nave) throws RemoteException;
    public void remove(int index) throws RemoteException;
    public void dibujar(Graphics g) throws RemoteException;
    public int size() throws RemoteException;
    public void clear() throws RemoteException;
    
    public boolean isSomeOneAlive() throws RemoteException;
    public boolean areAlive() throws RemoteException;
    public void removeHP()  throws RemoteException;
    public int getRandomId() throws RemoteException;
    public void restart() throws RemoteException;
}
