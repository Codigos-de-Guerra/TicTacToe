package br.ufrn.tictactoe;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class HashGameController {
	private static final Logger log = LoggerFactory.getLogger(HashGameController.class);

	/**
	 * Starts new Tic Tac Toe game
	 *
	 * @param session
	 * @param model Spring framework Model
	 * @return Spring framework View name
	 */
	@RequestMapping(value = "/tictactoe", method = RequestMethod.GET)
	public String game(HttpSession session, Model model) {
		HashGameState HashGameState;
	}

	/**
	 * Resets game to initial state
	 *
	 * @param session
	 * @param model Spring framework Model
	 * @return Spring framework View name
	 */
	@RequestMapping(value = "/tictactoe/reset", method = RequestMethod.GET)
	public String reset( HttpSession session, Model model) {
		log.info("Resetting new game");
		HashGameState hashGameState = new HashGameState();
	}

}
