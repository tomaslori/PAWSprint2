package ar.edu.itba.it.paw.web;

import java.util.List;

public interface Bag<T> {

	public void add(T obj);

	public int size();

	public int getRepetitions(T obj);

	public void remove(T obj);

	public void add(Iterable<T> iterable);

	public int getNOrGreaterMatching(int n, List<T> ans);
	
	public void removeAll(T obj);
}
