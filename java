
import java.util.*;

class Product {
    int id;
    String name;
    double price;

    Product(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public String toString() {
        return "ID: " + id + " | Name: " + name +
               " | Price: Rs." + price;
    }
}

// ================= BST =================

class BST {
    class Node {
        Product product;
        Node left, right;

        Node(Product p) {
            product = p;
        }
    }

    Node root;
    int comparisons = 0;

    boolean insert(Product p) {
        if (root == null) {
            root = new Node(p);
            return true;
        }
        return insertNode(root, p);
    }

    boolean insertNode(Node node, Product p) {
        if (p.id == node.product.id) return false;

        if (p.id < node.product.id) {
            if (node.left == null) {
                node.left = new Node(p);
                return true;
            }
            return insertNode(node.left, p);
        } else {
            if (node.right == null) {
                node.right = new Node(p);
                return true;
            }
            return insertNode(node.right, p);
        }
    }

    Product search(int id) {
        comparisons = 0;
        Node current = root;

        while (current != null) {
            comparisons++;

            if (id == current.product.id)
                return current.product;

            if (id < current.product.id)
                current = current.left;
            else
                current = current.right;
        }
        return null;
    }

    void inorder(Node node) {
        if (node == null) return;

        inorder(node.left);
        System.out.println(node.product);
        inorder(node.right);
    }

    void display() {
        inorder(root);
    }

    int height(Node node) {
        if (node == null) return 0;
        return 1 + Math.max(height(node.left), height(node.right));
    }

    int getHeight() {
        return height(root);
    }
}

// ================= AVL TREE =================

class AVLTree {
    class Node {
        Product product;
        Node left, right;
        int height = 1;

        Node(Product p) {
            product = p;
        }
    }

    Node root;
    int comparisons = 0;

    int height(Node n) {
        return n == null ? 0 : n.height;
    }

    int balance(Node n) {
        return n == null ? 0 : height(n.left) - height(n.right);
    }

    Node rightRotate(Node y) {
        Node x = y.left;
        Node temp = x.right;

        x.right = y;
        y.left = temp;

        y.height = 1 + Math.max(height(y.left), height(y.right));
        x.height = 1 + Math.max(height(x.left), height(x.right));

        return x;
    }

    Node leftRotate(Node x) {
        Node y = x.right;
        Node temp = y.left;

        y.left = x;
        x.right = temp;

        x.height = 1 + Math.max(height(x.left), height(x.right));
        y.height = 1 + Math.max(height(y.left), height(y.right));

        return y;
    }

    boolean insert(Product p) {
        if (searchWithoutCounting(p.id) != null) return false;
        root = insertNode(root, p);
        return true;
    }

    Node insertNode(Node node, Product p) {
        if (node == null) return new Node(p);

        if (p.id < node.product.id)
            node.left = insertNode(node.left, p);
        else
            node.right = insertNode(node.right, p);

        node.height = 1 + Math.max(height(node.left), height(node.right));

        int b = balance(node);

        // LL case
        if (b > 1 && p.id < node.left.product.id)
            return rightRotate(node);

        // RR case
        if (b < -1 && p.id > node.right.product.id)
            return leftRotate(node);

        // LR case
        if (b > 1 && p.id > node.left.product.id) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // RL case
        if (b < -1 && p.id < node.right.product.id) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    Product searchWithoutCounting(int id) {
        Node current = root;
        while (current != null) {
            if (id == current.product.id) return current.product;
            current = id < current.product.id ?
                      current.left : current.right;
        }
        return null;
    }

    Product search(int id) {
        comparisons = 0;
        Node current = root;

        while (current != null) {
            comparisons++;

            if (id == current.product.id)
                return current.product;

            current = id < current.product.id ?
                      current.left : current.right;
        }
        return null;
    }

    void inorder(Node node) {
        if (node == null) return;
        inorder(node.left);
        System.out.println(node.product);
        inorder(node.right);
    }

    void display() {
        inorder(root);
    }

    int getHeight() {
        return height(root);
    }
}

// ================= HASHING =================

class HashTable {
    Product[] table = new Product[47];
    int collisions = 0;
    int comparisons = 0;

    int hash(int id) {
        return Math.floorMod(id, table.length);
    }

    boolean insert(Product p) {
        int index = hash(p.id);

        for (int i = 0; i < table.length; i++) {
            int position = (index + i) % table.length;

            if (table[position] != null &&
                table[position].id == p.id) {
                return false;
            }

            if (table[position] == null) {
                table[position] = p;
                return true;
            }

            collisions++;
        }

        System.out.println("Hash table is full!");
        return false;
    }

    Product search(int id) {
        comparisons = 0;
        int index = hash(id);

        for (int i = 0; i < table.length; i++) {
            int position = (index + i) % table.length;
            comparisons++;

            if (table[position] == null)
                return null;

            if (table[position].id == id)
                return table[position];
        }

        return null;
    }

