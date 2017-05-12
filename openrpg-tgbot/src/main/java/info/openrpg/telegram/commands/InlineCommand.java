package info.openrpg.telegram.commands;

import com.google.common.base.Joiner;
import info.openrpg.constants.Commands;
import info.openrpg.database.models.Player;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InlineCommand {
    private static final Joiner JOINER = Joiner.on(" ").skipNulls();

    public static InlineKeyboardMarkup helpInlineCommands() {
        List<List<InlineKeyboardButton>> keys = new ArrayList<>();
        keys.add(createInlineKeyboardButtonRow("Начать игру", Commands.START));
        keys.add(createInlineKeyboardButtonRow("Помощь", Commands.HELP));
        keys.add(createInlineKeyboardButtonRow("Информация об игроке", Commands.PLAYER_INFO));
        keys.add(createInlineKeyboardButtonRow("Тыркнуть палкой", Commands.PEEK_PLAYER));
        keys.add(createInlineKeyboardButtonRow("Отправить сообщение", Commands.SEND_MESSAGE));
        return new InlineKeyboardMarkup().setKeyboard(keys);
    }

    public static InlineKeyboardMarkup playerList(String command, List<Player> players, int offset, int totalPlayersCount) {
        List<List<InlineKeyboardButton>> keys = new ArrayList<>();
        players.stream()
                .map(player ->
                        createInlineKeyboardButtonRow(
                                player.getUserName(),
                                JOINER.join(command, player.getUserName())
                        )
                )
                .forEach(keys::add);
        return new InlineKeyboardMarkup().setKeyboard(keys);
    }

    private static List<InlineKeyboardButton> createInlineKeyboardButtonRow(String text, String callback) {
        return Collections.singletonList(createInlineKeyboardButton(text, callback));
    }

    private static InlineKeyboardButton createInlineKeyboardButton(String text, String callback) {
        return new InlineKeyboardButton().setText(text).setCallbackData(callback);
    }
}
