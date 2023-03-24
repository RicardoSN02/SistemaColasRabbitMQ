/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.sistemascolasreportes;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import entidades.Producto;
import entidades.Reporte;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author rjsaa
 */
public class SistemaReportesFrm extends javax.swing.JFrame {

    ArrayList<Producto> productos = new ArrayList<>();
    ArrayList<Reporte> reportes = new ArrayList<>();
    ConnectionFactory factory = new ConnectionFactory();
    Connection connection = null;    
    Channel channelReportes = null;
    Channel channelProductos = null;
    public static final String QUEUE_NAME = "ColaReportes";
    public static final String QUEUE_NAME2 = "ColaProductos";
    
    
    
    /**
     * Creates new form SistemaFrm
     */
    public SistemaReportesFrm() {
        initComponents();
        
        Producto producto1 = new Producto(1, "television smart", "22 pulgadas");
        Producto producto2 = new Producto(2, "television smart", "30 pulgadas");
        Producto producto3 = new Producto(3, "television smart", "34 pulgadas");
        

        productos.add(producto1);
        productos.add(producto2);
        productos.add(producto3);
        

        this.llenarTablaSeleccionadas();
        
        factory.setHost("localhost");
        
        try{
        connection = factory.newConnection();
        channelReportes = connection.createChannel();
        channelReportes.queueDeclare(QUEUE_NAME,false,false,false,null);
        channelProductos = connection.createChannel();
        channelProductos.queueDeclare(QUEUE_NAME2,false,false,false,null);
        
        DeliverCallback deliverCallback = (consumerTag,delivery)->{
            try {
                ByteArrayInputStream bs= new ByteArrayInputStream(delivery.getBody());
                ObjectInputStream is = new ObjectInputStream(bs);
                Producto obj = (Producto)is.readObject();
                System.out.println(obj.toString());
                this.agregarProducto(obj);
                is.close();
                
            } catch (Exception ex) {
            
            }
            
        };
            
        channelProductos.basicConsume(QUEUE_NAME2, true,deliverCallback,consumerTag ->{
                
        });
        
        }catch(Exception e ){
            
        }
    }
    
    private Producto getNombreSeleccionado() {
        int indiceFilaSeleccionada = this.tblProductos.getSelectedRow();
        if (indiceFilaSeleccionada != -1) {
            DefaultTableModel modeloTabla = (DefaultTableModel) this.tblProductos.getModel();
            int indiceColumnaNombre = 0;
            String nombreSeleccionado = (String) modeloTabla.getValueAt(indiceFilaSeleccionada, indiceColumnaNombre);
            String descripcionSeleccionado = (String) modeloTabla.getValueAt(indiceFilaSeleccionada, indiceColumnaNombre + 1);
            Producto producto = new Producto(nombreSeleccionado, descripcionSeleccionado);
            return producto;
        } else {
            return null;
        }
    }

    private void llenarTablaSeleccionadas() {
        List<Producto> listaProductos = productos;

        DefaultTableModel modeloTabla = (DefaultTableModel) this.tblProductos.getModel();

        modeloTabla.setRowCount(0);

        listaProductos.forEach(producto -> {
            Object[] fila = new Object[2];
            fila[0] = producto.getNombre();
            fila[1] = producto.getDescripcion();
            modeloTabla.addRow(fila);
        });
    }

    
   public void agregarProducto(Producto producto) {

        productos.add(producto);

        this.llenarTablaSeleccionadas();
    }
   
   
   
    
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblProductos = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtAreaQueja = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tblProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nombre", "Descripcion "
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblProductos);
        if (tblProductos.getColumnModel().getColumnCount() > 0) {
            tblProductos.getColumnModel().getColumn(0).setResizable(false);
            tblProductos.getColumnModel().getColumn(1).setResizable(false);
        }

        jButton1.setText("Enviar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setText("Queja o reporte: ");

        txtAreaQueja.setColumns(20);
        txtAreaQueja.setRows(5);
        jScrollPane2.setViewportView(txtAreaQueja);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 477, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 477, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addComponent(jButton1)))
                .addContainerGap(31, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        Producto producto = this.getNombreSeleccionado();
        
        if(!producto.equals(null)){
        
            ObjectOutputStream os= null;
            try {
                
                Reporte reporte = new Reporte();
                
                reporte.setProducto(producto);
                reporte.setComentario(this.txtAreaQueja.getText());
                
                ByteArrayOutputStream bs= new ByteArrayOutputStream();
                os = new ObjectOutputStream (bs);
                os.writeObject(reporte);
                os.close();
                byte[] bytes =  bs.toByteArray();
                channelReportes.basicPublish("", QUEUE_NAME, null, bytes);
                
            } catch (IOException ex) {
                Logger.getLogger(SistemaReportesFrm.class.getName()).log(Level.SEVERE, null, ex);
            } 
            
        }else{
            
        }
        
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(SistemaReportesFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SistemaReportesFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SistemaReportesFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SistemaReportesFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SistemaReportesFrm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblProductos;
    private javax.swing.JTextArea txtAreaQueja;
    // End of variables declaration//GEN-END:variables
}