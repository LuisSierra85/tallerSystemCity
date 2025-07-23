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
import modelo.Conexion_DB;

/**
 *
 * @author Luis Sierra
 */
public class Paciente {
    //Declaración de variables
    Connection connection;
    private Integer id;   
    private Integer documentnumber;
    private String documenttype;
    private String fullnames;
    private String fullsurnames;
    private String phone;
    private String email;
    private String gender;
    private LocalDate dateofbirth;
    private String emergencycontact;
    private String emergencyphone;
   
public Paciente(){//<--Contructor vacio.
    
    
    }    
  
//Contructor de la clase Paciente
public Paciente(Integer ID, Integer DOCUMENT, String TYPE, String FULLN, String SURNAME, String PHONE,
        String EMAIL, String GENDER, String EMERGENCYCONT, String EMERGENCYPHO, LocalDate DATEBIRTH){
    
    this.id = ID;
    this.documentnumber = DOCUMENT;
    this.documenttype = TYPE;
    this.fullnames = FULLN;
    this.fullsurnames = SURNAME;
    this.phone = PHONE;
    this.email = EMAIL;
    this.gender = GENDER;
    this.emergencycontact = EMERGENCYCONT;
    this.emergencyphone = EMERGENCYPHO;
    this.dateofbirth = DATEBIRTH;
    
    
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

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }

    
    public String getEmergencycontact() {
        return emergencycontact;
    }

    public String getEmergencyphone() {
        return emergencyphone;
    }
    
     public LocalDate getDateofbirth() {
        return dateofbirth;
    }
    
    //Función insert new paciente
    public static void insertPaciente(Paciente paciente){
        
        Connection con = modelo.Conexion_DB.getConnection();
        PreparedStatement ps;
        
        try {
            ps = con.prepareStatement("INSERT INTO `paciente`(`documentnumber`,`documenttype`, `fullnames`, `fullsurnames`,`phone`, `email`,`gender`,`emergencycontact`,`emergencyphone`,`dateofbirth`) VALUES (?,?,?,?,?,?,?,?,?,?)");

            ps.setInt(1, paciente.getDocumentnumber());
            ps.setString(2, paciente.getDocumenttype());
            ps.setString(3, paciente.getFullnames());
            ps.setString(4, paciente.getFullsurnames());
            ps.setString(5, paciente.getPhone());
            ps.setString(6, paciente.getEmail());
            ps.setString(7, paciente.getGender());
            ps.setString(8, paciente.getEmergencycontact());
            ps.setString(9, paciente.getEmergencyphone());
            ps.setDate(10, java.sql.Date.valueOf(paciente.getDateofbirth()));
           

            if(ps.executeUpdate() != 0){
                JOptionPane.showMessageDialog(null, "New Paciente Added");
                
                }
                else{
                    JOptionPane.showMessageDialog(null, "Something Wrong");
                    
                }
            
        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null, "Error inserting Paciente:\n" + ex.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(Paciente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    // get paciente list
public ArrayList<Paciente> PacienteList(){
        
        ArrayList<Paciente> paciente_List = new ArrayList<>();
        connection = modelo.Conexion_DB.getConnection();

        ResultSet rs;
        PreparedStatement ps;

               String query = "SELECT `id`,`documentnumber`,`documenttype`, `fullnames`, `fullsurnames`,`phone`, `email`,`gender`,`emergencycontact`,`emergencyphone`,`dateofbirth` FROM `paciente`";
        
        try {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
           //el órden del while debe ser como el del constructor
            Paciente paciente;
            while(rs.next()){
                paciente = new Paciente(rs.getInt("id"),
                                 rs.getInt("documentnumber"),
                                 rs.getString("documenttype"),
                                 rs.getString("fullnames"),
                                 rs.getString("fullsurnames"),   
                                 rs.getString("phone"),
                                 rs.getString("email"),
                                 rs.getString("gender"),
                                 rs.getString("emergencycontact"),
                                 rs.getString("emergencyphone"),
                                 rs.getDate("dateofbirth").toLocalDate()
                                  );
                paciente_List.add(paciente);
            }
        
        } catch (SQLException ex) {
            Logger.getLogger(Paciente.class.getName()).log(Level.SEVERE, null, ex);
           
        }
        return paciente_List;
        
    }
    //Update paciente
  public static void updatePaciente(Paciente paciente)
    {
        Connection con = modelo.Conexion_DB.getConnection();
        PreparedStatement ps;
        
           
            try {
            
            ps = con.prepareStatement("UPDATE `paciente` SET `documentnumber`=?,`documenttype`=?,`fullnames`=? ,`fullsurnames`=?,`phone`=? ,`email`=? ,`gender`=? ,`emergencycontact`=?,`emergencyphone`=? ,`dateofbirth`=? WHERE `id` = ?");
            
            ps.setInt(1, paciente.getDocumentnumber());
            ps.setString(2, paciente.getDocumenttype());
            ps.setString(3, paciente.getFullnames());
            ps.setString(4, paciente.getFullsurnames());
            ps.setString(5, paciente.getPhone());
            ps.setString(6, paciente.getEmail());
            ps.setString(7, paciente.getGender());
            ps.setString(8, paciente.getEmergencycontact());
            ps.setString(9, paciente.getEmergencyphone());
            ps.setDate(10, java.sql.Date.valueOf(paciente.getDateofbirth()));
            //el ID lo debo enviar para actualizar
            ps.setInt(11, paciente.getId());

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
  //Delete paciente
public static void deletePaciente(Integer id)
    {
          Connection con = modelo.Conexion_DB.getConnection();
          PreparedStatement ps;
        
        try {
            ps = con.prepareStatement("DELETE FROM `paciente` WHERE `id` = ?");
          
            ps.setInt(1, id);
            
            int YesOrNo = JOptionPane.showConfirmDialog(null,"Do You Really Want To Delete This Paciente","Delete Paciente", JOptionPane.YES_NO_OPTION);
            if(YesOrNo == 0){
                
                if(ps.executeUpdate() != 0){
                JOptionPane.showMessageDialog(null, "Paciente Deleted");
                
                }
                else{
                    JOptionPane.showMessageDialog(null, "Something Wrong");
                    
                }
                
            }
                        
        } catch (SQLException ex) {
           Logger.getLogger(Users.class.getName()).log(Level.SEVERE, null, ex);
        }

    }  

    //Función para consultar Id de paciente en bd
public static boolean existePaciente(int id_paciente) {
    
     
    try (
           Connection con = Conexion_DB.getConnection(); 
            PreparedStatement ps = con.prepareStatement("SELECT id FROM paciente WHERE id = ?")) {
        ps.setInt(1, id_paciente);
        try (ResultSet rs = ps.executeQuery()) {
            return rs.next();
        }
    } catch (SQLException ex) {
        Logger.getLogger(Paciente.class.getName()).log(Level.SEVERE, "Error al verificar paciente", ex);
        return false;
    }

}
  
  
}
