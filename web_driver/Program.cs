using System;
using System.IO;
using OpenQA.Selenium;
using OpenQA.Selenium.Firefox;
using OpenQA.Selenium.Chrome;
using OpenQA.Selenium.Support.UI;
using System.Threading;
using System.Collections.Generic;
using NUnit.Framework;

namespace Login
{
    class Program
    {
        static void Main(string[] args)
        {
            CreateOperatorTest();
            Thread.Sleep(2000);
            RegisterWithdrawalTest();
        }

        static void CreateOperatorTest()
        {
            IWebDriver driver = new ChromeDriver(@"chrome\chromedriver");
            driver.Navigate().GoToUrl("http://localhost:3000/");
            WebDriverWait wait = new WebDriverWait(driver, TimeSpan.FromSeconds(10));
            IWebElement mainOperator = wait.Until(e => e.FindElement(By.Id("main_operators")));
            mainOperator.Click();
            Thread.Sleep(2000);
            IList<IWebElement> operatorsOld = driver.FindElements(By.CssSelector("[id^=operator_]"));
            Thread.Sleep(2000);
            driver.FindElement(By.Id("first_name")).SendKeys("Gui");
            Thread.Sleep(2000);
            driver.FindElement(By.Id("last_name")).SendKeys("Carvalho");
            Thread.Sleep(2000);
            driver.FindElement(By.Id("save_operator")).Click();
            Thread.Sleep(2000);
            driver.FindElement(By.CssSelector(".swal2-confirm")).Click();
            Thread.Sleep(2000);
            IList<IWebElement> operatorsNew = driver.FindElements(By.CssSelector("[id^=operator_]"));
            Assert.AreEqual(operatorsOld.Count + 1, operatorsNew.Count);
            Thread.Sleep(2000);
            driver.Quit();
        }

        static void RegisterWithdrawalTest() {
            IWebDriver driver = new ChromeDriver(@"chrome\chromedriver");
            driver.Navigate().GoToUrl("http://localhost:3000/");
            WebDriverWait wait = new WebDriverWait(driver, TimeSpan.FromSeconds(10));
            IWebElement mainDeliveries = wait.Until(e => e.FindElement(By.Id("main_deliveries")));
            mainDeliveries.Click();
            IWebElement delivery = wait.Until(e => e.FindElement(By.CssSelector(".fa-edit")));
            Thread.Sleep(2000);
            delivery.Click();
            Thread.Sleep(2000);
            driver.FindElement(By.Id("swal-input1")).SendKeys("1");
            Thread.Sleep(2000);
            driver.FindElement(By.CssSelector(".swal2-confirm")).Click();
            Thread.Sleep(2000);
            wait.Until(e => e.FindElement(By.CssSelector(".swal2-confirm"))).Click();
            Thread.Sleep(2000);
            IWebElement deliveryNew = driver.FindElement(By.CssSelector("[id^=withdrawal_date]"));
            Assert.IsNotEmpty(deliveryNew.Text);
            Thread.Sleep(2000);
            driver.Quit();
        }
    }
}
