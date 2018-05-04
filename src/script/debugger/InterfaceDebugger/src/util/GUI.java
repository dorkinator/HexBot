package util;

import com.hexrealm.hexos.Environment;
import com.hexrealm.hexos.accessor.*;
import com.hexrealm.hexos.event.impl.RenderEvent;
import com.hexrealm.hexos.ui.component.DecoratedFrame;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.plaf.IconUIResource;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.util.ArrayList;

/**
 * Created by Dorkinator on 1/31/2018.
 */
public class GUI extends DecoratedFrame {
	private JPanel interfaceTreeRootPanel;
	private JScrollPane interfaceTreePanel;
	private JPanel interfaceInfoPanel;
	private JPanel interfaceInfoTextPanel;

	private JPanel buttonPanel;

	private JButton refreshButton;
	private JTextField searchBar;

	private JTree interfaceTree;
	private DefaultMutableTreeNode rootNode;

	private JLabel widgetIdLabel;
	private JLabel widgetIdLabel_1;
	private JLabel hashLabel;
	private JLabel hashLabel_1;
	private JLabel heightLabel;
	private JLabel heightLabel_1;
	private JLabel widthLabel;
	private JLabel widthLabel_1;
	private JLabel realXLabel;
	private JLabel realXLabel_1;
	private JLabel realYLabel;
	private JLabel realYLabel_1;
	private JLabel textLabel;
	private JLabel textLabel_1;
	private JLabel isHiddenLabel;
	private JLabel isHiddenLabel_1;
	private ImagePanel interfaceImagePanel;

	private WidgetAccessor widgetToDraw;




	private BufferedImage screenSnapshot = null;
	private WidgetAccessor[][] widgetAccessor;

	public static void main(String[] args) {
		new GUI();
	}

	public GUI() {
		initGUI();
		buildGUI();
		addActionListeners();
		display();
	}

