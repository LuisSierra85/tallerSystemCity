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
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import modelo.Conexion_DB;

/**
 *
 * @author Luis Sierra
 */
public class Agenda {
     //Declaración de variables
    Connection connection;
    private Integer id;
    private Integer id_paciente;
    private Integer office;
    private LocalDate date;
    private LocalTime time;
    private String state;
    
    
    public Agenda() {//<--Contructor vacio.
        
    }
  //Contructor de la clase agenda
public Agenda(Integer id, Integer id_paciente, Integer office, LocalDate date, LocalTime time, String state) {
        this.id = id;
        this.id_paciente = id_paciente;
        this.office = office;
        this.date = date;
        this.time = time;
        this.state = state;
        
    }

    public Integer getId() {
        return id;
    }

  
    public Integer getId_paciente() {
        return id_paciente;
    }

    public Integer getOffice() {
        return office;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public String getState() {
        return state;
    }

   
   //Función insert new agenda
  public static void insertAgenda(Agenda agenda)  {
  
              
         try {
             Connection con = Conexion_DB.getConnection();
             
             
         PreparedStatement ps = con.prepareStatement("INSERT INTO `agenda`(`id_paciente`, `office`, `date`, `time`, `state`) VALUES (?,?,?,?,?)");

            ps.setInt(1, agenda.getId_paciente());
            ps.setInt(2, agenda.getOffice());
            ps.setDate(3, java.sql.Date.valueOf(agenda.getDate()));
            ps.setTime(4, java.sql.Time.valueOf(agenda.getTime()));
            ps.setString(5, agenda.getState());
           

            if(ps.executeUpdate() != 0){
                JOptionPane.showMessageDialog(null, "New Cita Added");
                
                }
                else{
                    JOptionPane.showMessageDialog(null, "Something Wrong");
                    
                }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error inserting Cita:\n" + ex.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(Paciente.class.getName()).log(Level.SEVERE, null, ex);
        }
      
  
  }
   // get agenda list con Join a tabla paciente por id 
 public ArrayList<Agenda> AgendaList(String val){
 
     ArrayList<Agenda> agenda_List = new ArrayList<>();
     connection = modelo.Conexion_DB.getConnection();
      ResultSet rs;
        PreparedStatement ps;

  String query = "SELECT agenda.id, agenda.id_paciente, office, date, time, state\n" +
"FROM agenda\n" +
"INNER JOIN paciente ON paciente.id = agenda.id_paciente\n" +
"WHERE CONCAT(agenda.id, agenda.office, agenda.date, agenda.time, agenda.state, agenda.id_paciente) LIKE ?;";
        
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, "%" + val + "%");
            rs = ps.executeQuery();
           
            Agenda agenda;
           
            while(rs.next()){
                //el órden del while debe ser como el del constructor
                agenda = new Agenda(rs.getInt("id"), 
                                 rs.getInt("id_paciente"), 
                                 rs.getInt("office"),
                                 rs.getDate("date").toLocalDate(),
                                 rs.getTime("time").toLocalTime(),
                                 rs.getString("state")
                                   );
                
                
                agenda_List.add(agenda);
            }
        
        } catch (SQLException ex) {
            Logger.getLogger(Agenda.class.getName()).log(Level.SEVERE, null, ex);
        }
        return agenda_List;
 
 
 
 }   
 
 //Update agenda
 public static void updateAgenda(Agenda agenda){
 
  try {
             Connection con = Conexion_DB.getConnection();
             
             
         PreparedStatement ps = con.prepareStatement("UPDATE `agenda` SET `id_paciente`=?,`office`=?,`date`=? ,`time`=?,`state`=? WHERE `id` = ?");

            ps.setInt(1, agenda.getId_paciente());
            ps.setInt(2, agenda.getOffice());
            ps.setDate(3, java.sql.Date.valueOf(agenda.getDate()));
            ps.setTime(4, java.sql.Time.valueOf(agenda.getTime()));
            ps.setString(5, agenda.getState());
            //el ID lo debo enviar para actualizar
            ps.setInt(6, agenda.getId());
           

            if(ps.executeUpdate() != 0){
                JOptionPane.showMessageDialog(null, "Agenda Updated");
                
                }
                else{
                    JOptionPane.showMessageDialog(null, "Something Wrong");
                    
                }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error Update Agenda:\n" + ex.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(Paciente.class.getName()).log(Level.SEVERE, null, ex);
        }
 
 
 }
 
 //Delete agenda
public static void deleteAgenda(Integer id){

Connection con = modelo.Conexion_DB.getConnection();
          PreparedStatement ps;
        
        try {
            ps = con.prepareStatement("DELETE FROM `agenda` WHERE `id` = ?");
          
            ps.setInt(1, id);
            
            int YesOrNo = JOptionPane.showConfirmDialog(null,"Do you really want to delete this Agenda?","Delete Agenda", JOptionPane.YES_NO_OPTION);
            if(YesOrNo == 0){
                
                if(ps.executeUpdate() != 0){
                JOptionPane.showMessageDialog(null, "Cita Deleted");
                
                }
                else{
                    JOptionPane.showMessageDialog(null, "Something Wrong");
                    
                }
                
            }
                        
        } catch (SQLException ex) {
           Logger.getLogger(Agenda.class.getName()).log(Level.SEVERE, null, ex);
        }

}
  
    
}
