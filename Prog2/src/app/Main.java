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
    	Categoria categoria = new Categoria();
    	categoria.criaCategoria();
    	*/
    
    	/*
    	Filme filme = new Filme();
    	filme.criarFilme();
		*/
    	
    	/*
    	Sala sala = new Sala();
    	sala.criarSala();
    	*/
    	
    	Sala sala = new Sala();
    	sala.buscarSalas(sc);
    	
    	/*Filme.buscarFilme();

    	Filme filme = new Filme(vazio);
    	filme = Filme.buscarFilmeEmCartaz();
    	*/
    	/*
    	Catalogo catalogo = new Catalogo();
    	System.out.println(catalogo.toString());
    	*/
    	sc.close();
    }
}