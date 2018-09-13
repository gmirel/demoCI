package com.mobile.iehs;


import com.mobile.iehs.pages.BaseClass;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;

public class TestAmazon extends BaseClass {

    By searchBar = By.id("twotabsearchtextbox");
    By searchBtn = By.cssSelector("div.nav-search-submit.nav-sprite > input.nav-input");
    By amazonLogo = By.id("nav-logo");
    By cartLogo = By.cssSelector("#nav-cart");
    By h1AreaText = By.cssSelector("h1");

    @Test
    public void testingBasket(){

        System.out.println("----------> RUNNING CHECK CART TEST <----------");

        findElementAndSendKey(searchBar, "Centric");
        System.out.println("Typing in `Centric`");

        findElementAndClick(searchBtn);
        System.out.println("Clicking on search button");

        findElementAndClick(amazonLogo);
        System.out.println("Clicking on Amazon logo");

        findElementAndClick(cartLogo);
        System.out.println("Clicking on cart logo");

        String h1Text = getTextFromArea(h1AreaText);
        Assert.assertTrue(h1Text.contains("empty"));
        System.out.println("Asserting text");

        System.out.println("----------> END CHECK CART TEST <----------");


    }
}
