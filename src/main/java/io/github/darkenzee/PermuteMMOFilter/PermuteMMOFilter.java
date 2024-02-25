package io.github.darkenzee.PermuteMMOFilter;

import static javax.swing.JFileChooser.APPROVE_OPTION;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Vector;
import java.util.function.Function;
import java.util.jar.Attributes;
import java.util.jar.Manifest;
import java.util.prefs.Preferences;

import javax.swing.DefaultComboBoxModel;
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
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;

import io.github.darkenzee.PermuteMMOFilter.parser.PathDetails;
import io.github.darkenzee.PermuteMMOFilter.parser.PathDetailsParser;
import io.github.darkenzee.PermuteMMOFilter.predicates.IPredicate;
import io.github.darkenzee.PermuteMMOFilter.predicates.PredicateFilter;
import io.github.darkenzee.PermuteMMOFilter.tables.AutoWidthSortableJTable;
import io.github.darkenzee.PermuteMMOFilter.tables.IRowClickedCallbacker;
import io.github.darkenzee.PermuteMMOFilter.types.AnyYesNo;
import io.github.darkenzee.PermuteMMOFilter.types.IMatchesExcpected;
import io.github.darkenzee.PermuteMMOFilter.types.PokemonGender;
import io.github.darkenzee.PermuteMMOFilter.types.PokemonNature;
import io.github.darkenzee.PermuteMMOFilter.types.ShinyType;
import javax.swing.JButton;

public class PermuteMMOFilter extends JFrame implements ActionListener, ChangeListener {
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
	private JLabel lblPathLength;
	private JLabel lblMinimumSpawns;
	private JLabel lblIVs;
	private JLabel lblMin;
	private JLabel lblMax;
	private JLabel lblIvHp;
	private JLabel lblIvAtk;
	private JLabel lblIvDef;
	private JLabel lblIvSpAtk;
	private JLabel lblIvSpDef;
	private JLabel lblIvSpeed;

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

	private JSpinner spinnerPathLength;
	private JSpinner spinnerMinimumPathResults;
	private JSpinner spinnerMinIvHp;
	private JSpinner spinnerMaxIvHp;
	private JSpinner spinnerMinIvAtk;
	private JSpinner spinnerMaxIvAtk;
	private JSpinner spinnerMinIvDef;
	private JSpinner spinnerMaxIvDef;
	private JSpinner spinnerMinIvSpAtk;
	private JSpinner spinnerMaxIvSpAtk;
	private JSpinner spinnerMinIvSpDef;
	private JSpinner spinnerMaxIvSpDef;
	private JSpinner spinnerMinIvSpeed;
	private JSpinner spinnerMaxIvSpeed;

	private JScrollPane scrollPanePathDetails;
	private JPanel panelStatus;
	private AutoWidthSortableJTable<PathDetails> tablePathDetails;

	private List<PathDetails> currentPaths = new ArrayList<>();
	private PathDetailsTableModel tableModel;
	private JButton btnResetFilters;

	public PermuteMMOFilter() {
		initGUI();
	}

