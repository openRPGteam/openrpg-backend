package info.openrpg.telegram.io;

import com.google.common.base.Joiner;
import info.openrpg.database.models.telegram.Player;
import info.openrpg.telegram.constants.Command;
import info.openrpg.telegram.constants.Direction;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InlineButton {
    private static final Joiner JOINER = Joiner.on(" ").skipNulls();

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
        List<List<InlineKeyboardButton>> keys = new ArrayList<>();
        List<InlineKeyboardButton> topRow = new ArrayList<>();
        topRow.add(createInlineKeyboardButton("🢄", getJoin(Direction.TOP_LEFT)));
        topRow.add(createInlineKeyboardButton("🢁", getJoin(Direction.TOP)));
        topRow.add(createInlineKeyboardButton("🢅", getJoin(Direction.TOP_RIGHT)));
        keys.add(topRow);
        List<InlineKeyboardButton> middleRow = new ArrayList<>();
        middleRow.add(createInlineKeyboardButton("🢀", getJoin(Direction.LEFT)));
        middleRow.add(createInlineKeyboardButton("◉", "/"));
        middleRow.add(createInlineKeyboardButton("🢂", getJoin(Direction.RIGHT)));
        keys.add(middleRow);
        List<InlineKeyboardButton> bottomRow = new ArrayList<>();
        bottomRow.add(createInlineKeyboardButton("🢇", getJoin(Direction.BOTTOM_LEFT)));
        bottomRow.add(createInlineKeyboardButton("🢃", getJoin(Direction.BOTTOM)));
        bottomRow.add(createInlineKeyboardButton("🢆", getJoin(Direction.BOTTOM_RIGHT)));
        keys.add(bottomRow);
        return new InlineKeyboardMarkup().setKeyboard(keys);
    }

    private static String getJoin(Direction direction) {
        return JOINER.join(Command.MOVE, direction.getX(), direction.getY());
    }

    private static List<InlineKeyboardButton> createInlineKeyboardButtonRow(String text, String callback) {
        return Collections.singletonList(createInlineKeyboardButton(text, callback));
    }

    private static InlineKeyboardButton createInlineKeyboardButton(String text, String callback) {
        return new InlineKeyboardButton().setText(text).setCallbackData(callback);
    }
}
