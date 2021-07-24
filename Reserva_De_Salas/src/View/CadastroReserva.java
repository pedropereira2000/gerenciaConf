/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Bean.Ambiente;
import Bean.Reserva;
import Dao.AmbienteDAO;
import Dao.ReservaDAO;
import java.awt.Color;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;

public class CadastroReserva extends javax.swing.JFrame {
    
    public void readServidor() {      
        
    }

    
    //Função para cadastrar as reservas por periodo
    public void cadReservaPeriodo(String periodo){
        //Verificação de se há algum campo vázio
        if((cbBloco.getSelectedItem().toString()!=" ") || (cbSala.getSelectedItem().toString()!=" ") || (periodo!=" ") || (dtcDate.getDate()!=null) || (txtNome.getText()!=" ") || (txtDocumento.getText()!=" ") || (txtTelefone.getText()!=" ")){
            //Pegando formato que é armazenando no banco
            SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd"); 
            //Formatando data pega do jchooserdate
            String dataf = s.format(dtcDate.getDate());
            //Pegando data atual do dia
            Date data = new Date();
            //Verificação de se a data escolhida no chooserdate é antes do dia atual
            if(dtcDate.getDate().before(data)){
                //Menssagem de erro
                JOptionPane.showConfirmDialog(null, "A data incerida é inválida","ERRO!",JOptionPane.OK_CANCEL_OPTION);
            }else{
                ReservaDAO rdao = new ReservaDAO();
                //Verificação se o periodo não está cadastrado
                if(rdao.validarReserva(cbBloco.getSelectedItem().toString(), cbSala.getSelectedItem().toString(), periodo, dataf).equals("0")){
                    //Verificando se os campos referentes ao cliente estão vazios
                    if((txtNome.getText().equals(""))||(txtDocumento.getText().equals(""))||(txtTelefone.getText().equals(""))){
                        //Menssagem de erro
                        JOptionPane.showConfirmDialog(null,"Os campor referentes ao clientes estão incompletos","Erro",JOptionPane.OK_CANCEL_OPTION,JOptionPane.CANCEL_OPTION);
                    }else{
                        //Verificando se o cliente informado já existe (resposta igual a 1 é que existe esse cliente e 0 que não existe)
                        if(rdao.listarCliente(txtDocumento.getText(),txtNome.getText(),txtTelefone.getText()).equals("1")){
                            //Confirmando se deseja realmente cadastrar o cliente
                            int confirma = JOptionPane.showConfirmDialog(null, "O cliente que está reservando já esta cadastrado é o\nNome: "+txtNome.getText()+"\nDocumento: "+txtDocumento.getText()+"\nDeseja reservar para esse cliente?","Atenção",JOptionPane.YES_NO_OPTION);
                            if(confirma == JOptionPane.YES_OPTION){
                                try {
                                    // Cadastrando Reserva
                                    cadReserva(periodo);
                                    //Menssagem de confirmação
                                    JOptionPane.showMessageDialog(null, "Reserva Realizado");
                                } catch (Exception e) {
                                    //Menssagem de erro
                                    JOptionPane.showMessageDialog(null, "Erro Reeserva não realizado" + e);
                                }
                            }
                        }else{
                            int confirma = JOptionPane.showConfirmDialog(null, "Deseja cadastrar o cliente\nNome: "+txtNome.getText()+"\nDocumento: "+txtDocumento.getText()+"\nPara esta reserva?","Atenção",JOptionPane.YES_NO_OPTION);
                            if(confirma == JOptionPane.YES_OPTION){
                                try {
                                    // Cadastrando Reserva
                                    cadReservaCli(periodo);
                                    //Menssagem de confirmação
                                    JOptionPane.showMessageDialog(null, "Reserva Realizado");
                                } catch (Exception e) {
                                    //Menssagem de erro
                                    JOptionPane.showMessageDialog(null, "Erro Reeserva não realizado" + e);
                                }
                            }
                        }
                    }
                }else{
                    //Menssagem de erro
                    JOptionPane.showMessageDialog(null, "Erro Reeserva não realizado o periodo " + periodo + "já está reservado" );
                }                
            }
        }
    }
    
