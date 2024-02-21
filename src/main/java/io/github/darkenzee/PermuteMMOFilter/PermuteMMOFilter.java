package io.github.darkenzee.PermuteMMOFilter;

import static javax.swing.JFileChooser.APPROVE_OPTION;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.prefs.Preferences;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.border.BevelBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import io.github.darkenzee.PermuteMMOFilter.parser.PathDetails;
import io.github.darkenzee.PermuteMMOFilter.parser.PathDetailsParser;
import io.github.darkenzee.PermuteMMOFilter.tables.AutoWidthSortableJTable;
import io.github.darkenzee.PermuteMMOFilter.types.AnyYesNo;
import io.github.darkenzee.PermuteMMOFilter.types.PokemonGender;
import io.github.darkenzee.PermuteMMOFilter.types.PokemonNature;
import io.github.darkenzee.PermuteMMOFilter.types.ShinyType;

public class PermuteMMOFilter extends JFrame {
	private static final long serialVersionUID = -2001943701794082933L;
	private final PermuteMMOFilter self = this;

	private JMenuBar menuBar;
	private JMenu mnFile;
	private JMenuItem mntmOpenPathFile;
	private JMenuItem mntmLoadPathFromText;

	private JLabel lblSpecies;
	private JLabel lblGender;
	private JLabel lblNature;
	private JLabel lblShinyType;
	private JLabel lblStatus;
	private JLabel lblAlpha;
	private JLabel lblBonus;
	private JLabel lblAggresive;
	private JLabel lblMultiScare;
	private JLabel lblSingleAdvances;
	private JLabel lblMultipleResults;
	private JLabel lblChain;
	private JLabel lblSkittish;

	private JComboBox<String> cbSpecies;
	private JComboBox<ShinyType> cbShinyType;
	private JComboBox<PokemonGender> cbGender;
	private JComboBox<PokemonNature> cbNature;

	private JComboBox<AnyYesNo> cbAlpha;
	private JComboBox<AnyYesNo> cbBonus;
	private JComboBox<AnyYesNo> cbChain;
	private JComboBox<AnyYesNo> cbMultiple;
	private JComboBox<AnyYesNo> cbSkittish;
	private JComboBox<AnyYesNo> cbMultiScare;
	private JComboBox<AnyYesNo> cbSkittishAggressive;
	private JComboBox<AnyYesNo> cbSingleAdvances;

	private JScrollPane scrollPanePathDetails;
	private JPanel panelStatus;
	private AutoWidthSortableJTable<PathDetails> tablePathDetails;

	public PermuteMMOFilter() {
		initGUI();
	}

