package laddergame.domain.results;

public enum Movement {
    RIGHT(1), LEFT(-1), DOWN(0);

    private final int position;

    Movement(final int position) {
        this.position = position;
    }

    public int move(int position){
        return position + getPosition();
    }

    public int getPosition() {
        return position;
    }
}
