package com.book.entity;

public class SearchInfo {
    private Integer id;

    private String allInfoText;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAllInfoText() {
        return allInfoText;
    }

    public void setAllInfoText(String allInfoText) {
        this.allInfoText = allInfoText == null ? null : allInfoText.trim();
    }
}