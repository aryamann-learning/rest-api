package com.example.demo.dto;

public class FlightFuelTimeDetailsDto {
	private FuelTime destination;
	private FuelTime alternate;
	private FuelTime reserve;
	private FuelTime melFuel;
	private FuelTime cont;
	private FuelTime rqr;
	private FuelTime rpf;
	private FuelTime capt;
	private FuelTime other;
	private FuelTime tkof;
	private FuelTime taxi;
	private FuelTime total;
	private FuelTime fod;

	public FuelTime getDestination() {
		return destination;
	}

	public void setDestination(FuelTime destination) {
		this.destination = destination;
	}

	public FuelTime getAlternate() {
		return alternate;
	}

	public void setAlternate(FuelTime alternate) {
		this.alternate = alternate;
	}

	public FuelTime getReserve() {
		return reserve;
	}

	public void setReserve(FuelTime reserve) {
		this.reserve = reserve;
	}

	public FuelTime getMelFuel() {
		return melFuel;
	}

	public void setMelFuel(FuelTime melFuel) {
		this.melFuel = melFuel;
	}

	public FuelTime getCont() {
		return cont;
	}

	public void setCont(FuelTime cont) {
		this.cont = cont;
	}

	public FuelTime getRqr() {
		return rqr;
	}

	public void setRqr(FuelTime rqr) {
		this.rqr = rqr;
	}

	public FuelTime getRpf() {
		return rpf;
	}

	public void setRpf(FuelTime rpf) {
		this.rpf = rpf;
	}

	public FuelTime getCapt() {
		return capt;
	}

	public void setCapt(FuelTime capt) {
		this.capt = capt;
	}

	public FuelTime getOther() {
		return other;
	}

	public void setOther(FuelTime other) {
		this.other = other;
	}

	public FuelTime getTkof() {
		return tkof;
	}

	public void setTkof(FuelTime tkof) {
		this.tkof = tkof;
	}

	public FuelTime getTaxi() {
		return taxi;
	}

	public void setTaxi(FuelTime taxi) {
		this.taxi = taxi;
	}

	public FuelTime getTotal() {
		return total;
	}

	public void setTotal(FuelTime total) {
		this.total = total;
	}

	public FuelTime getFod() {
		return fod;
	}

	public void setFod(FuelTime fod) {
		this.fod = fod;
	}

}
