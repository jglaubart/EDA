import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;


public class TimerTest {
    MyJodaTimer timer;
    @BeforeEach
    void instanceTimer(){
        this.timer = new MyJodaTimer(10); //siempre vuelve a empezar en 10
    }

    @Test
    @DisplayName("Probar si se mide bien la cantidad total de ms")
    void getTotalMilliTest(){
        timer.stop(65);
        assertEquals(55, timer.getTotalMillis());
    }

    @Test
    @DisplayName("Probar si la excepcion del stop funciona")
    void getStopExceptionTest(){
        assertThrows(RuntimeException.class, ()->timer.stop(1));
    }



    // @BeforeAll se ejecuta antes de toos los casos de prueba de la clase
//    @BeforeEach se ejecuta antes de cada @Test
//    @AfterEach se ejecuta antes de cada @Test
//    @AfterAll se ejecuta antes de toos los casos de prueba de la clase


@BeforeAll
static void initAll() {
System.out.println("Empiezan los tests");
}

@BeforeEach
void init() {
System.out.println("Empieza un test");
}

@AfterEach
void tearDown() {
System.out.println("Termina un test");
}

@AfterAll
static void tearDownAll() {
System.out.println("Terminaron todos los tests");
}


}
