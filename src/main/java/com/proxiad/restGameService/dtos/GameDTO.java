package com.proxiad.restGameService.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class GameDTO {
    @NotBlank(message = "Please make a guess!")
    @Size(max = 1, message = "Please input only 1 character at a time!")
    @Pattern(regexp = "^[a-zA-Z]*$", message = "Please input only letters!")
    private Character input;

    public Character getInput() {
        return input;
    }

    public void setInput(Character input) {
        this.input = input;
    }
}
