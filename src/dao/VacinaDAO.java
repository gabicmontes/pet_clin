
package dao;

import gui.FrVacina;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Persistencia;
import model.Vacina;

public class VacinaDAO {
    Vacina vacinaEditando;
    private List<Vacina> lista;
    PreparedStatement ps = null;
    ResultSet rs = null;    
    
    public List<Vacina> getLista() {
        return lista;
    }
    public void setLista(List<Vacina> lista) {
        this.lista = lista;
    }
    
    public VacinaDAO() throws SQLException {
        this.vacinaEditando = null;
        this.lista = new ArrayList<Vacina>();
        this.iniciaLista();
    }  
    
    
    public void iniciaLista() throws SQLException {
        try {
            ps = Persistencia.conectar().prepareStatement("Select * from vacina");
            rs = ps.executeQuery(); 
            
            while(rs.next()) {
                String dados[] = {rs.getString("idVacina"), rs.getString("nomeVacina"), rs.getString("precoVacina"), rs.getString("validadeVacina"), rs.getString("descricaoVacina")}; 
                Vacina v = new Vacina();  
                v.setId(Integer.parseInt(dados[0]));                   
                v.setNome(dados[1]);
                v.setPreco(Float.parseFloat(dados[2]));
                
                SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");         
                Date dataFormatada = formato.parse(dados[3]);                 
                v.setValidade(dataFormatada);
                
                v.setDescricao(dados[4]); 
                
                lista.add(v);
            }           
        } catch (SQLException ex) {
            Logger.getLogger(FrVacina.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(VacinaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void salvar(Vacina v) {
        
        getLista().add(v);
        try {
            ps = Persistencia.conectar().prepareStatement("Insert into vacina (nomeVacina, precoVacina, validadeVacina, descricaoVacina) values (?,?,?,?)");
                
            ps.setString(1, v.getNome());
            ps.setFloat(2, v.getPreco());
            ps.setString(3, (v.getValidade()));
            ps.setString(4, v.getDescricao());
            
            ps.executeUpdate();
                               
        
            } catch (SQLException ex) {
                Logger.getLogger(FrVacina.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("foi n");
            }
            
    }
    public void salvar(Vacina v, int id) {
        try { 
                
            ps = Persistencia.conectar().prepareStatement("update vacina set nomeVacina = ? , precoVacina = ?, validadeVacina = ?, descricaoVacina = ? where idVacina = ? ");
                    
            getLista().add(v);
                
            ps.setString(1, v.getNome());
            ps.setFloat(2, v.getPreco());
            ps.setString(3, (v.getValidade()));
            ps.setString(4, v.getDescricao());
            ps.setInt(5, id);
                
            ps.executeUpdate();
                
            setLista(new ArrayList<>());
            this.iniciaLista();
            
        } catch (SQLException ex) {
            Logger.getLogger(FrVacina.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void excluir(int id) {
        try {
            ps = Persistencia.conectar().prepareStatement("delete from vacina where idVacina = ? ");
            ps.setInt(1, id);
            ps.executeUpdate();
            lista = new ArrayList<>();
            this.iniciaLista();
                    
        } catch (SQLException ex) {
            Logger.getLogger(FrVacina.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
