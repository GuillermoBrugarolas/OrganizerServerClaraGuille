package controller;

import model.Logics;
import model.TasksObject;
import network.ServerS;
import view.MainView;
import view.TableView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;


public class MainViewController implements ActionListener{

    private static MainView view;
    private Logics logics;
    private ServerS server;


    public MainViewController(MainView view, Logics logics, ServerS server){
        this.view = view;
        this.logics = logics;
        this.server = server;
    }
    /**
     * Metode que gestiona els events creats per els listeners de la vista.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().toString().startsWith("javax.swing.JButton")) {
            if (((JButton) e.getSource()).getText().equals("Top Ten with most pending")) {
                String allUsersData = Logics.getAllUsers();
                LinkedList<TasksObject> listTasks = Logics.getTasksInfo(allUsersData);
                TableView tableView = new TableView();
                tableView.fillTable(listTasks);
            }
        }
    }
    /**
     * metode que crea un dialeg amb un missatge concret i una tipologia.
     */
    public static void makeDialog(String message, boolean type){
        view.makeDialog(message,type);
    }
}

