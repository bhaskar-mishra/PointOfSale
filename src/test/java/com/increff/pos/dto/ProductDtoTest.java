package com.increff.pos.dto;

import static org.junit.Assert.assertEquals;

import com.increff.pos.model.*;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.AbstractUnitTest;
import com.increff.pos.service.ApiException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;

import java.util.Arrays;
import java.util.List;

public class ProductDtoTest extends AbstractUnitTest {

    @Autowired
    private ProductDto productDto;
    @Autowired
    private BrandCategoryDto brandCategoryDto;

    @Test
    public void addProductTestWithBrandCategory() throws ApiException{
        ProductForm productForm = new ProductForm();
        BrandForm brandForm = new BrandForm();
        brandForm.setBrand("puma");
        brandForm.setCategory("clothing");
        brandCategoryDto.addBrandCategory(brandForm);

        productForm.setBarcode("abc");
        productForm.setBrand("puma");
        productForm.setCategory("clothing");
        productForm.setProduct("jockey");
        productForm.setMrp(Double.valueOf(25.44));
        productDto.addProduct(productForm);
        ProductData productData = productDto.getProductByBarcode("abc");
        assertEquals("abc",productData.getBarcode());
        assertEquals("puma",productData.getBrand());
        assertEquals("clothing",productData.getCategory());
        assertEquals("jockey",productData.getProduct());
        assertEquals(Double.valueOf(25.44),productData.getMrp());
    }

    @Test(expected = ApiException.class)
    public void addProductTestWithNoBrandCategory() throws ApiException{
        ProductForm productForm = new ProductForm();
        productForm.setBarcode("abc");
        productForm.setBrand("puma");
        productForm.setCategory("clothing");
        productForm.setProduct("jockey");
        productForm.setMrp(Double.valueOf(25.44));
        productDto.addProduct(productForm);
        ProductData productData = productDto.getProductByBarcode("abc");
        assertEquals("abc",productData.getBarcode());
        assertEquals("puma",productData.getBrand());
        assertEquals("clothing",productData.getCategory());
        assertEquals("jockey",productData.getProduct());
        assertEquals(Double.valueOf(25.44),productData.getMrp());
    }

    @Test
    public void getAllProductsTest() throws ApiException{
        BrandForm brandForm = new BrandForm();
        brandForm.setCategory("clothing");
        brandForm.setBrand("puma");
        brandCategoryDto.addBrandCategory(brandForm);

        List<ProductForm> productForms = Arrays.asList(new ProductForm(),new ProductForm());
        TestUtils.setProductForm(productForms.get(0),"puma","clothing","abc","jockey",Double.valueOf(25.50));
        TestUtils.setProductForm(productForms.get(1),"puma","clothing","def","shirt",Double.valueOf(99.25));
        productForms.get(0).setBrand("puma");
        productForms.get(0).setCategory("clothing");
        productForms.get(1).setBrand("puma");
        productForms.get(1).setCategory("clothing");

        productDto.addProduct(productForms.get(0));
        productDto.addProduct(productForms.get(1));

        List<ProductData> productData = productDto.getAllProducts();

        assertEquals("puma",productData.get(0).getBrand());
        assertEquals("clothing",productData.get(0).getCategory());
        assertEquals("jockey",productData.get(0).getProduct());
        assertEquals("abc",productData.get(0).getBarcode());
        assertEquals(Double.valueOf(25.50),productData.get(0).getMrp());

        assertEquals("puma",productData.get(1).getBrand());
        assertEquals("clothing",productData.get(1).getCategory());
        assertEquals("shirt",productData.get(1).getProduct());
        assertEquals("def",productData.get(1).getBarcode());
        assertEquals(Double.valueOf(99.25),productData.get(1).getMrp());
    }

