/**
 * Element.java
 *
 * Version         : v1.00
 * Creation date   : 30/Oct/2014
 * Creator         : Takahashi Toshiomi
 *
 */

/**
 * NSSMC/2014年度SE技能選手権<br/>
 * 集合内の要素を表現するクラス
 *
*/
public class Element {

	/**
	 * elementValue_<br/>
	 * 要素の値
	 */
	private String elementValue_;

	/**
	 * Element<br/>
	 * コンストラクタ<br/>
	 * 要素の値を小文字として格納する。
	 *
	 * @param elementValue
	 */
	public Element(String elementValue) {
		this.elementValue_ = elementValue.toLowerCase();

	}

	/**
	 * getElementValue<br/>
	 * 要素の値を取得する。
	 *
	 * @return elementValue_ 要素の値
	 */
	public String getElementValue() {
		return elementValue_;
	}

	

}
