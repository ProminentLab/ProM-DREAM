package org.processmining.plugins.dream.core.pntools;

import org.processmining.contexts.uitopia.annotations.UIImportPlugin;
import org.processmining.framework.plugin.annotations.Plugin;
import org.processmining.framework.plugin.annotations.PluginLevel;
import org.processmining.models.graphbased.directed.petrinet.Petrinet;
import org.processmining.models.semantics.petrinet.Marking;

@Plugin(name = "Import Petri net from Stream", level = PluginLevel.PeerReviewed, parameterLabels = { "Filename" }, returnLabels = { "Petri Net",
		"Marking" }, returnTypes = { Petrinet.class, Marking.class })
@UIImportPlugin(description = "PNML Petri net files", extensions = { "pnml" })
public class PnmlImportNetFromStream {// extends AbstractImportPlugin {

	/*
	protected FileFilter getFileFilter() {
		return new FileNameExtensionFilter("PNML files", "pnml");
	}
	
	public Object importFromStream(PluginContext context, InputStream input, String filename, long fileSizeInBytes)
			throws Exception {
		PnmlImportUtils utils = new PnmlImportUtils();
		Pnml pnml = utils.importPnmlFromStream(context, input, filename, fileSizeInBytes);
		if (pnml == null) {
			/*
			 * No PNML found in file. Fail.
			 */
	
	
	//		return null;
	//	}
		/*
		 * PNML file has been imported. Now we need to convert the contents to a
		 * regular Petri net.
		 */
		//PetrinetGraph net = PetrinetFactory.newPetrinet(pnml.getLabel());

		//return utils.connectNet(context, pnml, net);
	//}
}
