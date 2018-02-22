package com.example.ahmed.retrofit;

import java.util.List;

public class MovieReview {

    /**
     * id : 321612
     * page : 1
     * results : [{"id":"58d04679c3a3682dcd0002c6","author":"Salt-and-Limes","content":""}]
     * total_pages : 1
     * total_results : 4
     */

    private int id;
    private int page;
    private int total_pages;
    private int total_results;
    private List<ResultsEntity> results;

    public void setId(int id) {
        this.id = id;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }

    public void setResults(List<ResultsEntity> results) {
        this.results = results;
    }

    public int getId() {
        return id;
    }

    public int getPage() {
        return page;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public int getTotal_results() {
        return total_results;
    }

    public List<ResultsEntity> getResults() {
        return results;
    }

    public static class ResultsEntity {
        /**
         * id : 58d04679c3a3682dcd0002c6
         * author : Salt-and-Limes
         * content :
         */

        private String id;
        private String author;
        private String content;

        public void setId(String id) {
            this.id = id;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getId() {
            return id;
        }

        public String getAuthor() {
            return author;
        }

        public String getContent() {
            return content;
        }
    }
}