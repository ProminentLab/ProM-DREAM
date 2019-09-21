package org.processmining.plugins.dream.core.pntools;


import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.processmining.acceptingpetrinet.models.AcceptingPetriNet;
import org.processmining.models.graphbased.directed.petrinet.PetrinetEdge;
import org.processmining.models.graphbased.directed.petrinet.PetrinetNode;
import org.processmining.models.graphbased.directed.petrinet.elements.Place;
import org.processmining.models.graphbased.directed.petrinet.elements.Transition;
import org.processmining.plugins.dream.core.petrinet.PetriNet;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;



public class PromPetriNetParser {
	private PetriNet petriNet;
	private Document doc;

	/*
	public PetriNet getPetriNetFromFile(String fileName) {
		
		File f = new File (fileName);
		
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(f);
		}catch(Exception e){
			System.out.println(e);
		}
		
		
		

		PnmlImportUtils pnmlImportUtils = new PnmlImportUtils();

		InputStream input = null;
		try {
			input = new FileInputStream(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Pnml pnml = null;
		try {
			pnml = pnmlImportUtils.importPnmlFromStream(null, input, f.getName(), f.length());
		} catch (Exception e) {
			e.printStackTrace();
		}
		PetrinetGraph net = PetrinetFactory.newInhibitorNet(pnml.getLabel() + " (imported from " + f.getName() + ")");
		Marking marking = new Marking();
		pnml.convertToNet(net, marking, new GraphLayoutConnection(net));

		petriNet = new PetriNet();

		Iterator<Place> iter = marking.iterator();
		Map<String, Integer> initMarking = new HashMap<String, Integer>();
		Map<String, Integer> finalMarking = finalMarking();
		
		
		while(iter.hasNext()){
			Place p = iter.next();
			initMarking.put(p.getLabel(), initMarkingOfPlace(p.getLabel()));
		}
		
		for (Place place : net.getPlaces()) {
			org.processmining.plugins.dream.core.petrinet.Place p = null;
			if(initMarking.containsKey(place.getLabel())){
				p = new org.processmining.plugins.dream.core.petrinet.Place(place.getLabel(), initMarking.get(place.getLabel()));
			}else{
				p = new org.processmining.plugins.dream.core.petrinet.Place(place.getLabel(), 0);
			}
			
			System.out.println(place.getLabel());
			
			if(finalMarking.containsKey(place.getLabel())){
				System.out.println("FinalTokens" + finalMarking.get(place.getLabel()));
				p.setFinalTokens(finalMarking.get(place.getLabel()));
			}
			petriNet.addPlace(p);
		}

		for (Transition transition : net.getTransitions()) {
			//boolean visible = true;
			//if(transition.isInvisible())
			//	visible = false;
			
			boolean visible = transitionVisible(transition);
			//System.out.println("ACHTUNG ACHTUNG ACHTUNG - UNTERSCHIEDLICH FUER CONF PAPER IM PETRINETPARSER!!!!");
			//System.out.println(transition.getLabel() + " " + visible);
			visible = true;
			
			org.processmining.plugins.dream.core.petrinet.Transition t = new org.processmining.plugins.dream.core.petrinet.Transition(transition.getLabel(), visible);
			petriNet.addTransition(t);
		}

		for (PetrinetEdge<? extends PetrinetNode, ? extends PetrinetNode> edge : net.getEdges()) {
			String fromName = edge.getSource().getLabel();
			String toName = edge.getTarget().getLabel();

			org.processmining.plugins.dream.core.petrinet.Transition fromTransition = petriNet.getTransition(fromName);
			org.processmining.plugins.dream.core.petrinet.Place toPlace = petriNet.getPlace(toName);

			fromTransition.to(toPlace);
			toPlace.from(fromTransition);

			org.processmining.plugins.dream.core.petrinet.Place fromPlace = petriNet.getPlace(fromName);
			org.processmining.plugins.dream.core.petrinet.Transition toTransition = petriNet.getTransition(toName);

			fromPlace.to(toTransition);
			toTransition.from(fromPlace);
		}		

		return petriNet;
	}
	*/
	
	
	public PetriNet getPetriNetFromAcceptingPetriNet(AcceptingPetriNet apn) {
		petriNet = new PetriNet();
		
		Iterator<Place> iter = apn.getInitialMarking().iterator();
		Map<String, Integer> initMarking = new HashMap<String, Integer>();
		while (iter.hasNext()) {
			Place p = iter.next();			
			initMarking.put(p.getId().toString(), 1);
		}
		
		iter = apn.getFinalMarkings().iterator().next().iterator();
		Map<String, Integer> finalMarking = new HashMap<String, Integer>();
		while (iter.hasNext()) {
			Place p = iter.next();			
			finalMarking.put(p.getId().toString(), 1);
		}
	

		for (Place place : apn.getNet().getPlaces()) {
			org.processmining.plugins.dream.core.petrinet.Place p = null;
			System.out.println("Label: " + place.getLabel() + " -- ID: " + place.getId().toString());
			
			if (initMarking.containsKey(place.getId().toString())) {
				p = new org.processmining.plugins.dream.core.petrinet.Place(place.getId().toString(),
						initMarking.get(place.getId().toString()));
			} else {
				p = new org.processmining.plugins.dream.core.petrinet.Place(place.getId().toString(), 0);
			}

			if (finalMarking.containsKey(place.getId().toString())) {
				p.setFinalTokens(finalMarking.get(place.getId().toString()));
			}
			petriNet.addPlace(p);
		}

		for (Transition transition : apn.getNet().getTransitions()) {
			boolean visible = true;
			if(transition.isInvisible()) 
				visible = false;
	
			org.processmining.plugins.dream.core.petrinet.Transition t = new org.processmining.plugins.dream.core.petrinet.Transition(
					transition.getLabel(), visible);
			petriNet.addTransition(t);

		}

		for (PetrinetEdge<? extends PetrinetNode, ? extends PetrinetNode> edge : apn.getNet().getEdges()) {
			String fromNamePlace = edge.getSource().getId().toString();
			String toNameTransition = edge.getTarget().getLabel();

			org.processmining.plugins.dream.core.petrinet.Place fromPlace = petriNet.getPlace(fromNamePlace);
			org.processmining.plugins.dream.core.petrinet.Transition toTransition = petriNet.getTransition(toNameTransition);

			fromPlace.to(toTransition);
			toTransition.from(fromPlace);
			
			
			String fromNameTransition = edge.getSource().getLabel();
			String toNamePlace = edge.getTarget().getId().toString();

			org.processmining.plugins.dream.core.petrinet.Transition fromTransition = petriNet
					.getTransition(fromNameTransition);
			org.processmining.plugins.dream.core.petrinet.Place toPlace = petriNet.getPlace(toNamePlace);

			fromTransition.to(toPlace);
			toPlace.from(fromTransition);
		}

		return petriNet;
	}
	
