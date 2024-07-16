import java.util.Random;

class Node {
    int ipAdd;
    int dataPacket;
    Node left, right, parent;

    public Node(int ipAdd) {
        this.ipAdd = ipAdd;
        this.parent = null;
        this.left = null;
        this.right = null;
    }
}

class SplayTree {
    Node root;

    public SplayTree() {
        this.root = null;
    }

    public Node maximum(Node x) {
        while (x.right != null) {
            x = x.right;
        }
        return x;
    }

    public void leftRotate(Node x) {
        Node y = x.right;
        x.right = y.left;
        if (y.left != null) {
            y.left.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == null) {
            this.root = y;
        } else if (x == x.parent.left) {
            x.parent.left = y;
        } else {
            x.parent.right = y;
        }
        y.left = x;
        x.parent = y;
    }

    public void rightRotate(Node x) {
        Node y = x.left;
        x.left = y.right;
        if (y.right != null) {
            y.right.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == null) {
            this.root = y;
        } else if (x == x.parent.right) {
            x.parent.right = y;
        } else {
            x.parent.left = y;
        }
        y.right = x;
        x.parent = y;
    }

    public void splay(Node n) {
        while (n.parent != null) {
            if (n.parent == this.root) {
                if (n == n.parent.left) {
                    rightRotate(n.parent);
                } else {
                    leftRotate(n.parent);
                }
            } else {
                Node p = n.parent;
                Node g = p.parent;
                if (n.parent.left == n && p.parent.left == p) {
                    rightRotate(g);
                    rightRotate(p);
                } else if (n.parent.right == n && p.parent.right == p) {
                    leftRotate(g);
                    leftRotate(p);
                } else if (n.parent.right == n && p.parent.left == p) {
                    leftRotate(p);
                    rightRotate(g);
                } else if (n.parent.left == n && p.parent.right == p) {
                    rightRotate(p);
                    leftRotate(g);
                }
            }
        }
    }

    public void insert(Node n) {
        Node y = null;
        Node temp = this.root;
        while (temp != null) {
            y = temp;
            if (n.ipAdd < temp.ipAdd) {
                temp = temp.left;
            } else {
                temp = temp.right;
            }
        }
        n.parent = y;
        if (y == null) {
            this.root = n;
        } else if (n.ipAdd < y.ipAdd) {
            y.left = n;
        } else {
            y.right = n;
        }
        splay(n);
    }

    public Node search(Node n, int x) {
        if (n == null || x == n.ipAdd) {
            if (n != null) {
                splay(n);
            }
            return n;
        }
        if (x < n.ipAdd) {
            return search(n.left, x);
        } else {
            return search(n.right, x);
        }
    }

    public void inorder(Node n, String cmn) {
        if (n != null) {
            inorder(n.left, cmn);
            System.out.println(cmn + n.ipAdd + " -> " + n.dataPacket);
            inorder(n.right, cmn);
        }
    }

    public static void main(String[] args) {
        String cmn = "192.168.3.";
        SplayTree t = new SplayTree();
        Node[] nodes = {
            new Node(104), new Node(112), new Node(117), new Node(124),
            new Node(121), new Node(108), new Node(109), new Node(111),
            new Node(122), new Node(125), new Node(129)
        };
        for (Node node : nodes) {
            t.insert(node);
        }

        int[] find = {104, 112, 117, 124, 121, 108, 109, 111, 122, 125, 129};
        Random rand = new Random();
        for (int i = 0; i < find.length; i++) {
            int data = rand.nextInt(200);
            Node temp = t.search(nodes[i], find[i]);
            if (temp != null) {
                temp.dataPacket = data;
            }
        }

        System.out.println("IP ADDRESS -> DATA PACKET");
        t.inorder(t.root, cmn);
    }
}
