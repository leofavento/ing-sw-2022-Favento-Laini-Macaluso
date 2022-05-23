package it.polimi.ingsw.client.cli;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.cli.gameStates.NicknameState;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.fromServer.CommunicationMessage;
import it.polimi.ingsw.messages.fromServer.ErrorMessage;

public class MessageReceiver {
    private final CLI cli;
    private final Client client;

    public MessageReceiver(CLI cli, Client client) {
        this.cli = cli;
        this.client = client;
    }

    public void receiveMessage(Message message) {
        if (message instanceof CommunicationMessage) {
            receiveMessage((CommunicationMessage) message);
        } else if (message instanceof ErrorMessage) {
            receiveMessage((ErrorMessage) message);
        }
    }

    public void receiveMessage(CommunicationMessage message) {
        System.out.println(message.getMessage());
        if (message == CommunicationMessage.SUCCESS) {
            cli.setSuccess(true);
            synchronized (cli.getGameState()) {
                cli.getGameState().notifyAll();
            }
        } else if (message == CommunicationMessage.ENTER_NICKNAME) {
            cli.setGameState(new NicknameState());
        }
    }

    public void receiveMessage(ErrorMessage message) {
        System.out.println(message.getMessage());

        synchronized (cli.getGameState()) {
            cli.getGameState().notifyAll();
        }
    }
}
