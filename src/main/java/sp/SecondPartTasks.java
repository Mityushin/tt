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

        List<String> result = new ArrayList<>();

        for (String strPath : paths) {

            Path path = Paths.get(strPath);

            try (Stream<String> lineStream = Files.lines(path)) {

                result.addAll(
                        lineStream
                                .filter(x -> x.contains(sequence))
                                .collect(Collectors.toList()));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return result;

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