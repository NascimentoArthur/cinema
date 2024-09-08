package app;

import java.io.IOException;
import java.io.ObjectInputStream.GetField;
import java.sql.*;
import java.util.Scanner;

import classes.*;

public class Main {

    public static void main(String[] args) {
    	Scanner sc = new Scanner(System.in);
    	int vazio = 1;
    	
    	
    	
    	/*
    	Cliente cliente = new Cliente();
    	cliente.criarCliente();
    	*/
    	    	
    	
    	/*
    	Filme filme = new Filme();
    	filme.criarFilme();
		*/
    	
    	/*
    	Sala sala = new Sala();
    	sala.criarSala();
    	*/
    	
    	/*
    	Sala sala = new Sala();
    	sala.buscarSalas(sc);
    	
    	

    	
    	Sessao sessao = new Sessao();
    	sessao.criaSessao();
    */
    	Cliente cliente = new Cliente();
    	cliente.criaCliente();
    	
    	/*

    	Filme filme = new Filme(vazio);
    	filme = Filme.buscarFilmeEmCartaz();
    	
    	*/
    	//Sessao.buscaSessao();
    	
    	
    	sc.close();
    }
}