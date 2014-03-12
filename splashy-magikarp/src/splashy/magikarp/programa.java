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

import javax.swing.JFrame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseMotionListener;
import java.net.URL;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import java.awt.Toolkit;
import java.io.FileReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException; 
import java.util.Vector;
import java.io.BufferedReader;
import java.util.LinkedList;

public class programa extends JFrame implements Runnable, KeyListener,MouseListener,MouseMotionListener {
 
 private static final long serialVersionUID = 1L;
    // Se declaran las variables.
    private int direccion;    // Direccion del elefante
    private int velocidad,aceleracion; // determinado por vidas
    private final int MIN = -6;    //Minimo al generar un numero al azar.
    private final int MAX = 7;    //Maximo al generar un numero al azar.
    private Image dbImage;      // Imagen a proyectar  
    private Image gameover;
    private Image fondo;
    private Graphics dbg;       // Objeto grafico
    private SoundClip yay;    // Objeto AudioClip
    private SoundClip buuu;    // Objeto AudioClip
    private SoundClip bomb;    //Objeto AudioClip
    private SoundClip teleport;
    private Magikarp magikarp;    // Objeto de la clase Bueno
    private Pipeup pipeup;    //Objeto de la clase Malo
    private Pipedown pipedown;
    private int ancho;  //Ancho del elefante
    private int alto;   //Alto del elefante
    private ImageIcon elefante; // Imagen del elefante.
    private int x1; // posicion del mouse en x
    private int y1; // posicion del mouse en y
    private int x_pos;
    private int y_pos;
    private int vidas = 5;
    private int score = 0;
    private boolean pausa = false;
    private boolean clic = false; //para saber cuando hace clic
    private boolean up,down,right,left; //movimiento de teclado
    private boolean pchoco; // bool magikarp choco
    private double angulo; // angulo de la magikarp
    private double px,py; // posicion de la magikarp con formula
    private int intentos;
    private double velocidadx,velocidady;
    private double gravedad = 9.8;
    private double tiempo;
    private boolean btiempo;
    private boolean instrucciones = false;
    private boolean inst = false;
    private boolean pclic = false;
    private boolean inicio = false;
    
    private LinkedList listaup,listadown;
    
    //variables para el manejo de archivos
    private Vector vec;    // Objeto vector para agregar el puntaje.
    private String nombreArchivo;    //Nombre del archivo.
    private String[] arr;    //Arreglo del archivo divido.
    
    private boolean guardar = false; //bool para saber si se quiere guardar el juego
    private boolean cargar = false; //bool para saber si se quiere cargar el juego
    
    
    //Variables de control de tiempo de la animación
    private long tiempoActual;
    private long tiempoInicial;
    
    public programa() {
        init();
        start();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
    }
    
