package com.example.tapassubject;

public class Const {
    static public String BASEURL = "https://f30ab4e8-ee15-415c-98b8-d2004c5d2b9e.mock.pstmn.io";
    static public String getBrowseURL(int comics , int page)
    {
        return BASEURL + "/browse/fresh?series_type="+comics+"&page="+page;
    }
    static public String getSeriesURL(int series_id)
    {
        return BASEURL + "/series/"+series_id;
    }
    static public String getEpisodeURL(int series_id)
    {
        return getSeriesURL(series_id) + "/episodes";
    }

    static public String MSG_FAIL_INIT = "초기화에 실패했습니다";
}
