/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import controlador.Agenda;
import controlador.ExportarExcel;
import controlador.Paciente;
import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.sql.Connection;
import static java.sql.JDBCType.TIME;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerDateModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import javax.swing.text.DateFormatter;

/**
 *
 * @author Luis Sierra
 */
public class Admin_Agenda_Form extends javax.swing.JFrame {
   
    private Integer agendaId;
    int pos = 0;
    
 public Admin_Agenda_Form() {
        initComponents();
        //cargo la tabla con los registros de los empleados
        populateJtable();
       
    //Formato de hora para el jFormattedTextField_TIME para solo 08:00 18:00 codificar
    DateFormat formatoHora = new SimpleDateFormat("HH:mm");
    DateFormatter formateadorHora = new DateFormatter(formatoHora);

    jFormattedTextField_TIME.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(formateadorHora));
    jFormattedTextField_TIME.setValue(new Date());  
    
        jTable_AGENDA.setShowGrid(true);
        
         jTable_AGENDA.setGridColor(Color.GREEN);
        
         jTable_AGENDA.setSelectionBackground(Color.gray);
        
         JTableHeader th = jTable_AGENDA.getTableHeader();

         th.setFont(new Font("Tahoma", Font.PLAIN, 16));
        
    }
 
 //función para realizar busquedas 
 public void populateJtable(String valor) {
    controlador.Agenda agenda = new controlador.Agenda();
    ArrayList<controlador.Agenda> agendaList = agenda.AgendaList(valor);

    String[] colNames = {
        "ID", "ID Paciente", "Office", "Date", "Time", "State",
    };

    Object[][] rows = new Object[agendaList.size()][6];

    for (int i = 0; i < agendaList.size(); i++) {
        rows[i][0] = agendaList.get(i).getId();
        rows[i][1] = agendaList.get(i).getId_paciente();
        rows[i][2] = agendaList.get(i).getOffice();
        rows[i][3] = agendaList.get(i).getDate().toString();
        rows[i][4] = agendaList.get(i).getTime().toString();
        rows[i][5] = agendaList.get(i).getState();
    }

    modelo.TableModelAgenda mmd = new modelo.TableModelAgenda(rows, colNames);
    jTable_AGENDA.setModel(mmd);
    jTable_AGENDA.setRowHeight(40);

    jTable_AGENDA.getColumnModel().getColumn(0).setPreferredWidth(60);
    jTable_AGENDA.getColumnModel().getColumn(1).setPreferredWidth(120);
    jTable_AGENDA.getColumnModel().getColumn(2).setPreferredWidth(100);
    jTable_AGENDA.getColumnModel().getColumn(3).setPreferredWidth(120);
    jTable_AGENDA.getColumnModel().getColumn(4).setPreferredWidth(120);
    jTable_AGENDA.getColumnModel().getColumn(5).setPreferredWidth(120);
}

 
    
   
 //función para mostrar todos los datos de la tabla agenda con id de paciente 
