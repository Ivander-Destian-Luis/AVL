import java.util.Scanner;

class AvlNode<AnyType> {
    AnyType element;
    AvlNode<AnyType> left;
    AvlNode<AnyType> right;
    int height;

    public AvlNode(AnyType e, AvlNode<AnyType> l, AvlNode<AnyType> r) {
        element = e;
        left = l;
        right = r;
    }
        
    public String toString() {
        return left + " " + right;
    }
}

class AvlTree<AnyType extends Comparable<? super AnyType>> {
    private AvlNode<AnyType> root;

    public AvlTree() {
        root = null;
    }

    public AvlNode<AnyType> getRoot() {
        return root;
    }

    private int height(AvlNode<AnyType> t) {
        return t == null ? -1 : t.height;
    }

    private AnyType elementAt(AvlNode<AnyType> t) {
        return t == null ? null : t.element;
    }

    public int max(int lh, int rh) {
        if (lh > rh) {
            return lh;
        }
        return rh;
    }

    private AvlNode<AnyType> case1(AvlNode<AnyType> k2) {
        AvlNode<AnyType> k1 = k2.left;

        k2.left = k1.right;
        k1.right = k2;

        k2.height = max(height(k2.left), height(k2.right)) + 1;
        k1.height = max(height(k1.left), k2.height) + 1;

        return k1;
    }

    private AvlNode<AnyType> case4(AvlNode<AnyType> k1) {
        AvlNode<AnyType> k2 = k1.right;

        k1.right = k2.left;
        k2.left = k1;

        k1.height = max(height(k1.left), height(k1.right)) + 1;
        k2.height = max(height(k2.right), k1.height) + 1;

        return k2;
    }

    private AvlNode<AnyType> case2(AvlNode<AnyType> k3) {
        k3.left = case4(k3.left);
        return case1(k3);
    }

    private AvlNode<AnyType> case3(AvlNode<AnyType> k1) {
        k1.right = case1(k1.right);
        return case4(k1);
    }

    public void insert(AnyType x) {
        root = insert(x, root);
    }

    private AvlNode<AnyType> insert(AnyType x, AvlNode<AnyType> t) {
        if(t == null) {
            t = new AvlNode<AnyType>(x, null, null);
        }
        // Case 1 dan Case 2 (Tree mengalamai LH)
        else if (x.compareTo(t.element) < 0) {
            t.left = insert(x, t.left);
            if(height(t.left) - height(t.right) > 1) {
                if(x.compareTo(t.left.element) < 0) {
                    t = case1(t);
                }
                else {
                    t = case2(t);
                }
            }
        }
        // Case 3 dan Case 4 (Tree mengalamai LH)
        else if (x.compareTo(t.element) > 0) {
            t.right = insert(x, t.right);
            if(height(t.right) - height(t.left) > 1) {
                if(x.compareTo(t.right.element) > 0) {
                    t = case4(t);
                }
                else {
                    t = case3(t);
                }
            }
        }

        t.height = max(height(t.left), height(t.right)) + 1;
        return t;
    }

    private void inOrder(AvlNode<AnyType> t) {
        if (t != null) {
            if (t.left != null) {
                inOrder(t.left);
            }
            System.out.print(t.element + " ");
            if (t.right != null) {
                inOrder(t.right);
            }
        }
    }

    public void inOrder() {
        if (root != null) {
            inOrder(root);
        }
        else {
            System.out.println("Empty AVL tree");
        }
    }

    private void preOrder(AvlNode<AnyType> t) {
        if (t != null) {
            System.out.print(t.element + " ");
            if (t.left != null) {
                preOrder(t.left);
            }
            if (t.right != null) {
                preOrder(t.right);
            }
        }
    }

    public void preOrder() {
        if (root != null) {
            preOrder(root);
        }
        else {
            System.out.println("Empty AVL tree");
        }
    }

    private void postOrder(AvlNode<AnyType> t) {
        if (t != null) {
            if (t.left != null) {
                postOrder(t.left);
            }
            if (t.right != null) {
                postOrder(t.right);
            }
            System.out.print(t.element + " ");
        }
    }

    public void postOrder() {
        if (root != null) {
            postOrder(root);
        }
        else {
            System.out.println("Empty AVL tree");
        }
    }

    private AvlNode<AnyType> balance (AvlNode<AnyType> t){
        if(t == null){
            return null;
        }
        if(height(t.left) - height(t.right) > 1){
            if(height(t.left.left) >= height(t.left.right)){
                t = case1(t);
            }else{
                t = case2(t);
            }
        }else if(height(t.right) - height(t.left) > 1){
            if(height(t.right.right) >= height(t.right.left)){
                t = case4(t);
            }else{
                t = case3(t);
            }
        }
        t.height = max(height(t.left), height(t.right)) + 1 ;
        return t;
    }

