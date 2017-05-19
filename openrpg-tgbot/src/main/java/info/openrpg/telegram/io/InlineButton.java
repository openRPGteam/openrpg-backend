package info.openrpg.telegram.io;

import com.google.common.base.Joiner;
import info.openrpg.database.models.Player;
import info.openrpg.gameserver.enums.MoveDirections;
import info.openrpg.telegram.constants.Command;
import info.openrpg.telegram.constants.Direction;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InlineButton {
    private static final Joiner JOINER = Joiner.on(" ").skipNulls();
    private static final InlineKeyboardMarkup moveKeyboard;
    static {
        List<List<InlineKeyboardButton>> keys = new ArrayList<>();
        List<InlineKeyboardButton> topRow = new ArrayList<>();
        topRow.add(createInlineKeyboardButton("🢄", getJoin(MoveDirections.NORTHWEST)));
        topRow.add(createInlineKeyboardButton("🢁", getJoin(MoveDirections.NORTH)));
        topRow.add(createInlineKeyboardButton("🢅", getJoin(MoveDirections.NORTHEAST)));
        keys.add(topRow);
        List<InlineKeyboardButton> middleRow = new ArrayList<>();
        middleRow.add(createInlineKeyboardButton("🢀", getJoin(MoveDirections.WEST)));
        middleRow.add(createInlineKeyboardButton("◉", "/"));
        middleRow.add(createInlineKeyboardButton("🢂", getJoin(MoveDirections.EAST)));
        keys.add(middleRow);
        List<InlineKeyboardButton> bottomRow = new ArrayList<>();
        bottomRow.add(createInlineKeyboardButton("🢇", getJoin(MoveDirections.SOUTHWEST)));
        bottomRow.add(createInlineKeyboardButton("🢃", getJoin(MoveDirections.SOUTH)));
        bottomRow.add(createInlineKeyboardButton("🢆", getJoin(MoveDirections.SOUTHEAST)));
        keys.add(bottomRow);
        moveKeyboard = new InlineKeyboardMarkup().setKeyboard(keys);
    }

    public static InlineKeyboardMarkup helpInlineCommands() {
        List<List<InlineKeyboardButton>> keys = new ArrayList<>();
        keys.add(createInlineKeyboardButtonRow("Начать игру", Command.START));
        keys.add(createInlineKeyboardButtonRow("Помощь", Command.HELP));
        keys.add(createInlineKeyboardButtonRow("Информация об игроке", Command.PLAYER_INFO));
        keys.add(createInlineKeyboardButtonRow("Тыркнуть палкой", Command.PEEK_PLAYER));
        keys.add(createInlineKeyboardButtonRow("Отправить сообщение", Command.SEND_MESSAGE));
        return new InlineKeyboardMarkup().setKeyboard(keys);
    }

    public static InlineKeyboardMarkup playerList(String command, List<Player> players, int offset, int totalPlayersCount) {
        List<List<InlineKeyboardButton>> keys = new ArrayList<>();
        players.stream()
                .map(player -> createInlineKeyboardButtonRow(player.getUserName(), JOINER.join(command, player.getUserName())))
                .forEach(keys::add);
        return new InlineKeyboardMarkup().setKeyboard(keys);
    }

    public static InlineKeyboardMarkup moveButtonList() {
        return moveKeyboard;
    }

    private static String getJoin(MoveDirections direction) {
        return JOINER.join(Command.MOVE, direction.name());
    }

    private static List<InlineKeyboardButton> createInlineKeyboardButtonRow(String text, String callback) {
        return Collections.singletonList(createInlineKeyboardButton(text, callback));
    }

    private static InlineKeyboardButton createInlineKeyboardButton(String text, String callback) {
        return new InlineKeyboardButton().setText(text).setCallbackData(callback);
    }
}
