package org.processmining.plugins.dream;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.processmining.contexts.uitopia.annotations.Visualizer;
import org.processmining.framework.plugin.PluginContext;
import org.processmining.framework.plugin.annotations.Plugin;
import org.processmining.framework.plugin.annotations.PluginVariant;
import org.processmining.plugins.dream.core.TimedStateSamples;

/**
 * @author Julian Theis
 *
 */

@Plugin(name = "Show Timed State Samples", parameterLabels = { "Time State Samples" }, returnLabels = { "Timed State Samples Viewer" }, returnTypes = {
		JComponent.class }, userAccessible = true)
@Visualizer
public class TimedStateSamplesVisualizer {
	@PluginVariant(requiredParameterLabels = { 0 })
	public static JComponent visualize(PluginContext context, TimedStateSamples tss) {
		String[][] data = new String[tss.getSize()][tss.getSampleLenght()];
		
		int i = 0;
		for(String sample : tss.getSamples()) {
			data[i] = sample.split(";");
			i++;
		}
		
		// Column Names 
		String[] columnNames = tss.getHeader().split(";");

		// Initializing the JTable 
		JTable j = new JTable(data, columnNames);
		j.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		j.setBounds(30, 40, 200, 300);

		JScrollPane sp = new JScrollPane(j);
		return sp;
	}

}
