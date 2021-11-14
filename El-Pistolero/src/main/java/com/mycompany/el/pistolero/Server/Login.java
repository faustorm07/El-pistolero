/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.el.pistolero.Server;
import com.sun.net.httpserver.*;
import java.net.*;
import java.sql.*;
import java.io.*;  
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author fausto
 */

public class Login implements HttpHandler{
    @Override
    public void handle(HttpExchange exchange) throws IOException{
    URI uri = exchange.getRequestURI();
    
String name = new File(uri.getPath()).getName();
    File path = new File("/Users/fausto/NetBeansProjects/El-pistolero/J", name);
    Headers h = exchange.getResponseHeaders();
    h.add("Content-Type", "*/*");
    OutputStream out = exchange.getResponseBody();
    String pass = "NULL";
String usuario = "NULL";

if (path.exists()) {
      exchange.sendResponseHeaders(200, path.length());
      out.write(Files.readAllBytes(path.toPath()));
    } else {
      System.err.println("File not found: " + path.getAbsolutePath());

      exchange.sendResponseHeaders(200, path.length());
      out.write(Files.readAllBytes(path.toPath()));
    }
    
           String query = exchange.getRequestURI().getQuery();
           if(query.equals("usr=&pass=")){
       } else {
               String [] keyValues = query.split("&");
               usuario = keyValues[0].split("=")[1];
               pass = keyValues[1].split("=")[1];
               System.out.println("passw "+pass);
               System.out.println("usr "+usuario);
        }
                       Usuario usuarioent = new Usuario();
                       usuarioent.setUsrname(usuario);
                       usuarioent.setPassw(pass);
        try {
            if(logUsuario(usuarioent)== true){
                
                System.out.println("Usuario Logeado");
            }
            else{
            
            }
        } catch (Exception ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
    out.close();

    }
     public static boolean logUsuario(Usuario usuarioent)throws Exception{
         Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:8889/elpistolero","root","root");
    Statement stmt  = conn.createStatement();
    String sql = "SELECT `username`,`passw` FROM `Usuario`\n" +
    " WHERE username = '"+usuarioent.getUsrname()+"'" +
    "  AND passw = '"+usuarioent.getPassw()+"' ;";
    System.out.println(sql);
    ResultSet rs = stmt.executeQuery(sql);
    if (rs.next() == false){
        return false;
    }
    else{
        return true;
        }
    }
}
