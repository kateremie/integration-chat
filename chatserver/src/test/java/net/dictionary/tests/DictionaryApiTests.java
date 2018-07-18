package net.dictionary.tests;

import net.dictionary.api.client.DictionaryClient;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class DictionaryApiTests {

    @Test
    public void firstTest() {
        DictionaryClient dictionaryClient = new DictionaryClient();
        String translation = dictionaryClient.sendGet("Привет", "ru-en");
        assertEquals(translation, "{\"code\":200,\"lang\":\"ru-en\",\"text\":[\"Hi\"]}");
    }
}