package bomberman;
/**
 * Class Board
 * @author Do Thi Hong Ngat
 * @version 1.0
 * @since 2018-11-10
 */

import bomberman.entities.Entity;
import bomberman.entities.LayeredEntity;
import bomberman.entities.Message;
import bomberman.entities.bomb.Bomb;
import bomberman.entities.bomb.FlameSegment;
import bomberman.entities.character.Bomber;
import bomberman.entities.character.Character;
import bomberman.entities.tile.Wall;
import bomberman.entities.tile.destroyable.Brick;
import bomberman.graphics.IRender;
import bomberman.graphics.Screen;
import bomberman.input.Keyboard;
import bomberman.level.FileLevelLoader;
import bomberman.level.LevelLoader;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

/**
 * Quản lý thao tác điều khiển, load level, render các màn hình của game
 */
public class Board implements IRender
{
    
    public Entity[] _entities;
    public List<Character> _characters = new ArrayList<>();
    public static SoundPlayer levelUpSound = new SoundPlayer(new File("res/sound/levelUp.wav"));
    public static SoundPlayer backgroundSound = new SoundPlayer(new File("res/sound/bgmusic.wav"));
    public static SoundPlayer gameOverSound = new SoundPlayer(new File("res/sound/gameOver.wav"));
    public static SoundPlayer victorySound = new SoundPlayer(new File("res/sound/victory.wav"));
    public static  SoundPlayer explosionSound = new SoundPlayer(new File("res/sound/explosion.wav"));
    public static SoundPlayer placeBombSound = new SoundPlayer(new File("res/sound/placeBomb.wav"));
    public static SoundPlayer bomberDieSound = new SoundPlayer(new File("res/sound/bomberDie.wav"));
    public static SoundPlayer getItemSound = new SoundPlayer(new File("res/sound/getItem.wav"));
    
    protected LevelLoader _levelLoader;
    protected Game _game;
    protected Keyboard _input;
    protected Screen _screen;
    protected List<Bomb> _bombs = new ArrayList<>();
    private List<Message> _messages = new ArrayList<>();
    private int _screenToShow = -1; //1:endgame, 2:changelevel, 3:paused, 4: startgame,
    
    private int _time = Game.TIME;
    private int _points = Game.POINTS;
    private int _lives = Game.LIVES;
    private int plusPoint = 0;
    private int _highScore = 0;
    
    private boolean victory = false;
    private double lastUpdatePaused = 0;
    