    //Função para buscar os blocos
    public void readBlocs() {
        //removendo todos os itens dentro do comboBox
        cbBloco.removeAllItems();
        //Adicionando vazio
        cbBloco.addItem(" ");
        //removendo todos os itens dentro do comboBox
        cbSala.removeAllItems();
        //Adicionando vazio
        cbSala.addItem(" ");

        AmbienteDAO adao = new AmbienteDAO();
        
        //Laço de repetição para adicionar todos os blocos cadastrados
        for (Ambiente a : adao.listarBloco()) {
            //adicionando os blocos
            cbBloco.addItem(a.getBloco());
        }
    }

    //Função para Listar Todas as Salas Cadastradas
    public void readSalas(String desc) {
        //removendo todos os itens dentro do comboBox
        cbSala.removeAllItems();
        //Adicionando vazio
        cbSala.addItem(" ");

        AmbienteDAO adao = new AmbienteDAO();

        //Laço de repetição para adicionar todos as salas cadastrados
        for (Ambiente a : adao.listarSala(desc)) {
            cbSala.addItem(a.getSalaNum());
        }
    }
    
    //Função para cadastrar só as reservas
    public void cadReserva(String periodo) throws ParseException {
        Reserva res = new Reserva();
        ReservaDAO resDao = new ReservaDAO();
        
        //pegando valor do comboBox do bloco e colocando no atributo da classe ambiente
        res.getAmbi().setBloco(cbBloco.getSelectedItem().toString());
        //pegando valor do comboBox da sala e colocando no atributo da classe ambiente
        res.getAmbi().setSalaNum(cbSala.getSelectedItem().toString());
        
        //pegando valor do periodo selecionado e colocando no atributo reserva
        res.setPeriodoReservado(periodo);
        
        //pegando valor do jtext do nome e colocando no atributo da classe reserva
        res.setClienteNome(txtNome.getText());
        //pegando valor do jtext do telefone e colocando no atributo da classe reserva
        res.setClienteContato(txtTelefone.getText());
        //pegando valor do jtext do documento e colocando no atributo da classe reserva
        res.setClienteDocumento(txtDocumento.getText());
        //Verificando qual valor esta selecionado no combobox
        if ((cbContribuicao.getSelectedItem().toString()).equals("True")) {
            //pegando valor do comboBox da contribuição e colocando no atributo da classe reserva
            res.setContribuicao(true);
        } else if ((cbContribuicao.getSelectedItem().toString()).equals("False")) {
            //pegando valor do comboBox da contribuição e colocando no atributo da classe reserva
            res.setContribuicao(false);
        }
        //Criando variável para formatar
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        //Formatendo data do jchooserDate e salvando
        String dataf = s.format(dtcDate.getDate());
        //pegando valor da variavel da data e colocando no atributo da classe reserva
        res.setDataInicio(dataf);
        //pegando valor do jtext das observações e colocando no atributo da classe reserva
        res.setObservacao(txtObs.getText());
        //pegando valor do jtext da nome e colocando no atributo da classe servidor
        res.getServ().setNome(txtServ.getText());
        
        //Acessando método para buscar o id do cliente que está reservando
        int id = Integer.parseInt(resDao.buscarIdCli(res.getClienteDocumento(), res.getClienteNome(), res.getClienteContato()));
        //pegando valor da variavel do id do cliente e colocando no atributo da classe reserva
        res.setId(id);
        
        //Chamando método para cadastrar e passando o objeto da reserva populado agora
        resDao.cadastrarReserva(res);
    }
    
