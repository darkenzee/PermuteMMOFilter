package io.github.darkenzee.PermuteMMOFilter;

import java.util.function.Supplier;

import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class MinSpinnerChangeListener implements ChangeListener {

	private final JSpinner minSpinner;
	private final Supplier<JSpinner> pairedMaxSpinner;
	
	public MinSpinnerChangeListener(JSpinner minSpinner, Supplier<JSpinner> pairedMaxSpinner) {
		this.minSpinner = minSpinner;
		this.pairedMaxSpinner = pairedMaxSpinner;
	}
	
	@Override
	public void stateChanged(ChangeEvent e) {
		JSpinner maxSpinner = pairedMaxSpinner.get();
		int minSpinnerValue = (int)minSpinner.getValue();
		if (minSpinnerValue > (int)maxSpinner.getValue()) {
			maxSpinner.setValue(minSpinnerValue);
		}
	}
	
}
