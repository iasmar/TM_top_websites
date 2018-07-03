package com.iasmar.toronto.configuration;

/**
 * Created by Asmar on 15/10/2017.
 * <p>
 * This is the constant class that will provide the some major constants to the app such as Symbols.
 *
 * @author Asmar
 * @version 1
 * @since 0.1.0
 */
public class Constant {

    // Custom invalid int that will be used when we don`t have int.
    public static final int CUSTOM_INVALID_INT = -1;


    // Number of working days in topFives period
    public static int topFives_PERIOD_DAYS =10;

    // Websites required max shifts per day
    public static int MAX_SHIFTS_PER_DAY = 2;

    // Websites Should have a day / days off (consecutive)
    public static int Website_OFF_DAYS = 1;

    // WebSite shifts
    public static int MAX_SHIFTS_PER_Website = 2;



}

