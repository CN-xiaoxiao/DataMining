package com.xiaoxiao.pageRank;

public class PageRankStart {
    private PageRank pageRank;

    public void pageRankStart() {
        double[][] result = new double[0][];
        try {
            result = this.pageRank.pageRankStart();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        for (int i = 0; i < result.length; i++) {
            System.out.println(result[i][0]);
        }
    }

    public PageRankStart(PageRank pageRank) {
        this.pageRank = pageRank;
    }
}
