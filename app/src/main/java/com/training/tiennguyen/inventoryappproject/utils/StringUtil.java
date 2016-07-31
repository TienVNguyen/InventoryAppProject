/*
 * Copyright (c) 2016. Self Training Systems, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by TienNguyen <tien.workinfo@gmail.com - tien.workinfo@icloud.com>, October 2015
 */

package com.training.tiennguyen.inventoryappproject.utils;

import com.training.tiennguyen.inventoryappproject.constants.StringConstant;

/**
 * StringUtil
 *
 * @author TienNguyen
 */
public final class StringUtil {
    /**
     * emailPattern
     */
    private final static String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    /**
     * phonePattern
     */
    private final static String phonePattern = "^[+]?[0-9]{10,13}$";

    /**
     * Checks to see whether the input String is empty. Empty, in this context, means
     * <ul>
     * <li>is null
     * <li>has a length of zero
     * <li>contains only spaces
     * <li>contains the String "null" (not case sensitive)
     * </ul>
     *
     * @param inputStr to be examined.
     * @return true/false.
     */
    public static boolean isEmpty(final String inputStr) {
        return (inputStr == null || inputStr.trim().length() == 0 || (StringConstant.SNULL).equalsIgnoreCase(inputStr));
    }

    /**
     * Checks if a passed String is email. The definition of "email" is anything that can be validated.
     * If null, the answer is false.
     *
     * @param inputStr to be examined.
     * @return true==email / false==not email
     */
    public static boolean isEmail(final String inputStr) {
        return inputStr != null && inputStr.length() > 0 && inputStr.matches(emailPattern);
    }

    /**
     * Checks if a passed String is phone. The definition of "phone" is anything that can be validated.
     * If null, the answer is false.
     *
     * @param inputStr to be examined.
     * @return true==phone / false==not phone
     */
    public static boolean isPhone(final String inputStr) {
        return inputStr != null && inputStr.length() > 0 && inputStr.matches(phonePattern);
    }
}
