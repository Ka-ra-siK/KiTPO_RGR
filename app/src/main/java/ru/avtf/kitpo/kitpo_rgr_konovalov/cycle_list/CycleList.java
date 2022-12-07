package ru.avtf.kitpo.kitpo_rgr_konovalov.cycle_list;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;

import ru.avtf.kitpo.kitpo_rgr_konovalov.comparator.Comparator;
import ru.avtf.kitpo.kitpo_rgr_konovalov.types.users.UserType;

/**
 * Класс циклического списка.
 * Реализованы основные методы:
 *
 * @see CycleList#add(Object) вставка в конец
 * @see CycleList#add(Object, int) вставка по индексу
 * @see CycleList#remove(int) удаление по индексу
 * @see CycleList#getByIndex(int) получение данных по индексу
 * @see CycleList#getNode(int) получение узла
 * @see CycleList#getLength() получение длины списка
 * @see CycleList#sort(Comparator) сортировка слиянием
 * @see CycleList#printList() печать списка
 */
public class CycleList {
    private Node head;
    private int length;

    private Comparator comparator;


    /**
     * Класс узла списка
     * Хранит Object в виде данных
     * Указатель на следующий и предыдущий
     */
    private class Node implements Serializable {
        Object data;
        Node next;
        Node prev;

        public Node(Object data) {
            this.data = data;
        }

        @Override
        public String toString() {
            return data.toString();
        }
    }

    public CycleList(Comparator comparator) {
        this.comparator = comparator;
    }

    public void add(Object data) {
        if (head == null) {
            Node node = new Node(data);
            node.next = node.prev = node;
            head = node;
            length++;
            return;
        }
        Node tail = head.prev;
        Node node = new Node(data);
        node.next = head;
        head.prev = node;
        node.prev = tail;
        tail.next = node;
        length++;
    }

    public void add(Object data, int index) {
        Node tmp = getNode(index);
        Node newNode = new Node(data);
        Node tail = head.prev;
        if (tmp != head) {
            tmp.prev.next = newNode;
            newNode.prev = tmp.prev;
        } else {
            head = newNode;
        }
        newNode.next = tmp;
        tmp.prev = newNode;
        tail.next = head;
        head.prev = tail;
        length++;
    }

    public void remove(int index) {
        Node tmp = getNode(index);
        Node tail = head.prev;
        if (tmp != head) {
            tmp.prev.next = tmp.next;
        } else {
            head = tmp.next;
        }
        if (tmp != tail) {
            tmp.next.prev = tmp.prev;
        } else {
            tail = tmp.prev;
        }
        tmp.next = tmp.prev = null;
        tail.next = head;
        head.prev = tail;
        length--;
    }

    public Object getByIndex(int index) {
        if (index < 0 || index >= this.length)
            return null;
        else
            return getNode(index).data;
    }

    public int getLength() {
        return length;
    }

    private Node getNode(int index) {
        if (index < 0 || index >= length) throw new IndexOutOfBoundsException();
        Node tmp = head;

        for (int i = 0; i < index; i++) {
            tmp = tmp.next;
        }
        return tmp;
    }

    /**
     * Обход циклического списка с головного элемента,
     * проход до головного включительно
     *
     * @param iterator
     */
    public void forEach(Iterator iterator) throws IOException {
        Node tmp = head;
        for (int i = 0; i < length; i++) {
            iterator.toDo(tmp.data);
            tmp = tmp.next;
        }
    }

    /**
     * Обход циклического списка, в обратном направлении,
     * проход до головного включительно
     *
     * @param iterator
     */
    public void forEachReverse(Iterator iterator) throws IOException {
        Node tmp = head;
        for (int i = 0; i < length; i++) {
            iterator.toDo(tmp.data);
            tmp = tmp.prev;
        }
    }

    /**
     * Сортировка слиянием.
     * Реализовано 3 метода: (mergeSort(), merge(), getMidNode()).
     *
     * @param comparator экземпляр класса Comparator, для сравнения объектов
     * @see CycleList#mergeSort(Node, Comparator) рекурсивно разделяет список
     * @see CycleList#merge(Node, Node, Comparator) выполняет слияние
     * @see CycleList#getMidNode(Node) находит центр списка
     */
    public void sort(Comparator comparator) {
        if (head != null && head.next != head && head.prev != head) {
            Node tail = head.prev;
            tail.next = null;
            head.prev = null;
            head = mergeSort(head, comparator);
            tail = getNode(length - 1);
            tail.next = head;
            head.prev = tail;
        }
    }

    private Node mergeSort(Node headNode, Comparator comparator) {
        if (headNode == null || headNode.next == null) {
            return headNode;
        }
        Node middle = getMidNode(headNode);
        Node middleNext = middle.next;

        middle.next = null;

        Node left = mergeSort(headNode, comparator);
        Node right = mergeSort(middleNext, comparator);

        return merge(left, right, comparator);
    }

    private Node merge(Node firstNode, Node secondNode, Comparator comparator) {
        Node merged = new Node(null);
        Node temp = merged;
        Node tail = head.prev;
        while (firstNode != null && secondNode != null) {
            if (comparator.compare(firstNode.data, secondNode.data) < 0) {
                temp.next = firstNode;
                firstNode.prev = temp;
                firstNode = firstNode.next;
            } else {
                temp.next = secondNode;
                secondNode.prev = temp;
                secondNode = secondNode.next;
            }
            temp = temp.next;
        }
        while (firstNode != null) {
            temp.next = firstNode;
            firstNode.prev = temp;
            firstNode = firstNode.next;
            temp = temp.next;
        }
        while (secondNode != null) {
            temp.next = secondNode;
            secondNode.prev = temp;
            secondNode = secondNode.next;
            temp = temp.next;
            tail = temp;
        }
        return merged.next;
    }

    private Node getMidNode(Node node) {
        Node previousNode = node;
        Node currentNode = node;
        while (currentNode.next != null && currentNode.next.next != null) {
            previousNode = previousNode.next;
            currentNode = currentNode.next.next;
        }
        return previousNode;
    }

    public void printList() {
        Node tmp = head;
        for (int i = 0; i < length; i++) {
            System.out.print(i + ") ");
            System.out.println(tmp.data);
            tmp = tmp.next;
        }
    }

    @Override
    public String toString() {
        String str = "";
        Node tmp = head;
        for (int i = 0; i < length; i++) {
            str = str + tmp.data + "\n";
            tmp = tmp.next;
        }
        return str;

    }

    public void clearList() {
        head = null;
        length = 0;
    }
}
