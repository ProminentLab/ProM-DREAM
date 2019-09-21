package org.processmining.plugins.dream;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.processmining.contexts.uitopia.annotations.Visualizer;
import org.processmining.framework.plugin.PluginContext;
import org.processmining.framework.plugin.annotations.Plugin;
import org.processmining.framework.plugin.annotations.PluginVariant;
import org.processmining.plugins.dream.core.DecayFunctions;

/**
 * @author Julian Theis
 *
 */

@Plugin(name = "Show Decay Functions", parameterLabels = { "Decay Functions" }, returnLabels = { "Decay Functions Viewer" }, returnTypes = {
		JComponent.class }, userAccessible = true)
@Visualizer
public class DecayFunctionsVisualizer {
	@PluginVariant(requiredParameterLabels = { 0 })
	public static JComponent visualize(PluginContext context, DecayFunctions df) {
		
		String[][] data = new String[df.getFunctions().size()][df.getHeader().split(";").length];
		
		int i = 0;
		for(String sample : df.getFunctions()) {
			data[i] = sample.split(";");
			i++;
		}
		
		// Column Names 
		String[] columnNames = df.getHeader().split(";");

		// Initializing the JTable 
		JTable j = new JTable(data, columnNames);
		j.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		j.setBounds(30, 40, 200, 300);

		JScrollPane sp = new JScrollPane(j);
		return sp;
	}

}
