package br.ufrn.tictactoe;

import java.util.Random;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import br.ufrn.tictactoe.HashGameState.GameStage;

@Controller
public class HashGameController {
		
	private static final Logger log = LoggerFactory.getLogger(HashGameController.class);

	/**
	 * Starts new Tic Tac Toe game.
	 * 
	 * @param session 
	 * @param model Spring framework Model
	 * @return Spring framework View name
	 */
	@RequestMapping(value = "/tictactoe", method = RequestMethod.GET)
	public String game(HttpSession session, Model model) {
		
		HashGameState HashGameState = getStateFromSession(session);
		if(HashGameState == null) {
			log.info("HashGameState is null; starting new game");
			HashGameState = new HashGameState();
			putStateInSession(session, HashGameState);
		}
		model.addAttribute(Utils.HASH_GAME_STATE, HashGameState);
		
		return Utils.VIEW_GAME;
	}

	/**
	 * Resets game to initial state, known as PRE_BEGIN
	 *
	 * @param session
	 * @param model Spring framework Model
	 * @return Spring framework View name
	 */
	@RequestMapping(value = "/tictactoe/reset", method = RequestMethod.GET)
	public String reset(HttpSession session, Model model) {
		
		log.info("Resetting new game");
		HashGameState HashGameState = new HashGameState();
		putStateInSession(session, HashGameState);
		model.addAttribute(Utils.HASH_GAME_STATE, HashGameState);
		
		return Utils.VIEW_GAME;
	}
	
	/**
	 * Starts a new game
	 * Disclaimer: given up on documenting perfectly properly
	 */
	@RequestMapping(value = "/tictactoe/new", method = RequestMethod.GET)
	public String gameNew(HttpSession session, Model model) {
		
		log.info("Starting new game");
		HashGameState HashGameState = getStateFromSession(session);
		HashGameState.startNewGame();
		model.addAttribute(Utils.HASH_GAME_STATE, HashGameState);
		
		return Utils.VIEW_GAME;
	}
	
	/**
	 * Fills requested position with player's mark
	 */
	@RequestMapping(value = "/tictactoe/move", method = RequestMethod.GET)
	public String playerMove( HttpSession session,
			@RequestParam(value = "row", required = true) Integer row, 
			@RequestParam(value = "col", required = true) Integer col, 
			Model model) {
		
		HashGameState HashGameState = getStateFromSession(session);
		model.addAttribute(Utils.HASH_GAME_STATE, HashGameState);
		log.info("move=(" + row + ", " + col + ")");
		
		// If not in the midst of a game, don't allow move.
		if(!HashGameState.getGameStage().equals(GameStage.IN_GAME)) {
			log.info("Game not in progress); ignoring move request.");
			return Utils.VIEW_GAME;
		}
		
		Board board = HashGameState.getBoard();
		try {
			board.move(row, col, HashGameState.getTurn());
			checkBoard(HashGameState);	
		}
		catch( Exception e )
		{
			log.error("Cannot complete move", e.getMessage());
		}
		
		return Utils.VIEW_GAME;
	}
	
	/**
	 * Checks if a winner can be declared, or if there is a draw.
	 * Otherwise, switch active player
	 */
	public void checkBoard(HashGameState HashGameState) {
		Board board = HashGameState.getBoard();
	
		if(board.isWinner(HashGameState.getTurn())) {
			if(HashGameState.getTurn().equals(Board.Marker.O)) {
				HashGameState.setGameMessage("O wins!");
			}
			else {
				HashGameState.setGameMessage("X wins!");
			}
			HashGameState.setGameStage(GameStage.POST_GAME);
		}
		else if(board.isDraw()) {
			HashGameState.setGameMessage("It's a draw!");
			HashGameState.setGameStage(GameStage.POST_GAME);
		}
		else
		{
			if(HashGameState.getTurn() == Board.Marker.X) {
				HashGameState.setTurn(Board.Marker.O);
				HashGameState.setTurnMessage("Turn: O");
			}
			else {
				HashGameState.setTurn(Board.Marker.X);
				HashGameState.setTurnMessage("Turn: X");
			}
		}
	}
	
	/**
	 * Convenience method to retrieve game state from session.
	 */
	private HashGameState getStateFromSession(HttpSession session)
	{
		HashGameState HashGameState = (HashGameState)session.getAttribute(Utils.HASH_GAME_STATE);
		if(HashGameState == null) {
			log.info("New HashGameState created and put in session");
			HashGameState = new HashGameState();
			putStateInSession(session, HashGameState);
		}
		return HashGameState;
	}
	
	/**
	 * Saves game state in session.
	 */
	private void putStateInSession(HttpSession session, HashGameState HashGameState) {
		session.setAttribute(Utils.HASH_GAME_STATE, HashGameState);
	}
}
