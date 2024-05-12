// import java.util.List;
// import java.util.ArrayList;

// class IntervalST<Key extends Comparable<Key>, Value> {
//     private static final boolean RED = true;
//     private static final boolean BLACK = false;

//     private Node root;
//     ArrayList<Value> intersectsInterval;

//     private class Node {
//         Key lo, hi, max;
//         Value val;
//         Node left, right;
//         boolean color;
//         int size;

//         public Node(Key lo, Key hi, Value val, boolean color, int size) {
//             this.lo = lo;
//             this.hi = hi;
//             this.val = val;
//             this.color = color;
//             this.size = size;
//             this.max = hi;
//         }
//     }



//     private boolean isRed(Node x) {
//         if (x == null)
//             return false;
//         return x.color == RED;
//     }

//     private Node rotateLeft(Node h) {
//         Node x = h.right;
//         h.right = x.left;
//         x.left = h;
//         x.color = h.color;
//         h.color = RED;
//         x.size = h.size;
//         h.size = 1 + size(h.left) + size(h.right);
//         h.max = max(h.left, h.right, h.hi);
//         x.max = max(x.left, x.right, x.hi);
//         return x;
//     }

//     private Node rotateRight(Node h) {
//         Node x = h.left;
//         h.left = x.right;
//         x.right = h;
//         x.color = h.color;
//         h.color = RED;
//         x.size = h.size;
//         h.size = 1 + size(h.left) + size(h.right);
//         h.max = max(h.left, h.right, h.hi);
//         x.max = max(x.left, x.right, x.hi);
//         return x;
//     }

//     private void flipColors(Node h) {
//         h.color = RED;
//         h.left.color = BLACK;
//         h.right.color = BLACK;
//     }

//     public void put(Key lo, Key hi, Value val) {
//         root = put(root, lo, hi, val);
//         root.color = BLACK;
//     }

//     private Node put(Node h, Key lo, Key hi, Value val) {
//         if (h == null)
//             return new Node(lo, hi, val, RED, 1);

//         int cmp = lo.compareTo(h.lo);
//         if (cmp < 0)
//             h.left = put(h.left, lo, hi, val);
//         else if (cmp > 0)
//             h.right = put(h.right, lo, hi, val);
//         else {
//             int cmp2 = hi.compareTo(h.hi);
//             if (cmp2 < 0)
//                 h.left = put(h.left, lo, hi, val);
//             else if (cmp2 > 0)
//                 h.right = put(h.right, lo, hi, val);
//             else
//                 h.val = val;
//         }

//         if (isRed(h.right) && !isRed(h.left))
//             h = rotateLeft(h);
//         if (isRed(h.left) && isRed(h.left.left))
//             h = rotateRight(h);
//         if (isRed(h.left) && isRed(h.right))
//             flipColors(h);

//         h.size = 1 + size(h.left) + size(h.right);
//         h.max = max(h.left, h.right, h.hi);
//         return h;
//     }
//     private int size(Node x) {
//         if (x == null)
//             return 0;
//         return x.size;
//     }
//     private Key max(Node a, Node b, Key c) {
//         Key m1 = (a != null) ? a.max : null;
//         Key m2 = (b != null) ? b.max : null;
//         if (m1 != null && m2 != null) {
//             Key maxChild = m1.compareTo(m2) > 0 ? m1 : m2;
//             return c != null && c.compareTo(maxChild) > 0 ? c : maxChild;
//         } else if (m1 != null) {
//             return c != null && c.compareTo(m1) > 0 ? c : m1;
//         } else if (m2 != null) {
//             return c != null && c.compareTo(m2) > 0 ? c : m2;
//         } else {
//             return c;
//         }
//     }

//     public void delete(Key lo, Key hi) {
//         root = delete(root, lo, hi);
//     }

//     private Node delete(Node x, Key lo, Key hi) {
//         if (x == null)
//             return null;
//         int cmp1 = lo.compareTo(x.lo);
//         if (cmp1 == 0)
//             cmp1 = hi.compareTo(x.hi);
//         if (cmp1 < 0)
//             x.left = delete(x.left, lo, hi);
//         else if (cmp1 > 0)
//             x.right = delete(x.right, lo, hi);
//         else {
//             if (x.left == null)
//                 return x.right;
//             else if (x.right == null)
//                 return x.left;
//             else {
//                 Node t = x;
//                 x = min(t.right);
//                 x.right = deleteMin(t.right);
//                 x.left = t.left;
//             }
//         }
//         x.max = max(x.left, x.right, x.hi);
//         x.size = 1 + size(x.left) + size(x.right);
//         return x;
//     }

//     private Node min(Node x) {
//         if (x.left == null)
//             return x;
//         return min(x.left);
//     }

//     private Node deleteMin(Node x) {
//         if (x.left == null)
//             return x.right;
//         x.left = deleteMin(x.left);
//         return x;
//     }

//     public List<Value> intersects(Key lo, Key hi) {
//         intersectsInterval = new ArrayList<Value>();
//         searchInterval(root, lo, hi);
//         return intersectsInterval;
//     }

//     private void searchInterval(Node x, Key lo, Key hi) {
//         if (x == null)
//             return;
//         if (isIntersection(x, lo, hi))
//             intersectsInterval.add(x.val);
//         if (x.left == null || x.left.max.compareTo(lo) < 0) {
//             searchInterval(x.right, lo, hi);
//             return;
//         }
//         searchInterval(x.left, lo, hi);
//         searchInterval(x.right, lo, hi);
//     }

//     private boolean isIntersection(Node n1, Key lo, Key hi) {
//         if (n1.hi.compareTo(lo) < 0 || n1.lo.compareTo(hi) > 0)
//             return false;
//         return true;
//     }

//     public static void main(String[]args)
//     {
//         // Example
//         IntervalST<Integer, String> IST = new IntervalST<>();
//         IST.put(2,5,"badminton");
//         IST.put(1,5,"PDSA HW7");
//         IST.put(3,5,"Lunch");
//         IST.put(3,6,"Workout");
//         IST.put(3,7,"Do nothing");
//         IST.delete(2,5);
//         System.out.println(IST.intersects(1,2));
        
//         IST.put(8,8,"Dinner");
//         System.out.println(IST.intersects(6,10));
        
//         IST.put(3,7,"Do something");
//         System.out.println(IST.intersects(7,7));
        
//         IST.delete(3,7); // delete both "Do nothing" and "Do something"
//         System.out.println(IST.intersects(7,7));
//     }
// }