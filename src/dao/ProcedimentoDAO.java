
package dao;

import gui.FrProcedimento;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Persistencia;
import model.Procedimento;

public class ProcedimentoDAO {
    
    
    Procedimento procedimentoEditando;
    private List<Procedimento> lista;
    PreparedStatement ps = null;
    ResultSet rs = null;    
    
    public List<Procedimento> getLista() {
        return lista;
    }
    public void setLista(List<Procedimento> lista) {
        this.lista = lista;
    }
    
    public ProcedimentoDAO() throws SQLException{
        this.procedimentoEditando = null;
        this.lista = new ArrayList<Procedimento>();
        this.iniciaLista();
    }
    
    public void iniciaLista() throws SQLException {
        try {
            ps = Persistencia.conectar().prepareStatement("Select * from procedimentos");
            rs = ps.executeQuery(); 
            
            while(rs.next()) {
                String dados[] = {rs.getString("idProcedimentosClinicos"), rs.getString("nomeProcedimento"), rs.getString("precoProcedimento"), rs.getString("observacoesProcedimento")}; 
                Procedimento v = new Procedimento();                     
                v.setNome(dados[1]);
                v.setPreco(Float.parseFloat(dados[2]));
                v.setDescricao(dados[3]);
                v.setId(Integer.parseInt(dados[0]));
                getLista().add(v);
            }           
        } catch (SQLException ex) {
            Logger.getLogger(FrProcedimento.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void salvar(Procedimento p) {
            
            getLista().add(p);
        
            try {
                ps = Persistencia.conectar().prepareStatement("Insert into procedimentos (nomeProcedimento, precoProcedimento, observacoesProcedimento) values (?,?,?)");
                
                ps.setString(1, p.getNome());
                ps.setFloat(2, p.getPreco());
                ps.setString(3, p.getDescricao());
                
                ps.executeUpdate();
                               
        
            } catch (SQLException ex) {
                Logger.getLogger(FrProcedimento.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("foi n");
            }
            
    }
    public void salvar(Procedimento p, int id) {
        try { 
                
            ps = Persistencia.conectar().prepareStatement("update procedimentos set nomeProcedimento = ? , precoProcedimento = ?, observacoesProcedimento = ? where idProcedimentosClinicos = ? ");
                
            getLista().add(p);
                
            ps.setString(1, p.getNome());
            ps.setFloat(2, p.getPreco());
            ps.setString(3, p.getDescricao());
            ps.setInt(4, id);
                
            ps.executeUpdate();
                
            setLista(new ArrayList<>());
            this.iniciaLista();
            
        } catch (SQLException ex) {
            Logger.getLogger(FrProcedimento.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void excluir(int id) {
        try {
            ps = Persistencia.conectar().prepareStatement("delete from procedimentos where idProcedimentosClinicos = ? ");
            ps.setInt(1, id);
            ps.executeUpdate();
            setLista(new ArrayList<>());
            this.iniciaLista();
                    
        } catch (SQLException ex) {
            Logger.getLogger(FrProcedimento.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
