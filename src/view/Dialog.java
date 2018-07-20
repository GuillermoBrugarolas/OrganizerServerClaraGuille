package view;

import javax.swing.*;

public class Dialog extends JDialog{

	public static void DialogOK(String message){
		//JOptionPane.showMessageDialog(null,message, "Information message", JOptionPane.INFORMATION_MESSAGE);
		JOptionPane pane = new JOptionPane(message, JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION);
        JDialog dialog = pane.createDialog(new JFrame(), "Has Perdut!");
        dialog.setModalityType(ModalityType.MODELESS);
        dialog.setVisible(true);
	}

	public static void DialogKO(String message){
		JOptionPane.showMessageDialog(null,message, "An Error Has Ocurred", JOptionPane.ERROR_MESSAGE);
	}
}
