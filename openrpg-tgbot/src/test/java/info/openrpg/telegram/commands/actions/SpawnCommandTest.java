package info.openrpg.telegram.commands.actions;

import info.openrpg.database.models.Player;
import info.openrpg.database.repositories.PlayerDao;
import info.openrpg.gameserver.WorldInstance;
import info.openrpg.image.processing.RequestSender;
import info.openrpg.telegram.constants.Command;
import info.openrpg.telegram.io.InlineButton;
import info.openrpg.telegram.io.InputMessage;
import info.openrpg.telegram.io.MessageWrapper;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.objects.User;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SpawnCommandTest {
    private static final Long CHAT_ID = 13L;
    private static final String USER_NAME = "DarkCasual";
    private static final String PHOTO_NAME = "spawn";
    private static final InputStream STREAM = mock(InputStream.class);

    private SpawnCommand command;
    private WorldInstance worldInstance;

    @BeforeMethod
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
        command = new SpawnCommand(playerDao, requestSender);
    }

    @Test
    public void testExecute() throws Exception {
        User user = mock(User.class);
        when(user.getUserName()).thenReturn(USER_NAME);
        InputMessage inputMessage = new InputMessage(Command.START, CHAT_ID, user);

        List<MessageWrapper> wrappers = command.execute(inputMessage, worldInstance);

        Assert.assertFalse(wrappers.isEmpty());
        SendPhoto photo = wrappers.get(0).getPhoto();
        Assert.assertNotNull(photo);
        Assert.assertEquals(photo.getPhotoName(), PHOTO_NAME);
        Assert.assertEquals(photo.getChatId(), String.valueOf(CHAT_ID));
        Assert.assertEquals(photo.getReplyMarkup(), InlineButton.moveButtonList());
    }

    @Test
    public void testHandleCrash() throws Exception {
        InputMessage message = new InputMessage("", 13L, new User());
        Assert.assertEquals(command.handleCrash(new RuntimeException(), message, worldInstance), Collections.emptyList());
    }

    @Test
    public void testWrongStateCrash() throws Exception {
        InputMessage message = new InputMessage("", CHAT_ID, new User());
        List<MessageWrapper> messageWrappers = command.handleCrash(new IllegalStateException(), message, worldInstance);
        Assert.assertFalse(messageWrappers.isEmpty());
        Assert.assertTrue(messageWrappers.size() == 1);
        MessageWrapper messageWrapper = messageWrappers.get(0);
        Assert.assertNotNull(messageWrapper);
        SendMessage sendMessage = messageWrapper.getMessage();
        Assert.assertNotNull(sendMessage);
        Assert.assertEquals(sendMessage.getText(), "В данный момент сервер недоступен");
        Assert.assertEquals(sendMessage.getChatId(), String.valueOf(CHAT_ID));
    }

}