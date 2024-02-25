package io.github.darkenzee.PermuteMMOFilter;

import java.util.function.Supplier;

import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class MaxSpinnerChangeListener implements ChangeListener {

	private final JSpinner maxSpinner;
	private final Supplier<JSpinner> pairedMinSpinner;
	
	public MaxSpinnerChangeListener(JSpinner maxSpinner, Supplier<JSpinner> pairedMinSpinner) {
		this.maxSpinner = maxSpinner;
		this.pairedMinSpinner = pairedMinSpinner;
	}
	
	@Override
	public void stateChanged(ChangeEvent e) {
		JSpinner minSpinner = pairedMinSpinner.get();
		int maxSpinnerValue = (int)maxSpinner.getValue();
		if (maxSpinnerValue < (int)minSpinner.getValue()) {
			minSpinner.setValue(maxSpinnerValue);
		}
	}
	
}
