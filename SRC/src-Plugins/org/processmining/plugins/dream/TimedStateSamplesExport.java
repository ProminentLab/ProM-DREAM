/**
 * 
 */
package org.processmining.plugins.dream;

import java.io.File;
import java.io.IOException;

import org.processmining.contexts.uitopia.annotations.UIExportPlugin;
import org.processmining.framework.plugin.PluginContext;
import org.processmining.framework.plugin.annotations.Plugin;
import org.processmining.framework.plugin.annotations.PluginVariant;
import org.processmining.plugins.dream.core.TimedStateSamples;

/**
 * @author Julian Theis
 *
 */
@Plugin(name = "Timed State Samples Export", returnLabels = {}, returnTypes = {}, parameterLabels = {
		"Timed State Samples", "File" }, userAccessible = true)
@UIExportPlugin(description = "Timed State Samples files", extension = "csv")
public class TimedStateSamplesExport {
	@PluginVariant(variantLabel = "Timed State Samples Export", requiredParameterLabels = { 0, 1 })
	public void export(PluginContext context, TimedStateSamples tss, File file) throws IOException {
		tss.exportToFile(context, file);
	}
}
