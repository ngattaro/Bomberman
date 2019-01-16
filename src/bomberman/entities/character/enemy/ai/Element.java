package bomberman.entities.character.enemy.ai;
/**
 * Class Element
 * @author Do Thi Hong Ngat
 * @version 1.0
 * @since 2018-11-10
 */
public class Element
{
    public int posX;
    public int posY;
    public int distance;
    public Element(int x, int y, int d)
    {
        posX = x;
        posY = y;
        distance = d;
    }
}
