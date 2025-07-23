/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Luis Sierra
 */
public class Empleado {
    //Declaración de variables
    Connection connection;
    private Integer id;   
    private Integer documentnumber;
    private String documenttype;
    private String fullnames;
    private String fullsurnames;
    private String position;
    private String gender;
    private String phone;
    private String emergencycontact;
    private String email;
    private String address;
    private LocalDate dateofbirth;
    private LocalDate dateofentry;
    
  
public Empleado(){//<--Constructor vacio 
    
    
    }     
  //Constructor empleado  
 public Empleado(Integer ID, Integer DOCUMENT, String TYPE, String FULLN, String SURNAME,String POSITION,
         String GENDER, String PHONE, String EMERGENCYPHO, String EMAIL, String ADDRESS, LocalDate DATEBIRTH, LocalDate DATEENTRY){
     
    this.id = ID;
    this.documentnumber = DOCUMENT;
    this.documenttype = TYPE;
    this.fullnames = FULLN;
    this.fullsurnames = SURNAME;
    this.position = POSITION;
    this.gender = GENDER;
    this.phone = PHONE;
    this.emergencycontact = EMERGENCYPHO;
    this.email = EMAIL;
    this.address = ADDRESS;
    this.dateofbirth = DATEBIRTH;
    this.dateofentry = DATEENTRY;
    
    }      

    public Integer getId() {
        return id;
    }

    public Integer getDocumentnumber() {
        return documentnumber;
    }

    public String getDocumenttype() {
        return documenttype;
    }

    public String getFullnames() {
        return fullnames;
    }

    public String getFullsurnames() {
        return fullsurnames;
    }

    public String getPosition() {
        return position;
    }

    public String getGender() {
        return gender;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmergencycontact() {
        return emergencycontact;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public LocalDate getDateofbirth() {
        return dateofbirth;
    }

    public LocalDate getDateofentry() {
        return dateofentry;
    }
   //Función insert new empleado
 public static void insertEmpleado(Empleado empleado){  
     
        Connection con = modelo.Conexion_DB.getConnection();
        PreparedStatement ps;
        
      try {
            ps = con.prepareStatement("INSERT INTO `empleado`(`documentnumber`,`documenttype`, `fullnames`, `fullsurnames`, `position`,`gender`,`phone`,`emergencycontact`, `email`,`address`,`dateofbirth`,`dateofentry`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");

            ps.setInt(1, empleado.getDocumentnumber());
            ps.setString(2, empleado.getDocumenttype());
            ps.setString(3, empleado.getFullnames());
            ps.setString(4, empleado.getFullsurnames());
            ps.setString(5, empleado.getPosition());
            ps.setString(6, empleado.getGender());
            ps.setString(7, empleado.getPhone());
            ps.setString(8, empleado.getEmergencycontact());
            ps.setString(9, empleado.getEmail());
            ps.setString(10, empleado.getAddress());
            ps.setDate(11, java.sql.Date.valueOf(empleado.getDateofbirth()));
            ps.setDate(12, java.sql.Date.valueOf(empleado.getDateofentry()));
           

            if(ps.executeUpdate() != 0){
                JOptionPane.showMessageDialog(null, "New Empleado Added");
                
                }
                else{
                    JOptionPane.showMessageDialog(null, "Something Wrong");
                    
                }
            
        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null, "Error inserting Employee:\n" + ex.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(Empleado.class.getName()).log(Level.SEVERE, null, ex);
        }   


 } 
    
 // get empleado list
public ArrayList<Empleado> EmpleadoList(){
       ArrayList<Empleado> empleado_List = new ArrayList<>();
       connection = modelo.Conexion_DB.getConnection();

        ResultSet rs;
        PreparedStatement ps;

               String query = "SELECT `id`,`documentnumber`,`documenttype`, `fullnames`, `fullsurnames`,`position`,`gender`,`phone`,`emergencycontact`, `email`,`address`,`dateofbirth`,`dateofentry` FROM `empleado`";

         try {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
           //el órden del while debe ser como el del constructor
            Empleado empleado;
            while(rs.next()){
                empleado = new Empleado(rs.getInt("id"),
                                 rs.getInt("documentnumber"),
                                 rs.getString("documenttype"),
                                 rs.getString("fullnames"),
                                 rs.getString("fullsurnames"),   
                                 rs.getString("position"),
                                 rs.getString("gender"),
                                 rs.getString("phone"),
                                 rs.getString("emergencycontact"),
                                 rs.getString("email"),
                                 rs.getString("address"),
                                 rs.getDate("dateofbirth").toLocalDate(),
                                 rs.getDate("dateofentry").toLocalDate()
                                  );
                empleado_List.add(empleado);
            }
        
        } catch (SQLException ex) {
            Logger.getLogger(Paciente.class.getName()).log(Level.SEVERE, null, ex);
           
        }
        return empleado_List;       
}   
    //Update empleado
 public static void updateEmpleado(Empleado empleado)
    {
        Connection con = modelo.Conexion_DB.getConnection();
        PreparedStatement ps;
        
           
            try {
            
            ps = con.prepareStatement("UPDATE `empleado` SET `documentnumber`=?,`documenttype`=?,`fullnames`=? ,`fullsurnames`=?,`position`=? ,`gender`=? ,`phone`=? ,`emergencycontact`=?,`email`=? ,`address`=? ,`dateofbirth`=? ,`dateofentry`=? WHERE `id` = ?");
            
            ps.setInt(1, empleado.getDocumentnumber());
            ps.setString(2, empleado.getDocumenttype());
            ps.setString(3, empleado.getFullnames());
            ps.setString(4, empleado.getFullsurnames());
            ps.setString(5, empleado.getPosition());
            ps.setString(6, empleado.getGender());
            ps.setString(7, empleado.getPhone());
            ps.setString(8, empleado.getEmergencycontact());
            ps.setString(9, empleado.getEmail());
            ps.setString(10, empleado.getAddress());
            ps.setDate(11, java.sql.Date.valueOf(empleado.getDateofbirth()));
            ps.setDate(12, java.sql.Date.valueOf(empleado.getDateofentry()));
            //el ID lo debo enviar para actualizar
            ps.setInt(13, empleado.getId());

            if(ps.executeUpdate() != 0){
                JOptionPane.showMessageDialog(null, "Paciente Updated");
                
                }
                else{
                    JOptionPane.showMessageDialog(null, "Something Wrong");
                    
                }
            
        } catch (SQLException ex) {
            Logger.getLogger(Users.class.getName()).log(Level.SEVERE, null, ex);
        }

            
         
    }
  //Delete empleado
public static void deleteEmpleado(Integer id){
          Connection con = modelo.Conexion_DB.getConnection();
          PreparedStatement ps;
        
        try {
            ps = con.prepareStatement("DELETE FROM `empleado` WHERE `id` = ?");
          
            ps.setInt(1, id);
            
            int YesOrNo = JOptionPane.showConfirmDialog(null,"Do You Really Want To Delete This Empelado","Delete Empleado", JOptionPane.YES_NO_OPTION);
            if(YesOrNo == 0){
                
                if(ps.executeUpdate() != 0){
                JOptionPane.showMessageDialog(null, "Empleado Deleted");
                
                }
                else{
                    JOptionPane.showMessageDialog(null, "Something Wrong");
                    
                }
                
            }
                        
        } catch (SQLException ex) {
           Logger.getLogger(Empleado.class.getName()).log(Level.SEVERE, null, ex);
        } 
 
 
    }    
}
