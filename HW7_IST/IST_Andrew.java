import java.util.ArrayList;
import java.util.List;


class IntervalST<Key extends Comparable<Key>, Value>{
    private Node root;             // root of BST

    private class Node {
        private Key lo;           // sorted by key
        private Key hi;
        private Value val;         // associated data
        private Key max;
        private int size;
        private Node left, right;  // left and right subtrees

        private boolean intersects(Key lo, Key hi) {
            if (hi.compareTo(this.lo) < 0) return false;
            if (this.hi.compareTo(lo) < 0) return false;
            return true;
        }
        public Node(Key lo,Key hi, Value val,int size) {
            this.lo = lo;
            this.hi = hi;
            this.val = val;
            this.size = size;
            this.max = hi;
        }
    }
    public IntervalST(){}
    private int size(Node x) {
        if (x == null) return 0;
        else return x.size;
    }
    private Node put(Node x, Key lo, Key hi, Value val) {
        if (x == null) return new Node(lo,hi, val,1);
        int cmp = lo.compareTo(x.lo);
        if(cmp == 0 && hi.compareTo(x.hi) != 0)
            cmp = hi.compareTo(x.hi);
        if      (cmp < 0) x.left  = put(x.left,  lo,hi, val);
        else if (cmp > 0) x.right = put(x.right, lo,hi, val);
        else              x.val   = val;
        x.size = 1 + size(x.left) + size(x.right);
        if(x.right != null)
            x.max = (x.max.compareTo(x.right.max) > 0) ? x.max : x.right.max;
        if(x.left != null)
            x.max = (x.max.compareTo(x.left.max) > 0) ? x.max : x.left.max;
        return x;
    }
    public void put(Key lo, Key hi, Value val)
    {
        root = put(root, lo,hi, val);
    }
    private Node delete(Node x, Key lo, Key hi)
    {
        if (x == null) return null;

        int cmp = lo.compareTo(x.lo);
        if(cmp == 0)
            cmp = hi.compareTo(x.hi);
        if      (cmp < 0) x.left  = delete(x.left,  lo, hi);
        else if (cmp > 0) x.right = delete(x.right, lo, hi);
        else {
            if (x.right == null) return x.left;
            if (x.left  == null) return x.right;
            Node t = x;
            x = min(t.right);
            x.right = deleteMin(t.right);
            x.left = t.left;
        }
        x.size = size(x.left) + size(x.right) + 1;
        x.max = x.hi;
        if(x.right != null)
            x.max = (x.max.compareTo(x.right.max) > 0) ? x.max : x.right.max;
        if(x.left != null)
            x.max = (x.max.compareTo(x.left.max) > 0) ? x.max : x.left.max;
        return x;
    }
    public void delete(Key lo, Key hi)
    {
        root = delete(root, lo, hi);
    }
    private Node min(Node x) {
        if (x.left == null) return x;
        else                return min(x.left);
    }
    private Node deleteMin(Node x) {
        if (x.left == null) return x.right;
        x.left = deleteMin(x.left);
        x.size = size(x.left) + size(x.right) + 1;
        return x;
    }
    private void transverse(Node x)
    {
        if(x == null) return;
        transverse(x.left);
        System.out.print(x.lo);
        System.out.print(x.val);
        System.out.println(x.hi);
        transverse(x.right);
    }
    public void transverse()
    {
        transverse(root);
    }
    private List<Value> intersects(Node x, Key lo, Key hi, List<Value> arr)
    {
        if(x == null) return arr;
        if(x.intersects(lo,hi))
            arr.add(x.val);
        if(x.left != null && x.left.max.compareTo(lo) >= 0)
            arr = (List<Value>) intersects(x.left,lo,hi,arr);
        if(x.right != null && hi.compareTo(x.lo) >= 0)
            arr = (List<Value>) intersects(x.right,lo,hi,arr);
        return arr;
    }
    public List<Value> intersects(Key lo, Key hi)
    {
        if(lo.compareTo(hi)>0)
            return null;
        return  intersects(root, lo, hi, new ArrayList<Value>());
    }
    public static void main(String[]args)
    {
        // Example
        IntervalST<Integer, String> IST = new IntervalST<>();
        IST.put(2,5,"badminton");
        IST.put(1,5,"PDSA HW7");
        IST.put(3,5,"Lunch");
        IST.put(3,6,"Workout");
        IST.put(3,7,"Do nothing");
        IST.delete(2,5);
        System.out.println(IST.intersects(1,2));
        
        IST.put(8,8,"Dinner");
        System.out.println(IST.intersects(6,10));
        
        IST.put(3,7,"Do something"); // If an interval is identical to an existing node, then the value of that node is updated accordingly
        System.out.println(IST.intersects(7,7));
        
        IST.delete(3,7); // delete "Do something"
        System.out.println(IST.intersects(7,7));
    }
}
