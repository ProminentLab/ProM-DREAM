package org.processmining.plugins.dream.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.processmining.plugins.dream.core.pnmetrics.util.Constants;

/**
 * @author Julian Theis
 *
 */
public class Dataset {

	public Map<String, Integer> resources;
	public Map<String, String> metadata;

	public Dataset(String[] headers, String fileName, String timestamp_format, String is_long,
			List<Integer> selected_resources) {
		this.setUpDatasetWithResources(headers, fileName, timestamp_format, is_long, selected_resources);
	}

	private void setUpDatasetWithResources(String[] headers, String fileName, String timestamp_format, String is_long,
			List<Integer> selected_resources) {

		metadata = new HashMap<String, String>();
		metadata.put("isLong", is_long);
		metadata.put("isTimestamp", "true");
		metadata.put("timestampFormat", timestamp_format);
		metadata.put("ordering", Constants.ORDERING_BYCASEIDASC);
		metadata.put("timebase", Constants.DECAYVECTORPREDICTION_TIMEBASE_T0);
		metadata.put("numResources", String.valueOf(selected_resources.size()));
		metadata.put("excludeResources", "");

		// set resources
		resources = new HashMap<String, Integer>();
		int resource_number = 1;
		for (int i : selected_resources) {
			resources.put("Resource" + String.valueOf(resource_number), i);
			resource_number++;
		}

	}
}
