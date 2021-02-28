# SeoulSijang

서울시 공공 API를 이용해서 서울시 구마다 두개의 시장 가격 데이터를 불러온 앱.\
서울시 재래시장 및 대형시장 총 102곳의 특정 식료품 가격을 파싱했다.
 
1인 개발\
개발기간 : 2018.06~ 2018.10\
결과 : 2018 서울시 앱 공모전 예선당선


![Group 8](https://user-images.githubusercontent.com/42805797/109381678-1c428280-791f-11eb-8efb-19af9536a319.png)
![Group 9](https://user-images.githubusercontent.com/42805797/109381672-164ca180-791f-11eb-881d-2818f0be352d.png)

## Used library

Retrofit\
Gson\
Okhttp\
Butterknife(2018)\
Google play gms, play, photo

## Design

### Backdrop

![image](https://user-images.githubusercontent.com/42805797/109432795-4778bf00-7a50-11eb-80b3-6f8bd50bb342.png)

Link: [Backdrop Material Webpage][googlelink]

[googlelink]: https://material.io/components/backdrop#anatomy "go backdrop page"

Material 페이지에서 backdrop 디자인을 보고 카피하여 적용했다.\
뒷면을 Fragment로 만들어 앞에 Activit을 배치했음. Animate로 Activity를 내리고 올릴 수 있게 했다.
