package TokenRing;

import TokenRing.MessageQueue;
import java.awt.Color;

public class JanelaChat extends javax.swing.JFrame {

    private MessageQueue fila= new MessageQueue();
    private String token = "";

    public JanelaChat() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        rotuloEstacao = new javax.swing.JLabel();
        rotuloPorta = new javax.swing.JLabel();
        campoTextoPorta = new javax.swing.JTextField();
        rotuloToken = new javax.swing.JLabel();
        campoTextoToken = new javax.swing.JTextField();
        campoTextoFila = new javax.swing.JTextField();
        rotuloFila = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        areaTextoMsg = new javax.swing.JTextArea();
        rotuloMensagem = new javax.swing.JLabel();
        campoTextoCadastrarEstacao = new javax.swing.JTextField();
        botaoCadastrarEstacao = new javax.swing.JButton();
        campoTextoNovaMensagem = new javax.swing.JTextField();
        botaoEnviarMsg = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        areaTextoLog = new javax.swing.JTextArea();
        rotuloMensagem1 = new javax.swing.JLabel();
        comboBoxEstacao = new javax.swing.JComboBox<String>();
        rotuloCadastroEstacao1 = new javax.swing.JLabel();
        rotuloNovaMensagem = new javax.swing.JLabel();
        campoTextoNickname = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Chat UDP Rede Anel");
        setResizable(false);

