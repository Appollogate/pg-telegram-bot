package utility;

import java.util.Objects;

public class Pair<T, U> {

    private T first;
    private U second;

    public Pair(T t, U u) {
        first = t;
        second = u;
    }

    public T getFirst() {
        return first;
    }

    public void setFirst(T first) {
        this.first = first;
    }

    public U getSecond() {
        return second;
    }

    public void setSecond(U second) {
        this.second = second;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof Pair<?, ?>) {
            return ((Pair<?, ?>) obj).first.equals(first) && ((Pair<?, ?>) obj).second.equals(second);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }
}
