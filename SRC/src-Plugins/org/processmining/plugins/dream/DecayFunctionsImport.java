/**
 * 
 */
package org.processmining.plugins.dream;

import java.io.InputStream;

import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.processmining.contexts.uitopia.annotations.UIImportPlugin;
import org.processmining.framework.abstractplugins.AbstractImportPlugin;
import org.processmining.framework.plugin.PluginContext;
import org.processmining.framework.plugin.annotations.Plugin;
import org.processmining.plugins.dream.core.DecayFunctions;

/**
 * @author Julian Theis
 *
 */
@Plugin(name = "Import Decay Functions from file", parameterLabels = { "Filename" }, returnLabels = {
		"Decay Functions" }, returnTypes = { DecayFunctions.class })
@UIImportPlugin(description = "Decay Functions files", extensions = { "df" })
public class DecayFunctionsImport extends AbstractImportPlugin {
	protected FileFilter getFileFilter() {
		return new FileNameExtensionFilter("Decay Function files", "df");
	}

	protected Object importFromStream(PluginContext context, InputStream input, String filename, long fileSizeInBytes)
			throws Exception {
		DecayFunctions df = new DecayFunctions(input);
		return df;
	}

}
