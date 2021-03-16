
package dao;

import gui.FrProcedimento;
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
import model.Procedimento;
import model.Tutor;

public class TutorDAO {
    
    Tutor tutorEditando;
    private List<Tutor> lista;
    
    PreparedStatement ps = null;
    ResultSet rs = null;
    
    public List<Tutor> getLista() {
        return lista;
    }
    public void setLista(List<Tutor> lista) {
        this.lista = lista;
    }
    
    public TutorDAO() throws SQLException, ParseException {
        this.tutorEditando = null;
        this.lista = new ArrayList<Tutor>();
        this.iniciaLista();
        
    }
    
    public void iniciaLista() {
        try {
            ps = Persistencia.conectar().prepareStatement("Select * from tutor");
            rs = ps.executeQuery(); 
            
            while(rs.next()) {
                String dados[] = {rs.getString("idTutor"), rs.getString("nomeTutor"),  rs.getString("sexoTutor"), rs.getString("dataNascTutor"), rs.getString("enderecoTutor"), rs.getString("cpfTutor"), rs.getString("telefoneTutor")}; 
                Tutor v = new Tutor();  
                v.setId(Integer.parseInt(dados[0]));                   
                v.setNome(dados[1]);
                v.setSexo(dados[2]);
                
                SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");         
                Date dataFormatada = formato.parse(dados[3]);                 
                v.setDataNasc(dataFormatada);
                
                v.setEndereco(dados[4]); 
                v.setCpf(dados[5]); 
                v.setTelefone(dados[6]); 
                
                getLista().add(v);
            }           
        } catch (SQLException ex) {
            Logger.getLogger(FrProcedimento.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(TutorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void salvar(Tutor t) {
        
        getLista().add(t);    
        
        try {
            ps = Persistencia.conectar().prepareStatement("Insert into tutor (nomeTutor, sexoTutor, dataNascTutor, enderecoTutor, cpfTutor, telefoneTutor) values (?,?,?,?,?,?)");
        
                
            ps.setString(1, t.getNome());
            ps.setString(2, t.getSexo());
            ps.setString(3, (t.getDataNasc()));
            ps.setString(4, t.getEndereco());
            ps.setString(5, t.getCpf());
            ps.setString(6, t.getTelefone());

            ps.executeUpdate();
        
        } catch (SQLException ex) {
            Logger.getLogger(TutorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void salvar(Tutor t, int id) {
        
        try {
            ps = Persistencia.conectar().prepareStatement("update tutor set nomeTutor = ? , sexoTutor = ?, dataNascTutor = ?, cpfTutor = ?, enderecoTutor = ?, telefoneTutor = ? where idTutor = ? ");
            
            getLista().add(t);
            
            ps.setString(1, t.getNome());
            ps.setString(2, t.getSexo());
            ps.setString(3, (t.getDataNasc()));
            ps.setString(4, t.getCpf());
            ps.setString(5, t.getEndereco());
            ps.setString(6, t.getTelefone());
            ps.setInt(7, id);

            ps.executeUpdate();            
            
            setLista(new ArrayList<>());
            this.iniciaLista();
        
        } catch (SQLException ex) {
            Logger.getLogger(TutorDAO.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
    }
    
    public void excluir(int id){
        
        
        try {
            ps = Persistencia.conectar().prepareStatement("delete from tutor where idTutor = ? ");
            ps.setInt(1, id);
            ps.executeUpdate();
            setLista(new ArrayList<>());
            this.iniciaLista();
                    
        } catch (SQLException ex) {
            Logger.getLogger(FrProcedimento.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
}