    @Test
    public void updateProductTest() throws ApiException{
        BrandForm brandForm = new BrandForm();
        brandForm.setBrand("puma");
        brandForm.setCategory("clothing");
        brandCategoryDto.addBrandCategory(brandForm);

        ProductForm productForm = new ProductForm();
        productForm.setBrand("puma");
        productForm.setCategory("clothing");

        TestUtils.setProductForm(productForm,"puma","clothing","abc","jockey",Double.valueOf(25.4));
        productDto.addProduct(productForm);

        ProductEditForm productEditForm = new ProductEditForm();
        productEditForm.setProduct("shirt");
        productEditForm.setBarcode("abc");
        productEditForm.setMRP(Double.valueOf(22));

        productDto.updateProduct(productEditForm);

        ProductData productData = productDto.getProductByBarcode("abc");
        assertEquals("puma",productData.getBrand());
        assertEquals("clothing",productData.getCategory());
        assertEquals("shirt",productData.getProduct());
        assertEquals("abc",productData.getBarcode());
        assertEquals(Double.valueOf(22),productData.getMrp());

    }

    @Test
    public void normalizeProductFormTest() throws ApiException {
        ProductForm productForm = new ProductForm();
        productForm.setBarcode("      abc   ");
        productForm.setBrand(" PUmA  ");
        productForm.setCategory("   ClOtHIng");
        productForm.setProduct("   Jo Ckey");
        productForm.setMrp(Double.valueOf(25.4));

        DtoUtils.normalizeProductForm(productForm);
        assertEquals("abc",productForm.getBarcode());
        assertEquals("puma",productForm.getBrand());
        assertEquals("clothing",productForm.getCategory());
        assertEquals("jo ckey",productForm.getProduct());

    }

    @Test
    public void normalizeProductEditFormTest() throws ApiException{
        ProductEditForm productEditForm = new ProductEditForm();
        productEditForm.setProduct(" j oCk Ey   ");
        productEditForm.setBarcode("     ac@bA2B  ");
        productEditForm.setMRP(Double.valueOf(25.20));

        DtoUtils.normalizeProductEditForm(productEditForm);

        assertEquals("j ock ey",productEditForm.getProduct());
        assertEquals("ac@ba2b",productEditForm.getBarcode());
    }

    @Test(expected = ApiException.class)
    public void validateProductFormBarcodeNullTest() throws ApiException{
        ProductForm productForm = new ProductForm();
        TestUtils.setProductForm(productForm,"puma","clothing",null,"jockey",Double.valueOf(25.2));
    }

    @Test(expected = ApiException.class)
    public void validateProductFormBarcodeEmptyTest() throws ApiException{
        ProductForm productForm = new ProductForm();
        TestUtils.setProductForm(productForm,"puma","clothing","","jockey",Double.valueOf(25.2));
        DtoUtils.validateProductForm(productForm);
    }

    @Test(expected = ApiException.class)
    public void validateProductFormBrandCategoryNullTest() throws ApiException{
        ProductForm productForm = new ProductForm();
        TestUtils.setProductForm(productForm,null,null,"abc","jockey",Double.valueOf(25.2));
        DtoUtils.validateProductForm(productForm);
    }

    @Test(expected = ApiException.class)
    public void validateProductFormBrandCategoryEmptyTest() throws ApiException{
        ProductForm productForm = new ProductForm();
        TestUtils.setProductForm(productForm,"  ","","abc","jockey",Double.valueOf(25.2));
        DtoUtils.validateProductForm(productForm);
    }

    @Test(expected = ApiException.class)
    public void validateProductFormProductNullTest() throws ApiException{
        ProductForm productForm = new ProductForm();
        TestUtils.setProductForm(productForm,"puma","clothing","abc",null,Double.valueOf(25.2));
        DtoUtils.validateProductForm(productForm);
    }

    @Test(expected = ApiException.class)
    public void validateProductFormEmptyProductTest() throws ApiException{
        ProductForm productForm = new ProductForm();
        TestUtils.setProductForm(productForm,"puma","clothing","abc","   ",Double.valueOf(25.2));
        DtoUtils.validateProductForm(productForm);
    }

