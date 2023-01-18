package file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.util.AbstractMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.NoSuchElementException;

import store.Product;
import store.Store;

public class MyFile {

	private File file;
	private ObjectOutputStream oOut;
	private ObjectInputStream oIn;
	private FileInputStream fIn;
	private boolean isAppendable;
	private Store.Order order;

	public MyFile(String fileName) {
		file = new File(fileName);
		isAppendable = file.exists();
		resetOutputStream();
		resetInputStream();
		setOrder();
	}

	private void setOrder() {
		try {
			order = Store.Order.valueOf(oIn.readUTF());
		} catch (IOException | NullPointerException e) {
			order = null;
		}
	}

	public boolean exist() {
		return order != null;
	}

	private void resetOutputStream() {
		try {
			if (isAppendable) {
				oOut = new ObjectOutputStream(new FileOutputStream(file, isAppendable)) {
					@Override
					public void writeStreamHeader() throws IOException {
						return;
					}
				};
			} else {
				oOut = new ObjectOutputStream(new FileOutputStream(file));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void resetInputStream() {
		try {
			fIn = new FileInputStream(file);
			oIn = new ObjectInputStream(fIn);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Store.Order getOrder() {
		return order;
	}

	public void writeOrder(Store.Order order) {
		try {
			oOut.writeUTF(order.name());
			oOut.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void writeProduct(String catalogNum, Product product) {
		try {
			oOut.writeUTF(catalogNum);
			oOut.writeObject(product);
			oOut.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Iterator<Entry<String, Product>> iterator() {
		try {
			fIn.close();
			oIn.close();
			resetInputStream();
			oIn.readUTF();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return new FileIterator();
	}

	private class FileIterator implements Iterator<Entry<String, Product>> {

		private boolean canRemove = false;
		private int lastPos = 0;
		private int currentPos = 0;

		@Override
		public boolean hasNext() {
			try {
				return oIn.available() > 0;
			} catch (IOException e) {
				return false;
			}
		}

		@Override
		public Entry<String, Product> next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}

			String catalogNum = "";
			Product product = null;
			try {
				lastPos = (int) fIn.getChannel().position();
				catalogNum = oIn.readUTF();
				product = (Product) oIn.readObject();
				currentPos = (int) fIn.getChannel().position();
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}

			canRemove = true;
			return new AbstractMap.SimpleEntry<String, Product>(catalogNum, product);
		}

		@Override
		public void remove() throws IllegalStateException {
			if (!canRemove)
				throw new IllegalStateException();

			try (RandomAccessFile raf = new RandomAccessFile(file, "rw")) {
				int readIndex = currentPos;
				int writeIndex = lastPos;

				while (readIndex < file.length()) {
					raf.seek(readIndex);
					int data = raf.read();
					raf.seek(writeIndex);
					raf.write(data);
					readIndex++;
					writeIndex++;
				}

				raf.setLength(writeIndex);
				canRemove = false;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}
