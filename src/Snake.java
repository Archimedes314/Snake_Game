import java.util.*;

enum Direction{
	RIGHT,
	LEFT,
	ABOVE,
	BELOW
}


class Node<E>{
	
	Direction link;
	Node<E> neighbor;
	E element;


	Node(Direction link,Node neighbor ,E element){
		this.link=link;
		this.neighbor=neighbor;
		this.element=element;
	}

	public Direction getOppositeDirectionTo(){
		Direction dir=link;
		return getDirection(dir);


	}
	public static Direction getOppositeDirectionOf(Direction inputDirec){
		return getDirection(inputDirec);


	}

	private static Direction getDirection(Direction inputDirec) {
		switch (inputDirec){
			case LEFT:
				return Direction.RIGHT;
			case ABOVE:
				return Direction.BELOW;
			case RIGHT:
				return Direction.LEFT;
			case BELOW:
				return Direction.ABOVE;
			default:
				return null;

		}
	}

	public static Direction ConvertNumberToDirection(int n) throws IllegalDirectionException {

		switch (n){
			case 0:
				 return Direction.LEFT;
			case 1:
				 return Direction.RIGHT;
			case 2:
				 return Direction.ABOVE;
			case 3:
				 return Direction.BELOW;
			default:
				throw new IllegalDirectionException();

		}


	}



	//provide an appropriate constructor
}

public class Snake<E> implements Iterable<E> {

	Node<E> head;
	Node<E> tail;
	int size;
	Random rng;
	  
	public Snake(long seed){
		rng = new Random(seed);
		head = null;
		tail = null;
		size = 0;
	}

	public void add(E e, int dir) throws IllegalDirectionException {




		if(size==0){
			head=tail= new Node<>(Node.ConvertNumberToDirection(dir), null,e);
			size++;


		}
		else{
			Direction newDirec=Node.ConvertNumberToDirection(dir);
			if(newDirec==tail.getOppositeDirectionTo()){
				throw new IllegalDirectionException();
			}
			else {
				Node temp=tail;
				tail=new Node<E>(newDirec,null,e);
				temp.neighbor=tail;
				size++;
			}




		}
		//complete the code
	}

	public Node<E> get(int index){
		if(index>size-1||index<0){
			throw new IndexOutOfBoundsException();
		}
		else{
			Node<E> temp=head;
			for(int i=0;i<index;i++){
				temp=temp.neighbor;

			}
			return temp;
		}

	}


	public void add(E e) throws IllegalDirectionException {
		if(size==0){
			head=tail= new Node<>(Node.ConvertNumberToDirection(rng.nextInt(4)), null,e);
			size++;


		}
		else{


			Direction newDirec= Node.ConvertNumberToDirection(rng.nextInt(4));
			while (newDirec==tail.getOppositeDirectionTo()){
				newDirec= Node.ConvertNumberToDirection(rng.nextInt(4));

			}

			Node temp=tail;
			tail=new Node<E>(newDirec,null,e);
			temp.neighbor=tail;
			size++;
		}














		//complete the code
	}
	public void set(int index, E NewElement){
		if(index>size-1||index<0){
			throw new IndexOutOfBoundsException();
		}
		else{
			Node<E> temp=head;
			for(int i=0;i<index;i++){
				temp=temp.neighbor;

			}
			temp.element=NewElement;
		}


	}


	public SNIterator<E> SNIterator() {
		return new SnakeIterator<E>(this);
	}

	@Override
	public Iterator<E> iterator() {
		return new SnakeIterator<E>(this);
	}


	private class SnakeIterator<E> implements SNIterator<E> {

		Node<E> current;
		int index=-1;


		SnakeIterator(Snake<E> Sn){
			current=Sn.head;

		}
		@Override
		public boolean hasNext() {

			return current!=null;
		}

		public Node<E> nextNode(){
			Node<E> ce=current;
			current=current.neighbor;
			index++;

			return ce;
		}
		@Override
		public E next() {
		    E ce=current.element;
		    current=current.neighbor;
		    index++;

		    return ce;
		}

		@Override
		public void remove() {

			if(index==0){
				head=head.neighbor;
				current= (Node<E>) head;
				size--;

			}
			else if(index==size-1){


				Node temp= head;
				while(temp.neighbor!=tail){
					temp=temp.neighbor;
				}
				temp.neighbor=null;
				tail=temp;


				size--;
			}
			else{


				Node temp=head;
				while(temp.neighbor.neighbor!=current){
					temp=temp.neighbor;
				}
				Direction reStartDir=temp.neighbor.link;
				temp.neighbor=current;
				current=current.neighbor;
				temp=temp.neighbor;
				while (temp!=null){
					Direction tempDir=temp.link;
					temp.link=reStartDir;
					reStartDir=tempDir;
					temp=temp.neighbor;

				}
				size--;

			}


		}
	}


	//Do not forget to implement the Iterable interface	

}



