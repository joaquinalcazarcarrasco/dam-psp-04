package com.pspentrega04;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Principal {
	

	public static void main(String[] args) {
		
		//Creo instancia de Marco, capa y añado la capa al marco
		Marco marco = new Marco();
		Capa capa = new Capa();
		marco.add(capa);
		
		//Hago visible el marco y le digo que cuando le dé a cerrar me pare la aplicación.
		marco.setVisible(true);
		marco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
	}//main

}//Principal

class Marco extends JFrame{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Marco() {
		
		//Obtengo el tipo de pantalla que esté usando la persona usuaria
		Toolkit pantalla = Toolkit.getDefaultToolkit();
		
		//Obtengo las dimensiones de la pantalla
		Dimension dimensiones = pantalla.getScreenSize();	
		int anchuraPantalla = dimensiones.width;
		int alturaPantalla = dimensiones.height;
		
		//Indico el la posición (centrado en pantalla) y el tamaño de mi frame
		setBounds(anchuraPantalla/4, alturaPantalla/4, 200, 250);
		
		//título
		setTitle("Contadores");
		
		//Indico que no se pueda redimensionar el frame
		setResizable(false);	
		
	}
}//Marco

class Capa extends JPanel implements ActionListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton botonFin1 = new JButton("Finalizar hilo 1");
	private JButton botonFin2 = new JButton("Finalizar hilo 2");
	private Font fuente = new Font("Arial", Font.BOLD, 25);
	private HiloContador h1;
	private HiloContador h2;
	

	public Capa() {
		
		//Añado los botones
		add(botonFin1);
		add(botonFin2);
		
		//Les incovoco el manejador de eventos sobre Capa
		botonFin1.addActionListener(this);
		botonFin2.addActionListener(this);
		
		//Inicializo los hilos y los ejecuto
		h1 = new HiloContador();
		h2 = new HiloContador();
		h1.start();
		h2.start();

	}
	
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		//Borro trozo del frame
		g.clearRect(5, 5, 190, 210);
		
		//Ajusto el color de fondo
		setBackground(Color.pink);
		
		//Borde marco
		g.drawRect(5, 5, 190, 210);
		
		//configuro fuente y escribo la salida de cada hilo
		g.setFont(fuente);
		g.drawString("Hilo 1: " + h1.getContador().toString(), 20, 130);
		g.drawString("Hilo 2: " + h2.getContador().toString(), 20, 150);
				
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		//Obtengo el botón sobre el que se ha realizado el evento
		JButton pulsado = (JButton) e.getSource();
		
		//Según sea el 1 o el 2 se parará un hilo u otro. También se cambiará el texto del botón
		if(pulsado == botonFin1) {
			
			botonFin1.setText("Finalizado hilo 1");
			h1.pararHilo();
			
			
		}else if(pulsado == botonFin2) {
		
			botonFin2.setText("Finalizado hilo 2");
			h2.pararHilo();
				
			
		}
		
	}
	
	
	//Clase interna hilo 
	class HiloContador extends Thread{
		
		//Atributos
		private Integer contador;
		private boolean parar = false;
		
		//Constructor
		public HiloContador() {
			
			contador = 0;
		}
		
		//getter
		public Integer getContador() {
			return contador;
		}		
		
		
		@Override
		public void run() {
			
			while(!parar) {
				
				try {
					
					Thread.sleep(300);
					
				}catch(InterruptedException e) {
					
					e.printStackTrace();
				}
			
				//Volver a pintar con la informaicón actualizada
				repaint();
				contador++;//incremento contador
				
			}
			
		}
		
		//Método para parar los hilos en lugar de método stop() ya obsoleto por no ser seguro.
		public void pararHilo() {
			
			parar = true;
		}
		
	}//HiloContador
	
	
}//Capa
