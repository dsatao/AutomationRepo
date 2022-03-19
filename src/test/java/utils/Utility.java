package utils;


/*
 * @author : Dipak.Satao
 */

public class Utility {

	public BasePage getPageObject(String pageName) {
		BasePage page = null;
		switch (pageName) {
		case "Home Page app1":
			break;
		case "Home Page app2":
			break;
		default:
			System.out.println("Invalid Case");
		}
		return page;

	}

}
