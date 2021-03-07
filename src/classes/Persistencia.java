
package classes;

import java.sql.Connection;
import java.sql.DriverManager;

public class Persistencia {    
    private static Connection conexao = null;   
    private Persistencia() {
        
        try{
            conexao = DriverManager.getConnection("jdbc:mysql://localhost:3306/petclin_bd", "root", "");
        } catch(Exception e) {
            System.out.println("Erro ao conectar " + e.getMessage());
        }
    }
    
    public Connection conectar(){
        if(conexao == null) {
            new Persistencia();
        }
        return conexao;
    }
    
    
}




