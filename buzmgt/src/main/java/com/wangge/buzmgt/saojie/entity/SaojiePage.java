package com.wangge.buzmgt.saojie.entity;

import java.util.List;


public class SaojiePage{
  
    private List<Saojie> content;
    private int totalPages; //总页数
    private int number; //每页的记录数
    private int totalSize;//总记录数
    
    public List<Saojie> getContent() {
      return content;
    }

    public void setContent(List<Saojie> content) {
      this.content = content;
    }

    public int getTotalPages() {
      return totalPages;
    }

    public void setTotalPages(int totalPages) {
      this.totalPages = totalPages;
    }

    public int getTotalSize() {
      return totalSize;
    }
    public void setTotalSize(int totalSize) {
      this.totalSize = totalSize;
    }

    public int getNumber() {
      return number;
    }

    public void setNumber(int number) {
      this.number = number;
    }
    
}
