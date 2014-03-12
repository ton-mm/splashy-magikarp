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

public class Pipeup extends Base{
    
    /**
	 * Metodo constructor que hereda los atributos de la clase <code>Base</code>.
	 * @param posX es la <code>posiscion en x</code> del objeto raton.
	 * @param posY es el <code>posiscion en y</code> del objeto raton.
	 * @param image es la <code>imagen</code> del objeto raton.
	 */
    
         private static int conteo = 0;
         
         public static int getConteo()
         {
             return conteo;
         }
         
         public static void setConteo(int a)
         {
             conteo = a;
         }
         
	public Pipeup(int posX,int posY){
		super(posX,posY);
                Image pipe1 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Imagenes/pipeup.png"));
                /*
                Image pipe2 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Imagenes/2.gif"));
                Image pipe3 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Imagenes/3.gif"));
                Image pipe4 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Imagenes/4.gif"));
                Image pipe5 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Imagenes/5.gif"));
                Image pipe6 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Imagenes/6.gif"));
                Image pipe7 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Imagenes/7.gif"));
                Image pipe8 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Imagenes/8.gif"));
                */
                
                anim = new Animacion();
	anim.sumaCuadro(pipe1,100);
        
        /*anim.sumaCuadro(pipe2, 100);
        anim.sumaCuadro(pipe3, 100);
        anim.sumaCuadro(pipe4, 100);
        anim.sumaCuadro(pipe4, 100);
        anim.sumaCuadro(pipe3, 100);
        anim.sumaCuadro(pipe2, 100);
        anim.sumaCuadro(pipe1, 100);
                */
	}
    
}
