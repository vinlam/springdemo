package com;

import java.util.LinkedHashMap;
import java.util.Map;

public class LruMap<K, V> extends LinkedHashMap<K, V> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int maxSize;

	public LruMap(int initialCapacity, float loadFactor, boolean accessOrder, int maxSize) {
		super(initialCapacity, loadFactor, accessOrder);
		this.maxSize = maxSize;
	}

	public LruMap(int maxSize) {
		this(16, 0.75f, true, maxSize);
	}

	public LruMap(int tableSize, int maxSize) {
		this(tableSize, 0.75f, true, maxSize);
	}

	@Override
	protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
		boolean flag = size() > maxSize;
		if (flag) {
			System.out.println("移除头节点, key:" + eldest.getKey() + ", value:" + eldest.getValue());
		}
		return flag;
	}

	public static void main(String[] args) {
		LruMap<Integer, String> lruMap = new LruMap<>(4, 4);

		lruMap.put(1, "aaaa");
		lruMap.put(2, "bbbb");
		lruMap.put(3, "cccc");
		lruMap.put(4, "dddd");
		lruMap.put(5, "eeee");

		lruMap.entrySet().forEach(node -> {
			System.out.println("key:" + node.getKey() + ", value:" + node.getValue());
		});
//		利用get方法改变一下双向链表的顺序，可以控制移除的节点，修改一下测试数据，如下代码块
		lruMap.put(1, "aaaa");
		lruMap.put(2, "bbbb");
		lruMap.put(3, "cccc");
		lruMap.get(1);
		lruMap.get(2);
		lruMap.put(4, "dddd");
		lruMap.put(5, "eeee");
		lruMap.entrySet().forEach(node -> {
			System.out.println("key:" + node.getKey() + ", value:" + node.getValue());
		});
	}
}
