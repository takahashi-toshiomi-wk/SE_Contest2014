/**
 * DataSet.java
 *
 * Version         : v1.00
 * Creation date   : 30/Oct/2014
 * Creator         : Takahashi Toshiomi
 *
 */

/**
 * NSSMC/2014年度SE技能選手権<br/>
 * 集合を表現するクラス
 *
 */
public class DataSet {

	/**
	 * setName_<br/>
	 * 集合の名称
	 *
	 */
	private String setName_;

	/**
	 * elementList
	 * 集合内の要素を格納する配列
	 *
	 */
	private Element[] elementList_ = null;
	
	/**
	 * sortedFlag
	 * ソート済かどうかを示す
	 *
	 */
	private boolean sortedFlag_ = false;

	/**
	 * DataSet<br/>
	 * コンストラクタ
	 *
	 *
	 * @param setName
	 */
	public DataSet(String setName) {
		this.setName_ = setName;

	}

	/**
	 * getSetName<br/>
	 * 集合名を取得する
	 *
	 * @return setName_ 集合名
	 */
	public String getSetName() {
		return setName_;
	}
	
	/**
	 * getLastCharOfName<br/>
	 * 集合名の最終文字を取得する。
	 *
	 * @return lastChar 集合名の最終文字
	 */
	public String getLastCharOfName() {
		String lastChar = setName_.substring(setName_.length() - 1);
		return lastChar;
	}

	/**
	 * getElementList<br/>
	 * 要素配列を取得する
	 *
	 * @return elementList 要素配列
	 */
	public Element[] getElementList() {
		return elementList_;
	}

	/**
	 * replaceElementList<br/>
	 * 要素の配列を設定する。存在する場合は置き換える。
	 *
	 * @param elementList 要素配列
	 */
	public void replaceElementList(Element[] elementList) {
		this.elementList_ = elementList;

	}

	/**
	 * findElement<br/>
	 * 要素が集合内にあるか検索し、存在する場合は配列内の当該要素をNULLクリアする。<br/>
	 * なお、存在した時点で検索を打ち切る。<br/>
	 * 結果として、存在したかどうかを呼び出し元へ通知する。<br/>
	 *
	 * @param element
	 * @return 要素の存在有無 true:有 false:無
	 */
	public boolean findElement(Element argElement) {
		//入力チェック
		if (argElement == null) {
			return false;
		}
		//集合の存在チェック
		if (elementList_ == null) {
			return false;
		}
		//要素間の比較値
		int compareValue = 0;
		//集合をループさせ、引数の要素と比較を行う。
		for (int idx = 0; idx < elementList_.length; idx++) {
			Element thisElement = elementList_[idx];			
			//要素がnullの場合は次の要素へ
			if (thisElement == null) {
				continue;
			}
			//引数要素と自要素の大小比較を行う。
			compareValue = argElement.getElementValue()
					.compareTo(thisElement.getElementValue());
			if (compareValue == 0) {
				//一致する場合、配列より値をnullクリアする
				elementList_[idx] = null;
				return true;
			} else if (compareValue > 0) {
				//自要素の方が大きい場合、検索を打ち切る
				return false;
			} else {
				//自要素の方が小さい場合、ループを続行する。
				continue;
			}
		}
		//ループが終了した場合、一致なし
		return false;
	}

	/**
	 * qSort<br/>
	 * 要素の配列が検索しやすいよう、クイックソートにて昇順に並び替える。
	 *
	 */
	public void qSort() {
		//集合の存在チェック
		if (elementList_ == null) {
			return;
		} 
		//ソート済の場合、再度ソートは実施しない
		if (sortedFlag_) {
			return;
		}
		//ソート実施
		qSortInner (elementList_, 0, elementList_.length);
		
		//ソート済フラグをONにする
		sortedFlag_ = true;

	}
	
	/**
	 * qSortInner<br/>
	 * 
	 */
	private void qSortInner (Element[] list, int start, int end) {
		//開始値と終了値が同じ場合、終了
		if (start == end) {
			return;
		}
		//境界値の取得
		String border = getBorderVal(list, start, end);
		//分割・分割単位内ソートを行う
		int dividedIdx = divideListByBorder(list, start, end, border);
		
		//分割単位毎に再帰的にソートを行う
		qSortInner(list, start, dividedIdx -1);
		qSortInner(list, dividedIdx, end);
	}
	/**
	 * getBorderVal<br/>
	 * 分割に利用する境界値を、先頭から任意の2値を選び大きい方とする。<br/>
	 * 範囲内の要素値が全て同じ場合、nullを返す。
	 */
	private String getBorderVal (Element[] list, int start, int end) {
		//検索範囲がない場合はnullを返す
		if (start >= end) {
			return null;
		}
		
		//候補1は先頭要素
		String candidate1 = list[start].getElementValue();
		//候補2は次要素から候補1つ目より大きい値を探す
		String candidate2 = null;
		int compareResult = 0;
		for (int idx = start + 1; idx < end; idx++) {
			candidate2 = list[idx].getElementValue();
			compareResult = candidate1.compareTo(candidate2);
			if (compareResult > 0) {
				//候補1 > 候補2
				return candidate1;
			} else if (compareResult < 0) {
				//候補1 < 候補2
				return candidate2;
			}			
		}
		//全ての要素が候補1と同じ場合
		return null;
	}
	/**
	 * divideListByBorder
	 * 境界値に基づき、指定範囲の配列を大小の部分へ分割する
	 */
	private int divideListByBorder (Element[] list, int start, int end, String border) {
		//検索用インデックス
		int idxFromStart = start;
		int idxFromEnd = end;
		//値交換用ワーク領域
		String work;
		
		//指定範囲内を検索し終わるまでループ
		while (idxFromStart <= idxFromEnd) {
			//境界以上のデータを先頭から検索
			while (list[idxFromStart].getElementValue().compareTo(border) < 0
					&& idxFromStart <= end ) {
				idxFromStart++;
			}
			//境界以下のデータを末尾より検索
			while (list[idxFromEnd].getElementValue().compareTo(border) > 0
					&& idxFromEnd >= start ) {
				idxFromEnd--;
			}
			//発見したデータを交換
			work = list[idxFromStart].getElementValue();
			list[idxFromStart] = new Element(list[idxFromEnd].getElementValue());
			list[idxFromEnd] = new Element(work);
			//次の値を探す
			idxFromStart++;
			idxFromEnd--;
		}
		
		return idxFromStart;
	}

}
