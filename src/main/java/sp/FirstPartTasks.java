package sp;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public final class FirstPartTasks {

    private FirstPartTasks() {
    }

    // Список названий альбомов
    public static List<String> allNames(Stream<Album> albums) {
        return albums
                .map(Album::getName)
                .collect(Collectors.toList());
    }

    // Список названий альбомов, отсортированный лексикографически по названию
    public static List<String> allNamesSorted(Stream<Album> albums) {
        return albums
                .map(Album::getName)
                .sorted()
                .collect(Collectors.toList());
    }

    // Список треков, отсортированный лексикографически по названию, включающий все треки альбомов из 'albums'
    public static List<String> allTracksSorted(Stream<Album> albums) {
        return albums
                .map(Album::getTracks)
                .flatMap(List::stream)
                .map(Track::getName)
                .sorted()
                .collect(Collectors.toList());
    }

    // Список альбомов, в которых есть хотя бы один трек с рейтингом более 95, отсортированный по названию
    public static List<Album> sortedFavorites(Stream<Album> albums) {
        return albums
                .filter(album ->
                        !album
                                .getTracks()
                                .stream()
                                .filter(track -> track.getRating() > 95)
                                .collect(Collectors.toList())
                                .isEmpty())
                .sorted(Comparator.comparing(Album::getName))
                .collect(Collectors.toList());
    }

    // Сгруппировать альбомы по артистам
    public static Map<Artist, List<Album>> groupByArtist(Stream<Album> albums) {
        return albums
                .collect(Collectors.groupingBy(Album::getArtist, Collectors.toList()));
    }

    // Число повторяющихся альбомов в потоке
    public static long countAlbumDuplicates(Stream<Album> albums) {
        return albums
                .collect(Collectors.groupingBy(x -> x, Collectors.counting()))
                .entrySet()
                .stream()
                .filter(x -> x.getValue() > 1)
                .count();
    }

    // Альбом, в котором максимум рейтинга минимален
    // (если в альбоме нет ни одного трека, считать, что максимум рейтинга в нем --- 0)
    public static Optional<Album> minMaxRating(Stream<Album> albums) {
        return albums
                .collect(Collectors.toMap(
                        Function.identity(),
                        (Album x) -> {
                            List<Track> list;
                            if ((list = x.getTracks()).isEmpty()) {
                                return 0;
                            }
                            return list.stream()
                                    .map(Track::getRating)
                                    .max(Integer::compareTo)
                                    .get();
                        }
                ))
                .entrySet()
                .stream()
                .min(Map.Entry.comparingByValue(Integer::compare))
                .map(Map.Entry::getKey);
    }

    // Список альбомов, отсортированный по убыванию среднего рейтинга его треков (0, если треков нет)
    public static List<Album> sortByAverageRating(Stream<Album> albums) {
        return albums
                .collect(Collectors.toMap(
                        Function.identity(),
                        (Album x) -> {
                            long count = (long) x.getTracks().size();
                            if (count == 0) {
                                return 0L;
                            }
                            int sum = x.getTracks().stream()
                                    .mapToInt(Track::getRating)
                                    .sum();
                            return sum / count;
                        })
                )
                .entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue(Long::compare)))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    // Произведение всех чисел потока по модулю 'modulo'
    // (все числа от 0 до 10000)
    public static int moduloProduction(IntStream stream, int modulo) {
        return stream
                .reduce((a, b) -> ((a % modulo) * (b % modulo) % modulo))
                .getAsInt();
    }

    // Вернуть строку, состояющую из конкатенаций переданного массива, и окруженную строками "<", ">"
    // см. тесты
    public static String joinTo(String... strings) {

        return "<".concat(Arrays.stream(strings)
                .collect(Collectors.joining(", ")))
                .concat(">");
    }

    // Вернуть поток из объектов класса 'clazz'
    public static <R> Stream<R> filterIsInstance(Stream<?> s, Class<R> clazz) {
        return s
                .filter(clazz::isInstance)
                .map(x -> (R) x);
    }
}
