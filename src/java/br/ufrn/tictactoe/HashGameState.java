package br.ufrn.tictactoe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HashGameState {
	
	public enum GameStage {
		PRE_GAME, 
		PLAYER_IDENTIFICATION,
		IN_GAME,
		POST_GAME
	}
	
	private String xPlayerName;
	private String oPlayerName;
	private String gameMessage;
	private String turnMessage;
	private Board.Marker turn;
	private GameStage gameStage;
	private Board board;

	private static final Logger log = LoggerFactory.getLogger(HashGameState.class);

	public HashGameState()
	{
		board = new Board();

		reset();
	}
	
	public void reset()
	{
		setxPlayerName("X Player");
		setoPlayerName("O Player");
		setGameMessage("");
		setTurn(Board.Marker.X);
		setTurnMessage("Turn: X");
		setGameStage(GameStage.PRE_GAME);
		board.clear();
	}
	
	public void startNewGame()
	{
		board.clear();
		setGameMessage("");
		setTurnMessage("Turn: X");
		setTurn(Board.Marker.X);
		setGameStage(GameStage.IN_GAME);
	}
	
	public String getxPlayerName() {
		return xPlayerName;
	}

	public void setxPlayerName(String xPlayerName) {
		this.xPlayerName = xPlayerName;
	}

	public String getoPlayerName() {
		return oPlayerName;
	}

	public void setoPlayerName(String yPlayerName) {
		this.oPlayerName = yPlayerName;
	}

	public String getGameMessage() {
		return gameMessage;
	}

	public void setGameMessage(String playMessage) {
		this.gameMessage = playMessage;
	}

	public String getTurnMessage() {
		return turnMessage;
	}

	public void setTurnMessage(String turnMessage) {
		this.turnMessage = turnMessage;
	}

	public Board.Marker getTurn() {
		return turn;
	}

	public void setTurn(Board.Marker turn) {
		this.turn = turn;
	}
	
	public GameStage getGameStage() {
		return gameStage;
	}
	
	public void setGameStage(GameStage gameStage) {
		this.gameStage = gameStage;
	}

	public Board getBoard() {
		return board;
	}
	
	@Override
	public String toString() {
		return "HashGameState [xPlayerName=" + xPlayerName + ", oPlayerName=" + oPlayerName + ", gameMessage=" + gameMessage
				+ ", turnMessage=" + turnMessage + ", turn=" + turn + ", gameStage="
				+ gameStage + ", board=" + board + "]";
	}
		
}