	private void initGUI() {
		setPreferredSize(new Dimension(1200, 850));
		setMinimumSize(new Dimension(1030, 800));
		setTitle("Permutation Filter - " + getProgramVersion());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setIconImage(
				new ImageIcon(getClass().getResource("/icons/Hektakun-Pokemon-479-Rotom-Normal.72.png")).getImage());
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE };
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
		GridBagConstraints gbc_lblIVs = new GridBagConstraints();
		gbc_lblIVs.anchor = GridBagConstraints.SOUTHEAST;
		gbc_lblIVs.insets = new Insets(0, 0, 5, 5);
		gbc_lblIVs.gridx = 6;
		gbc_lblIVs.gridy = 1;
		getContentPane().add(getLblIVs(), gbc_lblIVs);
		GridBagConstraints gbc_lblIvHp = new GridBagConstraints();
		gbc_lblIvHp.anchor = GridBagConstraints.SOUTH;
		gbc_lblIvHp.insets = new Insets(0, 0, 5, 5);
		gbc_lblIvHp.gridx = 7;
		gbc_lblIvHp.gridy = 1;
		getContentPane().add(getLblIvHp(), gbc_lblIvHp);
		GridBagConstraints gbc_lblIvAtk = new GridBagConstraints();
		gbc_lblIvAtk.anchor = GridBagConstraints.SOUTH;
		gbc_lblIvAtk.insets = new Insets(0, 0, 5, 5);
		gbc_lblIvAtk.gridx = 8;
		gbc_lblIvAtk.gridy = 1;
		getContentPane().add(getLblIvAtk(), gbc_lblIvAtk);
		GridBagConstraints gbc_lblIvDef = new GridBagConstraints();
		gbc_lblIvDef.anchor = GridBagConstraints.SOUTH;
		gbc_lblIvDef.insets = new Insets(0, 0, 5, 5);
		gbc_lblIvDef.gridx = 9;
		gbc_lblIvDef.gridy = 1;
		getContentPane().add(getLblIvDef(), gbc_lblIvDef);
		GridBagConstraints gbc_lblIvSpAtk = new GridBagConstraints();
		gbc_lblIvSpAtk.anchor = GridBagConstraints.SOUTH;
		gbc_lblIvSpAtk.insets = new Insets(0, 0, 5, 5);
		gbc_lblIvSpAtk.gridx = 10;
		gbc_lblIvSpAtk.gridy = 1;
		getContentPane().add(getLblIvSpAtk(), gbc_lblIvSpAtk);
		GridBagConstraints gbc_lblIvSpDef = new GridBagConstraints();
		gbc_lblIvSpDef.anchor = GridBagConstraints.SOUTH;
		gbc_lblIvSpDef.insets = new Insets(0, 0, 5, 5);
		gbc_lblIvSpDef.gridx = 11;
		gbc_lblIvSpDef.gridy = 1;
		getContentPane().add(getLblIvSpDef(), gbc_lblIvSpDef);
		GridBagConstraints gbc_lblIvSpeed = new GridBagConstraints();
		gbc_lblIvSpeed.anchor = GridBagConstraints.SOUTH;
		gbc_lblIvSpeed.insets = new Insets(0, 0, 5, 5);
		gbc_lblIvSpeed.gridx = 12;
		gbc_lblIvSpeed.gridy = 1;
		getContentPane().add(getLblIvSpeed(), gbc_lblIvSpeed);
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
		GridBagConstraints gbc_lblMin = new GridBagConstraints();
		gbc_lblMin.anchor = GridBagConstraints.EAST;
		gbc_lblMin.insets = new Insets(0, 0, 5, 5);
		gbc_lblMin.gridx = 6;
		gbc_lblMin.gridy = 2;
		getContentPane().add(getLblMin(), gbc_lblMin);
		GridBagConstraints gbc_spinnerMinIvHp = new GridBagConstraints();
		gbc_spinnerMinIvHp.insets = new Insets(0, 0, 5, 5);
		gbc_spinnerMinIvHp.gridx = 7;
		gbc_spinnerMinIvHp.gridy = 2;
		getContentPane().add(getSpinnerMinIvHp(), gbc_spinnerMinIvHp);
		GridBagConstraints gbc_spinnerMinIvAtk = new GridBagConstraints();
		gbc_spinnerMinIvAtk.insets = new Insets(0, 0, 5, 5);
		gbc_spinnerMinIvAtk.gridx = 8;
		gbc_spinnerMinIvAtk.gridy = 2;
		getContentPane().add(getSpinnerMinIvAtk(), gbc_spinnerMinIvAtk);
		GridBagConstraints gbc_spinnerMinIvDef = new GridBagConstraints();
		gbc_spinnerMinIvDef.insets = new Insets(0, 0, 5, 5);
		gbc_spinnerMinIvDef.gridx = 9;
		gbc_spinnerMinIvDef.gridy = 2;
		getContentPane().add(getSpinnerMinIvDef(), gbc_spinnerMinIvDef);
		GridBagConstraints gbc_spinnerMinIvSpAtk = new GridBagConstraints();
		gbc_spinnerMinIvSpAtk.insets = new Insets(0, 0, 5, 5);
		gbc_spinnerMinIvSpAtk.gridx = 10;
		gbc_spinnerMinIvSpAtk.gridy = 2;
		getContentPane().add(getSpinnerMinIvSpAtk(), gbc_spinnerMinIvSpAtk);
		GridBagConstraints gbc_spinnerMinIvSpDef = new GridBagConstraints();
		gbc_spinnerMinIvSpDef.insets = new Insets(0, 0, 5, 5);
		gbc_spinnerMinIvSpDef.gridx = 11;
		gbc_spinnerMinIvSpDef.gridy = 2;
		getContentPane().add(getSpinnerMinIvSpDef(), gbc_spinnerMinIvSpDef);
		GridBagConstraints gbc_spinnerMinIvSpeed = new GridBagConstraints();
		gbc_spinnerMinIvSpeed.insets = new Insets(0, 0, 5, 5);
		gbc_spinnerMinIvSpeed.gridx = 12;
		gbc_spinnerMinIvSpeed.gridy = 2;
		getContentPane().add(getSpinnerMinIvSpeed(), gbc_spinnerMinIvSpeed);
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
		GridBagConstraints gbc_lblMax = new GridBagConstraints();
		gbc_lblMax.anchor = GridBagConstraints.EAST;
		gbc_lblMax.insets = new Insets(0, 10, 5, 5);
		gbc_lblMax.gridx = 6;
		gbc_lblMax.gridy = 3;
		getContentPane().add(getLblMax(), gbc_lblMax);
		GridBagConstraints gbc_spinnerMaxIvHp = new GridBagConstraints();
		gbc_spinnerMaxIvHp.insets = new Insets(0, 0, 5, 5);
		gbc_spinnerMaxIvHp.gridx = 7;
		gbc_spinnerMaxIvHp.gridy = 3;
		getContentPane().add(getSpinnerMaxIvHp(), gbc_spinnerMaxIvHp);
		GridBagConstraints gbc_spinnerMaxIvAtk = new GridBagConstraints();
		gbc_spinnerMaxIvAtk.insets = new Insets(0, 0, 5, 5);
		gbc_spinnerMaxIvAtk.gridx = 8;
		gbc_spinnerMaxIvAtk.gridy = 3;
		getContentPane().add(getSpinnerMaxIvAtk(), gbc_spinnerMaxIvAtk);
		GridBagConstraints gbc_spinnerMaxIvDef = new GridBagConstraints();
		gbc_spinnerMaxIvDef.insets = new Insets(0, 0, 5, 5);
		gbc_spinnerMaxIvDef.gridx = 9;
		gbc_spinnerMaxIvDef.gridy = 3;
		getContentPane().add(getSpinnerMaxIvDef(), gbc_spinnerMaxIvDef);
		GridBagConstraints gbc_spinnerMaxIvSpAtk = new GridBagConstraints();
		gbc_spinnerMaxIvSpAtk.insets = new Insets(0, 0, 5, 5);
		gbc_spinnerMaxIvSpAtk.gridx = 10;
		gbc_spinnerMaxIvSpAtk.gridy = 3;
		getContentPane().add(getSpinnerMaxIvSpAtk(), gbc_spinnerMaxIvSpAtk);
		GridBagConstraints gbc_spinnerMaxIvSpDef = new GridBagConstraints();
		gbc_spinnerMaxIvSpDef.insets = new Insets(0, 0, 5, 5);
		gbc_spinnerMaxIvSpDef.gridx = 11;
		gbc_spinnerMaxIvSpDef.gridy = 3;
		getContentPane().add(getSpinnerMaxIvSpDef(), gbc_spinnerMaxIvSpDef);
		GridBagConstraints gbc_spinnerMaxIvSpeed = new GridBagConstraints();
		gbc_spinnerMaxIvSpeed.insets = new Insets(0, 0, 5, 5);
		gbc_spinnerMaxIvSpeed.gridx = 12;
		gbc_spinnerMaxIvSpeed.gridy = 3;
		getContentPane().add(getSpinnerMaxIvSpeed(), gbc_spinnerMaxIvSpeed);
		GridBagConstraints gbc_lblPathLength = new GridBagConstraints();
		gbc_lblPathLength.anchor = GridBagConstraints.EAST;
		gbc_lblPathLength.insets = new Insets(0, 5, 5, 5);
		gbc_lblPathLength.gridx = 0;
		gbc_lblPathLength.gridy = 4;
		getContentPane().add(getLblPathLength(), gbc_lblPathLength);
		GridBagConstraints gbc_spinnerPathLength = new GridBagConstraints();
		gbc_spinnerPathLength.anchor = GridBagConstraints.WEST;
		gbc_spinnerPathLength.insets = new Insets(0, 0, 5, 5);
		gbc_spinnerPathLength.gridx = 1;
		gbc_spinnerPathLength.gridy = 4;
		getContentPane().add(getSpinnerPathLength(), gbc_spinnerPathLength);
		GridBagConstraints gbc_lblMinimumSpawns = new GridBagConstraints();
		gbc_lblMinimumSpawns.anchor = GridBagConstraints.EAST;
		gbc_lblMinimumSpawns.insets = new Insets(0, 0, 5, 5);
		gbc_lblMinimumSpawns.gridx = 2;
		gbc_lblMinimumSpawns.gridy = 4;
		getContentPane().add(getLblMinimumSpawns(), gbc_lblMinimumSpawns);
		GridBagConstraints gbc_spinnerMinimumPathResults = new GridBagConstraints();
		gbc_spinnerMinimumPathResults.anchor = GridBagConstraints.WEST;
		gbc_spinnerMinimumPathResults.insets = new Insets(0, 0, 5, 5);
		gbc_spinnerMinimumPathResults.gridx = 3;
		gbc_spinnerMinimumPathResults.gridy = 4;
		getContentPane().add(getSpinnerMinimumPathResults(), gbc_spinnerMinimumPathResults);
		GridBagConstraints gbc_btnResetFilters = new GridBagConstraints();
		gbc_btnResetFilters.anchor = GridBagConstraints.EAST;
		gbc_btnResetFilters.gridwidth = 9;
		gbc_btnResetFilters.insets = new Insets(0, 0, 5, 5);
		gbc_btnResetFilters.gridx = 4;
		gbc_btnResetFilters.gridy = 4;
		getContentPane().add(getBtnResetFilters(), gbc_btnResetFilters);
		GridBagConstraints gbc_scrollPanePathDetails = new GridBagConstraints();
		gbc_scrollPanePathDetails.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPanePathDetails.gridwidth = 14;
		gbc_scrollPanePathDetails.fill = GridBagConstraints.BOTH;
		gbc_scrollPanePathDetails.gridx = 0;
		gbc_scrollPanePathDetails.gridy = 5;
		getContentPane().add(getScrollPanePathDetails(), gbc_scrollPanePathDetails);
		GridBagConstraints gbc_panelStatus = new GridBagConstraints();
		gbc_panelStatus.gridwidth = 14;
		gbc_panelStatus.fill = GridBagConstraints.BOTH;
		gbc_panelStatus.gridx = 0;
		gbc_panelStatus.gridy = 6;
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
						statusLoading();
						SwingUtilities.invokeLater(new Runnable() {
							@Override
							public void run() {
								try {
									self.load(PathDetailsParser.loadFromFile(chooser.getSelectedFile()));
								} catch (IOException e1) {
									e1.printStackTrace();
								}
							}
						});
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
					final JTextArea text = new JTextArea("", 20, 80);
					text.setFont(new Font("monospaced", Font.PLAIN, 12));
					JOptionPane pane = new JOptionPane(
							new Object[] { "Paste path from PermuteMMO below", new JScrollPane(text) },
							JOptionPane.QUESTION_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
					pane.setWantsInput(false);
					JDialog dialog = pane.createDialog(self, "Load From Text");
					dialog.pack();
					dialog.setModal(true);
					dialog.setLocationRelativeTo(self);
					dialog.setResizable(true);
					dialog.setMinimumSize(dialog.getSize());
					dialog.setVisible(true);
					Integer value = (Integer) pane.getValue();
					if (value != null && value.intValue() == JOptionPane.OK_OPTION) {
						statusLoading();
						SwingUtilities.invokeLater(new Runnable() {
							@Override
							public void run() {
								try {
									self.load(PathDetailsParser.loadFromString(text.getText()));
								} catch (IOException e1) {
									e1.printStackTrace();
								}
							}
						});
					}
				}
			});
		}
		return mntmLoadPathFromText;
	}

	private JComboBox<String> getCbSpecies() {
		if (cbSpecies == null) {
			cbSpecies = new JComboBox<>(new String[] { "Any" });
			cbSpecies.setPreferredSize(new Dimension(120, 22));
			cbSpecies.setMinimumSize(new Dimension(120, 22));
			cbSpecies.addActionListener(self);
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
			lblGender = new JLabel("Gender (" + PokemonGender.Any.symbol() + ")");
		}
		return lblGender;
	}

	private JComboBox<PokemonGender> getCbGender() {
		if (cbGender == null) {
			cbGender = new JComboBox<>(PokemonGender.values());
			cbGender.addActionListener(self);
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
			cbNature.addActionListener(self);
		}
		return cbNature;
	}

	private JLabel getLblShinyType() {
		if (lblShinyType == null) {
			lblShinyType = new JLabel("Shiny Type (" + ShinyType.Any.symbol() + ")");
		}
		return lblShinyType;
	}

	private JComboBox<ShinyType> getCbShinyType() {
		if (cbShinyType == null) {
			cbShinyType = new JComboBox<>(ShinyType.values());
			cbShinyType.addActionListener(this);
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
			lblAlpha = new JLabel("Is Alpha (α)");
		}
		return lblAlpha;
	}

	private JComboBox<AnyYesNo> getCbAlpha() {
		if (cbAlpha == null) {
			cbAlpha = new JComboBox<>(AnyYesNo.values());
			cbAlpha.addActionListener(self);
		}
		return cbAlpha;
	}

	private JLabel getLblBonus() {
		if (lblBonus == null) {
			lblBonus = new JLabel("Is Bonus Round (BR)");
		}
		return lblBonus;
	}

	private JComboBox<AnyYesNo> getCbBonus() {
		if (cbBonus == null) {
			cbBonus = new JComboBox<>(AnyYesNo.values());
			cbBonus.addActionListener(self);
		}
		return cbBonus;
	}

	private JLabel getLblAggresive() {
		if (lblAggresive == null) {
			lblAggresive = new JLabel("Is Skittish Aggressive (AG)");
		}
		return lblAggresive;
	}

	private JLabel getLblMultiScare() {
		if (lblMultiScare == null) {
			lblMultiScare = new JLabel("Is Multi-Scare (MS)");
		}
		return lblMultiScare;
	}

	private JLabel getLblSingleAdvances() {
		if (lblSingleAdvances == null) {
			lblSingleAdvances = new JLabel("Is Single Advances (SA)");
		}
		return lblSingleAdvances;
	}

	private JLabel getLblMultipleResults() {
		if (lblMultipleResults == null) {
			lblMultipleResults = new JLabel("Has Multiple Results (MR)");
		}
		return lblMultipleResults;
	}

	private JLabel getLblChain() {
		if (lblChain == null) {
			lblChain = new JLabel("Is Chain Result (CR)");
		}
		return lblChain;
	}

	private JLabel getLblSkittish() {
		if (lblSkittish == null) {
			lblSkittish = new JLabel("Is Skittish (SK)");
		}
		return lblSkittish;
	}

	private JComboBox<AnyYesNo> getCbChain() {
		if (cbChain == null) {
			cbChain = new JComboBox<>(AnyYesNo.values());
			cbChain.addActionListener(self);
		}
		return cbChain;
	}

	private JComboBox<AnyYesNo> getCbMultiple() {
		if (cbMultiple == null) {
			cbMultiple = new JComboBox<>(AnyYesNo.values());
			cbMultiple.addActionListener(self);
		}
		return cbMultiple;
	}

	private JComboBox<AnyYesNo> getCbSkittish() {
		if (cbSkittish == null) {
			cbSkittish = new JComboBox<>(AnyYesNo.values());
			cbSkittish.addActionListener(self);
		}
		return cbSkittish;
	}

	private JComboBox<AnyYesNo> getCbMultiScare() {
		if (cbMultiScare == null) {
			cbMultiScare = new JComboBox<>(AnyYesNo.values());
			cbMultiScare.addActionListener(self);
		}
		return cbMultiScare;
	}

	private JComboBox<AnyYesNo> getCbSkittishAggressive() {
		if (cbSkittishAggressive == null) {
			cbSkittishAggressive = new JComboBox<>(AnyYesNo.values());
			cbSkittishAggressive.addActionListener(self);
		}
		return cbSkittishAggressive;
	}

	private JComboBox<AnyYesNo> getCbSingleAdvances() {
		if (cbSingleAdvances == null) {
			cbSingleAdvances = new JComboBox<>(AnyYesNo.values());
			cbSingleAdvances.addActionListener(self);
		}
		return cbSingleAdvances;
	}

	private JLabel getLblIVs() {
		if (lblIVs == null) {
			lblIVs = new JLabel("IVs");
		}
		return lblIVs;
	}

	private JLabel getLblMin() {
		if (lblMin == null) {
			lblMin = new JLabel("Min");
		}
		return lblMin;
	}

	private JLabel getLblMax() {
		if (lblMax == null) {
			lblMax = new JLabel("Max");
		}
		return lblMax;
	}

	private JLabel getLblIvHp() {
		if (lblIvHp == null) {
			lblIvHp = new JLabel("HP");
		}
		return lblIvHp;
	}

	private JLabel getLblIvAtk() {
		if (lblIvAtk == null) {
			lblIvAtk = new JLabel("Atk");
		}
		return lblIvAtk;
	}

	private JLabel getLblIvDef() {
		if (lblIvDef == null) {
			lblIvDef = new JLabel("Def");
		}
		return lblIvDef;
	}

	private JLabel getLblIvSpAtk() {
		if (lblIvSpAtk == null) {
			lblIvSpAtk = new JLabel("SAt");
		}
		return lblIvSpAtk;
	}

	private JLabel getLblIvSpDef() {
		if (lblIvSpDef == null) {
			lblIvSpDef = new JLabel("SDf");
		}
		return lblIvSpDef;
	}

	private JLabel getLblIvSpeed() {
		if (lblIvSpeed == null) {
			lblIvSpeed = new JLabel("Spd");
		}
		return lblIvSpeed;
	}

	private JSpinner getSpinnerMinIvHp() {
		if (spinnerMinIvHp == null) {
			spinnerMinIvHp = new JSpinner();
			spinnerMinIvHp.setModel(new SpinnerNumberModel(0, 0, 31, 1));
			spinnerMinIvHp.addChangeListener(new MinSpinnerChangeListener(spinnerMinIvHp, self::getSpinnerMaxIvHp));
			spinnerMinIvHp.addChangeListener(self);
		}
		return spinnerMinIvHp;
	}

	private JSpinner getSpinnerMaxIvHp() {
		if (spinnerMaxIvHp == null) {
			spinnerMaxIvHp = new JSpinner();
			spinnerMaxIvHp.setModel(new SpinnerNumberModel(31, 0, 31, 1));
			spinnerMaxIvHp.addChangeListener(new MaxSpinnerChangeListener(spinnerMaxIvHp, self::getSpinnerMinIvHp));
			spinnerMaxIvHp.addChangeListener(self);
		}
		return spinnerMaxIvHp;
	}

	private JSpinner getSpinnerMinIvAtk() {
		if (spinnerMinIvAtk == null) {
			spinnerMinIvAtk = new JSpinner();
			spinnerMinIvAtk.setModel(new SpinnerNumberModel(0, 0, 31, 1));
			spinnerMinIvAtk.addChangeListener(new MinSpinnerChangeListener(spinnerMinIvAtk, self::getSpinnerMaxIvAtk));
			spinnerMinIvAtk.addChangeListener(self);
		}
		return spinnerMinIvAtk;
	}

	private JSpinner getSpinnerMaxIvAtk() {
		if (spinnerMaxIvAtk == null) {
			spinnerMaxIvAtk = new JSpinner();
			spinnerMaxIvAtk.setModel(new SpinnerNumberModel(31, 0, 31, 1));
			spinnerMaxIvAtk.addChangeListener(new MaxSpinnerChangeListener(spinnerMaxIvAtk, self::getSpinnerMinIvAtk));
			spinnerMaxIvAtk.addChangeListener(self);
		}
		return spinnerMaxIvAtk;
	}

	private JSpinner getSpinnerMinIvDef() {
		if (spinnerMinIvDef == null) {
			spinnerMinIvDef = new JSpinner();
			spinnerMinIvDef.setModel(new SpinnerNumberModel(0, 0, 31, 1));
			spinnerMinIvDef.addChangeListener(new MinSpinnerChangeListener(spinnerMinIvDef, self::getSpinnerMaxIvDef));
			spinnerMinIvDef.addChangeListener(self);
		}
		return spinnerMinIvDef;
	}

	private JSpinner getSpinnerMaxIvDef() {
		if (spinnerMaxIvDef == null) {
			spinnerMaxIvDef = new JSpinner();
			spinnerMaxIvDef.setModel(new SpinnerNumberModel(31, 0, 31, 1));
			spinnerMaxIvDef.addChangeListener(new MaxSpinnerChangeListener(spinnerMaxIvDef, self::getSpinnerMinIvDef));
			spinnerMaxIvDef.addChangeListener(self);
		}
		return spinnerMaxIvDef;
	}

	private JSpinner getSpinnerMinIvSpAtk() {
		if (spinnerMinIvSpAtk == null) {
			spinnerMinIvSpAtk = new JSpinner();
			spinnerMinIvSpAtk.setModel(new SpinnerNumberModel(0, 0, 31, 1));
			spinnerMinIvSpAtk
					.addChangeListener(new MinSpinnerChangeListener(spinnerMinIvSpAtk, self::getSpinnerMaxIvSpAtk));
			spinnerMinIvSpAtk.addChangeListener(self);
		}
		return spinnerMinIvSpAtk;
	}

	private JSpinner getSpinnerMaxIvSpAtk() {
		if (spinnerMaxIvSpAtk == null) {
			spinnerMaxIvSpAtk = new JSpinner();
			spinnerMaxIvSpAtk.setModel(new SpinnerNumberModel(31, 0, 31, 1));
			spinnerMaxIvSpAtk
					.addChangeListener(new MaxSpinnerChangeListener(spinnerMaxIvSpAtk, self::getSpinnerMinIvSpAtk));
			spinnerMaxIvSpAtk.addChangeListener(self);
		}
		return spinnerMaxIvSpAtk;
	}

	private JSpinner getSpinnerMinIvSpDef() {
		if (spinnerMinIvSpDef == null) {
			spinnerMinIvSpDef = new JSpinner();
			spinnerMinIvSpDef.setModel(new SpinnerNumberModel(0, 0, 31, 1));
			spinnerMinIvSpDef
					.addChangeListener(new MinSpinnerChangeListener(spinnerMinIvSpDef, self::getSpinnerMaxIvSpDef));
			spinnerMinIvSpDef.addChangeListener(self);
		}
		return spinnerMinIvSpDef;
	}

	private JSpinner getSpinnerMaxIvSpDef() {
		if (spinnerMaxIvSpDef == null) {
			spinnerMaxIvSpDef = new JSpinner();
			spinnerMaxIvSpDef.setModel(new SpinnerNumberModel(31, 0, 31, 1));
			spinnerMaxIvSpDef
					.addChangeListener(new MaxSpinnerChangeListener(spinnerMaxIvSpDef, self::getSpinnerMinIvSpDef));
			spinnerMaxIvSpDef.addChangeListener(self);
		}
		return spinnerMaxIvSpDef;
	}

	private JSpinner getSpinnerMinIvSpeed() {
		if (spinnerMinIvSpeed == null) {
			spinnerMinIvSpeed = new JSpinner();
			spinnerMinIvSpeed.setModel(new SpinnerNumberModel(0, 0, 31, 1));
			spinnerMinIvSpeed
					.addChangeListener(new MinSpinnerChangeListener(spinnerMinIvSpeed, self::getSpinnerMaxIvSpeed));
			spinnerMinIvSpeed.addChangeListener(self);
		}
		return spinnerMinIvSpeed;
	}

	private JSpinner getSpinnerMaxIvSpeed() {
		if (spinnerMaxIvSpeed == null) {
			spinnerMaxIvSpeed = new JSpinner();
			spinnerMaxIvSpeed.setModel(new SpinnerNumberModel(31, 0, 31, 1));
			spinnerMaxIvSpeed
					.addChangeListener(new MaxSpinnerChangeListener(spinnerMaxIvSpeed, self::getSpinnerMinIvSpeed));
			spinnerMaxIvSpeed.addChangeListener(self);
		}
		return spinnerMaxIvSpeed;
	}

	private AutoWidthSortableJTable<PathDetails> getTablePathDetails() {
		if (tablePathDetails == null) {
			tablePathDetails = new AutoWidthSortableJTable<>();
			tablePathDetails.setActualModel(getTableModel());

			DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
			centerRenderer.setHorizontalAlignment(JLabel.CENTER);
			DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
			rightRenderer.setHorizontalAlignment(JLabel.RIGHT);

			int medium = 80;
			tablePathDetails.getColumnModel().getColumn(2).setMinWidth(medium);
			tablePathDetails.getColumnModel().getColumn(5).setMinWidth(medium);

			tablePathDetails.getColumnModel().getColumn(2).setMaxWidth(medium);
			tablePathDetails.getColumnModel().getColumn(5).setMaxWidth(medium);

			int small = 40;
			tablePathDetails.getColumnModel().getColumn(1).setMinWidth(small);
			tablePathDetails.getColumnModel().getColumn(3).setMinWidth(small);
			tablePathDetails.getColumnModel().getColumn(4).setMinWidth(small);
			tablePathDetails.getColumnModel().getColumn(6).setMinWidth(small);
			tablePathDetails.getColumnModel().getColumn(7).setMinWidth(small);
			tablePathDetails.getColumnModel().getColumn(8).setMinWidth(small);
			tablePathDetails.getColumnModel().getColumn(9).setMinWidth(small);
			tablePathDetails.getColumnModel().getColumn(10).setMinWidth(small);
			tablePathDetails.getColumnModel().getColumn(11).setMinWidth(small);
			tablePathDetails.getColumnModel().getColumn(12).setMinWidth(small);
			tablePathDetails.getColumnModel().getColumn(13).setMinWidth(small);
			tablePathDetails.getColumnModel().getColumn(14).setMinWidth(small);
			tablePathDetails.getColumnModel().getColumn(15).setMinWidth(small);
			tablePathDetails.getColumnModel().getColumn(16).setMinWidth(small);
			tablePathDetails.getColumnModel().getColumn(17).setMinWidth(small);
			tablePathDetails.getColumnModel().getColumn(18).setMinWidth(small);

			tablePathDetails.getColumnModel().getColumn(1).setMaxWidth(small);
			tablePathDetails.getColumnModel().getColumn(3).setMaxWidth(small);
			tablePathDetails.getColumnModel().getColumn(4).setMaxWidth(small);
			tablePathDetails.getColumnModel().getColumn(6).setMaxWidth(small);
			tablePathDetails.getColumnModel().getColumn(7).setMaxWidth(small);
			tablePathDetails.getColumnModel().getColumn(8).setMaxWidth(small);
			tablePathDetails.getColumnModel().getColumn(9).setMaxWidth(small);
			tablePathDetails.getColumnModel().getColumn(10).setMaxWidth(small);
			tablePathDetails.getColumnModel().getColumn(11).setMaxWidth(small);
			tablePathDetails.getColumnModel().getColumn(12).setMaxWidth(small);
			tablePathDetails.getColumnModel().getColumn(13).setMaxWidth(small);
			tablePathDetails.getColumnModel().getColumn(14).setMaxWidth(small);
			tablePathDetails.getColumnModel().getColumn(15).setMaxWidth(small);
			tablePathDetails.getColumnModel().getColumn(16).setMaxWidth(small);
			tablePathDetails.getColumnModel().getColumn(17).setMaxWidth(small);
			tablePathDetails.getColumnModel().getColumn(18).setMaxWidth(small);

			tablePathDetails.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
			tablePathDetails.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
			tablePathDetails.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
			tablePathDetails.getColumnModel().getColumn(12).setCellRenderer(centerRenderer);
			tablePathDetails.getColumnModel().getColumn(13).setCellRenderer(centerRenderer);
			tablePathDetails.getColumnModel().getColumn(14).setCellRenderer(centerRenderer);
			tablePathDetails.getColumnModel().getColumn(15).setCellRenderer(centerRenderer);
			tablePathDetails.getColumnModel().getColumn(16).setCellRenderer(centerRenderer);
			tablePathDetails.getColumnModel().getColumn(17).setCellRenderer(centerRenderer);
			tablePathDetails.getColumnModel().getColumn(18).setCellRenderer(centerRenderer);

			tablePathDetails.getColumnModel().getColumn(6).setCellRenderer(rightRenderer);
			tablePathDetails.getColumnModel().getColumn(7).setCellRenderer(rightRenderer);
			tablePathDetails.getColumnModel().getColumn(8).setCellRenderer(rightRenderer);
			tablePathDetails.getColumnModel().getColumn(9).setCellRenderer(rightRenderer);
			tablePathDetails.getColumnModel().getColumn(10).setCellRenderer(rightRenderer);
			tablePathDetails.getColumnModel().getColumn(11).setCellRenderer(rightRenderer);

			tablePathDetails.onRowDoubleClicked(new IRowClickedCallbacker<PathDetails>() {
				@Override
				public void rowClicked(MouseEvent e, PathDetails selectedItem) {
					SinglePathViewer dialog = new SinglePathViewer(selectedItem);
					dialog.setLocationRelativeTo(self);
					dialog.setVisible(true);
				}
			});
		}
		return tablePathDetails;
	}

	private JLabel getLblPathLength() {
		if (lblPathLength == null) {
			lblPathLength = new JLabel("Maximum Path Length");
		}
		return lblPathLength;
	}

	private JSpinner getSpinnerPathLength() {
		if (spinnerPathLength == null) {
			spinnerPathLength = new JSpinner();
			spinnerPathLength.setModel(new SpinnerNumberModel(20, 1, 20, 1));
			spinnerPathLength.addChangeListener(self);
		}
		return spinnerPathLength;
	}

	private JLabel getLblMinimumSpawns() {
		if (lblMinimumSpawns == null) {
			lblMinimumSpawns = new JLabel("Minimum Results on Path");
		}
		return lblMinimumSpawns;
	}

	private JSpinner getSpinnerMinimumPathResults() {
		if (spinnerMinimumPathResults == null) {
			spinnerMinimumPathResults = new JSpinner();
			spinnerMinimumPathResults.setModel(new SpinnerNumberModel(1, 1, 20, 1));
			spinnerMinimumPathResults.addChangeListener(self);
		}
		return spinnerMinimumPathResults;
	}

	private JButton getBtnResetFilters() {
		if (btnResetFilters == null) {
			btnResetFilters = new JButton("Reset Filters");
			btnResetFilters.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					resetSelectors();
				}
			});
		}
		return btnResetFilters;
	}

	private PathDetailsTableModel getTableModel() {
		if (tableModel == null) {
			tableModel = new PathDetailsTableModel();
			tableModel.updateModel(currentPaths);
		}
		return tableModel;
	}

	private void resetSelectors() {
		getTableModel().updateModel(new ArrayList<>());

		getCbSpecies().setSelectedIndex(0);
		getCbShinyType().setSelectedIndex(0);
		getCbGender().setSelectedIndex(0);
		getCbNature().setSelectedIndex(0);
		getCbAlpha().setSelectedIndex(0);
		getCbBonus().setSelectedIndex(0);
		getCbChain().setSelectedIndex(0);
		getCbMultiple().setSelectedIndex(0);
		getCbSkittish().setSelectedIndex(0);
		getCbMultiScare().setSelectedIndex(0);
		getCbSkittishAggressive().setSelectedIndex(0);
		getCbSingleAdvances().setSelectedIndex(0);

		getSpinnerPathLength().setValue(20);
		getSpinnerMinimumPathResults().setValue(1);

		getSpinnerMinIvHp().setValue(0);
		getSpinnerMaxIvHp().setValue(31);
		getSpinnerMinIvAtk().setValue(0);
		getSpinnerMaxIvAtk().setValue(31);
		getSpinnerMinIvDef().setValue(0);
		getSpinnerMaxIvDef().setValue(31);
		getSpinnerMinIvSpAtk().setValue(0);
		getSpinnerMaxIvSpAtk().setValue(31);
		getSpinnerMinIvSpDef().setValue(0);
		getSpinnerMaxIvSpDef().setValue(31);
		getSpinnerMinIvSpeed().setValue(0);
		getSpinnerMaxIvSpeed().setValue(31);

		Vector<String> species = new Vector<>();
		species.add("Any");
		SortedSet<String> currentSpecies = new TreeSet<>();
		for (PathDetails details : currentPaths) {
			currentSpecies.add(details.getSpecies());
		}
		species.addAll(currentSpecies);
		getCbSpecies().setModel(new DefaultComboBoxModel<String>(species));

		getTableModel().updateModel(currentPaths);
		updateStatus();
	}

	private void updateStatus() {
		self.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		getLblStatus().setText(getTableModel().getRowCount() + " of " + currentPaths.size());

	}

	private void statusLoading() {
		self.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		getLblStatus().setText("Loading...");
	}

	private void statusFiltering() {
		self.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		getLblStatus().setText("Filtering...");
	}

	private String getProgramVersion() {
		try {
			return getMyManifestAttributes().getValue(Attributes.Name.IMPLEMENTATION_VERSION);
		} catch (IOException e) {
			Properties info = new Properties();
			try {
				info.load(getClass().getResourceAsStream("/info.properties"));
				return info.getProperty("program.version", "Version Unknown");
			} catch (IOException e1) {
				return "Version Unknown";
			}
		}
	}

	private Attributes getMyManifestAttributes() throws IOException {
		String className = getClass().getSimpleName() + ".class";
		String classPath = getClass().getResource(className).toString();
		if (!classPath.startsWith("jar")) {
			throw new IOException("I don't live in a jar file");
		}
		URL url = new URL(classPath);
		JarURLConnection jarConnection = (JarURLConnection) url.openConnection();
		Manifest manifest = jarConnection.getManifest();
		return manifest.getMainAttributes();
	}

	private <T extends IMatchesExcpected<T>> IPredicate getPredicateFromCb(Function<PathDetails, T> getDetail,
			JComboBox<T> comboBox) {
		return d -> getDetail.apply(d).matchesExpected(comboBox.getModel().getElementAt(comboBox.getSelectedIndex()));
	}

	private IPredicate getPredicateFromCbBool(Function<PathDetails, Boolean> getBoolDetail,
			JComboBox<AnyYesNo> comboBox) {
		return d -> (getBoolDetail.apply(d) ? AnyYesNo.Yes : AnyYesNo.No)
				.matchesExpected(comboBox.getModel().getElementAt(comboBox.getSelectedIndex()));
	}

	private IPredicate getMinBoundFromSpinner(Function<PathDetails, Integer> getIntDetail, JSpinner spinner) {
		return d -> getIntDetail.apply(d) >= (int) spinner.getValue();
	}

	private IPredicate getMaxBoundFromSpinner(Function<PathDetails, Integer> getIntDetail, JSpinner spinner) {
		return d -> getIntDetail.apply(d) <= (int) spinner.getValue();
	}

	private void updateFilters() {
		statusFiltering();
		List<IPredicate> predicates = new ArrayList<>();

		if (getCbSpecies().getSelectedIndex() > 0) {
			predicates.add(d -> d.getSpecies().equals(getCbSpecies().getSelectedItem()));
		}

		predicates.add(getPredicateFromCb(PathDetails::getShinyType, getCbShinyType()));
		predicates.add(getPredicateFromCb(PathDetails::getGender, getCbGender()));
		predicates.add(getPredicateFromCb(PathDetails::getNature, getCbNature()));
		predicates.add(getPredicateFromCbBool(PathDetails::isChain, getCbChain()));
		predicates.add(getPredicateFromCbBool(PathDetails::isSpawnsMultipleResults, getCbMultiple()));
		predicates.add(getPredicateFromCbBool(PathDetails::isAlpha, getCbAlpha()));
		predicates.add(getPredicateFromCbBool(PathDetails::isBonus, getCbBonus()));
		predicates.add(getPredicateFromCbBool(PathDetails::isSkittish, getCbSkittish()));
		predicates.add(getPredicateFromCbBool(PathDetails::isSkittishMultiScaring, getCbMultiScare()));
		predicates.add(getPredicateFromCbBool(PathDetails::isSkittishAggressive, getCbSkittishAggressive()));
		predicates.add(getPredicateFromCbBool(PathDetails::isSingleAdvances, getCbSingleAdvances()));

		predicates.add(getMaxBoundFromSpinner(PathDetails::getFullPathLength, getSpinnerPathLength()));
		predicates.add(getMinBoundFromSpinner(PathDetails::getPathSpawnCount, getSpinnerMinimumPathResults()));

		predicates.add(getMinBoundFromSpinner(PathDetails::getIVHp, getSpinnerMinIvHp()));
		predicates.add(getMaxBoundFromSpinner(PathDetails::getIVHp, getSpinnerMaxIvHp()));
		predicates.add(getMinBoundFromSpinner(PathDetails::getIVAtk, getSpinnerMinIvAtk()));
		predicates.add(getMaxBoundFromSpinner(PathDetails::getIVAtk, getSpinnerMaxIvAtk()));
		predicates.add(getMinBoundFromSpinner(PathDetails::getIVDef, getSpinnerMinIvDef()));
		predicates.add(getMaxBoundFromSpinner(PathDetails::getIVDef, getSpinnerMaxIvDef()));
		predicates.add(getMinBoundFromSpinner(PathDetails::getIVSpAtk, getSpinnerMinIvSpAtk()));
		predicates.add(getMaxBoundFromSpinner(PathDetails::getIVSpAtk, getSpinnerMaxIvSpAtk()));
		predicates.add(getMinBoundFromSpinner(PathDetails::getIVSpDef, getSpinnerMinIvSpDef()));
		predicates.add(getMaxBoundFromSpinner(PathDetails::getIVSpDef, getSpinnerMaxIvSpDef()));
		predicates.add(getMinBoundFromSpinner(PathDetails::getIVSpeed, getSpinnerMinIvSpeed()));
		predicates.add(getMaxBoundFromSpinner(PathDetails::getIVSpeed, getSpinnerMaxIvSpeed()));

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				List<PathDetails> filtered = new PredicateFilter(predicates).applyFilters(currentPaths);
				getTableModel().updateModel(filtered);
				updateStatus();
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		updateFilters();
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		updateFilters();
	}

	protected void load(List<PathDetails> paths) {
		this.currentPaths = paths;
		resetSelectors();
	}

	public static void main(String[] args) {
		PermuteMMOFilter filter = new PermuteMMOFilter();
		filter.pack();
		filter.setLocationRelativeTo(null);
		filter.setVisible(true);
	}

}
