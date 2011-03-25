package utilities;
/**
 * Acts as tuple for usage in the binary tree. Comparator implemented 
 * on second item in the pair
 * 
 * @author Nick Barnwell
 *
 * @param <F> The type of the first element
 * @param <S> The type of the second element
 */

public class Pair<F, S extends Comparable<? super S>>
		implements Comparable<Pair<? extends F, ? extends S>> {

	private final F first; //Elements are non-mutable once created
	private final S second;

	public Pair(F ft, S sd) {
		first = ft;
		second = sd;
	}

	public int compareTo(Pair<? extends F, ? extends S> that) {
		int comparator = 0;
		//Ternary operator to to check the comparator and return proper value
		return comparator == 0 ? compare(second, that.second) : comparator;
	}

	private static <T extends Comparable<? super T>> int compare(T left, T right) {
		if (left == null) {
			return right == null ? 0 : -1;
		} else {
			return right == null ? 1 : left.compareTo(right);
		}
	}
	/**
	 * @return First item
	 */
	public F getFirst() {
		return first;
	}
	/**
	 * @return Second item
	 */
	public S getSecond() {
		return second;
	}

	public String toString() {
		return "(1st=" + first + ", 2nd=" + second + ")";
	}
}
