package classes;

import java.util.Scanner;
import java.time.format.DateTimeFormatter;

public class Config {
	
	//Criando Scanner para uso das classes
	Scanner sc = new Scanner(System.in);
	
	//Formatação de data para sessões
	DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("HH:mm");
}
