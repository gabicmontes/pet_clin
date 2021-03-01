
package classes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Consulta {
    private int id;
    private Date data;
    private Paciente paciente;
    private String tratamento;
    private String diagnostico;
    
    public Consulta() {
        this.id = 0;
        this.data = new Date();
        this.paciente = new Paciente();
        this.tratamento = "";
        this.diagnostico = "";
    }
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }
    public void setData(Date data) {
        this.data = data;
    }

    public Paciente getPaciente() {
        return paciente;
    }
    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public String getTratamento() {
        return tratamento;
    }
    public void setTratamento(String tratamento) {
        this.tratamento = tratamento;
    }

    public String getDiagnostico() {
        return diagnostico;
    }
    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }
    
    public void preencher(){
        try {
            SimpleDateFormat formataData = new SimpleDateFormat("dd/MM/yyy");
            Scanner leitor = new Scanner(System.in);
            System.out.println("**************** Preenchendo Consulta ****************");
            System.out.print("Id: ");
            this.setId(leitor.nextInt());
            System.out.print("Data da Consulta: ");
            this.setData(formataData.parse(leitor.next()));            
            System.out.print("Paciente: ");
            this.setPaciente(this.paciente);           
            System.out.print("Tratamento: ");
            this.setTratamento(leitor.next());          
            System.out.print("Diagnóstico: ");
            this.setDiagnostico(leitor.next());
            System.out.println("***************************************************\n");
        } catch (ParseException ex) {
            Logger.getLogger(Paciente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    public void imprimir() {
        System.out.println("****************** Dados do Paciente *****************");
        System.out.println("Id: " + this.getId());
        System.out.println("Data: " + this.getData());
        System.out.println("Paciente: " + this.paciente.getNome());        
        System.out.println("Tratamento: " + this.tratamento);    
        System.out.println("Diagnóstico: " + this.diagnostico);
        System.out.println("***************************************************\n");
    }
    
    public void copiar(Consulta consulta) {
        this.setId(consulta.getId());
        this.setData(consulta.getData());
        this.paciente = consulta.getPaciente();
        this.tratamento = consulta.getTratamento();
        this.diagnostico = consulta.getDiagnostico();
    }
    
}