public void populateJtable(){

    controlador.Agenda agenda = new controlador.Agenda();
    ArrayList<controlador.Agenda> agendaList = agenda.AgendaList("");

    // Columnas a mostrar
    String[] colNames = {
        "ID", "ID Paciente", "Office", "Date", "Time", "State",
    };

    // Crear matriz de datos
    Object[][] rows = new Object[agendaList.size()][6];

    for (int i = 0; i < agendaList.size(); i++) {
        rows[i][0] = agendaList.get(i).getId();
        rows[i][1] = agendaList.get(i).getId_paciente();
        rows[i][2] = agendaList.get(i).getOffice();
        rows[i][3] = agendaList.get(i).getDate().toString();
        rows[i][4] = agendaList.get(i).getTime().toString();
        rows[i][5] = agendaList.get(i).getState();
    }

    // Crear modelo y asignarlo a la tabla
    modelo.TableModelAgenda mmd = new modelo.TableModelAgenda(rows, colNames);
    jTable_AGENDA.setModel((TableModel) mmd);
    jTable_AGENDA.setRowHeight(40);

    // Ajustar ancho de columnas para que no se corte visualmente
    jTable_AGENDA.getColumnModel().getColumn(0).setPreferredWidth(60);   // ID
    jTable_AGENDA.getColumnModel().getColumn(1).setPreferredWidth(120);  // Paciente ID
    jTable_AGENDA.getColumnModel().getColumn(2).setPreferredWidth(100);  // Oficina
    jTable_AGENDA.getColumnModel().getColumn(3).setPreferredWidth(120);  // Fecha
    jTable_AGENDA.getColumnModel().getColumn(4).setPreferredWidth(120);  // Hora
    jTable_AGENDA.getColumnModel().getColumn(5).setPreferredWidth(120);  // Estad
   
}  
//Array con todos los registros de mi tabla agenda   
public ArrayList<Agenda> getAgendaList(){

 ArrayList<Agenda> agendalist  = new ArrayList<Agenda>();
          //  Connection con = getConnection();
            Connection con = modelo.Conexion_DB.getConnection();
            String query = "SELECT * FROM agenda";
           
            Statement st;
            ResultSet rs;
try {
           
            st = con.createStatement();
            rs = st.executeQuery(query);
            Agenda agenda;
           
            while(rs.next()){ //cargo cada registro llamándolo desde la tabla con sus respectivos nombres de campos
                 agenda = new  Agenda (rs.getInt("id"),rs.getInt("id_paciente"),rs.getInt("office"),
                         rs.getDate("date").toLocalDate(),rs.getTime("time").toLocalTime(),
                         rs.getString("state"));
                 agendalist.add(agenda);
            }
           
        } catch (SQLException ex) {
            Logger.getLogger(Admin_Paciente_Form.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        return agendalist;


}
// Show Data In Inputs para el movimiento dentro de la tabla   
public void ShowItem(int index){
// Obtener la agenda de la lista
    Agenda agenda = getAgendaList().get(index);

    // Mostrar valores numéricos en campos de texto
    jTextField_ID.setText(String.valueOf(agenda.getId()));
    jTextField_ID_PACIENTE.setText(String.valueOf(agenda.getId_paciente()));
    jTextField_OFFICE.setText(String.valueOf(agenda.getOffice()));

    // Mostrar el estado en los checkboxes
    String estado = agenda.getState();
    jCheckBoxAGENDADA.setSelected("Agendada".equals(estado));
    jCheckBox_ASISTIDA.setSelected("Asistida".equals(estado));
    jCheckBoxCANCELO.setSelected("Cancelada".equals(estado));

    // Mostrar la fecha (JXDatePicker trabaja con java.util.Date)
    if (agenda.getDate() != null) {
        java.util.Date fechaAgenda = java.sql.Date.valueOf(agenda.getDate());
        jXDatePicker_DATEAGENDA.setDate(fechaAgenda);
    }

    // Mostrar la hora en el campo JFormattedTextField
    if (agenda.getTime() != null) {
        java.util.Date horaAgenda = java.sql.Time.valueOf(agenda.getTime());
        jFormattedTextField_TIME.setValue(horaAgenda);
    }

}
 // Función para vericar todos los campos 
public boolean verifyFields(){

   // Campos de texto
   String id_paciente = jTextField_ID_PACIENTE.getText();
    String office = jTextField_OFFICE.getText();
    java.util.Date date = jXDatePicker_DATEAGENDA.getDate();
    Object time = jFormattedTextField_TIME.getValue(); 
    
    // state
    String state = "";
    if (jCheckBoxAGENDADA.isSelected()) state = "Agendada";
    else if (jCheckBox_ASISTIDA.isSelected()) state = "Asistida";
    else if (jCheckBoxCANCELO.isSelected()) state = "Cancelada";
    

    // Validar si el campo de hora tiene un valor válido
    boolean timeIsEmpty = false;
    if (time == null || !(time instanceof java.util.Date)) {
        timeIsEmpty = true;
    }

    // Validación de campos vacíos
    if (id_paciente.isEmpty() || office.isEmpty() || date == null || timeIsEmpty ||
            state.isEmpty()) {

        JOptionPane.showMessageDialog(null, "One Or More Fields Are Empty", "Empty Fields", JOptionPane.WARNING_MESSAGE);
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

        jCheckBoxMenuItem1 = new javax.swing.JCheckBoxMenuItem();
        jCheckBoxMenuItem2 = new javax.swing.JCheckBoxMenuItem();
        jCheckBoxMenuItem3 = new javax.swing.JCheckBoxMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable_AGENDA = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jTextField_ID = new javax.swing.JTextField();
        jTextField_ID_PACIENTE = new javax.swing.JTextField();
        jTextField_OFFICE = new javax.swing.JTextField();
        jXDatePicker_DATEAGENDA = new org.jdesktop.swingx.JXDatePicker();
        jButton_INSERT = new javax.swing.JButton();
        jButton_CLEAN = new javax.swing.JButton();
        jFormattedTextField_TIME = new javax.swing.JFormattedTextField();
        jCheckBoxAGENDADA = new javax.swing.JCheckBox();
        jCheckBox_ASISTIDA = new javax.swing.JCheckBox();
        jCheckBoxCANCELO = new javax.swing.JCheckBox();
        jButton_UPDATE = new javax.swing.JButton();
        jButton_DELETE = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jButton_FIRTS = new javax.swing.JButton();
        jButton_PREVIOUS = new javax.swing.JButton();
        jButton_LAST = new javax.swing.JButton();
        jButton_NEXT = new javax.swing.JButton();
        jButton_IMPRIMIREXCEL = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jTextField_SEARCH = new javax.swing.JTextField();
        jButton_SEARCH = new javax.swing.JButton();
        jButtonREFRESH = new javax.swing.JButton();

        jCheckBoxMenuItem1.setSelected(true);
        jCheckBoxMenuItem1.setText("jCheckBoxMenuItem1");

        jCheckBoxMenuItem2.setSelected(true);
        jCheckBoxMenuItem2.setText("jCheckBoxMenuItem2");

        jCheckBoxMenuItem3.setSelected(true);
        jCheckBoxMenuItem3.setText("jCheckBoxMenuItem3");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Agenda");
        setPreferredSize(new java.awt.Dimension(990, 530));
        getContentPane().setLayout(null);

        jPanel1.setBackground(new java.awt.Color(153, 153, 255));

        jTable_AGENDA.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable_AGENDAMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable_AGENDA);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 530, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE)
                .addContainerGap())
        );

        getContentPane().add(jPanel1);
        jPanel1.setBounds(403, 91, 550, 230);

        jPanel2.setBackground(new java.awt.Color(0, 0, 255));

        jPanel3.setBackground(new java.awt.Color(204, 204, 255));

        jLabel1.setText("Id:");

        jLabel2.setText("Id_Paciente:");

        jLabel4.setText("Office:");

        jLabel5.setText("Date:");

        jLabel6.setText("Time:");

        jLabel7.setText("Status:");

        jTextField_ID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_IDActionPerformed(evt);
            }
        });

        jButton_INSERT.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton_INSERT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/insert_1.png"))); // NOI18N
        jButton_INSERT.setText("Agendar");
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

        jFormattedTextField_TIME.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("hh:mm "))));
        jFormattedTextField_TIME.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jFormattedTextField_TIMEFocusGained(evt);
            }
        });
        jFormattedTextField_TIME.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFormattedTextField_TIMEActionPerformed(evt);
            }
        });

        jCheckBoxAGENDADA.setText("Agendada");
        jCheckBoxAGENDADA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxAGENDADAActionPerformed(evt);
            }
        });

        jCheckBox_ASISTIDA.setText("Asistida");

        jCheckBoxCANCELO.setText("Cancelada");

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

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(38, 38, 38)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextField_ID, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jFormattedTextField_TIME)
                                    .addComponent(jXDatePicker_DATEAGENDA, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                                    .addComponent(jTextField_OFFICE)
                                    .addComponent(jTextField_ID_PACIENTE)))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(13, 13, 13)
                                .addComponent(jCheckBoxAGENDADA)
                                .addGap(18, 18, 18)
                                .addComponent(jCheckBox_ASISTIDA)
                                .addGap(18, 18, 18)
                                .addComponent(jCheckBoxCANCELO))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jButton_DELETE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton_INSERT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(52, 52, 52)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton_UPDATE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton_CLEAN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(34, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jTextField_ID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField_ID_PACIENTE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(23, 23, 23)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextField_OFFICE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jXDatePicker_DATEAGENDA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jFormattedTextField_TIME, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBoxAGENDADA)
                    .addComponent(jCheckBox_ASISTIDA)
                    .addComponent(jCheckBoxCANCELO)
                    .addComponent(jLabel7))
                .addGap(37, 37, 37)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_INSERT)
                    .addComponent(jButton_UPDATE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_DELETE)
                    .addComponent(jButton_CLEAN))
                .addContainerGap(74, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        getContentPane().add(jPanel2);
        jPanel2.setBounds(10, 12, 387, 470);

        jPanel4.setBackground(new java.awt.Color(153, 153, 255));

        jButton_FIRTS.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton_FIRTS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/first.png"))); // NOI18N
        jButton_FIRTS.setText("Firts");
        jButton_FIRTS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_FIRTSActionPerformed(evt);
            }
        });

        jButton_PREVIOUS.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton_PREVIOUS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/previous.png"))); // NOI18N
        jButton_PREVIOUS.setText("Previous");
        jButton_PREVIOUS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_PREVIOUSActionPerformed(evt);
            }
        });

        jButton_LAST.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton_LAST.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/last.png"))); // NOI18N
        jButton_LAST.setText("Last");
        jButton_LAST.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_LASTActionPerformed(evt);
            }
        });

        jButton_NEXT.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton_NEXT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/next.png"))); // NOI18N
        jButton_NEXT.setText("Next");
        jButton_NEXT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_NEXTActionPerformed(evt);
            }
        });

        jButton_IMPRIMIREXCEL.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton_IMPRIMIREXCEL.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/excel_1.png"))); // NOI18N
        jButton_IMPRIMIREXCEL.setText("Print");
        jButton_IMPRIMIREXCEL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_IMPRIMIREXCELActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton_PREVIOUS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton_FIRTS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(42, 42, 42)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton_LAST, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton_NEXT, javax.swing.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE))
                .addGap(37, 37, 37)
                .addComponent(jButton_IMPRIMIREXCEL)
                .addContainerGap(82, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_FIRTS)
                    .addComponent(jButton_LAST))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_PREVIOUS)
                    .addComponent(jButton_NEXT))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton_IMPRIMIREXCEL, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        getContentPane().add(jPanel4);
        jPanel4.setBounds(410, 330, 540, 150);

        jPanel6.setBackground(new java.awt.Color(153, 153, 255));

        jButton_SEARCH.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton_SEARCH.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/search1.png"))); // NOI18N
        jButton_SEARCH.setText("Search");
        jButton_SEARCH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_SEARCHActionPerformed(evt);
            }
        });

        jButtonREFRESH.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButtonREFRESH.setText("Refresh");
        jButtonREFRESH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonREFRESHActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextField_SEARCH, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton_SEARCH)
                .addGap(18, 18, 18)
                .addComponent(jButtonREFRESH, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton_SEARCH, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonREFRESH, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jTextField_SEARCH))
                .addContainerGap())
        );

        getContentPane().add(jPanel6);
        jPanel6.setBounds(410, 10, 540, 60);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField_IDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_IDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_IDActionPerformed

    private void jButton_INSERTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_INSERTActionPerformed
        // TODO add your handling code here:
  try {
        // Capturar datos desde el formulario
        
        String idPacienteStr = jTextField_ID_PACIENTE.getText().trim();

    if (idPacienteStr.isEmpty() || !idPacienteStr.matches("\\d+")) {
        JOptionPane.showMessageDialog(this, "Ingrese un número de ID válido", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }
        
        int id_paciente = Integer.parseInt(jTextField_ID_PACIENTE.getText());
        int office = Integer.parseInt(jTextField_OFFICE.getText());
        
        // Determinar tipo State
        String state = "";
        if (jCheckBoxAGENDADA.isSelected()) {
            state = "Agendada";
        } else if (jCheckBox_ASISTIDA.isSelected()) {
            state = "Asistida";
        } else if (jCheckBoxCANCELO.isSelected()) {
            state = "Cancelada";
        }
        
        
         // Validar si el paciente existe
    if (!Paciente.existePaciente(id_paciente)) {
        JOptionPane.showMessageDialog(this, "El paciente con ID " + id_paciente + " no existe.");
        return;
    }

        // Convertir la fecha del JXDatePicker a LocalDate
        LocalDate date = jXDatePicker_DATEAGENDA.getDate()
                                .toInstant()
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate();

        // Obtener la hora del jFormattedTextField_TIME
        Date timeDate = (Date) jFormattedTextField_TIME.getValue(); 
        LocalTime time = timeDate.toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalTime();

        // Crear el objeto Agenda
        controlador.Agenda agenda = new controlador.Agenda(
            null, id_paciente, office, date, time, state
        );

        // Llamar al método que actualiza
        controlador.Agenda.insertAgenda(agenda);

    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(this, "Número inválido", "Error", JOptionPane.ERROR_MESSAGE);
    } catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error al registrar agenda: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }   
        
        
        
        
        
    }//GEN-LAST:event_jButton_INSERTActionPerformed
