package com.company;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.RowSorterEvent;
import javax.swing.event.RowSorterListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.ActionListener;

public class MetricsView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 24L;
	private JPanel contentPane;
	private JScrollPane scrollPane;
	private MainModel model;
	private JTable table;
	private DefaultTableModel tableModel;
	private TableRowSorter<TableModel> sorter;
	private JTable headerTable;

	private int nodeSize;
	private ArrayList<String> nodeNames;
	private JButton btnRisk;
	private JButton btnCost;
	private JButton btnProb;
	private JPanel panel;
	private JButton btnImpact;
	private JButton btnConnection;

	/**
	 * Create the metrics frame.
	 */
	public MetricsView(MainModel m) {

		this.model = m;

		nodeNames = new ArrayList<String>();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 650, 270);
		contentPane = new JPanel();
		contentPane.setBorder(null);
		setContentPane(contentPane);
		

		int iconWidth = 13; 
        int iconHeight = 13;

		ImageIcon connectionIcon = new ImageIcon(getClass().getResource("/icons/connection.png"));
		Image connectionImage = connectionIcon.getImage().getScaledInstance(iconWidth, iconHeight, Image.SCALE_SMOOTH);
        connectionIcon = new ImageIcon(connectionImage);

		ImageIcon riskIcon = new ImageIcon(getClass().getResource("/icons/risk.png"));
        Image riskImage = riskIcon.getImage().getScaledInstance(iconWidth, iconHeight, Image.SCALE_SMOOTH);
        riskIcon = new ImageIcon(riskImage);

        ImageIcon costIcon = new ImageIcon(getClass().getResource("/icons/cost.png"));
        Image costImage = costIcon.getImage().getScaledInstance(iconWidth, iconHeight, Image.SCALE_SMOOTH);
        costIcon = new ImageIcon(costImage);

        ImageIcon probabilityIcon = new ImageIcon(getClass().getResource("/icons/probability.png"));
        Image probabilityImage = probabilityIcon.getImage().getScaledInstance(iconWidth, iconHeight, Image.SCALE_SMOOTH);
        probabilityIcon = new ImageIcon(probabilityImage);

        ImageIcon impactIcon = new ImageIcon(getClass().getResource("/icons/impact.png"));
        Image impactImage = impactIcon.getImage().getScaledInstance(iconWidth, iconHeight, Image.SCALE_SMOOTH);
        impactIcon = new ImageIcon(impactImage);

		for (Node node : model.getNodes()) {
			nodeNames.add(node.getName());
		}

		nodeSize = model.getNodes().size();
		
		//Create table
		table = new JTable(nodeSize, nodeSize);
		JTableHeader th = table.getTableHeader();
		TableColumnModel tcm = th.getColumnModel();
		for (int i = 0; i < nodeSize; i++) {

			TableColumn tc = tcm.getColumn(i);
			tc.setHeaderValue(nodeNames.get(i));

		}
		th.repaint();
		
		//Sort the row by their name
		sorter = new TableRowSorter<TableModel>(table.getModel());
		table.setRowSorter(sorter);

		//Settings for table (column count, row count, column, row)
		tableModel = new DefaultTableModel() {

			private static final long serialVersionUID = 1L;

			@Override
			public int getColumnCount() {

				return 1;
			}

			@Override
			public boolean isCellEditable(int row, int col) {
				return false;
			}

			@Override
			public int getRowCount() {
				return table.getRowCount();
			}

			@Override
			public Class<?> getColumnClass(int colNum) {
				switch (colNum) {
				case 0:
					return String.class;
				default:
					return super.getColumnClass(colNum);
				}
			}
		};

		//Create sub table has titled row
		headerTable = new JTable(tableModel);
		for (int i = 0; i < table.getRowCount(); i++) {
			headerTable.setValueAt(model.getNodes().get(i).getName(), i, 0);
		}
		headerTable.setShowGrid(false);
		headerTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		headerTable.setPreferredScrollableViewportSize(new Dimension(60, 0));
		headerTable.getColumnModel().getColumn(0).setPreferredWidth(60);
		
		//Add feature which color of font and cell changed when user clicks the cell
		headerTable.getColumnModel().getColumn(0).setCellRenderer(new TableCellRenderer() {

			@Override
			public Component getTableCellRendererComponent(JTable x, Object value, boolean isSelected, boolean hasFocus,
					int row, int column) {

				boolean selected = table.getSelectionModel().isSelectedIndex(row);
				Component component = table.getTableHeader().getDefaultRenderer().getTableCellRendererComponent(table,
						value, false, false, -1, -2);
				((JLabel) component).setHorizontalAlignment(SwingConstants.CENTER);
				if (selected) {
					component.setFont(component.getFont().deriveFont(Font.BOLD));
					component.setForeground(Color.red);
				} else {
					component.setFont(component.getFont().deriveFont(Font.PLAIN));
				}
				return component;
			}
		});
		table.getRowSorter().addRowSorterListener(new RowSorterListener() {

			@Override
			public void sorterChanged(RowSorterEvent e) {
				tableModel.fireTableDataChanged();
			}
		});
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				tableModel.fireTableRowsUpdated(0, tableModel.getRowCount() - 1);
			}
		});
		
		//Add scrollpane to table
		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(0, 0, 635, 200);
		scrollPane.setRowHeaderView(headerTable);
		table.setPreferredScrollableViewportSize(table.getPreferredSize());
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		contentPane.setLayout(null);
		getContentPane().add(scrollPane);

		panel = new JPanel();
		panel.setBounds(0, 199, 635, 33);
		contentPane.add(panel);
		
		
		//Buttons at the bottom of the frame
		btnConnection = new JButton("Connection");
		btnConnection.setIcon(connectionIcon);
        btnConnection.setHorizontalTextPosition(SwingConstants.RIGHT);
        btnConnection.setVerticalTextPosition(SwingConstants.CENTER);
		//btnConnection.setPreferredSize(new Dimension(120, 30));
		btnConnection.addActionListener(e->connectionAction());
		panel.add(btnConnection);

		btnRisk = new JButton("Risk");
		btnRisk.setIcon(riskIcon);
        btnRisk.setHorizontalTextPosition(SwingConstants.RIGHT);
        btnRisk.setVerticalTextPosition(SwingConstants.CENTER);
		btnRisk.addActionListener(e->riskAction());
		panel.add(btnRisk);
		
		btnCost = new JButton("Cost");
		btnCost.setIcon(costIcon);
        btnCost.setHorizontalTextPosition(SwingConstants.RIGHT);
        btnCost.setVerticalTextPosition(SwingConstants.CENTER);
		btnCost.addActionListener(e->costAction());
		panel.add(btnCost);
		
		btnProb = new JButton("Probability");
		btnProb.setIcon(probabilityIcon);
        btnProb.setHorizontalTextPosition(SwingConstants.RIGHT);
        btnProb.setVerticalTextPosition(SwingConstants.CENTER);
		btnProb.addActionListener(e->probAction());
		panel.add(btnProb);
		
		btnImpact = new JButton("Impact");
		btnImpact.setIcon(impactIcon);
        btnImpact.setHorizontalTextPosition(SwingConstants.RIGHT);
        btnImpact.setVerticalTextPosition(SwingConstants.CENTER);
		btnImpact.addActionListener(e->impactAction());
		panel.add(btnImpact);
		
		this.setLocation(150, 150);
		this.setVisible(true);
	}
	
	//Shows connections between nodes when connection button is clicked
	public void connectionAction() {
		
		for (Arc arc : model.getArcs()) {
			
			table.setValueAt(arc.getInitNode() + "->" + arc.getEndNode(), arc.getInitNode(), arc.getEndNode());

		}
	}
	
	//Shows value of Risks when Risk button is clicked
	public void riskAction() {
		for (Arc arc : model.getArcs()) {
			table.setValueAt(String.format("%.2f", arc.getRisk()), arc.getInitNode(), arc.getEndNode());	
		}
	}
	
	//Shows value of Costs when Cost button is clicked
	public void costAction() {
		for(Arc arc: model.getArcs()) {
			table.setValueAt(String.format("%.2f", arc.getCost()), arc.getInitNode(), arc.getEndNode());		
		}
	}

	//Shows value of Probability when Probability button is clicked
	public void probAction() {
		for(Arc arc: model.getArcs()) {
			table.setValueAt(String.format("%.2f", arc.getProbability()), arc.getInitNode(), arc.getEndNode());	
		}
	}
	
	//Shows value of Impact when impact button is clicked
	public void impactAction() {
		for(Arc arc: model.getArcs()) {	
			table.setValueAt(String.format("%.2f", arc.getImpact()), arc.getInitNode(), arc.getEndNode());	
		}	
	}

	/*
	 * Getters and Setters
	 */
	public JButton getBtnRisk() {
		return btnRisk;
	}

	public void setBtnRisk(JButton btnRisk) {
		this.btnRisk = btnRisk;
	}

	public JButton getBtnCost() {
		return btnCost;
	}

	public void setBtnCost(JButton btnCost) {
		this.btnCost = btnCost;
	}

	public JButton getBtnProb() {
		return btnProb;
	}

	public void setBtnProb(JButton btnProb) {
		this.btnProb = btnProb;
	}

	public JButton getBtnImpact() {
		return btnImpact;
	}

	public void setBtnImpact(JButton btnImpact) {
		this.btnImpact = btnImpact;
	}

	public JTable getTable() {
		return table;
	}

	public void setTable(JTable table) {
		this.table = table;
	}

	public JTable getHeaderTable() {
		return headerTable;
	}

	public void setHeaderTable(JTable headerTable) {
		this.headerTable = headerTable;
	}
}
