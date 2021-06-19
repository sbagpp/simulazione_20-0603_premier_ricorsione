package it.polito.tdp.PremierLeague.model;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

public class Model {
	
	private PremierLeagueDAO dao;
	private SimpleDirectedWeightedGraph<Player, DefaultWeightedEdge> grafo;
	private Map<Integer, Player> idMap;
	
	public Model() {
		this.dao = new PremierLeagueDAO();
		this.idMap = new HashMap<>();
		this.dao.listAllPlayers(this.idMap);
	}
	
	public void CreaGrafo(Match m) {
		this.grafo = new SimpleDirectedWeightedGraph<Player, DefaultWeightedEdge> (DefaultWeightedEdge.class);
		Graphs.addAllVertices(this.grafo, this.dao.getVertex(m, this.idMap));
		List<Adiacenza> ad = this.dao.getAdiacenze(m, idMap);
		for(Adiacenza a  : ad) {
			if(a.getPeso()>=0) {
				if(grafo.containsVertex(a.getP1())&&grafo.containsVertex(a.getP2())) {
					Graphs.addEdge(this.grafo, a.getP1(), a.getP2(), a.getPeso());
				}
			}else {
				Graphs.addEdge(this.grafo, a.getP2(), a.getP1(), (a.getPeso()*(-1)));
			}
		}
		
	}
	
	public String infoGrafo(){
		String info = "Grafo Creato \nVertici = "+this.grafo.vertexSet().size()+"\nArchi = "+this.grafo.edgeSet().size();
		return info;
	}
	
	
	public List<Match> getMatch(){
		List<Match> r = this.dao.listAllMatches();
		Collections.sort(r);
		return r;
	}
	

	public GiocatoreTop getTop() {
		if(this.grafo==null) {
			return null;
		}
		
		Player best = null;
		Double max = null;
		for(Player p : this.grafo.vertexSet()) {
			Double delta = detDelta(p);
			if(max == null || delta > max) {
				best = p;
				max = delta;
			}
		}
		return new GiocatoreTop(best, max);
	}

	private Double detDelta(Player p) {
		Double delta = 0.0;
		for(DefaultWeightedEdge ed : this.grafo.outgoingEdgesOf(p)) {
			delta+=this.grafo.getEdgeWeight(ed);
		}
		for(DefaultWeightedEdge ed : this.grafo.incomingEdgesOf(p)) {
			delta-=this.grafo.getEdgeWeight(ed);
		}
		return delta;
	}
	
	
}