	private void initGUI() {
		setPreferredSize(new Dimension(750, 550));
		setMinimumSize(new Dimension(750, 550));
		setTitle("Permutation Filter");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setIconImage(
				new ImageIcon(getClass().getResource("/icons/Hektakun-Pokemon-479-Rotom-Normal.72.png")).getImage());
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE };
		getContentPane().setLayout(gridBagLayout);
		setJMenuBar(getMenuBar_1());
		GridBagConstraints gbc_lblSpecies = new GridBagConstraints();
		gbc_lblSpecies.insets = new Insets(0, 5, 5, 5);
		gbc_lblSpecies.anchor = GridBagConstraints.EAST;
		gbc_lblSpecies.gridx = 0;
		gbc_lblSpecies.gridy = 0;
		getContentPane().add(getLblSpecies(), gbc_lblSpecies);
		GridBagConstraints gbc_cbSpecies = new GridBagConstraints();
		gbc_cbSpecies.insets = new Insets(0, 0, 5, 5);
		gbc_cbSpecies.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbSpecies.gridx = 1;
		gbc_cbSpecies.gridy = 0;
		getContentPane().add(getCbSpecies(), gbc_cbSpecies);
		GridBagConstraints gbc_lblChain = new GridBagConstraints();
		gbc_lblChain.anchor = GridBagConstraints.EAST;
		gbc_lblChain.insets = new Insets(0, 0, 5, 5);
		gbc_lblChain.gridx = 2;
		gbc_lblChain.gridy = 0;
		getContentPane().add(getLblChain(), gbc_lblChain);
		GridBagConstraints gbc_cbChain = new GridBagConstraints();
		gbc_cbChain.insets = new Insets(0, 0, 5, 5);
		gbc_cbChain.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbChain.gridx = 3;
		gbc_cbChain.gridy = 0;
		getContentPane().add(getCbChain(), gbc_cbChain);
		GridBagConstraints gbc_lblSkittish = new GridBagConstraints();
		gbc_lblSkittish.anchor = GridBagConstraints.EAST;
		gbc_lblSkittish.insets = new Insets(0, 0, 5, 5);
		gbc_lblSkittish.gridx = 4;
		gbc_lblSkittish.gridy = 0;
		getContentPane().add(getLblSkittish(), gbc_lblSkittish);
		GridBagConstraints gbc_cbSkittish = new GridBagConstraints();
		gbc_cbSkittish.insets = new Insets(0, 0, 5, 5);
		gbc_cbSkittish.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbSkittish.gridx = 5;
		gbc_cbSkittish.gridy = 0;
		getContentPane().add(getCbSkittish(), gbc_cbSkittish);
		GridBagConstraints gbc_lblShinyType = new GridBagConstraints();
		gbc_lblShinyType.anchor = GridBagConstraints.EAST;
		gbc_lblShinyType.insets = new Insets(0, 5, 5, 5);
		gbc_lblShinyType.gridx = 0;
		gbc_lblShinyType.gridy = 1;
		getContentPane().add(getLblShinyType(), gbc_lblShinyType);
		GridBagConstraints gbc_cbShinyType = new GridBagConstraints();
		gbc_cbShinyType.insets = new Insets(0, 0, 5, 5);
		gbc_cbShinyType.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbShinyType.gridx = 1;
		gbc_cbShinyType.gridy = 1;
		getContentPane().add(getCbShinyType(), gbc_cbShinyType);
		GridBagConstraints gbc_lblMultipleResults = new GridBagConstraints();
		gbc_lblMultipleResults.anchor = GridBagConstraints.EAST;
		gbc_lblMultipleResults.insets = new Insets(0, 5, 5, 5);
		gbc_lblMultipleResults.gridx = 2;
		gbc_lblMultipleResults.gridy = 1;
		getContentPane().add(getLblMultipleResults(), gbc_lblMultipleResults);
		GridBagConstraints gbc_cbMultiple = new GridBagConstraints();
		gbc_cbMultiple.insets = new Insets(0, 0, 5, 5);
		gbc_cbMultiple.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbMultiple.gridx = 3;
		gbc_cbMultiple.gridy = 1;
		getContentPane().add(getCbMultiple(), gbc_cbMultiple);
		GridBagConstraints gbc_lblMultiScare = new GridBagConstraints();
		gbc_lblMultiScare.anchor = GridBagConstraints.EAST;
		gbc_lblMultiScare.insets = new Insets(0, 0, 5, 5);
		gbc_lblMultiScare.gridx = 4;
		gbc_lblMultiScare.gridy = 1;
		getContentPane().add(getLblMultiScare(), gbc_lblMultiScare);
		GridBagConstraints gbc_cbMultiScare = new GridBagConstraints();
		gbc_cbMultiScare.insets = new Insets(0, 0, 5, 5);
		gbc_cbMultiScare.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbMultiScare.gridx = 5;
		gbc_cbMultiScare.gridy = 1;
		getContentPane().add(getCbMultiScare(), gbc_cbMultiScare);
		GridBagConstraints gbc_lblGender = new GridBagConstraints();
		gbc_lblGender.anchor = GridBagConstraints.EAST;
		gbc_lblGender.insets = new Insets(0, 0, 5, 5);
		gbc_lblGender.gridx = 0;
		gbc_lblGender.gridy = 2;
		getContentPane().add(getLblGender(), gbc_lblGender);
		GridBagConstraints gbc_cbGender = new GridBagConstraints();
		gbc_cbGender.insets = new Insets(0, 0, 5, 5);
		gbc_cbGender.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbGender.gridx = 1;
		gbc_cbGender.gridy = 2;
		getContentPane().add(getCbGender(), gbc_cbGender);
		GridBagConstraints gbc_lblAlpha = new GridBagConstraints();
		gbc_lblAlpha.anchor = GridBagConstraints.EAST;
		gbc_lblAlpha.insets = new Insets(0, 5, 5, 5);
		gbc_lblAlpha.gridx = 2;
		gbc_lblAlpha.gridy = 2;
		getContentPane().add(getLblAlpha(), gbc_lblAlpha);
		GridBagConstraints gbc_cbAlpha = new GridBagConstraints();
		gbc_cbAlpha.insets = new Insets(0, 0, 5, 5);
		gbc_cbAlpha.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbAlpha.gridx = 3;
		gbc_cbAlpha.gridy = 2;
		getContentPane().add(getCbAlpha(), gbc_cbAlpha);
		GridBagConstraints gbc_lblAggresive = new GridBagConstraints();
		gbc_lblAggresive.anchor = GridBagConstraints.EAST;
		gbc_lblAggresive.insets = new Insets(0, 5, 5, 5);
		gbc_lblAggresive.gridx = 4;
		gbc_lblAggresive.gridy = 2;
		getContentPane().add(getLblAggresive(), gbc_lblAggresive);
		GridBagConstraints gbc_cbSkittishAggressive = new GridBagConstraints();
		gbc_cbSkittishAggressive.insets = new Insets(0, 0, 5, 5);
		gbc_cbSkittishAggressive.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbSkittishAggressive.gridx = 5;
		gbc_cbSkittishAggressive.gridy = 2;
		getContentPane().add(getCbSkittishAggressive(), gbc_cbSkittishAggressive);
		GridBagConstraints gbc_lblNature = new GridBagConstraints();
		gbc_lblNature.anchor = GridBagConstraints.EAST;
		gbc_lblNature.insets = new Insets(0, 0, 5, 5);
		gbc_lblNature.gridx = 0;
		gbc_lblNature.gridy = 3;
		getContentPane().add(getLblNature(), gbc_lblNature);
		GridBagConstraints gbc_cbNature = new GridBagConstraints();
		gbc_cbNature.insets = new Insets(0, 0, 5, 5);
		gbc_cbNature.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbNature.gridx = 1;
		gbc_cbNature.gridy = 3;
		getContentPane().add(getCbNature(), gbc_cbNature);
		GridBagConstraints gbc_lblBonus = new GridBagConstraints();
		gbc_lblBonus.anchor = GridBagConstraints.EAST;
		gbc_lblBonus.insets = new Insets(0, 5, 5, 5);
		gbc_lblBonus.gridx = 2;
		gbc_lblBonus.gridy = 3;
		getContentPane().add(getLblBonus(), gbc_lblBonus);
		GridBagConstraints gbc_cbBonus = new GridBagConstraints();
		gbc_cbBonus.insets = new Insets(0, 0, 5, 5);
		gbc_cbBonus.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbBonus.gridx = 3;
		gbc_cbBonus.gridy = 3;
		getContentPane().add(getCbBonus(), gbc_cbBonus);
		GridBagConstraints gbc_lblSingleAdvances = new GridBagConstraints();
		gbc_lblSingleAdvances.anchor = GridBagConstraints.EAST;
		gbc_lblSingleAdvances.insets = new Insets(0, 0, 5, 5);
		gbc_lblSingleAdvances.gridx = 4;
		gbc_lblSingleAdvances.gridy = 3;
		getContentPane().add(getLblSingleAdvances(), gbc_lblSingleAdvances);
		GridBagConstraints gbc_cbSingleAdvances = new GridBagConstraints();
		gbc_cbSingleAdvances.insets = new Insets(0, 0, 5, 5);
		gbc_cbSingleAdvances.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbSingleAdvances.gridx = 5;
		gbc_cbSingleAdvances.gridy = 3;
		getContentPane().add(getCbSingleAdvances(), gbc_cbSingleAdvances);
		GridBagConstraints gbc_scrollPanePathDetails = new GridBagConstraints();
		gbc_scrollPanePathDetails.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPanePathDetails.gridwidth = 7;
		gbc_scrollPanePathDetails.fill = GridBagConstraints.BOTH;
		gbc_scrollPanePathDetails.gridx = 0;
		gbc_scrollPanePathDetails.gridy = 4;
		getContentPane().add(getScrollPanePathDetails(), gbc_scrollPanePathDetails);
		GridBagConstraints gbc_panelStatus = new GridBagConstraints();
		gbc_panelStatus.gridwidth = 7;
		gbc_panelStatus.fill = GridBagConstraints.BOTH;
		gbc_panelStatus.gridx = 0;
		gbc_panelStatus.gridy = 5;
		getContentPane().add(getPanelStatus(), gbc_panelStatus);
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
					Preferences settings = Preferences.userRoot().node("io/github/darenzee/PermuteMMOFilter/settings");
					String lastDirectory = settings.get("lastFileChooserDir", "");
					JFileChooser chooser = new JFileChooser((String) null);
					FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
					chooser.setFileFilter(filter);
					chooser.setMultiSelectionEnabled(false);
					if (lastDirectory.length() > 0) {
						chooser.setCurrentDirectory(new File(lastDirectory));
					}
					if (chooser.showOpenDialog(self) == APPROVE_OPTION) {
						try {
							self.load(PathDetailsParser.loadFromFile(chooser.getSelectedFile()));
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
					try {
						settings.put("lastFileChooserDir", chooser.getCurrentDirectory().getCanonicalPath());
					} catch (IOException e1) {
						e1.printStackTrace();
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
					JOptionPane pane = new JOptionPane(
							new Object[] { "Paste path from PermuteMMO below", new JScrollPane(text) },
							JOptionPane.QUESTION_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
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
		for (PathDetails path : paths) {
			if (path.getChainParent() != null && path.getChainChildren().size() > 0) {
				SinglePathViewer viewer = new SinglePathViewer(path);
				viewer.setLocationRelativeTo(self);
				viewer.setVisible(true);
				break;
			}
		}
	}

	private JComboBox<String> getCbSpecies() {
		if (cbSpecies == null) {
			cbSpecies = new JComboBox<>();
			cbSpecies.setPreferredSize(new Dimension(120, 22));
			cbSpecies.setMinimumSize(new Dimension(120, 22));
		}
		return cbSpecies;
	}

	private JLabel getLblSpecies() {
		if (lblSpecies == null) {
			lblSpecies = new JLabel("Species");
		}
		return lblSpecies;
	}

	private JLabel getLblGender() {
		if (lblGender == null) {
			lblGender = new JLabel("Gender");
		}
		return lblGender;
	}

	private JComboBox<PokemonGender> getCbGender() {
		if (cbGender == null) {
			cbGender = new JComboBox<>(PokemonGender.values());
		}
		return cbGender;
	}

	private JLabel getLblNature() {
		if (lblNature == null) {
			lblNature = new JLabel("Nature");
		}
		return lblNature;
	}

	private JComboBox<PokemonNature> getCbNature() {
		if (cbNature == null) {
			cbNature = new JComboBox<>(PokemonNature.values());
		}
		return cbNature;
	}

	private JLabel getLblShinyType() {
		if (lblShinyType == null) {
			lblShinyType = new JLabel("Shiny Type");
		}
		return lblShinyType;
	}

	private JComboBox<ShinyType> getCbShinyType() {
		if (cbShinyType == null) {
			cbShinyType = new JComboBox<>(ShinyType.values());
		}
		return cbShinyType;
	}

	private JScrollPane getScrollPanePathDetails() {
		if (scrollPanePathDetails == null) {
			scrollPanePathDetails = new JScrollPane();
			scrollPanePathDetails.setViewportView(getTablePathDetails());
		}
		return scrollPanePathDetails;
	}

	private JPanel getPanelStatus() {
		if (panelStatus == null) {
			panelStatus = new JPanel();
			panelStatus.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
			panelStatus.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
			panelStatus.add(getLblStatus());
		}
		return panelStatus;
	}

	private JLabel getLblStatus() {
		if (lblStatus == null) {
			lblStatus = new JLabel("0 of 0");
		}
		return lblStatus;
	}

	private JLabel getLblAlpha() {
		if (lblAlpha == null) {
			lblAlpha = new JLabel("Is Alpha");
		}
		return lblAlpha;
	}

	private JComboBox<AnyYesNo> getCbAlpha() {
		if (cbAlpha == null) {
			cbAlpha = new JComboBox<>(AnyYesNo.values());
		}
		return cbAlpha;
	}

	private JLabel getLblBonus() {
		if (lblBonus == null) {
			lblBonus = new JLabel("Is Bonus Round");
		}
		return lblBonus;
	}

	private JComboBox<AnyYesNo> getCbBonus() {
		if (cbBonus == null) {
			cbBonus = new JComboBox<>(AnyYesNo.values());
		}
		return cbBonus;
	}

	private JLabel getLblAggresive() {
		if (lblAggresive == null) {
			lblAggresive = new JLabel("Is Skittish Aggressive");
		}
		return lblAggresive;
	}

	private JLabel getLblMultiScare() {
		if (lblMultiScare == null) {
			lblMultiScare = new JLabel("Is Multi-Scare");
		}
		return lblMultiScare;
	}

	private JLabel getLblSingleAdvances() {
		if (lblSingleAdvances == null) {
			lblSingleAdvances = new JLabel("Is Single Advances");
		}
		return lblSingleAdvances;
	}

	private JLabel getLblMultipleResults() {
		if (lblMultipleResults == null) {
			lblMultipleResults = new JLabel("Has Multiple Results");
		}
		return lblMultipleResults;
	}

	private JLabel getLblChain() {
		if (lblChain == null) {
			lblChain = new JLabel("Is Chain Result");
		}
		return lblChain;
	}

	private JLabel getLblSkittish() {
		if (lblSkittish == null) {
			lblSkittish = new JLabel("Is Skittish");
		}
		return lblSkittish;
	}

	private JComboBox<AnyYesNo> getCbChain() {
		if (cbChain == null) {
			cbChain = new JComboBox<>(AnyYesNo.values());
		}
		return cbChain;
	}

	private JComboBox<AnyYesNo> getCbMultiple() {
		if (cbMultiple == null) {
			cbMultiple = new JComboBox<>(AnyYesNo.values());
		}
		return cbMultiple;
	}

	private JComboBox<AnyYesNo> getCbSkittish() {
		if (cbSkittish == null) {
			cbSkittish = new JComboBox<>(AnyYesNo.values());
		}
		return cbSkittish;
	}

	private JComboBox<AnyYesNo> getCbMultiScare() {
		if (cbMultiScare == null) {
			cbMultiScare = new JComboBox<>(AnyYesNo.values());
		}
		return cbMultiScare;
	}

	private JComboBox<AnyYesNo> getCbSkittishAggressive() {
		if (cbSkittishAggressive == null) {
			cbSkittishAggressive = new JComboBox<>(AnyYesNo.values());
		}
		return cbSkittishAggressive;
	}

	private JComboBox<AnyYesNo> getCbSingleAdvances() {
		if (cbSingleAdvances == null) {
			cbSingleAdvances = new JComboBox<>(AnyYesNo.values());
		}
		return cbSingleAdvances;
	}

	private AutoWidthSortableJTable<PathDetails> getTablePathDetails() {
		if (tablePathDetails == null) {
			tablePathDetails = new AutoWidthSortableJTable<>();
		}
		return tablePathDetails;
	}

	public static void main(String[] args) {
		PermuteMMOFilter filter = new PermuteMMOFilter();
		filter.pack();
		filter.setLocationRelativeTo(null);
		filter.setVisible(true);
	}
}
