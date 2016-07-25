package io.riddles.bookinggame.game.data;

import io.riddles.bookinggame.game.player.BookingGamePlayer;
import io.riddles.bookinggame.game.state.BookingGameState;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by joost on 7/11/16.
 */
public class BookingGameBoard extends Board {
    protected String[][] fieldsComplete;

    public BookingGameBoard(int w, int h) {

        super(w, h);
        fieldsComplete = new String[w][h];
    }

    public void dump(ArrayList<BookingGamePlayer> players, BookingGameState state) {
        ArrayList<Enemy> enemies = state.getEnemies();

       Coordinate lc = getLoneliestField(players);

        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                String s = fields[x][y];
                for (int i = 0; i < players.size(); i++) {
                    if (players.get(i).getCoordinate().getX() == x && players.get(i).getCoordinate().getY() == y) {
                        s = String.valueOf(players.get(i).getId());
                    }
                }
                for (int i = 0; i < enemies.size(); i++) {
                    if (enemies.get(i).getCoordinate().getX() == x && enemies.get(i).getCoordinate().getY() == y) {
                        s = "E";
                    }
                }
                if (x == lc.getX() && y == lc.getY()) s = "#";
                System.out.print(s);
            }
            System.out.println();
        }
    }

    public String toRepresentationString(ArrayList<BookingGamePlayer> players, BookingGameState state) {
        ArrayList<Enemy> enemies = state.getEnemies();
        String output = "";
        int counter = 0;
        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                String s = fields[x][y];
                for (int i = 0; i < players.size(); i++) {
                    if (players.get(i).getCoordinate().getX() == x && players.get(i).getCoordinate().getY() == y) {
                        s = String.valueOf(players.get(i).getId());
                    }
                }
                for (int i = 0; i < enemies.size(); i++) {
                    if (enemies.get(i).getCoordinate().getX() == x && enemies.get(i).getCoordinate().getY() == y) {
                        s = "E";
                    }
                }
                if (counter > 0) output += ",";
                output += s;
                counter++;
            }
        }
        return output;
    }

    public void updateComplete(ArrayList<BookingGamePlayer> players, BookingGameState state) {
        ArrayList<Enemy> enemies = state.getEnemies();
        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                fieldsComplete[x][y] = fields[x][y];
                for (int i = 0; i < players.size(); i++) {
                    if (players.get(i).getCoordinate().getX() == x && players.get(i).getCoordinate().getY() == y) {
                        fieldsComplete[x][y] = String.valueOf(players.get(i).getId());
                    }
                }
                for (int i = 0; i < enemies.size(); i++) {
                    if (enemies.get(i).getCoordinate().getX() == x && enemies.get(i).getCoordinate().getY() == y) {
                        fieldsComplete[x][y] = "E";
                    }
                }
            }
        }
    }

    public Boolean isEmptyComplete(Coordinate c) {
        if (c.getX() < 0 || c.getY() < 0 || c.getX() >= this.width || c.getY() >= this.height) {
            return false;
        }
         return (!fieldsComplete[c.getX()][c.getY()].equals("x"));
    }

    public String toStringComplete() {
        String s = "";
        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                s += fieldsComplete[x][y];
            }
        }
        return s;
    }



    /* Returns coordinate of empty field furthest away from all players */
    public Coordinate getLoneliestField(ArrayList<BookingGamePlayer> players) {
        Coordinate c = new Coordinate(0,0);
        int score = 0;
        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                int minDistance = Integer.MAX_VALUE;
                for (int i = 0; i < players.size(); i++) {
                    Coordinate playerC = players.get(i).getCoordinate();
                    int distance = Math.abs(x - playerC.getX());
                    distance += Math.abs(y - playerC.getY());
                    if (minDistance > distance) minDistance = distance;
                }
                if (minDistance > score && this.fields[x][y].equals(".")) {
                    score = minDistance;
                    c = new Coordinate(x,y);
                }
            }
        }
        return c;
    }


    public boolean addSnippet(Coordinate c) {
        if (this.fields[c.getX()][c.getY()].equals(".")) {
            this.fields[c.getX()][c.getY()] = "C";
            return true;
        }
        return false;
    }

    public void addRandomSnippet() {
        boolean success = false;
        if (this.getNrAvailableFields() > 0 ) {
            Random r = new Random();
            while (!success) {
                int x = r.nextInt(this.width);
                int y = r.nextInt(this.height);
                if (this.fields[x][y].equals(".")) {
                    this.fields[x][y] = "C";
                    success = true;
                }
            }
        }
    }
}