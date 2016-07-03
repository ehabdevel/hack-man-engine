/*
 * Copyright 2016 riddles.io (developers@riddles.io)
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 *
 *     For the full copyright and license information, please view the LICENSE
 *     file that was distributed with this source code.
 */

package io.riddles.bookinggame.game.move;

import java.util.ArrayList;

import io.riddles.bookinggame.game.data.Direction;
import io.riddles.bookinggame.game.player.BookingGamePlayer;
import io.riddles.javainterface.exception.InvalidMoveException;
import io.riddles.javainterface.serialize.Deserializer;

/**
 * io.riddles.catchfrauds.game.move.BookingGameMoveDeserializer - Created on 8-6-16
 *
 * [description]
 *
 * @author jim
 */
public class BookingGameMoveDeserializer implements Deserializer<BookingGameMove> {

    private BookingGamePlayer player;
    private int checkPointCount;

    public BookingGameMoveDeserializer(BookingGamePlayer player, int checkPointCount) {
        this.player = player;
        this.checkPointCount = checkPointCount;
    }

    @Override
    public BookingGameMove traverse(String string) {
        try {
            return visitMove(string);
        } catch (InvalidMoveException ex) {
            return new BookingGameMove(this.player, ex);
        } catch (Exception ex) {
            return new BookingGameMove(
                this.player, new InvalidMoveException("Failed to parse move"));
        }
    }

    private BookingGameMove visitMove(String input) throws InvalidMoveException {
        String[] split = input.split(" ");

        Direction direction = visitAssessment(split[0]);

        String checkPointInput = null;
        return new BookingGameMove(this.player, direction);
    }

    public Direction visitAssessment(String input) throws InvalidMoveException {
        switch (input) {
            case "up":
                return Direction.UP;
            case "down":
                return Direction.DOWN;
            case "left":
                return Direction.LEFT;
            case "right":
                return Direction.RIGHT;
            case "pass":
                return Direction.PASS;
            default:
                throw new InvalidMoveException("Move isn't valid");
        }
    }
}
