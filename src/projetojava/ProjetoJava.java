
package projetojava;

import classes.Tutor;
import classes.Paciente;
import classes.Historico;
import conexao.Conexao;
import gui.FrFuncionario;
import gui.FrInicial;
import gui.FrTutor;

public class ProjetoJava {
    
    public static void main(String[] args) {
        
        FrInicial tela1 = new FrInicial();
        tela1.setVisible(true);
        Conexao c = new Conexao();
        c.getConexao();
        
        
        
    }
    
}
