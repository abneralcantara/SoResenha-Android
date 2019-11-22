package external;

import android.util.Log;

import com.ufrpe.bsi.soresenha.eventos.dominio.Evento;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

// https://www.baeldung.com/java-collaborative-filtering-recommendations
// https://github.com/eugenp/tutorials/blob/master/algorithms-miscellaneous-2/src/main/java/com/baeldung/algorithms/slope_one/SlopeOne.java

/**
 * Slope One algorithm implementation
 */
public class SlopeOne {

    private static Map<Long, Map<Long, Double>> diff = new HashMap<>();
    private static Map<Long, Map<Long, Integer>> freq = new HashMap<>();
    private static Map<Long, HashMap<Long, Double>> outputData = new HashMap<>();

    public static void slopeOne(Map<Long, HashMap<Long, Double>> inputData, List<Evento> eventoList) {
        buildDifferencesMatrix(inputData);
        predict(inputData, eventoList);
    }

    public static Map<Long, HashMap<Long, Double>> getOutput() {
        return outputData;
    }

    /**
     * Based on the available data, calculate the relationships between the
     * items and number of occurences
     *
     * @param data
     *            existing user data and their items' ratings
     */
    private static void buildDifferencesMatrix(Map<Long, HashMap<Long, Double>> data) {
        for (HashMap<Long, Double> user : data.values()) {
            for (Entry<Long, Double> e : user.entrySet()) {
                if (!diff.containsKey(e.getKey())) {
                    diff.put(e.getKey(), new HashMap<Long, Double>());
                    freq.put(e.getKey(), new HashMap<Long, Integer>());
                }
                for (Entry<Long, Double> e2 : user.entrySet()) {
                    int oldCount = 0;
                    if (freq.get(e.getKey()).containsKey(e2.getKey())) {
                        oldCount = freq.get(e.getKey()).get(e2.getKey()).intValue();
                    }
                    double oldDiff = 0.0;
                    if (diff.get(e.getKey()).containsKey(e2.getKey())) {
                        oldDiff = diff.get(e.getKey()).get(e2.getKey()).doubleValue();
                    }
                    double observedDiff = e.getValue() - e2.getValue();
                    freq.get(e.getKey()).put(e2.getKey(), oldCount + 1);
                    diff.get(e.getKey()).put(e2.getKey(), oldDiff + observedDiff);
                }
            }
        }
        for (Long j : diff.keySet()) {
            for (Long i : diff.get(j).keySet()) {
                double oldValue = diff.get(j).get(i).doubleValue();
                int count = freq.get(j).get(i).intValue();
                diff.get(j).put(i, oldValue / count);
            }
        }
        printData(data);
    }

    /**
     * Based on existing data predict all missing ratings. If prediction is not
     * possible, the value will be equal to -1
     *
     * @param data
     *            existing user data and their items' ratings
     */
    private static void predict(Map<Long, HashMap<Long, Double>> data, List<Evento> eventoList) {
        HashMap<Long, Double> uPred = new HashMap<>();
        HashMap<Long, Integer> uFreq = new HashMap<>();
        for (Long j : diff.keySet()) {
            uFreq.put(j, 0);
            uPred.put(j, 0.0);
        }
        for (Entry<Long, HashMap<Long, Double>> e : data.entrySet()) {
            for (Long j : e.getValue().keySet()) {
                for (Long k : diff.keySet()) {
                    try {
                        double predictedValue = diff.get(k).get(j).doubleValue() + e.getValue().get(j).doubleValue();
                        double finalValue = predictedValue * freq.get(k).get(j).intValue();
                        uPred.put(k, uPred.get(k) + finalValue);
                        uFreq.put(k, uFreq.get(k) + freq.get(k).get(j).intValue());
                    } catch (NullPointerException e1) {
                    }
                }
            }
            HashMap<Long, Double> clean = new HashMap<>();
            for (Long j : uPred.keySet()) {
                if (uFreq.get(j) > 0) {
                    clean.put(j, uPred.get(j).doubleValue() / uFreq.get(j).intValue());
                }
            }
            for (Evento j : eventoList) {
                if (e.getValue().containsKey(j.getId())) {
                    clean.put(j.getId(), e.getValue().get(j.getId()));
                } else {
                    //clean.put(j.getId(), -1.0);
                }
            }
            outputData.put(e.getKey(), clean);
        }
        printData(outputData);
    }

    private static void printData(Map<Long, HashMap<Long, Double>> data) {
        for (Long user : data.keySet()) {
            Log.i("SlO",user + " - :");
            print(data.get(user));
        }
    }

    private static void print(HashMap<Long, Double> hashMap) {
        NumberFormat formatter = new DecimalFormat("#0.000");
        for (Long j : hashMap.keySet()) {
            Log.i("SlO"," " + j + " --> " + formatter.format(hashMap.get(j).doubleValue()));
        }
    }

}
