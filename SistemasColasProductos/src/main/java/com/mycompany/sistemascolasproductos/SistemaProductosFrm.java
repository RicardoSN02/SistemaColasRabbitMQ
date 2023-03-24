/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.sistemascolasproductos;

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
public class SistemaProductosFrm extends javax.swing.JFrame {

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
    public SistemaProductosFrm() {
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
                Reporte obj = (Reporte)is.readObject();
                System.out.println(obj.toString());
                this.agregarReporte(obj);
                is.close();
                
            } catch (Exception ex) {
            
            }
            
        };
            
        channelReportes.basicConsume(QUEUE_NAME, true,deliverCallback,consumerTag ->{
                
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

    
   public void agregarReporte(Producto producto) {

        productos.add(producto);

        this.llenarTablaSeleccionadas();
    }
   
   public void agregarReporte(Reporte reporte) {
        
        reportes.add(reporte);
        
        this.llenarTablaSeleccionadasReportes();
    }
    
    
    private void llenarTablaSeleccionadasReportes() {
        List<Reporte> listaReportes = reportes;

        DefaultTableModel modeloTabla = (DefaultTableModel) this.tblReportes.getModel();

        modeloTabla.setRowCount(0);

        listaReportes.forEach(reportes -> {
            Object[] fila = new Object[2];
            fila[0] = reportes.getProducto().getNombre();
            System.out.println(reportes.getComentario());
            fila[1] = reportes.getComentario();
            modeloTabla.addRow(fila);
        });
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
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtDescripcion = new javax.swing.JTextArea();
        btnAgregar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblReportes = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tblProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nombre", "Descripcion"
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

        jLabel1.setText("Nombre");

        jLabel2.setText("Descripcion: ");

        txtDescripcion.setColumns(20);
        txtDescripcion.setRows(5);
        jScrollPane2.setViewportView(txtDescripcion);

        btnAgregar.setText("Agregar producto");
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        jLabel3.setText("Reportes recibidos: ");

        tblReportes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Producto", "Queja"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane3.setViewportView(tblReportes);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(63, 63, 63)
                        .addComponent(jLabel3))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 384, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(44, 44, 44)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtNombre)
                                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 273, Short.MAX_VALUE)))
                                    .addComponent(btnAgregar))))))
                .addContainerGap(34, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(38, 38, 38)
                        .addComponent(btnAgregar))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(59, 59, 59)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        // TODO add your handling code here:

            ObjectOutputStream os= null;
            try {
                
                
                Producto producto = new Producto();
                producto.setNombre(this.txtNombre.getText());
                producto.setDescripcion(this.txtDescripcion.getText());
                
                ByteArrayOutputStream bs= new ByteArrayOutputStream();
                os = new ObjectOutputStream (bs);
                os.writeObject(producto);
                os.close();
                byte[] bytes =  bs.toByteArray();
                channelProductos.basicPublish("", QUEUE_NAME2, null, bytes);
                
            } catch (IOException ex) {
                
            } 
            
        
    }//GEN-LAST:event_btnAgregarActionPerformed

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
            java.util.logging.Logger.getLogger(SistemaProductosFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SistemaProductosFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SistemaProductosFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SistemaProductosFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SistemaProductosFrm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable tblProductos;
    private javax.swing.JTable tblReportes;
    private javax.swing.JTextArea txtDescripcion;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}