    public void init() {
        
        nombreArchivo = "Puntaje.txt";
        vec = new Vector();
        
        
        direccion = 0;
        this.setSize(350, 630);
        URL eURL = this.getClass().getResource("Imagenes/1.gif");
        int dposy = getHeight() / 2 + getHeight() / 8;
        magikarp = new Magikarp(50, dposy );
        //magikarp.setPosX((int) (getWidth()/2));
        //magikarp.setPosY(getHeight());
        int posrX =  getWidth()/2 ;    //posision x es tres cuartos del applet
        int posrY =   getHeight() ;  //posision y es tres cuartos del applet
        URL rURL = this.getClass().getResource("Imagenes/pipe.png");
        pipeup = new Pipeup(posrX, posrY);
        pipeup.setPosX(pipeup.getPosX() - pipeup.getAncho());
        pipeup.setPosY(pipeup.getPosY() - pipeup.getAlto());
        setBackground(Color.white);
        addKeyListener(this);
        URL goURL = this.getClass().getResource("Imagenes/4.gif");
        gameover = Toolkit.getDefaultToolkit().getImage(goURL);
       
        //Se cargan los sonidos.
        yay = new SoundClip("Sonidos/yay.wav");
        buuu = new SoundClip("Sonidos/buuu.wav");
        teleport = new SoundClip("Sonidos/teleport.wav");
 
        elefante = new ImageIcon(Toolkit.getDefaultToolkit().getImage(eURL));
        ancho = elefante.getIconWidth();
        alto = elefante.getIconHeight();
        //ancho2 = pipeup.getIconWidth();
        // alto2 = pipeup.getIconHeight();
        addMouseListener(this);
        addMouseMotionListener(this); 
        
        URL fondoURL = this.getClass().getResource("Imagenes/fondo1.jpg");
        fondo = Toolkit.getDefaultToolkit().createImage(fondoURL);
        
        // variable de la magikarp
        angulo = (int) ((Math.random() * (60 - 45)) + 45);
 
        //movimiento de magikarp en x
        velocidad = 5;
        
        listaup = new LinkedList();
        listadown = new LinkedList();
        
        
        for(int i = 0; i < 3 ; i++)
        {
            pipeup = new Pipeup(350 + 350 * i, 500);
            listaup.addLast(pipeup);
            
            pipedown = new Pipedown(350 + 350 * i, -200);
            listadown.addLast(pipedown);
            
        }
        
        
        
        //movimiento de magikarp en y
        //py = -5 + 1 * tiempoActual;
    }
 
    public void start() {
        // Declaras un hilo
        Thread th = new Thread(this);
        // Empieza el hilo
        th.start();
    }

    /**
     *
     */
    @Override
    public void run() {
        //Guarda el tiempo actual del sistema 
        
        
         while (vidas > 0) {
            if (!pausa) {
                actualiza();
                checaColision();
                repaint(); // Se actualiza el <code>Applet</code> repintando el contenido.
            }
            try {
                // El thread se duerme.
                Thread.sleep(20);
            } catch (InterruptedException ex) {
                System.out.println("Error en " + ex.toString());
            }

        }

    }
    
