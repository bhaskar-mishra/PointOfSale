package com.increff.pos.dto;

import static org.junit.Assert.assertEquals;

import com.increff.pos.model.form.*;
import com.increff.pos.model.data.*;
import com.increff.pos.pojo.OrderItemPojo;
import com.increff.pos.service.AbstractUnitTest;
import com.increff.pos.service.ApiException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class OrderItemDtoTest extends AbstractUnitTest {

    @Autowired
    private OrderItemDto orderItemDto;
    @Autowired
    private OrderDto orderDto;
    @Autowired
    private BrandCategoryDto brandCategoryDto;
    @Autowired
    private ProductDto productDto;
    @Autowired
    private InventoryDto inventoryDto;




    @Test
    public void addItemTest() throws ApiException {
        OrderData orderData = orderDto.createOrder();
        OrderItemForm orderItemForm = new OrderItemForm();

        BrandForm brandForm = new BrandForm();
        TestUtils.setBrandForm(brandForm,"puma","clothing");
        brandCategoryDto.addBrandCategory(brandForm);

        ProductForm productForm = new ProductForm();
        TestUtils.setProductForm(productForm,"puma","clothing","abc","jockey",Double.valueOf(25.4));
        productDto.addProduct(productForm);

        InventoryForm inventoryForm = new InventoryForm();
        TestUtils.setInventoryForm(inventoryForm,Integer.valueOf(25),"abc");
        inventoryDto.addInventory(inventoryForm);

        TestUtils.setOrderItemForm(orderItemForm,orderData.getOrderCode(),"abc",10,Double.valueOf(10.5));
        orderItemDto.addItem(orderItemForm);

        List<OrderItemData> orderItemDataList = orderItemDto.getOrderItemsByCode(orderData.getOrderCode());

        assertEquals(orderItemForm.getBarcode(),orderItemDataList.get(0).getBarcode());
        assertEquals(orderItemForm.getQuantity(),orderItemDataList.get(0).getQuantity());
        assertEquals(orderItemForm.getPrice(),orderItemDataList.get(0).getPrice());
    }

    @Test
    public void editOrderItemTest() throws ApiException {
        OrderData orderData = orderDto.createOrder();
        OrderItemForm orderItemForm = new OrderItemForm();

        BrandForm brandForm = new BrandForm();
        TestUtils.setBrandForm(brandForm,"puma","clothing");
        brandCategoryDto.addBrandCategory(brandForm);

        ProductForm productForm = new ProductForm();
        TestUtils.setProductForm(productForm,"puma","clothing","abc","jockey",Double.valueOf(25.4));
        productDto.addProduct(productForm);

        InventoryForm inventoryForm = new InventoryForm();
        TestUtils.setInventoryForm(inventoryForm,Integer.valueOf(25),"abc");
        inventoryDto.addInventory(inventoryForm);

        TestUtils.setOrderItemForm(orderItemForm,orderData.getOrderCode(),"abc",20,Double.valueOf(10.5));
        orderItemDto.addItem(orderItemForm);

        UpdateOrderItemForm updateOrderItemForm = new UpdateOrderItemForm();
        TestUtils.setUpdateOrderItemForm(updateOrderItemForm,orderData.getOrderCode(),"abc",Integer.valueOf(10));

        orderItemDto.editOrderItem(updateOrderItemForm);

        List<OrderItemData> orderItemDataList = orderItemDto.getOrderItemsByCode(orderData.getOrderCode());

        assertEquals("abc",orderItemDataList.get(0).getBarcode());
        assertEquals(Integer.valueOf(10),orderItemDataList.get(0).getQuantity());
        assertEquals(Double.valueOf(10.5),orderItemDataList.get(0).getPrice());
    }

    @Test(expected = ApiException.class)
    public void editOrderItemQuantityLimitTest() throws ApiException{
        OrderData orderData = orderDto.createOrder();
        OrderItemForm orderItemForm = new OrderItemForm();

        BrandForm brandForm = new BrandForm();
        TestUtils.setBrandForm(brandForm,"puma","clothing");
        brandCategoryDto.addBrandCategory(brandForm);

        ProductForm productForm = new ProductForm();
        TestUtils.setProductForm(productForm,"puma","clothing","abc","jockey",Double.valueOf(25.4));
        productDto.addProduct(productForm);

        InventoryForm inventoryForm = new InventoryForm();
        TestUtils.setInventoryForm(inventoryForm,Integer.valueOf(25),"abc");
        inventoryDto.addInventory(inventoryForm);

        TestUtils.setOrderItemForm(orderItemForm,orderData.getOrderCode(),"abc",20,Double.valueOf(10.5));
        orderItemDto.addItem(orderItemForm);

        UpdateOrderItemForm updateOrderItemForm = new UpdateOrderItemForm();
        TestUtils.setUpdateOrderItemForm(updateOrderItemForm,orderData.getOrderCode(),"abc",Integer.valueOf(30));

        orderItemDto.editOrderItem(updateOrderItemForm);
    }

    @Test
    public void getOrderItemByIdTest() throws ApiException {
        OrderData orderData = orderDto.createOrder();
        OrderItemForm orderItemForm = new OrderItemForm();

        BrandForm brandForm = new BrandForm();
        TestUtils.setBrandForm(brandForm,"puma","clothing");
        brandCategoryDto.addBrandCategory(brandForm);

        ProductForm productForm = new ProductForm();
        TestUtils.setProductForm(productForm,"puma","clothing","abc","jockey",Double.valueOf(25.4));
        productDto.addProduct(productForm);

        InventoryForm inventoryForm = new InventoryForm();
        TestUtils.setInventoryForm(inventoryForm,Integer.valueOf(25),"abc");
        inventoryDto.addInventory(inventoryForm);

        TestUtils.setOrderItemForm(orderItemForm,orderData.getOrderCode(),"abc",20,Double.valueOf(10.5));
        orderItemDto.addItem(orderItemForm);

        List<OrderItemData> orderItemDataList = orderItemDto.getOrderItemsByCode(orderData.getOrderCode());
        OrderItemData orderItemData = orderItemDto.getOrderItemById(orderItemDataList.get(0).getOrderItemId());

        assertEquals(orderItemData.getOrderId(),orderItemDataList.get(0).getOrderId());
        assertEquals(orderItemData.getOrderItemId(),orderItemDataList.get(0).getOrderItemId());
        assertEquals(orderItemData.getBarcode(),orderItemDataList.get(0).getBarcode());
        assertEquals(orderItemData.getProduct(),orderItemDataList.get(0).getProduct());
        assertEquals(orderItemData.getPrice(),orderItemDataList.get(0).getPrice());
        assertEquals(orderItemData.getTotal(),orderItemDataList.get(0).getTotal());
        assertEquals(orderItemData.getQuantity(),orderItemDataList.get(0).getQuantity());
    }

    @Test
    public void getOrderItemByCodeTest() throws ApiException{
        OrderData orderData = orderDto.createOrder();
        OrderItemForm orderItemForm = new OrderItemForm();

        BrandForm brandForm = new BrandForm();
        TestUtils.setBrandForm(brandForm,"puma","clothing");
        brandCategoryDto.addBrandCategory(brandForm);

        ProductForm productForm = new ProductForm();
        TestUtils.setProductForm(productForm,"puma","clothing","abc","jockey",Double.valueOf(25.4));
        productDto.addProduct(productForm);

        InventoryForm inventoryForm = new InventoryForm();
        TestUtils.setInventoryForm(inventoryForm,Integer.valueOf(25),"abc");
        inventoryDto.addInventory(inventoryForm);

        TestUtils.setOrderItemForm(orderItemForm,orderData.getOrderCode(),"abc",20,Double.valueOf(10.5));
        orderItemDto.addItem(orderItemForm);

        List<OrderItemData> orderItemDataList = orderItemDto.getOrderItemsByCode(orderData.getOrderCode());

        assertEquals("abc",orderItemDataList.get(0).getBarcode());
        assertEquals(Integer.valueOf(20),orderItemDataList.get(0).getQuantity());
        assertEquals(Double.valueOf(10.5),orderItemDataList.get(0).getPrice());
    }

    @Test(expected = ApiException.class)
    public void getOrderItemByCodeNullTest() throws ApiException{
        OrderData orderData = orderDto.createOrder();
        OrderItemForm orderItemForm = new OrderItemForm();

        BrandForm brandForm = new BrandForm();
        TestUtils.setBrandForm(brandForm,"puma","clothing");
        brandCategoryDto.addBrandCategory(brandForm);

        ProductForm productForm = new ProductForm();
        TestUtils.setProductForm(productForm,"puma","clothing","abc","jockey",Double.valueOf(25.4));
        productDto.addProduct(productForm);

        InventoryForm inventoryForm = new InventoryForm();
        TestUtils.setInventoryForm(inventoryForm,Integer.valueOf(25),"abc");
        inventoryDto.addInventory(inventoryForm);

        TestUtils.setOrderItemForm(orderItemForm,orderData.getOrderCode(),"abc",20,Double.valueOf(10.5));
        orderItemDto.addItem(orderItemForm);

        List<OrderItemData> orderItemDataList = orderItemDto.getOrderItemsByCode(null);
    }

    @Test(expected = ApiException.class)
    public void getOrderItemByCodeEmptyTest() throws ApiException{
        OrderData orderData = orderDto.createOrder();
        OrderItemForm orderItemForm = new OrderItemForm();

        BrandForm brandForm = new BrandForm();
        TestUtils.setBrandForm(brandForm,"puma","clothing");
        brandCategoryDto.addBrandCategory(brandForm);

        ProductForm productForm = new ProductForm();
        TestUtils.setProductForm(productForm,"puma","clothing","abc","jockey",Double.valueOf(25.4));
        productDto.addProduct(productForm);

        InventoryForm inventoryForm = new InventoryForm();
        TestUtils.setInventoryForm(inventoryForm,Integer.valueOf(25),"abc");
        inventoryDto.addInventory(inventoryForm);

        TestUtils.setOrderItemForm(orderItemForm,orderData.getOrderCode(),"abc",20,Double.valueOf(10.5));
        orderItemDto.addItem(orderItemForm);

        List<OrderItemData> orderItemDataList = orderItemDto.getOrderItemsByCode("  ");
    }

    @Test
    public void placeOrderTest() throws ApiException{
        OrderData orderData = orderDto.createOrder();
        OrderItemForm orderItemForm = new OrderItemForm();

        BrandForm brandForm = new BrandForm();
        TestUtils.setBrandForm(brandForm,"puma","clothing");
        brandCategoryDto.addBrandCategory(brandForm);

        ProductForm productForm = new ProductForm();
        TestUtils.setProductForm(productForm,"puma","clothing","abc","jockey",Double.valueOf(25.4));
        productDto.addProduct(productForm);

        InventoryForm inventoryForm = new InventoryForm();
        TestUtils.setInventoryForm(inventoryForm,Integer.valueOf(25),"abc");
        inventoryDto.addInventory(inventoryForm);

        TestUtils.setOrderItemForm(orderItemForm,orderData.getOrderCode(),"abc",20,Double.valueOf(10.5));
        orderItemDto.addItem(orderItemForm);

        orderItemDto.placeOrder(orderData.getOrderCode());
        OrderData orderData1 = orderDto.getOrderByCode(orderData.getOrderCode());
        assertEquals("PLACED",orderData1.getStatus());
    }

    @Test(expected = ApiException.class)
    public void placeOrderNoItemTest() throws ApiException{
        OrderData orderData = orderDto.createOrder();
        orderItemDto.placeOrder(orderData.getOrderCode());
    }

    @Test(expected = ApiException.class)
    public void placeOrderNullCodeTest() throws ApiException{
        OrderData orderData = orderDto.createOrder();
        OrderItemForm orderItemForm = new OrderItemForm();

        BrandForm brandForm = new BrandForm();
        TestUtils.setBrandForm(brandForm,"puma","clothing");
        brandCategoryDto.addBrandCategory(brandForm);

        ProductForm productForm = new ProductForm();
        TestUtils.setProductForm(productForm,"puma","clothing","abc","jockey",Double.valueOf(25.4));
        productDto.addProduct(productForm);

        InventoryForm inventoryForm = new InventoryForm();
        TestUtils.setInventoryForm(inventoryForm,Integer.valueOf(25),"abc");
        inventoryDto.addInventory(inventoryForm);

        TestUtils.setOrderItemForm(orderItemForm,orderData.getOrderCode(),"abc",20,Double.valueOf(10.5));
        orderItemDto.addItem(orderItemForm);

        orderItemDto.placeOrder(null);
    }

    @Test(expected = ApiException.class)
    public void placeOrderEmptyCodeTest() throws ApiException{
        OrderData orderData = orderDto.createOrder();
        OrderItemForm orderItemForm = new OrderItemForm();

        BrandForm brandForm = new BrandForm();
        TestUtils.setBrandForm(brandForm,"puma","clothing");
        brandCategoryDto.addBrandCategory(brandForm);

        ProductForm productForm = new ProductForm();
        TestUtils.setProductForm(productForm,"puma","clothing","abc","jockey",Double.valueOf(25.4));
        productDto.addProduct(productForm);

        InventoryForm inventoryForm = new InventoryForm();
        TestUtils.setInventoryForm(inventoryForm,Integer.valueOf(25),"abc");
        inventoryDto.addInventory(inventoryForm);

        TestUtils.setOrderItemForm(orderItemForm,orderData.getOrderCode(),"abc",20,Double.valueOf(10.5));
        orderItemDto.addItem(orderItemForm);

        orderItemDto.placeOrder("  ");
    }

    @Test
    public void deleteOrderItemByIdTest() throws ApiException{
        OrderData orderData = orderDto.createOrder();
        OrderItemForm orderItemForm = new OrderItemForm();

        BrandForm brandForm = new BrandForm();
        TestUtils.setBrandForm(brandForm,"puma","clothing");
        brandCategoryDto.addBrandCategory(brandForm);

        ProductForm productForm = new ProductForm();
        TestUtils.setProductForm(productForm,"puma","clothing","abc","jockey",Double.valueOf(25.4));
        productDto.addProduct(productForm);

        InventoryForm inventoryForm = new InventoryForm();
        TestUtils.setInventoryForm(inventoryForm,Integer.valueOf(25),"abc");
        inventoryDto.addInventory(inventoryForm);

        TestUtils.setOrderItemForm(orderItemForm,orderData.getOrderCode(),"abc",20,Double.valueOf(10.5));
        orderItemDto.addItem(orderItemForm);

        OrderItemData orderItemData = orderItemDto.getOrderItemsByCode(orderData.getOrderCode()).get(0);
        orderItemDto.deleteOrderItemById(orderItemData.getOrderItemId());
        List<OrderItemData> orderItemDataList = orderItemDto.getOrderItemsByCode(orderItemForm.getOrderCode());

        assertEquals(Integer.valueOf(0),Integer.valueOf(orderItemDataList.size()));
    }

    @Test(expected = ApiException.class)
    public void deleteOrderItemByIdNullTest() throws ApiException{
        OrderData orderData = orderDto.createOrder();
        OrderItemForm orderItemForm = new OrderItemForm();

        BrandForm brandForm = new BrandForm();
        TestUtils.setBrandForm(brandForm,"puma","clothing");
        brandCategoryDto.addBrandCategory(brandForm);

        ProductForm productForm = new ProductForm();
        TestUtils.setProductForm(productForm,"puma","clothing","abc","jockey",Double.valueOf(25.4));
        productDto.addProduct(productForm);

        InventoryForm inventoryForm = new InventoryForm();
        TestUtils.setInventoryForm(inventoryForm,Integer.valueOf(25),"abc");
        inventoryDto.addInventory(inventoryForm);

        TestUtils.setOrderItemForm(orderItemForm,orderData.getOrderCode(),"abc",20,Double.valueOf(10.5));
        orderItemDto.addItem(orderItemForm);

        orderItemDto.deleteOrderItemById(null);
    }

    @Test(expected = ApiException.class)
    public void validateEditOrderItemFormNullCodeTest() throws ApiException{
        UpdateOrderItemForm updateOrderItemForm = new UpdateOrderItemForm();
        TestUtils.setUpdateOrderItemForm(updateOrderItemForm,null,"abc",10);
        DtoUtils.validateEditOrderItemForm(updateOrderItemForm);
    }

    @Test(expected = ApiException.class)
    public void validateEditOrderItemFormEmptyCodeTest() throws ApiException{
        UpdateOrderItemForm updateOrderItemForm = new UpdateOrderItemForm();
        TestUtils.setUpdateOrderItemForm(updateOrderItemForm,"  ","abc",10);
        DtoUtils.validateEditOrderItemForm(updateOrderItemForm);
    }

    @Test(expected = ApiException.class)
    public void validateEditOrderItemFormNullBarcodeTest() throws ApiException{
        UpdateOrderItemForm updateOrderItemForm = new UpdateOrderItemForm();
        TestUtils.setUpdateOrderItemForm(updateOrderItemForm,"ordercode",null,10);
        DtoUtils.validateEditOrderItemForm(updateOrderItemForm);
    }

    @Test(expected = ApiException.class)
    public void validateEditOrderItemFormEmptyBarcodeTest() throws ApiException{
        UpdateOrderItemForm updateOrderItemForm = new UpdateOrderItemForm();
        TestUtils.setUpdateOrderItemForm(updateOrderItemForm,"ordercode","  ",10);
        DtoUtils.validateEditOrderItemForm(updateOrderItemForm);
    }

    @Test(expected = ApiException.class)
    public void validateEditOrderItemFormQuantityNullTest() throws ApiException{
        UpdateOrderItemForm updateOrderItemForm = new UpdateOrderItemForm();
        TestUtils.setUpdateOrderItemForm(updateOrderItemForm,"ordercode","abc",null);
        DtoUtils.validateEditOrderItemForm(updateOrderItemForm);
    }

    @Test(expected = ApiException.class)
    public void validateEditOrderItemFormNegativeQuantityTest() throws ApiException{
        UpdateOrderItemForm updateOrderItemForm = new UpdateOrderItemForm();
        TestUtils.setUpdateOrderItemForm(updateOrderItemForm,"ordercode","abc",Integer.valueOf(-10));
        DtoUtils.validateEditOrderItemForm(updateOrderItemForm);
    }

    @Test(expected = ApiException.class)
    public void validateOrderItemFormNullOrderCodeTest() throws ApiException{
        OrderItemForm orderItemForm = new OrderItemForm();
        TestUtils.setOrderItemForm(orderItemForm,null,"barcode",10,25.20);
        DtoUtils.validateOrderItemForm(orderItemForm);
    }

    @Test(expected = ApiException.class)
    public void validateOrderItemFormEmptyOrderCodeTest() throws ApiException{
        OrderItemForm orderItemForm = new OrderItemForm();
        TestUtils.setOrderItemForm(orderItemForm," ","barcode",10,25.20);
        DtoUtils.validateOrderItemForm(orderItemForm);
    }

    @Test(expected = ApiException.class)
    public void validateOrderItemFormNullBarcodeTest() throws ApiException{
        OrderItemForm orderItemForm = new OrderItemForm();
        TestUtils.setOrderItemForm(orderItemForm,"ordercode",null,10,25.20);
        DtoUtils.validateOrderItemForm(orderItemForm);
    }

    @Test(expected = ApiException.class)
    public void validateOrderItemFormEmptyBarcodeTest() throws ApiException{
        OrderItemForm orderItemForm = new OrderItemForm();
        TestUtils.setOrderItemForm(orderItemForm,"orderCode"," ",10,25.20);
        DtoUtils.validateOrderItemForm(orderItemForm);
    }

    @Test(expected = ApiException.class)
    public void validateOrderItemFormNullQuantityTest() throws ApiException{
        OrderItemForm orderItemForm = new OrderItemForm();
        TestUtils.setOrderItemForm(orderItemForm,"orderCode","barcode",null,25.20);
        DtoUtils.validateOrderItemForm(orderItemForm);
    }

    @Test(expected = ApiException.class)
    public void validateOrderItemFormNegativeQuantityTest() throws ApiException{
        OrderItemForm orderItemForm = new OrderItemForm();
        TestUtils.setOrderItemForm(orderItemForm,"orderCode","barcode",Integer.valueOf(-10),25.20);
        DtoUtils.validateOrderItemForm(orderItemForm);
    }

    @Test(expected = ApiException.class)
    public void validateOrderItemFormNullPriceTest() throws ApiException{
        OrderItemForm orderItemForm = new OrderItemForm();
        TestUtils.setOrderItemForm(orderItemForm,"orderCode","barcode",10,null);
        DtoUtils.validateOrderItemForm(orderItemForm);
    }

    @Test(expected = ApiException.class)
    public void validateOrderItemFormNegativePriceTest() throws ApiException{
        OrderItemForm orderItemForm = new OrderItemForm();
        TestUtils.setOrderItemForm(orderItemForm,"orderCode","barcode",10,Double.valueOf(-25.50));
        DtoUtils.validateOrderItemForm(orderItemForm);
    }

    @Test
    public void convertOrderItemFormToPojoTest() throws ApiException {
        OrderItemForm orderItemForm = new OrderItemForm();
        TestUtils.setOrderItemForm(orderItemForm,"orderCode","abc",10,Double.valueOf(25.50));
        OrderItemPojo orderItemPojo = DtoUtils.convertOrderItemFormToPojo(orderItemForm,1,2);
        assertEquals(Integer.valueOf(1),orderItemPojo.getProductId());
        assertEquals(Integer.valueOf(2),orderItemPojo.getOrderId());
        assertEquals(Integer.valueOf(10),orderItemPojo.getQuantity());
        assertEquals(Double.valueOf(25.5),orderItemPojo.getSellingPrice());
    }

    @Test
    public void convertOrderItemPojoToDataTest() throws ApiException {
        OrderItemPojo orderItemPojo = new OrderItemPojo();
        TestUtils.setOrderItemPojo(orderItemPojo, 1, 2, 3, 10, 25.50);
        OrderItemData orderItemData = DtoUtils.convertOrderItemPojoToData(orderItemPojo, "jockey", "abc");

        assertEquals(Integer.valueOf(1), orderItemData.getOrderItemId());
        assertEquals("jockey", orderItemData.getProduct());
        assertEquals(Integer.valueOf(3), orderItemData.getOrderId());
        assertEquals(Integer.valueOf(10), orderItemData.getQuantity());
        assertEquals("abc", orderItemData.getBarcode());
        assertEquals(Double.valueOf(25.50), orderItemData.getPrice());
        assertEquals(Double.valueOf(25.50 * 10), orderItemData.getTotal());
    }
}
