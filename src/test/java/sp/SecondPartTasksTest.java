package sp;

import org.junit.*;
import org.junit.rules.TemporaryFolder;

import java.io.*;
import java.util.*;

import static org.junit.Assert.*;
import static sp.SecondPartTasks.*;

public class SecondPartTasksTest {

    @Test
    public void testFindQuotes() {
        assertEquals(
                Arrays.asList(BOOK_2_CONTENT),
                findQuotes(Arrays.asList(BOOK_0, BOOK_1, BOOK_2, BOOK_3, BOOK_4), "ccc")
        );
    }

    @Test
    public void testFindPrinter() {
        Map<String, List<String>> map = new HashMap<>();
        map.put(AUTHOR_0, Arrays.asList(BOOK_0, BOOK_1));
        map.put(AUTHOR_1, Arrays.asList(BOOK_2, BOOK_3));
        map.put(AUTHOR_2, Arrays.asList(BOOK_4));
        assertEquals(
                AUTHOR_1,
                findPrinter(map));
    }

    @BeforeClass
    public static void setUp() throws IOException {

        Map<String, String> books = new HashMap<>();
        books.put(BOOK_0, BOOK_0_CONTENT);
        books.put(BOOK_1, BOOK_1_CONTENT);
        books.put(BOOK_2, BOOK_2_CONTENT);
        books.put(BOOK_3, BOOK_3_CONTENT);
        books.put(BOOK_4, BOOK_4_CONTENT);

        for (Map.Entry<String, String> book : books.entrySet()) {
            FileWriter writer = new FileWriter(folder.newFile(book.getKey()));
            writer.write(book.getValue());
            writer.flush();
            writer.close();
        }

    }

    @ClassRule
    public static TemporaryFolder folder = new TemporaryFolder();


    private static final String AUTHOR_0 = "Ivan";
    private static final String AUTHOR_1 = "Vanya";
    private static final String AUTHOR_2 = "Kate";

    private static final String BOOK_0 = "book_0.txt";
    private static final String BOOK_1 = "book_1.txt";
    private static final String BOOK_2 = "book_2.txt";
    private static final String BOOK_3 = "book_3.txt";
    private static final String BOOK_4 = "book_4.txt";

    private static final String BOOK_0_CONTENT = "aaaaaa";
    private static final String BOOK_1_CONTENT = "bbbbbb";
    private static final String BOOK_2_CONTENT = "cccccc";
    private static final String BOOK_3_CONTENT = "dddddd";
    private static final String BOOK_4_CONTENT = "eeeeee";
}