package minimal.sequence;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import static org.junit.Assert.*;

/**
 * シーケンス {@link Sequence} をテストします。
 */
public class SequenceTest {
    @Test
    public void testIsEmpty() throws Exception {
        assertTrue(Sequence.of().isEmpty());
        assertFalse(Sequence.of(1).isEmpty());
        assertFalse(Sequence.of(1, 2).isEmpty());
    }

    @Test
    public void testSize() throws Exception {
        assertEquals(0, Sequence.of().size());
        assertEquals(1, Sequence.of(1).size());
        assertEquals(2, Sequence.of((Iterable<Integer>) Arrays.asList(1, 2)).size());
    }

    @Test
    public void testEach() throws Exception {
        int[] sum = new int[]{0};
        Sequence.of(1, 2, 3, 4).each(x -> sum[0] += x);
        assertEquals(10, sum[0]);
    }

    @Test
    public void testMap() throws Exception {
        assertArrayEquals(new Integer[]{1, 4, 9}, Sequence.of(1, 2, 3).map(x -> x * x).toArray());
        assertArrayEquals(new Integer[]{}, Sequence.<Integer>of().map(x -> x * x).toArray());
    }

    @Test
    public void testFilter() throws Exception {
        assertArrayEquals(new Integer[]{2, 4}, Sequence.of(1, 2, 3, 4).filter(x -> x % 2 == 0).toArray());
        assertArrayEquals(new Integer[]{}, Sequence.<Integer>of().filter(x -> true).toArray());
    }

    @Test
    public void testAppend() throws Exception {
        assertArrayEquals(new String[]{"ab", "cd", "ef", "gh"}, Sequence.of("ab", "cd").append("ef", "gh").toArray());
        assertArrayEquals(new String[]{"ef", "gh"}, Sequence.of().append("ef", "gh").toArray());
    }

    @Test
    public void testPrepend() throws Exception {
        assertArrayEquals(new String[]{"ab", "cd", "ef", "gh"}, Sequence.of("ef", "gh").prepend("ab", "cd").toArray());
        assertArrayEquals(new String[]{"ef", "gh"}, Sequence.of().prepend("ef", "gh").toArray());
    }

    @Test
    public void testFirst() throws Exception {
        assertEquals(Maybe.of(1), Sequence.of(1, 2, 3).first());
        assertEquals(Maybe.nothing, Sequence.of().first());
    }

    @Test
    public void testSingle() throws Exception {
        assertEquals(Maybe.of(1), Sequence.of(1).single());
        assertEquals(Maybe.nothing, Sequence.of().single());
        assertEquals(Maybe.nothing, Sequence.of(1, 2).single());
    }

    @Test
    public void testToHashMap() throws Exception {
        HashMap<String, Integer> map = Sequence.of(1, 2, 3).toHashMap(String::valueOf, x -> x * x);
        assertEquals(3, map.size());
        assertEquals(1, (int) map.get("1"));
        assertEquals(4, (int) map.get("2"));
        assertEquals(9, (int) map.get("3"));
    }

    @Test
    public void testGroupBy() throws Exception {
        LinkedHashMap<Integer, List<Integer>> groups = Sequence.of(0, 1, 2, 3, 4, 5, 6, 7).groupBy(x -> x % 3);
        assertEquals(3, groups.size());
        assertArrayEquals(new Integer[]{0, 3, 6}, groups.get(0).toArray());
        assertArrayEquals(new Integer[]{1, 4, 7}, groups.get(1).toArray());
        assertArrayEquals(new Integer[]{2, 5}, groups.get(2).toArray());
    }
}