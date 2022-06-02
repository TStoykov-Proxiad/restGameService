package com.proxiad.restGameService.controllers;

import com.proxiad.restGameService.dtos.GameDTO;
import com.proxiad.restGameService.dtos.UserDTO;
import com.proxiad.restGameService.entities.Game;
import com.proxiad.restGameService.entities.User;
import com.proxiad.restGameService.modelAssemblers.HangmanModelAssembler;
import com.proxiad.restGameService.services.GameService;
import com.proxiad.restGameService.services.StatisticService;
import com.proxiad.restGameService.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(value = "/game")
//Todo migrate to ResponseEntity for all responces with according repsonce codes
public class GameController {
    private GameService gameService;
    private StatisticService statisticsService;
    private HangmanModelAssembler hangmanModelAssembler;
    private UserService userService;

    //get game by id
    @GetMapping(value = "/{gameID}")
    public EntityModel<Game> one(@PathVariable("gameID") Long id) {
        Game hangman = gameService.getById(id);
        return hangmanModelAssembler.toModel(hangman);
    }

    //get all games
    @GetMapping(value = "/all-games")
    public CollectionModel<EntityModel<Game>> all() {
        List<Game> games = gameService.getAllGames();
        return hangmanModelAssembler.toCollectionModel(games);
    }

    //get active games
    @GetMapping(value = "/active-games")
    public CollectionModel<EntityModel<Game>> getActive() {
        List<Game> games = gameService.getActiveGames();
        return hangmanModelAssembler.toCollectionModel(games);
    }
    //start game
    @PostMapping(value = "/start-game",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public EntityModel<Game> startGame(@RequestBody(required = false) UserDTO userDTO) {
        User user = null;
        if (userDTO != null && userDTO.getUsername() != null) {
            user = userService.getByUsername(userDTO.getUsername());
            //todo catch exception and redirect to signup
        }
        return hangmanModelAssembler.toModel(gameService.startNewGame(user));
    }

    //make try
    @PutMapping(value = "/{gameID}/makeTry",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public EntityModel<Game> makeTry(@PathVariable(value = "gameID") long gameId, @Valid @RequestBody GameDTO gameDTO, Principal principal /*todo implement check for user corresponding to game*/) {
        return hangmanModelAssembler.toModel((Game) gameService.makeTry(gameDTO.getInput(), gameId));
    }

    @Autowired
    public GameController(GameService hangManService, StatisticService statisticsService, HangmanModelAssembler hangmanModelAssembler, UserService userService) {
        this.gameService = hangManService;
        this.statisticsService = statisticsService;
        this.hangmanModelAssembler = hangmanModelAssembler;
        this.userService = userService;
    }
}
