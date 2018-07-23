package network;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import controller.MainViewController;
import model.Logics;
import model.Project;
import model.Task;
import model.User;


public class ServerS extends Thread {
    private boolean isOn;

    private static ServerSocket sServer;

    private static Socket sClient;

    private ObjectOutputStream ObjectOut;

    private ObjectInputStream ObjectIn;

    private MainViewController controller;

    public ServerS(int portClient){
        try {
            sServer = new ServerSocket(portClient);
            isOn = false;
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void registerController(MainViewController controller){
        this.controller = controller;
    }

    public void startServer(){
        isOn = true;
        super.start();
        System.out.println("Obrint servidor...");
    }

    public void stopServer(){
        isOn = false;
    }

    public void run(){

        String message;
        Project project;
        Task task;

        while(isOn){
            try{
                sClient = sServer.accept();
                ObjectOut = new ObjectOutputStream(sClient.getOutputStream());
                ObjectIn = new ObjectInputStream(sClient.getInputStream());
                Object o = ObjectIn.readObject();
                if (o instanceof String) {
                    message = (String) o;
                    if (message.startsWith("ADD")) {
                        if (Logics.addUser(message)) {
                            ObjectOut.writeObject((String)"OK");
                        } else {
                            ObjectOut.writeObject((String)"KO");
                        }
                    } else if (message.startsWith("LOG")) {
                        if (Logics.logUser(message)) {
                            ObjectOut.writeObject((String)"OK");
                        } else {
                            ObjectOut.writeObject((String)"KO");
                        }
                    } else if (message.startsWith("GET")) {
                        User userData;
                        userData = Logics.getUser(message);
                        ObjectOut.writeObject((User) userData);
                    } else if (message.startsWith("GEP")) {
                        Project projectData;
                        projectData = Logics.getProject(message);
                        ObjectOut.writeObject((Project)projectData);
                    } else if (message.startsWith("GEU")) {
                        String allUsersData;
                        allUsersData = Logics.getAllUsers();
                        if (!allUsersData.equals("")) {
                            ObjectOut.writeObject((String)allUsersData);
                        } else {
                            ObjectOut.writeObject((String)"KO");
                        }
                    } else if (message.startsWith("DEL")) {
                        if (Logics.deleteProject(message)) {
                            ObjectOut.writeObject((String)"OK");
                        } else {
                            ObjectOut.writeObject((String)"KO");
                        }
                    } else if (message.startsWith("JIN")) {
                        if (Logics.joinProject(message)) {
                            ObjectOut.writeObject((String)"OK");
                        } else {
                            ObjectOut.writeObject((String)"KO");
                        }
                    } else {}
                } else if (o instanceof Project){
                    project = (Project)o;
                    if (Logics.addNewProject(project)){
                        ObjectOut.writeObject((String)"OK");
                    } else {
                        ObjectOut.writeObject((String)"KO");
                    }
                } else if (o instanceof Task) {
                    task = (Task)o;
                    if (Logics.addNewTask(task)){
                        ObjectOut.writeObject((String)"OK");
                    } else {
                        ObjectOut.writeObject((String)"KO");
                    }
                }
                ObjectOut.close();
                ObjectIn.close();
                sClient.close();
            }catch(IOException e){
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}

