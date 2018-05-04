package util;

import com.hexrealm.hexos.api.Perspective;
import com.hexrealm.hexos.api.Players;
import com.hexrealm.hexos.api.model.WorldTile;
import com.hexrealm.hexos.event.ScriptEventDispatcher;
import com.hexrealm.hexos.event.impl.RenderEvent;
import com.hexrealm.hexos.ui.component.DecoratedFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;

/**
 * Created by Dorkinator on 2/18/2018.
 */
public class GUI extends DecoratedFrame{
	private JScrollPane pointListPanel;
	private DefaultListModel<WorldTile> pointListModel;
	private JList pointListList;
	private JPanel buttonPanel;
	private JButton addPoint;
	private JButton removePoint;
	private JButton copyArea;


	public GUI(){
		ScriptEventDispatcher.register(RenderEvent.class, this::onRepaint);
		initGUI();
		buildGUI();
		addActionListeners();
		display();
	}

	private void onRepaint(RenderEvent e) {
		Graphics g = e.getGraphics();
		if(pointListModel.size() > 0) {
			WorldTile last = pointListModel.lastElement();
			for (int i = 0; i < pointListModel.getSize(); i++) {

				drawTile(g, pointListModel.get(i), last, pointListList.isSelectedIndex(i));
				last = pointListModel.get(i);
			}
		}
	}

	private static int size = 7;
	private void drawTile(Graphics g, WorldTile wt, WorldTile last, boolean selected) {
		Point p = Perspective.worldToViewort(wt.getRegionX()*128+64, wt.getRegionY()*128+64, 70);
		int offset = (size/2) - 1;
		if(selected){
			g.setColor(Color.RED);
		}
		g.fillOval(p.x - offset, p.y - offset, size, size);
		g.setColor(Color.WHITE);
		Point p2 = Perspective.worldToViewort(wt.getRegionX() * 128 + 64, wt.getRegionY() * 128 + 64, 70);
		g.drawLine(p.x + offset, p.y + offset, p2.x + offset, p2.y + offset);

	}


	public void initGUI(){
		pointListModel = new DefaultListModel();
		pointListList = new JList(pointListModel);
		pointListPanel = new JScrollPane(pointListList);
		buttonPanel = new JPanel();
		addPoint = new JButton("Add");
		removePoint  = new JButton("Del");
		copyArea = new JButton("Copy");
	}

	public void buildGUI(){
		pointListList.setLayoutOrientation(JList.VERTICAL);
		pointListList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		pointListPanel.setPreferredSize(new Dimension(100,100));

		buttonPanel.setLayout(new GridLayout(1,3));
		buttonPanel.add(addPoint);
		buttonPanel.add(copyArea);
		buttonPanel.add(removePoint);

		this.setLayout(new BorderLayout());
		this.add(pointListPanel, BorderLayout.CENTER);
		this.add(buttonPanel, BorderLayout.SOUTH);
	}

	public void addActionListeners(){
		addPoint.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!pointListList.isSelectionEmpty()){
					pointListModel.add(pointListList.getSelectedIndex(), Players.local().getWorldTile());
				}else{
					pointListModel.addElement(Players.local().getWorldTile());
				}
				pointListList.clearSelection();
			}
		});
		removePoint.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int index = pointListList.getSelectedIndex();
				for(int i: pointListList.getSelectedIndices()){
					pointListModel.remove(i);
				}
				if(index > 0){
					pointListList.setSelectedIndex(index - 1);
				}
			}
		});
		copyArea.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String xArr = "";
				String yArr = "";
				for(WorldTile i: Collections.list(pointListModel.elements())){
					xArr += i.getX()+", ";
					yArr += i.getY()+", ";
				}
				xArr = xArr.replaceAll(", $", "");
				yArr = yArr.replaceAll(", $", "");
				String output = "new Polygon(new int[]{"+xArr+"}, new int[]{"+yArr+"}, "+pointListModel.size()+");";
				System.out.println(output);
				Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(output), null);
			}
		});
	}

	public void display() {
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setTitle("Area Builder");
		this.setSize(450, 450);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

}
