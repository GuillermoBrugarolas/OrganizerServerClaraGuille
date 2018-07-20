package model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
/**
 * Aquesta classe ens permet llegir la informacio de configuracio d'un fitxer.json i guardarla en atributs locals.
 */
public class Configuration {
    /**
     * Guarda el port de connexio amb la base de dades.
     */
    private String portDB = new String();
    /**
     * guarda la ip de la connecio amb el client.
     */
    private String IP = new String();
    /**
     * guarda el nom de la base de dades.
     */
    private String nameDB = new String();
    /**
     * Guarda el nom de l'usuari de connexio amb la base de dades.
     */
    private String user = new String();
    /**
     * Guarda la contrassenya de conssexio amb la base de dades.
     */
    private String password = new String();
    /**
     * Guarda el port de connexio amb el client.
     */
    private int portClient;
    /**
     * Metode que llegeix el fitxer .json i escriu el seu contingut als atributs de la classe. Retorna false si no troba el fitxer.
     */
    public boolean configurate(){
        Gson gson = new GsonBuilder().create();
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader("config.json"));
            Configuration aux = gson.fromJson(br, Configuration.class);

            this.portDB = aux.portDB;
            this.IP = aux.IP;
            this.nameDB = aux.nameDB;
            this.user = aux.user;
            this.password = aux.password;
            this.portClient = aux.portClient;

        } catch (FileNotFoundException e) {
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public String getPortDB() {
        return portDB;
    }

    public String getIP() {
        return IP;
    }

    public String getNameDB() {
        return nameDB;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public int getPortClient() {
        return portClient;
    }
}