    public Board(Game game, Keyboard input, Screen screen)
    {
        _game = game;
        _input = input;
        _screen = screen;
        readHighScore();
        loadLevel(1); //start in level 1
    }
    private void readHighScore()
    {
        Scanner sc = null;
        try
        {
            sc = new Scanner(new File("res/userData/highScore.txt"));
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        _highScore = sc.nextInt();
        sc.close();
    }
    private void writeHighScore() throws IOException
    {
        FileWriter fw = new FileWriter(new File("res/userData/highScore.txt"));
        fw.write(String.valueOf(_highScore));
        fw.close();
    }
    @Override
    public void update()
    {
        if (_screenToShow == 1 && _input.space)
        {
            newGame();
        }
        if (_input.esc && System.currentTimeMillis() - lastUpdatePaused > 500)
        {
            if (_game.isPaused()) gameResume();
            else gamePause();
            lastUpdatePaused = System.currentTimeMillis();
        }
        if (_game.isPaused()) return;
        
        updateEntities();
        updateCharacters();
        updateBombs();
        updateMessages();
        detectEndGame();
        
        
        for (int i = 0; i < _characters.size(); i++)
        {
            Character a = _characters.get(i);
            if (a.isRemoved()) _characters.remove(i);
        }
        
    }
    
    @Override
    public void render(Screen screen)
    {
        if (_game.isPaused()) return;
        
        //only render the visible part of screen
        int x0 = Screen.xOffset >> 4; //tile precision, -> left X
        int x1 = (Screen.xOffset + screen.getWidth() + Game.TILES_SIZE) / Game.TILES_SIZE; // -> right X
        int y0 = Screen.yOffset >> 4;
        int y1 = (Screen.yOffset + screen.getHeight()) / Game.TILES_SIZE; //render one tile plus to fix black margins
        for (int y = y0; y < y1; y++)
        {
            for (int x = x0; x < x1; x++)
            {
                _entities[x + y * _levelLoader.getWidth()].render(screen);
            }
        }
        
        renderBombs(screen);
        renderCharacter(screen);
        
    }
    
    /**
     * Game Pause
     */
    public void gamePause()
    {
        _game.resetScreenDelay();
        if (_screenToShow <= 0)
            _screenToShow = 3;
        _game.pause();
    }
    
    /**
     * Game Resume
     */
    public void gameResume()
    {
        _game.resetScreenDelay();
        _screenToShow = -1;
        _game.run();
    }
    
    /**
     * Load level
     * @param level
     */
    public void loadLevel(int level)
    {
        _time = Game.TIME;
        _screenToShow = 2;
        _game.resetScreenDelay();
        _game.pause();
        _characters.clear();
        _bombs.clear();
        _messages.clear();
        try
        {
            writeHighScore();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        if (level <= 5)
        {
            _levelLoader = new FileLevelLoader(this, level);
            _entities = new Entity[_levelLoader.getHeight() * _levelLoader.getWidth()];
            
        } else
        {
            victory = true;
            endGame();
            return;
        }
        _levelLoader.createEntities();
        backgroundSound.stop();
        gameOverSound.stop();
        victorySound.stop();
        levelUpSound.play();
    }
    
    /**
     * next level
     */
    public void nextLevel()
    {
        Game.addBombRate(_bombs.size());
        _bombs.clear();
        addPoints(_time);
        plusPoint = 0;
        Game.levelnextItem();
        loadLevel(_levelLoader.getLevel() + 1);
        
    }
    
    /**
     * restart level
     */
    public void restartLevel()
    {
        _lives--;
        if (_lives == 0) endGame();
        else
        {
            this._points -=this.plusPoint;
            this.plusPoint = 0;
            Game.levelresetItem();
            loadLevel(_levelLoader.getLevel());
        }
    }
    
    /**
     * kiem tra xem het thoi gian hay chua
     */
    protected void detectEndGame()
    {
        if (_time < 0) restartLevel();
    }
    
    /**
     * new game
     */
    public void newGame()
    {
        _points = Game.POINTS;
        _lives = Game.LIVES;
        victory = false;
        Game.bomberSpeed = Game.BOMBERSPEED;
        Game.bombRadius = Game.BOMBRADIUS;
        Game.bombRate = Game.BOMBRATE;
        plusPoint = 0;
        Game.levelnextItem();
        loadLevel(1);
    }
    
    /**
     * end game
     */
    public void endGame()
    {
        _screenToShow = 1;
        _game.resetScreenDelay();
        _game.pause();
        backgroundSound.stop();
        if (victory)
            victorySound.play();
        else
            gameOverSound.play();
    }
    
    /**
     * kiem tra xem sl enemy = 0 ?
     * @return
     */
    public boolean detectNoEnemies()
    {
        int total = 0;
        for (int i = 0; i < _characters.size(); i++)
        {
            if (_characters.get(i) instanceof Bomber == false)
                ++total;
        }
        return total == 0;
    }
    
    public void drawScreen(Graphics g)
    {
        switch (_screenToShow)
        {
            case 1:
                if (victory)
                    _screen.drawVictory(g, _points);
                else
                    _screen.drawGameOver(g, _points);
                break;
            case 2:
                _screen.drawChangeLevel(g, _levelLoader.getLevel());
                break;
            case 3:
                _screen.drawPaused(g);
                break;
        }
    }
    
    public Entity getEntity(double x, double y, Character m)
    {
        
        Entity res = null;
        
        res = getFlameSegmentAt((int) x, (int) y);
        if (res != null) return res;
        
        res = getBombAt(x, y);
        if (res != null) return res;
        
        res = getCharacterAtExcluding((int) x, (int) y, m);
        if (res != null) return res;
        
        res = getEntityAt((int) x, (int) y);
        
        return res;
    }
    
    /**
     * kiem tra xem vi tri co phai wall hay brick
     * @param x toa do
     * @param y toa do
     * @return boolean
     */
    public boolean isWallBrick(int x, int y)
    {
        Entity e = _entities[x + y * _levelLoader.getWidth()];
        if (e instanceof LayeredEntity)
        {
            return ((LayeredEntity) e).getTopEntity() instanceof Brick;
        }
        return (e instanceof Brick || e instanceof Wall);
        
    }
    
    public Character getCharacterAt(double x, double y)
    {
        Iterator<Character> itr = _characters.iterator();
        
        Character cur;
        while (itr.hasNext())
        {
            cur = itr.next();
            
            if (cur.getXTile() == x && cur.getYTile() == y)
                return cur;
        }
        
        return null;
    }
    
    public List<Bomb> getBombs()
    {
        return _bombs;
    }
    
    public Bomb getBombAt(double x, double y)
    {
        Iterator<Bomb> bs = _bombs.iterator();
        Bomb b;
        while (bs.hasNext())
        {
            b = bs.next();
            if (b.getX() == (int) x && b.getY() == (int) y)
                return b;
        }
        
        return null;
    }
    
    public Bomber getBomber()
    {
        Iterator<Character> itr = _characters.iterator();
        
        Character cur;
        while (itr.hasNext())
        {
            cur = itr.next();
            
            if (cur instanceof Bomber)
                return (Bomber) cur;
        }
        
        return null;
    }
    
    public Character getCharacterAtExcluding(int x, int y, Character a)
    {
        Iterator<Character> itr = _characters.iterator();
        
        Character cur;
        while (itr.hasNext())
        {
            cur = itr.next();
            if (cur == a)
            {
                continue;
            }
            
            if (cur.getXTile() == x && cur.getYTile() == y)
            {
                return cur;
            }
            
        }
        
        return null;
    }
    
    public FlameSegment getFlameSegmentAt(int x, int y)
    {
        Iterator<Bomb> bs = _bombs.iterator();
        Bomb b;
        while (bs.hasNext())
        {
            b = bs.next();
            
            FlameSegment e = b.flameAt(x, y);
            if (e != null)
            {
                return e;
            }
        }
        
        return null;
    }
    
    public Entity getEntityAt(int x, int y)
    {
        return _entities[x + y * _levelLoader.getWidth()];
    }
    
    public void addEntity(int pos, Entity e)
    {
        _entities[pos] = e;
    }
    
    public void addCharacter(Character e)
    {
        _characters.add(e);
    }
    
    public void addBomb(Bomb e)
    {
        _bombs.add(e);
    }
    
    public void addMessage(Message e)
    {
        _messages.add(e);
    }
    
    protected void renderCharacter(Screen screen)
    {
        Iterator<Character> itr = _characters.iterator();
        
        while (itr.hasNext())
            itr.next().render(screen);
    }
    
    protected void renderBombs(Screen screen)
    {
        Iterator<Bomb> itr = _bombs.iterator();
        
        while (itr.hasNext())
            itr.next().render(screen);
    }
    
    public void renderMessages(Graphics g)
    {
        Message m;
        for (int i = 0; i < _messages.size(); i++)
        {
            m = _messages.get(i);
            
            g.setFont(new Font("Arial", Font.PLAIN, m.getSize()));
            g.setColor(m.getColor());
            g.drawString(m.getMessage(), (int) m.getX() - Screen.xOffset * Game.SCALE, (int) m.getY());
        }
    }
    
    protected void updateEntities()
    {
        if (_game.isPaused()) return;
        for (int i = 0; i < _entities.length; i++)
        {
            _entities[i].update();
            
        }
    }
    
    protected void updateCharacters()
    {
        if (_game.isPaused()) return;
        Iterator<Character> itr = _characters.iterator();
        Character bomber = null;
        while (itr.hasNext() && !_game.isPaused())
        {
            Character character = itr.next();
            if ((character instanceof Bomber)) bomber = character;
        }
        if(bomber!=null) bomber.update();
        itr = _characters.iterator();
        while (itr.hasNext() && !_game.isPaused())
        {
            Character character = itr.next();
            if (!(character instanceof Bomber)) character.update();
        }
    }
    
    protected void updateBombs()
    {
        if (_game.isPaused()) return;
        Iterator<Bomb> itr = _bombs.iterator();
        
        while (itr.hasNext())
            itr.next().update();
    }
    
    protected void updateMessages()
    {
        if (_game.isPaused()) return;
        Message m;
        int left;
        for (int i = 0; i < _messages.size(); i++)
        {
            m = _messages.get(i);
            left = m.getDuration();
            
            if (left > 0)
                m.setDuration(--left);
            else
                _messages.remove(i);
        }
    }
    
    public int subtractTime()
    {
        if (_game.isPaused())
            return this._time;
        else
            return this._time--;
    }
    
    public Keyboard getInput()
    {
        return _input;
    }
    
    public LevelLoader getLevel()
    {
        return _levelLoader;
    }
    
    public Game getGame()
    {
        return _game;
    }
    
    public int getShow()
    {
        return _screenToShow;
    }
    
    public void setShow(int i)
    {
        _screenToShow = i;
    }
    
    public int getTime()
    {
        return _time;
    }
    
    public int getPoints()
    {
        return _points;
    }
    
    public int getLives()
    {
        return _lives;
    }
    public int getHighScore()
    {
        return _highScore;
    }
    
    
    public void addPoints(int points)
    {
        this.plusPoint+=points; this._points += points;
        if(_highScore < _points) _highScore = _points;
        
    }
    
    public int getWidth()
    {
        return _levelLoader.getWidth();
    }
    
    public int getHeight()
    {
        return _levelLoader.getHeight();
    }
    
}
