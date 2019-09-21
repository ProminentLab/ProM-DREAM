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
import org.processmining.plugins.dream.core.DecayFunctions;

/**
 * @author Julian Theis
 *
 */
@Plugin(name = "Decay Functions Export", returnLabels = {}, returnTypes = {}, parameterLabels = {
		"Decay Functions", "File" }, userAccessible = true)
@UIExportPlugin(description = "Decay Functions files", extension = "df")
public class DecayFunctionsExport {
	@PluginVariant(variantLabel = "Decay Functions Export", requiredParameterLabels = { 0, 1 })
	public void export(PluginContext context, DecayFunctions df, File file) throws IOException {
		df.exportToFile(context, file);
	}
}
