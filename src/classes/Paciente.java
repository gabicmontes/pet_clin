
package classes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Paciente {
    
    private int id;
    private String nome;
    private String raca;
    private String sexo;
    private Date data_nasc;
    private Tutor tutor;
    private Historico historico;
    
    public Paciente() {
        this.id = 0;
        this.nome = "";
        this.raca = "";
        this.sexo = "";  
        this.data_nasc = new Date();
        this.tutor = new Tutor();
        this.historico = new Historico();
        
    }    

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getRaca() {
        return raca;
    }
    public void setRaca(String raca) {
        this.raca = raca;
    }
    
    public String getSexo() {
        return sexo;
    }
    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public Date getData_nasc() {
        return data_nasc;
    }
    public void setData_nasc(Date data_nasc) {
        this.data_nasc = data_nasc;
    }

    public Tutor getTutor() {
        return tutor;
    }
    public void setTutor(Tutor tutor) {
        this.tutor = tutor;
    }

    public Historico getHistorico() {
        return historico;
    }
    public void setHistorico(Historico historico) {
        this.historico = historico;
    }
    
    public void preencher(){
        try {
            SimpleDateFormat formataData = new SimpleDateFormat("dd/MM/yyy");
            Scanner leitor = new Scanner(System.in);
            System.out.println("**************** Preenchendo Paciente ****************");
            System.out.print("Id: ");
            this.setId(leitor.nextInt());
            System.out.print("Nome: ");
            this.setNome(leitor.next());
            System.out.print("Raça: ");
            this.setRaca(leitor.next());
            System.out.print("Sexo: ");
            this.setSexo(leitor.next());
            System.out.print("Data de nascimento: ");
            this.setData_nasc(formataData.parse(leitor.next()));            
            System.out.print("Tutor: ");
            this.tutor.preencher();           
            System.out.print("Historico: ");
            this.historico.preencher();
            System.out.println("***************************************************\n");
        } catch (ParseException ex) {
            Logger.getLogger(Paciente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    public void imprimir() {
        System.out.println("****************** Dados do Paciente *****************");
        System.out.println("Id: " + this.getId());
        System.out.println("Nome: " + this.getNome());
        System.out.println("Raça: " + this.getRaca());
        System.out.println("Sexo: " + this.getSexo());
        System.out.println("Data de nascimento: " + this.getData_nasc());
        System.out.println("Tutor: " + this.tutor.getNome());        
        System.out.println("Histórico: " + this.historico);
        System.out.println("***************************************************\n");
    }
    
    public void copiar(Paciente paciente) {
        this.setId(paciente.getId());
        this.setNome(paciente.getNome());
        this.setRaca(paciente.getRaca());
        this.setSexo(paciente.getSexo());
        this.data_nasc = paciente.getData_nasc();
        this.tutor = paciente.getTutor();
        this.historico = paciente.getHistorico();
    }
}