/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceinvander;

import View.WindowGame;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Godievski
 */
public class SpaceInvander {
    /**
     * @param args the command line arguments
     * @throws java.net.UnknownHostException
     */
    public static void main(String[] args) throws UnknownHostException {
        /*USER-CREA-PARTIDA*/
        try {
            WindowGame windowGame = new WindowGame(args[0],args[1]);
            windowGame.startGame();
        } catch (RemoteException | NotBoundException | MalformedURLException ex) {
            Logger.getLogger(SpaceInvander.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
