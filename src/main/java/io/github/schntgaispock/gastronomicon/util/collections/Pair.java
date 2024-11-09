package io.github.schntgaispock.gastronomicon.util.collections;

public class Pair<T, U> {

    private T first;
    private U second;

	public Pair(T first, U second) {
		this.first = first;
		this.second = second;
	}
	public T first() { return first; }
    public void first(T first) { this.first = first; }
    public U second() { return second; }
    public void second(U second) { this.second = second; }

    @Override
    public String toString() {
        return "(" + first + ", " + second + ")";
    }

    public static <T, U> Pair<T, U> of(T first, U second) {
        return new Pair<T, U>(first, second);
    }
}
