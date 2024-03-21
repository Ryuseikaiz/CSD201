package javaapplication54;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.function.BiConsumer;

class book implements Comparable<book> {

    private String ISBN;
    private String title;
    private String author;
    private String yearOfPublic;
    private String publicsher;
    private String imaURLS;
    private String imaURLM;
    private String imaURLL;
    private int index;

    public book() {
    }

    public book(String ISBN, String title, String author, String yearOfPublic, String publicsher, String imaURLS, String imaURLM, String imaURLL) {
        this.ISBN = ISBN;
        this.title = title;
        this.author = author;
        this.yearOfPublic = yearOfPublic;
        this.publicsher = publicsher;
        this.imaURLS = imaURLS;
        this.imaURLM = imaURLM;
        this.imaURLL = imaURLL;
        this.index = 0;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getYearOfPublic() {
        return yearOfPublic;
    }

    public void setYearOfPublic(String yearOfPublic) {
        this.yearOfPublic = yearOfPublic;
    }

    public String getPublicsher() {
        return publicsher;
    }

    public void setPublicsher(String publicsher) {
        this.publicsher = publicsher;
    }

    public String getImaURLS() {
        return imaURLS;
    }

    public void setImaURLS(String imaURLS) {
        this.imaURLS = imaURLS;
    }

    public String getImaURLM() {
        return imaURLM;
    }

    public void setImaURLM(String imaURLM) {
        this.imaURLM = imaURLM;
    }

    public String getImaURLL() {
        return imaURLL;
    }

    public void setImaURLL(String imaURLL) {
        this.imaURLL = imaURLL;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return String.format("""
                             ISBN: %s
                             Book-Title: %s
                             Book-Author: %s
                             Year-Of-Publication: %s
                             Publisher: %s
                             Image-URL-S: %s
                             Image-URL-M: %s
                             Image-URL-L: %s""",
                ISBN, title, author, yearOfPublic, publicsher, imaURLS, imaURLM, imaURLL);
    }

    @Override
    public int compareTo(book other) {
        return this.ISBN.compareTo(other.ISBN);
    }
}

class hashBook {

    MyHashMap hm = new MyHashMap();

    public static void main(String[] args) {
        hashBook hb = new hashBook();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Menu:");
            System.out.println("1. Load file");
            System.out.println("2. Search");
            System.out.println("3. Calculate total");
            System.out.println("4. Max collision");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    hb.loadFile(); //chi load 10000 du lieu dau tien
                    break;
                case 2:
                    System.out.print("Enter ISBN to search: ");
                    String isbn = scanner.next();
                    book book = hb.search(isbn);
                    if (book != null) {
                        System.out.println("Book found: " + book);
                    } else {
                        System.out.println("Book not found");
                    }
                    break;
                case 3:
                    System.out.println("Total collisions: " + hb.hm.getTotalCollisions());
                    break;
                case 4:
                    System.out.println("Max collisions in a single bucket: " + hb.hm.getMaxCollisions());
                    break;
                case 5:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 5.");
            }
        }
    }

    public void loadFile() {
        try {
            int count = 0;
            BufferedReader br = new BufferedReader(new FileReader("books.csv"));
            String line;
            if ((line = br.readLine()) == null) {
                System.out.println("File is empty");
                return;
            }
            while ((line = br.readLine()) != null && count < 10000) {
                String[] linearr = line.split("\";\"");
                try {
                    hm.put(linearr[0].substring(1).trim(), (new book(linearr[0].substring(1).trim(), linearr[1].trim(), linearr[2].trim(),
                            linearr[3].trim(), linearr[4].trim(), linearr[5].trim(), linearr[6].trim(), linearr[7].substring(0, linearr[7].length() - 1).trim())));
                } catch (NumberFormatException e) {
                    System.out.println("Error parsing int at line: " + line);
                }
                count++;
            }
            br.close();
        } catch (FileNotFoundException nfe) {
            System.out.println("File not found " + "book.csv");
        } catch (IOException ex) {
            System.out.println("An error occurred while reading the file");
        }
    }

    public void print() {
        if (hm.isEmpty()) {
            System.out.println("Hash map is empty!");
            return;
        }
        hm.forEach((key, value) -> System.out.println("Key = " + key + "\nValue = \n{" + value + "}\n"));
    }

    public book search(String key) {
        if (hm.containsKey(key)) {
            System.out.println("Founded!");
            return (book) hm.get(key);
        }
        System.out.println("Not found " + key);
        return null;
    }
}

class Entry<K, V> {

    K key;
    V value;
    Entry<K, V> next;

    public Entry(K key, V value, Entry<K, V> next) {
        this.key = key;
        this.value = value;
        this.next = next;
    }
}

class MyHashMap<K, V> {

    private Entry<K, V>[] buckets;
    private int[] collisionCount; // Array to store collision count for each bucket
    private static final int INITIAL_CAPACITY = 1 << 4; // 16
    private int totalCollisions = 0; // Total number of collisions

    public MyHashMap() {
        this.buckets = new Entry[INITIAL_CAPACITY];
        this.collisionCount = new int[INITIAL_CAPACITY];
    }

    public boolean isEmpty() {
        if (buckets == null) {
            return true;
        }
        return false;
    }

    public void put(K key, V value) {
        int bucket = getHash(key) % getBucketSize();
        Entry<K, V> existing = buckets[bucket];
        if (existing == null) {
            buckets[bucket] = new Entry<>(key, value, null);
        } else {
            // Collision occurred
            totalCollisions++;
            collisionCount[bucket]++;
            // compare the keys see if key already exists
            while (existing.next != null) {
                if (existing.key.equals(key)) {
                    existing.value = value; // update the value
                    return;
                }
                existing = existing.next;
            }
            if (existing.key.equals(key)) {
                existing.value = value;
            } else {
                existing.next = new Entry<>(key, value, null);
            }
        }
    }

    public int getTotalCollisions() {
        return totalCollisions;
    }

    public int getMaxCollisions() {
        int max = collisionCount[0];
        for (int count : collisionCount) {
            if (count > max) {
                max = count;
            }
        }
        return max;
    }

    public V get(K key) {
        Entry<K, V> bucket = buckets[getHash(key) % getBucketSize()];
        while (bucket != null) {
            if (bucket.key.equals(key)) {
                return bucket.value;
            }
            bucket = bucket.next;
        }
        return null;
    }

    private int getHash(K key) {
        return key == null ? 0 : Math.abs(key.hashCode());
    }

    private int getBucketSize() {
        return buckets.length;
    }

    public void forEach(BiConsumer<? super K, ? super V> action) {
        for (Entry<K, V> bucket : buckets) {
            Entry<K, V> current = bucket;
            while (current != null) {
                action.accept(current.key, current.value);
                current = current.next;
            }
        }
    }

    public boolean containsKey(K key) {
        int bucketIndex = getHash(key) % getBucketSize();
        Entry<K, V> bucket = buckets[bucketIndex];

        while (bucket != null) {
            if (bucket.key.equals(key)) {
                return true;
            }
            bucket = bucket.next;
        }

        return false;
    }
}
