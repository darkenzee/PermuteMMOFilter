package io.github.darkenzee.PermuteMMOFilter;

import static javax.swing.JFileChooser.APPROVE_OPTION;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;

import io.github.darkenzee.PermuteMMOFilter.parser.PathDetails;
import io.github.darkenzee.PermuteMMOFilter.parser.PathDetailsParser;

public class PermuteMMOFilter extends JFrame {
	private final PermuteMMOFilter self = this;

	public PermuteMMOFilter() {
		initGUI();
	}

	private void initGUI() {
		setMinimumSize(new Dimension(600, 500));
		setTitle("Permutation Filter");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setIconImage(
				new ImageIcon(getClass().getResource("/icons/Hektakun-Pokemon-479-Rotom-Normal.72.png")).getImage());
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0 };
		gridBagLayout.rowHeights = new int[] { 0 };
		gridBagLayout.columnWeights = new double[] { Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { Double.MIN_VALUE };
		getContentPane().setLayout(gridBagLayout);
		setJMenuBar(getMenuBar_1());
	}

	private static final long serialVersionUID = -2001943701794082933L;
	private JMenuBar menuBar;
	private JMenu mnFile;
	private JMenuItem mntmOpenPathFile;
	private JMenuItem mntmLoadPathFromText;

	public static void main(String[] args) {
		PermuteMMOFilter filter = new PermuteMMOFilter();
		filter.pack();
		filter.setLocationRelativeTo(null);
		filter.setVisible(true);
	}

	private JMenuBar getMenuBar_1() {
		if (menuBar == null) {
			menuBar = new JMenuBar();
			menuBar.add(getMnFile());
		}
		return menuBar;
	}

	private JMenu getMnFile() {
		if (mnFile == null) {
			mnFile = new JMenu("File");
			mnFile.setMnemonic('F');
			mnFile.add(getMntmOpenPathFile());
			mnFile.add(getMntmLoadPathFromText());
		}
		return mnFile;
	}

	private JMenuItem getMntmOpenPathFile() {
		if (mntmOpenPathFile == null) {
			mntmOpenPathFile = new JMenuItem("Open Path File");
			mntmOpenPathFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
			mntmOpenPathFile.setMnemonic('O');
			mntmOpenPathFile.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					JFileChooser chooser = new JFileChooser((String) null);
					FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
					chooser.setFileFilter(filter);
					chooser.setMultiSelectionEnabled(false);
					if (chooser.showOpenDialog(self) == APPROVE_OPTION) {
						try {
							self.load(PathDetailsParser.loadFromFile(chooser.getSelectedFile()));
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				}
			});
		}
		return mntmOpenPathFile;
	}

	private JMenuItem getMntmLoadPathFromText() {
		if (mntmLoadPathFromText == null) {
			mntmLoadPathFromText = new JMenuItem("Load Path From Text");
			mntmLoadPathFromText.setMnemonic('L');
			mntmLoadPathFromText.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_DOWN_MASK));
			mntmLoadPathFromText.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
			        final JTextArea text = new JTextArea("", 20, 40);
			        JOptionPane pane = new JOptionPane(new Object[] { "Paste path from PermuteMMO below",
			                new JScrollPane(text) }, JOptionPane.QUESTION_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
			        pane.setWantsInput(false);
			        JDialog dialog = pane.createDialog(self, "Load From Text");
			        dialog.pack();
			        dialog.setVisible(true);
			        Integer value = (Integer) pane.getValue();
			        if (value != null && value.intValue() == JOptionPane.OK_OPTION) {
			        	try {
							self.load(PathDetailsParser.loadFromString(text.getText()));
						} catch (IOException e1) {
							e1.printStackTrace();
						}
			        }
				}
			});
		}
		return mntmLoadPathFromText;
	}

	protected void load(List<PathDetails> paths) {
		System.err.println(paths.size());
	}
}
