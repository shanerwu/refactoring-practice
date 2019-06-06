package io.github.shanerwu.refactoring.ch1;

public class Movie {

    public static final int CHILDRENS = 2;
    public static final int REGULAR = 0;
    public static final int NEW_RELEASE = 1;

    private String _title; // 名稱
    private int _priceCode; // 價格（代號）

    public Movie(String title, int priceCode) {
        _title = title;
        _priceCode = priceCode;
    }

    public String getTitle() {
        return _title;
    }

    public void setTitle(String _title) {
        this._title = _title;
    }

    public int getPriceCode() {
        return _priceCode;
    }

    public void setPriceCode(int _priceCode) {
        this._priceCode = _priceCode;
    }

    public double getCharge(int dayRented) {
        double result = 0;
        switch (getPriceCode()) {// 取得影片出租價格
            case Movie.REGULAR: // 普通片
                result += 2;
                if (dayRented > 2)
                    result += (dayRented - 2) * 1.5;
                break;

            case Movie.NEW_RELEASE: // 新片
                result += dayRented * 3;
                break;

            case Movie.CHILDRENS: // 兒童片
                result += 1.5;
                if (dayRented > 3)
                    result += (dayRented - 3) * 1.5;
                break;
        }
        return result;
    }

    public int getFrequentRenterPoints(int dayRented) {
        if (getPriceCode() == Movie.NEW_RELEASE && dayRented > 1) {
            return 2;
        }
        return 1;
    }

}
