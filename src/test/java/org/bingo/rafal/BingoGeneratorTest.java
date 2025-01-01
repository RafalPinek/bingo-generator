package org.bingo.rafal;

import org.bingo.rafal.bingo90.Bingo90Generator;
import org.bingo.rafal.bingo90.BingoUtil;
import org.bingo.rafal.bingo90.Strip;
import org.bingo.rafal.bingo90.Ticket;
import org.junit.jupiter.api.Test;

import java.util.List;

import org.junit.jupiter.api.Assertions;

import java.time.Duration;
import java.util.LinkedList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;


public class BingoGeneratorTest
{

    /**
     * Tests that generated strip met all of the required conditions described in the assignment
     */
    @Test
    void testGeneratedStripMetAllRequiredConditions() {
        Bingo90Generator bingoGenerator = new Bingo90Generator(new BingoUtil());
        Strip strip = bingoGenerator.generateStrip();

        strip.print();
        assertNumbersCountPerRow(strip);
        assertNoEmptyColumnsPerTicket(strip);
        assertOrderedColumnsPerTicket(strip);
        assertAllNumbersAreFilled(strip);
    }

    /**
     * Tests that generated strip met all the conditions described in the assignment
     */
    @Test
    void testStripGenerationPerformance() {
        Bingo90Generator bingoGenerator = new Bingo90Generator(new BingoUtil());

        Assertions.assertTimeout(Duration.ofSeconds(1), () -> {
            for (int i = 0; i < 10000; i++) {
                bingoGenerator.generateStrip();
            }
        });
    }

    /**
     * Asserts each row has exactly {@link Strip#MAX_NUMBERS_PER_ROW} numbers
     *
     * @param strip the strip to assert
     */
    private void assertNumbersCountPerRow(Strip strip) {
        for (int row = 0; row < Strip.ROWS_COUNT; row++) {

            int numbersPerRow = 0;

            for (int column = 0; column < Strip.COLUMNS_COUNT; column++) {
                if (strip.isFilled(row, column)) {
                    numbersPerRow++;
                }
            }
            assertEquals(Strip.MAX_NUMBERS_PER_ROW, numbersPerRow);
        }
    }

    /**
     * Asserts all tickets has columns filled with at least one number
     *
     * @param strip the strip to assert
     */
    private void assertNoEmptyColumnsPerTicket(Strip strip) {

        for (int ticketNumber = 0; ticketNumber < Strip.TICKETS_COUNT; ticketNumber++) {

            Ticket ticket = new Ticket(strip, ticketNumber);

            for (int column = 0; column < Strip.COLUMNS_COUNT; column++) {

                assertFalse(ticket.hasUnfilledColumn(column));
            }
        }
    }

    /**
     * Asserts the numbers withing a ticket column are sorted
     *
     * @param strip the strip to assert
     */
    private void assertOrderedColumnsPerTicket(Strip strip) {

        for (int ticketNumber = 0; ticketNumber < Strip.TICKETS_COUNT; ticketNumber++) {

            Ticket ticket = new Ticket(strip, ticketNumber);

            for (int column = 0; column < Strip.COLUMNS_COUNT; column++) {

                int startRow = ticket.getStartRow();
                int finalColumn = column;

                List<Integer> filledNumbersOnColumn = IntStream.range(startRow, startRow + Strip.ROWS_COUNT_PER_TICKET)
                        .filter(row -> strip.isFilled(row, finalColumn))
                        .boxed()
                        .collect(Collectors.toList());

                assertEquals(filledNumbersOnColumn.stream().sorted().toList(), filledNumbersOnColumn);
            }
        }
    }

    /**
     * Asserts all possible numbers in the strip are filled
     *
     * @param strip the strip to assert
     */
    private void assertAllNumbersAreFilled(Strip strip) {

        for (int column = 0; column < Strip.COLUMNS_COUNT; column++) {

            List<Integer> possibleNumbers = new LinkedList<>(strip.getPossibleNumbersPerColumn(column));

            for (int row = 0; row < Strip.ROWS_COUNT; row++) {

                if (strip.isFilled(row, column)) {

                    int number = strip.getNumber(row, column);

                    assertTrue(possibleNumbers.contains(number));
                    possibleNumbers.removeIf(integer -> integer == number);
                }
            }

            assertTrue(possibleNumbers.isEmpty());
        }
    }

}
