/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package java_products_manager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Administrator
 */
public class Database {
    
    public static final String DB_URL = "jdbc:sqlite:";
    public static final String DB_NAME = "Database.db";
    public static final String APP_DATA_DIR = "Application Data";
    
     public static String GetDatabasePath() {
        // Create the Application Data folder if it doesn't exist
          File appDataFolder = new File(APP_DATA_DIR);
          if (!appDataFolder.exists()) {
              appDataFolder.mkdir();
              JOptionPane.showMessageDialog(null, "Application Data folder created successfully.");
          }
              
            // Construct the full database path
            String databasePath = appDataFolder.getAbsolutePath() + File.separator + DB_NAME;
           
            // Check if the database file exists
            File databaseFile = new File(databasePath);
            if (!databaseFile.exists()) {
              try {
                  // Create the database file
                  databaseFile.createNewFile();
                  JOptionPane.showMessageDialog(null, "Database file created successfully.");
              } catch (IOException ex) {
                 JOptionPane.showMessageDialog(null, "Database file creation failed.");
              }
            }
          
           
        return databasePath;
    }
     
     public static Connection DatabaseConnection(){     
        try {
            Class.forName("org.sqlite.JDBC"); // Register the SQLite JDBC driver
            Connection connection = DriverManager.getConnection(DB_URL + GetDatabasePath());
            
            return connection;
        } catch (SQLException | ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Oops, something went wrong on: " + ex.getMessage());
        }
        return null;
     }

    public static void DatabaseCreate() {
            if(DatabaseConnection() != null){
                try {
                    
                    Statement statement = DatabaseConnection().createStatement();
                    
                    String SQL = "CREATE TABLE IF NOT EXISTS Products (id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR(45) NOT NULL, category VARCHAR(45) NOT NULL, quantity INT, price DOUBLE NOT NULL, image_path VARCHAR(45) NOT NULL)";
                    
                    statement.executeUpdate(SQL);
                    
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Oops, something went wrong on: " + ex.getMessage());
                }
            }
        }
            
        
   public static void InsertProduct(String name, String category, int quantity, double price, String image_path){
          if(DatabaseConnection() != null){ 
                
                try{
 
                    // Prepare SQL statement with placeholders
                    PreparedStatement ps = DatabaseConnection().prepareStatement("insert into products(name, category, quantity, price, image_path) VALUES(?, ?, ?, ?, ?)");
                    
                    // Set values using prepared statement parameters
                    ps.setString(1, name);
                    ps.setString(2, category);
                    ps.setInt(3, quantity);
                    ps.setDouble(4, price);
                    ps.setString(5, image_path);
                    
                    // Execute the insert query
                    ps.executeUpdate();

                    JOptionPane.showMessageDialog(null,"New product inserted!!!");
                    
                }
                catch(Exception ex){
                    JOptionPane.showMessageDialog(null, "Oops, something went wrong on: " + ex.getMessage());
                }
          }
          
   }
   
   public static void EditProduct(String name, String category, int quantity, double price, String image_path, int ID){
          if(DatabaseConnection() != null){ 
                
                try{
 
                    // Prepare SQL statement with placeholders
                    PreparedStatement ps = DatabaseConnection().prepareStatement("update products set name = ?, category = ?, quantity = ?, price = ?, image_path = ? where id = ?");
                    
                    // Set values using prepared statement parameters
                    ps.setString(1, name);
                    ps.setString(2, category);
                    ps.setInt(3, quantity);
                    ps.setDouble(4, price);
                    ps.setString(5, image_path);
                    ps.setInt(6, ID);
                    
                    // Execute the insert query
                    ps.executeUpdate();

                    JOptionPane.showMessageDialog(null,"Product Modified!!!");
                    ManageProducts_Frame main = new ManageProducts_Frame();
                }
                catch(Exception ex){
                    JOptionPane.showMessageDialog(null, "Oops, something went wrong on: " + ex.getMessage());
                }
          }
   }
   
           public static void DeleteProduct(int ID){
          if(DatabaseConnection() != null){ 
                
                try{
 
                    // Prepare SQL statement with placeholders
                    PreparedStatement ps = DatabaseConnection().prepareStatement("delete from products where id = ?");
                    
                    // Set values using prepared statement parameters
                    ps.setInt(1, ID);
                    
                    // Execute the insert query
                    ps.executeUpdate();

                    JOptionPane.showMessageDialog(null,"Product Deleted!!!");
                    ManageProducts_Frame main = new ManageProducts_Frame();
                }
                catch(Exception ex){
                    JOptionPane.showMessageDialog(null, "Oops, something went wrong on: " + ex.getMessage());
                }
          }
   }
}