        rotuloEstacao.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        rotuloEstacao.setText("Estação");
        rotuloEstacao.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        rotuloPorta.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        rotuloPorta.setText("Porta");
        rotuloPorta.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        campoTextoPorta.setEditable(false);
        campoTextoPorta.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        campoTextoPorta.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        campoTextoPorta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoTextoPortaActionPerformed(evt);
            }
        });

        rotuloToken.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        rotuloToken.setText("Token inicial");
        rotuloToken.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        campoTextoToken.setEditable(false);
        campoTextoToken.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        campoTextoToken.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        campoTextoToken.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoTextoTokenActionPerformed(evt);
            }
        });

        campoTextoFila.setEditable(false);
        campoTextoFila.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        campoTextoFila.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        campoTextoFila.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoTextoFilaActionPerformed(evt);
            }
        });

        rotuloFila.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        rotuloFila.setText("Fila");
        rotuloFila.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        areaTextoMsg.setEditable(false);
        areaTextoMsg.setColumns(20);
        areaTextoMsg.setRows(5);
        jScrollPane1.setViewportView(areaTextoMsg);

        rotuloMensagem.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        rotuloMensagem.setText("   Log");
        rotuloMensagem.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        campoTextoCadastrarEstacao.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        campoTextoCadastrarEstacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoTextoCadastrarEstacaoActionPerformed(evt);
            }
        });

        botaoCadastrarEstacao.setText("Cadastrar");
        botaoCadastrarEstacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoCadastrarEstacaoActionPerformed(evt);
            }
        });

        campoTextoNovaMensagem.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        botaoEnviarMsg.setText("Enviar Mensagem");
        botaoEnviarMsg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoEnviarMsgActionPerformed(evt);
            }
        });

        areaTextoLog.setColumns(20);
        areaTextoLog.setRows(5);
        jScrollPane2.setViewportView(areaTextoLog);

        rotuloMensagem1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        rotuloMensagem1.setText("   Mensagens");
        rotuloMensagem1.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        comboBoxEstacao.setModel(new javax.swing.DefaultComboBoxModel<String>());

        rotuloCadastroEstacao1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        rotuloCadastroEstacao1.setText("Cadastrar Estação");
        rotuloCadastroEstacao1.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        rotuloNovaMensagem.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        rotuloNovaMensagem.setText(" Nova mensagem para:");

        campoTextoNickname.setEditable(false);
        campoTextoNickname.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        campoTextoNickname.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        campoTextoNickname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoTextoNicknameActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rotuloCadastroEstacao1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(23, 23, 23)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(rotuloFila, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(campoTextoFila, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(rotuloToken, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(campoTextoToken, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(rotuloPorta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(campoTextoPorta, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(rotuloEstacao, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(campoTextoNickname, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(campoTextoCadastrarEstacao)
                            .addComponent(botaoCadastrarEstacao, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE))
                        .addGap(21, 21, 21)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(rotuloMensagem1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 403, Short.MAX_VALUE))
                    .addComponent(rotuloNovaMensagem, javax.swing.GroupLayout.PREFERRED_SIZE, 402, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(botaoEnviarMsg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(comboBoxEstacao, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(campoTextoNovaMensagem, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rotuloMensagem, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(47, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rotuloMensagem1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rotuloMensagem, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(rotuloEstacao, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(campoTextoNickname, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(rotuloPorta, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(campoTextoPorta, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(23, 23, 23)
                                .addComponent(rotuloToken, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(campoTextoToken, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(rotuloFila, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(campoTextoFila, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(8, 8, 8)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(rotuloCadastroEstacao1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(rotuloNovaMensagem))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(campoTextoCadastrarEstacao, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(botaoCadastrarEstacao))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(31, 31, 31)
                                        .addComponent(campoTextoNovaMensagem, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(comboBoxEstacao, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(botaoEnviarMsg))))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 378, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        rotuloEstacao.getAccessibleContext().setAccessibleName("");
        rotuloPorta.getAccessibleContext().setAccessibleName("");
        rotuloToken.getAccessibleContext().setAccessibleName("");
        rotuloMensagem.getAccessibleContext().setAccessibleName("Log");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void campoTextoPortaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campoTextoPortaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_campoTextoPortaActionPerformed

    private void campoTextoTokenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campoTextoTokenActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_campoTextoTokenActionPerformed

    private void campoTextoFilaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campoTextoFilaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_campoTextoFilaActionPerformed

    private void campoTextoCadastrarEstacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campoTextoCadastrarEstacaoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_campoTextoCadastrarEstacaoActionPerformed

    private void botaoCadastrarEstacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoCadastrarEstacaoActionPerformed
        if (campoTextoCadastrarEstacao.getText().equalsIgnoreCase("") == false) {
            comboBoxEstacao.addItem(campoTextoCadastrarEstacao.getText().toUpperCase());
            campoTextoCadastrarEstacao.setText(null);      
            
        }
    }//GEN-LAST:event_botaoCadastrarEstacaoActionPerformed

    private void botaoEnviarMsgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoEnviarMsgActionPerformed
        if((campoTextoNovaMensagem.getText().equals("") == false)&&(comboBoxEstacao.getSelectedItem().toString() != null )){
            String estacao  = campoTextoNickname.getText();
        String destino  = comboBoxEstacao.getSelectedItem().toString();
        String mensagem = campoTextoNovaMensagem.getText();
        String aux = "4066;" +estacao+":"+destino+":"+mensagem;
        System.out.println(aux);
        fila.AddMessage(aux);
        campoTextoFila.setText(""+(Integer.parseInt(campoTextoFila.getText())+1));
        
        campoTextoNovaMensagem.setText(null);
        }
    }//GEN-LAST:event_botaoEnviarMsgActionPerformed

    private void campoTextoNicknameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campoTextoNicknameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_campoTextoNicknameActionPerformed

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {                
                new JanelaChat().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea areaTextoLog;
    private javax.swing.JTextArea areaTextoMsg;
    private javax.swing.JButton botaoCadastrarEstacao;
    private javax.swing.JButton botaoEnviarMsg;
    private javax.swing.JTextField campoTextoCadastrarEstacao;
    private javax.swing.JTextField campoTextoFila;
    private javax.swing.JTextField campoTextoNickname;
    private javax.swing.JTextField campoTextoNovaMensagem;
    private javax.swing.JTextField campoTextoPorta;
    private javax.swing.JTextField campoTextoToken;
    private javax.swing.JComboBox<String> comboBoxEstacao;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel rotuloCadastroEstacao1;
    private javax.swing.JLabel rotuloEstacao;
    private javax.swing.JLabel rotuloFila;
    private javax.swing.JLabel rotuloMensagem;
    private javax.swing.JLabel rotuloMensagem1;
    private javax.swing.JLabel rotuloNovaMensagem;
    private javax.swing.JLabel rotuloPorta;
    private javax.swing.JLabel rotuloToken;
    // End of variables declaration//GEN-END:variables

    
    
    public void setToken(boolean token){             
            campoTextoToken.setText(((token==true)? "Sim":"Não"));   
    }
    
    public void setPorta(int porta){
        campoTextoPorta.setText(""+porta);       
    }
    
       
    public void addMensagem(String msg){
        areaTextoMsg.append(msg+"\n");
    }
    
    public void addLog(String log){
        areaTextoLog.append(log+"\n");
    }
    
    public void setNicname(String nickname){
         campoTextoNickname.setText(nickname);                
    }
    
    public void novaFila(MessageQueue fila){
        this.fila = fila;
    }
    
    public void setTamanhoFila(int tamanho){
        campoTextoFila.setText(""+tamanho);
    }
        
}


