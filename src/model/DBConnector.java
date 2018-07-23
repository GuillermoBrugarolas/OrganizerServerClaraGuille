package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnector {

    private static String userName;

    private static String password;

    private static String db;

    private static int port;

    private static String url = "jdbc:mysql://";

    private static Connection conn = null;

    private static Statement s;

    public DBConnector(String usr, String pass, String db, int port, String ip) {
        userName = usr;
        password = pass;
        DBConnector.db = db;
        DBConnector.port = port;
        url += ip;
        url += ":"+port+"/";
        url += db;
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
        String query = "INSERT INTO users (nickname, email, password) VALUES ('"+nickname+"','"+email+"','"+password+"')";
        try {
            s =(Statement) conn.createStatement();
            s.executeUpdate(query);

        } catch (SQLException ex) {
            System.out.println("Problema al Inserir --> " + ex.getSQLState());
            return false;
        }
        return true;
    }

    /**
     * Insereix un nou projecte a la base de dades incloent tot els seus atributs.
     * @param p
     * @return
     */
    public static boolean insertProject(Project p){
        String name, id, background, owner, first, second, third;
        name = p.getName();
        id = p.getId();
        first = p.getColumn(1).getName();
        second = p.getColumn(2).getName();
        third = p.getColumn(3).getName();
        background = p.getBackground();
        owner = p.getOwner().getNickname();
        String query = "INSERT INTO projects (name, id, background, " +
                "owner, firstcolumnname, secondcolumnname, thirdcolumnname) VALUES ('"+
                name+"','"+id+"','"+background+"','"+owner+"','"+first+"','"+second+"','"+third+"')";
        try {
            s =(Statement) conn.createStatement();
            s.executeUpdate(query);

        } catch (SQLException ex) {
            System.out.println("Problema al inserir projecte");
            System.out.println("Problema al Inserir --> " + ex.getSQLState());
            return false;
        }
        return true;
    }

    /**
     * Aquest mètode inserta els membres dun projecte a la base de dades.
     * @param p
     * @return
     */
    public static boolean insertMembers(Project p){
        int in, size;
        String name, memberName;
        name = p.getName();
        size = p.getMembers().size();
        for (in = 0; in < size; in++){
            memberName = p.getMembers().get(in).getNickname();
            String query = "INSERT INTO members_projects (member, project) VALUES ('"+
                    memberName+"','"+name+"')";
            try {
                s = conn.createStatement();
                s.executeUpdate(query);
            } catch (SQLException ex) {
                System.out.println("Problema al inserir membres");
                System.out.println("Problema al Inserir --> " + ex.getSQLState());
                return false;
            }
        }
        return true;
    }

    /**
     * Aquest mètode insereix a la base de dades les tasques associades a un mateix projecte.
     * @param p
     * @return
     */
    public static boolean insertTasks(Project p){
        int in, si, cols, colPos;
        Column c;
        Task t;
        String taskName, desc, userInCharge, tag, project, category;
        project = p.getName();
        for (cols = 0; cols < 3; cols++) {
            c = p.getColumn(cols+1);
            category = c.getName();
            System.out.println("Column name: "+category);
            colPos = c.getPosition();
            si = c.getTasks().size();
            System.out.println("Nº of tasks: "+String.valueOf(si));
            for (in = 0; in < si; in++) {
                t = c.getTasks().get(in);
                taskName = t.getName();
                desc = t.getDescription();
                userInCharge = t.getUserAssigned().getNickname();
                tag = t.getTag().getName();
                String str = "Inserting: "+taskName+" + "+String.valueOf(colPos)+" + "+desc+" + "+userInCharge+" + "+tag+" + "+project+" + "+category+" ::";
                System.out.println(str);
                String query = "INSERT INTO tasques (name, columnposition, description, userincharge," +
                        " tagname, project, category) VALUES ('" +
                        taskName + "','" + colPos + "','" + desc + "','" + userInCharge + "','" + tag +
                        "','" + project + "','" + category + "')";
                try {
                    s = (Statement) conn.createStatement();
                    s.executeUpdate(query);
                } catch (SQLException ex) {
                    System.out.println("Problema al inserir tasques");
                    System.out.println("Problema al Inserir --> " + ex.getSQLState());
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Aquest mètode insereix una tasca a la base de dades.
     * @param t
     * @return
     */
    public static boolean insertTask(Task t){
        int in, si, cols, colPos;
        String taskName, desc, userInCharge, tag, project, category;
        taskName = t.getName();
        desc = t.getDescription();
        colPos = t.getColumn();
        userInCharge = t.getUserAssigned().getNickname();
        tag = t.getTag().getName();
        project = t.getProject();
        category = t.getCategory();
        String query = "INSERT INTO tasques (name, columnposition, description, userincharge," +
                " tagname, project, category) VALUES ('" +
                taskName + "','" + colPos + "','" + desc + "','" + userInCharge + "','" + tag +
                "','" + project + "','" + category + "')";
        try {
            s = (Statement) conn.createStatement();
            s.executeUpdate(query);
        } catch (SQLException ex) {
            System.out.println("Problema al Inserir --> " + ex.getSQLState());
            return false;
        }
        return true;
    }

    /**
     * Aquest mètode elimina un usuari de la base de dades.
     * @param nickname
     */
    public static void deleteUser(String nickname){
        String query = "DELETE FROM users WHERE nickname='"+nickname+"'";
        try {
            s = conn.createStatement();
            s.executeUpdate(query);
        } catch (SQLException ex) {
            System.out.println("Problema al Eliminar --> " + ex.getSQLState());
        }

    }

    /**
     * Aquest mètode elimina una tasca (passa a completades).
     * @param taskName
     * @return
     */
    public static boolean deleteTask(String taskName) {
        String query1 = "INSERT INTO tasques_compl SELECT * FROM tasques WHERE name='" + taskName + "'";
        try {
            s = conn.createStatement();
            s.executeUpdate(query1);
        } catch (SQLException ex) {
            System.out.println("Problema al Moure --> " + ex.getSQLState());
            return false;
        }
        String query2 = "DELETE FROM tasques WHERE name='" + taskName + "'";
        try {
            s = conn.createStatement();
            s.executeUpdate(query2);
        } catch (SQLException ex) {
            System.out.println("Problema al Eliminar --> " + ex.getSQLState());
            return false;
        }
        return true;
    }

    /**
     * Aquest mètode elimina un projecte de la base de dades.
     * @param projectName
     * @return
     */
    public static boolean deleteProject(String projectName){
        String query1 = "DELETE FROM projects WHERE name='"+projectName+"'";
        try {
            s = conn.createStatement();
            s.executeUpdate(query1);
        } catch (SQLException ex) {
            System.out.println("Problema al Eliminar --> " + ex.getSQLState());
            return false;
        }
        String query2 = "DELETE FROM tasques WHERE project='"+projectName+"'";
        try {
            s = conn.createStatement();
            s.executeUpdate(query2);
        } catch (SQLException ex) {
            System.out.println("Problema al Eliminar --> " + ex.getSQLState());
            return false;
        }
        String query3 = "DELETE FROM tasques_compl WHERE project='"+projectName+"'";
        try {
            s = conn.createStatement();
            s.executeUpdate(query3);
        } catch (SQLException ex) {
            System.out.println("Problema al Eliminar --> " + ex.getSQLState());
            return false;
        }
        String query4 = "DELETE FROM members_projects WHERE name='"+projectName+"'";
        try {
            s = conn.createStatement();
            s.executeUpdate(query4);
        } catch (SQLException ex) {
            System.out.println("Problema al Eliminar --> " + ex.getSQLState());
            return false;
        }
        String query5 = "DELETE FROM categories_projects WHERE name='"+projectName+"'";
        try {
            s = conn.createStatement();
            s.executeUpdate(query5);
        } catch (SQLException ex) {
            System.out.println("Problema al Eliminar --> " + ex.getSQLState());
            return false;
        }
        return true;
    }
    /**
     * retorna un result set amb tots el usuaris registrats a la base de dades.
     */
    public static ResultSet selectAllUsers(){
        ResultSet rs = null;
        String query = "SELECT * FROM users";
        try {
            s = conn.createStatement();
            rs = s.executeQuery (query);
        } catch (SQLException ex) {
            System.out.println("Problema al Recuperar les dades --> " + ex.getSQLState());
        }
        return rs;
    }

    /**
     * Aquest mètode retorna el nombre de tasques associades a un usuari.
     * @param username
     * @param pendNotCompl
     * @return
     */
    public static int selectNumOfTasks(String username, int pendNotCompl){
        int count = 0;
        String query="";
        ResultSet rs;
        if (pendNotCompl == 1) {
            query = "SELECT COUNT(userincharge) FROM tasques WHERE userincharge='" + username + "'";
        } else if (pendNotCompl == 0){
            query = "SELECT COUNT(userincharge) FROM tasques_compl WHERE userincharge='" + username + "'";
        }
        try {
            s = conn.createStatement();
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
        String query = "SELECT * FROM users WHERE nickname='"+nickname+"'";
        try {
            s = conn.createStatement();
            rs = s.executeQuery(query);
        } catch (SQLException ex) {
            System.out.println("Problema al Recuperar les dades --> " + ex.getSQLState());
        }
        return rs;
    }

    /**
     * Aquest mètode selecciona els projectes propis dun usuari.
     * @param nickname
     * @return
     */
    public static ResultSet selectOwnProjects(String nickname){
        ResultSet rs = null;
        String query = "SELECT * FROM projects WHERE owner='"+nickname+"'";
        try {
            s = conn.createStatement();
            rs = s.executeQuery(query);
        } catch (SQLException ex) {
            System.out.println("Problema al Recuperar les dades --> " + ex.getSQLState());
        }
        return rs;
    }

    /**
     * Aquest projecte selecciona els projectes als quals sha unit lusuari.
     * @param nickname
     * @return
     */
    public static ResultSet selectJoinedProjects(String nickname){
        ResultSet rs = null;
        String query = "SELECT * FROM members_projects WHERE member='"+nickname+"'";
        try {
            s = conn.createStatement();
            rs = s.executeQuery (query);
        } catch (SQLException ex) {
            System.out.println("Problema al Recuperar les dades --> " + ex.getSQLState());
        }
        return rs;
    }

    /**
     * Aquest projecte selecciona els membres del projecte que es passa per paràmetre.
     * @param projectName
     * @return
     */
    public static ResultSet selectMembers(String projectName){
        ResultSet rs = null;
        String query = "SELECT * FROM members_projects WHERE project='"+projectName+"'";
        try {
            s = conn.createStatement();
            rs = s.executeQuery (query);
        } catch (SQLException ex) {
            System.out.println("Problema al Recuperar les dades --> " + ex.getSQLState());
        }
        return rs;
    }

    /**
     * Aquest mètodde retorna el projecte amb nom igual al paràmetre.
     * @param name
     * @return
     */
    public static ResultSet selectProject(String name){
        ResultSet rs = null;
        String query = "SELECT * FROM projects WHERE name='"+name+"'";
        try {
            s = conn.createStatement();
            rs = s.executeQuery (query);
        } catch (SQLException ex) {
            System.out.println("Problema al Recuperar les dades --> " + ex.getSQLState());
        }
        return rs;
    }

    /**
     * Aquest mètode retorna les tasques associades a un mateix projecte.
     * @param projectName
     * @return
     */
    public static ResultSet selectTasks(String projectName){
        ResultSet rs = null;
        String query = "SELECT * FROM tasques WHERE project='"+projectName+"'";
        try {
            s = conn.createStatement();
            rs = s.executeQuery (query);
        } catch (SQLException ex) {
            System.out.println("Problema al Recuperar les dades --> " + ex.getSQLState());
        }
        return rs;
    }

    /**
     * Aquest mètode retorna el projecte amb ID igual al paràmetre.
     * @param id
     * @return
     */
    public static ResultSet getProjectByID(String id){
        ResultSet rs = null;
        String query = "SELECT * FROM projects WHERE id='"+id+"'";
        try {
            s = conn.createStatement();
            rs = s.executeQuery (query);
        } catch (SQLException ex) {
            System.out.println("Problema al Recuperar les dades --> " + ex.getSQLState());
        }
        return rs;
    }

    /**
     * Aquest mètode retorna si sha trobat lusuari amb nom i contrassenya indicats.
     * @param nickname
     * @param password
     * @return
     */
    public static ResultSet selectUserPassword(String nickname, String password){
        ResultSet rs = null;
        String query = "SELECT * FROM users WHERE nickname='"+nickname+"' and password='"+password+"'";
        try {
            s = conn.createStatement();
            rs = s.executeQuery (query);
        } catch (SQLException ex) {
            System.out.println("Problema al Recuperar les dades --> " + ex.getSQLState());
        }
        return rs;
    }

    /**
     * Aquest mètode retorna lusuari amb mail indicat.
     * @param email
     * @return
     */
    public static ResultSet selectEMail(String email){
        ResultSet rs = null;
        String query = "SELECT * FROM users WHERE email='"+email+"'";
        try {
            s = conn.createStatement();
            rs = s.executeQuery (query);
        } catch (SQLException ex) {
            System.out.println("Problema al Recuperar les dades --> " + ex.getSQLState());
        }
        return rs;
    }

    /**
     * Aquest mètode retorna lusuari amb nom i contrassenya indicats.
     * @param email
     * @param password
     * @return
     */
    public static ResultSet selectEMailPassword(String email, String password){
        ResultSet rs = null;
        String query = "SELECT * FROM users WHERE email='"+email+"' and password='"+password+"'";
        try {
            s = conn.createStatement();
            rs = s.executeQuery (query);
        } catch (SQLException ex) {
            System.out.println("Problema al Recuperar les dades --> " + ex.getSQLState());
        }
        return rs;
    }

    /**
     * Aquest mètode afegeix un usuari com a membre dun projecte.
     * @param userName
     * @param projectName
     * @return
     */
    public static boolean addUserToProject(String userName, String projectName){
        String query = "INSERT INTO members_projects (member, project) VALUES ('"+
                userName+"','"+projectName+"')";
        try {
            s = conn.createStatement();
            s.executeUpdate(query);
        } catch (SQLException ex) {
            System.out.println("Problema al Inserir --> " + ex.getSQLState());
            return false;
        }
        return true;
    }

    /**
     * Elimina un membre dun projecte.
     * @param userName
     * @param projectName
     * @return
     */
    public static boolean deleteMemberFromProject(String userName, String projectName){
        String query = "DELETE FROM members_projects WHERE project='"+projectName+"' AND member='"+userName+"'";
        try {
            s = conn.createStatement();
            s.executeUpdate(query);
        } catch (SQLException ex) {
            System.out.println("Problema al Eliminar --> " + ex.getSQLState());
            return false;
        }
        return true;
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