    void display() {
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null) {
                System.out.println("Index " + i + " : " + table[i]);
            }
        }
    }
}

// ================= MAIN PROGRAM =================

public class Main {

    static BST bst = new BST();
    static AVLTree avl = new AVLTree();
    static HashTable hash = new HashTable();

    static Scanner sc = new Scanner(System.in);

    static void addProduct(Product p) {
        boolean a = bst.insert(p);
        boolean b = avl.insert(p);
        boolean c = hash.insert(p);

        if (a && b && c)
            System.out.println("Product inserted successfully!");
        else
            System.out.println("Duplicate Product ID or insertion failed!");
    }

    static void searchProduct(int id) {
        System.out.println("\n========== SEARCH RESULTS ==========");

        Product p1 = bst.search(id);
        int bstCount = bst.comparisons;

        Product p2 = avl.search(id);
        int avlCount = avl.comparisons;

        Product p3 = hash.search(id);
        int hashCount = hash.comparisons;

        System.out.println("\nBST: " +
                (p1 == null ? "Not Found" : p1));
        System.out.println("BST Comparisons: " + bstCount);

        System.out.println("\nAVL: " +
                (p2 == null ? "Not Found" : p2));
        System.out.println("AVL Comparisons: " + avlCount);

        System.out.println("\nHashing: " +
                (p3 == null ? "Not Found" : p3));
        System.out.println("Hash Probes: " + hashCount);
    }

    static void loadDataset() {
        Product[] products = {
            new Product(1001, "Laptop", 55000),
            new Product(1048, "Keyboard", 1200),
            new Product(1095, "Mouse", 650),
            new Product(1142, "Monitor", 12000),
            new Product(1189, "Headphones", 1800),
            new Product(1236, "Printer", 8500),
            new Product(1283, "Webcam", 2200),
            new Product(1330, "Tablet", 18000),
            new Product(1010, "Speaker", 2500),
            new Product(1020, "Charger", 800),
            new Product(1030, "Router", 3200),
            new Product(1040, "Pendrive", 600),
            new Product(1050, "SSD", 4500),
            new Product(1060, "Hard Disk", 5200),
            new Product(1070, "Microphone", 1900),
            new Product(1080, "Smart Watch", 6500),
            new Product(1090, "Camera", 24000),
            new Product(1100, "Projector", 28000),
            new Product(1110, "Gaming Mouse", 1500),
            new Product(1120, "USB Hub", 900),
            new Product(1130, "Graphics Tablet", 7000),
            new Product(1140, "Power Bank", 1300),
            new Product(1150, "Laptop Stand", 1100),
            new Product(1160, "Smartphone", 22000),
            new Product(1170, "Graphics Card", 35000),
            new Product(1180, "RAM", 2800),
            new Product(1190, "Motherboard", 12500),
            new Product(1200, "CPU", 18000),
            new Product(1210, "Cooling Fan", 750),
            new Product(1220, "Mouse Pad", 400)
        };

        for (Product p : products)
            addProduct(p);
    }

    public static void main(String[] args) {

        loadDataset();

        int choice;

        do {
            System.out.println("\n==================================");
            System.out.println("     PRODUCT ID SEARCH SYSTEM");
            System.out.println("==================================");
            System.out.println("1. Add Product");
            System.out.println("2. Search Product");
            System.out.println("3. Display BST");
            System.out.println("4. Display AVL Tree");
            System.out.println("5. Display Hash Table");
            System.out.println("6. Performance Comparison");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");

            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    System.out.print("Enter Product ID: ");
                    int id = sc.nextInt();

                    sc.nextLine();
                    System.out.print("Enter Product Name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter Price: ");
                    double price = sc.nextDouble();

                    addProduct(new Product(id, name, price));
                    break;

                case 2:
                    System.out.print("Enter Product ID to search: ");
                    int searchId = sc.nextInt();
                    searchProduct(searchId);
                    break;

                case 3:
                    System.out.println("\nBST Inorder Traversal:");
                    bst.display();
                    break;

                case 4:
                    System.out.println("\nAVL Inorder Traversal:");
                    avl.display();
                    break;

                case 5:
                    System.out.println("\nHash Table:");
                    hash.display();
                    break;

                case 6:
                    System.out.println("\n===== PERFORMANCE COMPARISON =====");
                    System.out.println("BST Height: " + bst.getHeight());
                    System.out.println("AVL Height: " + avl.getHeight());
                    System.out.println("Hash Collisions: " + hash.collisions);
                    System.out.println("Hash Table Size: " + hash.table.length);
                    break;

                case 7:
                    System.out.println("Thank you for using the system!");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 7);

        sc.close();
    }
}
