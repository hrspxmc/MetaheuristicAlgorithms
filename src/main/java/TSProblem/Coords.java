package TSProblem;

public class Coords {

  private final double x, y;

  public Coords(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public double getX() {
    return this.x;
  }

  public double getY() {
    return this.y;
  }

  @Override
  public String toString() {
    return ("(" + x + "," + y + ")");
  }
}
