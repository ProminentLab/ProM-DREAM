package org.processmining.plugins.dream.core.pnmetrics.decay;

import java.io.Serializable;

import org.apache.log4j.Logger;

public class EstimationStatistic implements Serializable {
	private static final long serialVersionUID = -3668192013595867436L;
	final static Logger logger = Logger.getLogger(EstimationStatistic.class);
	
	public long min;
	public long max;
	public double mean;
	public double stddev;
	public double parameter;
	public double beta;
	
	public EstimationStatistic(){
		this.beta = 10;
	}
	
	public void calculateParameter(){
		if(mean != -1.0){
			parameter = this.beta/(mean);//-stddev); //+(stddev*stddev));
			logger.info("Estimated Parameter: " + parameter);
		}else{
			parameter = this.beta/(((double) max)); ///2.0);
			logger.info("Estimated Parameter: " + parameter);
		}
	}
}
