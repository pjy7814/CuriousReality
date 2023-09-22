package com.ssafy.curious.global.utils;

import java.util.regex.Pattern;

public class RegexUtil {
    private static final String PW_REGEX = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{8,20}$";
    private static final String EMAIL_REGEX = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]$";
    private static final String CONTACT_REGEX = "^01(?:0|1|[6-9])[-]?(\\d{3}|\\d{4})[-]?(\\d{4})$";

    private static final String NAME_REGEX = "^[a-zA-Zㄱ-ㅎ가-힣]*$";

    public static Boolean checkEmailRegex(String email){return Pattern.matches(EMAIL_REGEX,email);}
    public static Boolean checkPasswordRegex(String password){return Pattern.matches(PW_REGEX,password);}
    public static Boolean checkContactRegex(String contact){return Pattern.matches(CONTACT_REGEX,contact);}
    public static Boolean checkNameRegex(String name){return Pattern.matches(NAME_REGEX,name);}


}

