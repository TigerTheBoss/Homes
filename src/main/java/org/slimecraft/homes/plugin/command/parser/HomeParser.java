package org.slimecraft.homes.plugin.command.parser;

import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.cloud.caption.CaptionVariable;
import org.incendo.cloud.caption.StandardCaptionKeys;
import org.incendo.cloud.context.CommandContext;
import org.incendo.cloud.context.CommandInput;
import org.incendo.cloud.exception.parsing.ParserException;
import org.incendo.cloud.paper.util.sender.PlayerSource;
import org.incendo.cloud.parser.ArgumentParseResult;
import org.incendo.cloud.parser.ArgumentParser;
import org.incendo.cloud.parser.ParserDescriptor;
import org.incendo.cloud.suggestion.BlockingSuggestionProvider;
import org.slimecraft.homes.api.model.Home;
import org.slimecraft.homes.api.model.User;

import java.util.ArrayList;
import java.util.List;

public class HomeParser<C extends PlayerSource> implements ArgumentParser<C, Home>, BlockingSuggestionProvider.Strings<C> {

    public static <C extends PlayerSource> ParserDescriptor<C, Home> homeParser() {
        return ParserDescriptor.of(new HomeParser<>(), Home.class);
    }

    @Override
    public @NonNull ArgumentParseResult<@NonNull Home> parse(@NonNull CommandContext<@NonNull C> commandContext, @NonNull CommandInput commandInput) {
        final String input = commandInput.peekString();
        final Player player = commandContext.sender().source();
        final User user = User.from(player.getUniqueId());

        for (Home home : user.getHomes()) {
            if (home.getName().equals(input)) {
                commandInput.read();
                return ArgumentParseResult.success(home);
            }
        }

        return ArgumentParseResult.failure(new HomeParseException(input, commandContext));
    }

    @Override
    public @NonNull Iterable<@NonNull String> stringSuggestions(@NonNull CommandContext<C> commandContext, @NonNull CommandInput commandInput) {
        final Player player = commandContext.sender().source();
        final User user = User.from(player.getUniqueId());
        final List<String> suggestions = new ArrayList<>();

        for (Home home : user.getHomes()) {
            suggestions.add(home.getName());
        }

        return suggestions;
    }

    public static final class HomeParseException extends ParserException {
        private final String input;

        public HomeParseException(String input, CommandContext<?> context) {
            super(HomeParser.class, context, StandardCaptionKeys.EXCEPTION_INVALID_ARGUMENT, CaptionVariable.of("input", input));
            this.input = input;
        }

        public String input() {
            return this.input;
        }
    }
}