    public void actualiza() {
        
        long tiempoTranscurrido = System.currentTimeMillis() - tiempoActual;
        
        //Guarda el tiempo actual
        tiempoActual += tiempoTranscurrido; 
        
        magikarp.actualizaAnimacion(tiempoActual);
        pipeup.actualizaAnimacion(tiempoActual);
        
        
        // tiempo de jframe
        if(btiempo)
        {
           tiempo += .090; 
        }
       
        if(intentos == 3)
        {
            vidas--;
            intentos = 0;
            velocidad += 1;
        }
        
        if(!pausa)
        {
            if(clic)
            {
            
                
        
                //velocidad de y
                velocidady = -5 + 4 * tiempo;
            
                //boolean para tiempo
                btiempo = true;
            
                //actualziacion de posicion
                //magikarp.setPosX(magikarp.getPosX() + (int) velocidadx);
                magikarp.setPosY(magikarp.getPosY() + (int) velocidady);
            } 
        }
        
        
        
        
        
        if(left)
        {
            pipeup.setPosX(pipeup.getPosX() - 5);
            left = false;
        }
        else if(right)
        {
            pipeup.setPosX(pipeup.getPosX() + 5);
            right = false;
        }
        
        //si choca con la ventana se invierte la velocidad en X
        if(pchoco)
        {
            px *= -1;
            pchoco = false;
        }
        
        
        if(inicio)
        {
            for(int i = 0; i < 3 ; i++)
            {
                 pipeup  = (Pipeup) (listaup.get(i));
                 pipeup.setPosX(pipeup.getPosX() - 3);


                 pipedown  = (Pipedown) (listadown.get(i));
                 pipedown.setPosX(pipedown.getPosX() - 3);

            }
        }
        
        
        
    
 }
        
        
    
 
    public void checaColision() {
        
       /*
        if(magikarp.getPosX() + magikarp.getAncho() >= getWidth() || magikarp.getPosX() <= 0)
        {
            pchoco = true;
        }
        
        
         if (magikarp.getPosX() + magikarp.getAncho() >= getWidth()) {
            int posrX = (int) (0);    // posicion en x es un cuarto del applet
            int posrY = (int) (getHeight() / 2 + getHeight() / 8);    // posicion en y es un cuarto del applet
            magikarp.setPosX(posrX);
            magikarp.setPosY(posrY);
            btiempo = false;
            tiempo = 0;
            clic = false;
            // variable de la magikarp
            angulo = (int) ((Math.random() * (60 - 45)) + 45);
 
            //movimiento de magikarp en x
            velocidad = (int) ((Math.random() * (6 - 4)) + 4);
            
            // sonido de buuu
            buuu.play();
        }
        if (magikarp.getPosY() + magikarp.getAlto() >= getHeight()) {
            int posrX = (int) (0);    // posicion en x es un cuarto del applet
            int posrY = (int) (getHeight() / 2 + getHeight() / 8);    // posicion en y es un cuarto del applet
            magikarp.setPosX(posrX);
            magikarp.setPosY(posrY);
            intentos++;
            btiempo = false;
            tiempo = 0;
            clic = false;
            // variable de la magikarp
            angulo = (int) ((Math.random() * (60 - 45)) + 45);
 
            //movimiento de magikarp en x
            velocidad = (int) ((Math.random() * (6 - 4)) + 4);
            
            // sonido de buuu
            buuu.play();
        }
        
         if (magikarp.intersecta(pipeup) && (magikarp.getPosY() + magikarp.getAlto() - 5) < pipeup.getPosY()) {
            int posrX = (int) (0);    // posicion en x es un cuarto del applet
            int posrY = (int) (getHeight() / 2 + getHeight() / 8);    // posicion en y es un cuarto del applet
            magikarp.setPosX(posrX);
            magikarp.setPos   Y(posrY);
            score += 2;
            btiempo = false;
            tiempo = 0;
            clic = false;
            // variable de la magikarp
            angulo = (int) ((Math.random() * (60 - 45)) + 45);
 
            //movimiento de magikarp en x
            velocidad = (int) ((Math.random() * (6 - 4)) + 4);
            //sonido de yay
            yay.play();
        }
        */
        
        if(magikarp.getPosY() <= 0)
        {
            magikarp.setPosY(0);
        }
        
        
        for(int i = 0; i < 3 ; i++)
            {
                 pipeup  = (Pipeup) (listaup.get(i));
                 if(pipeup.getPosX() + pipeup.getAncho() <= 0)
                 {
                     pipeup.setPosX(1050);
                 }


                 pipedown  = (Pipedown) (listadown.get(i));
                 if(pipedown.getPosX() + pipedown.getAncho() <= 0)
                 {
                     pipedown.setPosX(1050);
                 }

            }
   
    }
 
    public void paint(Graphics g) {
        // Inicializan el DoubleBuffer
        if (dbImage == null) 
        {
            dbImage = createImage(this.getSize().width, this.getSize().height);
            dbg = dbImage.getGraphics();
        }
        
        // Actualiza la imagen de fondo.
        dbg.setColor(getBackground());
        dbg.fillRect(0, 0, this.getSize().width, this.getSize().height);
 
        // Actualiza el Foreground.
        dbg.setColor(getForeground());
        paint1(dbg);
 
        // Dibuja la imagen actualizada
        g.drawImage(dbImage, 0, 0, this);
    }
    
