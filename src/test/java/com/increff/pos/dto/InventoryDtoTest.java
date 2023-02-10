package com.increff.pos.dto;

import com.increff.pos.model.form.*;
import com.increff.pos.model.data.*;
import com.increff.pos.pojo.InventoryPojo;
import com.increff.pos.service.AbstractUnitTest;
import com.increff.pos.service.ApiException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
public class InventoryDtoTest extends AbstractUnitTest {

    @Autowired
    private InventoryDto inventoryDto;
    @Autowired
    private BrandCategoryDto brandCategoryDto;
    @Autowired
    private ProductDto productDto;


    @Test
    public void addInventoryTest() throws ApiException {
        BrandForm brandForm = new BrandForm();
        TestUtils.setBrandForm(brandForm,"puma","clothing");
        brandCategoryDto.addBrandCategory(brandForm);

        ProductForm productForm = new ProductForm();
        TestUtils.setProductForm(productForm,"puma","clothing","abc","jockey",Double.valueOf(25.4));
        productDto.addProduct(productForm);

        InventoryForm inventoryForm = new InventoryForm();
        inventoryForm.setBarcode("abc");
        inventoryForm.setQuantity(Integer.valueOf(25));

        inventoryDto.addInventory(inventoryForm);

        InventoryData inventoryData = inventoryDto.getInventory("abc");
        assertEquals("abc",inventoryData.getBarcode());
        assertEquals(Integer.valueOf(25),inventoryData.getQuantity());
    }

    @Test
    public void getInventoryTest() throws ApiException{
        BrandForm brandForm = new BrandForm();
        TestUtils.setBrandForm(brandForm,"puma","clothing");
        brandCategoryDto.addBrandCategory(brandForm);

        ProductForm productForm = new ProductForm();
        TestUtils.setProductForm(productForm,"puma","clothing","abc","jockey",Double.valueOf(25.4));
        productDto.addProduct(productForm);

        InventoryForm inventoryForm = new InventoryForm();
        inventoryForm.setBarcode("abc");
        inventoryForm.setQuantity(Integer.valueOf(25));

        inventoryDto.addInventory(inventoryForm);

        InventoryData inventoryData = inventoryDto.getInventory("abc");
        assertEquals("abc",inventoryData.getBarcode());
        assertEquals(Integer.valueOf(25),inventoryData.getQuantity());

    }

    @Test
    public void updateInventoryTest() throws ApiException{
        BrandForm brandForm = new BrandForm();
        TestUtils.setBrandForm(brandForm,"puma","clothing");
        brandCategoryDto.addBrandCategory(brandForm);

        ProductForm productForm = new ProductForm();
        TestUtils.setProductForm(productForm,"puma","clothing","abc","jockey",Double.valueOf(25.4));
        productDto.addProduct(productForm);

        InventoryForm inventoryForm = new InventoryForm();
        inventoryForm.setBarcode("abc");
        inventoryForm.setQuantity(Integer.valueOf(25));

        inventoryDto.addInventory(inventoryForm);

        inventoryForm.setQuantity(Integer.valueOf(30));
        inventoryDto.updateInventory(inventoryForm);

        InventoryData inventoryData = inventoryDto.getInventory("abc");
        assertEquals("abc",inventoryData.getBarcode());
        assertEquals(Integer.valueOf(30),inventoryData.getQuantity());
    }