//Función para limpiar todos los campor con Botón
    private void jButton_CLEANActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_CLEANActionPerformed
        // TODO add your handling code here:
    jTextField_ID_PACIENTE.setText("");
    jTextField_OFFICE.setText("");
    // Limpiar selección de checkboxes de state
    jCheckBoxAGENDADA.setSelected(false);
    jCheckBox_ASISTIDA.setSelected(false);
    jCheckBoxCANCELO.setSelected(false);
    //jTextField_STATUS.setText("");
    //jTextField_FULLNAME_PACIENTE.setText("");
    jXDatePicker_DATEAGENDA.setDate(null);
    jFormattedTextField_TIME.setValue(new java.util.Date());

        
        
    }//GEN-LAST:event_jButton_CLEANActionPerformed

    private void jFormattedTextField_TIMEFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jFormattedTextField_TIMEFocusGained
        // TODO add your handling code here:
        
        
  
        
        
    }//GEN-LAST:event_jFormattedTextField_TIMEFocusGained

    private void jCheckBoxAGENDADAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxAGENDADAActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBoxAGENDADAActionPerformed

    private void jTable_AGENDAMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable_AGENDAMouseClicked
        // TODO add your handling code here:
        
        int rowIndex = jTable_AGENDA.getSelectedRow();

    // Obtener datos de la tabla
    agendaId = Integer.parseInt(jTable_AGENDA.getValueAt(rowIndex, 0).toString());

    // Asignar valores a los campos de texto
    jTextField_ID.setText(jTable_AGENDA.getValueAt(rowIndex, 0).toString());
    jTextField_ID_PACIENTE.setText(jTable_AGENDA.getValueAt(rowIndex, 1).toString());
    jTextField_OFFICE.setText(jTable_AGENDA.getValueAt(rowIndex, 2).toString());
    
    
    // Estado
    String state = jTable_AGENDA.getValueAt(rowIndex, 5).toString();
    jCheckBoxAGENDADA.setSelected(state.equalsIgnoreCase("Agendada"));
    jCheckBox_ASISTIDA.setSelected(state.equalsIgnoreCase("Asistida"));
    jCheckBoxCANCELO.setSelected(state.equalsIgnoreCase("Cancelada"));

    // Convertir y asignar la fecha
    try {
        String fechaTexto = jTable_AGENDA.getValueAt(rowIndex, 3).toString();
        java.util.Date fecha = java.sql.Date.valueOf(fechaTexto);
        jXDatePicker_DATEAGENDA.setDate(fecha);
    } catch (Exception e) {
        jXDatePicker_DATEAGENDA.setDate(null); // en caso de error
    }
    
   // Convertir y asignar hora en formato HH:mm
    try {
       String horaTexto = jTable_AGENDA.getValueAt(rowIndex, 4).toString(); 
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        java.util.Date hora = sdf.parse(horaTexto);
        jFormattedTextField_TIME.setValue(hora);
    } catch (Exception e) {
        jFormattedTextField_TIME.setValue(null);
    }   
        
        
        
    }//GEN-LAST:event_jTable_AGENDAMouseClicked

    private void jButton_UPDATEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_UPDATEActionPerformed
        // TODO add your handling code here:
        
     try {
        // Obtener ID agenda a actualizar
        int id = Integer.parseInt(jTextField_ID.getText());
        int id_paciente = Integer.parseInt(jTextField_ID_PACIENTE.getText());
        int office = Integer.parseInt(jTextField_OFFICE.getText());
        
        // Determinar tipo State
        String state = "";
        if (jCheckBoxAGENDADA.isSelected()) {
            state = "Agendada";
        } else if (jCheckBox_ASISTIDA.isSelected()) {
            state = "Asistida";
        } else if (jCheckBoxCANCELO.isSelected()) {
            state = "Cancelada";
        }
        
      

        // Convertir la fecha del JXDatePicker a LocalDate
        LocalDate date = jXDatePicker_DATEAGENDA.getDate()
                                .toInstant()
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate();

        // Obtener la hora del jFormattedTextField_TIME
        Date timeDate = (Date) jFormattedTextField_TIME.getValue(); 
        LocalTime time = timeDate.toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalTime();

        // Crear el objeto Agenda
        controlador.Agenda agenda = new controlador.Agenda(
            id, id_paciente, office, date, time, state
        );

        // Llamar al método que actualiza
        controlador.Agenda.updateAgenda(agenda);
        // Refrescar tabla y mostrar mensaje
        populateJtable();

    } catch (NumberFormatException ex) {
         JOptionPane.showMessageDialog(null, "Agenda correctly updated");
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(null, "Error Updating: " + ex.getMessage());
        ex.printStackTrace();
    }   
        
       
        
        
    }//GEN-LAST:event_jButton_UPDATEActionPerformed

    private void jButton_DELETEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_DELETEActionPerformed
        // TODO add your handling code here:
         //Capturar el Id del paciente a eliminar de la Bd
       if(!jTextField_ID.getText().equals(""))
        {
            try {
                int id_agenda = Integer.parseInt(jTextField_ID.getText());
                controlador.Agenda.deleteAgenda(id_agenda);
                populateJtable();
               
            } catch (Exception ex) {
                Logger.getLogger(Admin_Empleado_Form.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "Cita Not Deleted");
            }

        }else{
            JOptionPane.showMessageDialog(null, "Cita Not Deleted : No Id To Delete");
        } 
        
        
       
        
        
    }//GEN-LAST:event_jButton_DELETEActionPerformed

    private void jButton_FIRTSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_FIRTSActionPerformed
                
        //Vuelve al inicio de la tabla
        
          pos = 0;
        ShowItem(pos);
        
        
    }//GEN-LAST:event_jButton_FIRTSActionPerformed

    private void jButton_LASTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_LASTActionPerformed
        
        // Vuelve al final de la tabla
        
        
         pos = getAgendaList().size()-1;
         ShowItem(pos);
        
    }//GEN-LAST:event_jButton_LASTActionPerformed

    private void jButton_PREVIOUSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_PREVIOUSActionPerformed
        
        // Devuelve uno a uno
        
        
         pos--;
        
        if(pos < 0)
        {
            pos = 0;
        }
        
        ShowItem(pos);
    }//GEN-LAST:event_jButton_PREVIOUSActionPerformed

    private void jButton_NEXTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_NEXTActionPerformed
        
         // Avanza uno a uno
         pos++;
        
        if(pos >= getAgendaList().size())
        {
            pos = getAgendaList().size()-1;
        }
        
        ShowItem(pos);
        
        
    }//GEN-LAST:event_jButton_NEXTActionPerformed

    private void jButton_IMPRIMIREXCELActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_IMPRIMIREXCELActionPerformed
        // TODO add your handling code here:
        
         ExportarExcel obj;
         
         try{
             obj = new ExportarExcel();
             obj.exportarExcel(jTable_AGENDA);
         }catch (IOException ex){
             System.out.println("Error: " + ex);
         }
        
        
        
        
    }//GEN-LAST:event_jButton_IMPRIMIREXCELActionPerformed
