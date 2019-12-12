package com.references;

import java.lang.ref.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private List<BigObject> strongList;
    private List<Reference<BigObject>> softList;
    private List<Reference<BigObject>> weakList;
    private List<Reference<BigObject>> phantomList;
    private ReferenceQueue<BigObject> queue;
    private List<String> loadMemoryList;

    public static void main(String[] args) {
        Main main = new Main();
        main.testPhantomReferences();
        System.out.println("_________________________");
        main.testSoftReferences();
    }

    public Main() {
    }

    public void testPhantomReferences() {
        init();
        System.gc();
        System.out.println("garbage collector invoked!");
        printLists();
    }

    public void testSoftReferences() {
        init();
        System.gc();
        System.out.println("Garbage collector invoked");
        printLists();
        System.out.println("Memory usage increased");
        loadMemory();
        System.out.println("loadMemoryList.size() = " + loadMemoryList.size());
        System.gc();
        System.out.println("garbage collector invoked");
        printLists();
    }

    private void init() {
        strongList = new ArrayList<BigObject>();
        softList = new ArrayList<Reference<BigObject>>();
        weakList = new ArrayList<Reference<BigObject>>();
        phantomList = new ArrayList<Reference<BigObject>>();
        loadMemoryList = new ArrayList<String>();
        queue = new ReferenceQueue<BigObject>();

        for (int i = 0; i < 3; i++) {
            strongList.add(new BigObject(i));
            softList.add(new SoftReference<BigObject>(new BigObject(i)));
            weakList.add(new WeakReference<BigObject>(new BigObject(i)));
            phantomList.add(new PhantomReference<BigObject>(new BigObject(i), queue));
        }
        printLists();
    }

    private void loadMemory() {
        for (int i = 0; i < 50_000_000; i++) {
            loadMemoryList.add(i + "");
        }
    }

    private void printLists() {
        System.out.println("Strong references: ");
        for (BigObject bigObject: strongList) {
            System.out.print(bigObject + " ");
        }

        System.out.println();
        System.out.println("Soft references: ");
        printList(softList);
        System.out.println("Weak references: ");
        printList(weakList);
        System.out.println("Phantom references: ");
        printList(phantomList);
    }

    private void printList(List<Reference<BigObject>> list) {
        for (Reference<BigObject> ref: list) {
            System.out.print(ref.get() + " ");
        }
        System.out.println();
    }
}