    //Função para cadastrar reservas e cliente
    public void cadReservaCli(String periodo) throws ParseException {
        Reserva res = new Reserva();
        ReservaDAO resDao = new ReservaDAO();
        
        //pegando valor do comboBox do bloco e colocando no atributo da classe ambiente
        res.getAmbi().setBloco(cbBloco.getSelectedItem().toString());
        //pegando valor do comboBox da sala e colocando no atributo da classe ambiente
        res.getAmbi().setSalaNum(cbSala.getSelectedItem().toString());
        
        //pegando valor da variavel do periodo e colocando no atributo da classe reserva
        res.setPeriodoReservado(periodo);
        
        //pegando valor do jtext do Nome do cliente e colocando no atributo da classe reserva
        res.setClienteNome(txtNome.getText());
        //pegando valor do jtext do Telefone do cliente e colocando no atributo da classe reserva
        res.setClienteContato(txtTelefone.getText());
        //pegando valor do jtext do Documento do cliente e colocando no atributo da classe reserva
        res.setClienteDocumento(txtDocumento.getText());
        //Verificando qual valor esta selecionado no combobox
        if ((cbContribuicao.getSelectedItem().toString()).equals("True")) {
            //pegando valor true e colocando no atributo da classe reserva
            res.setContribuicao(true);
        } else if ((cbContribuicao.getSelectedItem().toString()).equals("False")) {
            //pegando valor false e colocando no atributo da classe reserva
            res.setContribuicao(false);
        }
        //Criando variável para formatar
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        //Formatendo data do jchooserDate e salvando
        String dataf = s.format(dtcDate.getDate());
        //pegando valor da variavel da data e colocando no atributo da classe reserva
        res.setDataInicio(dataf);
        //pegando valor do comboBox da observação e colocando no atributo da classe reserva
        res.setObservacao(txtObs.getText());
        //pegando valor do jtext do nome do servidor e colocando no atributo da classe servidor
        res.getServ().setNome(txtServ.getText());
        //res.setId(Integer.parseInt(txtId.getText()));
        
        //Chamando método para cadastrar e passando informações do cliente
        resDao.cadastrarCliente(res.getClienteNome(), res.getClienteDocumento(), res.getClienteContato());
        
        //Chamando método para cadastrar e passando o objeto da reserva populado agora
        resDao.cadastrarReservaCli(res);
    }

