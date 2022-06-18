package com.delivious.backend.domain.user;


import com.delivious.backend.domain.Item.ItemService;
import com.delivious.backend.domain.cart.CartService;
import com.delivious.backend.domain.config.auth.PrincipalDetails;
import com.delivious.backend.domain.order.OrderService;
import com.delivious.backend.domain.sale.SaleService;
import com.delivious.backend.domain.cart.entity.Cart;
import com.delivious.backend.domain.cart.entity.CartItem;
import com.delivious.backend.domain.Item.entity.Item;
import com.delivious.backend.domain.order.entity.OrderItem;
import com.delivious.backend.domain.sale.entity.SaleItem;
import com.delivious.backend.domain.user.User;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

// 구매자에 해당하는 페이지 관리
// 마이페이지, 회원정보수정, 장바구니, 주문, 주문취소

@RequiredArgsConstructor
@Controller
public class UserPageController {

    private final UserPageService userPageService;
    private final CartService cartService;
    private final ItemService itemService;
    private final OrderService orderService;
    private final SaleService saleService;

    // 유저 페이지 접속
    @GetMapping("/user/{id}")
    public String userPage(@PathVariable("id") Integer id, Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        // 로그인이 되어있는 유저의 id와 유저 페이지에 접속하는 id가 같아야 함
        if (principalDetails.getUser().getId() == id) {

            model.addAttribute("user", userPageService.findUser(id));

            return "/user/userPage";
        } else {
            return "redirect:/main";
        }
    }

    // 회원 정보 수정
    @GetMapping("/user/modify/{id}")
    public String userModify(@PathVariable("id") Integer id, Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        // 로그인이 되어있는 유저의 id와 수정페이지에 접속하는 id가 같아야 함
        if (principalDetails.getUser().getId() == id) {

            model.addAttribute("user", userPageService.findUser(id));

            return "/userModify";
        } else {
            return "redirect:/main";
        }

    }

    // 수정 실행
    @PostMapping("/user/update/{id}")
    public String userUpdate(@PathVariable("id") Integer id, User user) {

        userPageService.userModify(user);

        return "redirect:/user/{id}";
    }

    // 장바구니 페이지 접속
    @GetMapping("/user/cart/{id}")
    public String userCartPage(@PathVariable("id") Integer id, Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        // 로그인이 되어있는 유저의 id와 장바구니에 접속하는 id가 같아야 함
        if (principalDetails.getUser().getId() == id) {

            User user = userPageService.findUser(id);
            // 로그인 되어 있는 유저에 해당하는 장바구니 가져오기
            Cart userCart = user.getCart();

            // 장바구니에 들어있는 아이템 모두 가져오기
            List<CartItem> cartItemList = cartService.allUserCartView(userCart);

            // 장바구니에 들어있는 상품들의 총 가격
            int totalPrice = 0;
            for (CartItem cartitem : cartItemList) {
                totalPrice += cartitem.getCount() * cartitem.getItem().getPrice();
            }

            model.addAttribute("totalPrice", totalPrice);
            model.addAttribute("totalCount", userCart.getCount());
            model.addAttribute("cartItems", cartItemList);
            model.addAttribute("user", userPageService.findUser(id));

            return "/user/userCart";
        }
        // 로그인 id와 장바구니 접속 id가 같지 않는 경우
        else {
            return "redirect:/main";
        }
    }

    // 장바구니에 물건 넣기
    @PostMapping("/user/cart/{id}/{itemId}")
    public String addCartItem(@PathVariable("id") Integer id, @PathVariable("itemId") Integer itemId, int amount) {

        User user = userPageService.findUser(id);
        Item item = itemService.itemView(itemId);

        cartService.addCart(user, item, amount);

        return "redirect:/item/view/{itemId}";
    }

