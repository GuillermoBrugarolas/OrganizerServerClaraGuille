package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.sun.xml.internal.ws.util.StringUtils;

import controller.MainViewController;
import model.DBConnector;
/**
 * Aquesta classe conte els metodes que duen a terme tota la logica del programa.
 * @author nvall
 *
 */
public class Logics {

	private static boolean competition = false;
	private long difference;
	private int duration;
	private MainViewController controller;
	
	public Logics(){

	}
	
	public void registerController(MainViewController controller){
		this.controller = controller;
	}

    public static boolean addUser(String message){
        String[] array = new String[3];
        boolean ok = false;
        message = message.substring(4);
        array = message.split("/");
        System.out.println(array[0]);
        System.out.println(array[1]);
        System.out.println(array[2]);
        if ((checkUniqueUser(array[0]) && (checkUniqueEMail(array[1])))) {
            if (DBConnector.insertUser(array[0], array[1], array[2])) {
                ok = true;
            }
        }
        return ok;
    }

    public static User getUser(String message){
        String userMessage, joinedProjectName;
        LinkedList<Project> ownPs, joinedPs;
        User user = new User();
        Project p, q, r, s;
        ResultSet dbUser, dbOwn, dbJoined, dbJoined2;
        userMessage = message.substring(4);
        if ((userMessage.contains("@")) && (userMessage.contains("."))){
            dbUser = DBConnector.selectEMail(userMessage);
        } else {
            dbUser = DBConnector.selectUser(userMessage);
        }
        try {
            if (dbUser != null && dbUser.next()) {
                user.setNickname((String)dbUser.getObject("nickname"));
                user.setEmail((String)dbUser.getObject("email"));
                user.setPassword((String)dbUser.getObject("password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        dbOwn = DBConnector.selectOwnProjects(userMessage);
        ownPs = new LinkedList<Project>();
        p = new Project();
        try {
            while (dbOwn != null && dbOwn.next()) {
                p.setName((String)dbOwn.getObject("name"));
                p.setId((String)dbOwn.getObject("id"));
                p.setBackground((String)dbOwn.getObject("background"));
                p.setOwner(user);
                ownPs.add(p);
            }
            user.setOwnProjects(ownPs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        dbJoined = DBConnector.selectJoinedProjects(userMessage);
        joinedPs = new LinkedList<Project>();
        q = new Project();
        try {
            while (dbJoined != null && dbJoined.next()) {
                joinedProjectName = (String)dbJoined.getObject("project");
                dbJoined2 = DBConnector.selectProject(joinedProjectName);
                q = new Project();
                try {
                    if (dbJoined2 != null && dbJoined2.next()) {
                        q.setName((String)dbJoined2.getObject("name"));
                        q.setId((String)dbJoined2.getObject("id"));
                        q.setBackground((String)dbJoined2.getObject("background"));
                        q.setOwner(new User((String)dbJoined2.getObject("owner"), null, null, null, null));
                        joinedPs.add(q);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            user.setJoinedProjects(joinedPs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public static boolean logUser(String message){
        String[] array = new String[2];
        boolean ok = false;
        message = message.substring(4);
        array = message.split("/");
        if ((checkLogInUserPassword(array[0], array[1]) || (checkLogInEMailPassword(array[0], array[1])))) {    ok = true;  }
        return ok;
    }

	public static boolean checkUniqueUser(String nickname){
		boolean uniqueNickname = false;
		ResultSet user;
		try {
			user = DBConnector.selectUser(nickname);
            if ((user == null) || (!user.next())){
                uniqueNickname = true;
            } else {
                uniqueNickname = false;
            }
		}catch (Exception e2){
			e2.printStackTrace();
		}
		return uniqueNickname;
	}

    public static boolean checkUniqueEMail(String email){
        boolean uniqueEMail = false;
        ResultSet user;
        try {
            user = DBConnector.selectEMail(email);
            if ((user == null) || (!user.next())){
                uniqueEMail = true;
            } else {
                uniqueEMail = false;
            }
        }catch (Exception e2){
            e2.printStackTrace();
        }
        return uniqueEMail;
    }

    public static boolean checkLogInUserPassword(String nickname, String password){
        boolean found = false;
        ResultSet userPassword;
        try {
            userPassword = DBConnector.selectUserPassword(nickname, password);
            if ((userPassword == null) || (!userPassword.next())){
                found = false;
            } else {
                found = true;
            }
        }catch (Exception e2){
            e2.printStackTrace();
        }
        return found;
    }

    public static boolean checkLogInEMailPassword(String eMail, String password){
        boolean found = false;
        ResultSet eMailPassword;
        try {
            eMailPassword = DBConnector.selectEMailPassword(eMail, password);
            if ((eMailPassword == null) || (!eMailPassword.next())){
                found = false;
            } else {
                found = true;
            }
        }catch (Exception e2){
            e2.printStackTrace();
        }
        return found;
    }

    public static String getAllUsers(){
        String sAllUsersData = "", sSingleUserData = "";
        ResultSet dbAllUsers;
        dbAllUsers = DBConnector.selectAllUsers();
        try {
            while ((dbAllUsers != null) && (dbAllUsers.next())) {
                sSingleUserData = (String)dbAllUsers.getObject("nickname") + "/";
                sAllUsersData = sAllUsersData + sSingleUserData;
            }
            sAllUsersData = sAllUsersData.substring(0, sAllUsersData.length() - 1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sAllUsersData;
    }

    public static LinkedList<TasksObject> getTasksInfo(String users){
        String[] usersList;
        int ii = 0, jj = 0, pending = 0, completed = 0, assigned = 0;
        double percentage = 0.0;
        LinkedList<TasksObject> listByTasks = new LinkedList<TasksObject>();
        TasksObject usersTasksData;
        usersList = users.split("/");
        for (ii = 0; ii < usersList.length; ii++) {
            pending = 0; assigned = 0; completed = 0; percentage = 0.0;
            pending = DBConnector.selectNumOfTasks(usersList[ii], 1);
            completed = DBConnector.selectNumOfTasks(usersList[ii], 0);
            assigned = pending + completed;
            if (assigned >= 1) {
                percentage = ((double)(completed * 100) / assigned);
                System.out.println(String.valueOf(percentage));
                System.out.println('\n');
            } else {
                percentage = 0.0;
                System.out.println(String.valueOf(percentage));
                System.out.println('\n');
            }
            usersTasksData = new TasksObject(usersList[ii], pending, assigned, percentage);
            listByTasks.add(usersTasksData);
        }
        listByTasks.sort(Comparator.<TasksObject>comparingInt(TasksObject::getPending).reversed());
        return listByTasks;
    }
}