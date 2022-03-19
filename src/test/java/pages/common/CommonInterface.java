package pages.common;

import org.openqa.selenium.By;

/*
 * @author : Dipak.Satao
 */

public interface CommonInterface {

	default By getLocator(String ele) {
		return null;
	}

	default public void userLogoutOption() {

	}

	default public void clickOnElement(String elementName, String elementText) {

	}

	default public void verifyIsDispalyed(String elementText) {

	}

	default public void loginInApp(String email) {

	}

	default public void loginPageErrors(String errorMessage) {

	}

	default public void invalidLoginErrors(String errorMessage) {

	}

	default public void logoutFromApp() {

	}

	default public void validatePage() {

	}

	default public void navigateToTab(String tabName) {

	}

	default public void CheckSubHeader(String header) {

	}

	default public void createUser(String persona) {

	}

	default public void updateUser(String persona) {

	}

	default public void deleteUser(String persona) {

	}

	default public void validateUserCreatedOrNot() {

	}

	default public void validateUserDeletedOrNot() {

	}

	void clickOnDropdownAndSelectValue(String dropdownName, String dropdownValue);

	default public void validateSensePageDetails(String validationPoint,String value) {

	}

	default public void enterText(String fieldName, String value) {

	}

}
