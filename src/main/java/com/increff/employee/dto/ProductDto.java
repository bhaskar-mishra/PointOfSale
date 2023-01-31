package com.increff.employee.dto;

import com.google.protobuf.Api;
import com.increff.employee.model.ProductData;
import com.increff.employee.model.ProductEditForm;
import com.increff.employee.model.ProductForm;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.BrandService;
import com.increff.employee.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductDto {

    @Autowired
    private BrandService brandService;
    @Autowired
    private ProductService productService;


    public void addProduct(ProductForm productForm) throws ApiException{
        validate(productForm);
        normalize(productForm);

        productService.add(convertFormToPojo(productForm));
    }

    public List<ProductData> getAllProducts() throws ApiException {
        List<ProductPojo> productPojoList = productService.getAll();
        List<ProductData> productDataList = new ArrayList<>();
        for(ProductPojo productPojo : productPojoList){
            productDataList.add(convertPojoToData(productPojo));
        }

        return productDataList;
    }

    public ProductData getProductByBarcode(String barcode) throws ApiException{
        if(barcode==null || barcode.toLowerCase().trim().equals("")){
            throw new ApiException("invalid barcode");
        }

        ProductPojo productPojo = productService.selectByBarcode(barcode);
        if(productPojo==null){
            throw new ApiException("invalid barcode");
        }

       return convertPojoToData(productPojo);
    }

    public void updateProductWithGivenBarcode(ProductEditForm productEditForm) throws ApiException{
        validate(productEditForm);
    }

    private   ProductPojo convertFormToPojo(ProductForm productForm) throws ApiException {
        ProductPojo productPojo = new ProductPojo();
        Integer brandCategoryId = 0;

        BrandPojo brandPojo = brandService.selectByBrandCategory(productForm.getBrand(), productForm.getCategory());
        if(brandPojo==null){
            throw new ApiException("Incorrect Brand Category");
        }
        brandCategoryId = brandPojo.getId();
        productPojo.setProduct(productForm.getProduct());
        productPojo.setBarcode(productForm.getBarcode());
        productPojo.setMrp(productForm.getMrp());
        productPojo.setBrand(productForm.getBrand());
        productPojo.setCategory(productForm.getCategory());
        productPojo.setBrandCategoryId(brandCategoryId);
        return productPojo;
    }

    private   ProductData convertPojoToData(ProductPojo productPojo){
        ProductData productData = new ProductData();
        productData.setProduct(productPojo.getProduct());
        productData.setMrp(productPojo.getMrp());
        productData.setBarcode(productPojo.getBarcode());
        productData.setBrand(productPojo.getBrand());
        productData.setCategory(productPojo.getCategory());
        return productData;
    }

    private void validate(ProductForm productForm) throws ApiException{
        if(productForm==null){
            throw new ApiException("productForm is null");
        }

        if(productForm.getBrand()==null || productForm.getBrand().toLowerCase().trim().equals("")){
            throw new ApiException("invalid brand category");
        }

        if(productForm.getCategory()==null || productForm.getCategory().toLowerCase().trim().equals("")){
            throw new ApiException("invalid brand category");
        }

        if(productForm.getProduct()==null || productForm.getProduct().toLowerCase().trim().equals("")){
            throw new ApiException("invalid product");
        }

        if(productForm.getCategory()==null || productForm.getCategory().toLowerCase().equals("")){
            throw new ApiException("invalid category");
        }

        if(productForm.getBarcode()==null || productForm.getBarcode().toLowerCase().trim().equals("")){
            throw new ApiException("invalid barcode");
        }

        if(productForm.getMrp()==null){
            throw new ApiException("MRP can't be null");
        }

        try{
            Double mrp = Double.parseDouble((""+productForm.getMrp()));
        }catch (NumberFormatException exception){
            throw new ApiException("MRP has to be numeric");
        }

        if(productForm.getMrp().compareTo(0.0)<=0){
            throw new ApiException("MRP should be a positive numeric value");
        }
    }


    public void validate(ProductEditForm productEditForm) throws ApiException{
        if(productEditForm==null){
            throw new ApiException("invalid request");
        }

        if(productEditForm.getBarcode()==null || productEditForm.getBarcode().toLowerCase().equals("")){
            throw new ApiException("invalid barcode");
        }

        if(productEditForm.getProduct()==null || productEditForm.getProduct().toLowerCase().trim().equals("")){
            throw new ApiException("product invalid");
        }

        if(productEditForm.getMRP()==null){
            throw new ApiException("mrp should be a positive numeric value");
        }

        try{
            Double mrp = Double.parseDouble(""+productEditForm.getMRP());
        }catch (Exception exception){
            throw new ApiException("invalid mrp : mrp should be a positive numeric value");
        }

        if(productEditForm.getMRP().compareTo(0.0)<=0){
            throw new ApiException("invalid mrp : mrp should be a positive numeric value");
        }
    }

    private void normalize(ProductForm productForm){
        productForm.setProduct(productForm.getProduct().toLowerCase().trim());
        productForm.setBrand(productForm.getBrand().toLowerCase().trim());
        productForm.setCategory(productForm.getCategory().toLowerCase().trim());
        productForm.setBarcode(productForm.getBarcode().toLowerCase().trim());
    }

    private void normalize(ProductEditForm productEditForm){
        productEditForm.setProduct(productEditForm.getProduct().toLowerCase().trim());
    }
}
