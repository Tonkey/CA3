package security;

import javax.xml.bind.DatatypeConverter;

public class Secret {

    private static byte[] SHARED_SECRET;

    public static byte[] getSecret() {

        if (SHARED_SECRET == null) {

            SHARED_SECRET = DatatypeConverter.parseHexBinary("386f9691fb6e8bb7e58c8cc3f190ab8eb3a62f9d9be0e3fd1bfc5ed8faa3839af727c9b555d6d9a041804da6c91787b512bb32495d16bc483615943931bd0b4d");

        }

        return SHARED_SECRET;
    }
}
