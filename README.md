# MonsterStrike
怪物彈珠攻略網
# 專案問題紀錄
## Java Code
- [x] JDBC新增功能失效(解決)
	* Test.java編寫錯誤(沒有加到" dao.insert(ManagerVO1); "這行)  

**問題更新 2021/11/9**

## 資料庫設計/SQL(MySQL)
* 使用 Drop table IF EXISTS [table] 來達到即使沒有table也不會因為出錯而導致停止執行指令
* 設計觸發器Trigger來達到自動新增"編號"的功能
* 設計一個View表來達到搜尋資料的的方便性(view_monster)
  * 設計View主要是因為想要達到搜尋SQl指令簡單化，不然要在monster下一堆inner join語法會搞混我的閱讀性，會發瘋。
  * 如果在monster下"搜尋sql + inner join語法"的話，欄位名稱會因為"inner join"語法的關係被取代，咱不喜歡。

 **[相關SQL](https://drive.google.com/drive/folders/1RsXqbnmhLCDZmaoc7bGrLVr_6gr1U20y?usp=sharing)**  
 * 權限部分有想過設計RBAC模型，但此專題先不採用  
 **[相關資料1](https://my.oschina.net/programs/blog/1648205)**  
 **[相關資料2](https://ithelp.ithome.com.tw/questions/10190444)**   
**問題更新 2022/04/21**
