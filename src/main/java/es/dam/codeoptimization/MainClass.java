/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package es.dam.codeoptimization;
import es.dam.codeoptimization.fantasy.FantasyCalculator;

/**
 * Main class of the application, DO NOT EDIT
 * @author Boris
 */
public class MainClass {

    public static void main(String[] args) {
        
        // 1. Request data to the user (DO NOT MODIFY THAT)
        PlayerStats playerStats = PlayerInput.askPlayerStats();
        
        // 2. Pass the data to the calculator that you have to refactor
        int puntosTotales = FantasyCalculator.calculatePoints(playerStats);
        
        // 3. Display the final result
        System.out.println("\n=================================");
        System.out.println("PUNTOS FANTASY TOTALES: " + puntosTotales);
        System.out.println("=================================");
    }
}