//Función para buscar en Jtable agenda
    private void jButton_SEARCHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_SEARCHActionPerformed
        // TODO add your handling code here:
        
    try {
        String entrada = jTextField_SEARCH.getText().trim();

        if (entrada.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese un valor a buscar", "Campo vacío", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Valida el formato dd/MM/yy
        if (entrada.matches("\\d{2}/\\d{2}/\\d{2}")) {
            DateTimeFormatter formatoEntrada = DateTimeFormatter.ofPattern("dd/MM/yy");
            LocalDate fechaConvertida = LocalDate.parse(entrada, formatoEntrada);
            String fechaMySQL = fechaConvertida.toString(); // fecha yyyy-MM-dd para MySQL

            populateJtable(fechaMySQL);

            // Verifica si la tabla está vacía tras búsqueda por fecha
            if (jTable_AGENDA.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "No se encontraron agendas para la fecha: " + entrada, "Sin resultados", JOptionPane.INFORMATION_MESSAGE);
            }

        } else {
            // Búsqueda por texto (estado, ID, oficina, etc.)
            populateJtable(entrada);

            if (jTable_AGENDA.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "No se encontraron resultados para: " + entrada, "Sin resultados", JOptionPane.INFORMATION_MESSAGE);
            }
        }

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error en la búsqueda: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace(); // Opcional: solo para depuración en consola
    }
       
    }//GEN-LAST:event_jButton_SEARCHActionPerformed
    //Función para limpiar la busqueda
    private void jButtonREFRESHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonREFRESHActionPerformed
        // TODO add your handling code here:
        jTextField_SEARCH.setText("");
         populateJtable("");
    }//GEN-LAST:event_jButtonREFRESHActionPerformed

    private void jFormattedTextField_TIMEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFormattedTextField_TIMEActionPerformed
        // TODO add your handling code here:
    
        
    }//GEN-LAST:event_jFormattedTextField_TIMEActionPerformed

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
            java.util.logging.Logger.getLogger(Admin_Agenda_Form.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Admin_Agenda_Form.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Admin_Agenda_Form.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Admin_Agenda_Form.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Admin_Agenda_Form().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonREFRESH;
    private javax.swing.JButton jButton_CLEAN;
    private javax.swing.JButton jButton_DELETE;
    private javax.swing.JButton jButton_FIRTS;
    private javax.swing.JButton jButton_IMPRIMIREXCEL;
    private javax.swing.JButton jButton_INSERT;
    private javax.swing.JButton jButton_LAST;
    private javax.swing.JButton jButton_NEXT;
    private javax.swing.JButton jButton_PREVIOUS;
    private javax.swing.JButton jButton_SEARCH;
    private javax.swing.JButton jButton_UPDATE;
    private javax.swing.JCheckBox jCheckBoxAGENDADA;
    private javax.swing.JCheckBox jCheckBoxCANCELO;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem1;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem2;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem3;
    private javax.swing.JCheckBox jCheckBox_ASISTIDA;
    private javax.swing.JFormattedTextField jFormattedTextField_TIME;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable_AGENDA;
    private javax.swing.JTextField jTextField_ID;
    private javax.swing.JTextField jTextField_ID_PACIENTE;
    private javax.swing.JTextField jTextField_OFFICE;
    private javax.swing.JTextField jTextField_SEARCH;
    private org.jdesktop.swingx.JXDatePicker jXDatePicker_DATEAGENDA;
    // End of variables declaration//GEN-END:variables
}
