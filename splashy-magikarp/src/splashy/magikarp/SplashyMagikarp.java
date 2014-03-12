/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package splashy.magikarp;

import javax.swing.JFrame;

/**
 *
 * @author tonystark
 */
public class SplashyMagikarp {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        programa juego = new programa();
        juego.setVisible(true);
        juego.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
}
