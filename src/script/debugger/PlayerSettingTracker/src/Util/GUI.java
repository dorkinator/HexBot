package Util;

import com.hexrealm.hexos.Environment;
import com.hexrealm.hexos.api.Varps;
import com.hexrealm.hexos.event.ScriptEventDispatcher;
import com.hexrealm.hexos.event.impl.VarpChangedEvent;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Dorkinator on 2/1/2018.
 */
public class GUI extends JFrame {
	private JScrollPane settingListPanel;
	private JList settingListList;
	private DefaultListModel<Integer> settingListModel;


	private JScrollPane settingListenerPane;
	private DefaultTableModel settingChangeTableModel;
	private JTable settingChangeTable;

	private JMenuBar menuBar;
	private JMenu settingsMenu;
	private JMenu settingValue;
	private JCheckBoxMenuItem requireValueChangedCheckBox;
	private JMenuItem clearSelectionMenuItem;
	private JMenuItem searchMenuItem;

	public GUI(){
		initGUI();
		buildGUI();
		addActionListeners();
		display();
		ScriptEventDispatcher.register(VarpChangedEvent.class, this::varpchanged);
	}


	int[] varps;

	private void varpchanged(VarpChangedEvent e) {
		int newval = Varps.getVarp(e.getVarpIndex());
		if(varps[e.getVarpIndex()] != newval || !requireValueChangedCheckBox.isSelected()) {
			if(settingListList.isSelectedIndex(e.getVarpIndex()) || settingListList.getSelectedIndices().length == 0) {
				if(settingListList.getSelectedIndex() == e.getVarpIndex()){
					settingValue.setText(e.getVarpIndex()+": 0b"+Integer.toBinaryString(newval)+"("+newval+")");
				}
				settingChangeTableModel.addRow(new Object[]{e.getVarpIndex(), "0b" + Integer.toBinaryString(newval), newval});
				settingChangeTable.setAutoscrolls(true);
			}
		}
		varps[e.getVarpIndex()] = newval;
	}

	private void initGUI(){
		settingListModel = new DefaultListModel();
		settingListList = new JList();
		settingListPanel = new JScrollPane(settingListList);
		settingChangeTableModel = new DefaultTableModel(new Object[]{"Varp", "Bin", "Dec"}, 0);
		settingChangeTable = new JTable(settingChangeTableModel);
		settingListenerPane = new JScrollPane(settingChangeTable);
		requireValueChangedCheckBox = new JCheckBoxMenuItem("Require varp change", null, true);

		menuBar = new JMenuBar();
		settingsMenu = new JMenu("Settings");
		settingValue = new JMenu("");
		clearSelectionMenuItem = new JMenuItem("Clear selection");
		searchMenuItem = new JMenuItem("Search");


	}

	private void buildGUI(){
		int[] tmp = Environment.getClient().getVarps();
		varps = new int[tmp.length];
		for (int i = 0; i < tmp.length; i++) {
			settingListModel.addElement(i);
			varps[i] = tmp[i];
		}

		settingListList.setLayoutOrientation(JList.VERTICAL);
		settingListList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		settingListPanel.setPreferredSize(new Dimension(100,100));

		menuBar.add(settingsMenu);
		menuBar.add(settingValue);
		settingsMenu.add(requireValueChangedCheckBox);
		settingsMenu.add(clearSelectionMenuItem);
		settingsMenu.add(searchMenuItem);

		this.setLayout(new BorderLayout());
		this.add(settingListPanel, BorderLayout.WEST);
		this.add(settingListenerPane, BorderLayout.CENTER);
		this.setJMenuBar(menuBar);

		settingListList.setModel(settingListModel);
		settingListList.setVisibleRowCount(-1);

		settingValue.setEnabled(false);
	}

	private void addActionListeners(){
		clearSelectionMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				settingListList.clearSelection();
			}
		});
		searchMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String query = JOptionPane.showInputDialog("Search Query: ");
				settingListList.clearSelection();
				for (int i = 0; i < varps.length; i++) {
					if((i+"").contains(query) || (varps[i]+"").contains(query) || Integer.toBinaryString(varps[i]).contains(query)){
						settingListList.getSelectionModel().addSelectionInterval(i,i);
					}
				}
			}
		});
		settingListList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(settingListList.getSelectedIndices().length > 0) {
					int selectedIndex = settingListList.getSelectedIndex();
					int newval = varps[selectedIndex];
					settingValue.setText(selectedIndex+ ": 0b" + Integer.toBinaryString(newval) + "(" + newval + ")");
				}
			}
		});
	}

	public void display() {
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setTitle("Varp Debugger");
		this.setSize(450, 450);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
}
