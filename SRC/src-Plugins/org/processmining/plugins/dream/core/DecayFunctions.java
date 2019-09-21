package org.processmining.plugins.dream.core;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.processmining.framework.plugin.PluginContext;
import org.processmining.models.connections.GraphLayoutConnection;
import org.processmining.plugins.dream.core.petrinet.Place;
import org.processmining.plugins.dream.core.pnmetrics.decay.EstimationStatistic;

/**
 * @author Julian Theis
 *
 */
public class DecayFunctions {

	private Map<String, EstimationStatistic> estParam;
	public String type;

	public DecayFunctions(InputStream input) {
		this.type = "Linear";
		
		estParam = new HashMap<String, EstimationStatistic>();

		try {
			StringWriter writer = new StringWriter();
			IOUtils.copy(input, writer, StandardCharsets.UTF_8);
			String data = writer.toString();
			
			String[] functions = data.split("\n");
			for(int i = 1; i < functions.length; i++) {		
				if(functions[i].length() > 0) {
					String[] function = functions[i].split(";");
					String node = function[0];	
					Double beta = Double.parseDouble(function[1]);
					Double alpha = Double.parseDouble(function[2]);
					
					System.out.println(node + " --- " + beta + " -- " + alpha);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public DecayFunctions() {
		this.type = "Linear";
	}

	public void setEstimatedParamPlace(Map<Place, EstimationStatistic> estParam) {
		this.estParam = new HashMap<String, EstimationStatistic>();
		Iterator<Place> keys = estParam.keySet().iterator();
		while(keys.hasNext()) {
			Place key = keys.next();
			this.estParam.put(key.name(), estParam.get(key));
		}
	}
	
	public void setEstimatedParamString(Map<String, EstimationStatistic> estParam) {
		this.estParam = estParam;
	}

	public Map<String, EstimationStatistic> getEstimatedParameter() {
		return this.estParam;
	}

	public String getHeader() {
		String header = "Place;Beta;Alpha";
		return header;
	}

	public List<String> getFunctions() {
		List<String> functions = new ArrayList<String>();

		Iterator<String> keys = estParam.keySet().iterator();
		while (keys.hasNext()) {
			String p = keys.next();
			String function = p + ";" + this.estParam.get(p).beta + ";" + this.estParam.get(p).parameter;
			functions.add(function);
		}
		return functions;
	}

	public void exportToFile(PluginContext context, File file) throws IOException {
		GraphLayoutConnection layout;

		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
		String text = getHeader() + "\n";
		bw.write(text);

		List<String> functions = this.getFunctions();
		for (String sample : functions) {
			bw.write(sample + "\n");
		}

		bw.close();
	}
}
