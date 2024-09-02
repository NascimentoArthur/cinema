package app;

import java.io.IOException;
import java.io.ObjectInputStream.GetField;
import java.sql.*;

public class Main {

    public static void main(String[] args) {
         
    	Connection connection = getConnection();
    	
    }
    
    public static Connection getConnection(){
    	// Agora conecte ao banco de dados
        Connection connection = null ;
        
        String url = "jdbc:mysql://localhost:3306/cinema";
        String username = "root"; // Substitua pelo seu usuário
        String password = "root"; // Substitua pela sua senha
    	
    	try {
    		connection = DriverManager.getConnection(url, username, password);
    		System.out.println("conetou");
			return connection;
    	}
    	catch (SQLException e) {
    		e.printStackTrace();
    		return null;
    	}
    }
}