
package classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Historico {
    private int id;
    private List<Consulta> consultas;
    private String vacina;
    private String procedimentos;
    
    public Historico() {
        this.id = 0;
        this.consultas = new ArrayList();
        this.vacina = "";
        this.procedimentos = "";
    }
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public List<Consulta> getConsultas() {
        return consultas;
    }
    public void setConsultas(List<Consulta> consultas) {
        this.consultas = consultas;
    }

    public String getVacina() {
        return vacina;
    }
    public void setVacina(String vacina) {
        this.vacina = vacina;
    }

    public String getProcedimentos() {
        return procedimentos;
    }
    public void setProcedimentos(String procedimentos) {
        this.procedimentos = procedimentos;
    }
    
    public void preencher() {
        Scanner leitor = new Scanner(System.in);
        System.out.println("************** Preenchendo Histórico **************");
        System.out.print("Id: ");
        this.id = leitor.nextInt();
        System.out.print("Vacina: ");
        this.vacina = leitor.next();
        System.out.print("Procedimentos: ");
        this.procedimentos = leitor.next();
                
        Consulta c = new Consulta();
        c.preencher();        
        this.consultas.add(c);
        
        System.out.println("***************************************************\n");
    }
    
     public void imprimir() {
        System.out.println("**************** Dados do Histórico ***************");
        System.out.println("Id: " + this.id);
        System.out.print("Vacina: " + this.vacina);
        System.out.print("Procedimentos: " + this.procedimentos);
         System.out.println("Consultas:");
        for(int i=0; i<this.consultas.size(); i++) {
            consultas.get(i).imprimir();
        }
        
        System.out.println("***************************************************\n");
     }
    
      public void copiar(Historico historico) {
        this.id = historico.getId();
        this.vacina = historico.getVacina();
        this.procedimentos = historico.getProcedimentos();
        
        for(int i=0; i<consultas.size(); i++) {
            consultas.get(i).copiar(historico.getConsultas().get(i));
        }
      }
}
