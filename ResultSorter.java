package com.exam.result.util;

import com.exam.result.model.Result;
import java.util.List;

public class ResultSorter {
    // Algorithm - Selection Sort implementation to sort students by scores
    public static List<Result> selectionSortByScore(List<Result> results) {
        for (int i = 0; i < results.size() - 1; i++) {
            int maxIndex = i;
            for (int j = i + 1; j < results.size(); j++) {
                if (results.get(j).getScore() > results.get(maxIndex).getScore()) {
                    maxIndex = j;
                }
            }

            // Swap the found maximum element with the first element
            if (maxIndex != i) {
                Result temp = results.get(i);
                results.set(i, results.get(maxIndex));
                results.set(maxIndex, temp);
            }
        }
        return results;
    }
}