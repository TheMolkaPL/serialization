package org.diorite.serialization.naming;

import org.apache.commons.lang3.StringUtils;
import org.diorite.commons.ParserContext;
import org.diorite.serialization.setting.Property;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class StandardFieldNamingStrategies
{
    public static final PropertyNamingStrategy IDENTITY                          = Property::getRawName;
    public static final PropertyNamingStrategy LOWER_CASE                        = (p) -> p.getRawName().toLowerCase();
    public static final PropertyNamingStrategy UPPER_CASE                        = (p) -> p.getRawName().toUpperCase();
    public static final PropertyNamingStrategy UPPER_CAMEL_CASE                  = (p) -> transform(p.getRawName(), '\0', true, true);
    public static final PropertyNamingStrategy CAMEL_CASE                        = (p) -> transform(p.getRawName(), '\0', false, true);
    public static final PropertyNamingStrategy UPPER_CAMEL_CASE_WITH_SPACES      = (p) -> transform(p.getRawName(), ' ', true, true);
    public static final PropertyNamingStrategy CAMEL_CASE_WITH_SPACES            = (p) -> transform(p.getRawName(), ' ', false, true);
    public static final PropertyNamingStrategy LOWER_CASE_WITH_SPACES            = (p) -> transform(p.getRawName(), ' ', false, false);
    public static final PropertyNamingStrategy UPPER_CAMEL_CASE_WITH_UNDERSCORES = (p) -> transform(p.getRawName(), '_', true, true);
    public static final PropertyNamingStrategy CAMEL_CASE_WITH_UNDERSCORES       = (p) -> transform(p.getRawName(), '_', false, true);
    public static final PropertyNamingStrategy LOWER_CASE_WITH_UNDERSCORES       = (p) -> transform(p.getRawName(), '_', false, false);
    public static final PropertyNamingStrategy UPPER_CAMEL_CASE_WITH_HYPHEN      = (p) -> transform(p.getRawName(), '-', true, true);
    public static final PropertyNamingStrategy CAMEL_CASE_WITH_HYPHEN            = (p) -> transform(p.getRawName(), '-', false, true);
    public static final PropertyNamingStrategy LOWER_CASE_WITH_HYPHEN            = (p) -> transform(p.getRawName(), '-', false, false);

    private static final char[] splitChars = {' ', '-', '.', ',', '_', '/', '\\', '=', '+', ':', ';'};

    static
    {
        Arrays.sort(splitChars);
    }

    private static List<String> splitToWords(String rawName)
    {
        int length = rawName.length();
        List<String> words = new ArrayList<>(5);
        StringBuilder wordBuilder = new StringBuilder();
        int i = 0;

        String lastOneLetter = null;
        ParserContext parserContext = new ParserContext(rawName);
        // skip special
        while (parserContext.hasNext())
        {
            char c = parserContext.next();
            if (Arrays.binarySearch(splitChars, c) < 0)
            {
                parserContext.previous();
                break;
            }
        }
        boolean endWord = false;
        while (parserContext.hasNext())
        {
            char c = parserContext.next();
            if (Arrays.binarySearch(splitChars, c) >= 0 || Character.isUpperCase(c))
            {
                endWord = true;
                if (! Character.isUpperCase(c))
                {
                    continue;
                }
            }
            if (endWord)
            {
                if (wordBuilder.length() == 1 && Character.isUpperCase(c))
                {
                    if (lastOneLetter == null)
                    {
                        lastOneLetter = wordBuilder.toString();
                    }
                    else
                    {
                        lastOneLetter = wordBuilder.toString();
                        words.set(words.size() - 1, words.get(words.size() - 1) + lastOneLetter);
                        wordBuilder = new StringBuilder();
                        wordBuilder.append(c);
                        continue;
                    }
                }
                else
                {
                    lastOneLetter = null;
                }
                endWord = false;
                words.add(wordBuilder.toString());
                wordBuilder = new StringBuilder();
                wordBuilder.append(c);
            }
            else
            {
                wordBuilder.append(c);
            }
        }
        if (wordBuilder.length() > 0)
        {
            if (wordBuilder.length() == 1 && Character.isUpperCase(wordBuilder.charAt(0)) && (lastOneLetter != null))
            {
                lastOneLetter = wordBuilder.toString();
                words.set(words.size() - 1, words.get(words.size() - 1) + lastOneLetter);
            }
            else
            {
                words.add(wordBuilder.toString());
            }
        }
        return words;
    }

    private static String transform(String rawName, char wordSeparator, boolean capitalizeFirstWord, boolean capitalizeWords)
    {
        List<String> words = splitToWords(rawName);
        StringBuilder nameBuilder = new StringBuilder();

        boolean first = true;
        for (int wordIndex = 0, wordsSize = words.size(); wordIndex < wordsSize; wordIndex++)
        {
            if (wordIndex != 0 && wordSeparator != '\0')
            {
                nameBuilder.append(wordSeparator);
            }
            String word = words.get(wordIndex);
            nameBuilder.append(capitalize(word, first ? capitalizeFirstWord : capitalizeWords));
            first = false;
        }

        // append additional underscores used in name
        int underscoresStart = 0;
        int underscoresEnd = 0;
        for (int i = 0; i < rawName.length(); i++)
        {
            if (rawName.charAt(i) == '_')
            {
                underscoresStart += 1;
            }
            else
            {
                break;
            }
        }
        for (int i = rawName.length() - 1; i >= 0; i--)
        {
            if (rawName.charAt(i) == '_')
            {
                underscoresEnd += 1;
            }
            else
            {
                break;
            }
        }

        return StringUtils.repeat('_', underscoresStart) + nameBuilder.toString() + StringUtils.repeat('_', underscoresEnd);
    }

    private static String capitalize(String str, boolean capitalize)
    {
        StringBuilder wordBuilder = new StringBuilder(str.length());
        boolean firstLetter = true;
        for (int i = 0; i < str.length(); i++)
        {
            char c = str.charAt(i);
            if (Character.isAlphabetic(c))
            {
                if (firstLetter)
                {
                    wordBuilder.append(capitalize ? Character.toUpperCase(c) : Character.toLowerCase(c));
                    firstLetter = false;
                    continue;
                }
                c = Character.toLowerCase(c);
            }
            wordBuilder.append(c);
        }
        return wordBuilder.toString();
    }
}
