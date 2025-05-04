package net.theevilreaper.xerus.api.kit;

import net.theevilreaper.aves.i18n.AvesTranslationRegistry;
import net.theevilreaper.aves.i18n.TextData;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.kyori.adventure.translation.GlobalTranslator;
import net.kyori.adventure.translation.TranslationRegistry;
import org.junit.jupiter.api.Test;

import java.text.MessageFormat;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class TranslatedKitTest {

    @Test
    void testTranslatedKit() {
        var kit = TranslatedKit.of("key", true);
        kit.setDescription(new TextData("description"));
        var translator = new AvesTranslationRegistry(TranslationRegistry.create(Key.key("test", "test")));
        translator.register("description", Locale.ENGLISH, new MessageFormat("fakeData"));
        GlobalTranslator.translator().addSource(translator);

        assertEquals("fakeData", PlainTextComponentSerializer.plainText().serialize(kit.getDescription(Locale.ENGLISH)));
        assertNotNull(kit);

    }

    @Test
    void testKitConstructor() {
        var kit = new TranslatedKit("key", true);
        assertNotNull(kit);
    }

    @Test
    void testStaticKitCreation() {
        var kit = TranslatedKit.of("Test", true);
        assertNotNull(kit);
    }
}