package me.fumba.weatherstation.model;

import java.sql.Timestamp;

public class Observation {

	private enum QUALITY_CODE {
		GOOD, ARRIVED_LATE, OUT_OF_RANGE
	};

	private long id;
	private Timestamp observedTimestamp;
	Timestamp recievedTimestamp;
	private double value;
	private QUALITY_CODE qualityCode;
	private long sensorId;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Timestamp getObservedTimestamp() {
		return observedTimestamp;
	}

	public void setObservedTimestamp(Timestamp observedTimestamp) {
		this.observedTimestamp = observedTimestamp;
	}

	public Timestamp getRecievedTimestamp() {
		return recievedTimestamp;
	}

	public void setRecievedTimestamp(Timestamp recievedTimestamp) {
		this.recievedTimestamp = recievedTimestamp;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public QUALITY_CODE getQualityCode() {
		return qualityCode;
	}

	public void setQualityCode(QUALITY_CODE qualityCode) {
		this.qualityCode = qualityCode;
	}

	public long getSensorId() {
		return sensorId;
	}

	public void setSensorId(long sensorId) {
		this.sensorId = sensorId;
	}
}
