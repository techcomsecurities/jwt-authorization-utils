package com.tcbs.authorization;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.tcbs.authorization.common.Constants.BEARER;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(JUnit4.class)
public class AuthorUtilsTest {



    @Test
    public void findSubStringRegex_test() {
        String regex = String.format("(%s)\\s(.*)", BEARER);
        String string = String.format("%s abcdef", BEARER);

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(string);

        assertTrue(matcher.find());

        System.out.println(regex);
        System.out.println(matcher.group(0));
        System.out.println(matcher.group(1));
        System.out.println(matcher.group(2));

        assertEquals("abcdef", matcher.group(2));
    }
}