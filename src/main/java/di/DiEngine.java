package di;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class DiEngine {

    private static DiEngine instance = null;
    private final Map<String, Class<?>> dc = new HashMap<>();

    private DiEngine(){

    }

    public static DiEngine getInstance(){
        if (instance == null){
            instance = new DiEngine();
        }
        return instance;
    }

}
