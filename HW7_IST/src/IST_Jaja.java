import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;
import java.util.Collections;

import com.google.gson.*;

import edu.princeton.cs.algs4.In;

import java.util.ArrayList;


class IntervalST<Key extends Comparable<Key>, Value> {
    private Node root;

    private class Node {
        private Key lo, hi, max;
        private Value val;
        private int size;
        private Node left, right;

        public Node(Key lo, Key hi, Value val) {
            this.lo = lo;
            this.hi = hi;
            this.max = hi;
            this.val = val;
            this.size = 1;
        }
    }

    public IntervalST() {
        root = null;
    }

    public void put(Key lo, Key hi, Value val) {
        System.out.println("NowPut:["+lo+","+hi+"], val:"+val);
        root = put(root, lo, hi, val);
    }

    private Node put(Node x, Key lo, Key hi, Value val) {
        if (x == null) return new Node(lo, hi, val);
        int cmp = lo.compareTo(x.lo);
        if (cmp < 0) x.left = put(x.left, lo, hi, val);
        else if (cmp > 0) x.right = put(x.right, lo, hi, val);
        else {
            int cmp2 = hi.compareTo(x.hi);
            if (cmp2 == 0) x.val = val; // Update value if interval is identical
            else if (cmp2 < 0) x.left = put(x.left, lo, hi, val);
            else x.right = put(x.right, lo, hi, val);
        }
        x.size = 1 + size(x.left) + size(x.right);
        x.max = max3(x.hi, max(x.left), max(x.right));
        return x;
    }

    public void delete(Key lo, Key hi) {
        System.out.println("NowDelete:["+lo+","+hi+"]");
        root = delete(root, lo, hi);
    }

    private Node delete(Node x, Key lo, Key hi) {
        if (x == null) return null;
        int cmp = lo.compareTo(x.lo);
        if (cmp < 0) x.left = delete(x.left, lo, hi);
        else if (cmp > 0) x.right = delete(x.right, lo, hi);
        else {
            int cmp2 = hi.compareTo(x.hi);
            if (cmp2 == 0) x = join(x.left, x.right);
            else if (cmp2 < 0) x.left = delete(x.left, lo, hi);
            else x.right = delete(x.right, lo, hi);
        }
        if (x != null) {
            x.size = 1 + size(x.left) + size(x.right);
            x.max = max3(x.hi, max(x.left), max(x.right));
        }
        return x;
    }

    private Node join(Node a, Node b) {
        if (a == null) return b;
        if (b == null) return a;

        if (Math.random() * (size(a) + size(b)) < size(a)) {
            a.right = join(a.right, b);
            a.size = 1 + size(a.left) + size(a.right);
            a.max = max3(a.hi, max(a.left), max(a.right));
            return a;
        } else {
            b.left = join(a, b.left);
            b.size = 1 + size(b.left) + size(b.right);
            b.max = max3(b.hi, max(b.left), max(b.right));
            return b;
        }
    }

    public List<Value> intersects(Key lo, Key hi) {
        System.out.println("NowIntersec:["+lo+","+hi+"]");
        List<Value> result = new ArrayList<>();
        intersects(root, lo, hi, result);
        return result;
    }

    private void intersects(Node x, Key lo, Key hi, List<Value> result) {
        if (x == null) return;
        if (intersects(lo, hi, x.lo, x.hi)) result.add(x.val);
        if (x.left != null && x.left.max.compareTo(lo) >= 0) intersects(x.left, lo, hi, result);
        if (x.right != null && x.right.lo.compareTo(hi) <= 0) intersects(x.right, lo, hi, result);
    }

    private boolean intersects(Key lo1, Key hi1, Key lo2, Key hi2) {
        return lo1.compareTo(hi2) <= 0 && hi1.compareTo(lo2) >= 0;
    }

    private Key max(Node x) {
        if (x == null) return null;
        return x.max;
    }

    private int size(Node x) {
        if (x == null) return 0;
        return x.size;
    }

    private Key max3(Key a, Key b, Key c) {
        return max(max(a, b), c);
    }

    private Key max(Key a, Key b) {
        if (a == null) return b;
        if (b == null) return a;
        return a.compareTo(b) > 0 ? a : b;
    }
}

class OutputFormat{
    List<String> answer;
    String func;
    String[] args;
}

class test{
    static boolean deepEquals(List<String> a, List<String> b)
    {
        return Arrays.deepEquals(a.toArray(), b.toArray());
    }
    static boolean run_and_check(OutputFormat[] data, IntervalST <Integer,String> IST)
    {
        for(OutputFormat cmd : data)
        {
            if(cmd.func.equals("intersects"))
            {
                int lo = Integer.parseInt(cmd.args[0]);
                int hi = Integer.parseInt(cmd.args[1]);
                
                List<String> student_answer = IST.intersects(lo, hi);
                
                Collections.sort(cmd.answer);
                Collections.sort(student_answer);
                
                
                System.out.println("StudentAnswer:");
                for (String answer : student_answer) {
                    System.out.println(answer);
                }
                System.out.println("RightAns:");
                for (String answer : cmd.answer) {
                    System.out.println(answer);
                }
                if(!deepEquals(student_answer, cmd.answer))
                {
                    return false;
                }
            }
            else if(cmd.func.equals("put"))
            {
                IST.put(Integer.parseInt(cmd.args[0]), Integer.parseInt(cmd.args[1]), cmd.args[2]);
            }
            else if(cmd.func.equals("delete"))
            {
                IST.delete(Integer.parseInt(cmd.args[0]), Integer.parseInt(cmd.args[1]));
            }
        }

        return true;
    }
    public static void main(String[] args)
    {
        Gson gson = new Gson();
        OutputFormat[][] datas;
        OutputFormat[] data;
        int num_ac = 0;

        try {
            datas = gson.fromJson(new FileReader("test_IST.json"), OutputFormat[][].class);
            for(int i = 0; i<datas.length;++i)
            {
                data = datas[i];
                
                System.out.print("Sample"+i+": ");
                if(run_and_check(data, new IntervalST<>()))
                {
                    System.out.println("AC");
                    num_ac++;
                }
                else
                {
                    
                    System.out.println("WA");
                    System.out.println("");
                }
            }
            System.out.println("Score: "+num_ac+"/"+datas.length);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        } catch (JsonIOException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}