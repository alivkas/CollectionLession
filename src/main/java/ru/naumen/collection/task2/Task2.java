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
     * Возвращает дубликаты пользователей, которые есть в обеих коллекциях
     */
    public static List<User> findDuplicates(Collection<User> collA, Collection<User> collB) {
        /**
         * LinkedList, так как работа метода retainAll основывается на переборе, проверке
         * содержимого двух коллекций и удалении элемета. В отличие от HashSet он будет бытсрее итерироваться
         * и в отличие от ArrayList он будет быстрее удалять, так как коллекция не гарантирует, что уникальные элементы
         * будут находиться в конце списка. Копирование коллекции в LinkedList происходит
         * за O(n), retainAll в данном случае будет работать за O(n), так как перебор происходит за O(n),
         * проверка на наличие элемента вызывается в HashSet, у которого определена хэш функция => O(1)
         * и операция удаления у связного списка идет за O(1).
         */
        List<User> userList = new LinkedList<>(collA); // O(n)
        userList.retainAll(new HashSet<>(collB)); // O(n) + O(n), так как тут еще копируется коллекия в HashSet.

        return userList; // => алгоритм имеет сложность O(3n)
    }
}
