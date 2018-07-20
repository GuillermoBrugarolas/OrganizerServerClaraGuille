package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnector {

    static String userName;

    static String password;

    static String db;

    static int port;

    static String url = "jdbc:mysql://";

    static Connection conn = null;

    static Statement s;

    public DBConnector(String usr, String pass, String db, int port, String ip) {
        this.userName = usr;
        this.password = pass;
        this.db = db;
        this.port = port;
        this.url += ip;
        this.url += ":"+port+"/";
        this.url += db;
    }
    /**
     * estableix la connexio amb la base de dades.
     */
    public void connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = (Connection) DriverManager.getConnection(url, userName, password);
            if (conn != null) {
                System.out.println("Conexió a base de dades "+url+" ... Ok");
            }
        }
        catch(SQLException ex) {
            System.out.println("Problema al connecta-nos a la BBDD --> "+url);
            ex.printStackTrace();
        }
        catch(ClassNotFoundException ex) {
            System.out.println(ex);
        }
    }
    /**
     * insereix un nou usuari amb eln nom i la contrassenya que rep.
     */
    public static boolean insertUser(String nickname, String email, String password){
        String query = new String("INSERT INTO users (nickname, email, password) VALUES ('"+nickname+"','"+email+"','"+password+"')");
        try {
            s =(Statement) conn.createStatement();
            s.executeUpdate(query);

        } catch (SQLException ex) {
            System.out.println("Problema al Inserir --> " + ex.getSQLState());
            return false;
        }
        return true;
    }

    public static void updateScore(String nickname, int score){
        String query = new String("UPDATE usuari SET score='"+score+"' WHERE nickname='"+nickname+"'");
        try {
            s =(Statement) conn.createStatement();
            s.executeUpdate(query);

        } catch (SQLException ex) {
            System.out.println("Problema al Modificar --> " + ex.getSQLState());
        }
    }

    public static void deleteUser(String nickname){
        String query = new String("DELETE FROM users WHERE nickname='"+nickname+"'");
        try {
            s =(Statement) conn.createStatement();
            s.executeUpdate(query);

        } catch (SQLException ex) {
            System.out.println("Problema al Eliminar --> " + ex.getSQLState());
        }

    }
    /**
     * retorna un result set amb tots el usuaris registrats a la base de dades.
     */
    public static ResultSet selectAllUsers(){
        ResultSet rs = null;
        String query = new String("SELECT * FROM users");
        try {
            s =(Statement) conn.createStatement();
            rs = s.executeQuery (query);

        } catch (SQLException ex) {
            System.out.println("Problema al Recuperar les dades --> " + ex.getSQLState());
        }
        return rs;
    }

    public static int selectNumOfTasks(String username, int pendNotCompl){
        int count = 0;
        String query="";
        ResultSet rs = null;
        if (pendNotCompl == 1) {
            query = new String("SELECT COUNT(userincharge) FROM tasques WHERE userincharge='" + username + "'");
        } else if (pendNotCompl == 0){
            query = new String("SELECT COUNT(userincharge) FROM tasques_compl WHERE userincharge='" + username + "'");
        }
        try {
            s =(Statement) conn.createStatement();
            rs = s.executeQuery(query);
            while(rs.next())
                count = rs.getInt(1);
        } catch (SQLException ex) {
            System.out.println("Problema al Recuperar les dades --> " + ex.getSQLState());
        }
        return count;
    }
    /**
     * retorna un result set de la base de dades amb un usuari amb el nom corresponent al que rep.
     */
    public static ResultSet selectUser(String nickname){
        ResultSet rs = null;
        String query = new String("SELECT * FROM users WHERE nickname='"+nickname+"'");
        try {
            s =(Statement) conn.createStatement();
            rs = s.executeQuery(query);

        } catch (SQLException ex) {
            System.out.println("Problema al Recuperar les dades --> " + ex.getSQLState());
        }
        return rs;
    }

    public static ResultSet selectOwnProjects(String nickname){
        ResultSet rs = null;
        String query = new String("SELECT * FROM projects WHERE owner='"+nickname+"'");
        try {
            s =(Statement) conn.createStatement();
            rs = s.executeQuery(query);

        } catch (SQLException ex) {
            System.out.println("Problema al Recuperar les dades --> " + ex.getSQLState());
        }
        return rs;
    }

    public static ResultSet selectJoinedProjects(String nickname){
        ResultSet rs = null;
        String query = new String("SELECT * FROM members_projects WHERE member='"+nickname+"'");
        try {
            s =(Statement) conn.createStatement();
            rs = s.executeQuery (query);

        } catch (SQLException ex) {
            System.out.println("Problema al Recuperar les dades --> " + ex.getSQLState());
        }
        return rs;
    }

    public static ResultSet selectProject(String name){
        ResultSet rs = null;
        String query = new String("SELECT * FROM projects WHERE name='"+name+"'");
        try {
            s =(Statement) conn.createStatement();
            rs = s.executeQuery (query);

        } catch (SQLException ex) {
            System.out.println("Problema al Recuperar les dades --> " + ex.getSQLState());
        }
        return rs;
    }

    public static ResultSet selectUserPassword(String nickname, String password){
        ResultSet rs = null;
        String query = new String("SELECT * FROM users WHERE nickname='"+nickname+"' and password='"+password+"'");
        try {
            s =(Statement) conn.createStatement();
            rs = s.executeQuery (query);

        } catch (SQLException ex) {
            System.out.println("Problema al Recuperar les dades --> " + ex.getSQLState());
        }
        return rs;
    }

    public static ResultSet selectEMail(String email){
        ResultSet rs = null;
        String query = new String("SELECT * FROM users WHERE email='"+email+"'");
        try {
            s =(Statement) conn.createStatement();
            rs = s.executeQuery (query);

        } catch (SQLException ex) {
            System.out.println("Problema al Recuperar les dades --> " + ex.getSQLState());
        }
        return rs;
    }

    public static ResultSet selectEMailPassword(String email, String password){
        ResultSet rs = null;
        String query = new String("SELECT * FROM users WHERE email='"+email+"' and password='"+password+"'");
        try {
            s =(Statement) conn.createStatement();
            rs = s.executeQuery (query);

        } catch (SQLException ex) {
            System.out.println("Problema al Recuperar les dades --> " + ex.getSQLState());
        }
        return rs;
    }


    /**
     * tanca la connexio amb la base de dades.
     */
    public void disconnect(){
        try {
            conn.close();
        } catch (SQLException e) {
            System.out.println("Problema al tancar la connexio --> " + e.getSQLState());
        }
    }

}

