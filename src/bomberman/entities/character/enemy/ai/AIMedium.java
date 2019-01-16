package  bomberman.entities.character.enemy.ai;
/**
 * Class AIMedium (tim duong di toi bomber)
 * @author Do Thi Hong Ngat
 * @version 1.0
 * @since 2018-11-10
 */
import bomberman.Board;
import  bomberman.entities.character.Bomber;
import  bomberman.entities.character.enemy.Enemy;
import bomberman.entities.tile.Wall;
import javafx.util.Pair;
import java.util.LinkedList;


public class AIMedium extends AI {
	public static final int INF = 10000000;
	public static final int [] hx = new int[4];
	public static final int [] hy = new int[4];
	Bomber _bomber;
	Enemy _e;
	Board _board;
	public AIMedium(Board board,Bomber bomber, Enemy e) {
		_board = board;
		_bomber = bomber;
		_e = e;
		hx[0] = 0; hx[1] = 1; hx[2] = 0; hx[3] = -1;
		hy[0] = -1; hy[1] = 0; hy[2] = 1; hy[3] = 0;
	}
	
	/**
	 * Ham check xem di vao o lx,ly duoc ko
	 * @param lx
	 * @param ly
	 * @return
	 */
	public boolean canMove(int lx, int ly)
	{
		if(lx<0 || ly<0 || lx>=_board.getWidth() || ly>=_board.getHeight() ||  (!(_board.getEntityAt(lx,ly).collide(_e)))) return false;
		
		return true;
	}
	
	/**
	 * Tim duong di ngan nhat
	 * @param xpx toa do x
	 * @param xpy toa do y
	 * @param ktx toa do x
	 * @param kty toa do y
	 * @return
	 */
	public int bfs(int xpx, int xpy, int ktx,int kty)
	{
		if(xpx == ktx && xpy == kty) return 0;
		int m = _board.getHeight();
		int n = _board.getWidth();
		boolean ok[][] = new boolean[n][m];
		for (int i = 0; i <n; i++)
			for(int j = 0; j <m; j++) ok[i][j] = false;
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
				if(!canMove(lx,ly) ||ok[lx][ly]) continue;
				if(lx == ktx && ly == kty) return curr.distance+1;
				ok[lx][ly] = true;
				queue.add(new Element(lx,ly,curr.distance+1));
			}
		}
		return INF;
	}
	
	/**
	 * Tim huong di toi uu
	 * @param ktx dich
	 * @param kty dich
	 * @return
	 */
	public Pair<Integer,Integer> findShortenPath(int ktx,int kty)
	{
		int shortenPath = INF;
		int direction = -1;
		for(int i = 0; i < 4; i++)
		{
			int lx = _e.getXTile()+hx[i];
			int ly = _e.getYTile()+hy[i];
			if(!canMove(lx,ly)) continue;
			int tmp = bfs(lx,ly,ktx,kty);
			if(tmp !=INF && shortenPath > tmp)
			{
				direction = i;
				shortenPath = tmp;
			}
		}
		return new Pair(shortenPath,direction);
	}
	@Override
	public int calculateDirection() {
		// cài đặt thuật toán tìm đường đi
		Pair<Integer,Integer> p = findShortenPath(_bomber.getXTile(),_bomber.getYTile());
		if(p.getKey()>5) return -1;
		return p.getValue();
	
	}

}