    //Função para inicializar componentes
    public CadastroReserva() {
        initComponents();
        getContentPane().setBackground(Color.WHITE);
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cbBloco = new javax.swing.JComboBox<>();
        cbSala = new javax.swing.JComboBox<>();
        txtDocumento = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtTelefone = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtObs = new javax.swing.JTextField();
        btnVoltar = new javax.swing.JButton();
        btnNovoR = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        btnSalvar = new javax.swing.JButton();
        cbContribuicao = new javax.swing.JComboBox<>();
        txtNome = new javax.swing.JTextField();
        txtServ = new javax.swing.JLabel();
        dtcDate = new com.toedter.calendar.JDateChooser();
        cbxM1 = new javax.swing.JCheckBox();
        cbxM2 = new javax.swing.JCheckBox();
        cbxM3 = new javax.swing.JCheckBox();
        cbxM4 = new javax.swing.JCheckBox();
        cbxM5 = new javax.swing.JCheckBox();
        cbxT1 = new javax.swing.JCheckBox();
        cbxT2 = new javax.swing.JCheckBox();
        cbxT3 = new javax.swing.JCheckBox();
        cbxT4 = new javax.swing.JCheckBox();
        cbxT6 = new javax.swing.JCheckBox();
        cbxT5 = new javax.swing.JCheckBox();
        cbxN1 = new javax.swing.JCheckBox();
        cbxN2 = new javax.swing.JCheckBox();
        cbxN3 = new javax.swing.JCheckBox();
        cbxN4 = new javax.swing.JCheckBox();
        cbxN5 = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setResizable(false);

        cbBloco.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        cbBloco.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
                cbBlocoPopupMenuWillBecomeVisible(evt);
            }
        });
        cbBloco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbBlocoActionPerformed(evt);
            }
        });

        cbSala.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        cbSala.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
                cbSalaPopupMenuWillBecomeVisible(evt);
            }
        });

        txtDocumento.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("Reservas");

        txtTelefone.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText("Bloco:");

        txtObs.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        btnVoltar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnVoltar.setText("CANCELAR");
        btnVoltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVoltarActionPerformed(evt);
            }
        });

        btnNovoR.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnNovoR.setText("LIMPAR");
        btnNovoR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovoRActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setText("Sala:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("Data:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("Periodo:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setText("Nome:");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setText("Documento do Cliente:");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setText("Contribuição:");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setText("Telefone:");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setText("Observações:");

        btnSalvar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnSalvar.setText("SALVAR");
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });

        cbContribuicao.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "True", "False" }));
        cbContribuicao.setBorder(javax.swing.BorderFactory.createCompoundBorder());

        txtNome.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtServ.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtServ.setText(TelaLogin.userNome);

        dtcDate.setBackground(new java.awt.Color(255, 255, 255));

        cbxM1.setText("M1");
        cbxM1.setPreferredSize(new java.awt.Dimension(40, 20));

        cbxM2.setText("M2");
        cbxM2.setMaximumSize(null);
        cbxM2.setMinimumSize(null);
        cbxM2.setPreferredSize(new java.awt.Dimension(40, 20));

        cbxM3.setText("M3");
        cbxM3.setPreferredSize(new java.awt.Dimension(40, 20));

        cbxM4.setText("M4");
        cbxM4.setPreferredSize(new java.awt.Dimension(40, 20));

        cbxM5.setText("M5");
        cbxM5.setPreferredSize(new java.awt.Dimension(40, 20));

        cbxT1.setText("T1");
        cbxT1.setPreferredSize(new java.awt.Dimension(40, 20));

        cbxT2.setText("T2");
        cbxT2.setPreferredSize(new java.awt.Dimension(40, 20));

        cbxT3.setText("T3");
        cbxT3.setPreferredSize(new java.awt.Dimension(40, 20));

        cbxT4.setText("T4");
        cbxT4.setPreferredSize(new java.awt.Dimension(40, 20));

        cbxT6.setText("T6");
        cbxT6.setPreferredSize(new java.awt.Dimension(40, 20));

        cbxT5.setText("T5");
        cbxT5.setPreferredSize(new java.awt.Dimension(40, 20));

        cbxN1.setText("N1");
        cbxN1.setPreferredSize(new java.awt.Dimension(40, 20));

        cbxN2.setText("N2");
        cbxN2.setPreferredSize(new java.awt.Dimension(40, 20));

        cbxN3.setText("N3");
        cbxN3.setPreferredSize(new java.awt.Dimension(40, 20));

        cbxN4.setText("N4");
        cbxN4.setPreferredSize(new java.awt.Dimension(40, 20));

        cbxN5.setText("N5");
        cbxN5.setPreferredSize(new java.awt.Dimension(40, 20));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 29, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(cbxM1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cbxM2, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbxM3, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cbxN1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cbxT1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(cbxN2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(cbxN3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(cbxT2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(cbxT3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cbxT4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbxM4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbxN4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbxM5, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbxN5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(cbxT5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbxT6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnNovoR, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnSalvar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnVoltar))
                            .addComponent(txtObs, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel6)
                            .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cbBloco, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2))
                                .addGap(38, 38, 38)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel8)
                                        .addGap(24, 24, 24))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(cbSala, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(cbContribuicao, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(jLabel5)
                            .addComponent(dtcDate, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(29, 29, 29)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel9)
                                    .addComponent(txtTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(jLabel1)
                            .addGap(94, 94, 94)
                            .addComponent(txtServ))))
                .addGap(28, 28, 28))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtServ))
                .addGap(30, 30, 30)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(jLabel3)
                        .addComponent(jLabel8))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jLabel9))
                        .addGap(4, 4, 4)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(38, 38, 38)))
                .addGap(2, 2, 2)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbBloco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbContribuicao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbSala, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dtcDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxM1, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxM2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxM3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxM4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxM5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxT2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxT1, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxT3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxT4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxT5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxT6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxN2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxN1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxN3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxN4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxN5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel10)
                .addGap(11, 11, 11)
                .addComponent(txtObs, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNovoR)
                    .addComponent(btnSalvar)
                    .addComponent(btnVoltar))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    
    //quando expande o comboBox busca os blocos cadastrados
    private void cbBlocoPopupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cbBlocoPopupMenuWillBecomeVisible
        readBlocs();
    }//GEN-LAST:event_cbBlocoPopupMenuWillBecomeVisible

    private void cbBlocoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbBlocoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbBlocoActionPerformed

    //quando expande o comboBox busca as salas cadastrados para o bloco selecionado
    private void cbSalaPopupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cbSalaPopupMenuWillBecomeVisible
        readSalas(cbBloco.getSelectedItem().toString());
    }//GEN-LAST:event_cbSalaPopupMenuWillBecomeVisible

    //Evento para voltar para a tela anterior
    private void btnVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVoltarActionPerformed
        new TelaReservaView().show();
        dispose();
    }//GEN-LAST:event_btnVoltarActionPerformed

    //Evento para limpar tela
    private void btnNovoRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoRActionPerformed
        //Limpando a Tela
        txtNome.setText(null);
        txtDocumento.setText(null);
        txtTelefone.setText(null);
        txtObs.setText(null);

    }//GEN-LAST:event_btnNovoRActionPerformed
    
    //Evento para salvar
    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        //Verificação para saber qual periodo está selecionado para ser reservado
        if((cbxM1.isSelected()==false)&&(cbxM2.isSelected()==false)&&(cbxM3.isSelected()==false)&&(cbxM4.isSelected()==false)&&(cbxM5.isSelected()==false)&&(cbxT1.isSelected()==false)&&(cbxT2.isSelected()==false)&&(cbxT3.isSelected()==false)&&(cbxT4.isSelected()==false)&&(cbxT5.isSelected()==false)&&(cbxT6.isSelected()==false)&&(cbxN1.isSelected()==false)&&(cbxN2.isSelected()==false)&&(cbxN3.isSelected()==false)&&(cbxN4.isSelected()==false)&&(cbxN5.isSelected()==false)){
            //Menssagem de erro
            JOptionPane.showMessageDialog(null, "Erro não foi selecionado nunhum periodo" );
        }else{
            //Verificação da marcação da caixa de seleção 
            if (cbxM1.isSelected()){
                //Chamando função para cadastrar por periodo e passando o periodo selecionado
                cadReservaPeriodo("M1");
                //Desmarcando seleção da caixa
                cbxM1.setSelected(false);
            }
            //Verificação da marcação da caixa de seleção 
            if (cbxM2.isSelected()){
                //Chamando função para cadastrar por periodo e passando o periodo selecionado
                cadReservaPeriodo("M2");
                //Desmarcando seleção da caixa
                cbxM2.setSelected(false);
            }
            //Verificação da marcação da caixa de seleção 
            if (cbxM3.isSelected()){
                //Chamando função para cadastrar por periodo e passando o periodo selecionado
                cadReservaPeriodo("M3");
                //Desmarcando seleção da caixa
                cbxM3.setSelected(false);
            }
            //Verificação da marcação da caixa de seleção 
            if (cbxM4.isSelected()){
                //Chamando função para cadastrar por periodo e passando o periodo selecionado
                cadReservaPeriodo("M4");
                //Desmarcando seleção da caixa
                cbxM4.setSelected(false);
            }
            //Verificação da marcação da caixa de seleção 
            if (cbxM5.isSelected()){
                //Chamando função para cadastrar por periodo e passando o periodo selecionado
                cadReservaPeriodo("M5");
                //Desmarcando seleção da caixa
                cbxM5.setSelected(false);
            }
            //Verificação da marcação da caixa de seleção 
            if (cbxT1.isSelected()){
                //Chamando função para cadastrar por periodo e passando o periodo selecionado
                cadReservaPeriodo("T1");
                //Desmarcando seleção da caixa
                cbxT1.setSelected(false);
            }
            //Verificação da marcação da caixa de seleção 
            if (cbxT2.isSelected()){
                //Chamando função para cadastrar por periodo e passando o periodo selecionado
                cadReservaPeriodo("T2");
                //Desmarcando seleção da caixa
                cbxT2.setSelected(false);
            }
            //Verificação da marcação da caixa de seleção 
            if (cbxT3.isSelected()){
                //Chamando função para cadastrar por periodo e passando o periodo selecionado
                cadReservaPeriodo("T3");
                //Desmarcando seleção da caixa
                cbxT3.setSelected(false);
            }
            //Verificação da marcação da caixa de seleção 
            if (cbxT4.isSelected()){
                //Chamando função para cadastrar por periodo e passando o periodo selecionado
                cadReservaPeriodo("T4");
                //Desmarcando seleção da caixa
                cbxT4.setSelected(false);
            }
            //Verificação da marcação da caixa de seleção 
            if (cbxT5.isSelected()){
                //Chamando função para cadastrar por periodo e passando o periodo selecionado
                cadReservaPeriodo("T5");
                //Desmarcando seleção da caixa
                cbxT5.setSelected(false);
            }
            //Verificação da marcação da caixa de seleção 
            if (cbxT6.isSelected()){
                //Chamando função para cadastrar por periodo e passando o periodo selecionado
                cadReservaPeriodo("T6");
                //Desmarcando seleção da caixa
                cbxT6.setSelected(false);
            }
            //Verificação da marcação da caixa de seleção 
            if (cbxN1.isSelected()){
                //Chamando função para cadastrar por periodo e passando o periodo selecionado
                cadReservaPeriodo("N1");
                //Desmarcando seleção da caixa
                cbxN1.setSelected(false);
            }
            //Verificação da marcação da caixa de seleção 
            if (cbxN2.isSelected()){
                //Chamando função para cadastrar por periodo e passando o periodo selecionado
                cadReservaPeriodo("N2");
                //Desmarcando seleção da caixa
                cbxN2.setSelected(false);
            }
            //Verificação da marcação da caixa de seleção 
            if (cbxN3.isSelected()){
                //Chamando função para cadastrar por periodo e passando o periodo selecionado
                cadReservaPeriodo("N3");
                //Desmarcando seleção da caixa
                cbxN3.setSelected(false);
            }
            //Verificação da marcação da caixa de seleção 
            if (cbxN4.isSelected()){
                //Chamando função para cadastrar por periodo e passando o periodo selecionado
                cadReservaPeriodo("N4");
                //Desmarcando seleção da caixa
                cbxN4.setSelected(false);
            }
            //Verificação da marcação da caixa de seleção 
            if (cbxN5.isSelected()){
                //Chamando função para cadastrar por periodo e passando o periodo selecionado
                cadReservaPeriodo("N5");
                //Desmarcando seleção da caixa
                cbxN5.setSelected(false);
            }
        }
        new TelaReservaView().show();
        dispose();
    }//GEN-LAST:event_btnSalvarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CadastroReserva.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CadastroReserva.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CadastroReserva.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CadastroReserva.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CadastroReserva().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnNovoR;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JButton btnVoltar;
    private javax.swing.JComboBox<String> cbBloco;
    private javax.swing.JComboBox<String> cbContribuicao;
    private javax.swing.JComboBox<String> cbSala;
    private javax.swing.JCheckBox cbxM1;
    private javax.swing.JCheckBox cbxM2;
    private javax.swing.JCheckBox cbxM3;
    private javax.swing.JCheckBox cbxM4;
    private javax.swing.JCheckBox cbxM5;
    private javax.swing.JCheckBox cbxN1;
    private javax.swing.JCheckBox cbxN2;
    private javax.swing.JCheckBox cbxN3;
    private javax.swing.JCheckBox cbxN4;
    private javax.swing.JCheckBox cbxN5;
    private javax.swing.JCheckBox cbxT1;
    private javax.swing.JCheckBox cbxT2;
    private javax.swing.JCheckBox cbxT3;
    private javax.swing.JCheckBox cbxT4;
    private javax.swing.JCheckBox cbxT5;
    private javax.swing.JCheckBox cbxT6;
    private com.toedter.calendar.JDateChooser dtcDate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JTextField txtDocumento;
    private javax.swing.JTextField txtNome;
    private javax.swing.JTextField txtObs;
    private javax.swing.JLabel txtServ;
    private javax.swing.JTextField txtTelefone;
    // End of variables declaration//GEN-END:variables
}
