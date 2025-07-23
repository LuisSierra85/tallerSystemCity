/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import controlador.Users;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;


/**
 *
 * @author Luis Sierra
 */
public class Admin_Users_Form extends javax.swing.JFrame {

    /**
     * Creates new form Admin_Users_Form
     */
    
    //para el reporte public static VentanaPrincipal ventanaPrincipal;
    
    //public static Admin_Users_Form ventanaPrincipal;
     private Integer productId;
    // get the image path
    String imagePth = null;
    
    int pos = 0;
    
       
    public Admin_Users_Form() {
        initComponents();
        //cargo la tabla que nos trae y nos muestra los registros de la base de datos contenidos en la trabla users
        populateJtable();
        
         jTable_USERS.setShowGrid(true);
        
        jTable_USERS.setGridColor(Color.GREEN);
        
        jTable_USERS.setSelectionBackground(Color.gray);
        
        JTableHeader th = jTable_USERS.getTableHeader();

        th.setFont(new Font("Tahoma", Font.PLAIN, 16));
        
      
    }

    
    // función para crear la tabla User
    public void populateJtable(){
        
        controlador.Users user = new controlador.Users();
        ArrayList<controlador.Users> userList = user.UsersList();
        
        String[] colNames = {"Id","Username","Password","Fullname","Tel","Gender","Picture"};
      
        Object[][] rows = new Object[userList.size()][7];
       //ciclo for como está en el constructor de la clase Users
        for(int i = 0; i < userList.size(); i++){
            rows[i][0] = userList.get(i).getId();
            rows[i][1] = userList.get(i).getUsername();
            rows[i][2] = userList.get(i).getPassword();
            rows[i][3] = userList.get(i).getFullname();                    
            rows[i][4] = userList.get(i).getPhone();
            rows[i][5] = userList.get(i).getGender();
            //manejo de la imagen en una tabla table no base de datos
          ImageIcon pic = new ImageIcon(new ImageIcon
                                          (userList.get(i).getPicture())
                                           .getImage()
                                           .getScaledInstance(120, 80, Image.SCALE_SMOOTH));            
           rows[i][6] = pic;
        }
        
         modelo.MyTableModel mmd = new modelo.MyTableModel(rows, colNames);
        jTable_USERS.setModel(mmd);
        jTable_USERS.setRowHeight(80);
        jTable_USERS.getColumnModel().getColumn(0).setPreferredWidth(60);
        jTable_USERS.getColumnModel().getColumn(6).setPreferredWidth(120);
        
        
    }
     // Show Data In Inputs para el movimiento dentro de la tabla
    public void ShowItem(int index)
    {
           jTextField_ID.setText(Integer.toString(getUsersList().get(index).getId()));
           jTextField_PASSWORD.setText(getUsersList().get(index).getFullname());
           jTextField_USERNAME.setText(getUsersList().get(index).getUsername());
           jTextField_FULLNAME.setText(getUsersList().get(index).getPassword());
           jTextField_PHONE.setText(getUsersList().get(index).getPhone());
          
                //Ciclo para el genero
               if(getUsersList().get(index).getGender().equals("Male")){
                   jRadioButton_MALE.setSelected(true);
                   jRadioButton_FEMALE.setSelected(false);  
                   }
                 else if(getUsersList().get(index).getGender().equals("Female")){
                     jRadioButton_MALE.setSelected(false);
                    jRadioButton_FEMALE.setSelected(true);  
         }
       
       jLabel_PICTURE.setIcon(ResizeImage(null, getUsersList().get(index).getPicture()));
    }
    
    
    // Función para vericar todos los campos  
    public boolean verifyFields()
    {
        // Campos de texto
        String fname = jTextField_FULLNAME.getText();
        String uname = jTextField_USERNAME.getText();
        String phone = jTextField_PHONE.getText();
        String pass1 = String.valueOf(jTextField_PASSWORD.getPassword());
        
        // Verificación de campos vacíos
        if(fname.trim().equals("") || uname.trim().equals("") || phone.trim().equals("")
           || pass1.trim().equals("") )
        {
            JOptionPane.showMessageDialog(null, "One Or More Fields Are Empty","Empty Fields",2);
            return false;
        }
        
               
        // if everything is ok
        else{
            return true;
        }
    
    
    }
    
    
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable_USERS = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jTextField_ID = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTextField_FULLNAME = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextField_USERNAME = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTextField_PHONE = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jRadioButton_MALE = new javax.swing.JRadioButton();
        jRadioButton_FEMALE = new javax.swing.JRadioButton();
        jLabel_PICTURE = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jButton_BROWSE_PIC = new javax.swing.JButton();
        jButton_update = new javax.swing.JButton();
        Btn_First = new javax.swing.JButton();
        Btn_Next = new javax.swing.JButton();
        Btn_Previous = new javax.swing.JButton();
        Btn_Last = new javax.swing.JButton();
        jTextField_PASSWORD = new javax.swing.JPasswordField();
        jButton_delete1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Admin Users");
        setBackground(new java.awt.Color(0, 0, 255));
        setPreferredSize(new java.awt.Dimension(1050, 550));

