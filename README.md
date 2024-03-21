# e-커머스 상품 주문 서비스

## 기술 스택

- Java 17
- Sprig Boot 3.2.0
- Spring Web
- Spring Data JPA
- H2 Database
- QueryDsl

## 요구 사항

- 상품 주문에 필요한 메뉴 정보들을 구성하고 조회가 가능해야 한다.
- 사용자는 상품을 여러 개 선택해서 주문할 수 있고, 미리 충전한 잔액을 이용한다.
- 상품 주문 내역을 통해 판매량이 가장 높은 상품을 추천한다.
- 각 기능 및 제약 사항에 대한 단위 테스트를 반드시 하나 이상 작성 해야 한다.
- 동시성 이슈를 고려 하여 구현 한다.

## 패키지 구조

```
src
├──main
  ├──java
     ├──com
       ├──example
          ├──hanghaeplus
            ├─ application
            ├──common
            ├──domain
            ├──infrastructure
            ├──presentation
```

엔티티 안에서 비즈니스 로직을 가지고 객체지향을 활용하는 Domain Model Pattern 을 적용한다.


- common
    - 애플리케이션 전반적으로 공통적으로 사용되는 설정 파일, 예외 처리 및 응답과 관련된 클래스 등을 포함 한다.
- application
    - 비즈니스 로직을 정의하고 정상적으로 수행될 수 있도록 도메인 계층과 인프라스트럭쳐 계층을 연결해준다.
- presentation
    - 애플리케이션의 사용자 인터페이스 및 클라이언트와의 상호작용을 처리한다.
    - 요청을 받아 도메인 영역으로 전달하고, 도메인 모델의 변화를 사용자에게 응답으로 반환한다.
- domain
    - 애플리케이션의 핵심 비즈니스 로직을 담당한다.
    - 비즈니스 규칙을 구현하고, 도메인 객체 간의 관계를 관리하며, 영속화된 데이터를 조작하는 역할을 수행한다.
- infrastructure
    - 데이터베이스나 외부 시스템과의 상호작용을 담당하는 영역이다.