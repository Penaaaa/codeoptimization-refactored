/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.dam.codeoptimization.fantasy;
import es.dam.codeoptimization.PlayerStats;

/**
 * THE CLASS YOU HAVE TO MODIFY
 * @author Your Name
 */
public class FantasyCalculator {

    // Method to calculate the points
    public static int calculatePoints(PlayerStats stats) {
        int result = 0; 
        
        int minutes = stats.minutes;
        int goals = stats.goals;
        int assists = stats.assists;
        boolean yellowCard = stats.yellowCard;
        boolean redCard = stats.redCard;
        int saves = stats.saves;
        int goalsAgainst = stats.goalsAgainst;
        char matchResult = stats.matchResult;
        String position = stats.position;

        // --- GOALKEEPER LOGIC ---
        if (position.equals("PORTERO")) {
            if (minutes > 0 && minutes < 60) {
                result = result + 3;
            } else if (minutes >= 60) {
                result = result + 5;
            }

            for (int i = 0; i < goals; i++) {
                result = result + 5;
            }

            result = result + (assists * 6);

            // 1 point per save
            result = result + saves; 
            
            if (goalsAgainst == 0) {
                result = result + 5; 
            } else if (goalsAgainst == 1) {
                result = result + 3;
            } else if (goalsAgainst == 2) {
                result = result + 1;
            }

            if (yellowCard == true) result = result - 3; 
            if (redCard == true) result = result - 5;
            
            if (matchResult == 'G') {
                result = result + 5;
            } else if (matchResult == 'E') {
                result = result + 2;
            }

        // --- DEFENDER LOGIC ---
        } else if (position.equals("DEFENSA")) {
            if (minutes > 0 && minutes < 60) {
                result = result + 3;
            } else if (minutes >= 60) {
                result = result + 5;
            }

            for (int i = 0; i < goals; i++) {
                result = result + 5;
            }

            result = result + (assists * 6);

            if (goalsAgainst == 0) {
                result = result + 5; 
            } else if (goalsAgainst == 1) {
                result = result + 3;
            } else if (goalsAgainst == 2) {
                result = result + 1;
            }

            if (yellowCard == true) result = result - 3;
            if (redCard == true) result = result - 5;
            
            if (matchResult == 'G') {
                result = result + 5;
            } else if (matchResult == 'E') {
                result = result + 2;
            }

        // --- MIDFIELDER LOGIC ---
        } else if (position.equals("MEDIO")) {
            if (minutes > 0 && minutes < 60) {
                result = result + 3;
            } else if (minutes >= 60) {
                result = result + 5;
            }

            for (int i = 0; i < goals; i++) {
                result = result + 5;
            }

            result = result + (assists * 6);

            if (yellowCard == true) result = result - 3;
            if (redCard == true) result = result - 5;
            
            if (matchResult == 'G') {
                result = result + 5;
            } else if (matchResult == 'E') {
                result = result + 2;
            }

        // --- FORWARD LOGIC ---
        } else if (position.equals("DELANTERO")) {
            if (minutes > 0 && minutes < 60) {
                result = result + 3;
            } else if (minutes >= 60) {
                result = result + 5;
            }

            for (int i = 0; i < goals; i++) {
                result = result + 6;
            }

            result = result + (assists * 5);

            if (yellowCard == true) result = result - 3;
            if (redCard == true) result = result - 5;
            
            if (matchResult == 'G') {
                result = result + 5;
            } else if (matchResult == 'E') {
                result = result + 2;
            }
        }

        return result;
    }
}
