# e-커머스 상품 주문 서비스

## 기술 스택

- Java 17
- Sprig Boot 3.2.0
- Spring Web
- Spring Data JPA
- H2 Database
- QueryDsl
- Docker
- Github Actions
- AWS ECR (Elastic Container Registry)
- AWS ECS (Elastic Container Service)

## 요구 사항

- 상품 주문에 필요한 메뉴 정보들을 구성하고 조회가 가능해야 한다.
- 잔액 충전 , 상품 주문 시 회원의 포인트 사용 내역을 저장 한다.
- 사용자는 상품을 여러 개 선택해서 주문할 수 있고, 미리 충전한 잔액을 이용한다.
- 상품 주문 내역을 통해 판매량이 가장 높은 상품을 추천한다.
- 각 기능 및 제약 사항에 대한 단위 테스트를 반드시 하나 이상 작성 해야 한다.
- 동시성 이슈를 고려 하여 구현 한다.
- 재고 관리에 문제가 없도록 구현 한다.

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



![img.png](docs/img.png)


## API 스펙 

### 회원 API

- 회원 등록
  - 이름 ,닉네임 ,이메일 정보를 통해 회원을 등록한다.
  - 회원 등록 시 포인트는 0으로 초기화 한다.
  - 닉네임과 이메일은 다른 회원과 중복 되어서는 안된다.

- 포인트 충전
  - 상품 주문에 필요한 포인트를 충전 한다.
- 포인트 조회
  - 회원의 포인트를 조회한다.

### 상품 API

- 상품 등록
  - 상품 이름, 가격, 재고 수량 정보를 통해 상품을 등록 한다.
- 상품 조회
  - 상품 식별자 를 통해 상품 정보(ID , 이름, 가격 ,잔여 수량) 를 조회 한다.

### 주문 API

- 상품 주문
  - 사용자 식별자 ,상품 Id ,상품 수량을 통해 주문을 요청한다. 
  - 주문 한 상품 수량 만큼 재고를 차감 한다.
  - 만약 해당 상품의 재고가 부족 하면 주문을 취소 한다.
  - 주문 한 상품 가격 만큼 회원의 포인트를 차감한다.
  - 회원의 포인트가 부족하면 주문은 취소 된다. 



## 테스트

- 테스트의 격리성을 위해 로컬 환경과 테스트 환경을 분리한다.
- 테스트는 독립적으로 수행 되어야 한다.
  - 테스트 실행 순서에 상관 없이 항상 성공해야 한다.

### 패키지 구조

```
src
├──main
  ├──java
     ├──com
       ├──example
          ├──hanghaeplus
            ├─ application
            ├──domain
            ├──fixture
            ├──infrastructure
            ├──medium
            ├──presentation
```

- fixture 
  - 테스트 코드 작성 시 필요한 객체를 정의 한다. 
- application
  - Repository 클래스를 Mocking 하여 테스트 코드를 작성 한다.
- domain
  - 도메인 주요 비즈니스 로직에 대한 테스트 코드를 작성 한다.
- presentation
  - SpringTest 의 MockMvc 를 사용해 진행 한다.


[//]: # (## 고민한 부분)
[//]: # ()
[//]: # (#### [테스트 코드 작성을 위해 코드를 추가 하는 것이 맞을까?]&#40;docs/테스트코드.md&#41;)


