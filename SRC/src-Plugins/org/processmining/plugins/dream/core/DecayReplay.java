package org.processmining.plugins.dream.core;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.deckfour.xes.model.XLog;
import org.processmining.acceptingpetrinet.models.AcceptingPetriNet;
import org.processmining.plugins.dream.core.log.EventLog;
import org.processmining.plugins.dream.core.log.EventLogParser;
import org.processmining.plugins.dream.core.petrinet.PetriNet;
import org.processmining.plugins.dream.core.petrinet.Place;
import org.processmining.plugins.dream.core.pnmetrics.TimeDecayReplayer;
import org.processmining.plugins.dream.core.pnmetrics.decay.DecayParameterEstimator;
import org.processmining.plugins.dream.core.pnmetrics.decay.EstimationStatistic;
import org.processmining.plugins.dream.core.pntools.PromPetriNetParser;

/**
 * @author Julian Theis
 *
 */
public class DecayReplay {

	public static TimedStateSamples linearDecayMinerEndToEnd(Dataset dataset, XLog xlog, AcceptingPetriNet uploadedPnml) {
		TimedStateSamples tss = null;
		try {			
			EventLog log = new EventLogParser().getEventLogFromXLog(xlog);
			PromPetriNetParser pnParser = new PromPetriNetParser();
			PetriNet petriNet = pnParser.getPetriNetFromAcceptingPetriNet(uploadedPnml);

			// Decay Dataset
			DecayParameterEstimator decayParamEst = new DecayParameterEstimator(petriNet.getTransitions(), log);
			Map<Place, EstimationStatistic> estParam = decayParamEst.getEstimatedParameters();
			
			/*
			Iterator<Place> iter = estParam.keySet().iterator();
			while(iter.hasNext()) {
				Place key = iter.next();
				System.out.println(key.name() + ": " + estParam.get(key).parameter);
			}
			*/
			
			TimeDecayReplayer replayer = new TimeDecayReplayer("prom-dream-plugin", petriNet, log, 0, estParam,
					dataset.metadata.get("timebase"));
			replayer.replayLog();
			
			tss = replayer.createTimedStateSamples();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return tss;
	}
	
	public static TimedStateSamples linearDecayMiner(Dataset dataset, XLog xlog, AcceptingPetriNet uploadedPnml, DecayFunctions df) {
		TimedStateSamples tss = null;
		try {			
			EventLog log = new EventLogParser().getEventLogFromXLog(xlog);
			PromPetriNetParser pnParser = new PromPetriNetParser();
			PetriNet petriNet = pnParser.getPetriNetFromAcceptingPetriNet(uploadedPnml);

			// Decay Dataset
			Map<String, EstimationStatistic> estParam = df.getEstimatedParameter();
			Map<Place, EstimationStatistic> estParamPlaces = new HashMap<Place, EstimationStatistic>();
			Iterator<String> iter = estParam.keySet().iterator();
			while(iter.hasNext()) {
				String placeId = iter.next();
				Place p = petriNet.getPlace(placeId);
				estParamPlaces.put(p, estParam.get(placeId));
			}
			
			TimeDecayReplayer replayer = new TimeDecayReplayer("prom-dream-plugin", petriNet, log, 0, estParamPlaces,
					dataset.metadata.get("timebase"));
			replayer.replayLog();
			
			tss = replayer.createTimedStateSamples();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return tss;
	}
	
	public static DecayFunctions linearDecayEnhancer(Dataset dataset, XLog xlog, AcceptingPetriNet uploadedPnml) {
		DecayFunctions df = new DecayFunctions();
		try {			
			EventLog log = new EventLogParser().getEventLogFromXLog(xlog);
			PromPetriNetParser pnParser = new PromPetriNetParser();
			PetriNet petriNet = pnParser.getPetriNetFromAcceptingPetriNet(uploadedPnml);

			// Decay Dataset
			DecayParameterEstimator decayParamEst = new DecayParameterEstimator(petriNet.getTransitions(), log);
			Map<Place, EstimationStatistic> estParam = decayParamEst.getEstimatedParameters();
			df.setEstimatedParamPlace(estParam);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return df;
	}

}