    // 장바구니에서 물건 삭제
    // 삭제하고 남은 상품의 총 개수
    @GetMapping("/user/cart/{id}/{cartItemId}/delete")
    public String deleteCartItem(@PathVariable("id") Integer id, @PathVariable("cartItemId") Integer itemId, Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        // 로그인 유저 id와 장바구니 유저의 id가 같아야 함
        if (principalDetails.getUser().getId() == id) {
            // itemId로 장바구니 상품 찾기
            CartItem cartItem = cartService.findCartItemById(itemId);

            // 해당 유저의 카트 찾기
            Cart userCart = cartService.findUserCart(id);

            // 장바구니 전체 수량 감소
            userCart.setCount(userCart.getCount()-cartItem.getCount());

            // 장바구니 물건 삭제
            cartService.cartItemDelete(itemId);

            // 해당 유저의 장바구니 상품들
            List<CartItem> cartItemList = cartService.allUserCartView(userCart);

            // 총 가격 += 수량 * 가격
            int totalPrice = 0;
            for (CartItem cartitem : cartItemList) {
                totalPrice += cartitem.getCount() * cartitem.getItem().getPrice();
            }

            // 총 개수 += 수량
            //int totalCount = 0;
            //for (CartItem cartitem : cartItemList) {
            //    totalCount += cartitem.getCount();
            //}


            model.addAttribute("totalPrice", totalPrice);
            model.addAttribute("totalCount", userCart.getCount());
            model.addAttribute("cartItems", cartItemList);
            model.addAttribute("user", userPageService.findUser(id));

            return "/user/userCart";
        }
        // 로그인 id와 장바구니 삭제하려는 유저의 id가 같지 않는 경우
        else {
            return "redirect:/main";
        }
    }

    // 주문 내역 조회 페이지
    @GetMapping("/user/orderHist/{id}")
    public String orderList(@PathVariable("id") Integer id, @AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
        // 로그인이 되어있는 유저의 id와 주문 내역에 접속하는 id가 같아야 함
        if (principalDetails.getUser().getId() == id) {

            // 로그인 되어 있는 유저에 해당하는 구매내역 가져오기
            List<OrderItem> orderItemList = orderService.findUserOrderItems(id);

            // 총 주문 개수
            int totalCount = 0;
            for (OrderItem orderItem : orderItemList) {
                if (orderItem.getIsCancel() != 1)
                    totalCount += orderItem.getItemCount();
            }

            model.addAttribute("totalCount", totalCount);
            model.addAttribute("orderItems", orderItemList);
            model.addAttribute("user", userPageService.findUser(id));

            return "user/userOrderList";
        }
        // 로그인 id와 주문 내역 접속 id가 같지 않는 경우
        else {
            return "redirect:/main";
        }
    }

    // 장바구니 상품 전체 주문
    @Transactional
    @PostMapping("/user/cart/checkout/{id}")
    public String cartCheckout(@PathVariable("id") Integer id, @AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
        // 로그인이 되어있는 유저의 id와 주문하는 id가 같아야 함
        if(principalDetails.getUser().getId() == id) {
            User user = userPageService.findUser(id);

            // 유저 카트 찾기
            Cart userCart = cartService.findUserCart(user.getId());

            // 유저 카트 안에 있는 상품들
            List<CartItem> userCartItems = cartService.allUserCartView(userCart);

            // 최종 결제 금액
            int totalPrice = 0;
            for (CartItem cartItem : userCartItems) {
                // 장바구니 안에 있는 상품의 재고가 없거나 재고보다 많이 주문할 경우
                if (cartItem.getItem().getStock() == 0 || cartItem.getItem().getStock() < cartItem.getCount()) {
                    return "redirect:/main";
                }
                totalPrice += cartItem.getCount() * cartItem.getItem().getPrice();
            }

            int userCoin = user.getCoin();
            // 유저의 현재 잔액이 부족하다면
            if (userCoin < totalPrice) {
                return "redirect:/main";
            } else {
                // 유저 돈에서 최종 결제금액 빼야함
                user.setCoin(user.getCoin() - totalPrice);

                List<OrderItem> orderItemList = new ArrayList<>();

                for (CartItem cartItem : userCartItems) {
                    // 각 상품에 대한 판매자
                    User seller = cartItem.getItem().getSeller();

                    // 판매자 수익 증가
                    seller.setCoin(seller.getCoin() + (cartItem.getCount() * cartItem.getItem().getPrice()));

                    // 재고 감소
                    cartItem.getItem().setStock(cartItem.getItem().getStock() - cartItem.getCount());

                    // 상품 개별로 판매 개수 증가
                    cartItem.getItem().setCount(cartItem.getItem().getCount() + cartItem.getCount());

                    // sale, saleItem 에 담기
                    SaleItem saleItem = saleService.addSale(cartItem.getItem().getId(), seller.getId(), cartItem);

                    // order, orderItem 에 담기
                    OrderItem orderItem = orderService.addCartOrder(cartItem.getItem().getId(), user.getId(), cartItem, saleItem);

                    orderItemList.add(orderItem);
                }

                orderService.addOrder(user, orderItemList);

                // 장바구니 상품 모두 삭제
                cartService.allCartItemDelete(id);
            }

            model.addAttribute("totalPrice", totalPrice);
            model.addAttribute("cartItems", userCartItems);
            model.addAttribute("user", userPageService.findUser(id));

            return "redirect:/user/cart/{id}";
        } else {
            return "redirect:/main";
        }
    }

