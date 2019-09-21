package org.processmining.plugins.dream.core.petrinet;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static java.util.Collections.addAll;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Place implements Serializable {
	public static final Place NULL = new Place("null", 0);

	private String name;
	private Set<Transition> inputs = new HashSet<>();
	private Set<Transition> outputs = new HashSet<>();
	private int tokens;
	private int initTokens;
	
	private int finalTokens;

	public Place(String name, int initTokens) {
		this.name = name;
		this.initTokens = initTokens;
		this.finalTokens = -1;
	}
	
	
	public void replaceInputTransition2TimedTransition(Transition t, TimedTransition tt){
		inputs.remove(t);
		inputs.add(tt);
	}
	
	public void replaceOutputTransition2TimedTransition(Transition t, TimedTransition tt){
		outputs.remove(t);
		outputs.add(tt);
	}
	
	public boolean hasInputTransition(Transition t){
		return inputs.contains(t);
	}
	
	public boolean hasOutputTransition(Transition t){
		return outputs.contains(t);
	}
	
	public void setFinalTokens(int tokens){
		this.finalTokens = tokens;
	}
	
	public boolean hasFinalMarking(){
		if(this.finalTokens > 0){
			return true;
		}else{
			return false;
		}
	}
	
	public int getFinalMarking(){
		return this.finalTokens;
	}
	
	public boolean hasInitMarking(){
		if(this.initTokens > 0){
			return true;
		}else{
			return false;
		}
	}
	
	public int initTokens(){
		return this.initTokens;
	}
	
	public void setName(String name){
		this.name = name;
	}

	public String name() {
		return name;
	}


	public Place from(Transition... transitions) {
		addAll(inputs, transitions);
		return this;
	}

	public Place to(Transition... transitions) {
		addAll(outputs, transitions);
		return this;
	}

	public boolean hasZeroInputs() {
		return inputs.isEmpty();
	}

	public boolean hasZeroOutputs() {
		return outputs.isEmpty();
	}
	
	public  Set<Transition> getInputs() {
		return inputs;
	}
	
	public  Set<Transition> getOutputs() {
		return outputs;
	}

	public int getOutputCount() {
		return outputs.size();
	}

	public int getTokenCount() {
		return tokens;
	}

	public void addToken() {
		tokens++;
	}
	
	public void addTokens(int tokens){
		this.tokens += tokens;
	}

	public boolean hasTokens() {
		return tokens > 0;
	}

	public void removeToken() {
		tokens--;
	}
	
	public void removeTokens(int tokens) {
		this.tokens-= tokens;
	}

	public void removeAllTokens() {
		tokens = 0;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Place place = (Place) o;
		return Objects.equals(name, place.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

	@Override
	public String toString() {
		return inputs + "->" + name + "(" + tokens + ")->" + outputs;
	}
}

