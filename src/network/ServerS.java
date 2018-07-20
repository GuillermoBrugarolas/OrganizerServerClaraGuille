package network;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import controller.MainViewController;
import model.Logics;
import model.User;


public class ServerS extends Thread {
    private boolean isOn;

    private static ServerSocket sServer;

    private static Socket sClient;

    private FileOutputStream fos;

    private FileInputStream fis;
    
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

        String message = new String();

        while(isOn){
            try{
                sClient = sServer.accept();
                ObjectOut = new ObjectOutputStream(sClient.getOutputStream());
                ObjectIn = new ObjectInputStream(sClient.getInputStream());
                Object o = ObjectIn.readObject();
                message = (String)o;

                if (message.startsWith("ADD")){
                    if(Logics.addUser(message)){
                        ObjectOut.writeObject((String)"OK");
                        ObjectOut.flush();
                    }else{
                        ObjectOut.writeObject((String)"KO");
                        ObjectOut.flush();
                    }
                }
                if (message.startsWith("LOG")){
                    if(Logics.logUser(message)){
                        ObjectOut.writeObject((String)"OK");
                        ObjectOut.flush();
                    }else{
                        ObjectOut.writeObject((String)"KO");
                        ObjectOut.flush();
                    }
                }
                if (message.startsWith("GET")){
                    User userData;
                    System.out.println("going to get user");
                    userData = Logics.getUser(message);
                    ObjectOut.writeObject(userData);
                    ObjectOut.flush();
                }
                if (message.startsWith("GETALLUSERS")){
                    String allUsersData;
                    allUsersData = Logics.getAllUsers();
                    if(!allUsersData.equals("")){
                        ObjectOut.writeObject((String)allUsersData);
                        ObjectOut.flush();
                    }else{
                        ObjectOut.writeObject((String)"KO");
                        ObjectOut.flush();
                    }
                }
                ObjectIn.close();
                ObjectOut.close();
                sClient.close();
            }catch(IOException e){
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}

