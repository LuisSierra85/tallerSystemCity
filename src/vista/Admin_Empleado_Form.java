/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import controlador.Empleado;
import java.util.ArrayList;
import javax.swing.table.TableModel;
import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.table.JTableHeader;

/**
 *
 * @author Luis Sierra
 */
public class Admin_Empleado_Form extends javax.swing.JFrame {

    /**
     * Creates new form Admin_Empleado_Form
     */
    
    private Integer empleadoId;
    int pos = 0;
    
    
public Admin_Empleado_Form() {
        initComponents();
        //cargo la tabla con los registros de los empleados
        populateJtable();
        
         jTable_EMPLEADO.setShowGrid(true);
        
        jTable_EMPLEADO.setGridColor(Color.GREEN);
        
        jTable_EMPLEADO.setSelectionBackground(Color.gray);
        
        JTableHeader th = jTable_EMPLEADO.getTableHeader();

        th.setFont(new Font("Tahoma", Font.PLAIN, 16));
    }
    
 //función para crear la tabla de los registros de empleados 
public void populateJtable(){
        //MVC
        controlador.Empleado empleado = new controlador.Empleado();
        ArrayList<controlador.Empleado> empleadoList = empleado.EmpleadoList();
        
        String[] colNames = {"Id","Document_Number","Document_Type","Full_Names","Full_Sur_Names",
            "Position","Gender","Phone","Emergency_Contact","Email","Address","Date_of_Birth","Date_of_Entry"};
      
        Object[][] rows = new Object[empleadoList.size()][13];
       //ciclo for como está en el constructor de la clase Empleado
        for(int i = 0; i < empleadoList.size(); i++){
            rows[i][0] = empleadoList.get(i).getId();
            rows[i][1] = empleadoList.get(i).getDocumentnumber();
            rows[i][2] = empleadoList.get(i).getDocumenttype();
            rows[i][3] = empleadoList.get(i).getFullnames();                    
            rows[i][4] = empleadoList.get(i).getFullsurnames();
            rows[i][5] = empleadoList.get(i).getPosition();
            rows[i][6] = empleadoList.get(i).getGender();
            rows[i][7] = empleadoList.get(i).getPhone();
            rows[i][8] = empleadoList.get(i).getEmergencycontact();
            rows[i][9] = empleadoList.get(i).getEmail();
            rows[i][10] = empleadoList.get(i).getAddress();            
            rows[i][11] = empleadoList.get(i).getDateofbirth().toString();
            rows[i][12] = empleadoList.get(i).getDateofentry().toString();
           
        }
        //Dibuja mi tabla
       modelo.TableModelEmpleado mmd = new modelo.TableModelEmpleado(rows, colNames);
        jTable_EMPLEADO.setModel((TableModel) mmd);
        jTable_EMPLEADO.setRowHeight(40);
        jTable_EMPLEADO.getColumnModel().getColumn(0).setPreferredWidth(60);
        jTable_EMPLEADO.getColumnModel().getColumn(1).setPreferredWidth(150);
        jTable_EMPLEADO.getColumnModel().getColumn(2).setPreferredWidth(150);
        jTable_EMPLEADO.getColumnModel().getColumn(3).setPreferredWidth(120);
        jTable_EMPLEADO.getColumnModel().getColumn(4).setPreferredWidth(140);
        jTable_EMPLEADO.getColumnModel().getColumn(5).setPreferredWidth(80);
        jTable_EMPLEADO.getColumnModel().getColumn(6).setPreferredWidth(80);
        jTable_EMPLEADO.getColumnModel().getColumn(7).setPreferredWidth(90);
        jTable_EMPLEADO.getColumnModel().getColumn(8).setPreferredWidth(150);
        jTable_EMPLEADO.getColumnModel().getColumn(9).setPreferredWidth(180);
        jTable_EMPLEADO.getColumnModel().getColumn(10).setPreferredWidth(120);
        jTable_EMPLEADO.getColumnModel().getColumn(11).setPreferredWidth(120);
        jTable_EMPLEADO.getColumnModel().getColumn(12).setPreferredWidth(120);
        
    }
  //Array con todos los registros de mi tabla empleado   
public ArrayList<Empleado> getEmpleadoList(){
    
            ArrayList<Empleado> empleadolist  = new ArrayList<Empleado>();
            Connection con = modelo.Conexion_DB.getConnection();
            String query = "SELECT * FROM empleado";
           
            Statement st;
            ResultSet rs;
           
        try {
           
            st = con.createStatement();
            rs = st.executeQuery(query);
            Empleado empleado;
           
            while(rs.next()){ //cargo cada registro llamándolo desde la tabla con sus respectivos nombres de campos
                 empleado = new  Empleado(rs.getInt("id"),rs.getInt("documentnumber"),rs.getString("documenttype"),rs.getString("fullnames"),rs.getString("fullsurnames")
                         ,rs.getString("position"),rs.getString("gender"),rs.getString("phone"),rs.getString("emergencycontact"),
                         rs.getString("email"),rs.getString("address"),rs.getDate("dateofbirth").toLocalDate(),rs.getDate("dateofentry").toLocalDate());
                 empleadolist.add(empleado);
            }
           
        } catch (SQLException ex) {
            Logger.getLogger(Admin_Empleado_Form.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        return empleadolist;
               
    }
     
     
  // Show Data In Inputs para el movimiento dentro de la tabla    
public void ShowItem(int index){

    jTextField_ID.setText(Integer.toString(getEmpleadoList().get(index).getId()));
    jTextField_DOCUMENTNUMBER.setText(Integer.toString(getEmpleadoList().get(index).getDocumentnumber()));

    // Tipo de Documento
    jCheckBox_TI.setSelected(getEmpleadoList().get(index).getDocumenttype().equals("TI"));
    jCheckBox_CC.setSelected(getEmpleadoList().get(index).getDocumenttype().equals("CC"));
    jCheckBox_CE.setSelected(getEmpleadoList().get(index).getDocumenttype().equals("CE"));
    //Campos de textos 
    jTextField_FULLNAMES.setText(getEmpleadoList().get(index).getFullnames());
    jTextField_FULLSURNAMES.setText(getEmpleadoList().get(index).getFullsurnames());
    jTextField_POSITION.setText(getEmpleadoList().get(index).getFullsurnames());
    jTextField_PHONE.setText(getEmpleadoList().get(index).getPhone());
    jTextField_EMERGENCYCONTACT.setText(getEmpleadoList().get(index).getEmergencycontact());
    jTextField_EMAIL.setText(getEmpleadoList().get(index).getEmail());
    jTextField_ADDRESS.setText(getEmpleadoList().get(index).getAddress());

    // Tipo de Género
    jCheckBox_MALE.setSelected(getEmpleadoList().get(index).getGender().equals("Male"));
    jCheckBox_FEMALE.setSelected(getEmpleadoList().get(index).getGender().equals("Female"));

    // Fecha de nacimiento y de ingreso
    jXDatePicker_DATEOFBIRTH.setDate(java.sql.Date.valueOf(getEmpleadoList().get(index).getDateofbirth()));
    jXDatePicker_DATEENTRY.setDate(java.sql.Date.valueOf(getEmpleadoList().get(index).getDateofbirth()));
    }
    // Función para vericar todos los campos 
  public boolean verifyFields(){
        // Campos de texto
    String documentnumber = jTextField_DOCUMENTNUMBER.getText();
    String fullnames = jTextField_FULLNAMES.getText();
    String fullsurnames = jTextField_FULLSURNAMES.getText();
    String position = jTextField_POSITION.getText();
    String phone = jTextField_PHONE.getText();
    String emergencycontact = jTextField_EMERGENCYCONTACT.getText();
    String email = jTextField_EMAIL.getText();
    String address = jTextField_ADDRESS.getText();

    // Fecha de nacimiento y de ingreso
    java.util.Date dateOfBirth  = jXDatePicker_DATEOFBIRTH.getDate();
    java.util.Date dateOfEntry = jXDatePicker_DATEENTRY.getDate();

    // Tipo de documento
    String documenttype = "";
    if (jCheckBox_TI.isSelected()) documenttype = "TI";
    else if (jCheckBox_CC.isSelected()) documenttype = "CC";
    else if (jCheckBox_CE.isSelected()) documenttype = "CE";

    // Género
    String gender = "";
    if (jCheckBox_MALE.isSelected()) gender = "Male";
    else if (jCheckBox_FEMALE.isSelected()) gender = "Female";

    // Verificación de campos vacíos
    if (documentnumber.isEmpty() || documenttype.isEmpty() || fullnames.isEmpty() || fullsurnames.isEmpty() ||
        position.isEmpty() ||gender.isEmpty() || phone.isEmpty() || emergencycontact.isEmpty() || email.isEmpty() ||
        address.isEmpty() || dateOfBirth == null|| dateOfBirth == null ) {

        JOptionPane.showMessageDialog(null, "One Or More Fields Are Empty","Empty Fields", JOptionPane.WARNING_MESSAGE);
        return false;
    }

    return true;
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
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable_EMPLEADO = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jTextField_ID = new javax.swing.JTextField();
        jTextField_FULLNAMES = new javax.swing.JTextField();
        jTextField_FULLSURNAMES = new javax.swing.JTextField();
        jCheckBox_TI = new javax.swing.JCheckBox();
        jCheckBox_CC = new javax.swing.JCheckBox();
        jCheckBox_CE = new javax.swing.JCheckBox();
        jTextField_DOCUMENTNUMBER = new javax.swing.JTextField();
        jTextField_PHONE = new javax.swing.JTextField();
        jTextField_EMAIL = new javax.swing.JTextField();
        jCheckBox_MALE = new javax.swing.JCheckBox();
        jCheckBox_FEMALE = new javax.swing.JCheckBox();
        jXDatePicker_DATEOFBIRTH = new org.jdesktop.swingx.JXDatePicker();
        jTextField_EMERGENCYCONTACT = new javax.swing.JTextField();
        jTextField_ADDRESS = new javax.swing.JTextField();
        jButton_UPDATE = new javax.swing.JButton();
        jButton_DELETE = new javax.swing.JButton();
        jButton_INSERT = new javax.swing.JButton();
        jButton_CLEAN = new javax.swing.JButton();
        jButtonFIRTS = new javax.swing.JButton();
        jButtonLAST = new javax.swing.JButton();
        jButtonPREVIOUS = new javax.swing.JButton();
        jButtonNEXT = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jTextField_POSITION = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jXDatePicker_DATEENTRY = new org.jdesktop.swingx.JXDatePicker();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Crud Empleado");
        setBackground(new java.awt.Color(0, 0, 255));
        setPreferredSize(new java.awt.Dimension(1243, 650));
        getContentPane().setLayout(null);

        jPanel1.setBackground(new java.awt.Color(0, 0, 255));

        jPanel2.setBackground(new java.awt.Color(204, 204, 255));
        jPanel2.setMaximumSize(new java.awt.Dimension(767, 767));
        jPanel2.setPreferredSize(new java.awt.Dimension(1120, 453));

        jTable_EMPLEADO.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTable_EMPLEADO.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable_EMPLEADOMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable_EMPLEADO);

        jLabel1.setText("Id:");

        jLabel2.setText("Full Names:");

        jLabel3.setText("Full Sur Names:");

        jLabel4.setText("Document Number:");

        jLabel5.setText("Document Type:");

        jLabel6.setText("Phone:");

        jLabel7.setText("Email:");

        jLabel8.setText("Gender:");

        jLabel9.setText("Date Of Birth:");

        jLabel10.setText("Emergency Contact:");

        jLabel11.setText("Address:");

        jCheckBox_TI.setText("TI");

        jCheckBox_CC.setText("CC");

        jCheckBox_CE.setText("CE");

        jTextField_DOCUMENTNUMBER.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_DOCUMENTNUMBERActionPerformed(evt);
            }
        });

        jCheckBox_MALE.setText("Male");

        jCheckBox_FEMALE.setText("Female");

        jButton_UPDATE.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton_UPDATE.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/update_1.png"))); // NOI18N
        jButton_UPDATE.setText("Update");
        jButton_UPDATE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_UPDATEActionPerformed(evt);
            }
        });

        jButton_DELETE.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton_DELETE.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/delete_1.png"))); // NOI18N
        jButton_DELETE.setText("Delete");
        jButton_DELETE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_DELETEActionPerformed(evt);
            }
        });

        jButton_INSERT.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton_INSERT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/insert_1.png"))); // NOI18N
        jButton_INSERT.setText("Insert");
        jButton_INSERT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_INSERTActionPerformed(evt);
            }
        });

        jButton_CLEAN.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton_CLEAN.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/clean_1.png"))); // NOI18N
        jButton_CLEAN.setText("Clean");
        jButton_CLEAN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_CLEANActionPerformed(evt);
            }
        });

        jButtonFIRTS.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButtonFIRTS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/first.png"))); // NOI18N
        jButtonFIRTS.setText("Firts");
        jButtonFIRTS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonFIRTSActionPerformed(evt);
            }
        });

        jButtonLAST.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButtonLAST.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/last.png"))); // NOI18N
        jButtonLAST.setText("Last");
        jButtonLAST.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLASTActionPerformed(evt);
            }
        });

        jButtonPREVIOUS.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButtonPREVIOUS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/previous.png"))); // NOI18N
        jButtonPREVIOUS.setText("Previous");
        jButtonPREVIOUS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPREVIOUSActionPerformed(evt);
            }
        });

        jButtonNEXT.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButtonNEXT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/next.png"))); // NOI18N
        jButtonNEXT.setText("Next");
        jButtonNEXT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNEXTActionPerformed(evt);
            }
        });

        jLabel12.setText("Position:");

        jLabel13.setText("Date Of Entry:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addGap(83, 83, 83)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField_ID, javax.swing.GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE)
                            .addComponent(jTextField_FULLNAMES)
                            .addComponent(jTextField_FULLSURNAMES)
                            .addComponent(jTextField_POSITION)
                            .addComponent(jTextField_DOCUMENTNUMBER)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4)
                            .addComponent(jLabel6)
                            .addComponent(jLabel11)
                            .addComponent(jLabel12)
                            .addComponent(jLabel8)
                            .addComponent(jLabel13)
                            .addComponent(jLabel9))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(66, 66, 66)
                                .addComponent(jCheckBox_TI)
                                .addGap(18, 18, 18)
                                .addComponent(jCheckBox_CC)
                                .addGap(18, 18, 18)
                                .addComponent(jCheckBox_CE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(65, 65, 65)
                                .addComponent(jCheckBox_MALE)
                                .addGap(18, 18, 18)
                                .addComponent(jCheckBox_FEMALE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(65, 65, 65)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField_PHONE, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField_EMERGENCYCONTACT, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField_ADDRESS, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jXDatePicker_DATEOFBIRTH, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jXDatePicker_DATEENTRY, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField_EMAIL, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addComponent(jLabel10)
                    .addComponent(jLabel7))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jScrollPane1))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jButton_INSERT, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton_CLEAN, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButtonPREVIOUS)
                                .addGap(18, 18, 18)
                                .addComponent(jButtonNEXT, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jButton_UPDATE, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton_DELETE, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButtonFIRTS, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButtonLAST, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 173, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jTextField_ID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jTextField_FULLNAMES, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jTextField_FULLSURNAMES, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jCheckBox_TI)
                            .addComponent(jCheckBox_CC)
                            .addComponent(jCheckBox_CE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jTextField_DOCUMENTNUMBER, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(jTextField_POSITION, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(29, 29, 29)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jCheckBox_MALE)
                                .addComponent(jCheckBox_FEMALE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jTextField_PHONE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(jTextField_EMERGENCYCONTACT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(4, 4, 4)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jTextField_EMAIL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton_UPDATE, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jButton_DELETE)
                                .addComponent(jButtonFIRTS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButtonLAST)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton_INSERT, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton_CLEAN)
                            .addComponent(jButtonPREVIOUS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButtonNEXT))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabel11))
                            .addComponent(jTextField_ADDRESS, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addGap(35, 35, 35))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jXDatePicker_DATEOFBIRTH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(jXDatePicker_DATEENTRY, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 64, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 1198, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(12, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 558, Short.MAX_VALUE)
                .addContainerGap())
        );

        getContentPane().add(jPanel1);
        jPanel1.setBounds(10, 0, 1220, 580);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField_DOCUMENTNUMBERActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_DOCUMENTNUMBERActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_DOCUMENTNUMBERActionPerformed

    private void jTable_EMPLEADOMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable_EMPLEADOMouseClicked
        // TODO add your handling code here:
        
       int rowIndex = jTable_EMPLEADO.getSelectedRow();

    // Obtener datos de la tabla
    empleadoId = Integer.parseInt(jTable_EMPLEADO.getValueAt(rowIndex, 0).toString());
    String documentType = jTable_EMPLEADO.getValueAt(rowIndex, 2).toString();
    String genero = jTable_EMPLEADO.getValueAt(rowIndex, 6).toString();

    // Asignar valores a los campos de texto
    jTextField_ID.setText(jTable_EMPLEADO.getValueAt(rowIndex, 0).toString());
    jTextField_DOCUMENTNUMBER.setText(jTable_EMPLEADO.getValueAt(rowIndex, 1).toString());
    jTextField_FULLNAMES.setText(jTable_EMPLEADO.getValueAt(rowIndex, 3).toString());
    jTextField_FULLSURNAMES.setText(jTable_EMPLEADO.getValueAt(rowIndex, 4).toString());
    jTextField_POSITION.setText(jTable_EMPLEADO.getValueAt(rowIndex, 5).toString());
    jTextField_PHONE.setText(jTable_EMPLEADO.getValueAt(rowIndex, 7).toString());
    jTextField_EMERGENCYCONTACT.setText(jTable_EMPLEADO.getValueAt(rowIndex, 8).toString());
    jTextField_EMAIL.setText(jTable_EMPLEADO.getValueAt(rowIndex, 9).toString());
    jTextField_ADDRESS.setText(jTable_EMPLEADO.getValueAt(rowIndex, 10).toString());

    // Convertir y asignar la fecha nacimeinto
    try {
        String fechaTexto = jTable_EMPLEADO.getValueAt(rowIndex, 11).toString();
        java.util.Date fecha = java.sql.Date.valueOf(fechaTexto);
        jXDatePicker_DATEOFBIRTH.setDate(fecha);
    } catch (Exception e) {
        jXDatePicker_DATEOFBIRTH.setDate(null); // en caso de error
    }
    // Convertir y asignar la fecha ingreso
    try {
        String fechaTexto = jTable_EMPLEADO.getValueAt(rowIndex, 12).toString();
        java.util.Date fecha = java.sql.Date.valueOf(fechaTexto);
        jXDatePicker_DATEENTRY.setDate(fecha);
    } catch (Exception e) {
        jXDatePicker_DATEENTRY.setDate(null); // en caso de error
    }
    

    // Seleccionar checkbox de tipo de documento
    jCheckBox_TI.setSelected(documentType.equals("TI"));
    jCheckBox_CC.setSelected(documentType.equals("CC"));
    jCheckBox_CE.setSelected(documentType.equals("CE"));

    // Seleccionar checkbox de género
    jCheckBox_MALE.setSelected(genero.equals("Male"));
    jCheckBox_FEMALE.setSelected(genero.equals("Female"));
          
        
    }//GEN-LAST:event_jTable_EMPLEADOMouseClicked

    private void jButton_UPDATEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_UPDATEActionPerformed
        // TODO add your handling code here:
        
    try {
        // Obtener ID del paciente a actualizar
        int id = Integer.parseInt(jTextField_ID.getText());
        int documentnumber = Integer.parseInt(jTextField_DOCUMENTNUMBER.getText());

        // Determinar tipo de documento
        String documenttype = "";
        if (jCheckBox_TI.isSelected()) {
            documenttype = "TI";
        } else if (jCheckBox_CC.isSelected()) {
            documenttype = "CC";
        } else if (jCheckBox_CE.isSelected()) {
            documenttype = "CE";
        }

        String fullnames = jTextField_FULLNAMES.getText();
        String fullsurnames = jTextField_FULLSURNAMES.getText();
        String position = jTextField_POSITION.getText();
        String phone = jTextField_PHONE.getText();
        String emergencycontact = jTextField_EMERGENCYCONTACT.getText();
        String email = jTextField_EMAIL.getText();
        String address = jTextField_ADDRESS .getText();
        

        // Determinar género
        String gender = "";
        if (jCheckBox_MALE.isSelected()) {
            gender = "Male";
        } else if (jCheckBox_FEMALE.isSelected()) {
            gender = "Female";
        }


        // Fechas
        java.util.Date dob = jXDatePicker_DATEOFBIRTH.getDate();
        java.util.Date doe = jXDatePicker_DATEENTRY.getDate();

        if (dob == null || doe == null) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar ambas fechas.");
            return;
        }
        LocalDate dateofbirth = dob.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate dateofentry = doe.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();


        // Objeto Paciente
        controlador.Empleado empleado = new controlador.Empleado(
            id, documentnumber, documenttype, fullnames,
            fullsurnames, position, gender, phone,
            emergencycontact, email, address, dateofbirth, dateofentry );

        // Llamar al método que actualiza
        controlador.Empleado.updateEmpleado(empleado);

        // Refrescar tabla y mostrar mensaje
        populateJtable();
        JOptionPane.showMessageDialog(null, "Empleado correctly updated");

    } catch (Exception ex) {
        JOptionPane.showMessageDialog(null, "Error updating: " + ex.getMessage());
        ex.printStackTrace();
    }
        
        
        
        
        
    }//GEN-LAST:event_jButton_UPDATEActionPerformed

    private void jButton_DELETEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_DELETEActionPerformed
        // TODO add your handling code here:
        //Capturar el Id del paciente a eliminar de la Bd
        if(!jTextField_ID.getText().equals(""))
        {
            try {
                int id_empleado = Integer.parseInt(jTextField_ID.getText());
                // Llamar al método que elimina
                controlador.Empleado.deleteEmpleado(id_empleado);
                populateJtable();
               
            } catch (Exception ex) {
                Logger.getLogger(Admin_Empleado_Form.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "Empleado Not Deleted");
            }

        }else{
            JOptionPane.showMessageDialog(null, "Paciente Not Deleted : No Id To Delete");
        }
        
        
        
    }//GEN-LAST:event_jButton_DELETEActionPerformed

    private void jButton_INSERTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_INSERTActionPerformed
        // TODO add your handling code here:
      try {
        // Obtener ID del paciente que vas a actualizar
        int id = Integer.parseInt(jTextField_ID.getText());
        
        // Convertir número de documento
        int documentnumber = Integer.parseInt(jTextField_DOCUMENTNUMBER.getText());

        // Determinar tipo de documento
        String documenttype = "";
        if (jCheckBox_TI.isSelected()) {
            documenttype = "TI";
        } else if (jCheckBox_CC.isSelected()) {
            documenttype = "CC";
        } else if (jCheckBox_CE.isSelected()) {
            documenttype = "CE";
        }

        // Nombres y otros campos
        String fullnames = jTextField_FULLNAMES.getText();
        String fullsurnames = jTextField_FULLSURNAMES.getText();
        String position = jTextField_POSITION.getText();
        String phone = jTextField_PHONE.getText();
        String emergencycontact = jTextField_EMERGENCYCONTACT.getText();
        String email = jTextField_EMAIL.getText();
        String address = jTextField_ADDRESS.getText();
        

        // Determinar género
        String gender = "";
        if (jCheckBox_MALE.isSelected()) {
            gender = "Male";
        } else if (jCheckBox_FEMALE.isSelected()) {
            gender = "Female";
        }


         // Fechas
        java.util.Date dob = jXDatePicker_DATEOFBIRTH.getDate();
        java.util.Date doe = jXDatePicker_DATEENTRY.getDate();

        if (dob == null || doe == null) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar ambas fechas.");
            return;
        }
        LocalDate dateofbirth = dob.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate dateofentry = doe.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        
         // Objeto Paciente
        controlador.Empleado empleado = new controlador.Empleado(
            id, documentnumber, documenttype, fullnames,
            fullsurnames, position, gender, phone,
            emergencycontact, email, address, dateofbirth, dateofentry );
        

        // Llamar al método que actualiza
        controlador.Empleado.insertEmpleado(empleado);

        // Refrescar tabla y mostrar mensaje
        populateJtable();
        JOptionPane.showMessageDialog(null, "Empleado correctly admitted");

    } catch (Exception ex) {
        JOptionPane.showMessageDialog(null, "Error entering: " + ex.getMessage());
        ex.printStackTrace();
    }
        
        
    }//GEN-LAST:event_jButton_INSERTActionPerformed
