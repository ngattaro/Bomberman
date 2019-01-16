package bomberman.entities.character.enemy.ai;
/**
 * Class AIHigh ( tranh bom va duoi theo bomber)
 * @author Do Thi Hong Ngat
 * @version 1.0
 * @since 2018-11-10
 */
import bomberman.Board;
import bomberman.Game;
import bomberman.entities.bomb.Bomb;
import bomberman.entities.character.Bomber;
import bomberman.entities.character.Character;
import bomberman.entities.character.enemy.Enemy;
import bomberman.entities.tile.Wall;
import bomberman.entities.tile.destroyable.Brick;
import javafx.util.Pair;
import java.util.LinkedList;

public class AIHigh extends AI
{
    public static final int INF = 10000000;
    public static final int [] hx = new int[4];
    public static final int [] hy = new int[4];
    Bomber _bomber;
    Enemy _e;
    Board _board;
    boolean ok[][];
    
    public AIHigh(Board board,Bomber bomber, Enemy e) {
        _board = board;
        _bomber = bomber;
        _e = e;
        hx[0] = 0; hx[1] = 1; hx[2] = 0; hx[3] = -1;
        hy[0] = -1; hy[1] = 0; hy[2] = 1; hy[3] = 0;
        ok = new boolean[_board.getWidth()][_board.getHeight()];
    }
    
    /**
     * Tinh khoang cach bom no
     * @param x vi tri no
     * @param y vi tri no
     * @param direction huong no
     * @return do dai
     */
    public int calculatePermitedDistance(int x, int y, int direction) {
        // thực hiện tính toán độ dài của Flame
        int radius = 0;
        while(radius < Game.getBombRadius()) {
            if(direction == 0) y--;
            else if(direction == 1) x++;
            else if(direction == 2) y++;
            else if(direction == 3) x--;
            if(_board.getEntityAt(x,y) instanceof Wall) break;
            ++radius;
            if(_board.getEntityAt(x,y) instanceof Brick) break;
            if (_board.getEntity(x,y,null) instanceof Character) break;
        }
        return radius;
    }
    
    /**
     * check xem co the di toi o lx, ly
     * @param lx
     * @param ly
     * @return
     */
    public boolean canMove(int lx, int ly)
    {
        if(lx<0 || ly<0 || lx>=_board.getWidth() || ly>=_board.getHeight() || (!(_board.getEntityAt(lx,ly).collide(_e)))) return false;
        for(Bomb bomb:_board.getBombs())
            if(lx==bomb.getX()&&ly==bomb.getY()) return false;
        return true;
    }
    
    /**
     * Tim duong di ngan nhat
     * @param xpx xuat phat
     * @param xpy xuat phat
     * @param ktx dich
     * @param kty dich
     * @return
     */
    public int bfs(int xpx, int xpy, int ktx,int kty)
    {
        if(xpx == ktx && xpy == kty) return 0;
        LinkedList<Element> queue = new LinkedList<Element>();
        queue.add(new Element(xpx,xpy,0));
        ok[xpx][xpy] = true;
        while (!queue.isEmpty())
        {
            Element curr = queue.getFirst();
            queue.remove(0);
            for(int i = 0; i < 4; i++)
            {
                int lx = curr.posX+hx[i];
                int ly = curr.posY+hy[i];
                if(!canMove(lx,ly) || ok[lx][ly]) continue;
                if(lx == ktx && ly == kty) return curr.distance+1;
                ok[lx][ly] = true;
                queue.add(new Element(lx,ly,curr.distance+1));
            }
        }
        return INF;
    }
    
    /**
     * Tim duong di ngan nhat thoat khoi bom
     * @param xpx xuat phat x
     * @param xpy xuat phat y
     * @return
     */
    public int bfs2(int xpx, int xpy)
    {
        LinkedList<Element> queue = new LinkedList<Element>();
        queue.add(new Element(xpx,xpy,0));
        boolean[][] ok2 = new boolean[_board.getWidth()][_board.getHeight()];
        for (int i = 0; i < _board.getWidth();i++)
            for(int j = 0; j < _board.getHeight();j++)
                ok2[i][j] = false;
        ok2[xpx][xpy] = true;
        while (!queue.isEmpty())
        {
            Element curr = queue.getFirst();
            queue.remove(0);
            for(int i = 0; i < 4; i++)
            {
                int lx = curr.posX+hx[i];
                int ly = curr.posY+hy[i];
                if(!canMove(lx,ly) || ok2[lx][ly]) continue;
                if(!ok[lx][ly]) return curr.distance+1;
                ok2[lx][ly] = true;
                queue.add(new Element(lx,ly,curr.distance+1));
            }
        }
        return INF;
    }
    
    /**
     * Tim huong di toi uu nhat
     * @param ktx vi tri dich
     * @param kty vi tri dich
     * @return
     */
    public Pair<Integer,Integer> findShortenPath(int ktx,int kty)
    {
        int shortenPath = INF;
        int direction = -1;
        
        for(int dr = 0; dr < 4; dr++)
        {
            int lx = _e.getXTile()+hx[dr];
            int ly = _e.getYTile()+hy[dr];
            if(!canMove(lx,ly)) continue;
            int tmp = INF;
            for (int i = 0; i < _board.getWidth();i++)
                for(int j = 0; j < _board.getHeight();j++)
                    ok[i][j] = false;
            for(Bomb bomb : _board.getBombs())
            {
                int x = (int)bomb.getX(), y = (int) bomb.getY();
                int flame = calculatePermitedDistance(x,y,0);
                for(int i = y; i >=y-flame;i--)
                    ok[x][i] = true;
                flame = calculatePermitedDistance(x,y,2);
                for(int i = y; i <=y+flame;i++)
                    ok[x][i] = true;
                flame = calculatePermitedDistance(x,y,1);
                for(int i = x; i <=x+flame;i++)
                    ok[i][y] = true;
                flame = calculatePermitedDistance(x,y,3);
                for(int i = x; i >=x-flame;i--)
                    ok[i][y] = true;
            }
          
            if(ok[_e.getXTile()][_e.getYTile()])
            {
                tmp = bfs2(lx,ly);
            }
            else if(!ok[lx][ly])
            {
                tmp = bfs(lx,ly,ktx,kty);
                
            }
            if(tmp !=INF && shortenPath > tmp)
            {
                direction = dr;
                shortenPath = tmp;
            }
        }
        
        return new Pair(shortenPath,direction);
    }
    
    @Override
    public int calculateDirection() {
        // cài đặt thuật toán tìm đường đi
        Pair<Integer,Integer> p = findShortenPath(_bomber.getXTile(),_bomber.getYTile());
        if(p.getKey()==INF) return -1;
        return p.getValue();
        
    }
}
