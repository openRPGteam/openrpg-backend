package info.openrpg.telegram.commands.actions;

import com.google.common.base.Joiner;
import info.openrpg.database.models.Player;
import info.openrpg.database.repositories.PlayerDao;
import info.openrpg.gameserver.WorldInstance;
import info.openrpg.gameserver.enums.GameClass;
import info.openrpg.gameserver.enums.MoveDirections;
import info.openrpg.gameserver.enums.Race;
import info.openrpg.gameserver.model.actors.PlayerStats;
import info.openrpg.gameserver.model.world.Location;
import info.openrpg.image.processing.RequestSender;
import info.openrpg.telegram.commands.Message;
import info.openrpg.telegram.constants.Command;
import info.openrpg.telegram.constants.Direction;
import info.openrpg.telegram.io.InlineButton;
import info.openrpg.telegram.io.InputMessage;
import info.openrpg.telegram.io.MessageWrapper;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.objects.User;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MoveCommandTest {
    private static final Long CHAT_ID = 13L;
    private static final Joiner JOINER = Joiner.on(" ").skipNulls();
    private static final String USER_NAME = "DarkCasual";
    private static final String PHOTO_NAME = "image";
    private static final InputStream STREAM = mock(InputStream.class);

    private MoveCommand command;
    private WorldInstance worldInstance;

    @BeforeClass
    public void setUp() throws Exception {
        PlayerDao playerDao = mock(PlayerDao.class);
        RequestSender requestSender = mock(RequestSender.class);
        when(requestSender.createImage(any())).thenReturn(Optional.of(STREAM));

        Player player = Player.builder()
                .firstName("Dark")
                .lastName("Casual")
                .userName(USER_NAME)
                .id(CHAT_ID.intValue())
                .build();
        when(playerDao.findPlayerByUsername(USER_NAME)).thenReturn(Optional.of(player));
        worldInstance = new WorldInstance();
        info.openrpg.gameserver.model.actors.Player serverPlayer = mock(info.openrpg.gameserver.model.actors.Player.class);
        when(serverPlayer.getPlayerId()).thenReturn(CHAT_ID.intValue());
        when(serverPlayer.getCurLocation()).thenReturn(new Location(1, 1, 1, 1));
        worldInstance.addPlayer(serverPlayer);
        command = new MoveCommand(playerDao, requestSender);
    }

    @Test
    public void testWithoutArgsExecute() throws Exception {
        User user = mock(User.class);
        when(user.getUserName()).thenReturn(USER_NAME);
        InputMessage inputMessage = new InputMessage(Command.MOVE, CHAT_ID, user);
        List<MessageWrapper> wrappers = command.execute(inputMessage, worldInstance);

        Assert.assertFalse(wrappers.isEmpty());
        Assert.assertTrue(wrappers.size() == 1);
        SendMessage message = wrappers.get(0).getMessage();
        Assert.assertNotNull(message);
        Assert.assertEquals(message.getText(), Message.MOVE_BUTTONS.sendTo(CHAT_ID).getText());
        Assert.assertEquals(Long.valueOf(message.getChatId()), CHAT_ID);
    }

    @Test
    public void testFullCommandExecute() throws Exception {
        User user = mock(User.class);
        when(user.getId()).thenReturn(CHAT_ID.intValue());
        when(user.getUserName()).thenReturn(USER_NAME);
        InputMessage inputMessage = new InputMessage(
                JOINER.join(Command.MOVE, MoveDirections.SOUTH.name()),
                CHAT_ID,
                user
        );
        List<MessageWrapper> wrappers = command.execute(inputMessage, worldInstance);

        Assert.assertFalse(wrappers.isEmpty());
        MessageWrapper.PhotoToMultipleUsers photo = wrappers.get(0).getPhotoToMultipleUsers();
        Assert.assertNotNull(photo);
        Assert.assertEquals(photo.getActualPhoto().getPhotoName(), PHOTO_NAME);
        Assert.assertEquals(photo.getActualPhoto().getChatId(), String.valueOf(CHAT_ID));
        Assert.assertEquals(photo.getActualPhoto().getReplyMarkup(), InlineButton.moveButtonList());
        Assert.assertTrue(photo.getReceivers().isEmpty());
    }

    @Test
    public void testHandleCrash() throws Exception {
        InputMessage message = new InputMessage("", 13L, new User());
        Assert.assertEquals(command.handleCrash(new RuntimeException(), message, worldInstance), Collections.emptyList());
    }

}