package src;
import javax.swing.*;
import java.io.*;
import javax.imageio.*;

import java.awt.*;
import java.awt.Event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class UserInterface {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame go = new Gui();
/*		try{
			go.setContentPane(new JLabel(new ImageIcon(ImageIO.read(
					new File("C:/Users/birde/Desktop/EverythingJava/COMP 208(Java Programs)/FinalProject/src/drugs.jpg")))));
		}catch(IOException e){
			System.out.println("File not found");
		}*/
		go.setTitle("Drug Price Analyzer");
		go.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		go.setResizable(false);
		go.setSize(350,200);
		go.setVisible(true);

	}
}
	
class Gui extends JFrame{

	private static String[] drugs = {"Tylenol", "Aspirin", "Benylin", "Vitamin C"};
	private static String[] state = {"New York", "California", "Atlanta", "Chicago"};
	private JTextField selectedDrugAndState = new JTextField(20);
	private JComboBox drugBox = new JComboBox();
	private JComboBox stateBox = new JComboBox();
	
	public Gui(){
		
		for(int i=0; i<drugs.length; i++){
			drugBox.addItem(drugs[i]);
		}
		
		for(int i=0; i<state.length; i++){
			stateBox.addItem(state[i]);
		}
		
		
		drugBox.addActionListener(
				new ActionListener(){
					@Override 
					public void actionPerformed(ActionEvent event){
						selectedDrugAndState.setText("You selected :" + ((JComboBox)event.getSource()).getSelectedItem());
					}
				}
		);
		
		stateBox.addActionListener(
				new ActionListener(){
					@Override 
					public void actionPerformed(ActionEvent event){
						selectedDrugAndState.setText("You selected :" + ((JComboBox)event.getSource()).getSelectedItem());
					}
				}
		);
		
		this.setLayout(new FlowLayout());
		this.add(drugBox);
		this.add(stateBox);
		this.add(selectedDrugAndState);
	}
}