    private AvlNode<AnyType> findMinimum(AvlNode<AnyType> t){
        if(t == null){
            return null;
        }
        while(t.left != null){
            t = t.left;
        }
        return t;
    }

        private AvlNode<AnyType> findMaximum(AvlNode<AnyType> t){
        if(t == null){
            return null;
        }
        while(t.right != null){
            t = t.right;
        }
        return t;
    }

    private AvlNode<AnyType> delete_succesor(AvlNode<AnyType> t, AnyType x){
        if(t == null){
            return null;
        }
        if(x.compareTo(t.element) < 0){
            t.left = delete_succesor(t.left, x);
        }else if(x.compareTo(t.element) > 0){
            t.right = delete_succesor(t.right,x);
        }else if(t.left != null && t.right != null){
            t.element = findMinimum(t.right).element;
            t.right = delete_succesor(t.right, t.element);
        }else{
            t = t.left != null ? t.left : t.right;
        }
        return balance(t);
    }

    public void delete_succesor(AnyType x){
        root = delete_succesor(root, x);
    }

    private AvlNode<AnyType> delete_predeccesor(AvlNode<AnyType> t, AnyType x){
        if(t == null){
            return null;
        }
        if(x.compareTo(t.element) < 0){
            t.left = delete_predeccesor(t.left, x);
        }else if(x.compareTo(t.element) > 0){
            t.right = delete_predeccesor(t.right,x);
        }else if(t.left != null && t.right != null){
            t.element = findMaximum(t.left).element;
            t.left = delete_predeccesor(t.left, t.element);
        }else{
            t = t.right != null ? t.left : t.left;
        }
        return balance(t);
    }

    public void delete_predeccesor(AnyType x){
        root = delete_predeccesor(root, x);
    }

    private String findNode( AvlNode<AnyType> t , AnyType x){
        if(t.element.equals(x)){
            return "Ketemu";
        }
         if (t != null) {
            if (t.left != null) {
                if(findNode(t.left , x).equals("Ketemu")) return "Ketemu";
            }
            if (t.right != null) {
                if(findNode(t.right, x).equals(x)) return "Ketemu";
            }
        }
        return "Tidak Ketemu";
    }

    public void findNode(AnyType x){
        System.out.println("Node " + x + " " +findNode(root , x));
    }

    private int jumlahNode(AvlNode<AnyType> t){
        int x = 0 , y = 0;
        if (t != null) {
            if (t.left != null) {
                x = 1 + jumlahNode(t.left);
            }
            if (t.right != null) {
                y = 1 + jumlahNode(t.right);
            }
        }
        return x + y;
    }

    public void jumlahNode(){
        int jumlahRoot = 1;
        System.out.println("Jumlah Node : " + (jumlahNode(root) + jumlahRoot));
    }
}

public class Implementasi_AVL_Tree {
    public static void main(String[] args) {
        AvlTree<Integer> tree = new AvlTree<Integer>();
        tree.insert(10);
        tree.insert(85);
        tree.insert(15);
        tree.insert(70);
        tree.insert(20);
        tree.insert(60);
        tree.insert(30);
        tree.insert(50);
        tree.insert(65);
        tree.insert(80);
        tree.insert(90);
        tree.insert(40);
        tree.insert(5);
        tree.insert(55);

        System.out.print("Inorder =  ");
        tree.inOrder();
        System.out.println();

        System.out.print("Preorder =  ");
        tree.preOrder();
        System.out.println();

        System.out.print("Postorder =  ");
        tree.postOrder();
        System.out.println();
        tree.delete_succesor(60);
        System.out.println("Setelah Delete 60");
        System.out.print("Inorder =  ");
        tree.inOrder();
        System.out.println();

        System.out.print("Preorder =  ");
        tree.preOrder();
        System.out.println();

        System.out.print("Postorder =  ");
        tree.postOrder();
        System.out.println();

        tree.delete_predeccesor(65);
        System.out.println("Setelah Delete 85");
        System.out.print("Inorder =  ");
        tree.inOrder();
        System.out.println();

        System.out.print("Preorder =  ");
        tree.preOrder();
        System.out.println();

        System.out.print("Postorder =  ");
        tree.postOrder();
        System.out.println();
        System.out.println();

        Scanner sc = new Scanner(System.in);
        System.out.print("Masukkan Node yang Dicari : ");
        int cari = sc.nextInt();
        System.out.println();
        tree.findNode(cari);

        tree.jumlahNode();
    }
}