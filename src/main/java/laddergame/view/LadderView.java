package laddergame.view;

import static laddergame.domain.line.LineStatus.CONNECTION;

import java.util.List;
import laddergame.domain.ladder.LadderGame;
import laddergame.domain.line.Line;
import laddergame.domain.line.LineStatus;
import laddergame.domain.results.Results;

public class LadderView {

    private static final String WIDTH_LINE = "------";
    private static final String BLANK = "      ";
    private static final String HEIGHT_LINE = "|";
    private static final String NEW_LINE = "\n";

    private final LadderGame ladderGame;
    private final Results results;

    public LadderView(final LadderGame ladderGame, final Results results) {
        this.ladderGame = ladderGame;
        this.results = results;
    }

    public void printLadder() {
        System.out.println("실행결과");
        System.out.println();

        StringBuilder sb = new StringBuilder();
        printNames(sb);
        printLadder(sb);

        results.get().forEach(result -> sb.append(result).append(BLANK));

        System.out.println(sb);
    }


    private void printNames(final StringBuilder sb) {
        ladderGame.getNames().forEach(name -> sb.append(name).append(BLANK));
        sb.append(NEW_LINE);
    }

    private void printLadder(final StringBuilder sb) {
        List<Line> linePerDepth = ladderGame.getLinePerDepth();
        linePerDepth.forEach(line -> {
            sb.append(HEIGHT_LINE);
            line.getPoints().forEach(status -> printLine(sb, status));
            sb.append(NEW_LINE);
        });
    }

    private void printLine(final StringBuilder sb, final LineStatus status) {
        if (status == CONNECTION) {
            sb.append(WIDTH_LINE).append(HEIGHT_LINE);
            return;
        }
        sb.append(BLANK).append(HEIGHT_LINE);
    }

}

