package com.proxiad.restGameService.modelAssemblers;

import com.proxiad.restGameService.controllers.GameController;
import com.proxiad.restGameService.entities.Game;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class HangmanModelAssembler implements RepresentationModelAssembler<Game, EntityModel<Game>> {

    @Override
    public EntityModel<Game> toModel(Game hangman) {
        return EntityModel.of(hangman,
                linkTo(methodOn(GameController.class).one(hangman.getId())).withSelfRel(),
                linkTo(methodOn(GameController.class).all()).withRel("games"));
    }

    @Override
    public CollectionModel<EntityModel<Game>> toCollectionModel(Iterable<? extends Game> entities) {
        List<EntityModel<Game>> games = new ArrayList<>();
        entities.iterator().forEachRemaining(hangman -> games.add(toModel(hangman)));

        return CollectionModel.of(games, linkTo(methodOn(GameController.class).all()).withSelfRel());
    }
}
