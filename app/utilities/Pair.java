package utilities;

public class Pair<F, S extends Comparable<? super S>>
		implements Comparable<Pair<? extends F, ? extends S>> {

	private final F first;
	private final S second;

	public Pair(F ft, S sd) {
		this.first = ft;
		this.second = sd;
	}

	public int compareTo(Pair<? extends F, ? extends S> that) {
		//int comparator = compare(first, that.first);
		int comparator = 0;
		return comparator == 0 ? compare(second, that.second) : comparator;
	}

	private static <T extends Comparable<? super T>> int compare(T l, T r) {
		if (l == null) {
			return r == null ? 0 : -1;
		} else {
			return r == null ? 1 : l.compareTo(r);
		}
	}

	public F getFirst() {
		return first;
	}
	
	public S getSecond() {
		return second;
	}

	public String toString() {
		return "(1st=" + first + ", 2nd=" + second + ")";
	}
}
