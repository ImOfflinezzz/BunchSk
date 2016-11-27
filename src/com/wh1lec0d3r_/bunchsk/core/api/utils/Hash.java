package com.wh1lec0d3r_.bunchsk.core.api.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hash {

    public enum HashEnum {

        MD5("MD5", 1),
        SHA1("SHA-1", 2),
        SHA256("SHA-256", 3),
        SHA384("SHA-384", 4),
        SHA512("SHA-512", 5);

        private final String name;
        private final int id;

        HashEnum(String name, int id) {
            this.name = name;
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }

    public static HashEnum getEnumById(int id) {
        for(HashEnum hashEnum : HashEnum.values()) {
            if(hashEnum.getId() == id)
                return hashEnum;
        }
        return null;
    }

    public static String getHash(HashEnum hashEnum, String toHash) {
        String hash = "";

        try {
            MessageDigest messageDigest = MessageDigest.getInstance(hashEnum.getName());
            messageDigest.update(toHash.getBytes());

            byte[] bytes = messageDigest.digest();

            StringBuilder stringBuilder = new StringBuilder();

            for(byte aByte : bytes) {
                stringBuilder.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }

            hash = stringBuilder.toString();

        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }

        System.out.println(hash);
        return hash;
    }

    public static boolean validate(HashEnum hashEnum, String first, String second) {
        return getHash(hashEnum, first).equals(getHash(hashEnum, second));
    }
}
