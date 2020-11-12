import java.util.Iterator;

public interface SNIterator<E> extends Iterator<E> {

    public Node<E> nextNode();

}
