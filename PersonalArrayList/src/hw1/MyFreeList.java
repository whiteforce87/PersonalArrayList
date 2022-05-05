package hw1;

import java.io.Serializable;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

public class MyFreeList<E> extends AbstractList<E> implements List<E>, Serializable {

	private static final long serialVersionUID = 1L;

	private static final Object[] EMPTY_DATA = {};

	private int size;

	private Object[] data;

	public MyFreeList() {
		this.data = EMPTY_DATA;
	}

	public MyFreeList(int enteredCapacity) {
		if (enteredCapacity == 0) {
			this.data = EMPTY_DATA;
		} else if (enteredCapacity > 0) {
			this.data = new Object[enteredCapacity];
		} else {
			throw new IllegalArgumentException("You entered a wrong capacity!");
		}
	}

	public MyFreeList(Collection<? extends E> c) {
		Object[] newData = c.toArray();
		if ((size = newData.length) != 0) {
			data = Arrays.copyOf(newData, size, Object[].class);
		} else {
			data = EMPTY_DATA;
		}
	}

	@Override
	public int size() {
		return this.size;
	}

	@Override
	public boolean isEmpty() {
		if (data.length == 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean contains(Object o) {
		for (Object item : data) {
			if (item.equals(o)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public E get(int index) {
		return (E) data[index];
	}

	@Override
	public void clear() {
		this.data = EMPTY_DATA;
		this.size = 0;
	}

	@Override
	public boolean add(E e) {
		add(e, data, size);
		return true;
	}

	@Override
	public void add(int index, E element) {

		Object[] newData = new Object[size + 1];

		if (index != 0) {
			for (int i = 0; i < index; i++) {
				newData[i] = data[i];
			}
			newData[index] = element;
			for (int i = index; i < (data.length); i++) {
				newData[i + 1] = data[i];
			}
			this.data = newData;
		} else if (index == 0) {
			for (int i = index + 1; i < (data.length) + 1; i++) {
				newData[i] = data[i + 1];
			}
			this.data = newData;
		}
		this.data = newData;
		size++;
	}

	private void add(E e, Object[] Data, int capacity) {
		if (capacity == Data.length) {
			Object[] newData = new Object[capacity + 1];

			if (!(data.length == 0)) {
				for (int i = 0; i < data.length; i++) {
					newData[i] = data[i];
					newData[capacity] = e;
				}
			} else {
				newData[capacity] = e;
			}

			this.data = newData;
			size = capacity + 1;
		}
	}

	@Override
	public E remove(int index) {

		Object oldItem = data[index];
		remove(index, data, size);

		return (E) oldItem;
	}

	private void remove(int index, Object[] data, int capacity) {
		if (data[index] != null) {

			Object[] newData = new Object[capacity - 1];

			if (index != 0) {
				for (int i = 0; i < index; i++) {
					newData[i] = data[i];
				}
				for (int i = index; i < (data.length) - 1; i++) {
					newData[i] = data[i + 1];
				}
				this.data = newData;
			} else if (index == 0) {
				for (int i = index; i < (data.length) - 1; i++) {
					newData[i] = data[i + 1];
				}
				this.data = newData;
			}
			size = capacity - 1;
		} else {
			throw new NoSuchElementException();
		}
	}

	@Override
	public boolean remove(Object o) {
		if (contains(o) == true) {
			for (int i = 0; i < data.length; i++) {
				if (data[i].equals(o)) {
					remove(i);
					return true;
				}
			}
		}
		System.out.println("There is no such element to remove!");
		return false;
	}

	@Override
	public int indexOf(Object o) {
		int index = 0;
		for (Object item : data) {
			if (item.equals(o)) {
				break;
			} else {
				index++;
			}
		}
		return index;
	}

	@Override
	public Iterator<E> iterator() {

		return new MyIterator(data);

	}

	public class MyIterator implements Iterator<E> {
		private int current;
		private Object[] items;

		public MyIterator(Object[] items) {
			this.current = 0;
			this.items = items;
		}

		public boolean hasNext() {
			return (current < items.length);
		}

		public E next() {
			return (E) items[current++];
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}
	}

	@Override
	public Object[] toArray() {
		return Arrays.copyOf(data, size);
	}

	@Override
	public <T> T[] toArray(T[] a) {
		if (size > a.length) {
			return (T[]) Arrays.copyOf(data, size, a.getClass());
		}
		System.arraycopy(data, 0, a, 0, size);
		if (a.length > size) {
			a[size] = null;
		}
		return a;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		int count = 0;
		for (int i = 0; i < data.length; i++) {
			for (Object object : c) {
				if (data[i].equals(object)) {
					count++;
				}
			}
		}
		if (count == c.size()) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		int count = 0;
		MyFreeList<E> newData = new MyFreeList<>();
		for (Object item : data) {
			newData.add((E) item);
		}
		for (Object object : c) {
			newData.add((E) object);
			count++;
		}
		data = newData.toArray();
		this.size = data.length;
		if (count == c.size()) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		int count = 0;
		MyFreeList<E> newData = new MyFreeList<>();
		for (int i = 0; i < index; i++) {
			newData.add((E) data[i]);
		}
		for (Object object : c) {
			newData.add((E) object);
			count++;
		}
		for (int i = index; i < data.length; i++) {
			newData.add((E) data[i]);
		}

		data = newData.toArray();
		this.size = data.length;
		if (count == c.size()) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		int count = 0;
		for (Object object : c) {
			for (Object object2 : data) {
				if (contains(object) == true && object.equals(object2)) {
					remove(object);
					count++;
				}
			}
		}
		if (count == c.size()) {
			this.size = 0;
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		int count = 0;
		MyFreeList<E> newData = new MyFreeList<>();
		for (int i = 0; i < data.length; i++) {
			for (Object object : c) {
				if (data[i].equals(object)) {
					newData.add((E) object);
					count++;
				}
			}
		}
		data = newData.toArray();
		this.size = data.length;
		if (count == c.size()) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public E set(int index, E element) {
		this.data[index] = element;
		return element;
	}

	@Override
	public int lastIndexOf(Object o) {
		for (int i = size - 1; i >= 0; i--) {
			if (data[i].equals(o)) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public String toString() {
		return "MyList [size=" + size + ", data=" + Arrays.toString(data) + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.deepHashCode(data);
		result = prime * result + Objects.hash(size);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MyFreeList other = (MyFreeList) obj;
		return Arrays.deepEquals(data, other.data) && size == other.size;
	}

}
