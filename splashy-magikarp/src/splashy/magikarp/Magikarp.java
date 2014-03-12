/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package splashy.magikarp;

/**
 *
 * @author santoscr92
 */

import java.awt.Image;
import java.awt.Toolkit;

public class Magikarp extends Base{
    
    /**
	 * Metodo constructor que hereda los atributos de la clase <code>Base</code>.
	 * @param posX es la <code>posiscion en x</code> del objeto elefante.
	 * @param posY es el <code>posiscion en y</code> del objeto elefante.
	 * @param image es la <code>imagen</code> del objeto elefante.
	 */
         
        private static final String desaparece = "DESAPARECE";
        private static final String pausado = "PAUSADO";  
        
       /* public getpausado()
        {
            return pausado;
        }
        */
    
	public Magikarp(int posX,int posY)
        {
		super(posX,posY);	
                Image magikarp1 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Imagenes/1.gif"));
                Image magikarp2 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Imagenes/2.gif"));
                Image magikarp3 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Imagenes/3.gif"));
                Image magikarp4 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Imagenes/4.gif"));
                Image magikarp5 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Imagenes/5.gif"));
                Image magikarp6 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Imagenes/6.gif"));
                Image magikarp7 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Imagenes/7.gif"));
                Image magikarp8 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Imagenes/8.gif"));
                
                
                anim = new Animacion();
	anim.sumaCuadro(magikarp1,100);
        anim.sumaCuadro(magikarp2, 100);
        anim.sumaCuadro(magikarp3, 100);
        anim.sumaCuadro(magikarp4, 100);
        anim.sumaCuadro(magikarp4, 100);
        anim.sumaCuadro(magikarp3, 100);
        anim.sumaCuadro(magikarp2, 100);
        anim.sumaCuadro(magikarp1, 100);
	}
        
        public boolean tiene (int posX,int posY)
        {
            return getPerimetro().contains(posX, posY);
        }
    
}
