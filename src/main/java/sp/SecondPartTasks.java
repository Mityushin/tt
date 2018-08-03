package sp;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class SecondPartTasks {

    private SecondPartTasks() {
    }

    // Найти строки из переданных файлов, в которых встречается указанная подстрока.
    public static List<String> findQuotes(List<String> paths, CharSequence sequence) {
        return paths.stream()
                .map(x -> Paths.get(x))
                .flatMap(y -> {
                    try (Stream<String> data = Files.lines(y)) {
                        return data.filter(z -> z.contains(sequence));
                    } catch (IOException e) {
                        return Stream.empty();
                    }
                })
                .collect(Collectors.toList());
    }

    // Дано отображение из имени автора в список с содержанием его произведений.
    // Надо вычислить, чья общая длина произведений наибольшая.
    public static String findPrinter(Map<String, List<String>> compositions) {
        return compositions.entrySet().stream()
                .map(x -> new AbstractMap.SimpleEntry<>(x.getKey(), x.getValue().stream().mapToLong(String::length).sum()))
                .max(Comparator.comparingLong(AbstractMap.SimpleEntry::getValue))
                .map(AbstractMap.SimpleEntry::getKey).orElse(null);
    }
}