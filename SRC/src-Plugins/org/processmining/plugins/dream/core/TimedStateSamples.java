package org.processmining.plugins.dream.core;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import org.processmining.framework.plugin.PluginContext;
import org.processmining.models.connections.GraphLayoutConnection;

/**
 * @author Julian Theis
 *
 */
public class TimedStateSamples {

	private List<String> csvSamples;
	
	private int places;
	private int resources;
	
	public TimedStateSamples(int places, int resources) {
		this.places = places;
		this.resources = resources;
		csvSamples = new ArrayList<String>();
	}
	
	public int getSize() {
		return this.csvSamples.size();
	}
	
	public int getSampleLenght() {
		return getHeader().split(";").length;
	}
	
	public String getHeader() {
		String header = "";
		for(int i = 0; i < this.places; i++) {
			header += "F" + i + ";";
		}
		for(int i = 0; i < this.places; i++) {
			header += "C" + i + ";";
		}
		for(int i = 0; i < this.places; i++) {
			header += "M" + i + ";";
		}
		for(int i = 0; i < this.resources; i++) {
			header += "R" + i + ";";
		}
		header += "Next Event;CaseID";
		return header;
	}
	
	
	public void addSample(String sample) {
		this.csvSamples.add(sample);
	}
	
	public List<String> getSamples() {
		return this.csvSamples;
	}
	
	public void exportToFile(PluginContext context, File file) throws IOException {
		GraphLayoutConnection layout;
		
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
		String text = getHeader() + "\n";
		bw.write(text);
		
		for(String sample : this.csvSamples) {
			bw.write(sample + "\n");
		}
		
		bw.close();
	}
}
