
class Node {

    String name;
    double diameter;
    double mass;
    double semiMajorAxis;
    double orbitalPeriod;
    Node left, right;

    Node(String name, double diameter,
            double mass, double semiMajorAxis, double orbitalPeriod) {
        this.name = name;
        this.diameter = diameter;
        this.mass = mass;
        this.semiMajorAxis = semiMajorAxis;
        this.orbitalPeriod = orbitalPeriod;
        left = right = null;
    }
}

class BSTree {

    Node root;

    BSTree() {
        root = null;
    }

    void insert(String name, double diameter,
            double mass, double semiMajorAxis, double orbitalPeriod) {
        root = insertRec(root, name, diameter,
                mass, semiMajorAxis, orbitalPeriod);
    }

    private Node insertRec(Node root, String name, double diameter,
            double mass, double semiMajorAxis,
            double orbitalPeriod) {
        if (root == null) {
            root = new Node(name, diameter,
                    mass, semiMajorAxis, orbitalPeriod);
            return root;
        }
        if (mass < root.mass) {
            root.left = insertRec(root.left, name, diameter,
                    mass, semiMajorAxis, orbitalPeriod);
        } else if (mass > root.mass) {
            root.right = insertRec(root.right, name, diameter,
                    mass, semiMajorAxis, orbitalPeriod);
        }
        return root;
    }

    void inOrder(Node p) {
        if (p != null) {
            inOrder(p.left);
            System.out.println("Name: " + p.name
                    + ", Diameter: " + p.diameter
                    + ", Mass: " + p.mass
                    + ", Semi-Major Axis: " + p.semiMajorAxis
                    + ", Orbital Period: " + p.orbitalPeriod);
            inOrder(p.right);
        }
    }

    public static void main(String[] args) {
        BSTree tree = new BSTree();
        tree.insert("Mercury", 0.383, 0.06, 0.39, 0.24);
        tree.insert("Venus", 0.949, 0.81, 0.72, 0.62);
        tree.insert("Earth", 1.000, 1.00, 1.00, 1.00);
        tree.insert("Mars", 0.532, 0.11, 1.52, 1.88);
        tree.insert("Jupiter", 11.209, 317.83, 5.20, 11.86);
        tree.insert("Saturn", 9.449, 95.16, 9.54, 29.45);
        tree.insert("Uranus", 4.007, 14.54, 19.19, 84.02);
        tree.insert("Neptune", 3.883, 17.15, 30.07, 164.79);
        System.out.println("In-order traversal of the BSTree:");
        tree.inOrder(tree.root);
    }
}
