package ar.edu.itba.it.paw.web;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MapBag<T> implements Bag<T> {

	private Map<T, Integer> map = new HashMap<T, Integer>();

	@Override
	public void add(T obj) {
		if (map.containsKey(obj)) {
			map.put(obj, map.get(obj) + 1);
		} else {
			map.put(obj, 1);
		}
	}

	@Override
	public int size() {
		Set<T> keys = map.keySet();
		int ans = 0;
		for (T t : keys) {
			ans += map.get(t);
		}
		return ans;
	}

	@Override
	public int getRepetitions(T obj) {
		if (map.containsKey(obj)) {
			return map.get(obj);
		} else {
			return 0;
		}
	}

	@Override
	public void remove(T obj) {
		if (map.containsKey(obj)) {
			Integer value = map.get(obj);
			if (value == 1) {
				map.remove(obj);
			} else {
				map.put(obj, value - 1);
			}
		}
	}

	@Override
	public void add(Iterable<T> iterable) {
		for (T t : iterable) {
			this.add(t);
		}
	}

	@Override
	public int getNOrGreaterMatching(int n, List<T> ans) {
		Set<T> keys = map.keySet();
		Set<T> remove = new HashSet<T>();
		int i = 0;
		for (T t : keys) {
			if (map.get(t) >= n) {
				ans.add(t);
				remove.add(t);
			}
			i++;
		}
		for (T t : remove) {
			removeAll(t);
		}
		return i;
	}

	@Override
	public void removeAll(T obj) {
		if (map.containsKey(obj)) {
			map.remove(obj);
		}
	}
}