	private int initMarkingOfPlace(String place){
		int markings = 0;
		NodeList places = doc.getElementsByTagName("place");
		for (int i = 0; i < places.getLength(); i++) {
			Node item = places.item(i);
			
			if (item.getAttributes().getNamedItem("id").toString().equals("id=\"" + place + "\"")){
				if(item.getChildNodes().getLength() > 0){
					try{
						markings = Integer.parseInt(item.getChildNodes().item(0).getChildNodes().item(0).getFirstChild().getNodeValue());
					}catch(Exception e){
						markings = 1;
					}
				}
				break;
			}
		}
		return markings;
	}
	
	private Map<String, Integer> finalMarking(){
		Map<String, Integer> finalMarking = new HashMap<String, Integer>();
		Node finalMarkings = doc.getElementsByTagName("finalmarkings").item(0);
		
		if(finalMarkings.hasChildNodes()){
			Node markings = finalMarkings.getFirstChild();
			System.out.println(markings.toString());
			for (int i = 0; i < markings.getChildNodes().getLength(); i++) {
				Node marking = markings.getChildNodes().item(i);
				String place = marking.getAttributes().getNamedItem("idref").getNodeValue();
				System.out.println(place + "***");
				int value = 0;
				try{
					value = Integer.parseInt(marking.getFirstChild().getFirstChild().getNodeValue());
				}catch(Exception e){
					value = 1;
				}
				finalMarking.put(place, value);
			}
		}
		return finalMarking;
	}
	
	
	
	private boolean transitionVisible(Transition t){
		boolean visible = true;
		
		String label = t.getLabel();
		
		boolean isT = false;
		if(label.length() >= 1){
			isT = label.substring(0, 1).equals("t");
		}
		
		boolean isNum;
		try{
			Integer.parseInt(label.substring(1));
			isNum = true;
		}catch(Exception e){
			isNum = false;
		}
		
		if(isT || isNum)
			visible = false;
		
		return visible;
	}
}
