package mobile.page;

import org.openqa.selenium.WebElement;

import mobile.page.base.AbstractPage;
import up.light.pagefactory.TestElement;
import up.light.wait.Conditions;
import up.light.wait.WaitUtil;

/**
 * 基金定投定赎
 * @category Stage-02
 */
public class PageJJDTDS extends AbstractPage {
	private TestElement oTextDT;	// 基金定投
	private TestElement oTextDS;	// 基金定赎
	private TestElement oBtnSQ;		// 申请按钮

	private TestElement oEditCode;	// 输入基金代码
	private TestElement oTextName;	// 基金名称
	private TestElement oEditNumber;// 输入定投金额
	private TestElement oBtnTime;	// 扣款周期
	private TestElement oTextMonth;	// 月
	private TestElement oTextWeek;	// 周
	private TestElement oMenuMonth;	// 列表项月
	private TestElement oMenuWeek;	// 列表项周

	private TestElement oEditBeginTime;	//开始日期
	private TestElement oEditEndTime;	//终止日期
	private TestElement oTextJEBZ;	// 巨额标志

	private TestElement oBtnTrade;	// 交易按钮
	private TestElement oMsgRisk;	// 风险提示
	private TestElement oImgLoad;	// 请等待...

	/**
	 * 切换类型
	 * @param type 基金定投、基金定赎
	 */
	public void doSwitch(String type) {
		TestElement vToClick = null;

		switch (type) {
		case "基金定投":
			vToClick = oTextDT;
			break;
		case "基金定赎":
			vToClick = oTextDS;
			break;
		default:
			throw new RuntimeException(type + " is unsupported");
		}

		WaitUtil.untilGone(driver, oImgLoad, WaitUtil.WAIT_LONG);
		vToClick.e().click();
		WaitUtil.untilGone(driver, oImgLoad, WaitUtil.WAIT_LONG);
	}

	/**
	 * 点击添加按钮
	 */
	public void doAdd() {
		oBtnSQ.e().click();
	}

	/**
	 * 输入基金代码
	 * @param code 基金代码
	 * @return 基金名称
	 */
	public String doInputCode(String code) {
		WaitUtil.sleep(1000);
		// 等待并点击代码编辑框
		WebElement e = WaitUtil.waitFor(driver, oEditCode, WaitUtil.WAIT_LONG);
		// 输入代码并等待数据加载
		getKeyboard().doInput(e, code);
		return WaitUtil.waitForText(driver, oTextName.e(), WaitUtil.WAIT_LONG, null, Conditions.NOTBLANK);
	}

	/**
	 * 输入交易数量
	 * @param number
	 */
	public void doInputNumber(String number) {
		WebElement e = oEditNumber.e();

		if(!getValue(e).equals(number)) {
			driver.executeScript("arguments[0].value = ''", e);
			getKeyboard().doInput(e, number);
		}
	}

	/**
	 * 选择扣款周期、赎回周期
	 * @param type 月、周
	 * @param time 日、周几
	 */
	public void doChooseTime(String type, String time) {
		oBtnTime.e().click();
		WaitUtil.sleep(1000);
		
		if(time.endsWith("号")) {
			time = time.substring(0, time.length() - 1);
		}
		

		switch (type) {
		case "每月":
			doChooseMonth(time);
			break;
		case "每周":
			doChooseWeek(time);
			break;
		default:
			throw new RuntimeException(type + " is unsupported");
		}
	}

	private void doChooseMonth(String time) {
		oTextMonth.e().click();
		int day = Integer.valueOf(time) - 1;
		oMenuMonth.es().get(day).click();
	}

	private void doChooseWeek(String time) {
		oTextWeek.e().click();
		Week w = Week.fromString(time);
		oMenuWeek.es().get(w.getIndex()).click();
	}

	/**
	 * 选择巨额标志
	 * @param jebz 取消、顺延
	 */
	public void doChooseJEBZ(String jebz) {
		oTextJEBZ.e().click();
		WaitUtil.sleep(500);
		String xpath = "//li[text()='" + jebz + "']";
		driver.findElementByXPath(xpath).click();
	}

	/**
	 * 获取开始时间
	 * @return
	 */
	public String doGetBeginTime() {
		return getValue(oEditBeginTime.e());
	}

	/**
	 * 获取结束时间
	 * @return
	 */
	public String doGetEndTime() {
		return getValue(oEditEndTime.e());
	}

	/**
	 * 点击交易按钮
	 */
	public void doTrade() {
		WaitUtil.sleep(1000);
		oBtnTrade.e().click();
	}

	/**
	 * 处理风险提示
	 */
	public void doHandleRiskAlert() {
		if (WaitUtil.exists(driver, oMsgRisk, 1)) {
			getAlert().doAccept();
		}
	}

	private static enum Week {
		MONDAY("星期一", 0), TUESDAY("星期二", 1), WEDNESDAY("星期三", 2), THURSDAY("星期四", 3), FRIDAY("星期五", 4);

		private String name;
		private int index;

		Week(String name, int index) {
			this.name = name;
			this.index = index;
		}

		public int getIndex() {
			return this.index;
		}

		public static Week fromString(String str) {
			for (Week w : Week.values()) {
				if (w.name.equals(str)) {
					return w;
				}
			}

			throw new RuntimeException(str + " is unsupported");
		}
	}
}
