package com.increff.employee.dto;

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

    @Transactional
    public void deleteByBarcode(String barcode) throws ApiException{
        if(barcode==null){
            throw new ApiException("null barcode not acceptable");
        }
        productService.deleteByBarcode(barcode);
    }

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

    public void updateProductWithGivenBarcode(String barcode,ProductEditForm productEditForm) throws ApiException{
        validateProductEditForm(barcode,productEditForm);
        normalize(productEditForm);
        productService.updateProduct(barcode,productEditForm);
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
        productData.setId(productPojo.getId());
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

    private void validateProductEditForm(String barcode, ProductEditForm productEditForm) throws ApiException {
        if(barcode==null){
            throw new ApiException("invalid barcode");
        }
        ProductPojo productPojo = productService.selectByBarcode(barcode);
        if(productPojo==null){
            throw new ApiException("incorrect barcode");
        }

        if(productEditForm.getProduct()==null && productEditForm.getMRP()==null){
            throw new ApiException("no input");
        }

        if(productEditForm.getProduct()==null || productEditForm.getProduct().equals("")){
            throw new ApiException("Invalid input: input a valid product");
        }

        if(productEditForm.getMRP()==null || productEditForm.getMRP().equals(0.0)){
            throw new ApiException("Invalid input : mrp not correct");
        }

        if(productEditForm.getMRP().compareTo(0.0)<0){
            throw new ApiException("MRP can't be negative");
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
