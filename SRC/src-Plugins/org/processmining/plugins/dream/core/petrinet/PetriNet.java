package org.processmining.plugins.dream.core.petrinet;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toMap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections15.IteratorUtils;

import com.google.common.base.MoreObjects;

@SuppressWarnings("serial")
public class PetriNet implements Cloneable, Serializable {
	
	private Map<String, String> placeIds_new2old = new HashMap<>();
	private Map<String, String> placeIds_old2new = new HashMap<>();
	private Map<String, String> transIds_new2old = new HashMap<>();
	private Map<String, String> transIds_old2new = new HashMap<>();
	
	private Map<String, Place> places = new HashMap<>();
	private Map<String, Transition> transitions = new HashMap<>();

	
	public Map<String, String> getPlaceIds_new2old(){
		return this.placeIds_new2old;
	}
	public Map<String, String>  getPlaceIds_old2new(){
		return this.placeIds_old2new;
	}
	public Map<String, String>  getTransIds_new2old(){
		return this.transIds_new2old;
	}
	public Map<String, String>  getTransIds_old2new(){
		return this.transIds_old2new;
	}
	
	public void setPlaceIds_new2old(Map<String, String> set){
		this.placeIds_new2old = set;
	}
	public void setPlaceIds_old2new(Map<String, String> set){
		this.placeIds_old2new = set;
	}
	public void setTransIds_new2old(Map<String, String> set){
		this.transIds_new2old = set;
	}
	public void setTransIds_old2new(Map<String, String> set){
		this.transIds_old2new = set;
	}
	
	public void updateTransition2TimedTransition(Transition t, TimedTransition timedTrans){
		transitions.put(timedTrans.name(), timedTrans);
		for(Place inp : timedTrans.getInputs()){
			if(inp.hasInputTransition(t))
				inp.replaceInputTransition2TimedTransition(t, timedTrans);
			if(inp.hasOutputTransition(t))
				inp.replaceOutputTransition2TimedTransition(t, timedTrans);
		}
	}
	
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public void addPlace(Place... places) {
		this.places.putAll(stream(places).collect(toMap(Place::name, place -> place)));
	}

	public void addTransition(Transition... transitions) {
		this.transitions.putAll(stream(transitions).collect(toMap(Transition::name, transition -> transition)));
	}

	/*
	public Place start() {
		return places.values().stream().filter(Place::hasInitMarking).findFirst().get();
	}
	
	public Place end() {
		return places.values().stream().filter(Place::hasFinalMarking).findFirst().get();
	}
	*/
	
	public List<Place> initialMarking() {
		Iterator<Place> iter = places.values().stream().filter(Place::hasInitMarking).iterator();
		List<Place> myList = IteratorUtils.toList(iter);  
		return myList;
	}

	public List<Place> finalMarking() {
		Iterator<Place> iter = places.values().stream().filter(Place::hasFinalMarking).iterator();
		List<Place> myList = IteratorUtils.toList(iter);  
		return myList;
	}

	public int countRemainingTokens() {
		return places.values().stream().parallel().mapToInt(Place::getTokenCount).sum();
	}

	public void cleanUpRemainingTokens() {
		places.values().parallelStream().forEach(Place::removeAllTokens);
	}

	public void removeToken(Place p) {
		p.removeToken();
	}
	
	public void removeTokens(Place p, int num) {
		p.removeTokens(num);
	}
	
	public void removeTokens(Set<Place> ps) {
		ps.parallelStream().forEach((p) -> p.removeToken());
		//for(Place p : ps)
		//	p.removeToken();
	}


	public List<Place> getPlaces() {
		return places.values().stream().collect(Collectors.toList());
	}

	public List<Transition> getTransitions() {
		return transitions.values().stream().collect(Collectors.toList());
	}

	/* Required for TraceSimulator */
	public List<Transition> enabledTransitions() {		
		List<Place> x = places.values().stream().filter(Place::hasTokens).collect(Collectors.toList());
		List<Transition> enabledTransitions = new ArrayList<Transition>();
		for (Place p : x) {
			Iterator<Transition> outputs = p.getOutputs().iterator();
			while (outputs.hasNext()) {
				Transition trans = outputs.next();
				if (trans.hasAllInputTokens()) {
					enabledTransitions.add(trans);
				}
			}
		}
		return enabledTransitions;
	}
	

	/** JULIAN **/
	public List<Place> getPlacesWithToken() {
		return places.values().stream().filter(Place::hasTokens).collect(Collectors.toList());
	}
	
	public Map<Place,Integer> getPlacesAndTokens() {
		Map<Place, Integer> place2tokenMap = new HashMap<Place, Integer>();
		places.values().stream().forEach(p ->{
			if(p.hasTokens()){
				place2tokenMap.put(p, p.getTokenCount());
			}
		});
		return place2tokenMap;
	}

