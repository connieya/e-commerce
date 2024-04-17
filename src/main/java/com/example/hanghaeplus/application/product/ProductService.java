package com.example.hanghaeplus.application.product;

import com.example.hanghaeplus.application.order.command.OrderProductCommand;
import com.example.hanghaeplus.application.product.command.ProductCategoryAdd;
import com.example.hanghaeplus.common.error.exception.EntityNotFoundException;
import com.example.hanghaeplus.domain.product.Product;
import com.example.hanghaeplus.application.order.command.OrderCommand;
import com.example.hanghaeplus.application.product.command.ProductCreate;
import com.example.hanghaeplus.application.product.command.ProductQuantityAdd;
import com.example.hanghaeplus.domain.product.ProductCategory;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.example.hanghaeplus.common.error.ErrorCode.*;

@Service
@Slf4j
@Builder
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;
    @Transactional
    public void deduct(OrderCommand orderCommand) {
        List<OrderProductCommand> orderProductCommand = orderCommand.getOrderProducts();

        Map<Long, Long> productIdQuntitiyMap = convertToProductIdQuantityMap(orderProductCommand);

        List<Product> products = findAllByOrderCommand(orderProductCommand);

        products.forEach(product-> {
            Long quantity = productIdQuntitiyMap.get(product.getId());
            product.deductQuantity(quantity);
        });
    }

    @Transactional
    public void register(ProductCreate productCreate) {
        ProductCategory productCategory = productCategoryRepository.findById(productCreate.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException(PRODUCT_NOT_FOUND));

        Product product = Product.of(productCreate,productCategory);

        productRepository.save(product);
    }

    @Transactional(readOnly = true)
    public Product getProduct(Long productId) {
        return findProduct(productId);
    }

    @Transactional(readOnly = true)
    public Product findProduct(Long productId) {
        return productRepository.findById(productId).orElseThrow(() -> new EntityNotFoundException(PRODUCT_NOT_FOUND));
    }

    private Map<Long, Long> convertToProductIdQuantityMap(List<OrderProductCommand> products) {
        return products.stream()
                .collect(Collectors.toMap(OrderProductCommand::getProductId, OrderProductCommand::getQuantity));
    }

    @Transactional(readOnly = true)
    public List<Product> findAllById(List<Long> productIds) {
        return productRepository.findAllByPessimisticLock(productIds);
    }

    @Transactional(readOnly = true)
    public List<Product> findAllByOrderCommand(List<OrderProductCommand> orderProductCommands){
        return productRepository.findAllByPessimisticLock(orderProductCommands.stream().map(OrderProductCommand::getProductId).collect(Collectors.toList()));
    }


    @Transactional
    public void addQuantity(ProductQuantityAdd command) {
        Product product = findProduct(command.getId());
        product.addStock(command.getQuantity());
        productRepository.save(product);
    }

    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Transactional
    public void registerCategory(ProductCategoryAdd productCategoryAdd) {
        ProductCategory productCategory = new ProductCategory(productCategoryAdd.getName());
        productCategoryRepository.save(productCategory);
    }

    public List<ProductCategory> getProductCategoryList() {
        return productCategoryRepository.findAll();
    }
}
