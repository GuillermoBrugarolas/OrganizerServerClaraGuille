package view;

import model.LineGraph;
import model.TasksObject;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.LinkedList;

/**
 * Created by Guillermo Brugarolas on 12/04/2018.
 */
public class TableView extends JFrame {

    private static JTable topTenTable;
    private DefaultTableModel tableModel;
    private static String[] columnNames = {"RANK", "USER NAME",
            "PENDING TASKS",
            "TASKS ASSIGNED",
            "PERCENTAGE COMPLETED"};

    public TableView() {
        this.tableModel = new DefaultTableModel(columnNames, 0);
        topTenTable = new JTable(tableModel);
        topTenTable.setRowHeight(30);
        this.add(new JScrollPane(topTenTable));
        setTitle("Organizer :: TOP TEN TABLE");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(1000, 400);
        setLocationRelativeTo(null);
        setVisible(false);
    }

    public void fillTable(LinkedList<TasksObject> list){
        for (int i = 0; i < list.size(); i++){
            list.get(i).setPosition(i+1);
            int position = list.get(i).getPosition();
            String username = list.get(i).getUsername();
            int pending = list.get(i).getPending();
            int assigned = list.get(i).getAssigned();
            double percentage = list.get(i).getPercentage();
            String percent = String.format("%.2f%%", percentage);
            Object[] data = {position, username, pending, assigned, percent};
            tableModel.addRow(data);
        }
        topTenTable.setGridColor(new Color(100, 100, 255));
        topTenTable.setBackground(new Color(250, 215, 180));
        this.setVisible(true);
    }
}
