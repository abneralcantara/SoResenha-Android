package external.slopeone;

import com.ufrpe.bsi.soresenha.eventos.dominio.Evento;
import com.ufrpe.bsi.soresenha.usuario.dominio.Usuario;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;


public class InputData {

    protected static List<Evento> items = Arrays.asList(new Evento("EV1"), new Evento("EV2"),
            new Evento("EV3"), new Evento("EV4"), new Evento("EV5"));

    public static Map<Usuario, HashMap<Evento, Double>> initializeData(int numberOfUsers) {
        Map<Usuario, HashMap<Evento, Double>> data = new HashMap<>();
        HashMap<Evento, Double> newUser;
        Set<Evento> newRecommendationSet;
        for (int i = 0; i < numberOfUsers; i++) {
            newUser = new HashMap<>();
            newRecommendationSet = new HashSet<>();
            for (int j = 0; j < 3; j++) {
                newRecommendationSet.add(items.get((int) (Math.random() * 5)));
            }
            for (Evento item : newRecommendationSet) {
                newUser.put(item, Math.random());
            }
            Usuario usr = new Usuario();
            usr.setNome("Usr " + new Random().nextLong());
            data.put(usr, newUser);
        }
        return data;
    }

}

