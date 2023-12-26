## 비즈니스 로직

### 주문 생성

```java
public void createOrder(OrderPostRequest request) {
        User user = userRepository.findById(request.getUserId()).get();
        List<ProductRequestForOrder> productRequest = request.getProducts();
        Map<Long, Long> stockMap = productRequest.stream()
                .collect(Collectors.toMap(ProductRequestForOrder::getProductId, ProductRequestForOrder::getQuantity));
        // product id 추출
        List<Product> products = productRepository.findAllById(productRequest.stream().map(ProductRequestForOrder::getProductId).collect(Collectors.toList()));

        // 재고 차감
        for (Product product : products) {
            Long quantity = stockMap.get(product.getId());
            if (product.isLessThanQuantity(quantity)){
                throw new InsufficientStockException(INSUFFICIENT_STOCK);
            }
            product.deductQuantity(quantity);
        }

        // 주문
        Order order = Order.create(user, products);
        Order savedOrder = orderRepository.save(order);

        // 결제
```

#### 테스트

```java
   @DisplayName("주문 리스트를 받아서 주문을 생성한다.")
    @Test
    void createOrder() {
        // given


        // when


        //then
    }
```

### 재고 확인 & 차감

```java
        Map<Long, Long> stockMap = productRequest.stream()
                .collect(Collectors.toMap(ProductRequestForOrder::getProductId, ProductRequestForOrder::getQuantity));
        // product id 추출
        List<Product> products = productRepository.findAllById(productRequest.stream().map(ProductRequestForOrder::getProductId).collect(Collectors.toList()));

        // 재고 차감
        for (Product product : products) {
            Long quantity = stockMap.get(product.getId());
            if (product.isLessThanQuantity(quantity)){
                throw new InsufficientStockException(INSUFFICIENT_STOCK);
            }
            product.deductQuantity(quantity);
        }
```
#### 테스트
```java
    @DisplayName("주문을 하기 전 물건의 재고를 확인하고 차감한다.")
    @Test
    void deductQuantity(){
        // given


        // when


        //then
    }
```


### 결제

```java
public void processPayment(Order order) {
        Long totalPrice = order.getTotalPrice();
        User user = userRepository.findById(order.getUser().getId()).get();
        if (user.getCurrentPoint() < totalPrice){
            throw new InsufficientPointsException(INSUFFICIENT_POINT);
        }
        Point point = Point.create(user, totalPrice);
        pointRepository.save(point);
    }
```

#### 테스트

```java
    @DisplayName("주문서를 토대로 결제를 한다")
    @Test
    void processPayment(){
        // given

        // when


        //then
    }
```