    @Test
    public void getAllInventoryTest() throws ApiException{
        BrandForm brandForm = new BrandForm();
        TestUtils.setBrandForm(brandForm,"puma","clothing");
        brandCategoryDto.addBrandCategory(brandForm);

        List<ProductForm> productForms = Arrays.asList(new ProductForm(),new ProductForm());
        TestUtils.setProductForm(productForms.get(0),"puma","clothing","abc","jockey",Double.valueOf(25.4));
        productDto.addProduct(productForms.get(0));

        TestUtils.setProductForm(productForms.get(1),"puma","clothing","def","van huesen",Double.valueOf(35.4));
        productDto.addProduct(productForms.get(1));


        List<InventoryForm> inventoryForms = Arrays.asList(new InventoryForm(),new InventoryForm());

        inventoryForms.get(0).setBarcode("abc");
        inventoryForms.get(0).setQuantity(Integer.valueOf(25));
        inventoryDto.addInventory(inventoryForms.get(0));

        inventoryForms.get(1).setBarcode("def");
        inventoryForms.get(1).setQuantity(Integer.valueOf(35));
        inventoryDto.addInventory(inventoryForms.get(1));

        List<InventoryData> inventoryDataList = inventoryDto.getAllInventory();

        assertEquals("abc",inventoryDataList.get(0).getBarcode());
        assertEquals(Integer.valueOf(25),inventoryDataList.get(0).getQuantity());
        assertEquals("def",inventoryDataList.get(1).getBarcode());
        assertEquals(Integer.valueOf(35),inventoryDataList.get(1).getQuantity());

    }

    @Test(expected = ApiException.class)
    public void validateInventoryFormBarcodeNullTest() throws ApiException{
        InventoryForm inventoryForm = new InventoryForm();
        inventoryForm.setBarcode(null);
        inventoryForm.setQuantity(Integer.valueOf(25));
        DtoUtils.validateInventoryForm(inventoryForm);
    }

    @Test(expected = ApiException.class)
    public void validateInventoryFormEmptyBarcodeTest() throws ApiException{
        InventoryForm inventoryForm = new InventoryForm();
        inventoryForm.setBarcode(" ");
        inventoryForm.setQuantity(Integer.valueOf(25));
        DtoUtils.validateInventoryForm(inventoryForm);
    }

    @Test(expected = ApiException.class)
    public void validateInventoryFormNullQuantityTest() throws ApiException{
        InventoryForm inventoryForm = new InventoryForm();
        inventoryForm.setBarcode("abc");
        inventoryForm.setQuantity(null);
        DtoUtils.validateInventoryForm(inventoryForm);
    }

    @Test(expected = ApiException.class)
    public void validateInventoryFormNegativeQuantityTest() throws ApiException{
        InventoryForm inventoryForm = new InventoryForm();
        inventoryForm.setBarcode("abc");
        inventoryForm.setQuantity(Integer.valueOf(-25));
        DtoUtils.validateInventoryForm(inventoryForm);
    }

    @Test
    public void validateInventoryFormQuantityZeroTest() throws ApiException{
        InventoryForm inventoryForm = new InventoryForm();
        inventoryForm.setBarcode("abc");
        inventoryForm.setQuantity(Integer.valueOf(0));
        DtoUtils.validateInventoryForm(inventoryForm);
        assertEquals("abc",inventoryForm.getBarcode());
        assertEquals(Integer.valueOf(0),inventoryForm.getQuantity());
    }

    @Test
    public void normalizeInventoryFormTest() throws ApiException{
        InventoryForm inventoryForm = new InventoryForm();
        inventoryForm.setBarcode("   A ny ThInG");
        inventoryForm.setQuantity(Integer.valueOf(25));
        DtoUtils.normalizeInventoryForm(inventoryForm);
        assertEquals("a ny thing",inventoryForm.getBarcode());
    }

    @Test
    public void convertInventoryFormToPojoTest() throws ApiException{
        InventoryForm inventoryForm = new InventoryForm();
        inventoryForm.setQuantity(Integer.valueOf(25));

        InventoryPojo inventoryPojo = DtoUtils.convertInventoryFormToPojo(inventoryForm,1);
        assertEquals(Integer.valueOf(25),inventoryPojo.getQuantity());
        assertEquals(Integer.valueOf(1),inventoryPojo.getProductId());

    }

    @Test
    public void convertInventoryPojoToDataTest() throws ApiException{
        InventoryPojo inventoryPojo = new InventoryPojo();
        inventoryPojo.setQuantity(Integer.valueOf(25));
        InventoryData inventoryData = DtoUtils.convertInventoryPojoToData("jockey",inventoryPojo,"abc");

        assertEquals(Integer.valueOf(25),inventoryData.getQuantity());
        assertEquals("jockey",inventoryData.getProduct());
        assertEquals("abc",inventoryData.getBarcode());
    }


}
