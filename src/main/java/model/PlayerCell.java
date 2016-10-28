package model;

/**
 * @author apomosov
 */
public class PlayerCell extends Cell {
  public PlayerCell(int x, int y) {
    super(x, y, GameConstants.DEFAULT_PLAYER_CELL_MASS);
  }
}
