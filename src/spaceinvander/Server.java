/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceinvander;

import Controller.Collision;
import Controller.EnemyMoving;
import Controller.GestorEnemigos;
import Controller.GestorNave;
import View.Game;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author USUARIO
 */
public class Server {
    public static void main(String[] args) {
        /*SERVIDOR*/
        //System.setProperty("java.security.policy", "C:/Temp/CLIENTE1/build/classes/java.policy");
        try {
            GestorNave naves = new GestorNave();
            GestorEnemigos enemies = new GestorEnemigos();
            Game game = new Game(naves,enemies);
            Naming.rebind("//localhost:1099/Naves",naves);
            Naming.rebind("//localhost:1099/Enemies",enemies);
            Naming.rebind("//localhost:1099/Game",game);
            System.out.println("Server created");
            System.out.println(InetAddress.getLocalHost().getHostAddress());
        }catch (RemoteException | MalformedURLException | UnknownHostException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        /**/ 
        /**/
    }
}
