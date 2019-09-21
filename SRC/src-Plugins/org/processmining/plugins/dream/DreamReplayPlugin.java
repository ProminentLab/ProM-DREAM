package org.processmining.plugins.dream;

import java.awt.Dimension;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import org.deckfour.xes.model.XLog;
import org.processmining.acceptingpetrinet.models.AcceptingPetriNet;
import org.processmining.contexts.uitopia.UIPluginContext;
import org.processmining.contexts.uitopia.annotations.UITopiaVariant;
import org.processmining.framework.plugin.annotations.Plugin;
import org.processmining.framework.plugin.annotations.PluginCategory;
import org.processmining.framework.plugin.annotations.PluginVariant;
import org.processmining.log.csv.CSVFile;
import org.processmining.log.csv.config.CSVConfig;
import org.processmining.log.csvimport.CSVConversionPlugin;
import org.processmining.log.csvimport.exception.CSVConversionException;
import org.processmining.plugins.dream.core.Dataset;
import org.processmining.plugins.dream.core.DecayFunctions;
import org.processmining.plugins.dream.core.DecayReplay;
import org.processmining.plugins.dream.core.TimedStateSamples;

/**
 * @author Julian Theis
 *
 */

@Plugin(name = "Decay Replay Mining (DREAM) - Replay and Extract Timed State Samples", returnLabels = { "Timed State Samples" }, returnTypes = {
		TimedStateSamples.class }, parameterLabels = { "Event Log",
				"Petri net", "Decay Functions" }, userAccessible = true, categories = PluginCategory.Analytics, keywords = { "analytics",
						"enhancement", "decay functions", "timed state samples",
						"time" }, help = "Extracting Timed State Samples from enhanced Petri net using Decay Replay Mining (DREAM). Based on: Theis, Julian, and Darabi, Houshang. \"DREAM-NAP: Decay Replay Mining to Predict Next Process Activities.\" arXiv preprint arXiv:1903.05084 (2019). A project by the PROMINENT research group at the University of Illinois at Chicago.")
public class DreamReplayPlugin {
	@UITopiaVariant(affiliation = "University of Illinois at Chicago", author = "Julian Theis", email = "jtheis3@uic.edu")
	@PluginVariant(variantLabel = "Decay Replay Mining (DREAM)", requiredParameterLabels = { 0, 1, 2 })
	public TimedStateSamples mineWithParameters(UIPluginContext uicontext, CSVFile csvFile, AcceptingPetriNet uploadedPnml, DecayFunctions df) {

		CSVConversionPlugin csvc = new CSVConversionPlugin();

		XLog log = csvc.convertCSVToXES(uicontext, csvFile);		
		
		String headers = "";
		try {
			String[] header = csvFile.readHeader(new CSVConfig(csvFile));
			for (String st : header) {
				headers = headers + " " + st;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (CSVConversionException e) {
			e.printStackTrace();
		}

		List<Integer> selected_resources = getSelectedResources(uicontext, headers.split(" "));

		String timestamp_format = "d/M/yyyy HH:mm";
		boolean is_long = false;
		Dataset datasets = new Dataset(headers.split(" "), csvFile.getFilename(), timestamp_format,
				String.valueOf(is_long), selected_resources);
		
		if(selected_resources.isEmpty())
			uicontext.log("Event log does not contain resources, defaulting to generating metadata without resources ");
				
		// call the default decayminer and add more conditions as more variants are added
		TimedStateSamples tss = null;
		if (df.type == "Linear") {
			tss = DecayReplay.linearDecayMiner(datasets, log, uploadedPnml, df);
			uicontext.log("Petri net enhanced with linear decay functions.");
		}
		
		return tss;

	}


	private List<Integer> getSelectedResources(UIPluginContext context, String[] headers) {
		String[] resources = new String[headers.length];
		// show only resources in the selection pane if no column starts with resources just return an empty list
		int i = 0;
		int res_counter = 0;
		HashMap<String, Integer> resources_map = new HashMap<String, Integer>();
		// populate the list if any column is a resource to show it to the user
		for (String head : headers) {
			if (head.startsWith("Res") || head.startsWith("res")) {
				context.log("Column " + head);
				resources[i] = head;

				i++;
			}
			if (!head.isEmpty()) {
				resources_map.put(head, res_counter);
				res_counter++;
			}
		}

		// if there are no resources just return to the plugin method
		boolean is_empty = true;
		for (String s : resources) {
			if (s != null) {
				is_empty = false;
				break;
			}
		}

		List<Integer> selected_resources = new ArrayList<Integer>();
		if (is_empty) {
			context.log(" No available resources");
			return selected_resources;
		}

		// else get selected resources to use
		else {

			JList list = new JList(resources);
			list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
			list.setVisibleRowCount(5);
			JScrollPane pane = new JScrollPane(list);
			// set layout size here
			pane.setPreferredSize(new Dimension(100, 120));

			JOptionPane.showMessageDialog(null, pane);

			for (Integer jl : list.getSelectedIndices()) {
				selected_resources.add(resources_map.get(resources[jl]));
				context.log("Column selected " + resources[jl]);
			}

			return selected_resources;
		}

	}
}
