package de.icevizion.xerus.api.kit;

import at.rxcki.strigiformes.MessageProvider;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class TranslatedKitTest {

    MessageProvider messageProvider;

    @BeforeAll
    void init() {
        this.messageProvider = Mockito.mock(MessageProvider.class);
    }

    @Test
    void testKitConstructor() {
        var kit = new TranslatedKit(messageProvider, "key", 9, true);
        assertNotNull(kit);
    }

    @Test
    void testKitConstructorWithException() {
        assertThrowsExactly(
                IllegalArgumentException.class,
                () -> new TranslatedKit(messageProvider, "Key", 12, false),
                "The maximum size for the HotBar is nine"
        );
    }

    @Test
    void testStaticKitCreation() {
        var kit = TranslatedKit.of(messageProvider, 9, "Test", true);
        assertNotNull(kit);
    }
}