    // 상품 개별 주문 -> 상품 상세페이지에서 구매하기 버튼으로 주문
    @Transactional
    @PostMapping("/user/{id}/checkout/{itemId}")
    public String checkout(@PathVariable("id") Integer id, @PathVariable("itemId") Integer itemId, @AuthenticationPrincipal PrincipalDetails principalDetails, Model model, int count) {
        // 로그인이 되어있는 유저의 id와 주문하는 id가 같아야 함
        if(principalDetails.getUser().getId() == id) {

            User user = userPageService.findUser(id);
            Item item = itemService.itemView(itemId);

            // 상품의 재고가 0이거나 재고가 적은 경우
            if (item.getStock() == 0 || item.getStock() < count) {
                return "redirect:/main";
            }

            // 최종 결제 금액
            int totalPrice = item.getPrice() * count;

            int userCoin = user.getCoin();
            // 유저의 현재 잔액이 부족하다면
            if (userCoin < totalPrice) {
                return "redirect:/main";
            } else {
                // 유저 돈에서 최종 결제금액 빼야함
                user.setCoin(user.getCoin() - totalPrice);

                // 판매자의 돈은 최종 결제금액만큼 늘어남
                item.getSeller().setCoin(item.getSeller().getCoin() + totalPrice);

                // 해당 상품들의 재고는 각각 구매한 수량만큼 줄어듬
                item.setStock(item.getStock() - count);
                item.setCount(item.getCount() + count);

                // sale, saleItem 에 담기
                SaleItem saleItem = saleService.addSale(item.getSeller().getId(), item, count);

                // order, orderItem 에 담기
                orderService.addOneItemOrder(user.getId(), item, count, saleItem);
            }

            return "redirect:/user/orderHist/{id}";
        } else {
            return "redirect:/main";
        }
    }

    // 주문 취소 기능
    @PostMapping("/user/{id}/checkout/cancel/{orderItemId}")
    public String cancelOrder(@PathVariable("id") Integer id, @PathVariable("orderItemId") Integer orderItemId, Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        // 로그인이 되어있는 유저의 id와 주문 취소하는 유저의 id가 같아야 함
        if (principalDetails.getUser().getId() == id) {
            // 취소할 상품 찾기
            OrderItem cancelItem= orderService.findOrderitem(orderItemId);
            // 취소하는 유저 찾기
            User user = userPageService.findUser(id);

            // 주문 내역 총 개수에서 취소 상품 개수 줄어듬
            List<OrderItem> orderItemList = orderService.findUserOrderItems(id);
            int totalCount = 0;
            for (OrderItem orderItem : orderItemList) {
                totalCount += orderItem.getItemCount();
            }
            totalCount = totalCount - cancelItem.getItemCount();

            orderService.orderCancel(user, cancelItem);

            model.addAttribute("totalCount", totalCount);
            model.addAttribute("orderItems", orderItemList);
            model.addAttribute("user", user);
            //model.addAttribute("message", "주문 취소가 완료되었습니다.");
            //model.addAttribute("searchUrl", "/user/orderHist/{id}");

            return "redirect:/user/orderHist/{id}";

        }
        // 로그인 id와 주문취소하는 유저 id가 같지 않는 경우 취소 불가
        else {
            return "redirect:/main";
        }
    }

    // 잔액 충전 페이지
    @Transactional
    @GetMapping("/user/cash/{id}")
    public String charge(@PathVariable("id") Integer id, Model model, @AuthenticationPrincipal PrincipalDetails principalDetails){
        // 로그인 유저와 잔액 충전 페이지에 접속하는 id가 같아야 함.
        if(principalDetails.getUser().getId() == id){
            // 유저의 주문내역
            User user = userPageService.findUser(id);
            model.addAttribute("user",user);

            return "/user/cash";
        }else{
            return "redirect:/main";
        }
    }

    // 잔액충전 처리
    @GetMapping("/user/charge/pro")
    public String chargePro(int amount, @AuthenticationPrincipal PrincipalDetails principalDetails){
        User user = userPageService.findUser(principalDetails.getUser().getId());
        userPageService.chargePoint(user.getId(),amount);
        return "redirect:/main";
    }

}
