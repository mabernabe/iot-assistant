package com.iotassistant.models;

import java.io.ByteArrayInputStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.methods.ParseMode;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

@Component
public class TelegramBotManager extends TelegramLongPollingBot{
	
	@Value("${bot.token}")
	private String telegramToken;
	
	@Value("${bot.username}")
	private String telegramUsername;
	
	@Value("${bot.chatid}")
	private String botChatId;
	
	
	
	public TelegramBotManager() {
		super();
	}

	@Override
	public void onUpdateReceived(Update update) {
	}

	@Override
	public String getBotUsername() {
		return telegramUsername;
	}

	@Override
	public String getBotToken() {
		return telegramToken;
	}

	public void sendTelegramTextMessage(String telegramMessage) throws TelegramApiException {
		SendMessage msg = new SendMessage();
		msg.setParseMode(ParseMode.MARKDOWN);
		msg.setText(telegramMessage);
		msg.setChatId(botChatId);
		execute(msg);	
	}
	
	public void sendTelegramPictureMessage(String telegramMessage, byte[] picture) throws TelegramApiException {
		SendPhoto msg = new SendPhoto();
		msg.setParseMode(ParseMode.MARKDOWN);
		msg.setChatId(botChatId);
		msg.setNewPhoto(telegramMessage, new ByteArrayInputStream(picture));
		sendPhoto(msg);
		
	}
	
	
	
	public boolean connected() {
		try {
			this.getMe();
			return true;
		} catch (TelegramApiException e) {
			return false;
		}
	}

}