//Función para limpiar todos los campor con Botón
    private void jButton_CLEANActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_CLEANActionPerformed
        // TODO add your handling code here:
        
    jTextField_ID.setText("");
    jTextField_DOCUMENTNUMBER.setText("");
    jTextField_FULLNAMES.setText("");
    jTextField_FULLSURNAMES.setText("");
    jTextField_POSITION.setText("");
    jTextField_PHONE.setText("");
    jTextField_EMERGENCYCONTACT.setText("");
    jTextField_EMAIL.setText("");
    jTextField_EMERGENCYCONTACT.setText("");
    jTextField_ADDRESS.setText("");

    // Limpiar selección de checkboxes de tipo de documento
    jCheckBox_TI.setSelected(false);
    jCheckBox_CC.setSelected(false);
    jCheckBox_CE.setSelected(false);

    // Limpiar selección de género
    jCheckBox_MALE.setSelected(false);
    jCheckBox_FEMALE.setSelected(false);

    // Limpiar fechas
    jXDatePicker_DATEOFBIRTH.setDate(null);
    jXDatePicker_DATEENTRY.setDate(null);
        
        
    }//GEN-LAST:event_jButton_CLEANActionPerformed

    private void jButtonFIRTSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonFIRTSActionPerformed
        // TODO add your handling code here:
        
        pos = 0;
        ShowItem(pos);
    }//GEN-LAST:event_jButtonFIRTSActionPerformed

    private void jButtonLASTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLASTActionPerformed
        // TODO add your handling code here:
        pos = getEmpleadoList().size()-1;
         ShowItem(pos);
    }//GEN-LAST:event_jButtonLASTActionPerformed

    private void jButtonPREVIOUSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPREVIOUSActionPerformed
        // TODO add your handling code here:
       pos--;
        
        if(pos < 0)
        {
            pos = 0;
        }
        
        ShowItem(pos);
    }//GEN-LAST:event_jButtonPREVIOUSActionPerformed

    private void jButtonNEXTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNEXTActionPerformed
        // TODO add your handling code here:
       pos++;
        
        if(pos >= getEmpleadoList().size())
        {
            pos = getEmpleadoList().size()-1;
        }
        
        ShowItem(pos);
    }//GEN-LAST:event_jButtonNEXTActionPerformed

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
            java.util.logging.Logger.getLogger(Admin_Empleado_Form.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Admin_Empleado_Form.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Admin_Empleado_Form.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Admin_Empleado_Form.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Admin_Empleado_Form().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonFIRTS;
    private javax.swing.JButton jButtonLAST;
    private javax.swing.JButton jButtonNEXT;
    private javax.swing.JButton jButtonPREVIOUS;
    private javax.swing.JButton jButton_CLEAN;
    private javax.swing.JButton jButton_DELETE;
    private javax.swing.JButton jButton_INSERT;
    private javax.swing.JButton jButton_UPDATE;
    private javax.swing.JCheckBox jCheckBox_CC;
    private javax.swing.JCheckBox jCheckBox_CE;
    private javax.swing.JCheckBox jCheckBox_FEMALE;
    private javax.swing.JCheckBox jCheckBox_MALE;
    private javax.swing.JCheckBox jCheckBox_TI;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable_EMPLEADO;
    private javax.swing.JTextField jTextField_ADDRESS;
    private javax.swing.JTextField jTextField_DOCUMENTNUMBER;
    private javax.swing.JTextField jTextField_EMAIL;
    private javax.swing.JTextField jTextField_EMERGENCYCONTACT;
    private javax.swing.JTextField jTextField_FULLNAMES;
    private javax.swing.JTextField jTextField_FULLSURNAMES;
    private javax.swing.JTextField jTextField_ID;
    private javax.swing.JTextField jTextField_PHONE;
    private javax.swing.JTextField jTextField_POSITION;
    private org.jdesktop.swingx.JXDatePicker jXDatePicker_DATEENTRY;
    private org.jdesktop.swingx.JXDatePicker jXDatePicker_DATEOFBIRTH;
    // End of variables declaration//GEN-END:variables
}