    public void paint1(Graphics g) {
        if (vidas>0){
        if (magikarp != null && pipeup != null) {
            //Dibuja la imagen en la posicion actualizada
            g.drawImage(fondo, 0, 0, null);
            g.drawImage(magikarp.getImagenI(), magikarp.getPosX(), magikarp.getPosY(), this);
            g.drawImage(pipeup.getImagenI(), pipeup.getPosX(), pipeup.getPosY(), this);
            
            for (int i = 0; i < listaup.size(); i++) {
            Pipeup pipeup = (Pipeup) listaup.get(i);
            g.drawImage(pipeup.getImagenI(), pipeup.getPosX(), pipeup.getPosY(), this);
            }
            
            for (int i = 0; i < listadown.size(); i++) {
            Pipedown pipedown = (Pipedown) listadown.get(i);
            g.drawImage(pipedown.getImagenI(), pipedown.getPosX(), pipedown.getPosY(), this);
            }
            
            
            
            
            
            
 
        } else {
            //Da un mensaje mientras se carga el dibujo
            g.drawString("No se cargo la imagen..", 20, 20);
        }
        }
        else{
            g.drawImage(gameover, 400, 150, this);
        }
   
        //
        g.drawImage(pipeup.getImagenI(), pipeup.getPosX(), pipeup.getPosY(), this);
        
        
        
        g.setColor(Color.black);
        g.drawString("Vidas: " + vidas, 15, 50);
        g.setColor(Color.black);
        g.drawString("Score: " + score, 70, 50); 
        g.setColor(Color.black);
        g.drawString("Intentos: " + intentos, 140, 50);
        
        /*if (pausa) {
                    g.setColor(Color.white);
                    g.drawString(pipeup.getpausado(), pipeup.getPosX() + pipeup.getAncho() / 3, pipeup.getPosY() + pipeup.getAlto() / 2);
                }
        */
                if (instrucciones ) {
                
               g.setColor(Color.black);
               g.drawString("INSTRUCCIONES: Para empezar el juego dar click a la magikarp. Intenta cachar", 200, 200); 
                    g.drawString(    "la magikarp con la pipeup. Mueve la pipeup con las flechas IZQ y DER", 200, 212); 
                     g.drawString(   "Para pausar el juego presiona 'p' ", 200, 225); 
                     g.drawString(   "Para guardar el juego presiona 'g'", 200, 238); 
                    g.drawString(    "Para cargar el juego presiona 'c'  ", 200, 250); 
            }
 
    }
    /**
     * Metodo que lee a informacion de un archivo y lo agrega a un vector.
     *
     * @throws IOException
     */
    public void leeArchivo()  {
          try
          {
                BufferedReader fileIn;
                try {
                        fileIn = new BufferedReader(new FileReader(nombreArchivo));
                } catch (FileNotFoundException e){
                        File archivo = new File(nombreArchivo);
                        PrintWriter fileOut = new PrintWriter(archivo);
                        fileOut.println("50.0,50.0,45.0,.02,5,.02,50,50,200,200");
                        fileOut.close();
                        fileIn = new BufferedReader(new FileReader(nombreArchivo));
                }
                String dato = fileIn.readLine();
                arr = dato.split (",");
                velocidadx = (Double.parseDouble(arr[0]));
                velocidady = (Double.parseDouble(arr[1]));
                angulo = (Double.parseDouble(arr[2]));
                tiempo = (Double.parseDouble(arr[3]));
                vidas = (Integer.parseInt(arr[4]));
                //dificultad = (Double.parseDouble(arr[5]));
                magikarp.setPosX((Integer.parseInt(arr[5])));
                magikarp.setPosY((Integer.parseInt(arr[6])));
                pipeup.setPosX((Integer.parseInt(arr[7])));
                pipeup.setPosY((Integer.parseInt(arr[8])));
                //perdida = (Integer.parseInt(arr[10]));
                //pico = (Boolean.parseBoolean(arr[11]));
                
                fileIn.close();
          }
          catch(IOException ioe) {
              velocidadx = 0;
              velocidady = 0;
              angulo = 0;
              tiempo = 0;
              vidas = 0;
              //dificultad = 0;
              magikarp.setPosX(0);
              magikarp.setPosY(0);
              pipeup.setPosX(0);
              pipeup.setPosY(0);
              //perdida = 0;
              //pico = false;
              
              
          }
        }
    