    @Test(expected = ApiException.class)
    public void validateProductFormPriceNull() throws ApiException{
        ProductForm productForm = new ProductForm();
        TestUtils.setProductForm(productForm,"puma","clothing","abc","jockey",null);
        DtoUtils.validateProductForm(productForm);
    }

    @Test(expected = ApiException.class)
    public void validateProductFormNegativePrice() throws ApiException{
        ProductForm productForm = new ProductForm();
        TestUtils.setProductForm(productForm,"puma","clothing","abc","jockey",Double.valueOf(-25.4));
        DtoUtils.validateProductForm(productForm);
    }

    @Test(expected = ApiException.class)
    public void validateProductEditFormNullProductTest() throws ApiException{
        ProductEditForm productEditForm = new ProductEditForm();
        TestUtils.setProductEditForm(productEditForm,null,"abc",Double.valueOf(25.4));
        DtoUtils.validateProductEditForm(productEditForm);
    }

    @Test(expected = ApiException.class)
    public void validateProductEditFormEmptyProductTest() throws ApiException{
        ProductEditForm productEditForm = new ProductEditForm();
        TestUtils.setProductEditForm(productEditForm,"  ","abc",Double.valueOf(25.4));
        DtoUtils.validateProductEditForm(productEditForm);
    }

    @Test(expected = ApiException.class)
    public void validateProductEditFormNullBarcodeTest() throws ApiException{
        ProductEditForm productEditForm = new ProductEditForm();
        TestUtils.setProductEditForm(productEditForm,"jockey",null,Double.valueOf(25.4));
        DtoUtils.validateProductEditForm(productEditForm);
    }

    @Test(expected = ApiException.class)
    public void validateProductEditFormEmptyBarcodeTest() throws ApiException{
        ProductEditForm productEditForm = new ProductEditForm();
        TestUtils.setProductEditForm(productEditForm,"jockey","",Double.valueOf(25.4));
        DtoUtils.validateProductEditForm(productEditForm);
    }

    @Test(expected = ApiException.class)
    public void validateProductEditFormNullMrp() throws ApiException{
        ProductEditForm productEditForm = new ProductEditForm();
        TestUtils.setProductEditForm(productEditForm,"jockey","abc",null);
        DtoUtils.validateProductEditForm(productEditForm);
    }

    @Test(expected = ApiException.class)
    public void validateProductEditFormNegativeMrp() throws ApiException{
        ProductEditForm productEditForm = new ProductEditForm();
        TestUtils.setProductEditForm(productEditForm,"jockey","abc",Double.valueOf(-25.4));
        DtoUtils.validateProductEditForm(productEditForm);
    }

    @Test
    public void convertProductFromToPojoTest() throws ApiException {
        ProductForm productForm = new ProductForm();
        TestUtils.setProductForm(productForm,"puma","clothing","abc","jockey",Double.valueOf(25.4));
        ProductPojo productPojo =  DtoUtils.convertProductFormToPojo(productForm,1);
        assertEquals(Integer.valueOf(1),productPojo.getBrandCategoryId());
        assertEquals(Double.valueOf(25.4),productPojo.getMrp());
        assertEquals("abc",productPojo.getBarcode());
        assertEquals("jockey",productPojo.getProduct());
    }

    @Test
    public void convertProductPojoToDataTest() throws ApiException{
        ProductPojo productPojo = new ProductPojo();
        productPojo.setProduct("jockey");
        productPojo.setMrp(Double.valueOf(25.4));
        productPojo.setBarcode("abc");;

        ProductData productData = DtoUtils.convertProductPojoToData(productPojo,"puma","clothing");
        assertEquals("puma",productData.getBrand());
        assertEquals("clothing",productData.getCategory());
        assertEquals("jockey",productData.getProduct());
        assertEquals("abc",productData.getBarcode());
        assertEquals(Double.valueOf(25.4),productData.getMrp());
    }


}
