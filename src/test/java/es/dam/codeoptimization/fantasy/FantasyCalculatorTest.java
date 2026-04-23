/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package es.dam.codeoptimization.fantasy;

import es.dam.codeoptimization.PlayerStats;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;

class FantasyCalculatorTest {

    private PlayerStats stats;

    @BeforeEach
    void setUp() {
        stats = new PlayerStats();
    }

    // ==========================================
    // PRUEBAS PARAMETRIZADAS (Escenarios Comunes)
    // ==========================================

    @ParameterizedTest(name = "{0} - {1} mins, Result {2} -> Expected {3}")
    @CsvSource({
        "PORTERO, 90, G, 15", // Base: 5 (min) + 5 (clean sheet) + 5 (win) = 15
        "DEFENSA, 90, G, 15", // Base: 5 (min) + 5 (clean sheet) + 5 (win) = 15
        "MEDIO,   90, G, 10", // Base: 5 (min) + 5 (win) = 10
        "DELANTERO,90, G, 10" // Base: 5 (min) + 5 (win) = 10
    })
    void testBasePointsByPosition(String pos, int mins, char res, int expected) {
        stats.position = pos;
        stats.minutes = mins;
        stats.matchResult = res;
        stats.goalsAgainst = 0; // Para activar clean sheet en GK/DEF
        
        assertEquals(expected, FantasyCalculator.calculatePoints(stats));
    }

    // ==========================================
    // TESTS ESPECÍFICOS POR ROL
    // ==========================================

    @Nested
    @DisplayName("Tests para Porteros (GK)")
    class GoalkeeperTests {
        @Test
        @DisplayName("Ideal Match: Portero imbatible con asistencia")
        void testGKComplete() {
            setStats("PORTERO", 90, 0, 1, 'G');
            stats.saves = 5;        // +5 pts
            stats.goalsAgainst = 0; // +5 pts
            // 5(min) + 6(ast) + 5(saves) + 5(cs) + 5(win) = 26
            assertEquals(26, FantasyCalculator.calculatePoints(stats));
        }

        @Test
        @DisplayName("Lógica de paradas (Saves)")
        void testGKSaves() {
            setStats("PORTERO", 90, 0, 0, 'D');
            stats.saves = 10;
            stats.goalsAgainst = 5; // Sin puntos por goles en contra
            // 5(min) + 10(saves) + 0(loss) = 15
            assertEquals(15, FantasyCalculator.calculatePoints(stats));
        }
    }

    @Nested
    @DisplayName("Tests para Delanteros (FWD)")
    class ForwardTests {
        @Test
        @DisplayName("Hat-trick y tarjeta roja")
        void testFWDHighImpact() {
            setStats("DELANTERO", 90, 3, 0, 'G');
            stats.redCard = true; // -5 pts
            // 5(min) + 18(goals: 3*6) + 5(win) - 5(red) = 23
            assertEquals(23, FantasyCalculator.calculatePoints(stats));
        }
    }

    // ==========================================
    // TESTS DE LÓGICA DE BORDE Y ERRORES
    // ==========================================

    @Nested
    @DisplayName("Casos de Borde y Validaciones")
    class EdgeCases {
        
        @Test
        @DisplayName("Minuto 60 exacto (Límite de puntos por tiempo)")
        void testExactSixtyMinutes() {
            setStats("MEDIO", 60, 0, 0, 'E');
            // 5(min >= 60) + 2(draw) = 7
            assertEquals(7, FantasyCalculator.calculatePoints(stats));
        }

        @Test
        @DisplayName("Minuto 59 (Límite inferior de puntos por tiempo)")
        void testFiftyNineMinutes() {
            setStats("MEDIO", 59, 0, 0, 'E');
            // 3(min < 60) + 2(draw) = 5
            assertEquals(5, FantasyCalculator.calculatePoints(stats));
        }

        @Test
        @DisplayName("Posición no reconocida o nula")
        void testUnknownPosition() {
            stats.position = "COACH";
            stats.minutes = 90;
            // Debería manejar el default (quizás 0 o lanzar excepción)
            // Asumiendo que el código actual devuelve 0 o puntos base de minutos
            assertDoesNotThrow(() -> FantasyCalculator.calculatePoints(stats));
        }
    }

    // Helper para reducir boilerplate
    private void setStats(String pos, int mins, int goals, int assists, char result) {
        stats.position = pos;
        stats.minutes = mins;
        stats.goals = goals;
        stats.assists = assists;
        stats.matchResult = result;
    }
}
