package org.bingo.rafal.bingo90;

import java.util.*;

public class Bingo90Generator {

    private final BingoUtil bingoUtil;

    public Bingo90Generator(BingoUtil bingoUtil) {
        this.bingoUtil = bingoUtil;
    }

    /**
     * Generate a Bingo 90 strip
     *
     * @return the generated strip
     */
    public Strip generateStrip() {
        Strip strip = new Strip();

        backtrackingPositionGeneration(strip);
        replacePositionsWithRandomNumbers(strip);
        return strip;
    }

    /**
     * Generates positions for a strip with a recursive backtracking algorithm
     *
     * @param strip the strip to be filled with generated positions
     *
     * @return true if all positions were filled. False otherwise
     */
    private boolean backtrackingPositionGeneration(Strip strip) {
        //validate if the current state can lead to a solution
        if (!bingoUtil.hasValidState(strip)) {
            return false;
        }

        for (int column = 0; column < Strip.COLUMNS_COUNT; column++) {

            if (strip.columnIsFilled(column)) {
                continue;
            }

            List<Integer> possiblePositions = bingoUtil.getPossiblePositionsForColumn(strip, column);
            int positionsCountLeftToBeFilled = strip.getPositionsCountToBeFilledForColumn(column);

            Collections.shuffle(possiblePositions);

            for (int i = 0; i < possiblePositions.size(); i++) {

                if (positionsCountLeftToBeFilled > possiblePositions.size() - i) {
                    return false;
                }

                int row = possiblePositions.get(i);

                strip.fillPosition(row, column);

                if (backtrackingPositionGeneration(strip)) {
                    return true;
                }

                strip.undoFilling(row, column);
            }

            // if current column is not filled after last 'for' cycle, then we should go back to the last solution
            if (!strip.columnIsFilled(column)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Takes a strip with generated positions and randomly feels those positions with numbers
     *
     * @param strip the strip to be filled with numbers
     */
    private void replacePositionsWithRandomNumbers(Strip strip) {

        for (int column = 0; column < Strip.COLUMNS_COUNT; column++) {

            List<Integer> possibleNumbers = strip.getPossibleNumbersPerColumn(column);

            Collections.shuffle(possibleNumbers);

            int possibleNumberIndex = 0;

            for (int row = 0; row < Strip.ROWS_COUNT; row++) {
                if (strip.isFilled(row, column)) {
                    strip.placeNumber(row, column, possibleNumbers.get(possibleNumberIndex++));
                }
            }
        }

        strip.sortColumnsPerTicket();
    }


}
