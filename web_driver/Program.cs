using System;
using System.IO;
using OpenQA.Selenium;
using OpenQA.Selenium.Firefox;
using OpenQA.Selenium.Chrome;
using OpenQA.Selenium.Support.UI;
using System.Threading;

namespace Login
{
    class Program
    {
        const string BROWSER = "CHROME";
        static void Main(string[] args)
        {
            IWebDriver driver = new ChromeDriver(@"chrome\chromedriver");

            driver.Navigate().GoToUrl("http://localhost:3000/");
            WebDriverWait wait = new WebDriverWait(driver, TimeSpan.FromSeconds(10));
            IWebElement firstResult = wait.Until(e => e.FindElement(By.Id("main_operators")));
            firstResult.Click();
            var operatorsOld = driver.FindElements(By.CssSelector("[id^=operator_]"));
            Thread.Sleep(2000);
            driver.FindElement(By.Id("first_name")).SendKeys("Gui");
            Thread.Sleep(2000);
            driver.FindElement(By.Id("last_name")).SendKeys("Carvalho");
            Thread.Sleep(2000);
            driver.FindElement(By.Id("save_operator")).Click();
            Thread.Sleep(2000);
            // swal2-title
            // Thread.Sleep(1000);
            driver.FindElement(By.CssSelector(".swal2-confirm")).Click();
            // driver.FindElement(By.Id("swal2-confirm")).Click();
            Thread.Sleep(2000);
            var operatorsNew = driver.FindElements(By.CssSelector("[id^=operator_]"));
            

            // driver.Quit();
        }
    }
}
