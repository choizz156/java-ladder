package refactoring;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.BDDAssertions.then;
import static refactoring.domain.LineStatus.CONNECTION;
import static refactoring.domain.LineStatus.DETACHMENT;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import refactoring.domain.Direction;
import refactoring.domain.Ladder;
import refactoring.domain.Line;
import refactoring.domain.Match;
import refactoring.domain.MatchResults;
import refactoring.domain.MyLadder;
import refactoring.domain.MyLine;
import refactoring.domain.Players;
import refactoring.domain.Point;
import refactoring.domain.Results;

class MyLadderResultTest {

    @DisplayName("참여자의 이름이 중복될 시 예외를 던집니다.")
    @Test
    void test4() throws Exception {
        assertThatThrownBy(() -> {
            Players.of(new String[]{"a", "a"});
        })
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("중복된 참여자입니다.");
    }

    @DisplayName("입력한 참여자가 없을 시 예외를 던집니다.")
    @Test
    void test6() throws Exception {
        Players player = Players.of(new String[]{"a", "b"});
        assertThatThrownBy(() -> {
            player.getIndex("c");
        })
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("찾을 수 없는 참가자 입니다.");
    }

    @DisplayName("참가자 수와 실행 결과의 수가 일치하지 않으면 예외를 던진다.")
    @Test
    void test2() throws Exception {

        var results = new Results(new String[]{"1000", "0"});

        MyLine first = new MyLine(get3Points(0, 1, 2));
        MyLine second = new MyLine(get3Points(1, 0, 2));
        MyLine third = new MyLine(get3Points(2, 1, 0));

        Ladder myLadder = new MyLadder(List.of(first, second, third), 3);

        assertThatThrownBy(() -> {
            myLadder.match(results, new MatchResults());
        })
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("참여자와 실행결과의 수가 일치하지 않습니다.");
    }

    private List<Point> get3Points(int pos1, int pos2, int pos3) {
        List<Point> lines = new ArrayList<>();
        Point first = new Point(pos1, new Direction(DETACHMENT, DETACHMENT));
        Point second = new Point(pos2, new Direction(DETACHMENT, CONNECTION));
        Point last = new Point(pos3, new Direction(DETACHMENT, DETACHMENT));
        lines.add(first);
        lines.add(second);
        lines.add(last);
        return lines;
    }


    @Nested
    @DisplayName("참여자가 짝수일 경우")
    class MyLadder_Particiapants2 {

        @DisplayName(" line이 없다면, 첫 번째 사람이 첫 번째 결과에 매칭된다. ")
        @Test
        void test5() throws Exception {
            //given
            var results = new Results(new String[]{"1000", "0"});
            Point first = new Point(0, new Direction(DETACHMENT, DETACHMENT));
            Point second = new Point(1, new Direction(DETACHMENT, DETACHMENT));

            Line myLine = new MyLine(List.of(first, second));
            Ladder myLadder = new MyLadder(List.of(myLine), 2);

            //when
            Match match = myLadder.match(results, new MatchResults());

            then(match.getResult(0)).isEqualTo("1000");
            then(match.getResult(1)).isEqualTo("0");
        }

        @DisplayName(" line 1개가 연결돼 있으면, 첫 번째 사람이 마지막 결과에 매칭된다. ")
        @Test
        void test2() throws Exception {
            //given
            var results = new Results(new String[]{"1000", "0"});
            Point first = new Point(0, new Direction(DETACHMENT, CONNECTION));
            Point second = new Point(1, new Direction(CONNECTION, DETACHMENT));

            Line myLine = new MyLine(List.of(first, second));

            Ladder myLadder = new MyLadder(List.of(myLine), 2);

            //when
            Match match = myLadder.match(results, new MatchResults());

            then(match.getResult(0)).isEqualTo("0");
            then(match.getResult(1)).isEqualTo("1000");
        }

        /*
          a b
          |-|
          |-|
       */

        @DisplayName(" line 2개가 연결되어 있으면, 첫 번째 사람이 첫 번째 결과에 매칭된다. ")
        @Test
        void test3() throws Exception {
            //given
            var results = new Results(new String[]{"1000", "0"});
            Point first = new Point(0, new Direction(DETACHMENT, CONNECTION));
            Point second = new Point(1, new Direction(CONNECTION, DETACHMENT));

            Point first1 = new Point(0, new Direction(DETACHMENT, CONNECTION));
            Point second1 = new Point(1, new Direction(CONNECTION, DETACHMENT));

            Line myLine = new MyLine(List.of(first, second));
            Line myLine1 = new MyLine(List.of(first1, second1));

            Ladder myLadder = new MyLadder(List.of(myLine, myLine1), 2);

            //when
            Match match = myLadder.match(results, new MatchResults());

            //then
            then(match.getResult(0)).isEqualTo("1000");
            then(match.getResult(1)).isEqualTo("0");
        }

