package it.polito.tdp.PremierLeague.model;

public class GiocatoreTop {
	private Player p1;
	private Double eff;
	public GiocatoreTop(Player p1, Double eff) {
		super();
		this.p1 = p1;
		this.eff = eff;
	}
	public Player getP1() {
		return p1;
	}
	public void setP1(Player p1) {
		this.p1 = p1;
	}
	public Double getEff() {
		return eff;
	}
	public void setEff(Double eff) {
		this.eff = eff;
	}
	@Override
	public String toString() {
		return "GiocatoreTop [p1=" + p1 + ", eff=" + eff + "]";
	}
	
	
	
}
