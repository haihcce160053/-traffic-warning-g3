package com.namhaigroup.map;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.namhaigroup.map.adapter.CartAdapter;
import com.namhaigroup.map.dao.CartDAO;
import com.namhaigroup.map.dao.OrderDAO;
import com.namhaigroup.map.dao.VoucherDAO;
import com.namhaigroup.map.object.Cart;
import com.namhaigroup.map.object.Orders;
import com.namhaigroup.map.system.UserInformation;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CartActivity extends AppCompatActivity {
    RecyclerView rcCartView;
    private CartAdapter cartAdapter;
    List<Cart> cartList;
    TextView tvTotalPrice, tvVoucherPrice;
    EditText etVoucher;
    Button btnVoucherApply, btnOrder;
    CartDAO cartDAO;
    OrderDAO orderDAO;
    List<Cart> listOfCart;
    private double total_price = 0;
    private int voucher_id = 0;
    private boolean isUseVoucher = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        tvTotalPrice = findViewById(R.id.tvTotalPrice);
        btnVoucherApply = findViewById(R.id.btnVoucherApply);
        etVoucher = findViewById(R.id.etVoucher);
        tvVoucherPrice = findViewById(R.id.tvVoucherPrice);
        btnOrder = findViewById(R.id.btnOrder);
        rcCartView = findViewById(R.id.rcCartView);
        cartDAO = new CartDAO(this);
        orderDAO = new OrderDAO(this);
        cartInit();

        btnVoucherApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isUseVoucher == false) {
                    String user_voucher = etVoucher.getText().toString();
                    VoucherDAO voucherDAO = new VoucherDAO(CartActivity.this);
                    if(voucherDAO.getVoucherByCode(user_voucher) != null) {
                        if(user_voucher.trim().equals(voucherDAO.getVoucherByCode(user_voucher).getVoucher_code())) {
                            isUseVoucher = true;
                            voucher_id = voucherDAO.getVoucherByCode(user_voucher).getId();
                            tvVoucherPrice.setText("-" + String.valueOf(formatCurrency(voucherDAO.getVoucherByCode(user_voucher).getSale())));
                            total_price = total_price - (voucherDAO.getVoucherByCode(user_voucher).getSale());
                            tvTotalPrice.setText(String.valueOf(formatCurrency(total_price)));
                        } else {
                            Toast.makeText(CartActivity.this, "Mã khuyến mãi không hợp lệ", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(CartActivity.this, "Mã khuyến mãi không hợp lệ", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(CartActivity.this, "Không thể áp dụng thêm mã khuyến mãi", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listOfCart.size() > 0) {
                    VoucherDAO voucherDAO = new VoucherDAO(CartActivity.this);
                    if(isUseVoucher == true) {
                        voucherDAO.deleteVoucherById(voucher_id);
                    }
                    List<Orders> orders = new ArrayList<>();
                    Date now = new Date();
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    String dateTimeString = formatter.format(now);
                    for (Cart cart : listOfCart) {
                        Orders newOrder = new Orders();
                        newOrder.setUsername(cart.getUsername());
                        newOrder.setProduct_id(cart.getProduct_id());
                        newOrder.setQuantity(cart.getQuantity());
                        newOrder.setTotal_price(cart.getProducts().getPrice());
                        newOrder.setOrder_date(dateTimeString);
                        newOrder.setStatus(1);
                        orders.add(newOrder);
                    }
                    orderDAO.createOrders(orders);
                    cartDAO.deleteCartList(listOfCart);
                    Toast.makeText(CartActivity.this, "Đặt hàng thành công", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(CartActivity.this, "Bạn chưa có sản phẩm nào trong giỏ hàng", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void cartInit() {
        listOfCart = new ArrayList<>();
        cartList = cartDAO.getCartByUsername(UserInformation.username);
        total_price = 0;
        for (Cart cart : cartList) {
            total_price = total_price + cart.getProducts().getPrice() * cart.getQuantity();
            listOfCart.add(cart);
        }
        rcCartView.setLayoutManager(new LinearLayoutManager(this));
        cartAdapter = new CartAdapter(listOfCart, this);
        rcCartView.setAdapter(cartAdapter);
        tvTotalPrice.setText(formatCurrency(total_price));
        isUseVoucher = false;
        tvVoucherPrice.setText("0đ");
        etVoucher.setText("");
        cartAdapter.setOnAddMoreClickListener(new CartAdapter.OnAddMoreClickListener() {
            @Override
            public void onAddMoreClick(int cartId) {
                if(cartDAO.increaseQuantity(cartId) == true) {
                    total_price = total_price + (cartDAO.getCartById(cartId).getProducts().getPrice() * cartDAO.getCartById(cartId).getQuantity());
                    cartInit();
                }
            }
        });

        cartAdapter.setOnMinusMoreClickListener(new CartAdapter.OnMinusMoreClickListener() {
            @Override
            public void onMinusMoreClick(int cartId) {
                if(cartDAO.getCartById(cartId).getQuantity() == 1) {
                    cartDAO.deleteCartById(cartId);
                    cartInit();
                } else {
                    if(cartDAO.decreaseQuantity(cartId) == true) {
                        total_price = total_price + (cartDAO.getCartById(cartId).getProducts().getPrice() * cartDAO.getCartById(cartId).getQuantity());
                        cartInit();
                    }
                }
            }
        });
    }

    private String formatCurrency(double amount) {
        Locale vietnameseLocale = new Locale("vi", "VN");
        Currency vietnameseCurrency = Currency.getInstance(vietnameseLocale);
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(vietnameseLocale);
        currencyFormatter.setCurrency(vietnameseCurrency);
        return currencyFormatter.format(amount);
    }
}