package io.github.darkenzee.PermuteMMOFilter;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import io.github.darkenzee.PermuteMMOFilter.parser.PathDetails;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;

public class SinglePathViewer extends JDialog {
	private static final long serialVersionUID = 3615966264336957411L;

	private final PathDetails details;
	private boolean showingChildren = false;
	
	private JScrollPane scrollPane;
	private JButton btnShowAdditionalChains;
	private JTextArea textArea;

	public SinglePathViewer(PathDetails details) {
		this.details = details;
		initGUI();
	}

	private void initGUI() {
		setMinimumSize(new Dimension(500, 500));
		setTitle("Path View");
		setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
		getContentPane().setLayout(gridBagLayout);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 2;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		getContentPane().add(getScrollPane(), gbc_scrollPane);
		GridBagConstraints gbc_btnShowAdditionalChains = new GridBagConstraints();
		gbc_btnShowAdditionalChains.gridx = 1;
		gbc_btnShowAdditionalChains.gridy = 1;
		getContentPane().add(getBtnShowAdditionalChains(), gbc_btnShowAdditionalChains);
	}

	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setViewportView(getTextArea());
		}
		return scrollPane;
	}

	private JButton getBtnShowAdditionalChains() {
		if (btnShowAdditionalChains == null) {
			btnShowAdditionalChains = new JButton("Show Additional Chain Children");
			btnShowAdditionalChains.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					showingChildren = !showingChildren;
					if (showingChildren) {
						getBtnShowAdditionalChains().setName("Hide Additional Chain Children");
					} else {
						getBtnShowAdditionalChains().setName("Show Additional Chain Children");
					}
					populateTextArea();
				}
			});
			btnShowAdditionalChains.setEnabled(details.getChainChildren().size() > 0);
		}
		return btnShowAdditionalChains;
	}

	private JTextArea getTextArea() {
		if (textArea == null) {
			textArea = new JTextArea();
			textArea.setEditable(false);
			populateTextArea();
		}
		return textArea;
	}
	
	private void populateTextArea() {
		String path = details.getOriginalText();
		PathDetails current = details;
		while ((current = current.getChainParent()) != null) {
			path = current.getOriginalText() + "\n" + path;
		}
		if (showingChildren && details.getChainChildren().size() > 0) {
			path += getChildrenPaths(details.getChainChildren());
		}
		getTextArea().setText(path);
	}
	
	private String getChildrenPaths(List<PathDetails> children) {
		String paths = "";
		for (PathDetails child : children) {
			paths = paths + "\n" + child.getOriginalText();
			if (child.getChainChildren().size() > 0) {
				paths = paths + "\n" + getChildrenPaths(child.getChainChildren());
			}
		}
		return paths;
	}
}
