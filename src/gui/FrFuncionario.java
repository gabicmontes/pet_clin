
package gui;

import model.Funcionario;
import model.Persistencia;
import model.Procedimento;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import static java.util.Date.parse;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class FrFuncionario extends javax.swing.JFrame {
    
    Funcionario funcionarioEditando;
    List<Funcionario> lista;
       
    PreparedStatement ps = null;
    ResultSet rs = null;
    
    public FrFuncionario() throws SQLException, ParseException {
        this.funcionarioEditando = null;
        lista = new ArrayList<>();
        initComponents();
        this.habilitarCampos(false);
        this.iniciaLista();
    }
    
    public void iniciaLista() throws SQLException, ParseException {
        try {
            ps = Persistencia.conectar().prepareStatement("Select * from funcionario");
            rs = ps.executeQuery(); 
            
            while(rs.next()) {
                String dados[] = {rs.getString("idFuncionario"), rs.getString("nomeFuncionario"), rs.getString("cargoFuncionario"), rs.getString("salarioFuncionario"), rs.getString("sexoFuncionario"), rs.getString("dataNasc"), rs.getString("cpfFuncionario"), rs.getString("enderecoFuncionario"), rs.getString("telefoneFuncionario")}; 
                Funcionario v = new Funcionario();  
                v.setId(Integer.parseInt(dados[0]));                   
                v.setNome(dados[1]);
                v.setCargo(dados[2]);
                v.setSalario(Float.parseFloat(dados[3]));
                v.setSexo(dados[4]);
                
                SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");         
                Date dataFormatada = formato.parse(dados[5]);                 
                v.setDataNasc(dataFormatada);
                
                v.setCpf(dados[6]); 
                v.setEndereco(dados[7]); 
                v.setTelefone(dados[8]); 
                
                lista.add(v);
            }           
        } catch (SQLException ex) {
            Logger.getLogger(FrProcedimento.class.getName()).log(Level.SEVERE, null, ex);
        }
        imprimirListaFuncionario();
    }
    
    public void habilitarCampos(boolean flag) {
        for(int i=0; i < pnlCadastro.getComponents().length; i++){
            pnlCadastro.getComponent(i).setEnabled(flag);
        }
    }
       
    public void limparCampos() {
        
        txtNome.setText("");
        ftxtDataNascimento.setText("");
        ftxtCpf.setText("");
        txtEndereco.setText("");
        ftxtTelefone.setText("");
        txtCargo.setText("");
        txtSalario.setText("");
    }
    
    public void camposParaObjeto(){
        Funcionario f = new Funcionario();
        try { 
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");         
            Date dataFormatada = formato.parse(ftxtDataNascimento.getText());            
            f.setNome(txtNome.getText());
            f.setDataNasc(dataFormatada);
            f.setCpf(ftxtCpf.getText());
            f.setEndereco(txtEndereco.getText());
            f.setSexo(cbxSexo.getItemAt(0));
            f.setTelefone(ftxtTelefone.getText());
            f.setCargo(txtCargo.getText());
            f.setSalario(Float.parseFloat(txtSalario.getText()));
            f.setId(lista.size()+1);
        } catch (ParseException ex) {
            Logger.getLogger(FrFuncionario.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        lista.add(f);
    }
    
    public void camposParaObjeto(int id){
        try { 
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");         
            Date dataFormatada = formato.parse(ftxtDataNascimento.getText()); 
            id = id-1;        
            lista.get(id).setNome(txtNome.getText());
            lista.get(id).setDataNasc(dataFormatada);
            lista.get(id).setCpf(ftxtCpf.getText());
            lista.get(id).setEndereco(txtEndereco.getText());
            lista.get(id).setSexo(cbxSexo.getItemAt(0));
            lista.get(id).setTelefone(ftxtTelefone.getText());
            lista.get(id).setCargo(txtCargo.getText());
            lista.get(id).setSalario(Float.parseFloat(txtSalario.getText()));
            lista.get(id).setId(lista.size()+1);
            String sql = "insert into funcionario values ("+lista.get(id)+", )";
            
        } catch (ParseException ex) {
            Logger.getLogger(FrTutor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     
    public void objetoParaCampos(Funcionario f){
        txtNome.setText(f.getNome());
        txtEndereco.setText(f.getEndereco());        
        ftxtCpf.setText(f.getCpf());       
        ftxtTelefone.setText(f.getTelefone());  
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");  
        String dataFormatada = formato.format(f.getDataNasc());  
        ftxtDataNascimento.setText(dataFormatada);  
        txtCargo.setText(f.getCargo());
        txtSalario.setText(""+f.getSalario());
    }
    
    public void imprimirListaFuncionario() {
        
        String [] colunas = {"ID", "Nome", "Data de Nascimento", "CPF", "Telefone", "Endereço", "Cargo", "Salário"};
        DefaultTableModel model = new DefaultTableModel(colunas, 0);
        
        for(int i=0; i<lista.size(); i++) {
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");  
            String dataFormatada = formato.format(lista.get(i).getDataNasc()); 
            Object [] linha = {lista.get(i).getId(), lista.get(i).getNome(), dataFormatada, lista.get(i).getCpf(), lista.get(i).getTelefone(), lista.get(i).getEndereco(), lista.get(i).getCargo(), lista.get(i).getSalario()};
            model.addRow(linha);
        }
        
        tblFuncionario.setModel(model);
    }
    
    public Funcionario pesquisaFuncionario(int id) {
        for(int i=0; i<= lista.size() - 1; i++) {
            if(lista.get(i).getId() == id) {
                return lista.get(i);
            }
        }
        return null;
    }
    
    public boolean validaCpf(String cpf) {
        int digito1=0, digito2=0, calculoDigito1=0, calculoDigito2=0, j=10, z=11;
        int [] arrayCpf;
        boolean repetido = true;
        arrayCpf = new int[9];
        digito1 = Integer.parseInt(cpf.substring(12,13));
        digito2 = Integer.parseInt(cpf.substring(13,14));   
        cpf = cpf.substring(0, 3) + cpf.substring(4,7) + cpf.substring(8,11);
        
        
        for(int i=0; i<arrayCpf.length; i++) {
            arrayCpf[i] = Integer.parseInt(cpf.substring(i,i+1));
            
            calculoDigito1 += j*arrayCpf[i];
            j--;
            
            calculoDigito2 += z*arrayCpf[i];
            z--;            
            
            if(arrayCpf[0] != arrayCpf[i] && repetido) {
                repetido = false;
            }
        }
        calculoDigito2 += digito1*z;
            
        calculoDigito1 = calculoDigito1 * 10;
        calculoDigito1 = calculoDigito1%11;
        calculoDigito2 = calculoDigito2 * 10;
        calculoDigito2 = calculoDigito2%11;
        
        if(calculoDigito1 == 10)
            calculoDigito1 = 0;
        if(calculoDigito2 == 10)
            calculoDigito2 = 0;
        
        if(calculoDigito1 != digito1 || calculoDigito2 != digito2 || repetido) {
            return false;
        } else {
            return true;
        }
    }
    

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        lblTitulo = new javax.swing.JLabel();
        btnNovo = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();
        pnlCadastro = new javax.swing.JPanel();
        lblNome = new javax.swing.JLabel();
        txtNome = new javax.swing.JTextField();
        lblIdade = new javax.swing.JLabel();
        lblCPF = new javax.swing.JLabel();
        ftxtCpf = new javax.swing.JFormattedTextField();
        lblSexo = new javax.swing.JLabel();
        cbxSexo = new javax.swing.JComboBox<>();
        lblEndereco = new javax.swing.JLabel();
        txtEndereco = new javax.swing.JTextField();
        ftxtDataNascimento = new javax.swing.JFormattedTextField();
        lblTelefone = new javax.swing.JLabel();
        ftxtTelefone = new javax.swing.JFormattedTextField();
        lblSalario = new javax.swing.JLabel();
        txtSalario = new javax.swing.JTextField();
        lblCargo = new javax.swing.JLabel();
        txtCargo = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblFuncionario = new javax.swing.JTable();
        btnListar = new javax.swing.JButton();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTable1);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(jTable2);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblTitulo.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setText("Cadastro Funcionário");
        lblTitulo.setToolTipText("");

        btnNovo.setText("Novo");
        btnNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovoActionPerformed(evt);
            }
        });

        btnEditar.setText("Editar");
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnExcluir.setText("Excluir");
        btnExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirActionPerformed(evt);
            }
        });

        btnSalvar.setText("Salvar");
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });

        lblNome.setText("Nome:");

        txtNome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomeActionPerformed(evt);
            }
        });

        lblIdade.setText("Data de Nascimento:");

        lblCPF.setText("CPF:");

        try {
            ftxtCpf.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###.###.###-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        ftxtCpf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ftxtCpfActionPerformed(evt);
            }
        });

        lblSexo.setText("Sexo:");

        cbxSexo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Feminino", "Masculino", "Outro" }));
        cbxSexo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxSexoActionPerformed(evt);
            }
        });

        lblEndereco.setText("Endereço:");

        txtEndereco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEnderecoActionPerformed(evt);
            }
        });

        try {
            ftxtDataNascimento.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        lblTelefone.setText("Telefone:");

        try {
            ftxtTelefone.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##) 9####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        lblSalario.setText("Salário:");

        lblCargo.setText("Cargo:");

        javax.swing.GroupLayout pnlCadastroLayout = new javax.swing.GroupLayout(pnlCadastro);
        pnlCadastro.setLayout(pnlCadastroLayout);
        pnlCadastroLayout.setHorizontalGroup(
            pnlCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCadastroLayout.createSequentialGroup()
                .addComponent(lblNome, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(lblTelefone)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ftxtTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblCPF)
                .addGap(31, 31, 31)
                .addComponent(ftxtCpf, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblSexo, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(cbxSexo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(pnlCadastroLayout.createSequentialGroup()
                .addComponent(lblEndereco)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtEndereco))
            .addGroup(pnlCadastroLayout.createSequentialGroup()
                .addComponent(lblIdade)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ftxtDataNascimento, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(lblSalario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtSalario, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(lblCargo)
                .addGap(18, 18, 18)
                .addComponent(txtCargo))
        );
        pnlCadastroLayout.setVerticalGroup(
            pnlCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCadastroLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblNome)
                        .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblTelefone)
                        .addComponent(ftxtTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lblCPF, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(pnlCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ftxtCpf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblSexo)
                            .addComponent(cbxSexo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(pnlCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblIdade)
                    .addComponent(ftxtDataNascimento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblSalario)
                    .addComponent(txtSalario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCargo)
                    .addComponent(txtCargo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(pnlCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblEndereco)
                    .addComponent(txtEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(44, 44, 44))
        );

        tblFuncionario.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane4.setViewportView(tblFuncionario);

        btnListar.setText("Listar Funcionários");
        btnListar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnListarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnListar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane4)
                    .addComponent(pnlCadastro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(38, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnCancelar, btnEditar, btnExcluir, btnNovo, btnSalvar});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(btnNovo)
                    .addComponent(btnEditar)
                    .addComponent(btnCancelar)
                    .addComponent(btnExcluir)
                    .addComponent(btnSalvar))
                .addGap(18, 18, 18)
                .addComponent(pnlCadastro, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnListar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        
        int id;
        
        if(tblFuncionario.getSelectedRow() != -1) {
            id = (int) tblFuncionario.getValueAt(tblFuncionario.getSelectedRow(), 0);
            funcionarioEditando = this.pesquisaFuncionario(id);
            this.limparCampos();
            this.habilitarCampos(true);
            this.objetoParaCampos(funcionarioEditando);
        } else {
            System.out.println("Nenhuma linha selecionada");
        }        
        
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoActionPerformed
        this.habilitarCampos(true);
        this.limparCampos();
    }//GEN-LAST:event_btnNovoActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        
        if(this.funcionarioEditando == null) {
            
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");         
            Date dataFormatada; 
            try {
                dataFormatada = formato.parse(ftxtDataNascimento.getText());
                Funcionario v = new Funcionario();        
                v.setNome(txtNome.getText());
                v.setCargo(txtCargo.getText());       
                v.setSexo(cbxSexo.getItemAt(0));
                v.setSalario(Float.parseFloat(txtSalario.getText()));
                v.setDataNasc(dataFormatada);
                v.setCpf(ftxtCpf.getText()); 
                v.setEndereco(txtEndereco.getText()); 
                v.setTelefone(ftxtTelefone.getText());           
            
                lista.add(v);        
           
                ps = Persistencia.conectar().prepareStatement("Insert into funcionario (nomeFuncionario, cargoFuncionario, salarioFuncionario, sexoFuncionario, dataNasc, cpfFuncionario, enderecoFuncionario, telefoneFuncionario) values (?,?,?,?,?,?,?,?)");
                
                ps.setString(1, v.getNome());
                ps.setString(2, v.getCargo());
                ps.setFloat(3, v.getSalario());
                ps.setString(4, v.getSexo());
                ps.setString(5, ftxtDataNascimento.getText());
                ps.setString(6, v.getCpf());
                ps.setString(7, v.getEndereco());
                ps.setString(8, v.getTelefone());
                
                ps.executeUpdate();
                
                lista = new ArrayList<>();
                this.iniciaLista();
        
            } catch (SQLException ex) {
                Logger.getLogger(FrProcedimento.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParseException ex) {            
                Logger.getLogger(FrFuncionario.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.imprimirListaFuncionario();
            
        } else {
            try {                
                int id = this.funcionarioEditando.getId();
                ps = Persistencia.conectar().prepareStatement("update funcionario set nomeFuncionario = ? , cargoFuncionario = ?, salarioFuncionario = ?, sexoFuncionario = ?, dataNasc = ?, cpfFuncionario = ?, enderecoFuncionario = ?, telefoneFuncionario = ? where idFuncionario = ? ");
                
                SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");         
                Date dataFormatada; 
                dataFormatada = formato.parse(ftxtDataNascimento.getText());
                Funcionario v = new Funcionario();        
                v.setNome(txtNome.getText());
                v.setCargo(txtCargo.getText());       
                v.setSexo(cbxSexo.getItemAt(0));
                v.setSalario(Float.parseFloat(txtSalario.getText()));
                v.setDataNasc(dataFormatada);
                v.setCpf(ftxtCpf.getText()); 
                v.setEndereco(txtEndereco.getText()); 
                v.setTelefone(ftxtTelefone.getText());       
                
                lista.add(v);                       
                
                ps.setString(1, v.getNome());
                ps.setString(2, v.getCargo());
                ps.setFloat(3, v.getSalario());
                ps.setString(4, v.getSexo());
                ps.setString(5, ftxtDataNascimento.getText());
                ps.setString(6, v.getCpf());
                ps.setString(7, v.getEndereco());
                ps.setString(8, v.getTelefone());
                ps.setInt(9, id);
                
                ps.executeUpdate();
                
                this.funcionarioEditando = null;
                lista = new ArrayList<>();
                this.iniciaLista();
                
            } catch (SQLException ex) {
                Logger.getLogger(FrProcedimento.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParseException ex) {
                Logger.getLogger(FrFuncionario.class.getName()).log(Level.SEVERE, null, ex);
            }
        }        
        
        this.limparCampos();
        this.habilitarCampos(false);    
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        this.limparCampos();
        this.habilitarCampos(false);
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        
        int i;
        if(tblFuncionario.getSelectedRow() != -1) {
            i = (int) tblFuncionario.getValueAt(tblFuncionario.getSelectedRow(), 0);
            funcionarioEditando = this.pesquisaFuncionario(i);
            int id = this.funcionarioEditando.getId();
            this.objetoParaCampos(funcionarioEditando);
            this.habilitarCampos(false);
            
            if(JOptionPane.showConfirmDialog(rootPane, "Deseja realmente exluir o funcionario " + funcionarioEditando.getNome() + " ?", "Sistema PETClin", JOptionPane.YES_NO_OPTION) == 0) {
                try {
                    ps = Persistencia.conectar().prepareStatement("delete from funcionario where idFuncionario = ? ");
                    ps.setInt(1, id);
                    ps.executeUpdate();
                    lista = new ArrayList<>();
                    this.iniciaLista();
                    
                } catch (SQLException ex) {
                    Logger.getLogger(FrProcedimento.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ParseException ex) {
                    Logger.getLogger(FrFuncionario.class.getName()).log(Level.SEVERE, null, ex);
                }              
            }
        }
    }//GEN-LAST:event_btnExcluirActionPerformed

    private void btnListarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnListarActionPerformed
        imprimirListaFuncionario();
    }//GEN-LAST:event_btnListarActionPerformed

    private void txtEnderecoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEnderecoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEnderecoActionPerformed

    private void cbxSexoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxSexoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxSexoActionPerformed

    private void ftxtCpfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ftxtCpfActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ftxtCpfActionPerformed

    private void txtNomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomeActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnListar;
    private javax.swing.JButton btnNovo;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JComboBox<String> cbxSexo;
    private javax.swing.JFormattedTextField ftxtCpf;
    private javax.swing.JFormattedTextField ftxtDataNascimento;
    private javax.swing.JFormattedTextField ftxtTelefone;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JLabel lblCPF;
    private javax.swing.JLabel lblCargo;
    private javax.swing.JLabel lblEndereco;
    private javax.swing.JLabel lblIdade;
    private javax.swing.JLabel lblNome;
    private javax.swing.JLabel lblSalario;
    private javax.swing.JLabel lblSexo;
    private javax.swing.JLabel lblTelefone;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JPanel pnlCadastro;
    private javax.swing.JTable tblFuncionario;
    private javax.swing.JTextField txtCargo;
    private javax.swing.JTextField txtEndereco;
    private javax.swing.JTextField txtNome;
    private javax.swing.JTextField txtSalario;
    // End of variables declaration//GEN-END:variables
}
