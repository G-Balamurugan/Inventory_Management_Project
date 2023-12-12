package com.quinbay.inventoryservicesmanagement.serviceImplementation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quinbay.inventoryservicesmanagement.doa.api.CategoryRepository;
import com.quinbay.inventoryservicesmanagement.doa.api.ProductRepository;
import com.quinbay.inventoryservicesmanagement.model.entity.Category;
import com.quinbay.inventoryservicesmanagement.model.entity.Product;
import com.quinbay.inventoryservicesmanagement.model.vo.CategoryVo;
import com.quinbay.inventoryservicesmanagement.model.vo.ProductVo;
import com.quinbay.inventoryservicesmanagement.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("productBean")
public class ProductServiceImplementation implements ProductService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryRepository categoryRepository;
    private ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private KafkaTemplate<String , String> kafkaTemplate;

    public void sendMessage(ProductVo productVo) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        kafkaTemplate.send("com.quinbay.product.create", "--->" + objectMapper.writeValueAsString(productVo));
    }
    public List<String> productTableInsert(List<ProductVo> productVoList)
    {
        List<String> result = new ArrayList<>();
        List<Product> productList = productVoList.stream()
                .map(productVo -> {
                    Product product = objectMapper.convertValue(productVo, Product.class);
                    if (productVo.getCategory() != null) {
                        Category category = objectMapper.convertValue(productVo.getCategory(), Category.class);
                        product.setCategory(category);
                    }
                    return product;
                })
                .collect(Collectors.toList());

        List<Product> productsToAdd = new ArrayList<>();
        for (Product product : productList) {
            Optional<Product> existingProduct = productRepository.findById(product.getId());
            if (existingProduct.isEmpty()) {
                productsToAdd.add(product);
                result.add("Added");
            }
            else
                result.add("Already Present");
        }
        try {
            productRepository.saveAll(productsToAdd);
        }catch (Exception exceptionArised)
        {
            System.out.println("Exception Catched => " + exceptionArised);
        }
        return result;
    }
    public List<String> categoryTableInsert(List<CategoryVo> categoryVoList)
    {
        List<String> result = new ArrayList<>();
        List<Category> categoryList = categoryVoList.stream()
                .map(categoryVo -> objectMapper.convertValue(categoryVo,Category.class))
                .collect(Collectors.toList());
        List<Category> categoryToAdd = new ArrayList<>();
        for (Category category : categoryList)
        {
            if(category.getId() == null)
            {
                categoryToAdd.add(category);
            }
            else{
                Optional<Category> existingCategory = categoryRepository.findById(category.getId());
                if(existingCategory.isEmpty()){
                    categoryToAdd.add(category);
                    result.add("Added");
                }
                else
                    result.add("Already Present");
            }
        }
        try {
            categoryRepository.saveAll(categoryToAdd);
        }catch (Exception exceptionArised)
        {
            System.out.println("Exception Catched => " + exceptionArised);
        }
        return result;
    }
    public String deleteProductEntry(Long id)
    {
        Optional<Product> chkProducts = productRepository.findById(id);

        if (chkProducts.isPresent()) {
            try {
                productRepository.deleteById(id);
            }catch (Exception exceptionArised)
            {
                System.out.println("Exception Catched => " + exceptionArised);
            }
            return "Deleted Successfully";
        }return "Not Found";
    }
    public String deleteCategoryEntry(Long id)
    {
        Optional<Category> chkCategory = categoryRepository.findById(id);

        if (chkCategory.isPresent()) {
            for (Product product : chkCategory.get().getProductList())
            {
                deleteProductEntry(product.getId());
            }
            try {
                categoryRepository.deleteById(id);
            }catch (Exception exceptionArised)
            {
                System.out.println("Exception Catched => " + exceptionArised);
            }
            return "Delete Successfully";
        }
        return "Not Found";
    }
    @Transactional
    public String updateProduct(ProductVo productVo , long productId)
    {
        Optional<Product> chkProduct = productRepository.findById(productId);

        if(chkProduct.isPresent())
        {
            Product product = chkProduct.get();
            Optional<Category> chkCategory = categoryRepository.findById(product.getCategory().getId());
            if(chkCategory.isPresent())
            {
                Category category = chkCategory.get();
                if(!category.getName().equals(product.getName())) {
                    return "Category Not Found";
                }
            }
            product.setName(productVo.getName());
            product.setQuantity(productVo.getQuantity());
            product.setCategory(productVo.getCategory());
            if(product.getPrice() > productVo.getPrice())
            {
                try {
                    sendMessage(productVo);
                }catch (JsonProcessingException exceptionCatched)
                {
                    System.out.println("Exception Arised => " + exceptionCatched);
                }catch (Exception exceptionArised)
                {
                    System.out.println("Exception Catched => " + exceptionArised);
                }
            }
            product.setPrice(productVo.getPrice());
            try {
                productRepository.save(product);
            }catch (Exception exceptionArised)
            {
                System.out.println("Exception Catched => " + exceptionArised);
            }
            return "Updated Successfully";
        }
        return "Product Not Found";
    }
    @Transactional
    public String updateCategory(CategoryVo categoryVo , long categoryId)
    {
        Optional<Category> chkCategory = categoryRepository.findById(categoryId);
        if (chkCategory.isPresent())
        {
            Category category = chkCategory.get();
            category.setName(categoryVo.getName());
            try {
                categoryRepository.save(category);
            }catch (Exception exceptionArised)
            {
                System.out.println("Exception Catched => " + exceptionArised);
            }
            return "Updated Successfully";
        }
        return "Category Not Found";
    }
    public List<ProductVo> getProductList()
    {
        List<Product> productList = productRepository.findAll();
        return objectMapper.convertValue(productList , List.class);
    }
    public List<CategoryVo> getCategoryList()
    {
        List<Category> categoryList = categoryRepository.findAll();
        return objectMapper.convertValue(categoryList , List.class);
    }
    public List<ProductVo> productByFilter(Long productId , String category , String productName)
    {
        List<Product> productList = productRepository.findProductIdCategoryName(productId , category , productName);
        return objectMapper.convertValue(productList, List.class);
    }
    public int productQuantity(Long productId)
    {
        Optional<Product> chkProduct = productRepository.findById(productId);
        if(chkProduct.isPresent())
        {
            int quantity = productRepository.findProductQuantity(productId);
            return objectMapper.convertValue(quantity,Integer.class);
        }
        return -1;
    }
    public ProductVo updateQuantity(Long productId , int purchaseQuantity)
    {
        Optional<Product> chkProduct = productRepository.findById(productId);
        if(chkProduct.isPresent())
        {
            Product product = chkProduct.get();
            product.setQuantity(product.getQuantity() - purchaseQuantity);
            try {
                productRepository.save(product);
            }catch (Exception exceptionArised)
            {
                System.out.println("Exception Catched => " + exceptionArised);
            }
            return objectMapper.convertValue(product,ProductVo.class);
        }
        ProductVo productVo = new ProductVo();
        return productVo;
    }
}
