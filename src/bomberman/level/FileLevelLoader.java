package  bomberman.level;
/**
 * Class FileLevelLoader
 * @author Do Thi Hong Ngat
 * @version 1.0
 * @since 2018-11-10
 */
import  bomberman.Board;
import  bomberman.Game;
import  bomberman.entities.LayeredEntity;
import  bomberman.entities.character.Bomber;
import bomberman.entities.character.enemy.*;
import  bomberman.entities.tile.Grass;
import bomberman.entities.tile.Portal;
import bomberman.entities.tile.Wall;
import  bomberman.entities.tile.destroyable.Brick;
import bomberman.entities.tile.item.BombItem;
import bomberman.entities.tile.item.FlameItem;
import  bomberman.entities.tile.item.SpeedItem;
import  bomberman.graphics.Screen;
import  bomberman.graphics.Sprite;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileLevelLoader extends LevelLoader
{
	
	/**
	 * Ma trận chứa thông tin bản đồ, mỗi phần tử lưu giá trị kí tự đọc được
	 * từ ma trận bản đồ trong tệp cấu hình
	 */
	private static String[] _map;
	
	public FileLevelLoader(Board board, int level)
	{
		super(board, level);
	}
	
	@Override
	public void loadLevel(int level)
	{
		//  đọc dữ liệu từ tệp cấu hình /levels/Level{level}.txt
		//  cập nhật các giá trị đọc được vào _width, _height, _level, _map
		try
		{
			Scanner sc = new Scanner(new File("res/levels/Level" + level + ".txt"));
			_level = sc.nextInt();
			_height = sc.nextInt();
			_width = sc.nextInt();
			_map = new String[_height];
			_map[0] = sc.nextLine();
			for (int i = 0; i < _height; i++)
			{
				_map[i] = sc.nextLine();
			}
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		
		
	}
	@Override
	public void  createEntities()
	{
		for(int y = 0; y<_height;y++)
		{
			for(int x = 0; x <_width; x++)
			{
				AddEntity(_map[y].charAt(x), x, y);
			}
		}
		
	}
	
	/**
	 * Them entity
	 * @param ch ki hieu
	 * @param x toa do
	 * @param y toa do
	 */
	public void AddEntity(char ch, int x, int y)
	{
		// tạo các Entity của màn chơi
		//  sau khi tạo xong, gọi _board.addEntity() để thêm Entity vào game
		int pos = x + y * _width;
		switch (ch)
		{
			case '#':
				_board.addEntity(pos, new Wall(x, y, Sprite.wall));
				break;
			case ' ':
				_board.addEntity(pos, new Grass(x, y, Sprite.grass));
				break;
			case 'p':
				_board.addCharacter(new Bomber(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILES_SIZE, _board));
				Screen.setOffset(0, 0);
				_board.addEntity(pos, new Grass(x, y, Sprite.grass));
				break;
			case '*':
				_board.addEntity(pos, new LayeredEntity(x, y, new Grass(x, y, Sprite.grass), new Brick(x, y, Sprite.brick)));
				break;
			case 'b':
				_board.addEntity(pos, new LayeredEntity(x, y, new Grass(x, y, Sprite.grass), new BombItem(x, y, Sprite.powerup_bombs), new Brick(x, y, Sprite.brick)));
				break;
			case 'f':
				_board.addEntity(pos, new LayeredEntity(x, y, new Grass(x, y, Sprite.grass), new FlameItem(x, y, Sprite.powerup_flames), new Brick(x, y, Sprite.brick)));
				break;
			case 's':
				_board.addEntity(pos, new LayeredEntity(x, y, new Grass(x, y, Sprite.grass), new SpeedItem(x, y, Sprite.powerup_speed), new Brick(x, y, Sprite.brick)));
				break;
			case 'x':
				_board.addEntity(pos, new LayeredEntity(x, y, new Grass(x, y, Sprite.grass), new Portal(x, y, Sprite.portal,_board), new Brick(x, y, Sprite.brick)));
				break;
			case '1':
				_board.addCharacter(new Balloon(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILES_SIZE, _board));
				_board.addEntity(pos, new Grass(x, y, Sprite.grass));
				break;
			case '2':
				_board.addCharacter(new Oneal(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILES_SIZE, _board));
				_board.addEntity(pos, new Grass(x, y, Sprite.grass));
				break;
			case '3':
				_board.addCharacter(new Doll(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILES_SIZE, _board));
				_board.addEntity(pos, new Grass(x, y, Sprite.grass));
				break;
			case '4':
				_board.addCharacter(new Minvo(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILES_SIZE, _board));
				_board.addEntity(pos, new Grass(x, y, Sprite.grass));
				break;
			case '5':
				_board.addCharacter(new Kondoria(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILES_SIZE, _board));
				_board.addEntity(pos, new Grass(x, y, Sprite.grass));
				break;
			case '6':
				_board.addCharacter(new Ovapes(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILES_SIZE, _board));
				_board.addEntity(pos, new Grass(x, y, Sprite.grass));
				break;
			default:
				_board.addEntity(pos, new Grass(x, y, Sprite.grass));
				break;
			
		}
		
	}
}

