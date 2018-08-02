package sp;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
        return compositions
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        (Map.Entry<String, List<String>> x) -> x.getValue()
                                .stream()
                                .mapToInt((String y) -> {
                                    Path path = Paths.get(y);
                                    int result = 0;
                                    try (Stream<String> lineStream = Files.lines(path)) {
                                        result = lineStream
                                                .mapToInt(String::length)
                                                .sum();

                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    return result;
                                })
                                .sum()
                ))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue(Integer::compare))
                .map(Map.Entry::getKey)
                .get();
    }
}