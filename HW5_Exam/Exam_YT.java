import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import edu.princeton.cs.algs4.MaxPQ;
// import edu.princeton.cs.algs4.MinPQ;

class Exam {
    public static List<int[]> getPassedList(Integer[][] scores) {
        // input:
        // scores: int[exam][id]
        // eg. scores[0][0] -> exam: 0, id: 0
        // scores[1][5] -> exam: 1, id: 5

        Exam e = new Exam();
        int students = scores[0].length;
        int examNumber = scores.length;
        // int cannotEnrollNumber = (int) Math.floor(students * 0.8);
        int enrollNumber = (int) Math.ceil(students * 0.2);
        MaxPQ<Subject> scoresFinalPQ = new MaxPQ<Subject>();
        int[] failScores = new int[examNumber];

        // 找到不及格的分數標準
        for (int subject = 0; subject < examNumber; subject++) {
            MaxPQ<Subject> exam = new MaxPQ<Subject>();
            for (int id = 0; id < students; id++) {
                exam.insert(e.new Subject(id, scores[subject][id]));
            }
            for (int Enroll = 0; Enroll < enrollNumber; Enroll++) {
                Subject ex = exam.delMax();
                failScores[subject] = ex.score;
            }
        }
        // 找到錄取的名單
        for (int id = 0; id < students; id++) {
            int totalScore = 0;
            boolean failed = false;
            for (int exam = 0; exam < examNumber; exam++) {
                if (scores[exam][id] < failScores[exam]) {
                    failed = true;
                    break;
                } else {
                    totalScore += scores[exam][id];
                }
            }
            if(!failed) {
                scoresFinalPQ.insert(e.new Subject(id, totalScore));
            }
        }
        // 排序錄取的名單
        List<int[]> getPassed = new ArrayList<int[]>();
        for (int enroll = 0; enroll < enrollNumber; enroll++) {
            if (!scoresFinalPQ.isEmpty()) {
                Subject score = scoresFinalPQ.delMax();
                getPassed.add(new int[] { score.id, score.score });
            } else {
                break;
            }
        }
        return getPassed;
        // return:
        // return a List of {id, totalScore}
        // sorted in descending order of score
    }

    private class Subject implements Comparable<Subject> {
        public int id;
        public int score;

        public Subject(int id, int score) {
            this.id = id;
            this.score = score;
        }

        public int compareTo(Subject other) {
            if (this.score < other.score) {
                return -1;
            } else if (this.score > other.score) {
                return 1;
            } else if (this.id < other.id) {
                return 1;
            } else if (this.id > other.id) {
                return -1;
            } else {
                return 0;
            }
        }
    }

    Exam() {
    }

    public static void main(String[] args) {
        List<int[]> ans = getPassedList(new Integer[][] {
                { 67, 82, 98, 32, 65, 76, 87, 12, 43, 75, 25 },
                { 42, 90, 80, 12, 76, 58, 95, 30, 67, 78, 10 }
        });
        for (int[] student : ans)
            System.out.print(Arrays.toString(student));
        // [6, 182][2, 178][1, 172]

        // new Integer[][]{[17,183],[0,175],[23,172],[28,99],[7,97],[11,96]};

    }
}