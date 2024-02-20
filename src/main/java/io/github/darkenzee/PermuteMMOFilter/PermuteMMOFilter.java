package io.github.darkenzee.PermuteMMOFilter;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import java.awt.Dimension;

public class PermuteMMOFilter extends JFrame
{
	public PermuteMMOFilter() {
		initGUI();
	}
	private void initGUI() {
		setMinimumSize(new Dimension(600, 500));
		setTitle("Permutation Filter");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setIconImage(new ImageIcon(getClass().getResource("/icons/Hektakun-Pokemon-479-Rotom-Normal.72.png")).getImage());
	}
	private static final long serialVersionUID = -2001943701794082933L;

	public static void main( String[] args )
    {
		PermuteMMOFilter filter = new PermuteMMOFilter();
		filter.pack();
		filter.setLocationRelativeTo(null);
		filter.setVisible(true);
    }
}