    /**
     * Metodo que agrega la informacion del vector al archivo.
     *
     * @throws IOException
     */
    public void grabaArchivo() throws IOException{
        
        //if(guardar) {
            
            
            PrintWriter fileOut = new PrintWriter(new FileWriter(nombreArchivo));
            fileOut.println(""+velocidadx+","+velocidady+","+angulo+","+tiempo+","+vidas+","+magikarp.getPosX()+","+magikarp.getPosY()+","+pipeup.getPosX()+","+pipeup.getPosY());
            fileOut.close();
           
           // guardar = false;
        //}
   	
    }
    
    
    
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_P) //Presiono tecla P
        {    
            pausa = !pausa;
        }
        
        if (e.getKeyCode() == KeyEvent.VK_I) //Presiono tecla I
        {    
            instrucciones = !instrucciones;
            //pausa = !pausa;
        }
        
        if (e.getKeyCode() == KeyEvent.VK_G) //Presiono tecla G
        {  
            try{
			//leeArchivo();    //lee el contenido del archivo
			//vec.add(new Puntaje(nombre,score));    //Agrega el contenido del nuevo puntaje al vector.
			grabaArchivo();    //Graba el vector en el archivo.
		}catch(IOException i){
			System.out.println("Error en " + i.toString());
		}
            //guardar = true;
        }
        
        if (e.getKeyCode() == KeyEvent.VK_C) //Presiono tecla C
        {   
            leeArchivo();
        }
        
        if (e.getKeyCode() == KeyEvent.VK_SPACE) //Presiono tecla C
        {   
           magikarp.setPosY(magikarp.getPosY() - 15);
           tiempo = 0.0;
           clic = true;
           inicio = true; 
           
        }
       
        
        if (e.getKeyCode() == KeyEvent.VK_UP) //Presiono flecha arriba
        {   
            up = true;
	} 
        else if (e.getKeyCode() == KeyEvent.VK_DOWN) //Presiono flecha abajo
                {    
                    down = true;
		} 
        else if (e.getKeyCode() == KeyEvent.VK_LEFT) //Presiono flecha izquierda
                {    
			left = true;
		} 
        else if (e.getKeyCode() == KeyEvent.VK_RIGHT) //Presiono flecha derecha
                {    
			right = true;
		}
        
         
    }
 
    /**
     * Metodo <I>keyTyped</I> sobrescrito de la interface
     * <code>KeyListener</code>.<P>
     * En este metodo maneja el evento que se genera al presionar una tecla que
     * no es de accion.
     *
     * @param e es el <code>evento</code> que se genera en al presionar las
     * teclas.
     */
    @Override
    public void keyTyped(KeyEvent e) {
 
    }
 
    /**
     * Metodo <I>keyReleased</I> sobrescrito de la interface
     * <code>KeyListener</code>.<P>
     * En este metodo maneja el evento que se genera al soltar la tecla
     * presionada.
     *
     * @param e es el <code>evento</code> que se genera en al soltar las teclas.
     */
    @Override
    public void keyReleased(KeyEvent e) {
 
    }
 
     public void mouseClicked(MouseEvent e) {
       
         if(!pausa)
         {
             x1 = e.getX();
        y1 = e.getY();
        if(magikarp.tiene(x1, y1))
        {
            clic = true;
        }
         }
        
        
    }
 
    @Override
    public void mouseEntered(MouseEvent e) {
    }
 
    @Override
    public void mouseExited(MouseEvent e) {
    }
 
    @Override
    public void mousePressed(MouseEvent e) {
 
    }
 
    @Override
    public void mouseReleased(MouseEvent e) {
    }
 
    @Override
    public void mouseMoved(MouseEvent e) {
    }
 
    @Override
    public void mouseDragged(MouseEvent e) {
        
    }
    
        
    
}
