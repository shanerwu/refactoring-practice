## 重構，第一個案例
### 起點
> 如果你發現自己需要為程式添加一個特性，而程式碼結構使你無法很方便地那麼做，那就先重構那個程式，使特性的添加比較容易進行，然後再添加特性。

* 用戶希望以 HTML 格式列印報表，但無法重複使用 (reuse) Customer 的 `statement()`，需要編寫新的 `htmlStatement()`，且大量重複 `statement()` 中做的事情。
* 若用戶希望可以改變影片的分類規則，而這會影響顧客消費和常客累積的計算方式。為了應付這些改變，除了修改 `statement()`，還要一併修改 `htmlStatement()`。
* 隨著規則變得越來越複雜，犯錯的機會也會越來越多。

### 重構的第一步
> 建立可靠的測試機制，而這些測試必須要有自我檢驗（self-checking）的能力。

* 進行重構時，需要倚賴測試。
* `Customer.statement()` 回傳結果是字串，所以可以這樣做測試：
    1. 建立測試顧客。
    2. 讓每個顧客各租幾部不同的影片。
    3. 產生報表字串。
    4. 用新的字串和已經檢查過的參考字串做比較。
* 測試必須讓測試有能力自我檢驗。
* 花點時間建立一個優良的測試機制是非常值得的。

### 分解並重組 statement()

找出程式碼的邏輯泥團（Logical Clump），並運用 `Extract Method`。

#### 金額計算
    
1. 使用 `Extract Method`，將金額計算的 switch 敘述提煉出來成為一個獨立涵式。
    
2. 找出函式內的區域變數和參數：

    `each`, `thisAmount`（前者不會被修改，後者會被修改）。
    * 不會被修改的變數，可以被當成參數傳入新的函式。
    * 會被修改的變數要格外小心，若只有一個變數會被修改，就把它當作返回值。

   然而在修改後執行測試，發現測試失敗，因為 `thisAmount` 的返回值變成了 `int`，而不是原先的 `double`，但由於修改的幅度很小，所以很快就發現問題。
   > 以微小的步伐修改程式，如果你犯下錯誤，便很容易發現它。
   
3. 修改變數名稱：
   
    ```
    isAmount -> result
    each -> aRental
    ```
    
    * 變數名稱是程式碼清晰的關鍵。
    * 選擇能夠表現意圖的名稱。
   
4. 使用 Move Method，將程式碼搬移到合適的 Class
    
    `amountFor()` 使用了來自 `Rental` 的資訊，卻沒有使用 `Customer` 的資訊。
    應該將 `amountFor()` 搬到 `Rental`，並調整程式碼使之適應新家。
    
5. 找出原本的引用（reference）點，並修改它們，讓它們改用新涵式：
    
    在這個例子中，只有一個地方使用到原本在 `Customer` 中的 `amountFor()`，然而在一般情況下，可能要將運用該涵式的所有 Classes 中搜尋一遍。
    
    接下來就是要 `去除舊涵式`，讓它呼叫新涵式：
    
    ```
    thisAmount = amountFor(each);
                ↓
    thisAmount = each.getCharge();
    ```
    
    而對於舊涵式的去留，Martin Fowler 表示若舊涵式是 `public` 涵式，而又不想修改到其他 Class 時，他會選擇保留舊涵式。
    
6. 使用 Replace Temp with Query 去除多餘的變數：

    `thisAmount` 在接受 `each.getCharge()` 的結果後就不再做任何改變，應該盡量除去這種暫時性變數。
    
    暫時變數往往會形成問題，導致大量參數被傳來傳去，容易失去對它們的追蹤。
    當然這麼做會付出效率上的代價，但這很容易在 `Rental` 中被最佳化。
    
    應該先讓程式碼有合理的組織和管理，在最佳化時就會有很好的效果。
    
#### 常客積點計算

1. 使用 `Extract Method`，將常客積點計算提煉出來成為一個獨立涵式。

2. 找出函式內的區域變數：

    `each`, `frequentRenterPoints`
    
3. 使用 Replace Temp with Query 去除多餘的變數：
    
    在 `Customer` 中使用 `Query Method` 取代 `totalAmount` 和 `frequentRenterPoints` 這兩個暫時變數。