        @DisplayName(" line 3개가 연결되어 있으면, 첫 번째 사람이 마지막 결과에 매칭된다. ")
        @Test
        void test4() throws Exception {
            //given
            var results = new Results(new String[]{"1000", "0"});
            Point first = new Point(0, new Direction(DETACHMENT, CONNECTION));
            Point second = new Point(1, new Direction(CONNECTION, DETACHMENT));

            Point first1 = new Point(0, new Direction(DETACHMENT, CONNECTION));
            Point second1 = new Point(1, new Direction(CONNECTION, DETACHMENT));

            Point first2 = new Point(0, new Direction(DETACHMENT, CONNECTION));
            Point second2 = new Point(1, new Direction(CONNECTION, DETACHMENT));

            Line myLine = new MyLine(List.of(first, second));
            Line myLine1 = new MyLine(List.of(first1, second1));
            Line myLine2 = new MyLine(List.of(first2, second2));

            MyLadder myLadder = new MyLadder(List.of(myLine, myLine1, myLine2), 2);

            //when
            Match match = myLadder.match(results, new MatchResults());

            //then
            then(match.getResult(0)).isEqualTo("0");
            then(match.getResult(1)).isEqualTo("1000");
        }
    }


    @Nested
    @DisplayName("참여자가 홀수(a,b,c)일 경우")
    class Players3 {

        /*
          a b c
          |-| |
        */
        @DisplayName("line이 1개가 a,b 사이에 있을 경우 a는 두 번째 결과, b는 첫 번째 결과, c는 세번째 결과를 갖는다.")
        @Test
        void test1() throws Exception {
            //given
            var results = new Results(new String[]{"1", "2", "3"});
            Point first = new Point(0, new Direction(DETACHMENT, CONNECTION));
            Point second = new Point(1, new Direction(CONNECTION, DETACHMENT));
            Point third = new Point(2, new Direction(DETACHMENT, DETACHMENT));

            Line myLine = new MyLine(List.of(first, second, third));
            Ladder myLadder = new MyLadder(List.of(myLine), 3);

            //when
            Match match = myLadder.match(results, new MatchResults());

            //then
            then(match.getResult(0)).isEqualTo("2");
            then(match.getResult(1)).isEqualTo("1");
            then(match.getResult(2)).isEqualTo("3");
        }

        /*
            a b c
            | |-|
            |-| |
            | |-|
        */
        @DisplayName("line이 3개가 (b,c), (a,b), (b,c) 사이에 있을 경우 a는 세 번째 결과, b는 두 번째 결과, c는 첫 번째 결과를 갖는다.")
        @Test
        void test8() throws Exception {
            //given
            var results = new Results(new String[]{"1", "2", "3"});
            Point first = new Point(0, new Direction(DETACHMENT, DETACHMENT));
            Point second = new Point(1, new Direction(DETACHMENT, CONNECTION));
            Point third = new Point(2, new Direction(CONNECTION, DETACHMENT));

            Point first1 = new Point(0, new Direction(DETACHMENT, CONNECTION));
            Point second1 = new Point(1, new Direction(CONNECTION, DETACHMENT));
            Point third1 = new Point(2, new Direction(DETACHMENT, DETACHMENT));

            Point first2 = new Point(0, new Direction(DETACHMENT, DETACHMENT));
            Point second2 = new Point(1, new Direction(DETACHMENT, CONNECTION));
            Point third2 = new Point(2, new Direction(CONNECTION, DETACHMENT));

            Line myLine = new MyLine(List.of(first, second, third));
            Line myLine1 = new MyLine(List.of(first1, second1, third1));
            Line myLine2 = new MyLine(List.of(first2, second2, third2));

            Ladder myLadder = new MyLadder(List.of(myLine, myLine1, myLine2), 3);

            //when
            Match match = myLadder.match(results, new MatchResults());

            //then
            then(match.getResult(0)).isEqualTo("3");
            then(match.getResult(1)).isEqualTo("2");
            then(match.getResult(2)).isEqualTo("1");
        }
    }
}


