
import edu.princeton.cs.algs4.Quick;
import edu.princeton.cs.algs4.ST;


import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

class Exam {
    public static List<int[]> getPassedList(Integer[][] scores)
    {
        //input:
        //    scores: int[exam][id] 
        //    eg. scores[0][0] -> exam: 0, id: 0
        //        scores[1][5] -> exam: 1, id: 5

        //return:
        //    return an array of {id, totalScore} 
        //    sorted in descending order of score
        int numOfExam = scores.length;
        int numOfStudent = scores[0].length;
        int topk = (int) Math.floor(numOfStudent * 0.8);

        ArrayList<int[]> ans = new ArrayList<>();
        int[] thresholdScore = new int[numOfExam];
        Integer[] temp = new Integer[numOfStudent];

        for(int i = 0; i<numOfExam; ++i)
        {
            thresholdScore[i] = (int)Quick.select(Arrays.copyOf(scores[i], scores[i].length), topk);
        }

        int sum = 0;
        boolean isFailed = false;
        for(int i = 0; i<numOfStudent; ++i)
        {
            sum = 0;
            isFailed = false;
            for(int j = 0; j<numOfExam; j++)
            {
                int score = scores[j][i];
                if(scores[j][i] < thresholdScore[j])
                {
                    isFailed = true;
                    break;
                }
                sum += scores[j][i];
            }
            if(!isFailed)
                ans.add(new int[]{i,sum});
        }

        Collections.sort(ans,(a,b) -> {
            if(a[1]!=b[1])
                return b[1]-a[1];
            return a[0]-b[0];
        });
        return ans;
    }
    
    Exam(){
    }
    public static void main(String[] args) {
        long startTimeNano = System.nanoTime();
        List<int[]> ans = getPassedList(new Integer[][]
            {{71,53,84,76,97,50,30,39,55,81,92,79,72,11,21,47,15,21,85,39,49,9,56,59,57,44,12,79,37,73},{70,58,82,79,34,54,27,18,61,79,88,80,91,25,84,48,11,24,63,38,51,18,56,52,63,42,7,68,29,70},{77,51,87,67,49,53,34,23,54,91,79,85,37,98,36,41,15,24,7,48,48,22,57,53,53,42,17,78,27,81}}
        );
        long endTimeNano = System.nanoTime();
        long elapsedTimeNano = endTimeNano - startTimeNano;
        System.out.println("Elapsed time: " + elapsedTimeNano + " nanoseconds");
        for(int[] student : ans)
            System.out.println(Arrays.toString(student));
    } 
}