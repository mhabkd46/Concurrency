package Confluent;

import java.util.*;

class Node {
    int key;
    int value;
    long expiryTime;
    Node next;
    Node prev;

    Node(int key, int value, long expiryTime) {
        this.key = key;
        this.value = value;
        this.expiryTime = System.currentTimeMillis() + expiryTime;
    }
}

public class WindowTTLCache {
    int capacity;
    Node head;
    Node tail;
    Map<Integer, Node> map;
    float runningSum;

    public WindowTTLCache(int capacity) {
        this.capacity = capacity;
        this.runningSum = 0;

        this.head = new Node(-1, -1, -1);
        this.tail = new Node(-1, -1, -1);

        this.head.next = this.tail;
        this.tail.prev = this.head;
    }

    public float getAverage() {
        return this.runningSum/map.size();
    }

    public int get(int key) {
        evict();
        if (map.containsKey(key)) {
            moveToFront(map.get(key));
            return map.get(key).value;
        }

        return -1;
    }

    public boolean put(int key, int value, long expiryTime) {
        evict();
        if (map.containsKey(key)) {
            removeNode(map.get(key));
        }
        else if (capacity == map.size()){
            Node toRemove = tail.prev;
            removeNode(toRemove);
        }

        Node node = new Node(key, value, expiryTime);
        addAtFront(node);

        return true;
    }

    private void removeNode(Node node) {
        Node prev = node.prev;
        Node next = node.next;

        prev.next = next;
        next.prev = prev;

        node.next = null;
        node.prev = null;

        map.remove(node.key);
        this.runningSum =- node.value;
    }

    private void addAtFront(Node node) {
        Node next = head.next;
        node.next = next;
        node.prev = head;
        head.next = node;

        map.put(node.key, node);
        this.runningSum =+ node.value;
    }

    private void moveToFront(Node node) {
        removeNode(node);
        addAtFront(node);
    }

    private void evict() {
        long currentTimeMillis = System.currentTimeMillis();
        map.entrySet().removeIf(entry -> {
            Node node = entry.getValue();
            boolean isExpired = node.expiryTime < currentTimeMillis;
            if (isExpired) {
                removeNode(node);
            }
            return isExpired;
        });
    }
}
