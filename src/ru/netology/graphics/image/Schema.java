package ru.netology.graphics.image;

import java.util.HashMap;
import java.util.Map;

public class Schema implements TextColorSchema{
    private final Map<Integer, Character> map = new HashMap<>();

    public Schema(){

        for (int i = 1; i < 32; i++) {
            map.put(i, '▇');
        }
        for (int j = 32; j < 64; j++) {
            map.put(j, '●');
        }
        for (int k = 64; k < 96; k++) {
            map.put(k, '◉');
        }
        for (int k = 96; k < 128; k++) {
            map.put(k, '◍');
        }
        for (int k = 128; k < 160; k++) {
            map.put(k, '◎');
        }
        for (int k = 160; k < 192; k++) {
            map.put(k, '○');
        }
        for (int k = 192; k < 224; k++) {
            map.put(k, '☉');
        }
        for (int k = 224; k < 240; k++) {
            map.put(k, '◌');
        }
        for (int k = 240; k < 256; k++) {
            map.put(k, '-');
        }
    }

    @Override
    public char convert(int color) {
        return map.get(color);
    }
}
