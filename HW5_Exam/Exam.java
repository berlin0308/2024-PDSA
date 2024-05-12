
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

        Collections.sort(ans,(a,b) -> b[1] - a[1]);
        return ans;
    }
    
    Exam(){
    }
    public static void main(String[] args) {
        long startTimeNano = System.nanoTime();
        List<int[]> ans = getPassedList(new Integer[][]
            {{67,82,98,32,65,76,87,12,43,75,25},{42,90,80,12,76,58,95,30,67,78,10}}
        );
        long endTimeNano = System.nanoTime();
        long elapsedTimeNano = endTimeNano - startTimeNano;
        System.out.println("Elapsed time: " + elapsedTimeNano + " nanoseconds");
        for(int[] student : ans)
            System.out.println(Arrays.toString(student));
    } 
}