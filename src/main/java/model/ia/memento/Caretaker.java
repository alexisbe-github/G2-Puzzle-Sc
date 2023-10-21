package main.java.model.ia.memento;

import java.util.ArrayList;
import java.util.List;

import main.java.model.Puzzle.Memento;

public class Caretaker {

	private List<Memento> puzzlesMemento;
	
	public Caretaker() {
		puzzlesMemento = new ArrayList<>();
	}
	
	public void addMemento(Memento puzzle) {
		puzzlesMemento.add(puzzle);
	}
	
	public Memento getMemento(int index) {
		return puzzlesMemento.get(index);
	}
	
}
