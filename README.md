<h1 align="center">
시장서울
</h1>

- [OVERVIEW](https://github.com/meanu/SeoulSijang#overview)
- [Features](https://github.com/meanu/SeoulSijang#features)
- [FIXED](https://github.com/meanu/SeoulSijang#API)
  - [JSON](https://github.com/meanu/SeoulSijang#JSON)
  - [LIBRARY](https://github.com/meanu/SeoulSijang#LIBRARY)
  - [REVIEW](https://github.com/meanu/SeoulSijang#REVIEW)



# **OVERVIEW**

[원본](https://github.com/meanu/2018seoul-27)

2018년 서울시 앱 개발공모전 참가를 위해 만들었던 공공 API를 이용한 서울시 **시장 가격 데이터 제공** Android application.


# **Features**

- [x]  서울시 시장 가격 데이터 제공
- [x]  지도 뷰 제공

# **FIXED**


## **JSON**

[서울시 열린 데이터 광장](http://data.seoul.go.kr/dataList/OA-1170/S/1/datasetView.do)
[Sample data](http://openapi.seoul.go.kr:8088/sample/json/ListNecessariesPricesService/1/5/)
정기(매주 2회, 화, 금)

#### 결론

일단, 이전의 만든 서비스가 더 이상 제공이 되지 않는 가장 큰 이유중 하나인 제공되는 JSON의 변화가 있었다.   
이전 데이터는 하나의 row에 한 시장의 **모든 가격 데이터**가 담겨 있는데, 변화된 데이터는 하나의 row에 한 시장의 **하나의 가격 데이터**가 담겨있다.  


#### 현재 JSON
```json
{
  "ListNecessariesPricesService": {
    "list_total_count":284756,
    "RESULT":{"CODE":"INFO-000","MESSAGE":"정상 처리되었습니다"},
    "row":[
      {
        "P_SEQ":1660111.0,
        "M_SEQ":25.0,
        "M_NAME":"방학동도깨비시장",
        "A_SEQ":305.0,
        "A_NAME":"사과(부사, 300g)",
        "A_UNIT":"300g",
        "A_PRICE":"2000",
        "P_YEAR_MONTH":"2021-05",
        "ADD_COL":"국내산",
        "P_DATE":"2021-05-20",
        "M_TYPE_CODE":"001",
        "M_TYPE_NAME":"전통시장",
        "M_GU_CODE":"320000",
        "M_GU_NAME":"도봉구"
      }
    ]         
  }
}
```

#### 출력값

| No   | 출력명           | 출력설명                               |
| ---- | ---------------- | -------------------------------------- |
| 공통 | list_total_count | 총 데이터 건수 (정상조회 시 출력됨)    |
| 공통 | RESULT.CODE      | 요청결과 코드 (하단 메세지설명 참고)   |
| 공통 | RESULT.MESSAGE   | 요청결과 메시지 (하단 메세지설명 참고) |
| 1    | P_SEQ            | 일련번호                               |
| 2    | M_SEQ            | 시장/마트 번호                         |
| 3    | M_NAME           | 시장/마트 이름                         |
| 5    | A_SEQ            | 품목 번호                              |
| 6    | A_NAME           | 품목 이름                              |
| 7    | A_UNIT           | 실판매규격                             |
| 8    | A_PRICE          | 가격(원)                               |
| 9    | P_YEAR_MONTH     | 년도-월                                |
| 10   | ADD_COL          | 비고                                   |
| 11   | P_DATE           | 점검일자                               |
| 12   | M_TYPE_CODE      | 시장유형 구분 코드                     |
| 13   | M_TYPE_NAME      | 시장유형 구분 이름                     |
| 14   | M_GU_CODE        | 자치구 코드                            |
| 15   | M_GU_NAME        | 자치구 이름                            |

#### 이전 JSON
```json
{
  "Mgismulgainfo": {
    "list_total_count": 102,
    "RESULT": {
      "CODE": "INFO-000",
      "MESSAGE": "정상 처리되었습니다"
    },
    "row": [
      {
        "COT_ADDR_FULL_NEW": "필운대로6길 3",
        "COT_ADDR_FULL_OLD": "종로구 통인동 44",
        "COT_COORD_X": 126.968790488,
        "COT_COORD_Y": 37.580821073,
        "COT_CONTS_ID": "PRICE_1",
        "COT_CONTS_LAN_TYPE": "KOR",
        "COT_CONTS_NAME": "통인시장",
        "COT_GU_NAME": "종로구",
        "COT_DONG_NAME": "통인동",
        "COT_MASTER_NO": "44",
        "COT_VALUE_01": "",
        "COT_VALUE_02": "가격 : 2500원 (1 개)\n",
        "COT_VALUE_03": "가격 : 4000원 (1 개)\n",
        "COT_VALUE_04": "가격 : 4000원 (1포기 )\n",
        "..."
        "COT_VALUE_16": "가격 : 7000원 (1마리)\n",
        "COT_VALUE_17": "가격 : 6000원 (1마리)\n",
        "COT_VALUE_18": "가격 : 6500원 (1마리)\n",
        "COT_NAME_01": "조사일자;;;;2018-12-20",
        "COT_NAME_02": "사과(부사, 300g)",
        "COT_NAME_03": "배(신고, 600g)",
        "COT_NAME_04": "배추(중간)",
        "..."
        "COT_NAME_16": "명태(러시아,냉동)",
        "COT_NAME_17": "오징어(생물,국산)",
        "COT_NAME_18": "고등어(생물,국산)",
        "COT_COORD_DATA": "",
        "COT_COORD_TYPE": "1",
        "COT_LINE_WEIGHT": "0",
        "COT_LINE_COLOR": "",
        "COT_KW": "",
        "COT_REG_DATE": "2018-12-24 15:36:23.0",
        "COT_UPDATE_DATE": "2018-12-24 15:36:23.0"
      }
    ]
  }
}
```

## **LIBRARY**

[butterknife](https://github.com/JakeWharton/butterknife)(deprecated) → [View Binding](https://developer.android.com/topic/libraries/view-binding)

## **REVIEW**

가격 데이터를 유저에게 제공하는 목표만을 위해서 개발하였기 때문에 과정에서 모든 판단에 대한 이유가 없었다. 틀린 판단에 대한 수정이 없었고, 단지 빌드 완성을 목표로 진행하였다. (그 당시에는 그게 맞는 줄 알았다.)
2018년에 작성했던 코드를 리뷰하고 좋은 방향으로 설계해서 코드작성 진행

### main

method
- location gps check → GetLocationPermission 이전에 미리 받아두기
- json check →  json update
- Start next Activity with latitude and longtitude intent → ProductActivity


나쁜점

- 지금 좌표 기준으로 가장 가까운 시장 순으로 리스트를 소트했음

    → 서울 밖의 유저들이 있다고 가정하지 못함

- 지속적으로 업데이트 되는 json을 parse 해 오면서 같지 않으면 업데이트를 했음

    →  많은 row값 중에서 가격 데이터 한 부분만 바뀌는데 하나를 위해 모든 데이터를 업데이트하는건 비효율적
