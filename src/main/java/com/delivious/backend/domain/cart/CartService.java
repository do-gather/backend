package com.delivious.backend.domain.cart;


import com.delivious.backend.domain.cart.entity.Cart;
import com.delivious.backend.domain.cart.repository.CartRepository;
import com.delivious.backend.domain.cart.entity.CartItem;
import com.delivious.backend.domain.cart.repository.CartItemRepository;
import com.delivious.backend.domain.Item.repository.ItemRepository;
import com.delivious.backend.domain.Item.entity.Item;
import com.delivious.backend.domain.user.User;
import com.delivious.backend.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final ItemRepository itemRepository;
    private final CartItemRepository cartItemRepository;

    // 회원가입 하면 회원당 카트 하나 생성
    public void createCart(User user){

        Cart cart = Cart.createCart(user);

        cartRepository.save(cart);
    }

    // 장바구니 담기
    @Transactional
    public void addCart(User user, Item newItem, int amount) {

        // 유저 id로 해당 유저의 장바구니 찾기
        Cart cart = cartRepository.findByUserId(user.getId());

        // 장바구니가 존재하지 않는다면
        if (cart == null) {
            cart = Cart.createCart(user);
            cartRepository.save(cart);
        }

        Item item = itemRepository.findItemById(newItem.getId());
        CartItem cartItem = cartItemRepository.findByCartIdAndItemId(cart.getId(), item.getId());

        // 상품이 장바구니에 존재하지 않는다면 카트상품 생성 후 추가
        if (cartItem == null) {
            cartItem = CartItem.createCartItem(cart, item, amount);
            cartItemRepository.save(cartItem);
        }

        // 상품이 장바구니에 이미 존재한다면 수량만 증가
        else {
            CartItem update = cartItem;
            update.setCart(cartItem.getCart());
            update.setItem(cartItem.getItem());
            update.addCount(amount);
            update.setCount(update.getCount());
            cartItemRepository.save(update);
        }

        // 카트 상품 총 개수 증가
        cart.setCount(cart.getCount()+amount);

    }

    // 유저 id로 해당 유저의 장바구니 찾기
    public Cart findUserCart(int userId) {

        return cartRepository.findCartByUserId(userId);

    }

    // 카트 상품 리스트 중 해당하는 유저가 담은 상품만 반환
    // 유저의 카트 id와 카트상품의 카트 id가 같아야 함
    public List<CartItem> allUserCartView(Cart userCart) {

        // 유저의 카트 id를 꺼냄
        int userCartId = userCart.getId();

        // id에 해당하는 유저가 담은 상품들 넣어둘 곳
        List<CartItem> UserCartItems = new ArrayList<>();

        // 유저 상관 없이 카트에 있는 상품 모두 불러오기
        List<CartItem> CartItems = cartItemRepository.findAll();

        for(CartItem cartItem : CartItems) {
            if(cartItem.getCart().getId() == userCartId) {
                UserCartItems.add(cartItem);
            }
        }

        return UserCartItems;
    }

    // 카트 상품 리스트 중 해당하는 상품 id의 상품만 반환
    public List<CartItem> findCartItemByItemId(int id) {

        List<CartItem> cartItems = cartItemRepository.findCartItemByItemId(id);

        return cartItems;
    }

    // 카트 상품 리스트 중 해당하는 상품 id의 상품만 반환
    public CartItem findCartItemById(int id) {

        CartItem cartItem = cartItemRepository.findCartItemById(id);

        return cartItem;
    }

    // 장바구니의 상품 개별 삭제
    public void cartItemDelete(int id) {

        cartItemRepository.deleteById(id);
    }

    // 장바구니 아이템 전체 삭제 -> 매개변수는 유저 id
    public void allCartItemDelete(int id) {

        // 전체 cartItem 찾기
        List<CartItem> cartItems = cartItemRepository.findAll();

        // 반복문을 이용하여 해당하는 User 의 CartItem 만 찾아서 삭제
        for(CartItem cartItem : cartItems){

            if(cartItem.getCart().getUser().getId() == id) {

                cartItem.getCart().setCount(0);

                cartItemRepository.deleteById(cartItem.getId());
            }
        }
    }

}
