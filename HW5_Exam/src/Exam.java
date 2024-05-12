import java.util.*;

class Exam {
    public static List<int[]> getPassedList(Integer[][] scores) {
        int studentCount = scores[0].length;
        int subjectCount = scores.length;

        boolean[][] qualified = new boolean[subjectCount][studentCount];
        List<int[]> studentScores = new ArrayList<>();

        for (int subjectId = 0; subjectId < subjectCount; subjectId++) {
            Integer[] subjectScores = new Integer[studentCount];
            for (int studentId = 0; studentId < studentCount; studentId++) {
                subjectScores[studentId] = scores[subjectId][studentId];
            }
            Arrays.sort(subjectScores, Collections.reverseOrder());
            int thresholdScore = subjectScores[Math.min((int)Math.ceil(studentCount * 0.2) - 1, studentCount - 1)];

            for (int studentId = 0; studentId < studentCount; studentId++) {
                if (scores[subjectId][studentId] >= thresholdScore) {
                    qualified[subjectId][studentId] = true;
                }
            }
        }

        for (int studentId = 0; studentId < studentCount; studentId++) {
            boolean allQualified = true;
            for (int subjectId = 0; subjectId < subjectCount; subjectId++) {
                if (!qualified[subjectId][studentId]) {
                    allQualified = false;
                    break;
                }
            }
            if (allQualified) {
                int totalScore = 0;
                for (int subjectId = 0; subjectId < subjectCount; subjectId++) {
                    totalScore += scores[subjectId][studentId];
                }
                studentScores.add(new int[]{studentId, totalScore});
            }
        }

        Collections.sort(studentScores, (a, b) -> {
            if (b[1] != a[1]) {
                return b[1] - a[1];
            } else {
                return a[0] - b[0];
            }
        });

        return studentScores;
    }

    public static void main(String[] args) {
        List<int[]> ans = getPassedList(new Integer[][]{
            {67, 82, 98, 32, 65, 76, 87, 12, 43, 75, 25},
            {42, 90, 80, 12, 76, 58, 95, 30, 67, 78, 10}
        });
        for (int[] student : ans) {
            System.out.println(Arrays.toString(student));
        }
    }
}
