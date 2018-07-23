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
        if ((checkUniqueUser(array[0]) && (checkUniqueEMail(array[1])))) {
            if (DBConnector.insertUser(array[0], array[1], array[2])) {
                ok = true;
            }
        }
        return ok;
    }

    public static User getUser(String message){
        String userMessage, joinedProjectName, name, id, background;
        LinkedList<Project> ownPs, joinedPs;
        User user = new User(), us;
        Project p, q;
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
        dbOwn = DBConnector.selectOwnProjects(user.getNickname());
        ownPs = new LinkedList<Project>();
        try {
            while (dbOwn != null && dbOwn.next()) {
                name = (String)dbOwn.getObject("name");
                id = (String)dbOwn.getObject("id");
                background = (String)dbOwn.getObject("background");
                us = user;
                ownPs.add(new Project(us, null, name, id, null, null, null, background));
            }
            user.setOwnProjects(ownPs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        dbJoined = DBConnector.selectJoinedProjects(user.getNickname());
        joinedPs = new LinkedList<Project>();
        try {
            q = new Project();
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
        String sAllUsersData = "", sSingleUserData;
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
        int ii, pending, completed, assigned;
        double percentage;
        LinkedList<TasksObject> listByTasks = new LinkedList<TasksObject>();
        TasksObject usersTasksData;
        usersList = users.split("/");
        for (ii = 0; ii < usersList.length; ii++) {
            pending = DBConnector.selectNumOfTasks(usersList[ii], 1);
            completed = DBConnector.selectNumOfTasks(usersList[ii], 0);
            assigned = pending + completed;
            if (assigned >= 1) {
                percentage = ((double)(completed * 100) / assigned);
            } else {
                percentage = 0.0;
            }
            usersTasksData = new TasksObject(usersList[ii], pending, assigned, percentage);
            listByTasks.add(usersTasksData);
        }
        listByTasks.sort(Comparator.<TasksObject>comparingInt(TasksObject::getPending).reversed());
        return listByTasks;
    }

    public static boolean addNewProject(Project p){
        boolean added1 = false, added2 = false, added3 = false, added = false;
        if (DBConnector.insertProject(p)){
            added1 = true;
        }
        if (DBConnector.insertMembers(p)){
            added2 = true;
        }
        if (DBConnector.insertTasks(p)){
            added3 = true;
        }
        if (added1 && added2 && added3){    added = true;}
        else {  added = false;}
        return added;
    }

    public static boolean addNewTask(Task t){
        boolean added = false;
        if (DBConnector.insertTask(t)){
            added = true;
        }
        return added;
    }

    public static boolean deleteProject(String message){
        boolean ok = false;
        message = message.substring(4);
        if (DBConnector.deleteProject(message)){
            ok = true;
        } else { ok = false;}
        return ok;
    }

    public static boolean joinProject(String message){
        String[] array = new String[2];
        String projectName="";
        ResultSet rs;
        boolean ok = false;
        message = message.substring(4);
        array = message.split("/");
        rs = DBConnector.getProjectByID(array[1]);
        try {
            if ((rs != null) && (rs.next())) {
                projectName = (String)rs.getObject("name");
                System.out.println(projectName);
                if (DBConnector.addUserToProject(array[0], projectName)) {
                    ok = true;
                } else {    ok = false;}
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return ok;
    }

    public static Project getProject(String message){
        String projectName, name, id, background, owner, col1Name, col2Name, col3Name, memberName, projNameTask;
        int colPos;
        String taskName, description, userInCharge, tagName, category;
        LinkedList<Project> ownPs, joinedPs;
        Column c1 = new Column(null, 1, new LinkedList<Task>());
        Column c2 = new Column(null, 2, new LinkedList<Task>());
        Column c3 = new Column(null, 3, new LinkedList<Task>());;
        User uss, member;
        Task task;
        Project proj = new Project();
        ResultSet dbProject, dbTasks, dbMembers;
        projectName = message.substring(4);
        dbProject = DBConnector.selectProject(projectName);
        try {
            if (dbProject != null && dbProject.next()) {
                proj.setName((String)dbProject.getObject("name"));
                proj.setId((String)dbProject.getObject("id"));
                proj.setBackground((String)dbProject.getObject("background"));
                owner = (String)dbProject.getObject("owner");
                uss = getUser(owner);
                proj.setOwner(uss);
                c1.setName((String)dbProject.getObject("firstcolumnname"));
                proj.setColumnOne(c1);
                c2.setName((String)dbProject.getObject("secondcolumnname"));
                proj.setColumnTwo(c2);
                c3.setName((String)dbProject.getObject("thirdcolumnname"));
                proj.setColumnThree(c3);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        dbTasks = DBConnector.selectTasks(proj.getName());
        try {
            while (dbTasks != null && dbTasks.next()) {
                taskName = (String)dbTasks.getObject("name");
                colPos = (int)dbTasks.getObject("columnposition");
                description = (String)dbTasks.getObject("description");
                userInCharge = (String)dbTasks.getObject("userincharge");
                tagName = (String)dbTasks.getObject("tagname");
                projNameTask = (String)dbTasks.getObject("project");
                category = (String)dbTasks.getObject("category");
                task = new Task(taskName, colPos, description, new Tag(tagName, null), new User(userInCharge, null, null, null, null), projNameTask, category);
                proj.getColumn(colPos).addTask(task);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        dbMembers = DBConnector.selectMembers(proj.getName());
        proj.setMembers(new LinkedList<User>());
        try {
            while (dbMembers != null && dbMembers.next()) {
                memberName = (String)dbMembers.getObject("member");
                System.out.println(memberName);
                member = new User(memberName, null, null, null, null);
                proj.addMember(member);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return proj;
    }
}