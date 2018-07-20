package model;

import javax.swing.SwingUtilities;

import controller.MainViewController;
import network.ServerS;
import view.MainView;
//import network.*;

public class MainServer {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable(){

            @Override
            public void run() {

                Configuration config = new Configuration();

                if(config.configurate()){

                    MainView serverMainView = new MainView();
                    Logics logics = new Logics();
                    ServerS server = new ServerS(config.getPortClient());
                    MainViewController controller = new MainViewController(serverMainView, logics, server);

                    serverMainView.registerController(controller);
                    server.registerController(controller);
                    logics.registerController(controller);
                    server.startServer();
                    DBConnector conn = new DBConnector(config.getUser(), config.getPassword(), config.getNameDB(), Integer.parseInt(config.getPortDB()), config.getIP());
                    conn.connect();
                }
            }
        });
    }
}
