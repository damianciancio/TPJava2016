package logic;
import java.util.Random;

import data.*;
import entities.*;
import util.ErrorConexionException;
import util.PersonajeState;

public class ControladorPelea {
	public PersonajeLuchando p1;
	public PersonajeLuchando p2;
	
	public PersonajeLuchando getP1() {
		return p1;
	}
	public void setP1(PersonajeLuchando p1) {
		this.p1 = p1;
	}
	public PersonajeLuchando getP2() {
		return p2;
	}
	public void setP2(PersonajeLuchando p2) {
		this.p2 = p2;
	}
	public PersonajeLuchando getTurnoDe() {
		return turnoDe;
	}
	public void setTurnoDe(PersonajeLuchando turnoDe) {
		this.turnoDe = turnoDe;
	}
	public PersonajeLuchando getEsperando() {
		return esperando;
	}
	public void setEsperando(PersonajeLuchando esperando) {
		this.esperando = esperando;
	}

	public PersonajeLuchando turnoDe;
	public PersonajeLuchando esperando;
	
	public ControladorPelea(Personaje pe1, Personaje pe2)
	{
		/*recibe los dos personajes elegidos y y crea dos personajeLuchando, despues
		 * le da el primer turno a alguno de forma random
		 */
		p1 = new PersonajeLuchando(pe1);
		p2 = new PersonajeLuchando(pe2);
		
		if(new Random().nextDouble()>0.5)
		{
			turnoDe = p1;
			esperando = p2;
		}
		else
		{
			turnoDe = p2;
			esperando = p1;
		}
		
		
	}
	public boolean atacar(int cantPtos)
	{
		/*
		 * resultado esta en true si el oponente no lo evadio, el metodo atacar de 
		 * Personaje se encarga de restar los puntos
		 */
		boolean resultado;
		if(turnoDe.getEnergiaActual()<=cantPtos)
		{
			resultado = turnoDe.atacar(esperando, cantPtos);
		}
		else
			resultado =  false;
		return resultado;
	}
	
	public void defender()
	{
		turnoDe.defender();
	}
	
	public Personaje cambiarDeTurno() throws ErrorConexionException, Exception
	{
		/*
		 * si el personaje que estaba en estado en espera esta sin vida, se asigna 
		 * el otro como ganador y se guarda la lucha en la base de datos, devuelve el ganador para
		 * mostrarlo en la interfaz como ganador
		 */
		if(esperando.getEstado() == PersonajeState.SIN_VIDA)
		{
			
			new LuchaAdapter().guardar(turnoDe.getP(), esperando.getP());
			return turnoDe.getP();
		}
		else
		{
			PersonajeLuchando aux = turnoDe;
			turnoDe = esperando;
			esperando = aux;
			return null;
		}
		
	}
}
