package view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.sun.javafx.geom.Rectangle;
import controller.MainViewController;

//import controller.MainViewControllerS;


public class MainView extends JFrame {

    private static JLabel jlTitle = new JLabel("ORGANIZER SERVER");
    private static JButton jbTopTen = new JButton("Top Ten with most pending");

    public MainView() {
        getContentPane().setLayout(new BorderLayout());
        jlTitle.setHorizontalAlignment(SwingConstants.CENTER);
        jlTitle.setFont(new Font("Calibri", Font.BOLD, 20));
        jlTitle.setForeground(new Color(180, 30, 40));
        this.getContentPane().add(jlTitle, BorderLayout.NORTH);
        ImageIcon image = new ImageIcon("logo.png");
        JLabel imageLabel = new JLabel(image);
        this.getContentPane().add(imageLabel, BorderLayout.CENTER);
        this.getContentPane().add(jbTopTen, BorderLayout.SOUTH);
        setTitle("Organizer Server");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(500, 300);
        setVisible(true);
    }

    public void registerController(MainViewController actionListener){
        jbTopTen.addActionListener(actionListener);
    }

    public void makeDialog(String message, boolean type){
        if(type){
            Dialog.DialogOK(message);
        }else{
            Dialog.DialogKO(message);
        }
    }
}
