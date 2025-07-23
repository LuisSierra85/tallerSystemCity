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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Luis Sierra
 */
public class Users {
    //Declaración de variables
    Connection connection;
    private Integer id;
    private String fullname;
    private String username;
    private String password;
    private String phone;
    private String gender;
    private byte[] picture;

    public Users(){//<--Contructor vacio.
    
    
    }
    //Contructor de la clase Users
    public Users(Integer ID, String UNAME, String PASW, String FNAME, String TEL, String GENDER, byte[] PICTURE){
    
        this.id = ID;
        this.username = UNAME;
        this.password = PASW;
        this.fullname = FNAME;
        this.phone = TEL;
        this.gender = GENDER;
        this.picture=PICTURE;
        
    }
    
    public Integer getId() {
        return id;
    }

    public String getFullname() {
        return fullname;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }


    public String getPhone() {
        return phone;
    }

    public String getGender() {
        return gender;
    }

    public byte[] getPicture() {
        return picture;
    }
   
     
    // insert a new user
public static void insertUser(Users user){
    
        Connection con = modelo.Conexion_DB.getConnection();
        PreparedStatement ps;
        
        try {
            ps = con.prepareStatement("INSERT INTO `users`(`full_name`, `username`, `password`, `phone`, `gender`,`picture`) VALUES (?,?,?,?,?,?)");

            ps.setString(1, user.getFullname());
            ps.setString(2, user.getUsername());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getPhone());
            ps.setString(5, user.getGender());
            ps.setBytes(6, user.getPicture());
            
            

            if(ps.executeUpdate() != 0){
                JOptionPane.showMessageDialog(null, "New User Added");
                
                }
                else{
                    JOptionPane.showMessageDialog(null, "Something Wrong");
                    
                }
            
        } catch (SQLException ex) {
            Logger.getLogger(Users.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     
     
      
    // get users list
    public ArrayList<Users> UsersList(){
        
        ArrayList<Users> user_list = new ArrayList<>();
        connection = modelo.Conexion_DB.getConnection();

        ResultSet rs;
        PreparedStatement ps;

               String query = "SELECT `id`,`username`,`password`,`full_name`,`phone`, `gender`, `picture`FROM `users`";
        
        try {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
           //el órden del while debe ser como el del constructor
            Users user;
            while(rs.next()){
                user = new Users(rs.getInt("id"),
                                 rs.getString("username"),
                                 rs.getString("password"),
                                 rs.getString("full_name"),
                                 rs.getString("phone"),
                                 rs.getString("gender"),
                                 rs.getBytes("picture")
                                  );
                user_list.add(user);
            }
        
        } catch (SQLException ex) {
            Logger.getLogger(Users.class.getName()).log(Level.SEVERE, null, ex);
           
        }
        return user_list;
        
    }
    
   
public static void updateUsers(Users user, boolean changeImage){
    
        Connection con = modelo.Conexion_DB.getConnection();
        PreparedStatement ps;
        
         // si es true es por que cambió la imagen
        if(changeImage)
        {
           
            try {
            ps = con.prepareStatement("UPDATE `users` SET `full_name`=?,`username`=?,`password`=? ,`phone`=? ,`gender`=? ,`picture`=? WHERE `id` = ?");
            
            ps.setString(1, user.getFullname());
            ps.setString(2, user.getUsername());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getPhone());
            ps.setString(5, user.getGender());
            ps.setBytes(6, user.getPicture());
            //el ID lo debo enviar para actualizar
            ps.setInt(7, user.getId());

            if(ps.executeUpdate() != 0){
                JOptionPane.showMessageDialog(null, "Product Updated");
                
                }
                else{
                    JOptionPane.showMessageDialog(null, "Something Wrong");
                    
                }
            
        } catch (SQLException ex) {
            Logger.getLogger(Users.class.getName()).log(Level.SEVERE, null, ex);
        }

            
        }
        //no actualiza la imagen
        else{
            
            try {
            ps = con.prepareStatement("UPDATE `users` SET `full_name`=?,`username`=?,`password`=? ,`phone`=? ,`gender`=?  WHERE `id` = ?");
            
            ps.setString(1, user.getFullname());
            ps.setString(2, user.getUsername());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getPhone());
            ps.setString(5, user.getGender());
           //el ID lo debo enviar para actualizar
            ps.setInt(6, user.getId());

            if(ps.executeUpdate() != 0){
                JOptionPane.showMessageDialog(null, "Users Updated");
                
                }
                else{
                    JOptionPane.showMessageDialog(null, "Something Wrong");
                    
                }
            
        } catch (SQLException ex) {
            Logger.getLogger(Users.class.getName()).log(Level.SEVERE, null, ex);
        }

            
        }
    
    
    }
    
    
     // delete Users
public static void deleteUsers(Integer id)
    {
          Connection con = modelo.Conexion_DB.getConnection();
        PreparedStatement ps;
        
        try {
            ps = con.prepareStatement("DELETE FROM `users` WHERE `id` = ?");
          
            ps.setInt(1, id);
           
            int YesOrNo = JOptionPane.showConfirmDialog(null,"Do You Really Want To Delete This Users","Delete Users", JOptionPane.YES_NO_OPTION);
            if(YesOrNo == 0){
                
                if(ps.executeUpdate() != 0){
                JOptionPane.showMessageDialog(null, "Users Deleted");
                
                }
                else{
                    JOptionPane.showMessageDialog(null, "Something Wrong");
                    
                }
                
            }
                        
        } catch (SQLException ex) {
           Logger.getLogger(Users.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
}
