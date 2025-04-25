package C1_2022;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProximityIndexTester {
    private ProximityIndex index = new ProximityIndex();

    @BeforeEach
    void initialize() {
        index = new ProximityIndex();
    }

    @Test
    void test1(){
        index.initialize(new String[]{"Ana", "Carlos", "Juan", "Yolanda"});
        assertEquals("Yolanda", index.search("Carlos" ,2));
    }

    @Test
    void test2(){
        index.initialize(new String[]{"Ana", "Carlos", "Juan", "Yolanda"});
        assertEquals("Carlos", index.search("Carlos" ,0));
    }

    @Test
    void test3(){
        index.initialize(new String[]{"Ana", "Carlos", "Juan", "Yolanda"});
        assertEquals("Ana", index.search("Carlos" ,3));
    }

    @Test
    void test4(){
        index.initialize(new String[]{"Ana", "Carlos", "Juan", "Yolanda"});
        assertEquals("Juan", index.search("Ana" ,14));
    }

    @Test
    void test5(){
        index.initialize(new String[]{"Ana", "Carlos", "Juan", "Yolanda"});
        assertEquals("Juan", index.search("Ana" ,-2));
    }

    @Test
    void test6(){
        index.initialize(new String[]{"Ana", "Carlos", "Juan", "Yolanda"});
        assertEquals("Yolanda", index.search("Ana" ,-17));
    }

    @Test
    void test7(){
        index.initialize(new String[]{"Ana", "Carlos", "Juan", "Yolanda"});
        assertEquals("Juan", index.search("Juan" ,-4));
    }

    @Test
    void notFound(){
        index.initialize(new String[]{"Ana", "Carlos", "Juan", "Yolanda"});
        assertNull(index.search("X" ,2));
    }

    @Test
    void nullElements(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            index.initialize(null);
        });
    }

    @Test
    void emptyElements(){
        index.initialize(new String[]{});
        assertNull(index.search("Ana" ,2));
    }

    @Test
    void unsortedElements(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            index.initialize(new String[]{"Ana", "Carlos", "Juan", "Yolanda", "Carlos"});
        });
    }

    @Test
    void duplicates(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            index.initialize(new String[]{"Ana", "Carlos", "Carlos", "Juan", "Yolanda"});
        });
    }
}
