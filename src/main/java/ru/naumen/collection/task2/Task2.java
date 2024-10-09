package ru.naumen.collection.task2;

import java.util.*;

/**
 * Дано:
 * <pre>
 * public class User {
 *     private String username;
 *     private String email;
 *     private byte[] passwordHash;
 *     …
 * }
 * </pre>
 * Нужно реализовать метод
 * <pre>
 * public static List<User> findDuplicates(Collection<User> collA, Collection<User> collB);
 * </pre>
 * <p>который возвращает дубликаты пользователей, которые есть в обеих коллекциях.</p>
 * <p>Одинаковыми считаем пользователей, у которых совпадают все 3 поля: username,
 * email, passwordHash. Дубликаты внутри коллекций collA, collB можно не учитывать.</p>
 * <p>Метод должен быть оптимален по производительности.</p>
 * <p>Пользоваться можно только стандартными классами Java SE.
 * Коллекции collA, collB изменять запрещено.</p>
 *
 * См. {@link User}
 *
 * @author vpyzhyanov
 * @since 19.10.2023
 */
public class Task2 {
    /**
     * ArrayList, так как добавление элементов происходит в конце списка => O(1)
     */
    private final static List<User> duplicates = new ArrayList<>();

    /**
     * Возвращает дубликаты пользователей, которые есть в обеих коллекциях
     */
    public static List<User> findDuplicates(Collection<User> collA, Collection<User> collB) {
        /**
         * HashSet, так как работа метода retainAll основывается на переборе и проверке
         * содержимого двух коллекций. Перебор будет работать за O(n), а contains в
         * HashSet будет работать за O(1), при условии, что хэш-функция определена правильно
         * у класса User. Следовательно, копирование коллекции в HashSet будет работать за
         * O(n) (происходит перебор), а его метод reatainAll тоже за O(n) (O(n * 1))
         */
        Set<User> userSet = new HashSet<>(collA); // O(n)
        userSet.retainAll(collB); // O(n)

        /**
         * (m - работа с другим количеством данных), происходит перебор O(m) и добавление в конец списка O(1).
         */
        duplicates.addAll(userSet); // O(m)

        return duplicates; // => алгоритм имеет сложность O(2n + m)
    }
}
