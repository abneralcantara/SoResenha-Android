package external.slopeone;

import android.util.Log;

import com.ufrpe.bsi.soresenha.eventos.dominio.Evento;
import com.ufrpe.bsi.soresenha.usuario.dominio.Usuario;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Slope One algorithm implementation
 */
public class SlopeOne {

    private static Map<Evento, Map<Evento, Double>> diff = new HashMap<>();
    private static Map<Evento, Map<Evento, Integer>> freq = new HashMap<>();
    private static Map<Usuario, HashMap<Evento, Double>> inputData;
    private static Map<Usuario, HashMap<Evento, Double>> outputData = new HashMap<>();

    public static void slopeOne() {
        inputData = InputData.initializeData(15);
        System.out.println("Slope One - Before the Prediction\n");
        buildDifferencesMatrix(inputData);
        System.out.println("\nSlope One - With Predictions\n");
        predict(inputData);
    }

    /**
     * Based on the available data, calculate the relationships between the
     * items and number of occurences
     *
     * @param data
     *            existing user data and their items' ratings
     */
    private static void buildDifferencesMatrix(Map<Usuario, HashMap<Evento, Double>> data) {
        for (HashMap<Evento, Double> user : data.values()) {
            for (Entry<Evento, Double> e : user.entrySet()) {
                if (!diff.containsKey(e.getKey())) {
                    diff.put(e.getKey(), new HashMap<Evento, Double>());
                    freq.put(e.getKey(), new HashMap<Evento, Integer>());
                }
                for (Entry<Evento, Double> e2 : user.entrySet()) {
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
        for (Evento j : diff.keySet()) {
            for (Evento i : diff.get(j).keySet()) {
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
    private static void predict(Map<Usuario, HashMap<Evento, Double>> data) {
        HashMap<Evento, Double> uPred = new HashMap<Evento, Double>();
        HashMap<Evento, Integer> uFreq = new HashMap<Evento, Integer>();
        for (Evento j : diff.keySet()) {
            uFreq.put(j, 0);
            uPred.put(j, 0.0);
        }
        for (Entry<Usuario, HashMap<Evento, Double>> e : data.entrySet()) {
            for (Evento j : e.getValue().keySet()) {
                for (Evento k : diff.keySet()) {
                    try {
                        double predictedValue = diff.get(k).get(j).doubleValue() + e.getValue().get(j).doubleValue();
                        double finalValue = predictedValue * freq.get(k).get(j).intValue();
                        uPred.put(k, uPred.get(k) + finalValue);
                        uFreq.put(k, uFreq.get(k) + freq.get(k).get(j).intValue());
                    } catch (NullPointerException e1) {
                    }
                }
            }
            HashMap<Evento, Double> clean = new HashMap<Evento, Double>();
            for (Evento j : uPred.keySet()) {
                if (uFreq.get(j) > 0) {
                    clean.put(j, uPred.get(j).doubleValue() / uFreq.get(j).intValue());
                }
            }
            for (Evento j : InputData.items) {
                if (e.getValue().containsKey(j)) {
                    clean.put(j, e.getValue().get(j));
                } else {
                    clean.put(j, -1.0);
                }
            }
            outputData.put(e.getKey(), clean);
        }
        printData(outputData);
    }

    private static void printData(Map<Usuario, HashMap<Evento, Double>> data) {
        for (Usuario user : data.keySet()) {
            Log.i("SlO",user.getNome() + ":");
            print(data.get(user));
        }
    }

    private static void print(HashMap<Evento, Double> hashMap) {
        NumberFormat formatter = new DecimalFormat("#0.000");
        for (Evento j : hashMap.keySet()) {
            Log.i("SlO"," " + j.getNome() + " --> " + formatter.format(hashMap.get(j).doubleValue()));
        }
    }

}
