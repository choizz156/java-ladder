package laddergame.domain.results;

import static laddergame.domain.results.Movement.DOWN;
import static laddergame.domain.results.Movement.LEFT;
import static laddergame.domain.results.Movement.RIGHT;

import java.util.List;
import laddergame.domain.line.Line;
import laddergame.domain.line.LineStatus;

public class RewordMatching implements MatchStrategy {

    private static final int FIRST = 0;

    @Override
    public int getMatchedIndex(int position, List<Line> lines) {
        for (Line line : lines) {
            position = getIndex(position, line.getStatus());
        }
        return position;
    }

    private int getIndex(int position, final List<LineStatus> status) {

        if (isFirst(position)) {
            return connectFirstIndex(position, status);
        }

        if (isMiddle(position, status)) {
            return connectMiddleIndex(position, status);
        }

        return connectLastIndex(position, status);
    }

    private int connectFirstIndex(int position, final List<LineStatus> status) {
        return isRightConnected(position, status) ? RIGHT.move(position) : DOWN.move(position);
    }

    private int connectMiddleIndex(int position, final List<LineStatus> status) {
        if (isLeftConnected(position, status)) {
            return LEFT.move(position);
        }

        if (isRightConnected(position, status)) {
            return RIGHT.move(position);
        }

        return DOWN.move(position);
    }

    private int connectLastIndex(int position, List<LineStatus> status) {
        return isLeftConnected(position, status) ? LEFT.move(position) : DOWN.move(position);
    }

    private boolean isLeftConnected(final int position, final List<LineStatus> status) {
        return status.get(position - 1) == LineStatus.CONNECTION;
    }

    private boolean isRightConnected(final int position, final List<LineStatus> status) {
        return status.get(position) == LineStatus.CONNECTION;
    }

    private boolean isMiddle(final int position, final List<LineStatus> status) {
        return position != FIRST && status.size() != position;
    }

    private boolean isFirst(final int position) {
        return position == FIRST;
    }
}
