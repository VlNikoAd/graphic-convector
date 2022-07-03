package ru.netology.graphics;

import ru.netology.graphics.image.*;
import ru.netology.graphics.server.GServer;


public class Main {

    public static void main(String[] args) throws Exception {

        TextGraphicsConverter converter = new BadImageSizeException.Converter(new Schema()); // Создайте тут объект вашего класса конвертера

        GServer server = new GServer(converter); // Создаём объект сервера

        server.start(); // Запускаем

        // Или то же, но с выводом на экран:
        //String url = "https://you-anime.ru/anime-images/characters/character_101268.jpg?1637720312";
        //converter.setMaxRatio(4);
        //converter.setMaxWidth(300);
        //converter.setMaxHeight(300);
        
        //String imgTxt = converter.convert(url);

        //System.out.println(imgTxt);
    }
}
