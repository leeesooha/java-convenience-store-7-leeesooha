# java-convenience-store-precourse

🏪 프로젝트 개요
이 프로젝트는 편의점에서의 상품 구매와 결제를 위한 시스템을 구현하는 것입니다. 구매자는 할인 혜택과 재고 상황을 고려하여 최종 결제 금액을 안내받을 수 있습니다. 상품의 가격, 수량, 프로모션, 멤버십 할인이 반영된 결제 금액을 영수증으로 출력하고, 구매 내역을 관리합니다.

## 📝 상품 구매
  - [x] 상품 구매 안내 메세지 출력
  - [x] 구매할 상품 입력: `[콜라-10],[사이다-3]`
  - [x] 구매할 상품을 장바구니에 담는다.
  - [x] 예외 상황
    - [x] 빈 입력일때 예외 처리하는 기능 : `[ERROR] 빈 입력입니다.`
    - [x] 입력형식 맞지 않으면 메세지 띄우고 다시 입력 받음: `[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.`
    - [x] 존재하지 않는 상품을 입력한 메세지 띄우고 경우 다시 입력 받음: `[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요.`
    - [x] 구매 수량이 재고 수량을 초과한 경우 메시지 띄우고 다시 입력 받음: `[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.`
    - [x] 기타 잘못된 입력의 경우 메시지 띄우고 다시 입력 받음: `[ERROR] 잘못된 입력입니다. 다시 입력해 주세요.`
    - [x] 상품 입력시 중복된 상품명 입력시 예외 처리하는 기능 추가: `[ERROR] 중복된 상품명은 입력할 수 없습니다.`

## 📝 재고관리
  - [x] 파일에서 재고정보 읽어와서 재고 저장하는 기능
  - [x] 재고를 출력하는 기능
  - [x] 구매할 상품종류가 재고에 존재하는지 확인하는 기능
  - [x] 구매할 일반 상품개수 만큼 재고 수량 차감하는 기능
  - [x] 구매할 프로모션 상품 만큼 재고 수량 차감하는 기능
  - [x] 구매할 상품개수가 재고에 충분한지 확인하는 기능
  - [x] 구매할 일반 상품 재고가 있는지 확인하는 기능
  - [x] 구매할 프로모션 상품 재고가 있는지 확인하는 기능
  - [x] 상품을 구매하면 재고를 차감한다.

## 📝프로모션 할인
  - [x] 파일에서 프로모션정보 읽어와서 프로모션 정보 저장하는 기능
  - [x] 프로모션 상품인지 확인하는 기능
  - [x] 프로모션 구매 수량만큼 재고창고에서 차감하는 기능추가
  - [x] 프로모션 기간 확인하는 기능

## 📝멤버십 할인
  - [x] 멤버쉽 할인 금액이 8000원 이상일 시 최대 8000원까지만 제한하는 기능

## 📝결제 금액 계산
  - [x] 결제 상품 개수 + 상품 금액으로 총 상품 금액을 계산한다. 
  - [x] 멤버십할인 적용하는 기능
  - [x] 프로모션 할인 금액 적용하는 기능

## 📝영수증 출력
  - [x] 장바구니에서 구매한 상품, 증정 상품, 수량 , 할인금액 출력

## 📝안내 메시지 출력
  - [x] 편의점 방문시 환영 안내메시지를 출력하는 기능
