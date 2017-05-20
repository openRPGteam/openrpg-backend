package info.openrpg.telegram.commands.actions;

import info.openrpg.gameserver.WorldInstance;
import info.openrpg.image.processing.RequestSender;
import info.openrpg.telegram.constants.Command;
import info.openrpg.telegram.io.InputMessage;
import info.openrpg.telegram.io.MessageWrapper;
import org.telegram.telegrambots.api.objects.User;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EchoCommandTest {
    private static final String RESPONSE_MESSAGE = "Hello world";
    private static final long CHAT_ID = 1L;
    private ExecutableCommand command;
    private WorldInstance worldInstance;

    @BeforeClass
    public void setUp() {
        RequestSender requestSenderMock = mock(RequestSender.class);
        when(requestSenderMock.ping()).thenReturn(RESPONSE_MESSAGE);
        command = new EchoCommand(requestSenderMock);
        worldInstance = new WorldInstance();
    }

    @Test
    public void testExecute() throws Exception {
        InputMessage inputMessage = new InputMessage(Command.ECHO, 1L, new User());
        List<MessageWrapper> wrappers = command.execute(inputMessage, worldInstance);
        Assert.assertFalse(wrappers.isEmpty());
        Assert.assertTrue(wrappers.size() == 1);
        Assert.assertNotNull(wrappers.get(0).getMessage());
        Assert.assertEquals(wrappers.get(0).getMessage().getText(), RESPONSE_MESSAGE);
        Assert.assertEquals(wrappers.get(0).getMessage().getChatId(), String.valueOf(CHAT_ID));
    }

    @Test
    public void testHandleCrash() throws Exception {
        InputMessage inputMessage = new InputMessage(Command.ECHO, 1L, new User());
        Assert.assertEquals(Collections.emptyList(), command.handleCrash(new RuntimeException(), inputMessage, worldInstance));
    }

}