	/** JULIAN **/
	public void measureDistance(Place a, Transition t) {
	}

	public int countEnabledTransitions() {
		return places.values().stream().filter(Place::hasTokens).mapToInt(Place::getOutputCount).sum();
	}

	public int countTransitions() {
		return transitions.size();
	}

	public int countPlaces() {
		return places.size();
	}
	
	

	public void addStartToken() {
		places.values().parallelStream().filter(Place::hasInitMarking).forEach((p)-> addToken(p));
		//for(Place p : initialMarking()){
		//	addToken(p);
		//}
	}
	

	public void addToken(Place p) {
		p.addToken();
	}
		
	public void addTokens(Set<Place> ps) {
		ps.parallelStream().forEach((p)-> p.addToken());
		//for(Place p : ps)
		//	p.addToken();
	}

	/*
	public boolean hasEndToken() {
		return end().hasTokens();
	}
	*/
	
	public boolean finalMarkingReached() {
		boolean reached = true;
		Iterator<Place> finalMarkingPlaces = places.values().stream().filter(Place::hasFinalMarking).iterator();
		while(finalMarkingPlaces.hasNext()){
			Place place = finalMarkingPlaces.next();
			if(place.getFinalMarking() > place.getTokenCount()){
				reached = false;
				break;
			}
		}
		
		
		return reached;
	}

	public void removeEndToken() {
		places.values().stream().parallel().filter(Place::hasFinalMarking).forEach((p)-> removeTokens(p, p.getFinalMarking()));
		//for(Place p : finalMarking()){
		//	removeTokens(p, p.getFinalMarking());
		//}
	}

	/**
	 * Add missing end tokens to each final Marking place
	 */
	public void addEndToken() {
		places.values().stream().parallel().filter(Place::hasFinalMarking).forEach((p)-> p.addTokens((p.getFinalMarking() - p.getTokenCount())));
	}
	
	/**
	 * Returns the total number of final marking tokens required for a succesful run
	 * @return total number of final tokens required for a succesfull run
	 */
	public int finalMarkingTokens(){
		/*
		int i = 0;
		for(Place p : finalMarking()){
			if(p.getFinalMarking() > 0)
				i += p.getFinalMarking();
		}
		System.out.println("Final Marking Tokens:" + i + " " + val);
		*/
		return places.values().parallelStream().filter(Place::hasFinalMarking).mapToInt(Place::getFinalMarking).sum();
	}
	
	public int tokensInFinalMarkingPosition(){
		/*
		int i = 0;
		for(Place p : finalMarking()){
			if(p.getTokenCount() > 0)
				i += p.getTokenCount();
		}
		int val = 
		System.out.println("Token Count: " + i + " " + val);
		*/
		return places.values().parallelStream().filter(Place::hasFinalMarking).mapToInt(Place::getTokenCount).sum();
	}

	public Transition getTransition(String name) {
		return transitions.get(name) != null ? transitions.get(name) : Transition.NULL;
	}

	public Place getPlace(String name) {
		return places.get(name) != null ? places.get(name) : Place.NULL;
	}
	
	public boolean placesHaveTokens(Set<Place> plcs){
		boolean r = true;
		for(Place p : plcs){
			//System.out.println(p.name());
			if(!p.hasTokens()){
				r = false;
			}
		}
		return r;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("places", places).add("transitions", transitions).toString();
	}

	public void addStartTokens() {
		 Iterator<Place> initMarking = places.values().stream().filter(Place::hasInitMarking).iterator();
		 while(initMarking.hasNext()){
			 Place p = initMarking.next();
			 p.addTokens(p.initTokens());
		 }
	}
	
	public int startTokens(){
		return (int) places.values().stream().filter(Place::hasInitMarking).count();
	}
	
	public void setIdTranslations(Map<String, String> placeIds_new2old, Map<String, String> placeIds_old2new, Map<String, String> transIds_new2old, Map<String, String> transIds_old2new){
		this.placeIds_new2old = placeIds_new2old;
		this.placeIds_old2new = placeIds_old2new;
		this.transIds_new2old = transIds_new2old;
		this.transIds_old2new = transIds_old2new;
	}
	
	public String getState(){
		String state = "{";
		for(Place p : this.getPlaces()){
			state += p.name() + "=" + p.getTokenCount() + " ";
		}
		state += "}";
		return state;
	}
	
	public double[] getMarking(){
		double[] marking = new double[this.getPlaces().size()];
		List<Place> plcs = this.getPlaces();
		for(int i = 0; i<marking.length;i++){
			marking[i] = plcs.get(i).getTokenCount();
		}
		return marking;
	}
}

