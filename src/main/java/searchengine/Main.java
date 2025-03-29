package searchengine;

import org.apache.lucene.morphology.LuceneMorphology;
import org.apache.lucene.morphology.russian.RussianLuceneMorphology;
import searchengine.services.impl.LemmaFinderImpl;

import java.io.IOException;

public class Main {

    private static final String WORD_TYPE_REGEX = "\\W\\w&&[^а-яА-Я\\s]";
    private static final String[] particlesNames = new String[]{"МЕЖД", "ПРЕДЛ", "СОЮЗ"};
    private static LuceneMorphology luceneMorph;

    public static void main(String[] args) throws IOException {
        luceneMorph = new RussianLuceneMorphology();
        var lemmaService = new LemmaFinderImpl();

        var text = "PlayBack.ru 5 минут от метро ВДНХ 8(495)143-77-71 пн-пт: c 11 до 20 сб-вс: с 11 до 18 Возникла проблема? Напишите нам! Корзина пуста Каталог Смартфоны Чехлы для смартфонов Xiaomi Защитные стекла для смартфонов Xiaomi Чехлы для смартфонов Samsung Защитные стекла для смартфонов Samsung Зарядные устройства и кабели Держатели для смартфонов Автодержатели Носимая электроника Наушники и колонки Гаджеты Xiaomi Запчасти для телефонов Чехлы для планшетов Аксессуары для фото-видео Чехлы для смартфонов Apple USB Флеш-накопители Защитные стекла для смартфонов Realme Чехлы для смартфонов Realme Карты памяти Защитные стекла для планшетов Защитные стекла для смартфонов Защитные стекла для смартфонов Apple Чехлы для смартфонов Vivo Чехлы для смартфонов Tecno Доставка Самовывоз Оплата Гарантия и обмен Контакты ◄ Все чехлы для смартфонов xiaomi Чехлы для Redmi Note 8 Pro Protective Case Отбор по производителю: Bubble | Fashion Case | New Color | Protective Case | Все Сортировать по: Наименованию Цене Чехол книжка Protective Case для Xiaomi Redmi Note 8 Pro Черный 400р. Купить 1 Информация Наши спецпредложения Доставка Оплата Гарантия Контакты Положение о конфиденциальности и защите персональных данных +7(495)143-77-71 График работы: пн-пт: c 11-00 до 20-00 сб-вс: с 11-00 до 18-00 Наш адрес: Москва, Звездный бульвар, 10, строение 1, 2 этаж, офис 10. 2005-2025 ©Интернет магазин PlayBack.ru Наверх";

        var result = lemmaService.getLemmasAndFrequency(text);

        for (var lemma : result.entrySet()){
            System.out.println(lemma.getKey() + " - " + lemma.getValue());
        }

//        List<String> wordBaseForms =
//                luceneMorph.getMorphInfo("над");
//        wordBaseForms.forEach(System.out::println);
    }

}
