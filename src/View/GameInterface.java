/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.Collision;
import Controller.EnemyMoving;
import Controller.GestorEnemigos;
import Controller.GestorNave;
import Model.Nave;
import java.awt.Point;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Godievski
 */
public interface GameInterface extends java.rmi.Remote{
    public boolean getState() throws RemoteException;

    public void initGame(int var1) throws RemoteException;

    public void endGame(int var1) throws RemoteException;

    public void joinGame(Nave var1) throws RemoteException;

    public GestorNave getNaves() throws RemoteException;

    public GestorEnemigos getEnemies() throws RemoteException;

    public Nave createPlayer() throws RemoteException;

    public void moverPlayer(int var1, boolean var2, boolean var3, boolean var4, boolean var5) throws RemoteException;

    public void updateWindowPosition(int var1, Point var2) throws RemoteException;

    public void setFlags(int var1, boolean var2, boolean var3, boolean var4) throws RemoteException;

    public EnemyMoving getEnemyMoving() throws RemoteException;

    public void setEnemyMoving(EnemyMoving var1) throws RemoteException;

    public void newEnemyMoving() throws RemoteException;

    public void enemyMovingStart() throws RemoteException;

    public Collision getCollision() throws RemoteException;

    public void setCollision(Collision var1) throws RemoteException;

    public void newCollision() throws RemoteException;

    public void collisionStart() throws RemoteException;
    
}
