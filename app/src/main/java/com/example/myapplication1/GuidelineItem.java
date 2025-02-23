package com.example.myapplication1;

public class GuidelineItem {
    private String title;
    private String description;
    private boolean isExpanded;

    public GuidelineItem(String title, String description) {
        this.title = title;
        this.description = description;
        this.isExpanded = false;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void toggleExpanded() {
        isExpanded = !isExpanded;
    }
}
