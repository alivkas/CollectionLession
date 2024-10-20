package ru.naumen.collection.task3;

import java.nio.file.Path;
import java.util.*;

/**
 * <p>Написать консольное приложение, которое принимает на вход произвольный текстовый файл в формате txt.
 * Нужно собрать все встречающийся слова и посчитать для каждого из них количество раз, сколько слово встретилось.
 * Морфологию не учитываем.</p>
 * <p>Вывести на экран наиболее используемые (TOP) 10 слов и наименее используемые (LAST) 10 слов</p>
 * <p>Проверить работу на романе Льва Толстого “Война и мир”</p>
 *
 * @author vpyzhyanov
 * @since 19.10.2023
 */
public class WarAndPeace
{
    private static final Path WAR_AND_PEACE_FILE_PATH = Path.of("src/main/resources",
            "Лев_Толстой_Война_и_мир_Том_1,_2,_3,_4_(UTF-8).txt");
    /**
     * По умолчанию LinkedHashMap использует метод hashCode() класса String,
     * который хорошо распределяет хэш-значения для строк.
     * Поэтому вставка и чтение будет происходить за O(1).
     * Для еще большей оптимизации, в конструкторе можно указать количество изначально
     * хранящихся баккетов, это поможет создать заранее готовое
     * количество баккетов для исключения коллизий и уменьшения затрат на память (так как при стандарном размере
     * LinkedHashMap динамически расширяется).
     */
    public static void main(String[] args) {
        int capacity = uniqueWordsCount() * 2; // O(n)
        Map<String, Integer> mapCount = new LinkedHashMap<>(capacity);
        /**
         * O(n), т.к. перебор O(n) * вставка у LinkedHashMap O(1).
         */
        new WordParser(WAR_AND_PEACE_FILE_PATH)
                .forEachWord(word -> {
                    mapCount.put(word, mapCount.getOrDefault(word, 0) + 1);
                });
        List<Map.Entry<String, Integer>> sortedList = mapSort(mapCount); // O(n + nLog(n))

        topWords(sortedList); // O(1)
        lastWords(sortedList); // O(1)
        // Весь алгоритм имеет сложность O(3n + nLog(n))
    }

    /**
     * Считает количество уникальных слов
     * Сложность будет O(n), так как имеем перебор по тексту,
     * добавление элементов в множество O(1) (у String определен hashCode),
     * операция size O(1), так как коллекция возвращает поле с размером
     */
    private static int uniqueWordsCount() {
        Set<String> wordsSet = new HashSet<>();
        new WordParser(WAR_AND_PEACE_FILE_PATH)
                .forEachWord(word -> {
                    wordsSet.add(word);
                });
        return wordsSet.size();
    }

    /**
     * Копирование словаря в список происходит перебором => O(n)
     * Как указано в JavaDoc, Collection.sort имеет сложность O(nLog(n))
     * => весь алгоритм имеет сложность O(n + nLog(n))
     */
    private static List<Map.Entry<String, Integer>> mapSort(Map<String, Integer> map) {
        List<Map.Entry<String, Integer>> sortedList = new ArrayList<>(map.entrySet());
        sortedList.sort(Map.Entry.comparingByValue());

        return sortedList;
    }

    /**
     * O(1), так как происходит перебор фиксированного количества элементов.
     */
    private static void lastWords(List<Map.Entry<String, Integer>> words) {
        System.out.println("LAST 10 наименее используемых слов: ");
        for (int i = 0; i < 10; i++) {
            System.out.println(words.get(i));
        }
        System.out.println();
    }

    /**
     * O(1), аналогично.
     */
    private static void topWords(List<Map.Entry<String, Integer>> words) {
        System.out.println("TOP 10 наиболее используемых слов: ");
        for (int i = words.size() - 1; i > words.size() - 11; i--) {
            System.out.println(words.get(i));
        }
        System.out.println();
    }
}
