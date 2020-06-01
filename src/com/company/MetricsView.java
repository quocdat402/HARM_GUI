package com.company;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Action;
import javax.swing.JTextPane;
import javax.swing.JList;

public class MetricsView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 24L;
	private JPanel contentPane;
	private JScrollPane scrollPane;
	private MainModel model;
	private JTable table;
	private DefaultTableModel mainModel;
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
	private JList list;
	private JButton btnConnection;

	/**
	 * Create the frame.
	 */
	public MetricsView(MainModel m) {

		this.model = m;

		nodeNames = new ArrayList<String>();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 557, 270);
		contentPane = new JPanel();
		contentPane.setBorder(null);
		setContentPane(contentPane);

		for (Node node : model.getNodes()) {

			nodeNames.add(node.getName());

		}

		nodeSize = model.getNodes().size();

		table = new JTable(nodeSize, nodeSize);
		// table = new JTable(mainModel);
//		for (int i = 0; i < table.getRowCount(); i++) {
//			table.setValueAt("A", i, 0);
//		}

		// table.setValueAt("A", 0, 2);

		JTableHeader th = table.getTableHeader();
		TableColumnModel tcm = th.getColumnModel();
		for (int i = 0; i < nodeSize; i++) {

			TableColumn tc = tcm.getColumn(i);
			tc.setHeaderValue(nodeNames.get(i));

		}

		th.repaint();

		sorter = new TableRowSorter<TableModel>(table.getModel());
		table.setRowSorter(sorter);

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

		headerTable = new JTable(tableModel);
		for (int i = 0; i < table.getRowCount(); i++) {
			headerTable.setValueAt(model.getNodes().get(i).getName(), i, 0);
		}
		headerTable.setShowGrid(false);
		headerTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		headerTable.setPreferredScrollableViewportSize(new Dimension(50, 0));
		headerTable.getColumnModel().getColumn(0).setPreferredWidth(50);
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
		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(0, 0, 547, 200);
		scrollPane.setRowHeaderView(headerTable);
		table.setPreferredScrollableViewportSize(table.getPreferredSize());
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		contentPane.setLayout(null);
		getContentPane().add(scrollPane);

		panel = new JPanel();
		panel.setBounds(0, 199, 547, 33);
		contentPane.add(panel);
		
		btnConnection = new JButton("Connection");
		btnConnection.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				for (Arc arc : model.getArcs()) {
					
					table.setValueAt(arc.getInitNode() + "->" + arc.getEndNode(), arc.getInitNode(), arc.getEndNode());

				}
			}
		});
		panel.add(btnConnection);

		btnRisk = new JButton("Risk");
		btnRisk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// table.setValueAt("A",0,0);

				for (Arc arc : model.getArcs()) {
					
					table.setValueAt(arc.getRisk(), arc.getInitNode(), arc.getEndNode());
					//table.setValueAt(arc.getInitNode() + "->" + arc.getEndNode(), arc.getInitNode(), arc.getEndNode());

				}
			}
		});
		
		
		panel.add(btnRisk);

		ArrayList<String> nodes = new ArrayList<String>();
		for (Node node : model.getNodes()) {

			nodes.add(node.getName());

		}

//		list = new JList(nodes.toArray());
//		list.setBounds(12, 5, 50, 110);
//
//
		//scrollPane.setViewportView(list);

		btnCost = new JButton("Cost");
		btnCost.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				for(Arc arc: model.getArcs()) {
					
					table.setValueAt(arc.getCost(), arc.getInitNode(), arc.getEndNode());
					
				}
			}
		});
		panel.add(btnCost);
		
		btnProb = new JButton("Probability");
		btnProb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				for(Arc arc: model.getArcs()) {
					
					table.setValueAt(arc.getProbability(), arc.getInitNode(), arc.getEndNode());
					
				}
			
			}
		});
		panel.add(btnProb);
		
		btnImpact = new JButton("Impact");
		btnImpact.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				for(Arc arc: model.getArcs()) {
					
					table.setValueAt(arc.getImpact(), arc.getInitNode(), arc.getEndNode());
					
				}
				
			}
		});
		panel.add(btnImpact);
		
		//this.pack();
		this.setLocation(150, 150);
		this.setVisible(true);
	}

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
}
