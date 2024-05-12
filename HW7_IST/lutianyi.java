import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;
import java.util.Collections;
import com.google.gson.*;
import java.util.ArrayList;

class IntervalST<Key extends Comparable<Key>, Value> {
    private Node root;

    private class Node {
        Key lo, hi, max;
        Value val;
        Node left, right;

        public Node(Key lo, Key hi, Value val) {
            this.lo = lo;
            this.hi = hi;
            this.val = val;
            this.max = hi;
        }
    }

    public IntervalST() {
        // Tree initialized with no nodes
    }

    public void put(Key lo, Key hi, Value val) {
        if (lo.compareTo(hi) > 0) {
            throw new IllegalArgumentException("End point must not be less than start point");
        }
        root = put(root, lo, hi, val);
    }

    private Node put(Node x, Key lo, Key hi, Value val) {
        if (x == null) {
            return new Node(lo, hi, val);
        }

        int cmp = lo.compareTo(x.lo);
        if (cmp < 0) {
            x.left = put(x.left, lo, hi, val);
        } else if (cmp > 0) {
            x.right = put(x.right, lo, hi, val);
        } else {
            int cmpHi = hi.compareTo(x.hi);
            if (cmpHi == 0) {
                x.val = val; // Update the value if it matches exactly
            } else if (cmpHi < 0) {
                x.left = put(x.left, lo, hi, val);
            } else {
                x.right = put(x.right, lo, hi, val);
            }
        }

        updateMax(x);
        return x;
    }

    private void updateMax(Node x) {
        if (x != null) {
            x.max = x.hi;
            if (x.left != null) {
                x.max = max(x.max, x.left.max);
            }
            if (x.right != null) {
                x.max = max(x.max, x.right.max);
            }
        }
    }

    private Key max(Key a, Key b) {
        return (a.compareTo(b) > 0) ? a : b;
    }

    public void delete(Key lo, Key hi) {
        root = delete(root, lo, hi);
    }

    private Node delete(Node x, Key lo, Key hi) {
        if (x == null) {
            // Node not found, return null
            return null;
        }

        // Compare the given interval with the current node's interval
        int cmpLo = lo.compareTo(x.lo);
        int cmpHi = hi.compareTo(x.hi);

        if (cmpLo < 0) {
            // Continue search in the left subtree
            x.left = delete(x.left, lo, hi);
        } else if (cmpLo > 0) {
            // Continue search in the right subtree
            x.right = delete(x.right, lo, hi);
        } else if (cmpHi == 0) { // Both lo and hi match
            // Node found with an exact match, proceed to delete this node
            if (x.left == null) {
                return x.right; // Return right child if left child is null
            } else if (x.right == null) {
                return x.left; // Return left child if right child is null
            }

            // Node with two children: Get the smallest node from the right subtree
            Node t = x;
            x = min(t.right); // Smallest node in right subtree
            x.right = deleteMin(t.right); // Delete the minimum node in right subtree and update x.right
            x.left = t.left; // Preserve left subtree
        } else {
            // Interval does not match exactly, continue search in the right subtree
            x.right = delete(x.right, lo, hi);
        }

        // Update the max value in case it has changed
        updateMax(x);
        return x;
    }

    private Node min(Node x) {
        while (x.left != null) {
            x = x.left;
        }
        return x;
    }

    private Node deleteMin(Node x) {
        if (x.left == null) {
            return x.right;
        }
        x.left = deleteMin(x.left);
        updateMax(x);
        return x;
    }

    public List<Value> intersects(Key lo, Key hi) {
        List<Value> result = new ArrayList<>();
        intersects(root, lo, hi, result);
        return result; // Always return the result list, even if it's empty
    }

    private void intersects(Node x, Key lo, Key hi, List<Value> result) {
        if (x == null)
            return; // Base case: if current node is null, just return

        if (lo.compareTo(x.hi) <= 0 && hi.compareTo(x.lo) >= 0) {
            result.add(x.val); // Add value to result if current node's interval intersects with the query
                               // interval
        }

        if (x.left != null && x.left.max.compareTo(lo) >= 0) {
            intersects(x.left, lo, hi, result); // Traverse left subtree if it might contain overlapping intervals
        }
        if (x.right != null && hi.compareTo(x.lo) >= 0) {
            intersects(x.right, lo, hi, result); // Traverse right subtree if it might contain overlapping intervals
        }
    }

    public static void main(String[] args) {
        IntervalST<Integer, String> IST = new IntervalST<>();
        IST.put(2, 5, "badminton");
        IST.put(1, 5, "PDSA HW7");
        IST.put(3, 5, "Lunch");
        IST.put(3, 6, "Workout");
        IST.put(3, 7, "Do nothing");
        IST.delete(2, 5); // delete "badminton"
        System.out.println(IST.intersects(1, 2));

        IST.put(8, 8, "Dinner");
        System.out.println(IST.intersects(6, 10));

        IST.put(3, 7, "Do something"); // If an interval is identical to an existing node, then the value of that node
                                       // is updated accordingly
        System.out.println(IST.intersects(7, 7));

        IST.delete(3, 7); // delete "Do something"
        System.out.println(IST.intersects(7, 7));
    }

}

class OutputFormat {
    List<String> answer;
    String func;
    String[] args;
}

class test {
    static boolean deepEquals(List<String> a, List<String> b) {
        return Arrays.deepEquals(a.toArray(), b.toArray());
    }

    static boolean run_and_check(OutputFormat[] data, IntervalST<Integer, String> IST) {
        for (OutputFormat cmd : data) {
            if (cmd.func.equals("intersects")) {
                int lo = Integer.parseInt(cmd.args[0]);
                int hi = Integer.parseInt(cmd.args[1]);

                List<String> student_answer = IST.intersects(lo, hi);
                Collections.sort(cmd.answer);
                Collections.sort(student_answer);
                if (!deepEquals(student_answer, cmd.answer)) {
                    return false;
                }
            } else if (cmd.func.equals("put")) {
                IST.put(Integer.parseInt(cmd.args[0]), Integer.parseInt(cmd.args[1]),
                        cmd.args[2]);
            } else if (cmd.func.equals("delete")) {
                IST.delete(Integer.parseInt(cmd.args[0]), Integer.parseInt(cmd.args[1]));
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Gson gson = new Gson();
        OutputFormat[][] datas;
        OutputFormat[] data;
        int num_ac = 0;

        try {
            datas = gson.fromJson(new FileReader(args[0]), OutputFormat[][].class);
            for (int i = 0; i < datas.length; ++i) {
                data = datas[i];

                System.out.print("Sample" + i + ": ");
                if (run_and_check(data, new IntervalST<>())) {
                    System.out.println("AC");
                    num_ac++;
                } else {
                    System.out.println("WA");
                    System.out.println("");
                }
            }
            System.out.println("Score: " + num_ac + "/" + datas.length);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        } catch (JsonIOException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}