        jPanel1.setBackground(new java.awt.Color(204, 204, 255));

        jTable_USERS.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable_USERSMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable_USERS);

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("  ID:");

        jTextField_ID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_IDActionPerformed(evt);
            }
        });

        jLabel2.setText("FULL NAME:");

        jLabel3.setText("USER NAME:");

        jLabel4.setText("PASSWORD:");

        jLabel5.setText("PHONE:");

        jLabel6.setText("GENDER:");

        jRadioButton_MALE.setText("MALE");

        jRadioButton_FEMALE.setText("FEMALE");

        jLabel_PICTURE.setBackground(new java.awt.Color(204, 255, 204));
        jLabel_PICTURE.setOpaque(true);

        jLabel7.setText(" PICTURE");

        jButton_BROWSE_PIC.setText("Select a Picture");
        jButton_BROWSE_PIC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_BROWSE_PICActionPerformed(evt);
            }
        });

        jButton_update.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton_update.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/update_1.png"))); // NOI18N
        jButton_update.setText("Update");
        jButton_update.setIconTextGap(15);
        jButton_update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_updateActionPerformed(evt);
            }
        });

        Btn_First.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        Btn_First.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/first.png"))); // NOI18N
        Btn_First.setText("First");
        Btn_First.setIconTextGap(15);
        Btn_First.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_FirstActionPerformed(evt);
            }
        });

        Btn_Next.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        Btn_Next.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/next.png"))); // NOI18N
        Btn_Next.setText("Next");
        Btn_Next.setIconTextGap(15);
        Btn_Next.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_NextActionPerformed(evt);
            }
        });

        Btn_Previous.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        Btn_Previous.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/previous.png"))); // NOI18N
        Btn_Previous.setText("Previous");
        Btn_Previous.setIconTextGap(15);
        Btn_Previous.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_PreviousActionPerformed(evt);
            }
        });

        Btn_Last.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        Btn_Last.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/last.png"))); // NOI18N
        Btn_Last.setText("Last");
        Btn_Last.setIconTextGap(15);
        Btn_Last.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_LastActionPerformed(evt);
            }
        });

        jButton_delete1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton_delete1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/delete_1.png"))); // NOI18N
        jButton_delete1.setText("Delete");
        jButton_delete1.setIconTextGap(15);
        jButton_delete1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_delete1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField_PHONE)
                            .addComponent(jTextField_USERNAME)
                            .addComponent(jTextField_FULLNAME)
                            .addComponent(jTextField_ID)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jRadioButton_MALE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jRadioButton_FEMALE, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel_PICTURE, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton_BROWSE_PIC, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField_PASSWORD))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(74, 74, 74)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 706, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(253, 253, 253)
                .addComponent(jButton_update)
                .addGap(27, 27, 27)
                .addComponent(jButton_delete1)
                .addGap(18, 18, 18)
                .addComponent(Btn_First)
                .addGap(18, 18, 18)
                .addComponent(Btn_Next)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Btn_Previous)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Btn_Last)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 387, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jTextField_ID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jTextField_FULLNAME, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jTextField_USERNAME, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jTextField_PASSWORD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jTextField_PHONE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jRadioButton_MALE)
                            .addComponent(jRadioButton_FEMALE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel_PICTURE, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton_BROWSE_PIC)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Btn_Last)
                    .addComponent(Btn_Previous)
                    .addComponent(Btn_Next)
                    .addComponent(Btn_First)
                    .addComponent(jButton_update)
                    .addComponent(jButton_delete1))
                .addGap(58, 58, 58))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(21, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jTable_USERSMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable_USERSMouseClicked
        // TODO add your handling code here:
        
         Integer rowIndex = jTable_USERS.getSelectedRow();
         
        productId = Integer.valueOf(jTable_USERS.getValueAt(rowIndex, 0).toString());
        jTextField_ID.setText(jTable_USERS.getValueAt(rowIndex, 0).toString());
        jTextField_FULLNAME.setText(jTable_USERS.getValueAt(rowIndex, 3).toString());
        jTextField_USERNAME.setText(jTable_USERS.getValueAt(rowIndex, 1).toString());
        jTextField_PASSWORD.setText(jTable_USERS.getValueAt(rowIndex, 2).toString());
       
        jTextField_PHONE.setText(jTable_USERS.getValueAt(rowIndex, 4).toString());
        String genero=(jTable_USERS.getValueAt(rowIndex, 5).toString());
        
         // mostrar la imagen en el jlabel
         //esta linea primera es la imagen de mi jTable_USERS
            ImageIcon image1 = (ImageIcon)jTable_USERS.getValueAt(rowIndex, 6);
            Image image2 = image1.getImage().getScaledInstance(jLabel_PICTURE.getWidth(), jLabel_PICTURE.getHeight()
                     , Image.SCALE_SMOOTH);
            ImageIcon image3 = new ImageIcon(image2);
            jLabel_PICTURE.setIcon(image3);
        
             
        
        // Verificar género
        if(genero.equals("Male")){
           jRadioButton_MALE.setSelected(true);
            jRadioButton_FEMALE.setSelected(false);  
         }
        else if(genero.equals("Female")){
            jRadioButton_MALE.setSelected(false);
            jRadioButton_FEMALE.setSelected(true);  
         }
        
        else {
             jRadioButton_MALE.setSelected(false);
            jRadioButton_FEMALE.setSelected(false);  
        }
                
        
        
    }//GEN-LAST:event_jTable_USERSMouseClicked

    private void jButton_BROWSE_PICActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_BROWSE_PICActionPerformed
        // TODO add your handling code here:
        modelo.MisMetodos mf = new modelo.MisMetodos();
        
        //jLabel_PICTURE
        imagePth = mf.browseImage(jLabel_PICTURE);
        System.out.println(imagePth);
        
        
    }//GEN-LAST:event_jButton_BROWSE_PICActionPerformed

    private void jButton_updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_updateActionPerformed
        // TODO add your handling code here:
         String fname = jTextField_FULLNAME.getText();
         String username = jTextField_USERNAME.getText();
         String pass1 = String.valueOf(jTextField_PASSWORD.getPassword());
         String phone = jTextField_PHONE.getText();
         //crear el objeto para enviar los datos del formulario al metodo  public static void insertUser(Users user)
         String gender = "Male";
         controlador.Users user;
         
         //encriptar la clave
         String sha1 = "";
         // With the java libraries
	try {
                        MessageDigest digest = MessageDigest.getInstance("SHA-1");
	        digest.reset();
                //enviar el nombre de la variable que me recibe el password para este caso es pass1
	        digest.update(pass1.getBytes("utf8"));
	        sha1 = String.format("%040x", new BigInteger(1, digest.digest()));
	} catch (Exception e){
	    e.printStackTrace();
	}
            if(jRadioButton_FEMALE.isSelected()){
             gender = "Female";
               }
            
             if(verifyFields()){
                 
                                 
                  //si cambia la foto
                  if(imagePth != null)
                      { 
                 
                  Path path = Paths.get(imagePth);  
                  
             try {

                byte[] img;

                img = Files.readAllBytes(path);
                      
          
                user =new controlador.Users(productId,username,sha1,fname,phone,gender,img);
                     
                       // Si cambia la foto le digo que es true si la cambió 
                       // Llamar al método que actualiza
                    controlador.Users.updateUsers(user, true);
                    populateJtable();
                    JOptionPane.showMessageDialog(null, "Users Updated");
                    
                     } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "nada");
                        }
                    //cierra  if(image_path != null)     
                    }
            
                      else{
                      //si no cambia la foto se envia a guardar un null para conservar la foto actual
                          user =new controlador.Users(productId,username,sha1,fname,phone,gender,null);
                      // si no la cambia le digo que es false
                      controlador.Users.updateUsers(user, false);
                      populateJtable();
                      JOptionPane.showMessageDialog(null, "Users Updated");
                         
                      }   
                         
             //cierre el if(verifyFields())
             }
        
        
        
    }//GEN-LAST:event_jButton_updateActionPerformed

    private void Btn_FirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_FirstActionPerformed
        // TODO add your handling code here:
        
        pos = 0;
        ShowItem(pos);
        
        
    }//GEN-LAST:event_Btn_FirstActionPerformed

    private void Btn_NextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_NextActionPerformed
        // TODO add your handling code here:
        
         pos++;
        
        if(pos >= getUsersList().size())
        {
            pos = getUsersList().size()-1;
        }
        
        ShowItem(pos);
        
        
        
    }//GEN-LAST:event_Btn_NextActionPerformed

    private void Btn_PreviousActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_PreviousActionPerformed
        // TODO add your handling code here:
        
          pos--;
        
        if(pos < 0)
        {
            pos = 0;
        }
        
        ShowItem(pos);
        
        
        
        
    }//GEN-LAST:event_Btn_PreviousActionPerformed

    private void Btn_LastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_LastActionPerformed
        // TODO add your handling code here:
        // pos = getUsersList().size()-1;
          pos = getUsersList().size()-1;
          ShowItem(pos);
        
        
        
    }//GEN-LAST:event_Btn_LastActionPerformed

    private void jButton_delete1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_delete1ActionPerformed
        // TODO add your handling code here:
        //jTextField_ID
          if(!jTextField_ID.getText().equals(""))
        {
            try {
                int id_user = Integer.parseInt(jTextField_ID.getText());
                controlador.Users.deleteUsers(id_user);
                populateJtable();
               
            } catch (Exception ex) {
                Logger.getLogger(Admin_Users_Form.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "Users Not Deleted");
            }

        }else{
            JOptionPane.showMessageDialog(null, "Users Not Deleted : No Id To Delete");
        }
        
                
    }//GEN-LAST:event_jButton_delete1ActionPerformed

    private void jTextField_IDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_IDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_IDActionPerformed

    
    
    public ArrayList<Users> getUsersList()
    {
            ArrayList<Users> userList  = new ArrayList<Users>();
          //  Connection con = getConnection();
            Connection con = modelo.Conexion_DB.getConnection();
            String query = "SELECT * FROM users";
            
            Statement st;
            ResultSet rs;
            
        try {
            
            st = con.createStatement();
            rs = st.executeQuery(query);
            Users user;
            
            while(rs.next())
            { //cargo cada registro llamándolo desde la tabla con sus respectivos nombres de campos
                 user = new  Users(rs.getInt("id"),rs.getString("full_name"),rs.getString("username"),rs.getString("password"),rs.getString("phone"),rs.getString("gender"), rs.getBytes("picture"));
                 userList.add(user);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Admin_Users_Form.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return userList; 
                
    }
    
     // Function To Resize The Image To Fit Into JLabel
    public ImageIcon ResizeImage(String imagePath, byte[] pic)
    {
        ImageIcon myImage = null;
        
        if(imagePath != null)
        {
            myImage = new ImageIcon(imagePath);
        }else{
            myImage = new ImageIcon(pic);
        }
        
        Image img = myImage.getImage();
        Image img2 = img.getScaledInstance(jLabel_PICTURE.getWidth(),jLabel_PICTURE.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(img2);
        return image;
        
    }
    
    
    
    
    
    
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
            java.util.logging.Logger.getLogger(Admin_Users_Form.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Admin_Users_Form.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Admin_Users_Form.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Admin_Users_Form.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Admin_Users_Form().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Btn_First;
    private javax.swing.JButton Btn_Last;
    private javax.swing.JButton Btn_Next;
    private javax.swing.JButton Btn_Previous;
    private javax.swing.JButton jButton_BROWSE_PIC;
    private javax.swing.JButton jButton_delete1;
    private javax.swing.JButton jButton_update;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel_PICTURE;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JRadioButton jRadioButton_FEMALE;
    private javax.swing.JRadioButton jRadioButton_MALE;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable_USERS;
    private javax.swing.JTextField jTextField_FULLNAME;
    private javax.swing.JTextField jTextField_ID;
    private javax.swing.JPasswordField jTextField_PASSWORD;
    private javax.swing.JTextField jTextField_PHONE;
    private javax.swing.JTextField jTextField_USERNAME;
    // End of variables declaration//GEN-END:variables
}
