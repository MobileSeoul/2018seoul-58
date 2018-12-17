package com.walk.angel.angelwalk.Data;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class SightList {

    @SerializedName("stat")
    private String result;

    @SerializedName("msg")
    private String message;

    @SerializedName("data")
    private ArrayList<SightsData> arrayListOfSightData = new ArrayList<>();

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<SightsData> getArrayListOfSightData() {
        return arrayListOfSightData;
    }

    public void setArrayListOfSightData() {
        this.arrayListOfSightData.add(new SightsData(0,
                37.583344, 126.803692,
                "개화산 무장애 숲길", "서울시 강서구",
                "https://disability.seoul.go.kr/facility/facility_view2.jsp?returnURL=%2Ffacility%2Ffacility_list.jsp&seqNo=100&bbsType=1&bbsOrder=1",
                "https://disability.seoul.go.kr/etc/datafilesave.jsp?fname=288_brier21-thum10.jpg&fpath=facility&fview=brier21-thum1.jpg"));
        this.arrayListOfSightData.add(new SightsData(1,
                37.579855, 126.976987,
                "경복궁"           , "서울시 종로구",
                "https://disability.seoul.go.kr/facility/facility_view2.jsp?returnURL=%2Ffacility%2Ffacility_list.jsp&seqNo=95&bbsType=1&bbsOrder=1",
                "https://disability.seoul.go.kr/etc/datafilesave.jsp?fname=288_brier16-thum10.jpg&fpath=facility&fview=brier16-thum1.jpg"));
        this.arrayListOfSightData.add(new SightsData(2,
                37.556518, 127.157750,"고덕산 무장애 자락길","서울시 강동구",
                "https://disability.seoul.go.kr/facility/facility_view2.jsp?returnURL=%2Ffacility%2Ffacility_list.jsp&seqNo=102&bbsType=1&bbsOrder=1",
                "https://disability.seoul.go.kr/etc/datafilesave.jsp?fname=288_brier23-thum10.jpg&fpath=facility&fview=brier23-thum1.jpg"));
        this.arrayListOfSightData.add(new SightsData(3,
                37.455447, 126.958818,"관악산 무장애 숲길", "서울시 관악구",
                "https://disability.seoul.go.kr/facility/facility_view2.jsp?returnURL=%2Ffacility%2Ffacility_list.jsp&seqNo=91&bbsType=1&bbsOrder=1",
                "https://disability.seoul.go.kr/etc/datafilesave.jsp?fname=288_brier12-thum10.JPG&fpath=facility&fview=brier12-thum1.JPG"));
        this.arrayListOfSightData.add(new SightsData(4,
                37.583450, 126.978992,"국립민속박물관",      "서울시 종로구",
                "https://disability.seoul.go.kr/facility/facility_view2.jsp?returnURL=%2Ffacility%2Ffacility_list.jsp&seqNo=96&bbsType=1&bbsOrder=1",
                "https://disability.seoul.go.kr/etc/datafilesave.jsp?fname=288_brier17-thum10.jpg&fpath=facility&fview=brier17-thum1.jpg"));
        this.arrayListOfSightData.add(new SightsData(5,
                37.526394, 126.980204,"국립중앙박물관",      "서울시 용산구",
                "https://disability.seoul.go.kr/facility/facility_view2.jsp?returnURL=%2Ffacility%2Ffacility_list.jsp&seqNo=63&bbsType=1&bbsOrder=1",
                "https://disability.seoul.go.kr/etc/datafilesave.jsp?fname=288_brier1-thum10.jpg&fpath=facility&fview=brier1-thum1.jpg"));
        this.arrayListOfSightData.add(new SightsData(6,
                37.553005, 126.986502,"남산 순환 나들길",    "서울시 중구",
                "https://disability.seoul.go.kr/facility/facility_view2.jsp?returnURL=%2Ffacility%2Ffacility_list.jsp&seqNo=93&bbsType=1&bbsOrder=1",
                "https://disability.seoul.go.kr/etc/datafilesave.jsp?fname=288_brier14-thum10.jpg&fpath=facility&fview=brier14-thum1.jpg"));
        this.arrayListOfSightData.add(new SightsData(7,
                37.559570, 126.994434,"남산골한옥마을",      "서울시 중구",
                "https://disability.seoul.go.kr/facility/facility_view2.jsp?returnURL=%2Ffacility%2Ffacility_list.jsp&seqNo=84&bbsType=1&bbsOrder=1",
                "https://disability.seoul.go.kr/etc/datafilesave.jsp?fname=288_brier5-thum10.JPG&fpath=facility&fview=brier5-thum1.JPG"));
        this.arrayListOfSightData.add(new SightsData(8,
                37.566060, 126.975071,"덕수궁",             "서울시 중구",
                "https://disability.seoul.go.kr/facility/facility_view2.jsp?returnURL=%2Ffacility%2Ffacility_list.jsp&seqNo=90&bbsType=1&bbsOrder=1",
                "https://disability.seoul.go.kr/etc/datafilesave.jsp?fname=288_brier11-thum10.JPG&fpath=facility&fview=brier11-thum1.JPG"));;
        this.arrayListOfSightData.add(new SightsData(9,
                37.686462, 127.037300,"도봉옛길",           "서울시 도봉구",
                "https://disability.seoul.go.kr/facility/facility_view2.jsp?returnURL=%2Ffacility%2Ffacility_list.jsp&seqNo=110&bbsType=1&bbsOrder=1",
                "https://disability.seoul.go.kr/etc/datafilesave.jsp?fname=288_brier31-thum10.JPG&fpath=facility&fview=brier31-thum1.JPG"));

        this.arrayListOfSightData.add(new SightsData(10,
                37.566744, 127.009496,"동대문 디자인 플라자(DDP)", "서울시 동대문구",
                "https://disability.seoul.go.kr/facility/facility_view2.jsp?returnURL=%2Ffacility%2Ffacility_list.jsp&seqNo=161&bbsType=1&bbsOrder=1",
                "https://disability.seoul.go.kr/etc/datafilesave.jsp?fname=288_brier35-thum10.JPG&fpath=facility&fview=brier35-thum1.JPG"));
        this.arrayListOfSightData.add(new SightsData(11,
                37.522554, 127.120843, "몽촌토성",                "서울시 송파구",
                "https://disability.seoul.go.kr/facility/facility_view2.jsp?returnURL=%2Ffacility%2Ffacility_list.jsp&seqNo=162&bbsType=1&bbsOrder=1",
                "https://disability.seoul.go.kr/etc/datafilesave.jsp?fname=288_brier36-thum10.jpg&fpath=facility&fview=brier36-thum1.jpg"));
        this.arrayListOfSightData.add(new SightsData(12,
                37.582441, 126.983653, "북촌한옥마을",             "서울시 종로구",
                "https://disability.seoul.go.kr/facility/facility_view2.jsp?returnURL=%2Ffacility%2Ffacility_list.jsp&seqNo=104&bbsType=1&bbsOrder=1",
                "https://disability.seoul.go.kr/etc/datafilesave.jsp?fname=288_brier25-thum10.jpg&fpath=facility&fview=brier25-thum1.jpg"));
        this.arrayListOfSightData.add(new SightsData(13,
                37.494788, 126.961573, "서달산 자락길",       "서울시 동작구",
                "https://disability.seoul.go.kr/facility/facility_view2.jsp?returnURL=%2Ffacility%2Ffacility_list.jsp&seqNo=92&bbsType=1&bbsOrder=1",
                "https://disability.seoul.go.kr/etc/datafilesave.jsp?fname=288_brier13-thum10.jpg&fpath=facility&fview=brier13-thum1.jpg"));
        this.arrayListOfSightData.add(new SightsData(14,
                37.576708, 126.937830, "서대문 자연사 박물관", "서울시 서대문구",
                "https://disability.seoul.go.kr/facility/facility_view2.jsp?returnURL=%2Ffacility%2Ffacility_list.jsp&seqNo=108&bbsType=1&bbsOrder=1",
                "https://disability.seoul.go.kr/etc/datafilesave.jsp?fname=288_brier29-thum10.JPG&fpath=facility&fview=brier29-thum1.JPG"));
        this.arrayListOfSightData.add(new SightsData(15,
                37.570518, 126.970576, "서울 역사박물관",    "서울시 종로구",
                "https://disability.seoul.go.kr/facility/facility_view2.jsp?returnURL=%2Ffacility%2Ffacility_list.jsp&seqNo=87&bbsType=1&bbsOrder=1",
                "https://disability.seoul.go.kr/etc/datafilesave.jsp?fname=288_brier8-thum10.JPG&fpath=facility&fview=brier8-thum1.JPG"));
        this.arrayListOfSightData.add(new SightsData(16,
                37.566289, 126.977879, "서울도서관",        "서울시 중구",
                "https://disability.seoul.go.kr/facility/facility_view2.jsp?returnURL=%2Ffacility%2Ffacility_list.jsp&seqNo=88&bbsType=1&bbsOrder=1",
                "https://disability.seoul.go.kr/etc/datafilesave.jsp?fname=288_brier9-thum10.JPG&fpath=facility&fview=brier9-thum1.JPG"));
        this.arrayListOfSightData.add(new SightsData(17,
                37.564048, 126.973745, "서울시립미술관",     "서울시 중구",
                "https://disability.seoul.go.kr/facility/facility_view2.jsp?returnURL=%2Ffacility%2Ffacility_list.jsp&seqNo=89&bbsType=1&bbsOrder=1",
                "https://disability.seoul.go.kr/etc/datafilesave.jsp?fname=288_brier10-thum10.JPG&fpath=facility&fview=brier10-thum1.JPG"));
        this.arrayListOfSightData.add(new SightsData(18,
                37.538560, 127.043009, "수도박물관",        "서울시 성동구",
                "https://disability.seoul.go.kr/facility/facility_view2.jsp?returnURL=%2Ffacility%2Ffacility_list.jsp&seqNo=163&bbsType=1&bbsOrder=1",
                "https://disability.seoul.go.kr/etc/datafilesave.jsp?fname=288_brier38-thum10.jpg&fpath=facility&fview=brier38-thum1.jpg"));
        this.arrayListOfSightData.add(new SightsData(19,
                37.551501, 127.100779, "아차산 무장애 숲길", "서울시 광진구",
                "https://disability.seoul.go.kr/facility/facility_view2.jsp?returnURL=%2Ffacility%2Ffacility_list.jsp&seqNo=103&bbsType=1&bbsOrder=1",
                "https://disability.seoul.go.kr/etc/datafilesave.jsp?fname=288_brier24-thum10.JPG&fpath=facility&fview=brier24-thum1.JPG"));

        this.arrayListOfSightData.add(new SightsData(20,
                37.580439, 126.938522,"안산 자락길",      "서울시 서대문구",
                "https://disability.seoul.go.kr/facility/facility_view2.jsp?returnURL=%2Ffacility%2Ffacility_list.jsp&seqNo=109&bbsType=1&bbsOrder=1",
                "https://disability.seoul.go.kr/etc/datafilesave.jsp?fname=288_brier30-thum10.JPG&fpath=facility&fview=brier30-thum1.JPG"));
        this.arrayListOfSightData.add(new SightsData(21,
                37.560564, 127.130339, "암사동 선사유적지", "서울시 강동구",
                "https://disability.seoul.go.kr/facility/facility_view2.jsp?returnURL=%2Ffacility%2Ffacility_list.jsp&seqNo=101&bbsType=1&bbsOrder=1",
                "https://disability.seoul.go.kr/etc/datafilesave.jsp?fname=288_brier22-thum10.jpg&fpath=facility&fview=brier22-thum1.jpg"));
        this.arrayListOfSightData.add(new SightsData(22,
                37.548403, 127.080828, "어린이 대공원",     "서울시 광진구",
                "https://disability.seoul.go.kr/facility/facility_view2.jsp?returnURL=%2Ffacility%2Ffacility_list.jsp&seqNo=85&bbsType=1&bbsOrder=1",
                "https://disability.seoul.go.kr/etc/datafilesave.jsp?fname=288_brier6-thum10.jpg&fpath=facility&fview=brier6-thum1.jpg"));
        this.arrayListOfSightData.add(new SightsData(23,
                37.562575, 126.895083,"월드컵 평화의 공원", "서울시 마포구",
                "https://disability.seoul.go.kr/facility/facility_view2.jsp?returnURL=%2Ffacility%2Ffacility_list.jsp&seqNo=107&bbsType=1&bbsOrder=1",
                "https://disability.seoul.go.kr/etc/datafilesave.jsp?fname=288_brier28-thum10.JPG&fpath=facility&fview=brier28-thum1.JPG"));
        this.arrayListOfSightData.add(new SightsData(24,
                37.578857, 126.959509, "인왕산 자락길",     "서울시 종로구",
                "https://disability.seoul.go.kr/facility/facility_view2.jsp?returnURL=%2Ffacility%2Ffacility_list.jsp&seqNo=105&bbsType=1&bbsOrder=1",
                "https://disability.seoul.go.kr/etc/datafilesave.jsp?fname=288_brier26-thum10.JPG&fpath=facility&fview=brier26-thum1.JPG"));
        this.arrayListOfSightData.add(new SightsData(25,
                37.536844, 126.977118, "전쟁기념관",       "서울시 용산구",
                "https://disability.seoul.go.kr/facility/facility_view2.jsp?returnURL=%2Ffacility%2Ffacility_list.jsp&seqNo=81&bbsType=1&bbsOrder=1",
                "https://disability.seoul.go.kr/etc/datafilesave.jsp?fname=288_brier2-thum10.jpg&fpath=facility&fview=brier2-thum1.jpg"));
        this.arrayListOfSightData.add(new SightsData(26,
                37.578950, 126.994859,"창경궁",           "서울시 종로구",
                "https://disability.seoul.go.kr/facility/facility_view2.jsp?returnURL=%2Ffacility%2Ffacility_list.jsp&seqNo=86&bbsType=1&bbsOrder=1",
                "https://disability.seoul.go.kr/etc/datafilesave.jsp?fname=288_brier7-thum10.JPG&fpath=facility&fview=brier7-thum1.JPG"));
        this.arrayListOfSightData.add(new SightsData(27,
                37.579487, 126.991035, "창덕궁",           "서울시 종로구",
                "https://disability.seoul.go.kr/facility/facility_view2.jsp?returnURL=%2Ffacility%2Ffacility_list.jsp&seqNo=83&bbsType=1&bbsOrder=1",
                "https://disability.seoul.go.kr/etc/datafilesave.jsp?fname=288_brier4-thum10.JPG&fpath=facility&fview=brier4-thum1.JPG"));
        this.arrayListOfSightData.add(new SightsData(28,
                37.452639, 126.925552, "호암 늘솔길",       "서울시 금천구",
                "https://disability.seoul.go.kr/facility/facility_view2.jsp?returnURL=%2Ffacility%2Ffacility_list.jsp&seqNo=99&bbsType=1&bbsOrder=1",
                "https://disability.seoul.go.kr/etc/datafilesave.jsp?fname=288_brier20-thum10.jpg&fpath=facility&fview=brier20-thum1.jpg"));
    }
}