	public void addActionListeners() {
		interfaceTree.addTreeSelectionListener(new TreeSelectionListener() {
			@Override
			public void valueChanged(TreeSelectionEvent e) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) interfaceTree.getLastSelectedPathComponent();
				if (node != null) {
					String widgetId = "";
					ArrayList<Integer> widgetPath = new ArrayList<>();
					for (TreeNode i : node.getPath()) {
						if (!i.toString().equals("Interfaces")) {
							widgetId += i.toString() + ",";
							widgetPath.add(Integer.parseInt(i.toString()));
						}
					}
					if(widgetId.length() > 1) {
						widgetId = widgetId.substring(0, widgetId.length() - 1);
						widgetIdLabel_1.setText(widgetId);
					}
					setWidgetInfoPanel(widgetPath);
				}
			}
		});
		refreshButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateWidgets(rootNode);
				interfaceTree.updateUI();
			}
		});
		searchBar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateWidgets(rootNode, searchBar.getText());
			}
		});
	}

	public void initGUI() {
		UIManager.put("Tree.closedIcon", new IconUIResource(new NodeIcon('+')));
		UIManager.put("Tree.openIcon", new IconUIResource(new NodeIcon('-')));
		UIManager.put("Tree.leafIcon", new IconUIResource(new NodeIcon(' ')));
		UIManager.put("Tree.collapsedIcon", new IconUIResource(new NodeIcon(' ')));
		UIManager.put("Tree.expandedIcon", new IconUIResource(new NodeIcon(' ')));

		interfaceTreeRootPanel = new JPanel();
		interfaceInfoPanel = new JPanel();
		interfaceInfoTextPanel = new JPanel();
		buttonPanel = new JPanel();

		refreshButton = new JButton("Refresh");
		searchBar = new JTextField();

		rootNode = new DefaultMutableTreeNode("Interfaces");
		interfaceTree = new JTree(rootNode);
		interfaceTree.setBorder(BorderFactory.createEmptyBorder());
		interfaceTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

		interfaceTreePanel = new JScrollPane(interfaceTree);

		widgetIdLabel = new JLabel("ID");
		widgetIdLabel_1 = new JLabel("");
		hashLabel = new JLabel("Hash");
		hashLabel_1 = new JLabel("");
		heightLabel = new JLabel("Height");
		heightLabel_1 = new JLabel("");
		widthLabel = new JLabel("Width");
		widthLabel_1 = new JLabel("");
		realXLabel = new JLabel("Real X");
		realXLabel_1 = new JLabel("");
		realYLabel = new JLabel("Real Y");
		realYLabel_1 = new JLabel("");
		textLabel = new JLabel("Text");
		textLabel_1 = new JLabel("");
		isHiddenLabel = new JLabel("isHidden");
		isHiddenLabel_1 = new JLabel("");
		interfaceImagePanel = new ImagePanel(null);


		updateWidgets(rootNode);
	}

	public void buildGUI() {
		this.setLayout(new BorderLayout());
		this.add(interfaceTreeRootPanel, BorderLayout.WEST);
		this.add(interfaceInfoPanel, BorderLayout.CENTER);

		interfaceTreeRootPanel.setLayout(new BorderLayout());
		interfaceTreeRootPanel.setPreferredSize(new Dimension(150, 0));
		interfaceTreeRootPanel.add(interfaceTreePanel, BorderLayout.CENTER);
		interfaceTreeRootPanel.add(buttonPanel, BorderLayout.SOUTH);

		interfaceInfoPanel.setLayout(new BorderLayout());
		interfaceInfoPanel.add(interfaceInfoTextPanel, BorderLayout.CENTER);
		interfaceInfoPanel.add(interfaceImagePanel, BorderLayout.SOUTH);
		interfaceInfoTextPanel.setLayout(new GridLayout(8, 2));
		interfaceInfoTextPanel.add(widgetIdLabel);
		interfaceInfoTextPanel.add(widgetIdLabel_1);
		interfaceInfoTextPanel.add(hashLabel);
		interfaceInfoTextPanel.add(hashLabel_1);
		interfaceInfoTextPanel.add(heightLabel);
		interfaceInfoTextPanel.add(heightLabel_1);
		interfaceInfoTextPanel.add(widthLabel);
		interfaceInfoTextPanel.add(widthLabel_1);
		interfaceInfoTextPanel.add(realXLabel);
		interfaceInfoTextPanel.add(realXLabel_1);
		interfaceInfoTextPanel.add(realYLabel);
		interfaceInfoTextPanel.add(realYLabel_1);
		interfaceInfoTextPanel.add(textLabel);
		interfaceInfoTextPanel.add(textLabel_1);
		interfaceInfoTextPanel.add(isHiddenLabel);
		interfaceInfoTextPanel.add(isHiddenLabel_1);


		buttonPanel.setLayout(new BorderLayout());
		buttonPanel.add(refreshButton, BorderLayout.WEST);
		buttonPanel.add(searchBar, BorderLayout.CENTER);
	}

	public void display() {
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setTitle("Interface Debugger");
		this.setSize(450, 450);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	private void updateWidgets(DefaultMutableTreeNode root) {
		updateWidgets(root, null);
	}

	private void updateWidgets(DefaultMutableTreeNode root, String search){
		root.removeAllChildren();
		screenSnapshot = null;
		setWidgetInfoPanel(null);
		ClientAccessor ca = Environment.getClient();
		widgetAccessor = Environment.getClient().getWidgetGroups();
		for(int i = 0; i < widgetAccessor.length; i++){
			if(widgetAccessor[i] != null && widgetAccessor[i].length > 0) {
				DefaultMutableTreeNode firstChild = new DefaultMutableTreeNode(i+"");
				addNodeChildern(widgetAccessor[i], firstChild, search);
				if(search == null || firstChild.getChildCount() > 0) {
					root.add(firstChild);
				}
			}
		}
	}

	private void addNodeChildern(WidgetAccessor[] children, DefaultMutableTreeNode parentNode, String search){
		for(int i = 0; i < children.length; i++){
			DefaultMutableTreeNode newChild = new DefaultMutableTreeNode(i+"");
			if(children[i].getChildren() != null && children[i].getChildren().length > 0) {
				addNodeChildern(children[i].getChildren(), newChild, search);
			}
			if(search == null || newChild.getChildCount() > 0 || children[i].getText() != null && children[i].getText().toLowerCase().contains(search)) {
				parentNode.add(newChild);
			}
		}
	}

	public void setWidgetInfoPanel(ArrayList<Integer> widgetPath) {
		if (widgetPath == null || widgetPath.size() < 1) {
			widgetIdLabel_1.setText("");
			hashLabel_1.setText("");
			heightLabel_1.setText("");
			widthLabel_1.setText("");
			isHiddenLabel_1.setText("");
			realXLabel_1.setText("");
			realYLabel_1.setText("");
			textLabel_1.setText("");
			interfaceImagePanel.setImage(null);
		} else {
			int parent = widgetPath.size() > 0 ? widgetPath.get(0) : 0;
			int child = widgetPath.size() > 1 ? widgetPath.get(1) : 0;

			WidgetAccessor widgetParent = widgetAccessor[parent][0];
			WidgetAccessor widget = widgetAccessor[parent][child];
			while (widgetPath.size() > 2){
				widget = widget.getChildren()[widgetPath.get(2)];
				widgetPath.remove(2);
			}
			hashLabel_1.setText(widget.getHash() + "");
			heightLabel_1.setText(widget.getHeight() + "");
			widthLabel_1.setText(widget.getWidth() + "");
			isHiddenLabel_1.setText(widget.isHidden() + "");
			realXLabel_1.setText(widget.getRealX() + "");
			realYLabel_1.setText(widget.getRealY() + "");
			textLabel_1.setText(widget.getText());
			widgetToDraw = widget;

			if(widgetParent.getWidth() > 0 && widgetParent.getHeight() > 0) {
				BufferedImage screenSnapshotCopy = deepCopy(screenSnapshot);
				Graphics g = screenSnapshotCopy.getGraphics();
				g.setColor(Color.RED);
				g.drawRect(widget.getRealX(), widget.getRealY(), widget.getWidth(), widget.getHeight());
				interfaceImagePanel.setImage(screenSnapshotCopy.getSubimage(widgetParent.getRealX(), widgetParent.getRealY(), widgetParent.getWidth(), widgetParent.getHeight()));
				interfaceImagePanel.updateUI();
			}
		}
	}

	public void onRenderEvent(RenderEvent e) {
		if(widgetToDraw != null) {
			e.getGraphics().setColor(Color.RED);
			e.getGraphics().drawRect(widgetToDraw.getRealX(), widgetToDraw.getRealY(), widgetToDraw.getWidth(), widgetToDraw.getHeight());
		}
		if(screenSnapshot == null){
			screenSnapshot = deepCopy(e.getBufferedImage());
		}
	}
	static BufferedImage deepCopy(BufferedImage bi) {
		ColorModel cm = bi.getColorModel();
		boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		WritableRaster raster = bi.copyData(null);
		return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}
}
