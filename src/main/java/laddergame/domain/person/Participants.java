package laddergame.domain.person;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Participants {

    private final List<Player> playerList;

    private Participants(String[] names) {
        this(Arrays.stream(names).map(Player::new).collect(Collectors.toList()));
    }

    public Participants(List<Player> playerList) {
        this.playerList = playerList;
    }

    public static Participants of(String[] names) {
        validateDuplication(names);
        return new Participants(names);
    }

    public List<String> getNames() {
        return playerList.stream().map(Player::getName).collect(Collectors.toList());
    }

    public String getName(int index) {
        return playerList.get(index).getName();
    }

    public int getPosition(final String name) {
        return playerList.stream()
            .filter(p -> p.getName().equals(name))
            .findFirst()
            .map(playerList::indexOf)
            .orElseThrow(() -> {
                throw new IllegalArgumentException("찾을 수 없는 참가자입니다.");
            });
    }

    public int getCount() {
        return playerList.size();
    }

    private static void validateDuplication(final String[] names) {
        if (isDuplication(names)) {
            throw new IllegalStateException("참여자 이름이 중복됐습니다.");
        }
    }

    private static boolean isDuplication(final String[] names) {
        return Arrays.stream(names).distinct().count() != names.length;
    }
}
