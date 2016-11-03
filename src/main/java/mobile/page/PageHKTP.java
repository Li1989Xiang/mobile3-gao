package mobile.page;

import org.openqa.selenium.WebElement;

import mobile.page.base.ElementOf;
import mobile.page.base.ElementOfs;
import mobile.page.base.PageTradeWithSelect;
import up.light.pagefactory.TestElement;

/**
 * 港股通投票界面
 * @category Stage-02
 * @since 2016-08-15
 */
public class PageHKTP extends PageTradeWithSelect {
	@ElementOf(ElementOfs.CODE)
	private TestElement oEditCode;

	@ElementOf(ElementOfs.NAME)
	private TestElement oTextName;

	private TestElement oEditGGBH;
	private TestElement oEditYABH;
	private TestElement oEditZCSL;
	private TestElement oEditFDSL;
	private TestElement oEditQQSL;

	@ElementOf(ElementOfs.BUTTON_OK)
	private TestElement oBtnSubmit;

	/**
	 * 输入公告编号和议案编号
	 */
	public void doInputNos(String ggbh, String yabh) {
		// getKeyboard().doInput(oEditGGBH, ggbh);
		WebElement e = oEditGGBH.e();
		driver.executeScript("arguments[0].removeAttribute('readonly');", e);
		e.sendKeys(ggbh);
		getKeyboard().doInput(oEditYABH.e(), yabh);
	}

	/**
	 * 输入数量
	 */
	public void doInputNumbers(String zcsl, String fdsl, String qqsl) {
		getKeyboard().doInput(oEditZCSL.e(), zcsl);
		getKeyboard().doInput(oEditFDSL.e(), fdsl);
		getKeyboard().doInput(oEditQQSL.e(), qqsl);
	}

	@Override
	public void doInputNumber(String num) {
		throw new RuntimeException("this method is not available, do you mean to call doInputNumbers(String, String, String)");
	}
}
