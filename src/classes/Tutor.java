
package classes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Tutor {

    private int id;
    private String nome;
    private String sexo;
    private Date dataNasc;
    private String endereco;
    private String cpf;
    private String telefone;
    
    public Tutor(){
        this.id = 0;
        this.nome = "";
        this.sexo = "";
        this.dataNasc = new Date();
        this.endereco = "";
        this.cpf = "000.000.000-00";
        this.telefone = "";
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

    public String getSexo() {
        return sexo;
    }
    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public Date getDataNasc() {
        return dataNasc;
    }
    public void setDataNasc(Date dataNasc) {
        this.dataNasc = dataNasc;
    }
    
    public String getEndereco() {
        return endereco;
    }
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    
    public String getTelefone() {
        return telefone;
    }
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    public void preencher(){
        try { 
            SimpleDateFormat formataData = new SimpleDateFormat("dd/MM/yyy");
            Scanner leitor = new Scanner(System.in);
            System.out.println("************** Preenchendo Tutor **************");
            System.out.print("Id: ");
            this.setId(leitor.nextInt());
            System.out.print("Nome: ");
            this.setNome(leitor.next());
            System.out.print("Sexo: ");
            this.setSexo(leitor.next());
            System.out.print("Data de nascimento: ");
            this.setDataNasc(formataData.parse(leitor.next()));
            System.out.print("Endereço: ");
            this.setEndereco(leitor.next());
            System.out.print("CPF: ");
            this.setCpf(leitor.next());
            System.out.print("Telefone: ");
            this.setTelefone(leitor.next());
            System.out.println("***************************************************\n");
        } catch (ParseException ex) {
            Logger.getLogger(Tutor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    public void imprimir() {
        System.out.println("**************** Dados do Tutor***************");
        System.out.println("Id: " + this.getId());
        System.out.println("Nome: " + this.getNome());
        System.out.println("Sexo: " + this.getSexo());
        System.out.println("Data de nascimento: " + this.getDataNasc());
        System.out.println("Endereço: " + this.getEndereco());
        System.out.println("Telefone: " + this.getTelefone());
        System.out.println("****************************************************\n");
    }
    
    public String imprimirParaString() {
        String saida = "";
        saida = "**************** Dados do Professor ***************\n"
        +"\nId: " + this.getId()
        +"\nNome: " + this.getNome()
        +"\nSexo: " + this.getSexo()
        +"\nData de nascimento: " + this.getDataNasc()
        +"\nEndereço: " + this.getEndereco()
        +"\nCPF: " + this.getCpf() 
        +"\nTelefone: " + this.getTelefone()          
        +"\n\n******************************************************\n";
        return saida;
    }
    
    public void copiar(Tutor tutor) {
        this.setId(tutor.getId());
        this.setNome(tutor.getNome());
        this.setSexo(tutor.getSexo());
        this.dataNasc = tutor.getDataNasc();        
        this.setEndereco(tutor.getEndereco());
        this.setCpf(tutor.getCpf());
        this.setTelefone(tutor.getTelefone());
    }
}
