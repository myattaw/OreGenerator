package com.reliableplugins.oregenerator.util;

public enum Message {

    LIST_GENERATORS(Util.color("&7Generators: {LIST}")),
    GENERATOR_CREATED(Util.color("&7New generator created: {NAME}")),
    GENERATOR_REMOVED(Util.color("&7Generator removed: {NAME}")),
    RELOADED(Util.color("&7Oregenerator has been reloaded")),

    HELP_CREATE_GENERATOR(Util.color("&cInvalid usage; /oregen create <name>")),
    HELP_REMOVE_GENERATOR(Util.color("&cInvalid usage; /oregen remove <name>")),

    SELECT_ERROR(Util.color("&cYou do not have permission to use this generator")),
    SELECT_GENERATOR(Util.color("&aYou have selected the %s generator")),

    UPGRADE_CONFIRM_TITLE(Util.color("&4&lPURCHASE FOR $[COST]")),
    UPGRADE_PREVIOUS(Util.color("&cYou must unlock the previous level.")),
    UPGRADE_CANCELLED(Util.color("&cYou have cancelled your upgrade.")),
    UPGRADE_PURCHASED(Util.color("Purchased &a[NAME]&f upgrade for &a$[COST]")),

    LEVEL_WRONG_ARGS(Util.color("&cInvalid usage; /oregen level <add|rem> <name>")),
    LEVEL_ADD(Util.color("&aYou have added a new generator for %s!")),
    LEVEL_REM(Util.color("&cYou have removed a generator from %s!")),

    ERROR_PCTG_INVALID(Util.color("&cInvalid percentage, enter a number")),
    ERROR_PCTG_HIGH(Util.color("&cThat percentage is too high (must be <= {MAX})")),
    ERROR_PCTG_LOW(Util.color("&cThat percentage is too low")),

    ERROR_ALREADY_0(Util.color("&cYou are already at 0%")),
    ERROR_ALREADY_100(Util.color("&cYou are already at 100%")),

    ERROR_INVALID_GENERATOR(Util.color("&cInvalid generator.")),
    ERROR_GENERATOR_EXISTS(Util.color("&cThat generator already exists.")),

    ERROR_NOT_ENOUGH_MONEY(Util.color("&cYou do not have enough money.")),
    ERROR_TOO_MANY_ARGS(Util.color("&cToo many arguments; /oregen")),
    ERROR_NOT_ENOUGH_ARGS(Util.color("&cNot enough arguments; /oregen")),
    ERROR_PERMISSION(Util.color("&cYou do not have access to this command.")),
    ERROR_NOT_PLAYER(Util.color("&cOnly players may execute this command."));


    private final String text;
    private final String header = Util.color("&8&l(&2&lOreGenerator&8&l)&r ");

    Message(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public String getMessage() {
        return header + this.text;
    }
}
