package io.github.shanerwu.refactoring.ch1;

import java.util.Enumeration;
import java.util.Vector;

public class Customer {

    private String _name; // 姓名
    private Vector _rentals = new Vector(); // 租借紀錄

    public Customer(String name) {
        _name = name;
    }

    public void addRental(Rental arg) {
        _rentals.addElement(arg);
    }

    public String getName() {
        return _name;
    }

    public String statement() {
        double totalAmount = 0; // 消費總金額
        int frequentRenterPoints = 0; // 常客積點
        Enumeration rentals = _rentals.elements();
        String result = "Rental Record for " + getName() + "\n";

        while (rentals.hasMoreElements()) {
            double thisAmount = 0;
            Rental each = (Rental) rentals.nextElement(); // 取得一筆租借紀錄

            // 計算一筆租片費用
            thisAmount = amountFor(each);

            // 累加常客積點
            frequentRenterPoints++;

            // 若租借新片，且租期為兩天以上，再累加一次常客積點
            if (each.getMovie().getPriceCode() == Movie.NEW_RELEASE && each.getDaysRented() > 1)
                frequentRenterPoints++;

            // 顯示此筆租借資料
            result += "\t" + each.getMovie().getTitle() + "\t" +
                    String.valueOf(thisAmount) + "\n";
            totalAmount += thisAmount;
        }

        // 結尾列印
        result += "Amount owed is " + String.valueOf(totalAmount) + "\n";
        result += "You earned " + String.valueOf(frequentRenterPoints) +
                " frequent renter points";
        return result;
    }

    private int amountFor(Rental each) {
        int thisAmount = 0;
        switch (each.getMovie().getPriceCode()) {// 取得影片出租價格
            case Movie.REGULAR: // 普通片
                thisAmount += 2;
                if (each.getDaysRented() > 2)
                    thisAmount += (each.getDaysRented() - 2) * 1.5;
                break;

            case Movie.NEW_RELEASE: // 新片
                thisAmount += each.getDaysRented() * 3;
                break;

            case Movie.CHILDRENS: // 兒童片
                thisAmount += 1.5;
                if (each.getDaysRented() > 3)
                    thisAmount += (each.getDaysRented() - 3) * 1.5;
                break;
        }
        return thisAmount;
    }

}
