package com.codingdayo.shopping_cart.service.product;

import com.codingdayo.shopping_cart.Exceptions.ProductAlreadyExistException;
import com.codingdayo.shopping_cart.Exceptions.ResourceNotFoundException;
import com.codingdayo.shopping_cart.dto.ImageDto;
import com.codingdayo.shopping_cart.dto.ProductDto;
import com.codingdayo.shopping_cart.model.Category;
import com.codingdayo.shopping_cart.model.Image;
import com.codingdayo.shopping_cart.model.Product;
import com.codingdayo.shopping_cart.repository.CategoryRepository;
import com.codingdayo.shopping_cart.repository.ImageRepository;
import com.codingdayo.shopping_cart.repository.ProductRepository;
import com.codingdayo.shopping_cart.request.ProductUpdateRequest;
import com.codingdayo.shopping_cart.request.addProductRequest;
import com.codingdayo.shopping_cart.service.product.interfac.IProductService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {


    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final ImageRepository imageRepository;



    @Override
    public Product addProduct(addProductRequest request) {
        //check if the category is found in the DB
        //If yes, set it as the new product category
        //If not, then save it as a new category.
        //Then set it as the new product category.
        Category category = Optional.ofNullable(
                categoryRepository.findByName(request.getCategory().getName()))
                .orElseGet(() -> {
                    Category newCategory = new Category(request.getCategory().getName());
                    return categoryRepository.save(newCategory);
                });
        request.setCategory(category);
        return productRepository.save(createProduct(request, category));
    }
    private Product createProduct(addProductRequest request, Category category){

        //this is to prevent creating the same product twice.
        List<Product> existingProduct = productRepository.findByBrandAndName(request.getBrand(), request.getName());
        if (!existingProduct.isEmpty()){
            throw new ProductAlreadyExistException("Product already exists, can't be duplicated");
        }
        return new  Product(
                request.getName(),
                request.getBrand(),
                request.getPrice(),
                request.getInventory(),
                request.getDescription(),
                category
        );
    }

    @Override
    public Product updateProduct(ProductUpdateRequest request, Long productId) {

        return productRepository.findById(productId)
                .map(exisingProduct -> updateExistingProduct(exisingProduct, request))
                .map(productRepository :: save)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found!"));
    }

    private Product updateExistingProduct(Product existingProduct, ProductUpdateRequest request){
        existingProduct.setName(request.getName());
        existingProduct.setBrand(request.getBrand());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setInventory(request.getInventory());
        existingProduct.setDescription(request.getDescription());

        Category category = categoryRepository.findByName(request.getCategory().getName());
        existingProduct.setCategory(category);

        return existingProduct;

    }


    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found1"));
    //NB it was changed from ProductNotFoundException to ResourceNotFoundException because the latter can be used globally.
    }

    @Override
    public void deleteProductById(Long id) {
            productRepository.findById(id).ifPresentOrElse(productRepository::delete,
                    () -> {throw new ResourceNotFoundException("Product not found!");});
    }


    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryName(category);
    }


    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category, brand);
    }

    @Override
    public List<Product> getProductsByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndName(brand, name);
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand, name);
    }


    //converting the product with model mapper
    @Override
    public ProductDto convertToDto(Product product){
        ProductDto productDto = modelMapper.map(product, ProductDto.class);
        List<Image> images = imageRepository.findByProductId(product.getId());
        List<ImageDto> imageDtos = images.stream().map(image ->
                modelMapper.map(image, ImageDto.class))
                .toList();

        productDto.setImages(imageDtos);
        return productDto;
    }


    //then the utility class to convert the, AFTER here go and update in the product controller
    @Override
    public List<ProductDto> getConvertedProducts(List<Product> products){
        return products.stream().map(this::convertToDto).toList();
    }


}
